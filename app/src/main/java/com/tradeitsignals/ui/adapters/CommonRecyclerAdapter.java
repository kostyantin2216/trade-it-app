package com.tradeitsignals.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.tradeitsignals.ui.animations.RecyclerItemAnimation;
import com.tradeitsignals.ui.animations.RecyclerItemScaleAnimation;
import com.tradeitsignals.ui.viewholders.ArticleViewHolder;
import com.tradeitsignals.ui.viewholders.AssetViewHolder;
import com.tradeitsignals.ui.viewholders.BrokerViewHolder;
import com.tradeitsignals.ui.viewholders.ContentPageViewHolder;
import com.tradeitsignals.ui.viewholders.MarketReviewViewHolder;
import com.tradeitsignals.ui.viewholders.CommonViewHolder;

import java.util.List;

/**
 * Created by Kostyantin on 6/27/2015.
 */
public class CommonRecyclerAdapter<E extends CommonRecyclerItem> extends RandomAnimationRecyclerAdapter<CommonViewHolder>  {

    private final static RecyclerItemAnimation DEFAULT_ITEM_ANIMATION = new RecyclerItemScaleAnimation();

    public final static int TYPE_MARKET_REVIEWS = 0;
    public final static int TYPE_BROKERS = 1;
    public final static int TYPE_CONTENT_PAGE = 3;
    public final static int TYPE_CHART_LIST = 4;
    public final static int TYPE_ARTICLE_LIST = 5;

    private List<E> items;
    private int rowLayout;
    private int type;

    public CommonRecyclerAdapter(int rowLayout, List<E> items, int type) {
        super();
        this.items = items;
        this.rowLayout = rowLayout;
        this.type = type;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        switch (type) {
            case TYPE_MARKET_REVIEWS:
                return new MarketReviewViewHolder(parent.getContext(), v);
            case TYPE_BROKERS:
                return new BrokerViewHolder(parent.getContext(), v);
            case TYPE_CONTENT_PAGE:
                return new ContentPageViewHolder(v);
            case TYPE_CHART_LIST:
                return new AssetViewHolder(parent.getContext(), v);
            case TYPE_ARTICLE_LIST:
                return new ArticleViewHolder(v);
            default:
                throw new IllegalArgumentException("CANNOT START: CommonRecyclerAdapter - type (" + type + ") does not exist");
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        E item = items.get(position);

        holder.populateViews(item);

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return (items == null ? 0 : items.size());
    }

    public void swapItems(List<E> items) {
        if(items != null) {
            this.items.clear();
            this.items.addAll(items);
        } else {
            this.items = items;
        }
        notifyDataSetChanged();
    }

    public E removeItem(int position) {
        E item = items.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    public void addItem(int position, E item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to) {
        E item = items.remove(from);
        items.add(to, item);
        notifyItemMoved(from, to);
    }

    public void animateTo(List<E> items) {
        applyRemovals(items);
        applyAdditions(items);
        applyMovedItems(items);
    }

    private void applyRemovals(List<E> newItems) {
        for(int i = items.size() - 1; i >= 0; i--) {
            E item = items.get(i);
            if(!newItems.contains(item)) {
                removeItem(i);
            }
        }
    }

    private void applyAdditions(List<E> newItems) {
        int count = newItems.size();
        for(int i = 0; i < count; i++) {
            E item = newItems.get(i);
            if(!items.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void applyMovedItems(List<E> newItems) {
        for(int to = newItems.size() - 1; to >= 0; to--) {
            E item = newItems.get(to);
            int from = items.indexOf(item);
            if(from >= 0 && from != to) {
                moveItem(from, to);
            }
        }
    }

}


