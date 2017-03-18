package com.opens.android.opensource.api_util;

import android.util.Log;

import com.opens.android.opensource.sum.Sum;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.user_model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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


    private final String KIND_SOFTWARE ="KIND_SOFTWARE" ;//30
    private final String ITEM_SOFTWARE="ITEM_SOFTWARE";// 31 32  33 34

    private final String MY_FAVORITE="MY_FAVORITE";
    private final String MY_FOLLOWING="MY_FOLLOWING";
    private final String MY_FANS="MY_FANS";
    private final String MY_MESSAGE="MY_MESSAGE";
    private final String MY_BLOG="MY_BLOG";
    private static User theUser;

    public User getUser() {
        return theUser;
    }

    public User setUser(User user) throws IOException, JSONException {
        Api api=new Api();
        JSONObject json=new FetchJson(api.getUserInfo()).getUrlString();
        user=new ParseJson(json).parseUserInfo();
        return user;

    }




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


        if(api.getAccessToken()==null){
            System.out.println("access token is null++++++++++++++++++++++++++++++++++++++++++++");
        }
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
                List<Sum> sumRecList= new ParseJson(jsonBody).parseSumRecommndBlog();
                return loadRecBlog(sumRecList);

            case "02"://综合--技术问答
                jsonBody=new FetchJson(api.getTechQA()).getUrlString();
                List<Tweet> tweetTechQAList=new ParseJson(jsonBody).parseTechQA();
                return loadTechQABody(tweetTechQAList);


            case"03"://综合--职业生涯
                jsonBody=new FetchJson(api.getJobCareer()).getUrlString();
                List<Tweet> jobCareerList=new ParseJson(jsonBody).parseTechQA();
                return loadTechQABody(jobCareerList);

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
            case "30":
                Log.d(KIND_SOFTWARE," open software kind");
                jsonBody=new FetchJson(api.getSoftwareListApi(getType())).getUrlString();
                return new ParseJson(jsonBody).parseKindSoftwares();

            case "31":
            case "32":
            case "33":
            case "34":
                Log.d(ITEM_SOFTWARE," open software kind");
                jsonBody=new FetchJson(api.getSoftwareListApi(getType())).getUrlString();
                return new ParseJson(jsonBody).parseItemSoftwares();


            case "40":
                Log.d(MY_TWEET,"my tweet");
                User current=setUser(new User());
                String userId=current.getUserId();
                System.out.println(userId);
                jsonBody=new FetchJson(api.getUserTweet(userId)).getUrlString();
                return new ParseJson(jsonBody).parseTweets();
            case "41":
                Log.d(MY_FAVORITE,"my favorite");
                jsonBody=new FetchJson(api.getUserFavorList()).getUrlString();
                return new ParseJson(jsonBody).parseFavorList();
            case "42":
                Log.d(MY_FOLLOWING,"my following");
//                String str=api.getFcousList("1");
//                String str1=api.getFcousList("0");
                jsonBody=new FetchJson(api.getFcousList("1")).getUrlString();
                return new ParseJson(jsonBody).parseFavorList();
            case "43":
                Log.d(MY_FANS,"my fans");
                jsonBody=new FetchJson(api.getFanList("0")).getUrlString();
                return new ParseJson(jsonBody).parseFavorList();
            case "44":
                Log.d(MY_MESSAGE,"my message");
                //暂无
            case "45":
                Log.d(MY_BLOG,"my blog");
                jsonBody=new FetchJson(api.getMyBlog(setUser(new User()).getUserId())).getUrlString();
                return new ParseJson(jsonBody).parseMyBlog();
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
    public List<Sum> loadRecBlog(List<Sum> sumList) throws IOException, JSONException {
        Api api=new Api();
        JSONObject jsonBody;

        for(Sum theSum: sumList){
           jsonBody=new FetchJson(api.getRecommondBlogDetail(theSum.getSumId())).getUrlString();
            theSum=new ParseJson(jsonBody).parseRecBlogBody(theSum);
        }
        return sumList;
    }

    /**
     * @param tweetList  综合--技术问答的body
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public List<Tweet> loadTechQABody(List<Tweet> tweetList) throws IOException, JSONException {
        Api api=new Api();
        JSONObject jsonBody;

        for (Tweet tweet:
             tweetList) {
            jsonBody=new FetchJson(api.getTechQADetail(tweet.getTweetId())).getUrlString();
            tweet=new ParseJson(jsonBody).parseTechQABody(tweet);
        }
        return  tweetList;
    }
    public User JudgeAndRetUser() throws IOException, JSONException {
        Api api=new Api();


        if(api.getAccessToken()==null){
            System.out.println("access token is null++++++++++++++++++++++++++++++++++++++++++++");
        }
        JSONObject jsonBody=new JSONObject();

                Log.d("MY","my");
                jsonBody=new FetchJson(api.getMyInformation()).getUrlString();
                User u=new ParseJson(jsonBody).parseMyInformation();
                return u;
    }
}

