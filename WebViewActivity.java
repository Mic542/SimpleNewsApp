package com.example.michael.newsapp;

import android.net.wifi.aware.PublishConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    public static String NEWS_URL = "NEWS_URL";
    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.wv);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (getIntent().hasExtra(NEWS_URL)) {
            url = getIntent().getStringExtra(NEWS_URL);
        } else {
            throw new IllegalArgumentException("Activity cannot find  extras " + NEWS_URL);
        }

        webView.loadUrl(url);
    }
}
