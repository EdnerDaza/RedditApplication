package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ednerdaza.codigoriginal.redditapplication.R;
import com.ednerdaza.codigoriginal.redditapplication.classes.helpers.Helpers;
import com.ednerdaza.codigoriginal.redditapplication.classes.utilities.Config;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.clients.CommentsWebViewClient;
import com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.clients.NewsWebViewClient;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Children;
import com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities.Data;

public class DetailActivity extends AppCompatActivity {

    private Children mChildren;
    WebView mWebViewUrlMain, mWebViewUrlPermalink;
    WebSettings mWebSettings;

    Context mContext;

    //region METODOS DEL CICLO DE VIDA

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

            mWebViewUrlMain = (WebView) findViewById(R.id.webviewNews);
            mWebSettings = mWebViewUrlMain.getSettings();
            mWebSettings.setJavaScriptEnabled(true);
            mWebViewUrlMain.setWebViewClient(new NewsWebViewClient());
            mWebViewUrlMain.loadUrl(data.getUrl());

            mWebViewUrlPermalink = (WebView) findViewById(R.id.webviewComments);
            mWebSettings = mWebViewUrlPermalink.getSettings();
            mWebSettings.setJavaScriptEnabled(true);
            mWebViewUrlPermalink.setWebViewClient(new CommentsWebViewClient());
            mWebViewUrlPermalink.loadUrl(Config.BASE_URL+data.getPermalink());

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    //endregion

    //region METODOS DEL MENU

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            }
            return super.onOptionsItemSelected(item);
        }

    //endregion

    //region METODO DEL BOTON BACK

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Log.v( Config.LOG_TAG , " // onBackPressed Called from BaseActionBarActivity");
            this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }

    //endregion

}
