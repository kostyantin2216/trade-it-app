package com.tradeitsignals.ui.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.enums.charts.ChartType;
import com.tradeitsignals.datamodel.enums.charts.ChartIndicator;
import com.tradeitsignals.datamodel.enums.charts.ChartInterval;

/**
 * Created by ThorneBird on 2/24/2016.
 */
public class MenuDialogFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {

    private final static String MENU_TYPE = "menuType";
    private final static String MENU_TITLE = "menuTitle";
    private final static String MENU_ITEM_CHECK = "itemCheck";
    private MenuInterface menuCallback;
    private RadioGroup radioGroup;
    public final static int MENU_INTERVAL = 0;
    public final static int MENU_INDICATOR = 1;
    public final static int MENU_CHART_TYPE = 2;
    private int menuType;
    private String title;
    private String checkItem;


    public MenuDialogFragment() {
    }

    public interface MenuInterface {
        public void intervalSelected(String interval);

        public void indicatorSelected(String indicator);

        public void chartTypeSelected(String chart);
    }


    public static MenuDialogFragment newInstance(int menuType, String title, String checkItem) {
        MenuDialogFragment menuDialogFragment = new MenuDialogFragment();
        Bundle args = new Bundle();
        args.putInt(MENU_TYPE, menuType);
        args.putString(MENU_TITLE, title);
        args.putString(MENU_ITEM_CHECK, checkItem);
        menuDialogFragment.setArguments(args);
        return menuDialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle bundle = getArguments();
        if (bundle != null) {
            menuType = bundle.getInt(MENU_TYPE);
            title = bundle.getString(MENU_TITLE);
            checkItem = bundle.getString(MENU_ITEM_CHECK);
            Log.i("LOG", " checkitem: " + checkItem);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            menuCallback = (MenuInterface) getActivity();
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = null;

        if (menuType == MENU_INTERVAL) {

            rootView = inflater.inflate(R.layout.fragment_menu_interval, null, false);
            getDialog().setTitle(title);
            radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_options);
            radioGroup.setOnCheckedChangeListener(this);

            RadioButton rbOne = (RadioButton) rootView.findViewById(R.id.rg_one);
            RadioButton rbThree = (RadioButton) rootView.findViewById(R.id.rg_three);
            RadioButton rbFive = (RadioButton) rootView.findViewById(R.id.rg_five);
            RadioButton rbFifteen = (RadioButton) rootView.findViewById(R.id.rg_fifteen);
            RadioButton rbThirty = (RadioButton) rootView.findViewById(R.id.rg_thirty);
            RadioButton rbHour = (RadioButton) rootView.findViewById(R.id.rg_hour);
            RadioButton rbTwoHour = (RadioButton) rootView.findViewById(R.id.rg_two_hour);
            RadioButton rbThreeHour = (RadioButton) rootView.findViewById(R.id.rg_three_hour);
            RadioButton rbFourHour = (RadioButton) rootView.findViewById(R.id.rg_four_hour);
            RadioButton rbDaily = (RadioButton) rootView.findViewById(R.id.rg_daily);
            RadioButton rbWeekly = (RadioButton) rootView.findViewById(R.id.rg_week);

            if (checkItem.equals(ChartInterval.ONE_MINUTE.getIntervalCode())) {
                setItemChecked(rbOne);
                //rbOne.setChecked(true);
            } else if (checkItem.equals(ChartInterval.THREE_MINUTES.getIntervalCode())) {
                setItemChecked(rbThree);
                //  rbThree.setChecked(true);
            } else if (checkItem.equals(ChartInterval.FIVE_MINUTES.getIntervalCode())) {
                // rbFive.setChecked(true);
                setItemChecked(rbFive);
            } else if (checkItem.equals(ChartInterval.FIFTEEN_MINUTES.getIntervalCode())) {
                //rbFifteen.setChecked(true);
                setItemChecked(rbFifteen);
            } else if (checkItem.equals(ChartInterval.THIRTY_MINUTES.getIntervalCode())) {
                //  rbThirty.setChecked(true);
                setItemChecked(rbThirty);
            } else if (checkItem.equals(ChartInterval.ONE_HOUR.getIntervalCode())) {
                //rbHour.setChecked(true);
                setItemChecked(rbHour);
            } else if (checkItem.equals(ChartInterval.TWO_HOURS.getIntervalCode())) {
                //rbTwoHour.setChecked(true);
                setItemChecked(rbTwoHour);
            } else if (checkItem.equals(ChartInterval.THREE_HOURS.getIntervalCode())) {
                //    rbThreeHour.setChecked(true);
                setItemChecked(rbThreeHour);
            } else if (checkItem.equals(ChartInterval.FOUR_HOURS.getIntervalCode())) {
                //  rbFourHour.setChecked(true);
                setItemChecked(rbFourHour);
            } else if (checkItem.equals(ChartInterval.DAILY.getIntervalCode())) {
                //rbDaily.setChecked(true);
                setItemChecked(rbDaily);
            } else if (checkItem.equals(ChartInterval.WEEKLY.getIntervalCode())) {
                // rbWeekly.setChecked(true);
                setItemChecked(rbWeekly);
            }


        } else if (menuType == MENU_CHART_TYPE) {


            rootView = inflater.inflate(R.layout.fragment_menu_chart_type, null, false);
            getDialog().setTitle(title);
            radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_options);
            radioGroup.setOnCheckedChangeListener(this);

            RadioButton rbCandles = (RadioButton) rootView.findViewById(R.id.rg_candles);
            RadioButton rbArea = (RadioButton) rootView.findViewById(R.id.rg_area);
            RadioButton rbBars = (RadioButton) rootView.findViewById(R.id.rg_bars);
            RadioButton rbLine = (RadioButton) rootView.findViewById(R.id.rg_line);
            RadioButton rbHollow = (RadioButton) rootView.findViewById(R.id.rg_hollow);
            RadioButton rbHeikinAshi = (RadioButton) rootView.findViewById(R.id.rg_heikin_ashi);

            if (checkItem.equals(ChartType.CANDLE.getChartTypeCode())) {
                setItemChecked(rbCandles);
            } else if (checkItem.equals(ChartType.AREA.getChartTypeCode())) {
                setItemChecked(rbArea);
            } else if (checkItem.equals(ChartType.BARS.getChartTypeCode())) {
                setItemChecked(rbBars);
            } else if (checkItem.equals(ChartType.LINE.getChartTypeCode())) {
                setItemChecked(rbLine);
            } else if (checkItem.equals(ChartType.HOLLOW.getChartTypeCode())) {
                setItemChecked(rbHollow);
            } else if (checkItem.equals(ChartType.HEIKIN_ASHI.getChartTypeCode())) {
                setItemChecked(rbHeikinAshi);
            }


        } else if (menuType == MENU_INDICATOR) {


            rootView = inflater.inflate(R.layout.fragment_menu_indicator, null, false);
            getDialog().setTitle(title);
            radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_options);
            radioGroup.setOnCheckedChangeListener(this);

