package com.tradeitsignals.ui.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.helpers.ImageManager;

import java.io.File;

/**
 * Created by Kostyantin on 6/30/2015.
 */
public class BrokerViewHolder extends CommonViewHolder<Broker> {

    private Context context;
    private ImageManager imageManager;

    private ImageView ivLogo;
    private TextView tvMinDep;
    private TextView tvBonus;
    private RatingBar rbRating;

    public BrokerViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.imageManager = new ImageManager(context);
        ivLogo = (ImageView) view.findViewById(R.id.iv_broker_logo);
        tvMinDep = (TextView) view.findViewById(R.id.tv_broker_min_deposit_amount);
        tvBonus = (TextView) view.findViewById(R.id.tv_broker_bonus);
        rbRating = (RatingBar) view.findViewById(R.id.rb_broker_rating);
    }

    @Override
    public void populateViews(Broker broker) {
        Picasso.with(context)
               .load(broker.getLogoUrl())
               .config(Bitmap.Config.RGB_565)
               .fit()
               .centerCrop()
               .into(ivLogo);
        tvMinDep.setText("$" + broker.getMinDeposit());
        tvBonus.setText(broker.getPromotion());
        rbRating.setRating(broker.getRating());
    }

}
