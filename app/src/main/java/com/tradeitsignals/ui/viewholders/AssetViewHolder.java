package com.tradeitsignals.ui.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.ChartDataDAO;
import com.tradeitsignals.datamodel.data.AssetDataChart;
import com.tradeitsignals.datamodel.data.ChartData;
import com.tradeitsignals.datamodel.enums.AssetType;
import com.tradeitsignals.datamodel.enums.charts.ChartType;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.utils.Constants;
import com.tradeitsignals.utils.UIUtils;

/**
 * Created by ThorneBird on 2/13/2016.
 */
public class AssetViewHolder extends CommonViewHolder<AssetDataChart> {

    private Context context;
    private TextView tvAssetName;
    private ImageView imAsset;
    private ImageView ivChartIcon;
    private TextView tvChartSetting;
    private TextView tvInterval;
    private TextView tvIndicator;

    public AssetViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        tvAssetName = (TextView) view.findViewById(R.id.tv_asset_name);
        imAsset = (ImageView) view.findViewById(R.id.im_asset);
        tvChartSetting = (TextView) view.findViewById(R.id.tv_chart_settings);
        tvInterval = (TextView) view.findViewById(R.id.tv_interval_setting);
        tvIndicator = (TextView) view.findViewById(R.id.tv_indicator_setting);
        ivChartIcon = (ImageView) view.findViewById(R.id.iv_chart_icon);
        ivChartIcon.setColorFilter(context.getResources().getColor(R.color.myPrimaryColor));
    }

    @Override
    public void populateViews(final AssetDataChart item) {
        tvAssetName.setText(item.getAssetName());

        if (item.getAssetType() == AssetType.CURRENCY) {
            Integer flagId = getFlagId(item.getAssetName());
            Drawable assetImg = context.getResources().getDrawable(flagId);
            imAsset.setImageDrawable(assetImg);

        } else if (item.getAssetType() == AssetType.STOCK) {
            imAsset.setImageDrawable(context.getResources().getDrawable(getStockImageById(item.getAssetName())));
        }

        setData(tvInterval, tvIndicator, tvChartSetting, item.getAssetName());
    }

    private void setData(TextView tvInterval, TextView tvIndicator, TextView tvChartType, String assetName) {
        ChartData chartData = ChartDataDAO.getInstance().findByName(assetName);
        if (chartData != null && chartData.getAssetName().equals(assetName)) {
            tvInterval.setText(chartData.getIntervalDisplay());
            tvIndicator.setText(chartData.getIndicatorDisplay());
            tvChartType.setText(chartData.getChartTypeDisplay());
        } else {
            tvInterval.setText(Constants.DEFAULT_CHART_INTERVAL.getDisplay());
            tvIndicator.setText(Constants.DEFAULT_CHART_INDICATOR.getDisplay());
            tvChartType.setText(Constants.DEFAULT_CHART_TYPE.getDisplay());
        }
    }

    private Integer getFlagId(String key) {
        Integer id = UIUtils.CURRENCY_PAIR_FLAGS_MAP.get(key);
        return id;
    }

    private Integer getStockImageById(String key) {
        return UIUtils.STOCK_FLAGS_MAP.get(key);
    }
}
