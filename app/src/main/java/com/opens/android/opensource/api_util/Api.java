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

    public String getTweetListApi(){
        String url= Uri.parse("https://www.oschina.net/action/openapi/tweet_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;

    }

}
