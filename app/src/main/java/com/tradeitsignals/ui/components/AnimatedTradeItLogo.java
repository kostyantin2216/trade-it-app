package com.tradeitsignals.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tradeitsignals.R;

/**
 * Created by Kostyantin on 5/25/2016.
 */
public class AnimatedTradeItLogo extends FrameLayout {

    private TradeItLogoAnimationCallback mCallback;

    private Animation slideInLeft;
    private Animation slideInTop;
    private Animation slideInRight;
    private Animation slideOutLeft;
    private Animation slideOutTop;
    private Animation slideOutRight;

    private ImageView ivLeft;
    private ImageView ivMiddle;
    private ImageView ivRight;

    private boolean welcomeAnimationFinished = false;
    private boolean finishAnimationFinished = false;

    public AnimatedTradeItLogo(Context context) {
        super(context);
        init(context, null, null);
    }

    public AnimatedTradeItLogo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, null);
    }

    public AnimatedTradeItLogo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, Integer defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.component_animated_trade_it_logo, this, true);

        if (!isInEditMode()) {
            ivLeft = (ImageView) findViewById(R.id.iv_splash_1);
            ivMiddle = (ImageView) findViewById(R.id.iv_splash_2);
            ivRight = (ImageView) findViewById(R.id.iv_splash_3);

            initAnimations(context);
        }
    }

    private void initAnimations(Context context) {
        slideInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        slideInTop = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
        slideInRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);

        slideInRight.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                welcomeAnimationFinished = true;
                if(mCallback != null) {
                    mCallback.onWelcomeAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        slideOutLeft = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        slideOutTop = AnimationUtils.loadAnimation(context, R.anim.slide_out_top);
        slideOutRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        // Application will start upon ending of last itemAnimation and make all ImageView's GONE.
        slideOutRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finishAnimationFinished = true;
                if(mCallback != null) {
                    mCallback.onFinishAnimationFinished();
                }
                ivLeft.setVisibility(View.GONE);
                ivMiddle.setVisibility(View.GONE);
                ivRight.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startWelcomeAnimation() {
        ivLeft.startAnimation(slideInLeft);
        ivMiddle.startAnimation(slideInTop);
        ivRight.startAnimation(slideInRight);
    }

    public void startFinishAnimation() {
        ivLeft.startAnimation(slideOutLeft);
        ivMiddle.startAnimation(slideOutTop);
        ivRight.startAnimation(slideOutRight);
    }

    public void setAnimationCallback(TradeItLogoAnimationCallback callback) {
        this.mCallback = callback;
    }

    public boolean isWelcomeAnimationFinished() {
        return welcomeAnimationFinished;
    }

    public boolean isFinishAnimationFinished() {
        return finishAnimationFinished;
    }

    public interface TradeItLogoAnimationCallback {
        void onWelcomeAnimationFinished();
        void onFinishAnimationFinished();
    }
}