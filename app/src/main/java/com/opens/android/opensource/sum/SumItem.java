package com.opens.android.opensource.sum;

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


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/13.
 */

public class SumItem {
    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="SumItem";
    private List<Sum> mItems = new ArrayList<>();
//    private ThumbnailDownloader<SumItem.PhotoHolder> mPhotoHolderThumbnailDownloader;
    private Context mContext;
    private Fragment mFragment;
    private String bigType;//决定是综合中的哪一块

    public SumItem(RecyclerView mRcycle, Context context, Fragment fragment,String str) {
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        bigType=str;

        new FetchItemsTask().execute();

//        Handler responseHandler=new Handler();
//        mPhotoHolderThumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
//        mPhotoHolderThumbnailDownloader.setThumbnailDownloadListener(
//                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
//                    @Override
//                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap thumbnail) {
//                        Drawable drawable=new BitmapDrawable(mFragment.getResources(),thumbnail);
////                        photoHolder.bindDrawable(drawable);//因为没有图片
//                    }
//                }
//        );
//        mPhotoHolderThumbnailDownloader.start();
//        mPhotoHolderThumbnailDownloader.getLooper();
//        Log.i(TAG,"Background thread started");

        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        setupAdapter();
    }

//    public void Destroy() {
//        mPhotoHolderThumbnailDownloader.clearQueue();
//        mPhotoHolderThumbnailDownloader.quit();
//        Log.i(TAG,"Backeground thread destroyed");
//    }

    private class FetchItemsTask extends AsyncTask<Void,Void,List<Sum>> {
        @Override
        protected List<Sum> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(bigType);
            List<Sum>  item= null;
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
    private class PhotoHolder extends RecyclerView.ViewHolder{

        private TextView mTitleTextView;
        private TextView mAbstractTextView;
        private TextView mAuthorTextView;
        private TextView mDateTextView;
        private TextView mCommentTextView;

        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title_text_view);
            mAbstractTextView= (TextView) itemView.findViewById(R.id.abstract_text_view);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.author_text_view);
            mDateTextView= (TextView) itemView.findViewById(R.id.date_text_view);
            mCommentTextView= (TextView) itemView.findViewById(R.id.comment_text_view);
        }

        public void bindDrawable(Drawable drawable) {
//            mTweetPortraitImageView.setImageDrawable(drawable);

        }
        public void bindSumOthers(Sum sum){

            mTitleTextView.setText(sum.getSumTitle().toString());
            mAuthorTextView.setText("@"+sum.getSumAuthorName().toString());
            mDateTextView.setText(sum.getSumPubDate().toString());
            mCommentTextView.setText("评论"+sum.getSumCommentCount().toString());
        }

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<Sum> mSumList;

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
    }
}
