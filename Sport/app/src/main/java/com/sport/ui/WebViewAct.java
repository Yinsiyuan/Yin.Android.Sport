package com.sport.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sport.R;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import butterknife.BindView;


public class WebViewAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;

    @BindView(R.id.mWebView)
    WebView mWebView;

    @BindView(R.id.mProgress)
    ProgressBar mProgress;

    private String title;
    private String url;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_webview;
    }

    @Override
    public void initView() {
        title=getIntent().getStringExtra("title");
        url=getIntent().getStringExtra("url");
        mTopView.init(true, 0, title, 0, 0, 0, new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                backOnClick();
            }
        });
        initWebView();
        //加载网址
        mWebView.loadUrl(url);
    }

    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        //支持网页的js
        settings.setJavaScriptEnabled(true);
        //防止webview跳出程序
        mWebView.setWebViewClient(new MyWebClient());
        //设置进度条
        mWebView.setWebChromeClient(new MyChromeClient());

    }

    private class MyWebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            view.loadUrl(url);
            return true;
        }
    }

    private class MyChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);
            } else {
                if (View.GONE == mProgress.getVisibility()) {
                    mProgress.setVisibility(View.VISIBLE);
                }
                mProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    private void backOnClick() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }
}
