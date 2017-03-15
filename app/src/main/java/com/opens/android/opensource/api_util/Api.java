package com.opens.android.opensource.api_util;

import android.net.Uri;

/**
 * Created by ttc on 2017/3/13.
 */

public class Api {
    private static final String TAG = "Api";
    private static final String ACCESS_TOKEN = "9714012a-347c-4d2e-a677-3625e78c151f";
    public Api() {
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



}
