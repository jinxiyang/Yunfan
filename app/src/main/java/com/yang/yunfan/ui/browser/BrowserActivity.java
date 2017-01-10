package com.yang.yunfan.ui.browser;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yang.yunfan.R;
import com.yang.yunfan.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends BaseActivity {

    /**
     * 加载的网页地址
     */
    public static final String URL = "url";

    /**
     * 加载网页的标题
     */
    public static final String TITLE = "title";

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String mUrl;

    private Map<String, String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        mTitles = new HashMap<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mUrl = bundle.getString(URL, "");
            String title = bundle.getString(TITLE, "");
            if (!TextUtils.isEmpty(mUrl)){
                mTitles.put(mUrl, title);
            }
        }

        toolBar.setTitle("");
        toolBar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()){
                    webview.goBack();
                }else {
                    finishWithAnim();
                }
            }
        });
        if (!mTitles.isEmpty() && !TextUtils.isEmpty(mUrl)){
            tvTitle.setText(mTitles.get(mUrl));
        }
        initWebView();
        webview.loadUrl(mUrl);
    }



    /**
     * 初始化webview
     */
    private void initWebView() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(false);//支持js
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar.getVisibility() != View.VISIBLE){
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
                final String url = view.getUrl();
                if (newProgress == 100){
                    progressBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            tvTitle.setText(mTitles.get(url));
                        }
                    }, 500);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String url = view.getUrl();
                if (url.equals(mUrl)){
                    String firstTitle = mTitles.get(mUrl);
                    if (TextUtils.isEmpty(firstTitle)){
                        mTitles.put(mUrl, title);
                    }
                }else {
                    mTitles.put(url, title);
                }
                tvTitle.setText(mTitles.get(url));
            }
        });

        webview.setWebViewClient(new WebViewClient() {//点击页面链接，在当前browser打开

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()){
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
