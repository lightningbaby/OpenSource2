package com.opens.android.opensource.api_util;

import android.net.Uri;

/**
 * Created by ttc on 2017/3/13.
 */

public class Api {
    private static final String TAG = "Api";
    private static final String ACCESS_TOKEN = "74dbedca-3933-4597-902a-3c39ddd6910f";
    public Api() {
    }

    /**
     * @return 综合模块--开源咨询--列表--不包含摘要
     */
    public String getOpenNewsApi( ){

        String url= Uri.parse("https://www.oschina.net/action/openapi/news_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("catalog", "1")
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }

    public String getTweetListApi(String tweetType){
        String user="0";//default the latest

        switch (tweetType){
            case "10"://latest
                user="0";
                break;
            case "11"://hot
                user="-1";
                break;
            case "12"://oschina
                user="1";
                break;
        }

        String url= Uri.parse("https://www.oschina.net/action/openapi/tweet_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("user", user)
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }




}
