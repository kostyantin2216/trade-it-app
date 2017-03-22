package com.tradeitsignals.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tradeitsignals.R;

import org.w3c.dom.Attr;

/**
 * Created by Kostyantin on 5/28/2016.
 */
public class AcceptOrDeclineButtons extends FrameLayout implements View.OnClickListener {

    private AcceptOrDeclineCallback mCallback;

    public AcceptOrDeclineButtons(Context context) {
        super(context);
        init(context, null, null);
    }

    public AcceptOrDeclineButtons(Context context, AcceptOrDeclineCallback callback) {
        super(context);
        this.mCallback = callback;
        init(context, null, null);
    }

    public AcceptOrDeclineButtons(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, null);
    }

    public AcceptOrDeclineButtons(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, Integer defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.component_accept_or_decline, this, true);

        if(!isInEditMode()) {
            findViewById(R.id.btn_accept).setOnClickListener(this);
            findViewById(R.id.btn_decline).setOnClickListener(this);
        }
    }

    public void setCallback(AcceptOrDeclineCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onClick(View v) {
        if(mCallback != null) {
            switch (v.getId()) {
                case R.id.btn_accept:
                    mCallback.onAccept();
                    break;
                case R.id.btn_decline:
                    mCallback.onDecline();
                    break;
            }
        }
    }

    public interface AcceptOrDeclineCallback {
        void onAccept();
        void onDecline();
    }
}
