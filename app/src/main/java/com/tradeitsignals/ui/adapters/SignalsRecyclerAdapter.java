package com.tradeitsignals.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.ui.viewholders.CommonViewHolder;
import com.tradeitsignals.utils.DateUtils;
import com.tradeitsignals.utils.UIUtils;

import java.util.List;

/**
 * Created by Kostyantin on 6/3/2016.
 */
public class SignalsRecyclerAdapter extends RandomAnimationRecyclerAdapter<SignalsRecyclerAdapter.SignalViewHolder> {

    private final OnSignalInteractionListener mListener;
    private final List<Signal> signals;

    public SignalsRecyclerAdapter(List<Signal> signals, OnSignalInteractionListener listener) {
        this.signals = signals;
        this.mListener = listener;
    }

    @Override
    public SignalViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_signal, parent, false);

        Signal signal = signals.get(position);

        return new SignalViewHolder(signal.isCall(), itemView);
    }

    @Override
    public void onBindViewHolder(SignalViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        final Signal signal = signals.get(position);

        holder.populateViews(signal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSignalSelected(signal);
                }
            }
        });
        holder.animateCallOrPutArrow();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return signals != null ? signals.size() : 0;
    }

    @Override
    public void onViewDetachedFromWindow(SignalViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.ivCallOrPut.clearAnimation();
    }

    public void swapItems(List<Signal> signals) {
        this.signals.clear();
        this.signals.addAll(signals);

        notifyDataSetChanged();
    }

    public interface OnSignalInteractionListener {
        void onSignalSelected(Signal signal);
    }

    public class SignalViewHolder extends CommonViewHolder<Signal> {

        private final static long CALL_ARROW_ANIM_DURATION_MILLIS = 1800;

        private TextView tvCurrencyPair;
        private TextView tvCreatedAt;
        private TextView tvExpiry;
        private TextView tvRate;
        private ImageView ivAsset;
        protected ImageView ivCallOrPut;

        private Animation ivCallOrPutAnim;

        public SignalViewHolder(boolean isCall, View view) {
            super(view);
            tvCurrencyPair = (TextView) view.findViewById(R.id.tv_currency_pair);
            tvCreatedAt = (TextView) view.findViewById(R.id.tv_date_created);
            tvExpiry = (TextView) view.findViewById(R.id.tv_time_left);
            tvRate = (TextView) view.findViewById(R.id.tv_rate);
            ivAsset = (ImageView) view.findViewById(R.id.iv_asset);
            ivCallOrPut = (ImageView) view.findViewById(R.id.iv_callorput);

            ivCallOrPutAnim = createCallOrPutArrowAnimation(isCall);
        }

        private Animation createCallOrPutArrowAnimation(boolean isCall) {
            float fromY = isCall ? 1F : -1F;
            Animation translate = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0F,
                    Animation.RELATIVE_TO_SELF, 0F,
                    Animation.RELATIVE_TO_SELF, fromY,
                    Animation.RELATIVE_TO_SELF, 0F
            );
            translate.setDuration(CALL_ARROW_ANIM_DURATION_MILLIS);

            Animation alpha = new AlphaAnimation(0F, 1F);
            alpha.setDuration(CALL_ARROW_ANIM_DURATION_MILLIS * 2);

            AnimationSet animSet = new AnimationSet(true);
            animSet.addAnimation(translate);
            animSet.addAnimation(alpha);
            animSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    ivCallOrPut.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            return animSet;
        }

        @Override
        public void populateViews(final Signal signal) {
            Context context = itemView.getContext();
            ivCallOrPut.setVisibility(View.INVISIBLE);

            tvCurrencyPair.setText(signal.getAsset());

            tvCreatedAt.setText(String.format(context.getString(R.string.created_at),
                    DateUtils.formatToString(DateUtils.FORMAT_GLOBAL_DATE, signal.getCreatedAt())));

            if(!signal.isExpired()) {
                tvExpiry.setText(String.format(context.getString(R.string.expires_in), signal.getMinutesLeft()));
            } else {
                tvExpiry.setText(context.getString(R.string.expired));
            }

            String formatString = context.getString((signal.isCall() ? R.string.or_lower : R.string.or_higher));
            tvRate.setText(String.format(formatString, signal.getEntryRate()));

            String asset = signal.getAsset();
            if(asset != null) {
                Integer flagId = getFlagId(signal.getAsset());
                if(flagId != null) {
                    ivAsset.setImageDrawable(context.getResources().getDrawable(flagId));
                }
            }

            ivCallOrPut.setImageDrawable(context.getResources().getDrawable((signal.isCall() ? R.drawable.ic_call : R.drawable.ic_put)));
        }

        private Integer getFlagId(String key) {
            return UIUtils.CURRENCY_PAIR_FLAGS_MAP.get(key);
        }

        private void animateCallOrPutArrow() {
            ivCallOrPut.startAnimation(ivCallOrPutAnim);
        }
    }
}
