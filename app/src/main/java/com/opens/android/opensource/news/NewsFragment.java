package com.opens.android.opensource.news;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
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
import com.opens.android.opensource.fetchsource.ThumbnailDownloader;
import com.opens.android.opensource.topbar.BaseAdapter;
import com.opens.android.opensource.topbar.HorizontalScrollMenu;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.tweet.TweetFetch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ttc on 2017/3/12.
 */

public class NewsFragment  extends Fragment{
    private TextView mTextView;
    private HorizontalScrollMenu hsm_container;


    /****>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*****************/



    private List<RecyclerView> mPhotoRecyclerView=new ArrayList<RecyclerView>();
    private static final String TAG="TweetFetchFragment";
    private List<Tweet> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mPhotoHolderThumbnailDownloader;


     /*************<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*********/

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//need to learn more

        /***>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*************/
         new FetchItemsTask().execute();

         Handler responseHandler=new Handler();

         mPhotoHolderThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
         mPhotoHolderThumbnailDownloader.setThumbnailDownloadListener(
         new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
        @Override
        public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap thumbnail) {
        Drawable drawable=new BitmapDrawable(getResources(),thumbnail);
        photoHolder.bindDrawable(drawable);
            }
        });
         mPhotoHolderThumbnailDownloader.start();
         mPhotoHolderThumbnailDownloader.getLooper();
         Log.i(TAG,"Background thread started");
        /************* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<**/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news,container,false);
    hsm_container = (HorizontalScrollMenu) v.findViewById(R.id.hsm_container);
    hsm_container.setSwiped(true);
    hsm_container.setAdapter(new MenuAdapter());

    Log.v("news","*****************");
    //Toast.makeText(this.getActivity(),"news",Toast.LENGTH_SHORT);
    return v;
}


class MenuAdapter extends BaseAdapter
    {
        String[] names = new String[]
                { "菜单一", "菜单二", "菜单三", "菜单四", "菜单五", "菜单六", "菜单七","菜单八" };

        @Override
        public List<String> getMenuItems()
        {
            // TODO Auto-generated method stub
            return Arrays.asList(names);
        }

        @Override
        public List<View> getContentViews()
        {
            List<View> views = new ArrayList<View>();
            // TODO Auto-generated method stub

            for (String str : names)
            {
                View v = LayoutInflater.from(getActivity()).inflate(
                        R.layout.content_view, null);
                TextView tv = (TextView) v.findViewById(R.id.tv_content);
                RecyclerView Recycle=(RecyclerView)v.findViewById(R.id.crime_recycler_view);
                mPhotoRecyclerView.add(Recycle);
                tv.setText(str);
                views.add(v);
            }
            for( RecyclerView recy:mPhotoRecyclerView){
                recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                setupAdapter(recy);
            }
            return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus)
        {
//            // TODO Auto-generated method stub
//            Toast.makeText(getActivity(),
//                    "内容页：" + (position + 1) + " 访问状态：" + visitStatus,
//                    Toast.LENGTH_SHORT).show();

        }

    }



    /****>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*****/






    private class FetchItemsTask extends AsyncTask<Void,Void,List<Tweet>> {
        @Override
        protected List<Tweet> doInBackground(Void... voids) {
//
            return new TweetFetch().fetchItems();
        }


        @Override
        protected void onPostExecute(List<Tweet> items) {
            mItems = items;
            for( RecyclerView recy:mPhotoRecyclerView){
                recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                setupAdapter(recy);
            }

        }
    }

    //set view
    private class PhotoHolder extends RecyclerView.ViewHolder {
        //VH 里放 View组件
        private ImageView mTweetPortraitImageView;
        private TextView mAuthorTextView;
        private TextView mTweetBodyTextView;
        private TextView mTweetTimeView;
        private TextView mCommentView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTweetPortraitImageView = (ImageView) itemView
                    .findViewById(R.id.head);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.usr_id);
            mTweetBodyTextView= (TextView) itemView.findViewById(R.id.title);
            mTweetTimeView=(TextView)itemView.findViewById(R.id.time);
            mCommentView=(TextView)itemView.findViewById(R.id.comment);


        }
        public void bindDrawable(Drawable drawable) {
            mTweetPortraitImageView.setImageDrawable(drawable);

        }
        public void bindTweetOthers(Tweet tweet){
            mAuthorTextView.setText(tweet.getAuthor().toString());
            mTweetBodyTextView.setText(tweet.getTweetBody().toString());
            mTweetTimeView.setText(tweet.getTweetPubDate().toString());
            mCommentView.setText("评论："+tweet.getCommentCount().toString());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        //Adapter里放模型
        private List<Tweet> mTweets;

        public PhotoAdapter(List<Tweet> tweets) {
            mTweets = tweets;
        }


        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
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
    private void setupAdapter(RecyclerView mRecy) {
        if (isAdded()) {
//            判断该Fragment是否已经与Activity关联
            mRecy.setAdapter(new PhotoAdapter(mItems));
        }
    }

     /***<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<*****/



}
