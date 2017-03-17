package com.opens.android.opensource.api_util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ttc on 2017/3/17.
 */

public  class PostPic {
    private final static String TAG="PostPic";
    private Context mContext;

    public PostPic() {
    }

    public void sendImage( String msg){
        Api api=new Api();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 60, stream);
        byte[] bytes = stream.toByteArray();
        String img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("access_token",api.getAccessToken());
        params.add("msg",msg);
//        params.add("img", img);

        client.post("http://www.oschina.net/action/openapi/post_pub", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                Toast.makeText(mContext, "Upload Success!", Toast.LENGTH_LONG).show();
                Log.d(TAG,"发送成功");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Toast.makeText(mContext, "Upload Failed!", Toast.LENGTH_LONG).show();
                Log.d(TAG,"发送失败");
            }

//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                Toast.makeText(MainActivity.this, "Upload Success!", Toast.LENGTH_LONG).show();
//
//            }
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Toast.makeText(MainActivity.this, "Upload Fail!", Toast.LENGTH_LONG).show();
//            }
        });
    }

}
