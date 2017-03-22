package com.tradeitsignals.ui.adapters;

import android.support.v7.widget.RecyclerView;

import com.tradeitsignals.ui.animations.RecyclerItemScaleAnimation;
import com.tradeitsignals.ui.animations.RecyclerItemSlideInAnimation;

import java.util.Random;

/**
 * Created by Kostyantin on 5/30/2016.
 */
public abstract class RandomAnimationRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends AnimatedRecyclerAdapter<VH> {

    public RandomAnimationRecyclerAdapter() {
        super(null);

        int itemCount = getItemCount();
        int seed = itemCount > 0 ? itemCount : 1;
        int nextAnimation = new Random(System.currentTimeMillis() / seed).nextInt(3);
        switch (nextAnimation) {
            case 0:
                itemAnimation = new RecyclerItemScaleAnimation();
                break;
            case 1:
                itemAnimation = new RecyclerItemSlideInAnimation(RecyclerItemSlideInAnimation.Direction.LEFT);
                break;
            case 2:
                itemAnimation = new RecyclerItemSlideInAnimation(RecyclerItemSlideInAnimation.Direction.RIGHT);
                break;
        }
    }
}
