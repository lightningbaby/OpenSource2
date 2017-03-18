package com.opens.android.opensource.User;

import android.content.Context;
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
import com.opens.android.opensource.fetchsource.ThumbnailDownloader;
import com.opens.android.opensource.sum.Sum;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.user_model.User;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ttc on 2017/3/17.
 */

public class UserItemOne {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private Fragment mFragment;
    private static String mString;
    private List<Tweet> mTweetItems = new ArrayList<>();
    private List<User> mUserItems = new ArrayList<>();
    private List<Sum> mBlogItems = new ArrayList<>();
    private ThumbnailDownloader<ItemHolder> mThumbnailDownloader;
    public UserItemOne(RecyclerView recyclerView, Context context, Fragment fragment, String str){
        mRecyclerView=recyclerView;
        mContext=context;
        mFragment=fragment;
        mString=str;
        System.out.println("-----------------------------Item get string"+mString);
        switch (mString){
            case "40":
                new FetchTweetTask().execute();
                Handler responseHandler=new Handler();
                mThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
                mThumbnailDownloader.setThumbnailDownloadListener(
                        new ThumbnailDownloader.ThumbnailDownloadListener<ItemHolder>() {
                            @Override
                            public void onThumbnailDownloaded(ItemHolder holder, Bitmap thumbnail) {
                                Drawable drawable=new BitmapDrawable(mContext.getResources(),thumbnail);
                                holder.bindDrawable(drawable);
                            }
                        }
                );
                mThumbnailDownloader.start();
                mThumbnailDownloader.getLooper();
                Log.i(TAG,"Background thread started");
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                setupAdapter();
                break;
            case "41":
                //null
            case "42":
                new FetchUserTask().execute();
                Handler responseHandler1=new Handler();
                mThumbnailDownloader=new ThumbnailDownloader<>(responseHandler1);
                mThumbnailDownloader.setThumbnailDownloadListener(
                        new ThumbnailDownloader.ThumbnailDownloadListener<ItemHolder>() {
                            @Override
                            public void onThumbnailDownloaded(ItemHolder holder, Bitmap thumbnail) {
                                Drawable drawable=new BitmapDrawable(mContext.getResources(),thumbnail);
                                holder.bindDrawable(drawable);
                            }
                        }
                );
                mThumbnailDownloader.start();
                mThumbnailDownloader.getLooper();
                Log.i(TAG,"Background thread started");
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                setupAdapter();
                break;
            case "43":
                new FetchUserTask().execute();
                Handler responseHandler2=new Handler();
                mThumbnailDownloader=new ThumbnailDownloader<>(responseHandler2);
                mThumbnailDownloader.setThumbnailDownloadListener(
                        new ThumbnailDownloader.ThumbnailDownloadListener<ItemHolder>() {
                            @Override
                            public void onThumbnailDownloaded(ItemHolder holder, Bitmap thumbnail) {
                                Drawable drawable=new BitmapDrawable(mContext.getResources(),thumbnail);
                                holder.bindDrawable(drawable);
                            }
                        }
                );
                mThumbnailDownloader.start();
                mThumbnailDownloader.getLooper();
                Log.i(TAG,"Background thread started");
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                setupAdapter();
                break;
            case "44":
            case "45":
                new FetchBlogTask().execute();
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                setupAdapter();
                break;
        }
    }
    private void setupAdapter(){
        if (mFragment.isAdded()) {
//            判断该Fragment是否已经与Activity关联
            switch (mString){
                case "40":
                    System.out.println("Setting Adapter......");
                    mRecyclerView.setAdapter(new ItemTweetAdapter(mTweetItems));
                    break;
                case "41":
                case "42":
                    System.out.println("Setting Adapter......");
                    mRecyclerView.setAdapter(new ItemUserAdapter(mUserItems));
                    break;
                case "43":
                    mRecyclerView.setAdapter(new ItemUserAdapter(mUserItems));
                    break;
                case "44":
                case "45":
                    mRecyclerView.setAdapter(new ItemBlogAdapter(mBlogItems));
                    break;
            }

        }
    }
    private class FetchTweetTask extends AsyncTask<Void,Void,List<Tweet>> {
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
            mTweetItems = items;
            setupAdapter();
        }
    }
    private class FetchUserTask extends AsyncTask<Void,Void,List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(mString);
            List<User>  item= new ArrayList<>();
            try {
                item =  (List<User>)judgeType.JudgeAndRet();
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
        protected void onPostExecute(List<User> items) {
            mUserItems = items;
            setupAdapter();
        }
    }
    private class FetchBlogTask extends AsyncTask<Void,Void,List<Sum>> {
        @Override
        protected List<Sum> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(mString);
            List<Sum>  item= new ArrayList<>();
            try {
                item =  (List<Sum>)judgeType.JudgeAndRet();
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
        protected void onPostExecute(List<Sum> items) {
            mBlogItems = items;
            setupAdapter();
        }
    }
    public void onDestroy() {
        mThumbnailDownloader.clearQueue();
        mThumbnailDownloader.quit();
        Log.i(TAG,"Backeground thread destroyed");
    }
    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mTweetPortraitImageView;
        private TextView mUserTextView;
        private TextView mTweetBodyTextView;
        private TextView mTweetTimeView;
        private TextView mCommentView;
        private Tweet mTweet;
        private User mUser;
        private Sum mBlog;


        public ItemHolder(View itemView) {
            super(itemView);
            switch (mString){
                case "40":
                    mTweetPortraitImageView = (ImageView) itemView.findViewById(R.id.head);
                    mUserTextView= (TextView) itemView.findViewById(R.id.usr_id);
                    mTweetBodyTextView= (TextView) itemView.findViewById(R.id.title);
                    mTweetTimeView=(TextView)itemView.findViewById(R.id.time);
                    mCommentView=(TextView)itemView.findViewById(R.id.comment);
                    break;
                case "41":
                case "42":
                    mTweetPortraitImageView = (ImageView) itemView.findViewById(R.id.head);
                    mUserTextView= (TextView) itemView.findViewById(R.id.usr_id);
                    break;
                case "43":
                    mTweetPortraitImageView = (ImageView) itemView.findViewById(R.id.head);
                    mUserTextView= (TextView) itemView.findViewById(R.id.usr_id);
                    break;
                case "44":
                case "45":
                    mTweetPortraitImageView = (ImageView) itemView.findViewById(R.id.head);
                    mUserTextView= (TextView) itemView.findViewById(R.id.usr_id);
                    mTweetBodyTextView= (TextView) itemView.findViewById(R.id.title);
                    mTweetTimeView=(TextView)itemView.findViewById(R.id.time);
                    mCommentView=(TextView)itemView.findViewById(R.id.comment);
                    break;
            }
        }
        public void bindDrawable(Drawable drawable) {
            mTweetPortraitImageView.setImageDrawable(drawable);
        }
        public void bindTweetOthers(Tweet tweet){
            mTweet=tweet;
            mUserTextView.setText(tweet.getAuthor().toString());
            mTweetBodyTextView.setText(tweet.getTweetBody().toString());
            mTweetTimeView.setText(tweet.getTweetPubDate().toString());
            mCommentView.setText("评论："+tweet.getCommentCount().toString());
            itemView.setOnClickListener(this);
        }
        public void bindUserOthers(User user){
            mUser=user;
            mUserTextView.setText(user.getUserName().toString());
            itemView.setOnClickListener(this);
        }
        public void bindBlogOthers(Sum blog){
            mBlog=blog;
            mUserTextView.setText(blog.getSumAuthorName().toString());
            mTweetBodyTextView.setText(blog.getSumTitle().toString());
            mTweetTimeView.setText(blog.getSumPubDate().toString());
            mCommentView.setText("评论："+blog.getSumCommentCount().toString());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
    private class ItemTweetAdapter extends RecyclerView.Adapter<ItemHolder>{
        private List<Tweet> mTweets;
        public ItemTweetAdapter(List<Tweet> tweets) {
            mTweets = tweets;   //null
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.example_list_item, parent, false);
            System.out.println("Create View Holder");
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Tweet tweet = mTweets.get(position);
            mThumbnailDownloader.queueThumbanail(holder, tweet.getPortraitUrl());
            holder.bindTweetOthers(tweet);
        }

        @Override
        public int getItemCount() {
            return mTweets.size();
        }
    }
    private class ItemUserAdapter extends RecyclerView.Adapter<ItemHolder>{
        private List<User> mUsers;
        public ItemUserAdapter(List<User> users) {
            mUsers = users;   //null
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.example_list_item, parent, false);
            System.out.println("Create View Holder");
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            User user = mUsers.get(position);
            mThumbnailDownloader.queueThumbanail(holder, user.getUserAvatar());
            holder.bindUserOthers(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
    private class ItemBlogAdapter extends RecyclerView.Adapter<ItemHolder>{
        private List<Sum> mBlogs;
        public ItemBlogAdapter(List<Sum> blogs) {
            mBlogs = blogs;   //null
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.example_list_item, parent, false);
            System.out.println("Create View Holder");
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Sum blog = mBlogs.get(position);
            holder.bindBlogOthers(blog);
        }

        @Override
        public int getItemCount() {
            return mBlogs.size();
        }
    }
}
