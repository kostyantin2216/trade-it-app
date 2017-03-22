package com.tradeitsignals.ui.navdrawer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.utils.UIUtils;

import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private View mSelectedView;
    private int mSelectedPosition;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        mData = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.itemView.setClickable(true);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       if (mSelectedView != null) {
                                                           viewHolder.setSelected(false);
                                                       }
                                                       mSelectedPosition = viewHolder.getAdapterPosition();
                                                       viewHolder.setSelected(true);
                                                       mSelectedView = v;
                                                       if (mNavigationDrawerCallbacks != null)
                                                           mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(viewHolder.getAdapterPosition());
                                                   }
                                               }
        );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.populate(mData.get(i));

        if (mSelectedPosition == i) {
            if (mSelectedView != null) {
                viewHolder.setSelected(false);
            }
            mSelectedPosition = i;
            mSelectedView = viewHolder.itemView;
            viewHolder.setSelected(true);
        }

    }

    public void selectPosition(int position) {
        mSelectedPosition = position;
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
            icon = (ImageView) itemView.findViewById(R.id.iv_nav_item_icon);

            itemView.setBackgroundResource(R.drawable.navigation_row_selector);
        }

        public void populate(NavigationItem item) {
            textView.setText(item.getText());
            icon.setImageDrawable(item.getDrawable());
            icon.setColorFilter(itemView.getResources().getColor(R.color.myTextSecondaryColor));
        }

        public void setSelected(boolean isSelected) {
            int color;
            int iconColor;
            if(isSelected) {
                color = itemView.getResources().getColor(R.color.myPrimaryDarkColor);
                iconColor = color;
            } else {
                color = itemView.getResources().getColor(R.color.myTextPrimaryColor);
                iconColor = itemView.getResources().getColor(R.color.myTextSecondaryColor);
            }
            textView.setTextColor(color);
            icon.setColorFilter(iconColor);
            itemView.setSelected(isSelected);
        }
    }
}