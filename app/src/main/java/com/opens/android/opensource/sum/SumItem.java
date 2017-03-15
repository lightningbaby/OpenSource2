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


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/13.
 */

public class SumItem {
    //    private String EXTRA_CRIME_ID ="EXTRA_CRIME_ID";
//    private String EXTRA_CRIME_IDENTIFY ="EXTRA_CRIME_IDENTIFY";
    private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";

    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="SumItem";
    private List<Sum> mItems = new ArrayList<>();
    private ThumbnailDownloader<SumItem.PhotoHolder> mPhotoHolderThumbnailDownloader;
    private Context mContext;
    private Fragment mFragment;
    private String bigType;//决定是综合中的哪一块

    public SumItem(RecyclerView mRcycle, Context context, Fragment fragment,String str) {
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        bigType=str;

        new FetchItemsTask().execute();


        Handler responseHandler=new Handler();
        mPhotoHolderThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        mPhotoHolderThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap thumbnail) {
                        Drawable drawable=new BitmapDrawable(mFragment.getResources(),thumbnail);
//                        photoHolder.bindDrawable(drawable);//因为没有图片
                    }
                }
        );
        mPhotoHolderThumbnailDownloader.start();
        mPhotoHolderThumbnailDownloader.getLooper();
        Log.i(TAG,"Background thread started");

        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
       //setupAdapter();
    }

    public void Destroy() {
        mPhotoHolderThumbnailDownloader.clearQueue();
        mPhotoHolderThumbnailDownloader.quit();
        Log.i(TAG,"Backeground thread destroyed");
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Sum>> {
        @Override
        protected List<Sum> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(bigType);
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
            mItems = items;
            setupAdapter();
        }
    }
    private class PhotoHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mAbstractTextView;
        private TextView mAuthorTextView;
        private TextView mDateTextView;
        private TextView mCommentTextView;
        private ImageView mImageView;
        private Sum mSum;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title_text_view);
            mAbstractTextView= (TextView) itemView.findViewById(R.id.abstract_text_view);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.author_text_view);
            mDateTextView= (TextView) itemView.findViewById(R.id.date_text_view);
            mCommentTextView= (TextView) itemView.findViewById(R.id.comment_text_view);
        }

        public void bindDrawable(Drawable drawable) {
//          mTweetPortraitImageView.setImageDrawable(drawable);

        }
        public void bindSumOthers(Sum sum){
            mSum=sum;

            mTitleTextView.setText(sum.getSumTitle().toString());
            mAuthorTextView.setText("@"+sum.getSumAuthorName().toString());
            mDateTextView.setText(sum.getSumPubDate().toString());
            mCommentTextView.setText("评论 "+sum.getSumCommentCount().toString());
            mAbstractTextView.setText(sum.getSumBody().toString());
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
            intent.putExtra(EXTRA_CRIME_URL,mSum.getSumDetailUrl());
            mContext.startActivity(intent);
        }

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<Sum> mSumList = new ArrayList<>();

        public PhotoAdapter(List<Sum> sumList) {
            mSumList = sumList;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.sum_list_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
           Sum sum=mSumList.get(position);
//            String url="https://static.oschina.net/uploads/user/0/1_50.jpg?t=1389148177000";
//            mPhotoHolderThumbnailDownloader.queueThumbanail(holder,url);
           holder.bindSumOthers(sum);
        }

        @Override
        public int getItemCount() {
            return mSumList.size();
        }
    }
    private void setupAdapter(){
        if(mFragment.isAdded()){
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
        //mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
    }
}
