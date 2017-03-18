package com.opens.android.opensource.sum;


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

import com.opens.android.opensource.R;
import com.opens.android.opensource.api_util.JudgeType;
import com.opens.android.opensource.api_util.WebViewActivity;
import com.opens.android.opensource.fetchsource.ThumbnailDownloader;
import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/14.
 */


public class TechQAItem{

    private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";////用于向webView传递url

    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="TechQAItem";
    private List<Tweet> mItems = new ArrayList<>();
    private ThumbnailDownloader<TechQAItem.PhotoHolder> mPhotoHolderThumbnailDownloader;
    private Context mContext;
    private Fragment mFragment;
    private String bigType;//决定是综合中技术问答还是职业生涯





    public TechQAItem(RecyclerView mRcycle, Context context, Fragment fragment,String str){
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        bigType=str;

        new FetchItemsTask().execute();


        //消息循环读取图片
        Handler responseHandler=new Handler();
        mPhotoHolderThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        mPhotoHolderThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap thumbnail) {
                        Drawable drawable=new BitmapDrawable(mFragment.getResources(),thumbnail);
                        photoHolder.bindDrawable(drawable);
                    }
                }
        );


        //开始消息循环
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

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Tweet>> {
        @Override
        protected List<Tweet> doInBackground(Void... voids) {
            JudgeType judgeType = new JudgeType(bigType);
            List<Tweet> item = new ArrayList<>();
            try {
                item = (List<Tweet>) judgeType.JudgeAndRet();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return item;
        }

        @Override
        protected void onPostExecute(List<Tweet> tweets) {
            mItems = tweets;
            setupAdapter();
        }
    }
    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mTechPortraitImageView;
        private TextView mTitleTextView;
        private TextView mTechQABodyTextView;
        private TextView mTechAuthorView;
        private TextView mDateTextView;
        private TextView mCommentView;
        private Tweet mTweet;

        public PhotoHolder(View itemView){
            super(itemView);
            mTechPortraitImageView=(ImageView) itemView.findViewById(R.id.author_portrait_image_view);
            mTitleTextView= (TextView) itemView.findViewById(R.id.title_text_view);
            mTechQABodyTextView= (TextView) itemView.findViewById(R.id.tech_qa_body_text_view);
            mTechAuthorView=(TextView)itemView.findViewById(R.id.author_text_view);
            mDateTextView=(TextView)itemView.findViewById(R.id.date_text_view);
            mCommentView=(TextView)itemView.findViewById(R.id.comment_text_view);

        }
        public void bindDrawable(Drawable drawable) {
            mTechPortraitImageView.setImageDrawable(drawable);

        }
        public void bindTweetOthers(Tweet tweet){
            mTweet=tweet;
            mTitleTextView.setText(tweet.getTweetTitle().toString());
            mTechAuthorView.setText(tweet.getTweetAuthorName().toString());
            mTechQABodyTextView.setText(tweet.getTweetBody().toString());
            mDateTextView.setText(tweet.getTweetPubDate().toString());
            mCommentView.setText("评论："+tweet.getCommentCount().toString());
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext,
//                    mTweet.getTweetId() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();

            Intent intent = new Intent(mContext, WebViewActivity.class);
//            intent.putExtra(EXTRA_CRIME_ID, mSoftware.getName());
//            intent.putExtra(EXTRA_CRIME_IDENTIFY, "31");
            intent.putExtra(EXTRA_CRIME_URL,mTweet.getTweetDetailUrl());
            mContext.startActivity(intent);
        }
    }
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<Tweet> mTweets;

        public PhotoAdapter(List<Tweet> tweets) {
            mTweets = tweets;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.tech_qa_list, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Tweet tweet = mTweets.get(position);
//            Drawable placeholder = getResources().getDrawable(R.drawable.bill_up_close);
//            photoHolder.bindDrawable(tweet.getPortraitUrl());
            mPhotoHolderThumbnailDownloader.queueThumbanail(photoHolder, tweet.getPortraitUrl());
            photoHolder.bindTweetOthers(tweet);
        }

        @Override
        public int getItemCount() {
            return mTweets.size();
        }
    }
    private void setupAdapter() {
        if (mFragment.isAdded()) {
//            判断该Fragment是否已经与Activity关联
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }
}