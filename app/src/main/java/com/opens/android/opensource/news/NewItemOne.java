package com.opens.android.opensource.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opens.android.opensource.R;
import com.opens.android.opensource.api_util.JudgeType;
import com.opens.android.opensource.api_util.WebViewActivity;
import com.opens.android.opensource.fetchsource.ThumbnailDownloader;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.tweet.TweetFetch;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ttc on 2017/3/9.
 */

public class NewItemOne {
    private String EXTRA_CRIME_ID ="EXTRA_CRIME_ID";
    private String EXTRA_CRIME_IDENTIFY ="EXTRA_CRIME_IDENTIFY";

    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="NewsItemOne";
    private List<Tweet> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mPhotoHolderThumbnailDownloader;
    private Context mContext;
    private Fragment mFragment;
    private String mString;


    public NewItemOne(RecyclerView mRcycle, Context context, Fragment fragment,String str){
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        mString=str;


        new FetchItemsTask().execute();

        Handler responseHandler=new Handler();
        mPhotoHolderThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        mPhotoHolderThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap thumbnail) {
                        Drawable drawable=new BitmapDrawable(mContext.getResources(),thumbnail);
                        photoHolder.bindDrawable(drawable);
                    }
                }
        );
        mPhotoHolderThumbnailDownloader.start();
        mPhotoHolderThumbnailDownloader.getLooper();
        Log.i(TAG,"Background thread started");


        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        setupAdapter();

    }
    public void Destroy() {
        mPhotoHolderThumbnailDownloader.clearQueue();
        mPhotoHolderThumbnailDownloader.quit();
        Log.i(TAG,"Backeground thread destroyed");
    }
//it is Void ,not void

    /**
     * doInBackground启用后台线程来获取数据，当后台完成任务后用onPostExecute更新UI
     */
    private class FetchItemsTask extends AsyncTask<Void,Void,List<Tweet>> {
        @Override
        protected List<Tweet> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(mString);
            List<Tweet>  item= new ArrayList<>();
            try {
                item =  (List<Tweet>)judgeType.JudgeAndRet();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }

        /**
         *当后台完成任务后用onPostExecute更新UI
         */
        @Override
        protected void onPostExecute(List<Tweet> items) {
            mItems = items;
            setupAdapter();
        }
    }

    //set view
    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //VH 里放 View组件
        private ImageView mTweetPortraitImageView;
        private TextView mAuthorTextView;
        private TextView mTweetBodyTextView;
        private TextView mTweetTimeView;
        private TextView mCommentView;
        private Tweet mTweet;


        public PhotoHolder(View itemView) {
            super(itemView);
            mTweetPortraitImageView = (ImageView) itemView.findViewById(R.id.head);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.usr_id);
            mTweetBodyTextView= (TextView) itemView.findViewById(R.id.title);
            mTweetTimeView=(TextView)itemView.findViewById(R.id.time);
            mCommentView=(TextView)itemView.findViewById(R.id.comment);


        }
        public void bindDrawable(Drawable drawable) {
            mTweetPortraitImageView.setImageDrawable(drawable);
        }

        public void bindTweetOthers(Tweet tweet){
            mTweet=tweet;

            mAuthorTextView.setText(tweet.getAuthor().toString());
            mTweetBodyTextView.setText(tweet.getTweetBody().toString());
            mTweetTimeView.setText(tweet.getTweetPubDate().toString());
            mCommentView.setText("评论："+tweet.getCommentCount().toString());
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext,
//                    mTweet.getTweetId() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();

//            Intent intent = new Intent(mContext, WebViewActivity.class);
//            intent.putExtra(EXTRA_CRIME_ID, mTweet.getTweetId());
//            intent.putExtra(EXTRA_CRIME_IDENTIFY, "10");
//            mContext.startActivity(intent);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        //Adapter里放模型
        private List<Tweet> mTweets;

        public PhotoAdapter(List<Tweet> tweets) {
            mTweets = tweets;
        }

        /**
         * @param viewGroup
         * @param viewType
         * 这个函数是搞啥嘞？？？？？？？？？？？？
         */
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.example_list_item, viewGroup, false);
            return new PhotoHolder(view);
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Tweet tweet = mTweets.get(position);
//            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
//            photoHolder.bindDrawable(tweet.getPortraitUrl().);
            mPhotoHolderThumbnailDownloader.queueThumbanail(photoHolder, tweet.getPortraitUrl());
            photoHolder.bindTweetOthers(tweet);



        }
        @Override
        public int getItemCount() {
            return mTweets.size();
        }
    }

    //关联adapter
    private void setupAdapter() {
         if (mFragment.isAdded()) {
//            判断该Fragment是否已经与Activity关联
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }
}
