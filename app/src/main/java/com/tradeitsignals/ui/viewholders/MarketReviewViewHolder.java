package com.tradeitsignals.ui.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.helpers.ImageManager;
import com.tradeitsignals.utils.DateUtils;

import java.io.File;

/**
 * Created by Kostyantin on 6/30/2015.
 */
public class MarketReviewViewHolder extends CommonViewHolder<MarketReview> {

    public final static int MAX_CONTENT_CHARS = 250;

    private Context context;
    private ImageManager imageManager;

    private ImageView ivReviewImage;
    private TextView tvReviewTitle;
    private TextView tvReviewContent;
    private TextView tvCreatedAt;

    public MarketReviewViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.imageManager = new ImageManager(context);
        ivReviewImage = (ImageView) view.findViewById(R.id.iv_market_review_image);
        tvReviewTitle = (TextView) view.findViewById(R.id.tv_market_review_title);
        tvReviewContent = (TextView) view.findViewById(R.id.tv_market_review_content);
        tvCreatedAt = (TextView) view.findViewById(R.id.tv_market_review_created_at);
    }

    public void populateViews(MarketReview review) {
        Picasso.with(context).load(review.getImageUrl()).config(Bitmap.Config.RGB_565).fit().centerCrop().into(ivReviewImage);

        tvReviewTitle.setText(review.getTitle());

        String contents = review.getContent();
        if(contents.length() > MAX_CONTENT_CHARS) {
            contents = contents.substring(0, MAX_CONTENT_CHARS) + "...";
        }
        tvReviewContent.setText(contents);

        tvCreatedAt.setText(String.format(context.getString(R.string.created_at),
                DateUtils.formatToString(DateUtils.FORMAT_GLOBAL_DATE, review.getCreatedAt())));
    }
}
