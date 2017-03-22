package com.tradeitsignals.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kostyantin on 6/30/2015.
 */
public abstract class CommonViewHolder<E extends Object> extends RecyclerView.ViewHolder {

    public CommonViewHolder(View view) {
        super(view);
    }

    public abstract void populateViews(E item);
}
