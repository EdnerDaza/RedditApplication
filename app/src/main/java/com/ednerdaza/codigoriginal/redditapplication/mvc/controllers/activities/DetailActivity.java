package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.helpers.Helpers;
import com.ednerdaza.codigoriginal.redditapplication.classes.utilities.Config;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.clients.NewsWebViewClient;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Children;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Data;

public class DetailActivity extends AppCompatActivity {

    private Children mChildren;

    ImageView mImageViewItemDetail, mHeaderViewItemDetail;
    TextView mTextViewTitle, mTextViewItemTitle, mTextViewItemSummary;
    WebView mWebViewUrlMain, mWebViewUrlPermalink;
    WebSettings mWebSettings;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(Config.LOG_TAG, "/ ON CREATE / "+mChildren);
        mContext = this;
        Helpers.setContexto(mContext);
        Helpers.setActivity(this);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        mChildren= (Children)intent.getSerializableExtra("children");

        String kind = mChildren.getKind();
        Data data = mChildren.getData();

        //setTitle(data.getTitle());

        mWebViewUrlMain = (WebView) findViewById(R.id.webviewNews);
        mWebSettings = mWebViewUrlMain.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebViewUrlMain.setWebViewClient(new NewsWebViewClient());
        mWebViewUrlMain.loadUrl(data.getUrl());

        mWebViewUrlPermalink = (WebView) findViewById(R.id.webviewComments);
        mWebSettings = mWebViewUrlPermalink.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebViewUrlPermalink.setWebViewClient(new NewsWebViewClient());
        mWebViewUrlPermalink.loadUrl(Config.BASE_URL+data.getPermalink());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v( Config.LOG_TAG , " // onBackPressed Called from BaseActionBarActivity");
        this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}
