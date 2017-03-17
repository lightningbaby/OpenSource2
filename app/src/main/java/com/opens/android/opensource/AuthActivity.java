package com.opens.android.opensource;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.opens.android.opensource.api_util.Api;
import com.opens.android.opensource.api_util.FetchJson;
import com.opens.android.opensource.api_util.ParseJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ttc on 2017/3/15.
 */

public class AuthActivity extends AppCompatActivity{
    private WebView AuthView;
    public static final String AUTH_URL= "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=67g5B5iJJGsNdOc6LfeH&redirect_uri=https://my.oschina.net/u/2962802";
    private String mCode;
    RequestQueue mQueue;
    public static final String CALL_BACK_URL= "https://my.oschina.net/lightningbaby";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**全屏设置，隐藏窗口所有装饰**/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_auth);
        init();
    }
    private void init() {
        AuthView = (WebView) findViewById(R.id.fake_web_view);
        WebSettings webSettings = AuthView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        //webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        auth();
    }

    private void auth(){
        mQueue = Volley.newRequestQueue(this);
        String accessToken=null;
        if(TextUtils.isEmpty(accessToken)){
            AuthView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if(url.startsWith(CALL_BACK_URL)){
                        String retrunCode=null;
                        if(url.indexOf("code=")!=-1){
                            retrunCode=getCodeFromUrl(url);
                        }
                        if(!TextUtils.isEmpty(retrunCode)){
                            try {
                                requestForAccessToken(retrunCode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(AuthActivity.this,"auth wrong",Toast.LENGTH_SHORT);
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view,url);
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Log.i("page","finish");
                }

            });

            AuthView.loadUrl(new Api().getInitUrl());
        }
    }
    private String getCodeFromUrl(String url) {
        int startIndex = url.indexOf("code=") + "code=".length();
        int endIndex = url.indexOf("&state");
        String code = url.substring(startIndex, endIndex);
        Log.i("code=" , code);
        return code;
    }
    private void requestForAccessToken(String returnCode) throws IOException, JSONException {
        //final JSONObject requestJson = new JSONObject();
        System.out.println("code...................................:" + returnCode);
        mCode = returnCode;

        new FetchAccessToken().execute();


    }

    private class FetchAccessToken extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Api api=new Api();
            JSONObject jsonBody=new JSONObject();
            try {
                jsonBody= new FetchJson((api.getAccessToken(mCode))).getUrlString();
                String access_token=new ParseJson(jsonBody).paserAccessToken();

                api.setAccessToken(access_token);
                System.out.println("access-token-----get--------------------"+api.getAccessToken());
                System.out.println("access-token-------------------------"+access_token);
//                return access_token;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i=new Intent(AuthActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }


}
