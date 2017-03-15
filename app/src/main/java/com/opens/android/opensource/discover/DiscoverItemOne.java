package com.opens.android.opensource.discover;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by ttc on 2017/3/14.
 */

public class DiscoverItemOne {

    private String EXTRA_CRIME_ID ="EXTRA_CRIME_ID";
    private String EXTRA_CRIME_IDENTIFY ="EXTRA_CRIME_IDENTIFY";
    private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";


    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="NewsItemOne";
    private List<Software> mItems = new ArrayList<>();
    private ThumbnailDownloader<PhotoHolder> mPhotoHolderThumbnailDownloader;
    private Context mContext;
    private Fragment mFragment;
    private String mString;


    public DiscoverItemOne(RecyclerView mRcycle, Context context, Fragment fragment, String str){
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        mString=str;


        new FetchItemsTask().execute();

        Handler responseHandler=new Handler();
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
    private class FetchItemsTask extends AsyncTask<Void,Void,List<Software>> {
        @Override
        protected List<Software> doInBackground(Void... voids) {
            JudgeType judgeType=new JudgeType(mString);
            List<Software>  item= new ArrayList<>();
            try {
                item =  (List<Software>)judgeType.JudgeAndRet();
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
        protected void onPostExecute(List<Software> items) {
            mItems = items;
            setupAdapter();
        }
    }

    //set view
    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView kinditem;
        private Software mSoftware;


        public PhotoHolder(View itemView) {
            super(itemView);
            kinditem= (TextView) itemView.findViewById(R.id.software_kind_name);
        }
        public void bindSoftOthers(Software software){
            mSoftware=software;
            kinditem.setText(software.getName().toString());
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext,
//                    mTweet.getTweetId() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();

//            Intent intent = new Intent(mContext, WebViewActivity.class);
//            intent.putExtra(EXTRA_CRIME_ID, mSoftware.getName());
//            intent.putExtra(EXTRA_CRIME_IDENTIFY, "30");
//            mContext.startActivity(intent);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        //Adapter里放模型
        private List<Software> mSoftwares;

        public PhotoAdapter(List<Software> softwares) {
            mSoftwares = softwares;
        }

        /**
         * @param viewGroup
         * @param viewType
         * 这个函数是搞啥嘞？？？？？？？？？？？？
         */
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.software_type, viewGroup, false);
            return new PhotoHolder(view);
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            Software soft = mSoftwares.get(position);
            photoHolder.bindSoftOthers(soft);



        }
        @Override
        public int getItemCount() {
            return mSoftwares.size();
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
