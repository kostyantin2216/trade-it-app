package com.tradeitsignals.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;
import com.tradeitsignals.datamodel.enums.charts.ChartType;
import com.tradeitsignals.helpers.TIDictionary;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kostyantin on 5/31/2016.
 */
public class ChartToolbar extends FrameLayout
        implements View.OnClickListener {

    private ChartToolbarSelectionCallback mCallback;

    public enum Section {
        INDICATOR(TIDictionary.translate("indicator")),
        INTERVAL(TIDictionary.translate("interval")),
        TYPE(TIDictionary.translate("chart_type"));

        private final String title;

        Section(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    private ViewGroup selectionsContainer;

    public ChartToolbar(Context context, ChartToolbarSelectionCallback callback) {
        super(context);
        this.mCallback = callback;
        init(context, null, null);
    }

    private void init(Context context, AttributeSet attrs, Integer defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.component_chart_toolbar, this, true);

        if(!isInEditMode()) {
            this.selectionsContainer = (ViewGroup) findViewById(R.id.container_selections);

            findViewById(R.id.tv_interval).setOnClickListener(this);
            findViewById(R.id.tv_indicator).setOnClickListener(this);
            findViewById(R.id.tv_chart_type).setOnClickListener(this);
            findViewById(R.id.iv_toggle_toolbar).setOnClickListener(this);
        }
    }

    public void setSelectionCallback(ChartToolbarSelectionCallback callback) {
        this.mCallback = callback;
    }

    public void toggle() {
        if(selectionsContainer.getVisibility() == VISIBLE) {
            selectionsContainer.setVisibility(INVISIBLE);
        } else {
            selectionsContainer.setVisibility(VISIBLE);
        }
    }

    public void showSelectionsDialog(final Section section) {
        final List<String> selections;
        switch (section) {
            case INDICATOR:
                selections = ChartIndicator.toDisplayList();
                break;
            case INTERVAL:
                selections = ChartInterval.toDisplayList();
                break;
            case TYPE:
                selections = ChartType.toDisplayList();
                break;
            default:
                selections = Collections.emptyList();
        }

        int selectedIndex;
        if(mCallback == null) {
            selectedIndex = -1;
        } else {
            String selectedValue = mCallback.getSelectedToolbarValue(section);
            selectedIndex = selections.indexOf(selectedValue);
        }

        new MaterialDialog.Builder(getContext())
                .title(section.title)
                .items(selections)
                .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        if(mCallback != null) {
                            mCallback.onChartToolbarSelection(section, text.toString());
                        }
                        return true;
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toggle_toolbar:
                toggle();
                break;
            case R.id.tv_interval:
                showSelectionsDialog(Section.INTERVAL);
                break;
            case R.id.tv_indicator:
                showSelectionsDialog(Section.INDICATOR);
                break;
            case R.id.tv_chart_type:
                showSelectionsDialog(Section.TYPE);
                break;
        }
    }

    public interface ChartToolbarSelectionCallback {
        void onChartToolbarSelection(Section section, String value);
        String getSelectedToolbarValue(Section section);
    }
}
