package com.tradeitsignals.ui.animations;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.tradeitsignals.helpers.TIConfiguration;

/**
 * Created by Kostyantin on 5/30/2016.
 */
public class RecyclerItemScaleAnimation implements RecyclerItemAnimation {

    private long duration;

    public RecyclerItemScaleAnimation(long duration) {
        this.duration = duration;
    }

    public RecyclerItemScaleAnimation() {
        duration = TIConfiguration.getInt("default_recycler_item_animation_time_millis", 1000);
    }

    @Override
    public Animation getAnimation() {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(duration);
        return anim;
    }
}
