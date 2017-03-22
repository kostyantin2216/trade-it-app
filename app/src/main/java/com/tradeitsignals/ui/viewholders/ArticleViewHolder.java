package com.tradeitsignals.ui.viewholders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.ArticleData;

/**
 * Created by ThorneBird on 3/28/2016.
 */
public class ArticleViewHolder extends CommonViewHolder<ArticleData> {
    private TextView tvArticlePubDate;
    private TextView tvArticleDesc;
    private TextView tvTitle;

    public ArticleViewHolder(View view) {
        super(view);
        tvTitle=(TextView) view.findViewById(R.id.tv_title);
        tvArticlePubDate = (TextView) view.findViewById(R.id.tv_article_pubdate);
        tvArticleDesc = (TextView) view.findViewById(R.id.tv_article_des);
    }

    @Override
    public void populateViews(ArticleData item) {
        tvArticlePubDate.setText(item.getPubDate());
        tvArticleDesc.setText(item.getDescription());
        tvTitle.setText(item.getTitle());
    }
}
