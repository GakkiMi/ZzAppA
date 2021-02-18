package com.huadingcloudpackage.www.activity.webview;


import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.util.WebViewLifecycleUtils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.pb_web_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.wv_web_view)
    WebView mWebView;
    String url = "https://www.baidu.com";
    String title = "标题";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
        initToolbar();
        setBackBtn();
        setTitle(title);
        // 不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

        WebSettings settings = mWebView.getSettings();
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        settings.setUseWideViewPort(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUserAgentString("dqlpuser");
        // 允许文件访问
        settings.setAllowFileAccess(true);
        // 支持javaScript
        settings.setJavaScriptEnabled(true);
        //设置适应Html5
        settings.setDomStorageEnabled(true); //重点是这个设置
        // 允许网页定位
        settings.setGeolocationEnabled(true);
        // 允许保存密码
        settings.setSavePassword(true);
        settings.setAppCacheEnabled(true);
        settings.setBlockNetworkImage(false);

        // 解决Android 5.0上WebView默认不允许加载Http与Https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        // 加快HTML网页加载完成的速度，等页面finish再加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());

        mWebView.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            // 后退网页并且拦截该事件
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        WebViewLifecycleUtils.onResume(mWebView);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebViewLifecycleUtils.onPause(mWebView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebViewLifecycleUtils.onDestroy(mWebView);
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {

        // 网页加载错误时回调，这个方法会在 onPageFinished 之前调用
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {
//            showError();

        }

        // 开始加载网页
        @Override
        public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        // 完成加载网页
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);
//            showComplete();
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
            // handler.cancel();// Android默认的处理方式
            handler.proceed();// 接受所有网站的证书
            // handleMessage(Message msg);// 进行其他处理
        }

        // 跳转到其他链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {

            String scheme = Uri.parse(url).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                mWebView.loadUrl(url);
            }
            // 已经处理该链接请求
            return true;
        }
    }

    private ValueCallback mFilePathCallback4;

    private class MyWebChromeClient extends WebChromeClient {


        // 收到网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (title != null) {
//                setTitle(title);
            }
        }

        // 收到加载进度变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
        }


    }
}
