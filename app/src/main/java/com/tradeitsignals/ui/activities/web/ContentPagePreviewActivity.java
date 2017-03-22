package com.tradeitsignals.ui.activities.web;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tradeitsignals.datamodel.dao.ContentPageDAO;
import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.Constants;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kostyantin on 9/15/2015.
 *
 * This class extends WebViewActivity in order to manipulate its loadFile() method and replace the
 * next and previous links in the html with file names.
 *
 * Its intent must contain the following variables:
 *  file_name: the name of the html file.
 *  next_page: the name of the html for the 'next' anchor element.
 *  previous_page: the name of the html for the 'previous' anchor element.
 *
 */
public class ContentPagePreviewActivity extends BaseWebViewActivity {

    private List<ContentPage> pages;
    private Integer currentPagePos;
    private ContentPage currentPage;

    public final static String CONTENT_PAGE_POSITION = "cp_pos";
    public final static String CONTENT_PAGES_LIST = "cp_list";

    private final static String PLACEHOLDER_NEXT_PAGE = "%NEXT_PAGE_LINK%";
    private final static String PLACEHOLDER_PREVIOUS_PAGE = "%PREV_PAGE_LINK%";
    private final static String PLACEHOLDER_TITLE = "%TITLE%";
    private final static String PLACEHOLDER_CONTENT = "%CONTENT%";
    private final static String ASSETS_FILE_PREFIX = "html" + File.separator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        pages = ContentPageDAO.getInstance().findAll();
        currentPagePos = intent.getExtras().getInt(CONTENT_PAGE_POSITION);
        if(pages != null && currentPagePos != null && currentPagePos < pages.size()) {
            currentPage = pages.get(currentPagePos);
            loadFile(currentPage.getTemplateFileName());
        } else {
            TILogger.getLog().e(LOG_TAG, pages == null ? "content pages list was not inside the intent"
                    : currentPagePos == null ? "no current page position included in intent"
                    : currentPagePos > pages.size() ? "current page position was larger then list size"
                    : "error with intents in ContentPagePreviewActivity");
            loadErrorPage();
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                TILogger.getLog().i(LOG_TAG, "Intercepted url loading... url: " + url);
                if(url.contains("http://page: ")) {
                    Integer newPagePos = null;
                    try {
                        newPagePos = Integer.valueOf(url.substring(url.length() - 1, url.length()));
                    } catch(NumberFormatException e) {
                        TILogger.getLog().i("Could not load new page without a new page position contained in url: " + url);
                    }

                    if(newPagePos != null && newPagePos < pages.size()) {
                        currentPage = pages.get(newPagePos);
                        currentPagePos = newPagePos;
                        loadFile(currentPage.getTemplateFileName());
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    public void loadFile(String fileName) {
        if(currentPage != null) {
            String nextPage = "#";
            String previousPage = "#";
            if(pages != null &&(currentPagePos != null || pages.indexOf(currentPage) >= 0)) {
                if (currentPagePos == null) {
                    currentPagePos = pages.indexOf(currentPage);
                }
                if(currentPagePos + 1 < pages.size()) {
                    nextPage = "http://page: " + (currentPagePos + 1);
                }
                if(currentPagePos - 1 >= 0) {
                    previousPage = "http://page: " + (currentPagePos - 1);
                }
            }
            try {
                if(!fileName.contains(ASSETS_FILE_PREFIX)) {
                    fileName = ASSETS_FILE_PREFIX + fileName;
                }
                String content = IOUtils.toString(getAssets().open(fileName))
                        .replaceAll(PLACEHOLDER_NEXT_PAGE, nextPage)
                        .replaceAll(PLACEHOLDER_PREVIOUS_PAGE, previousPage)
                        .replaceAll(PLACEHOLDER_TITLE, currentPage.getTitle())
                        .replaceAll(PLACEHOLDER_CONTENT, currentPage.getHtmlContent());
                webView.loadDataWithBaseURL(Constants.BASE_ASSETS_PATH + fileName, content, "text/html", "UTF-8", null);
            } catch (IOException e) {
                // TODO: make loaded content page without footer.
                webView.loadUrl(Constants.BASE_ASSETS_PATH + fileName);
                TILogger.getLog().e("Could not load asset \"" + fileName + "\" for content page in order to replace" +
                        "next and previous placeholders with links", e);
            }
        }
    }

}
