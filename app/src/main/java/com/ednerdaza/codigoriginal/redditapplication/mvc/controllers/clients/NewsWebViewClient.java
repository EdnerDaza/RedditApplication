package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.clients;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.helpers.Helpers;

/**
 * Created by administrador on 19/02/17.
 */

public class NewsWebViewClient extends WebViewClient {

    //region CONSTRUCTOR

        public NewsWebViewClient() {
            super();
        }

    //endregion

    //region METODOS WEB VIEW CLIENT

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Helpers.progressDialogLoadingShow("", Helpers.getActivity().getResources().
                    getString(R.string.wait_loading_news));
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Helpers.customProgressDialogClose();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

    //endregion

}
