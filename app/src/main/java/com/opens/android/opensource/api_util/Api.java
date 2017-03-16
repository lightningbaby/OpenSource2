package com.opens.android.opensource.api_util;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by ttc on 2017/3/13.
 */

public class Api {
    private static final String TAG = "Api";
    public static final String APP_ID= "OMLCTCM2aKXpnlXeEoI1";
    public static final String APP_SCERET= "Uo52vp8wmE4vb5okdm4XqiHKDWPSTNM1";
    public static final String GRANT_TYPE="authorization_code";
    public static final String CALL_BACK_URL= "https://my.oschina.net/lightningbaby";
    public static  String ACCESS_TOKEN;

//    private String accessToken;



    public Api() {
    }

    public Api(String access) {

        ACCESS_TOKEN=access;
    }

    public String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    //获取access token
    public String getAccessToken(String code){

        String url= Uri.parse("https://www.oschina.net/action/openapi/token")
                .buildUpon()
                .appendQueryParameter("client_id",APP_ID)
                .appendQueryParameter("client_secret", APP_SCERET)
                .appendQueryParameter("grant_type", GRANT_TYPE)
                .appendQueryParameter("redirect_uri", CALL_BACK_URL)
                .appendQueryParameter("code", code)
                .appendQueryParameter("refresh_token", "")
                .appendQueryParameter("dataType", "json")
                .appendQueryParameter("callback", "json")
                .build().toString();
        return url;

    }
    public String getInitUrl(){
        String url= Uri.parse("https://www.oschina.net/action/oauth2/authorize")
                .buildUpon()
                .appendQueryParameter("response_type","code")
                .appendQueryParameter("client_id", APP_ID)
                .appendQueryParameter("redirect_uri",CALL_BACK_URL)
                .build().toString();
        return url;
    }

    public String getPubTweetUrl(String msg){
        String url= Uri.parse("https://www.oschina.net/action/openapi/tweet_pub")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("msg", msg)
//                .appendQueryParameter("image", image.toString())
                .build().toString();
        return url;
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

    public String getOpenNewsDetailApi(String SumId){
        String url= Uri.parse("https://www.oschina.net/action/openapi/news_detail")
                .buildUpon()
                .appendQueryParameter("id", SumId)
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }

    /**
     * @return  综合模块--推荐博客
     */
    public String getRecommndBlog(){

        String url= Uri.parse("https://www.oschina.net/action/openapi/blog_recommend_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }

    /**
     * @return推荐博客的详情
     */
    public String getRecommondBlogDetail(String SumId){

        String url= Uri.parse("https://www.oschina.net/action/openapi/blog_detail")
                .buildUpon()
                .appendQueryParameter("id", SumId)
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }

    /**
     * @return综合模块--技术问答
     */
    public String getTechQA(){
        String url= Uri.parse("https://www.oschina.net/action/openapi/post_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("catalog", "1")
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }

    public String getTechQADetail(String  tweetId){
    String url= Uri.parse("https://www.oschina.net/action/openapi/post_detail")
            .buildUpon()
            .appendQueryParameter("id", tweetId)
            .appendQueryParameter("access_token",ACCESS_TOKEN)
            .appendQueryParameter("dataType", "json")
            .build().toString();
    return url;
    }
    /**
     * @return 综合模块--职业生涯
     */
    public String getJobCareer(){

        String url= Uri.parse("https://www.oschina.net/action/openapi/post_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("catalog", "100")
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("page/pageIndex", "1")
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


    public String getSoftwareListApi(String softwareType){
        String type="";//default the latest
        String url;

        if("30".equals(softwareType)){
                url= Uri.parse("https://www.oschina.net/action/openapi/project_catalog_list")
                        .buildUpon()
                        .appendQueryParameter("access_token",ACCESS_TOKEN)
                        .appendQueryParameter("tag", "0")
                        .appendQueryParameter("dataType", "json")
                        .build().toString();
            return url;
        }
      //  https://www.oschina.net/action/openapi/project_catalog_list/?access_token=f146017c-d4b3-4be3-9bdf-9d74650b61fe&tag=0&dataType=json

        switch (softwareType){
            case "31"://latest
                type="recommend";
                break;
            case "32"://hot
                type="time";
                break;
            case "33"://oschina
                type="view";
                break;
            case "34":
                type="cn";
                break;
        }
        url= Uri.parse("https://www.oschina.net/action/openapi/project_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("type", type)
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;

    }

//  https://www.oschina.net/action/openapi/project_list?access_token=f146017c-d4b3-4be3-9bdf-9d74650b61fe&type=recommend&dataType=json
//    https://www.oschina.net/action/openapi/project_list?access_token=f146017c-d4b3-4be3-9bdf-9d74650b61fe&type=time&dataType=json
//    https://www.oschina.net/action/openapi/project_list?access_token=f146017c-d4b3-4be3-9bdf-9d74650b61fe&type=view&dataType=json
//    https://www.oschina.net/action/openapi/project_list?access_token=9714012a-347c-4d2e-a677-3625e78c151f&type=cn&dataType=json



  //  https://www.oschina.net/action/openapi/search_list?access_token=b7436a62-e11b-4f3b-82bb-2847fb59ee49&catalog=news&q=android&pageSize=20&page=1&dataType=json

    public String getSearchNewsApi(String searchKey){
        String keyQ=searchKey;//default the latest

        String url= Uri.parse("https://www.oschina.net/action/openapi/search_list")
                .buildUpon()
                .appendQueryParameter("access_token",ACCESS_TOKEN)
                .appendQueryParameter("catalog", "news")
                .appendQueryParameter("q", keyQ)
                .appendQueryParameter("pageSize", "20")
                .appendQueryParameter("page/pageIndex", "1")
                .appendQueryParameter("dataType", "json")
                .build().toString();
        return url;
    }


}
