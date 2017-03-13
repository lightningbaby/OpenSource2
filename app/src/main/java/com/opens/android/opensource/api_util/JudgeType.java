package com.opens.android.opensource.api_util;

import android.util.Log;

import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/13.
 */

public class JudgeType {

    private final String LATEST_TWEET="LATEST_TWEET";// 00
    private final String HOT_TWEET="HOT_TWEET";// 01
    private final String MY_TWEET="MY_TWEET";//02


    private String Type;


    public JudgeType(String type) {
        Type = type;
    }

    public String getType() {
        return Type;
    }




    public List<?> JudgeAndRet() throws IOException, JSONException {
        Api api=new Api();
        JSONObject jsonBody=new JSONObject();

        switch (getType()){
            case "00"://the latest
                Log.d(LATEST_TWEET,"latest tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();

            case "01"://the hot
                Log.d(HOT_TWEET,"hot tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();

            case "02"://oschina
                Log.d(MY_TWEET,"hot tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();

            default:
                List<Tweet> mTweets = new ArrayList<>();
                return new ParseJson(mTweets,jsonBody).parseTweets();
        }

    }
}

