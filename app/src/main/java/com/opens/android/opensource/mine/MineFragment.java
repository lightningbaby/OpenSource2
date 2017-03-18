package com.opens.android.opensource.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opens.android.opensource.R;
import com.opens.android.opensource.User.UserListActivity;
import com.opens.android.opensource.api_util.JudgeType;
import com.opens.android.opensource.shake.ShakeActivity;
import com.opens.android.opensource.user_model.User;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by ttc on 2017/3/12.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private TextView mTextView;
    private LinearLayout mShakeLayout;
    private LinearLayout MyTweet;
    private LinearLayout MyFavorite;
    private LinearLayout MyFollowing;
    private LinearLayout MyFans;
    private LinearLayout MyMessage;
    private LinearLayout MyBlog;
    private TextView MyTweetText;
    private TextView MyFavoriteText;
    private TextView MyFollowingText;
    private TextView MyFansText;
    private TextView MyUsrName;
    //private ThumbnailDownloader mThumbnailDownloader;
    private ImageView MyPortrait;
    private Context mContext;
    Bitmap bitmap=null;
    private User mUser=new User();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mine,container,false);
        mShakeLayout=(LinearLayout)v.findViewById(R.id.my_shake);
        MyTweet=(LinearLayout)v.findViewById(R.id.my_tweet);
        MyFavorite=(LinearLayout)v.findViewById(R.id.my_favorite);
        MyFollowing=(LinearLayout)v.findViewById(R.id.my_following);
        MyFans=(LinearLayout)v.findViewById(R.id.my_fans);
        MyMessage=(LinearLayout)v.findViewById(R.id.my_message);
        MyBlog=(LinearLayout)v.findViewById(R.id.my_blog);
        MyTweetText=(TextView)v.findViewById(R.id.tv_tweet);
        MyFavoriteText=(TextView)v.findViewById(R.id.tv_favorite);
        MyFollowingText=(TextView)v.findViewById(R.id.tv_following);
        MyFansText=(TextView)v.findViewById(R.id.tv_follower);
        MyPortrait=(ImageView)v.findViewById(R.id.iv_portrait);
        MyUsrName=(TextView)v.findViewById(R.id.tv_user_name);
        mShakeLayout.setOnClickListener(this);
        MyTweet.setOnClickListener(this);
        MyFavorite.setOnClickListener(this);
        MyFollowing.setOnClickListener(this);
        MyFans.setOnClickListener(this);
        MyMessage.setOnClickListener(this);
        MyBlog.setOnClickListener(this);
        loading();
//
//        //      mTextView=(TextView)v.findViewById(R.id.text1);
//        //mTextView.setText("摇一摇");
//        /*
//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(), ShakeActivity.class);
//                startActivity(intent);
//            }
//        });
//        */
        //
        return v;
    }
    public void loading(){
        new FetchTask().execute();
    }
//    public void bindDrawable(Drawable drawable){
//        MyPortrait.setImageDrawable(drawable);
//    }
    public void bind(User user){
        MyTweetText.setText("0");
        MyFavoriteText.setText(user.getFavoriteCount());
        MyFollowingText.setText(user.getFollowersCount());
        MyFansText.setText(user.getFansCount());
        MyUsrName.setText(user.getUserName());
        //MyPortrait.setImageBitmap(bitmap);

    }

    @Override
    public void onClick(View v){
        int position=0;
        Intent intent;
        switch (v.getId()){
            case R.id.my_shake:
                intent=new Intent(getActivity(), ShakeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_tweet:
                position =40;
                System.out.println(position);
                System.out.println("my_tweet");
                break;
            case R.id.my_favorite:
                position=41;
                System.out.println(position);
                System.out.println("my_fav");
                break;
            case R.id.my_following:
                position=42;
                System.out.println(position);
                break;
            case R.id.my_fans:
                position=43;
                System.out.println(position);
                break;
            case R.id.my_message:
                position=44;
                System.out.println(position);
                break;
            case R.id.my_blog:
                System.out.println(position);
                position=45;
                break;
        }
        if(position!=0){
            System.out.println("final"+position);
            intent= UserListActivity.newIntent(getActivity(),position);
            startActivity(intent);
        }
    }


    private class FetchTask extends AsyncTask<Void,Void,User> {
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            mUser=user;
            bind(user);
        }

        @Override
        protected User doInBackground(Void... Void) {
            JudgeType judgeType = new JudgeType("4");
            User item = new User();
            try {
                item =judgeType.JudgeAndRetUser();
//                Drawable drawable=new BitmapDrawable(mContext.getResources(),thumbnail);
//                bindDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            HttpGet httpRequest = new HttpGet(item.getUserPortrait());
//            //取得HttpClient 对象
//            HttpClient httpclient = new DefaultHttpClient();
//            try {
//                //请求httpClient ，取得HttpRestponse
//                HttpResponse httpResponse = httpclient.execute(httpRequest);
//                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//                    //取得相关信息 取得HttpEntiy
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    //获得一个输入流
//                    InputStream is = httpEntity.getContent();
//                    System.out.println(is.available());
//                    System.out.println("Get, Yes!");
//                    bitmap = BitmapFactory.decodeStream(is);
//                    is.close();
//                }
//
//            } catch (ClientProtocolException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            return item;

        }


    }
}

