package com.opens.android.opensource.api_util;

import android.text.Html;

import com.opens.android.opensource.discover.Software;
import com.opens.android.opensource.sum.Sum;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.user_model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

//获取access token
    public String paserAccessToken() throws JSONException {
        return getJsonBody().getString("access_token");
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

    /**
     * @return  综合--开源资讯
     * @throws JSONException
     */
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

    /**
     * @param
     * @return  根据开源资讯列表的ID--获取某一条的body---将body填充到Sum中
     * @throws JSONException
     */
    public Sum parseSumOpenNewsBody(Sum sum) throws JSONException {

//        for(int i=0;i<sumList.size();i++){
//            Sum sum=sumList.get(i);
//            sum.setSumBody(getJsonBody().getString("body"));
//        }
//        return sumList;

        Document document=  Jsoup.parse(getJsonBody().getString("body"));//parse的参数是要解析的字符串或网页啥的，比如 <p>hello</p><p>world</p><t>baga</t>
        Elements elements=  document.getElementsByTag("p");//根据标签解析 存储所有符合条件的东东,比如解析得到集合<p>hello</p><p>world</p>
        String body="";
        for(Element e:elements){
            body +=e.text();//遍历符合条件的东东 ，.text方法是取标签里面的内容，比如遍历输出 hello   world
        }
        sum.setSumBody(body);

        String detailUrl=getJsonBody().getString("url");
        sum.setSumDetailUrl(detailUrl);

        return sum;
    }


    /**
     * @return  z综合--推荐博客
     * @throws JSONException
     */
    public List<Sum> parseSumRecommndBlog() throws JSONException {

        List<Sum> sumList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("bloglist");

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
    //我的博客
    public List<Sum> parseMyBlog() throws JSONException {

        List<Sum> sumList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("projectlist");

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

    public Sum parseRecBlogBody(Sum sum) throws JSONException {
//        Document document=  Jsoup.parse(getJsonBody().getString("body"));//parse的参数是要解析的字符串或网页啥的，比如 <p>hello</p><p>world</p><t>baga</t>
//        Elements elements=  document.getElementsByTag("/n");//根据标签解析 存储所有符合条件的东东,比如解析得到集合<p>hello</p><p>world</p>
//        Element element=document.getBy
        String body=getJsonBody().getString("body");
        body=body.replace("\n","");
//        for(Element e:elements){
//            body +=e.text();//遍历符合条件的东东 ，.text方法是取标签里面的内容，比如遍历输出 hello   world
//        }
        sum.setSumBody(body);
        return sum;
    }

    /**
     * @return  开源——软件分类
     * @throws JSONException
     */
    public List<Software> parseKindSoftwares() throws JSONException {

        List<Software> softwareList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("softwareTypes");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            Software software=new Software();
            software.setName(newsJsonObject.getString("name"));
            software.setTag(newsJsonObject.getString("tag"));
            softwareList.add(software);
        }
        return softwareList;
    }


    /**
     * @return  开源——软件分类-- 推荐 最新 热门 国产
     * @throws JSONException
     */
    public List<Software> parseItemSoftwares() throws JSONException {

        List<Software> softwareList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("projectlist");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            Software software=new Software();
            software.setName(newsJsonObject.getString("name"));
            software.setDescription(newsJsonObject.getString("description"));
            software.setUrl(newsJsonObject.getString("url"));
            softwareList.add(software);
        }
        return softwareList;
    }
    /**
     * @param tweet
     * @return  获取综合--技术问答的body
     * @throws JSONException
     */
    public Tweet parseTechQABody(Tweet tweet) throws JSONException {

//        Document document=Jsoup.parse(getJsonBody().getString("body"));
//        Elements elements=document.get
        String body=getJsonBody().getString("body");
        tweet.setTweetDetailUrl(getJsonBody().getString("url"));

        tweet.setTweetBody(Html.fromHtml(body).toString());
        return tweet;
    }

    /**
     * @return  解析综合--技术问答列表--不包含body
     * @throws JSONException
     */
    public List<Tweet> parseTechQA() throws JSONException {

        List<Tweet> tweetList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("post_list");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            Tweet tweet=new Tweet();
            tweet.setAuthor(newsJsonObject.getString("author"));
            tweet.setTweetId(newsJsonObject.getString("id"));
            tweet.setPortraitUrl(newsJsonObject.getString("portrait"));
            tweet.setAuthorId(newsJsonObject.getString("authorid"));
            tweet.setTweetPubDate(newsJsonObject.getString("pubDate"));
            tweet.setCommentCount(newsJsonObject.getString("answerCount"));
            tweet.setTweetTitle(newsJsonObject.getString("title"));
            tweetList.add(tweet);
        }
        return tweetList;
    }

    /**
     * @return  解析综合--技术问答列表--不包含body
     * @throws JSONException
     */
    public List<Tweet> parseSearchNewsResult() throws JSONException {

        List<Tweet> tweetList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("searchlist");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            Tweet tweet=new Tweet();
            tweet.setAuthor(newsJsonObject.getString("author"));
            tweet.setTweetId(newsJsonObject.getString("id"));
            tweet.setTweetTitle(newsJsonObject.getString("title"));
            tweet.setTweetPubDate(newsJsonObject.getString("pubDate"));
            tweet.setTweetDetailUrl(newsJsonObject.getString("url"));
            tweetList.add(tweet);
        }
        return tweetList;
    }


    /**
     * @return
     * @throws JSONException  收藏 关注 粉丝
     */
    public List<User> parseFavorList() throws JSONException {

        List<User> userList=new ArrayList<>();
        JSONArray newsJsonArray =getJsonBody().getJSONArray("userList");

        for (int i=0;i<newsJsonArray.length();i++){
            JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
            User user=new User();
            user.setUserGender(newsJsonObject.getString("gender"));
            user.setUserName(newsJsonObject.getString("name"));//对应 favorite中的objid
            user.setUserPortrait(newsJsonObject.getString("portrait"));
            user.setUserExpertise(newsJsonObject.getString("expertise"));
            user.setUserId(newsJsonObject.getString("userid"));


            userList.add(user);
        }
        return userList;
    }
//获取当前用户信息
    public User parseUserInfo() throws JSONException {

        //JSONObject jsonObject=new JSONObject();
        User user=new User();
        user.setUserId(jsonBody.getString("id"));
        user.setUserEmail(jsonBody.getString("email"));
        user.setUserName(jsonBody.getString("name"));
//        user.setUserGender(jsonBody.getString("gender"));
        user.setUserAvatar(jsonBody.getString("avatar"));
//        user.setUserLocation(jsonBody.getString("location"));

        return user;
    }

    /**
     * 获取我的信息
     * @return
     * @throws JSONException
     */
    public User parseMyInformation() throws JSONException {

        User user=new User();
        user.setUserId(jsonBody.getString("uid"));
        user.setUserName(jsonBody.getString("name"));
        user.setUserGender(jsonBody.getString("gender"));
        user.setFansCount(jsonBody.getString("fansCount"));
        user.setFollowersCount(jsonBody.getString("followersCount"));
        user.setFavoriteCount(jsonBody.getString("favoriteCount"));
        user.setUserPortrait(jsonBody.getString("portrait"));
//        user.setUserLocation(jsonBody.getString("location"));
        return user;
    }
}
