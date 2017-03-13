package com.opens.android.opensource.api_util;

import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ttc on 2017/3/12.
 */

public class ParseJson {
    private List<Tweet> tweets;
    private JSONObject jsonBody;

    public ParseJson(List<Tweet> tweets, JSONObject jsonBody) {
        this.tweets = tweets;
        this.jsonBody = jsonBody;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public JSONObject getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(JSONObject jsonBody) {
        this.jsonBody = jsonBody;
    }


    public List<Tweet> parseTweets() throws JSONException {

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

}
