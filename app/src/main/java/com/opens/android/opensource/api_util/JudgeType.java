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

        switch (getType()){
            case "00":
                Log.d(LATEST_TWEET,"latest tweet");
                JSONObject jsonBody=new FetchJson(api.getTweetListApi()).getUrlString();
                List<Tweet> mTweets = new ArrayList<>();
                return new ParseJson(mTweets,jsonBody).parseTweets();
            case "01":
                Log.d(HOT_TWEET,"hot tweet");
                break;
            case "02":
                Log.d(MY_TWEET,"my tweet");
                break;
        }
        return null;
    }
}

