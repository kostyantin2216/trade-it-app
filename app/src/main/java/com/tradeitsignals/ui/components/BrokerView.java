package com.tradeitsignals.ui.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.helpers.ImageManager;

/**
 * Created by Kostyantin on 6/14/2015.
 */
public class BrokerView extends LinearLayout {

    private ImageManager imageManager;
    private OnBrokerSelectionListener onBrokerSelectionListener;

    private TextView tvName;
    private TextView tvMinDep;
    private TextView tvMinWithdraw;
    private TextView tvDesc;
    private ImageView ivLogo;
    private RatingBar rbRating;
    private Button btnRegister;

    public BrokerView(Context context) {
        super(context);
        this.imageManager = new ImageManager(context);
    }

    public BrokerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.imageManager = new ImageManager(context);
    }

    public BrokerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.imageManager = new ImageManager(context);
    }

    public void setOnBrokerSelectionListener(OnBrokerSelectionListener listener) {
        this.onBrokerSelectionListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void showBroker(final Broker broker) {
        tvName = (TextView) findViewById(R.id.tv_broker_name);
    //    Typeface robotoFont = Typeface.createFromAsset(context.getAssets(), "font/Roboto-Regular.ttf");
    //    tvName.setTypeface(robotoFont);
        tvName.setText(broker.getName());

        ivLogo = (ImageView) findViewById(R.id.iv_broker_logo);
        Picasso.with(getContext()).load(broker.getLogoUrl()).config(Bitmap.Config.RGB_565).fit().into(ivLogo);

        tvMinDep = (TextView) findViewById(R.id.tv_broker_min_deposit_amount);
        tvMinDep.setText("$" + broker.getMinDeposit());

        tvMinWithdraw = (TextView) findViewById(R.id.tv_broker_bonus);
        tvMinWithdraw.setText("$" + broker.getMinWithdrawal());

        rbRating = (RatingBar) findViewById(R.id.rb_broker_rating);
        rbRating.setRating(broker.getRating());

        tvDesc = (TextView) findViewById(R.id.tv_broker_description);
        tvDesc.setText(broker.getDescription());

        btnRegister = (Button) findViewById(R.id.b_broker_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onBrokerSelectionListener != null) {
                    onBrokerSelectionListener.onBrokerSelected(broker);
                } else {
                    throw new RuntimeException("Must set OnBrokerSelectionListener on BrokerView");
                }
            }
        });

    }

    public interface OnBrokerSelectionListener {
        void onBrokerSelected(Broker broker);
    }
}
