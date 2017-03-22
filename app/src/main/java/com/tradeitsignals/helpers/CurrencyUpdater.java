package com.tradeitsignals.helpers;

import android.content.Context;
import android.os.AsyncTask;

import com.tradeitsignals.datamodel.dao.CurrencyDAO;
import com.tradeitsignals.datamodel.data.Currency;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *  Updates Currency rates from http://themoneyconverter.com/  rss feeds.
 *
 *  Each xml item received from feed:
 *  <item>
 *      <title>AED/USD</title>
 *      <link>http://themoneyconverter.com/USD/AED.aspx</link>
 *      <pubDate>Tue, 21 Jul 2015 15:53:54 GMT</pubDate>
 *      <description>
 *          1 United States Dollar = 3.67250 United Arab Emirates Dirham
 *      </description>
 *      <category>Middle East</category>
 *  </item>
 *
 *
 *  Created by Kostyantin on 7/18/2015.
 *
 */
public class CurrencyUpdater {

    public final static String BASE_URL = "http://themoneyconverter.com/rss-feed/";
    public final static String END_URL = "/rss.xml";

    public final static String TAG_ITEM = "item";
    public final static String TAG_TITLE = "title";
    public final static String TAG_PUB_DATE = "pubDate";
    public final static String TAG_DESCRIPTION = "description";

    public Context mContext;

    public CurrencyUpdater(Context context) {
        this.mContext = context;
    }

    /**
     * First String param contains all iso currency codes(eg. USD) to update.
     * Each param will be used to post a url:
     *      BASE_URL + param + END_URL = http://themoneyconverter.com/rss-feed/USD/rss.xml
     */
    private class CurrencyUpdateTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (String param : params) {
                    URL url = new URL(BASE_URL + param + END_URL);

                    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                    parser.setInput(url.openStream(), null);

                    String[] data = null;

                    boolean item = false;
                    boolean title = false;
                    boolean pubDate = false;
                    boolean description = false;

                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String tagName;

                        if (eventType == XmlPullParser.START_TAG) {
                            tagName = parser.getName();

                            if (tagName.equals(TAG_ITEM)) {
                                item = true;
                                data = new String[4];
                            } else if (item) {
                                if(tagName.equals(TAG_TITLE)) {
                                    title = true;
                                } else if(tagName.equals(TAG_PUB_DATE)) {

                                } else if(tagName.equals(TAG_DESCRIPTION)) {
                                    description = true;
                                }
                            }
                        }

                        if(eventType == XmlPullParser.END_TAG) {
                            tagName = parser.getName();

                            if(tagName.equals(TAG_ITEM)) {
                                item = false;
                                updateCurrency(param, data);
                            } else if(item) {
                                if(tagName.equals(TAG_TITLE)) {
                                    title = false;
                                } else if(tagName.equals(TAG_PUB_DATE)) {
                                    pubDate = false;
                                } else if(tagName.equals(TAG_DESCRIPTION)) {
                                    description = false;
                                }
                            }
                        }

                        if(eventType == XmlPullParser.TEXT) {
                            if(item) {
                                if(title) {
                                    data[0] = parser.getText();
                                } else if(pubDate) {
                                    data[1] = parser.getText();
                                } else if(description) {
                                    data[2] = parser.getText();
                                }
                            }
                        }
                    }
                }

            } catch (XmlPullParserException e) {
                // TODO: print to log.
                e.printStackTrace();
            } catch (MalformedURLException e) {
                // TODO: print to log.
                e.printStackTrace();
            } catch (IOException e) {
                // TODO: print to log.
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * @param isoCode String of Iso code for currency to update.
     * @param data String Array containing all values needed for update.
     *             - data[0] is title.
     *             - data[1] is pubDate.
     *             - data[2] is description.
     */
    private void updateCurrency(String isoCode, String[] data) {
        CurrencyDAO dao = CurrencyDAO.getInstance();
        Currency currency = new Currency();
        currency.setIsoCode("");
    }
}
