package com.opens.android.opensource.api_util;

import android.util.Log;

import com.opens.android.opensource.sum.Sum;
import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by ttc on 2017/3/13.
 */

public class JudgeType {

    private final String SUM="sum";// 00

    private final String LATEST_TWEET="LATEST_TWEET";// 10
    private final String HOT_TWEET="HOT_TWEET";// 11
    private final String MY_TWEET="MY_TWEET";//12


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
//        List<Tweet> mTweets = new ArrayList<>();
//        List<Sum> sumList=new ArrayList<>();

        switch (getType()){
            case "00":
                Log.d(SUM,"SUM");
                jsonBody=new FetchJson(api.getOpenNewsApi()).getUrlString();
                return new ParseJson(jsonBody).parseSumOpenNews();


            case "10"://the latest tweet
                Log.d(LATEST_TWEET,"latest tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();
                return new ParseJson(jsonBody).parseTweets();

            case "11"://the hot tweet
                Log.d(HOT_TWEET,"hot tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();
                return new ParseJson(jsonBody).parseTweets();

            case "12"://oschina tweet
                Log.d(MY_TWEET,"hot tweet");
                jsonBody=new FetchJson(api.getTweetListApi(getType())).getUrlString();
                return new ParseJson(jsonBody).parseTweets();

        }
        return null;
    }

}

