package com.opens.android.opensource.api_util;

import com.opens.android.opensource.sum.Sum;
import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/12.
 */

public class ParseJson {

    private JSONObject jsonBody;

    public ParseJson(JSONObject jsonBody) {

        this.jsonBody = jsonBody;
    }


    public JSONObject getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
    }


    public List<Tweet> parseTweets() throws JSONException {

        List<Tweet> tweets=new ArrayList<>();
        JSONArray tweetsJsonArray =getJsonBody().getJSONArray("tweetlist");

        //取出photo数组中的object，实例一个galleryitem 存起来，填好ID，caption，如果有url,也村上
        for (int i = 0; i < tweetsJsonArray.length(); i++) {
            JSONObject tweetJsonObject= tweetsJsonArray.getJSONObject(i);
            Tweet tweet = new Tweet();
            tweet.setPortraitUrl(tweetJsonObject.getString("portrait"));
            tweet.setAuthor(tweetJsonObject.getString("author"));
            tweet.setTweetBody(tweetJsonObject.getString("body"));
            tweet.setTweetPubDate(tweetJsonObject.getString("pubDate"));
            tweet.setCommentCount(tweetJsonObject.getString("commentCount"));
            tweets.add(tweet);
        }
        return tweets;
    }

    public List<Sum> parseSumOpenNews() throws JSONException {
        List<Sum> sumList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("newslist");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            Sum sum=new Sum();
            sum.setSumAuthorName(newsJsonObject.getString("author"));
            sum.setSumId(newsJsonObject.getString("id"));
            sum.setSumTitle(newsJsonObject.getString("title"));
            sum.setSumType(newsJsonObject.getString("type"));
            sum.setSumAuthorId(newsJsonObject.getString("authorid"));
            sum.setSumPubDate(newsJsonObject.getString("pubDate"));
            sum.setSumCommentCount(newsJsonObject.getString("commentCount"));
            sumList.add(sum);
        }
        return sumList;
    }
}
