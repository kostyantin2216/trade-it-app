package com.tradeitsignals.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.tradeitsignals.ui.animations.RecyclerItemAnimation;

/**
 * Created by Kostyantin on 5/30/2016.
 */
public abstract class AnimatedRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected RecyclerItemAnimation itemAnimation;

    private int lastPosition = -1;

    public AnimatedRecyclerAdapter(RecyclerItemAnimation itemAnimation) {
        this.itemAnimation = itemAnimation;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        setAnimation(holder, position);
    }

    public void setAnimation(final VH viewHolder, final int position) {
        if(position > lastPosition) {
            Animation animation = itemAnimation.getAnimation();
            viewHolder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        holder.itemView.clearAnimation();
    }
}
