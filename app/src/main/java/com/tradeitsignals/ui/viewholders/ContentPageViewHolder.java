package com.tradeitsignals.ui.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.ContentPage;

/**
 * Created by Kostyantin on 8/19/2015.
 */
public class ContentPageViewHolder extends CommonViewHolder<ContentPage> {

    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvDescription;

    public ContentPageViewHolder(View view) {
        super(view);
        this.ivIcon = (ImageView) view.findViewById(R.id.iv_content_page_icon);
        this.tvTitle = (TextView) view.findViewById(R.id.tv_content_page_title);
        this.tvDescription = (TextView) view.findViewById(R.id.tv_content_page_description);
    }

    @Override
    public void populateViews(ContentPage page) {
        Context context = itemView.getContext();
        int resId = context.getResources().getIdentifier(page.getAndroidIconResId(), "drawable", context.getPackageName());
        Picasso.with(context).load(resId).config(Bitmap.Config.RGB_565).into(ivIcon);
        ivIcon.setColorFilter(Color.WHITE);
        tvTitle.setText(page.getTitle());
        tvDescription.setText(page.getDescription());
    }
}
