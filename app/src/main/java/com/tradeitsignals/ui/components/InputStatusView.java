package com.tradeitsignals.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tradeitsignals.R;

/**
 * Created by Kostyantin on 9/17/2016.
 */
public class InputStatusView extends FrameLayout {

    private final ViewHolder mView;

    private static class ViewHolder {
        public final ImageView status;
        public final ProgressBar loader;

        public ViewHolder(ImageView status, ProgressBar loader) {
            this.status = status;
            this.loader = loader;
        }
    }

    private enum State {
        GONE,
        LOADING,
        FAILURE,
        SUCCESS;
    }

    public InputStatusView(Context context) {
        this(context, null, 0);
    }

    public InputStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mView = new ViewHolder(createStatusView(), createProgressView());
        alter(State.GONE);
    }

    private ImageView createStatusView() {
        ImageView status = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        status.setLayoutParams(lp);

        addView(status);
        return status;
    }

    private ProgressBar createProgressView() {
        ProgressBar loader = new ProgressBar(getContext());
        loader.setIndeterminateDrawable(getResources().getDrawable(R.drawable.loader_blue_small));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        loader.setLayoutParams(lp);

        addView(loader);
        return loader;
    }

    private void alter(State state) {
        switch (state) {
            case GONE:
                setVisibility(GONE);
                break;

            case LOADING:
                setVisibility(VISIBLE);
                mView.loader.setVisibility(VISIBLE);
                mView.status.setVisibility(View.INVISIBLE);
                break;

            case FAILURE:
                setVisibility(VISIBLE);
                mView.loader.setVisibility(INVISIBLE);
                mView.status.setImageResource(R.drawable.ic_red_x);
                mView.status.setVisibility(VISIBLE);
                break;

            case SUCCESS:
                setVisibility(VISIBLE);
                mView.loader.setVisibility(INVISIBLE);
                mView.status.setImageResource(R.drawable.ic_green_tick);
                mView.status.setVisibility(VISIBLE);
                break;
        }
    }

    public void hide() {
        alter(State.GONE);
    }

    public void loading() {
        alter(State.LOADING);
    }

    public void success() {
        alter(State.SUCCESS);
    }

    public void failure() {
        alter(State.FAILURE);
    }
}
