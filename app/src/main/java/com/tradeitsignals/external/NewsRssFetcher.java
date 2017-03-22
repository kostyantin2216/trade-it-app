package com.tradeitsignals.external;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import com.tradeitsignals.datamodel.data.ArticleData;
import com.tradeitsignals.logging.TILogger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ThorneBird on 3/28/2016.
 */

public class NewsRssFetcher extends AsyncTask<Void, Void, List<ArticleData>> {

    public final static String LOG_TAG = "#NEWS";

    private String rssUrl;
    private NewsFetcherCallback mCallbacks;

    public NewsRssFetcher(String rssUrl, NewsFetcherCallback newsFetcherCallback) {
        this.rssUrl = rssUrl;
        this.mCallbacks = newsFetcherCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mCallbacks.onNewsFetchStarted();
    }

    @Override
    protected List<ArticleData> doInBackground(Void... params) {
        List<ArticleData> loadedArticles = articlesDailyFx();
        return loadedArticles;
    }

    @Override
    protected void onPostExecute(List<ArticleData> list) {
        super.onPostExecute(list);

        mCallbacks.onNewsFetched(list);
    }

    private ArrayList<ArticleData> articlesDailyFx() {
        ArrayList<ArticleData> loadedArticles = new ArrayList<>();
        String tagName = "";
        try {

            URL url = new URL(rssUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(url.openConnection().getInputStream(), null);
            int eventType = xpp.getEventType();

            ArticleData articleData = new ArticleData();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();
                    if (tagName.equalsIgnoreCase("item")) {
                        if (articleData.getLink() != null &&
                                articleData.getPubDate() != null) {
                            loadedArticles.add(articleData);
                            articleData = new ArticleData();
                        }
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (tagName.equalsIgnoreCase("title")) {
                        String title = Html.fromHtml(xpp.getText()).toString();
                        articleData.setTitle(title);
                    } else if (tagName.equalsIgnoreCase("link") && !xpp.isWhitespace()) {
                        articleData.setLink(xpp.getText());
                    }  else if (tagName.equalsIgnoreCase("pubDate") && !xpp.isWhitespace()) {
                        articleData.setPubDate(xpp.getText());
                    } else if (tagName.equalsIgnoreCase("description") && !xpp.isWhitespace()) {
                        String des = Html.fromHtml(xpp.getText()).toString();
                        articleData.setDescription(des);
                    }
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            TILogger.getLog().e(LOG_TAG, "Could not parse xml from url: " + rssUrl, e);
        } catch (MalformedURLException e) {
            TILogger.getLog().e(LOG_TAG, "Error while trying to convert string(" + rssUrl + ") to new URL()", e);
        } catch (IOException e) {
            TILogger.getLog().e(LOG_TAG, "Errors during parsing from url: " + rssUrl, e);
        }
        return loadedArticles;
    }

    public interface NewsFetcherCallback {
        void onNewsFetchStarted();
        void onNewsFetched(List<ArticleData> articleData);
    }
}
