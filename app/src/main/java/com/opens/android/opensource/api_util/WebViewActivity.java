package com.opens.android.opensource.api_util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.opens.android.opensource.R;


/**
 * Created by ttc on 2017/3/15.
 */

public class WebViewActivity extends AppCompatActivity {
    private String EXTRA_CRIME_ID ="EXTRA_CRIME_ID";
    private String EXTRA_CRIME_IDENTIFY ="EXTRA_CRIME_IDENTIFY";
    private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";

    private WebView webView;
    private String identify;
    private String id;
    private String urlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        identify = (String)this.getIntent().getSerializableExtra(EXTRA_CRIME_IDENTIFY);
        id=(String)this.getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        urlString=(String)this.getIntent().getSerializableExtra(EXTRA_CRIME_URL);
        setContentView(R.layout.activity_webview);
        init();
    }
    private void init(){
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl(urlString);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
