package com.tradeitsignals.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.helpers.ImageManager;
import com.tradeitsignals.utils.DateUtils;
import com.tradeitsignals.utils.UIUtils;

import java.io.File;

/**
 * TODO: Make toolbar title show long text.
 * Created by Kostyantin on 7/1/2015.
 */
public class MarketReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_market_review);

        if(getIntent().hasExtra("review")) {
            MarketReview review = getIntent().getParcelableExtra("review");

            Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
            setSupportActionBar(toolbar);

            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(review.getTitle());

            ImageView ivHeader = (ImageView) findViewById(R.id.header);
            Picasso.with(this).load(review.getImageUrl()).config(Bitmap.Config.RGB_565).fit().centerCrop().into(ivHeader);

            TextView tvContent = (TextView) findViewById(R.id.tv_market_review_content);
            tvContent.setText(review.getContent());

            TextView tvCreatedAt = (TextView) findViewById(R.id.tv_market_review_created_at);
            tvCreatedAt.setText(DateUtils.formatToString(DateUtils.FORMAT_GLOBAL_DATE, review.getCreatedAt()));
        } else {
            UIUtils.showError(this, "ERROR - No market review was provided inside of MarketReviewActivity intent");
        }
    }


}
