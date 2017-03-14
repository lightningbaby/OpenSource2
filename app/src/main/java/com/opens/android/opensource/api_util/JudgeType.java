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

    private final String SUM_OPENNEWS="sum---openNews";// 00
    private final String SUM_RECOMMOND_BLOG="sum--recommond_blog";//01
    private final String SUM_TECH_QA="sum--tech_q&a";//02
    private final String SUM_JOB_CAREER="sum--job_career";//03

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


    /**
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public List<?> JudgeAndRet() throws IOException, JSONException {
        Api api=new Api();
        JSONObject jsonBody=new JSONObject();
//        List<Tweet> mTweets = new ArrayList<>();
//        List<Sum> sumList=new ArrayList<>();

        switch (getType()){
            case "00"://综合--开源资讯
                Log.d(SUM_OPENNEWS,"SUM--OPEN--NEWS");
                jsonBody=new FetchJson(api.getOpenNewsApi()).getUrlString();
                List<Sum> sumList=new ParseJson(jsonBody).parseSumOpenNews();
                return loadBody(sumList);


            case "01"://综合--推荐博客
                jsonBody=new FetchJson(api.getRecommndBlog()).getUrlString();
                return new ParseJson(jsonBody).parseSumRecommndBlog();

            case "02"://综合--技术问答
                jsonBody=new FetchJson(api.getTechQA()).getUrlString();

            case"03"://综合--职业生涯
                jsonBody=new FetchJson(api.getJobCareer()).getUrlString();

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

    public List<?> loadBody(List<Sum> sumList) throws IOException, JSONException {
        Api api=new Api();
        JSONObject jsonBody;

        for(Sum theSum: sumList){
           jsonBody=new FetchJson(api.getOpenNewsDetailApi(theSum.getSumId())).getUrlString();
            theSum=new ParseJson(jsonBody).parseSumOpenNewsBody(theSum);
        }
        return sumList;
    }

}