            RadioButton rbBollingerBands = (RadioButton) rootView.findViewById(R.id.rg_bollinger_bands);
            RadioButton rbStochastic = (RadioButton) rootView.findViewById(R.id.rg_stochastic);
            RadioButton rbMacd = (RadioButton) rootView.findViewById(R.id.rg_macd);
            RadioButton rbElliottWave = (RadioButton) rootView.findViewById(R.id.rg_elliott_wave);
            RadioButton rbRsi = (RadioButton) rootView.findViewById(R.id.rg_rsi);
            RadioButton rbMovingAverages = (RadioButton) rootView.findViewById(R.id.rg_moving_averages);
            RadioButton rbPivotPoints = (RadioButton) rootView.findViewById(R.id.rg_pivot_points);
            RadioButton rbAccumulation = (RadioButton) rootView.findViewById(R.id.rg_accumulation);
            RadioButton rbMoneyFlow = (RadioButton) rootView.findViewById(R.id.rg_money_flow);
            RadioButton rbVolume = (RadioButton) rootView.findViewById(R.id.rg_volume);

            if (checkItem.equals(ChartIndicator.BOLLINGERBANDS.getIndicatorCode())) {
                setItemChecked(rbBollingerBands);
            } else if (checkItem.equals(ChartIndicator.STOCHASTIC.getIndicatorCode())) {
                setItemChecked(rbStochastic);
            } else if (checkItem.equals(ChartIndicator.MACD.getIndicatorCode())) {
                setItemChecked(rbMacd);
            } else if (checkItem.equals(ChartIndicator.ELLIOTTWAVE.getIndicatorCode())) {
                setItemChecked(rbElliottWave);
            } else if (checkItem.equals(ChartIndicator.RSI.getIndicatorCode())) {
                setItemChecked(rbRsi);
            } else if (checkItem.equals(ChartIndicator.MOVING_AVERAGES.getIndicatorCode())) {
                setItemChecked(rbMovingAverages);
            } else if (checkItem.equals(ChartIndicator.PIVOT_POINTS_HIGH_LOW.getIndicatorCode())) {
                setItemChecked(rbPivotPoints);
            } else if (checkItem.equals(ChartIndicator.ACCUMULATION_DISCTIRUBTION.getIndicatorCode())) {
                setItemChecked(rbAccumulation);
            } else if (checkItem.equals(ChartIndicator.MONEY_FLOW.getIndicatorCode())) {
                setItemChecked(rbMoneyFlow);
            } else if (checkItem.equals(ChartIndicator.VOLUME.getIndicatorCode())) {
                setItemChecked(rbVolume);
            }
        }

        return rootView;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (menuType == MENU_INTERVAL) {
            switch (checkedId) {
                case R.id.rg_one:
                    if (!checkItem.equals(ChartInterval.ONE_MINUTE.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.ONE_MINUTE.getIntervalCode());
                    break;
                case R.id.rg_three:
                    if (!checkItem.equals(ChartInterval.THREE_MINUTES.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.THREE_MINUTES.getIntervalCode());
                    break;
                case R.id.rg_five:
                    if (!checkItem.equals(ChartInterval.FIVE_MINUTES.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.FIVE_MINUTES.getIntervalCode());
                    break;
                case R.id.rg_fifteen:
                    if (!checkItem.equals(ChartInterval.FIFTEEN_MINUTES.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.FIFTEEN_MINUTES.getIntervalCode());
                    break;
                case R.id.rg_thirty:
                    if (!checkItem.equals(ChartInterval.THIRTY_MINUTES.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.THIRTY_MINUTES.getIntervalCode());
                    break;
                case R.id.rg_hour:
                    if (!checkItem.equals(ChartInterval.ONE_HOUR.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.ONE_HOUR.getIntervalCode());
                    break;
                case R.id.rg_two_hour:
                    if (!checkItem.equals(ChartInterval.TWO_HOURS.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.TWO_HOURS.getIntervalCode());
                    break;
                case R.id.rg_three_hour:
                    if (!checkItem.equals(ChartInterval.THREE_HOURS.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.THREE_HOURS.getIntervalCode());
                    break;
                case R.id.rg_four_hour:
                    if (!checkItem.equals(ChartInterval.FOUR_HOURS.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.FOUR_HOURS.getIntervalCode());
                    break;
                case R.id.rg_daily:
                    if (!checkItem.equals(ChartInterval.DAILY.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.DAILY.getIntervalCode());
                    break;
                case R.id.rg_week:
                    if (!checkItem.equals(ChartInterval.WEEKLY.getIntervalCode()))
                        menuCallback.intervalSelected(ChartInterval.WEEKLY.getIntervalCode());
                    break;
            }
        } else if (menuType == MENU_INDICATOR) {
            switch (checkedId) {
                case R.id.rg_bollinger_bands:
                    if (!checkItem.equals(ChartIndicator.BOLLINGERBANDS.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.BOLLINGERBANDS.getIndicatorCode());
                    break;
                case R.id.rg_stochastic:
                    if (!checkItem.equals(ChartIndicator.STOCHASTIC.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.STOCHASTIC.getIndicatorCode());
                    break;
                case R.id.rg_macd:
                    if (!checkItem.equals(ChartIndicator.MACD.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.MACD.getIndicatorCode());
                    break;
                case R.id.rg_elliott_wave:
                    if (!checkItem.equals(ChartIndicator.ELLIOTTWAVE.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.ELLIOTTWAVE.getIndicatorCode());
                    break;
                case R.id.rg_rsi:
                    if (!checkItem.equals(ChartIndicator.RSI.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.RSI.getIndicatorCode());
                    break;
                case R.id.rg_moving_averages:
                    if (!checkItem.equals(ChartIndicator.MOVING_AVERAGES.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.MOVING_AVERAGES.getIndicatorCode());
                    break;
                case R.id.rg_pivot_points:
                    if (!checkItem.equals(ChartIndicator.PIVOT_POINTS_HIGH_LOW.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.PIVOT_POINTS_HIGH_LOW.getIndicatorCode());
                    break;
                case R.id.rg_accumulation:
                    if (!checkItem.equals(ChartIndicator.ACCUMULATION_DISCTIRUBTION.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.ACCUMULATION_DISCTIRUBTION.getIndicatorCode());
                    break;
                case R.id.rg_money_flow:
                    if (!checkItem.equals(ChartIndicator.MONEY_FLOW.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.MONEY_FLOW.getIndicatorCode());
                    break;
                case R.id.rg_volume:
                    if (!checkItem.equals(ChartIndicator.VOLUME.getIndicatorCode()))
                        menuCallback.indicatorSelected(ChartIndicator.VOLUME.getIndicatorCode());
                    break;
            }
        } else if (menuType == MENU_CHART_TYPE) {
            switch (checkedId) {
                case R.id.rg_candles:
                    if (!checkItem.equals(ChartType.CANDLE.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.CANDLE.getChartTypeCode());
                    break;
                case R.id.rg_area:
                    if (!checkItem.equals(ChartType.AREA.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.AREA.getChartTypeCode());
                    break;
                case R.id.rg_bars:
                    if (!checkItem.equals(ChartType.BARS.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.BARS.getChartTypeCode());
                    break;
                case R.id.rg_line:
                    if (!checkItem.equals(ChartType.LINE.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.LINE.getChartTypeCode());
                    break;
                case R.id.rg_hollow:
                    if (!checkItem.equals(ChartType.HOLLOW.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.HOLLOW.getChartTypeCode());
                    break;
                case R.id.rg_heikin_ashi:
                    if (!checkItem.equals(ChartType.HEIKIN_ASHI.getChartTypeCode()))
                        menuCallback.chartTypeSelected(ChartType.HEIKIN_ASHI.getChartTypeCode());

            }
        }
    }

    private void setItemChecked(RadioButton radioButton) {

        radioButton.setChecked(true);
    }


}

