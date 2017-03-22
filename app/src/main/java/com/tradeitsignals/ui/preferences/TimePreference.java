package com.tradeitsignals.ui.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tradeitsignals.R;
import com.tradeitsignals.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class TimePreference extends DialogPreference {

    private final Calendar calendar;
    private TimePicker picker;
    private TextView tvValue;

    public TimePreference(Context ctxt) {
        this(ctxt, null);
    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        this(ctxt, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public TimePreference(Context ctxt, AttributeSet attrs, int defStyle) {
        super(ctxt, attrs, defStyle);

        setPositiveButtonText(R.string.set);
        setNegativeButtonText(R.string.cancel);
        calendar = new GregorianCalendar();
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        return picker;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        if (Build.VERSION.SDK_INT >= 23) {
            picker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            picker.setMinute(calendar.get(Calendar.MINUTE));
        } else {
            picker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            picker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            if (Build.VERSION.SDK_INT >= 23) {
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, picker.getCurrentHour());
                calendar.set(Calendar.MINUTE, picker.getCurrentMinute());
            }

            setSummary(getSummary());
            long calendarTime = TimeUnit.HOURS.toMillis(calendar.get(Calendar.HOUR_OF_DAY))
                    + TimeUnit.MINUTES.toMillis(calendar.get(Calendar.MINUTE));

            if (callChangeListener(calendarTime)) {
                persistLong(calendarTime);
                tvValue.setText(formatToString(calendarTime));
                notifyChanged();
            }
        }
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        long value;
        if (defaultValue == null) {
            value = TimeUnit.HOURS.toMillis(12);
        } else {
            value = (long) defaultValue;
        }

        if(restoreValue) {
            value = getPersistedLong(value);
        } else if(shouldPersist()) {
            persistLong(value);
        }

        resetCalendar();
        calendar.add(Calendar.MILLISECOND, (int) value);

        setSummary(getSummary());
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        tvValue = (TextView) view.findViewById(R.id.tv_time_restriction);
        tvValue.setText(formatToString(calendar.getTimeInMillis()));
    }

    private void resetCalendar() {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
    }

    private String formatToString(long time) {
        return DateUtils.formatToString(DateUtils.FORMAT_PREFERENCES_TIME, time);
    }
    
}