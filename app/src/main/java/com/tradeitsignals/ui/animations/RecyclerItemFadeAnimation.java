package com.tradeitsignals.ui.animations;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.tradeitsignals.helpers.TIConfiguration;

/**
 * Created by Kostyantin on 5/30/2016.
 */
public class RecyclerItemFadeAnimation implements RecyclerItemAnimation {

    private long duration;

    public RecyclerItemFadeAnimation(long duration) {
        this.duration = duration;
    }

    public RecyclerItemFadeAnimation() {
        this.duration = TIConfiguration.getInt("default_recycler_item_animation_time_millis", 1000);
    }

    @Override
    public Animation getAnimation() {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        return anim;
    }
}
