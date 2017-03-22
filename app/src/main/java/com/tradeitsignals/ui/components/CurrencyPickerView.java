package com.tradeitsignals.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.dao.CurrencyDAO;
import com.tradeitsignals.datamodel.data.Currency;
import com.tradeitsignals.utils.CurrencyUtils;

/**
 * Created by Kostyantin on 7/25/2015.
 */
public class CurrencyPickerView extends RelativeLayout {

    public final static String DEFAULT_CURRENCY = "USD";

    private OnCurrencySelectionListener mCallbacks;

    private ImageView ivFlag;
    private TextView tvIso;
    private AutoCompleteTextView acPicker;

    private Currency currency;

    public CurrencyPickerView(Context context) {
        super(context);
        init();
    }

    public CurrencyPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurrencyPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initViews();
        if(this.currency == null) {
            setCurrency(CurrencyDAO.getInstance().findByIsoCode(DEFAULT_CURRENCY));
        } else {
            updateViews();
        }
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_currency_picker, this);

        this.ivFlag = (ImageView) findViewById(R.id.iv_currency_flag);
        this.tvIso = (TextView) findViewById(R.id.tv_currency_iso);
        this.acPicker = (AutoCompleteTextView) findViewById(R.id.ac_currency_picker);

        ArrayAdapter<String> adapter = (new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, CurrencyUtils.CURRENCIES_USED));
        this.acPicker.setAdapter(adapter);
        this.acPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCurrencyIso = acPicker.getAdapter().getItem(position).toString();
                setCurrency(CurrencyDAO.getInstance().findByIsoCode(selectedCurrencyIso));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateViews() {
        this.ivFlag.setImageResource(this.currency.getFlagResId());
        this.tvIso.setText(this.currency.getIsoCode());
        this.acPicker.setText(this.currency.getIsoCode());
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        updateViews();
        mCallbacks.onCurrencySelected(currency);
    }

    public void setOnCurrencySelectionListener(OnCurrencySelectionListener listener) {
        this.mCallbacks = listener;
    }

    public interface OnCurrencySelectionListener {
        void onCurrencySelected(Currency selectedCurrency);
    }

}
