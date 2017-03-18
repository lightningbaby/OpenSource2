package com.opens.android.opensource.sum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.opens.android.opensource.R;
import com.opens.android.opensource.api_util.JudgeType;
import com.opens.android.opensource.api_util.WebViewActivity;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/13.
 */

public class SumItem {
    private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";//用于向webView传递url

    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="SumItem";
    private List<Sum> mItems = new ArrayList<>();
    private Context mContext;
    private Fragment mFragment;
    private String bigType;//决定是综合中的哪一块


    public SumItem(RecyclerView mRcycle, Context context, Fragment fragment,String str) {
        mPhotoRecyclerView=mRcycle;
        mContext=context;
        mFragment=fragment;
        bigType=str;
        new FetchItemsTask().execute();
    }

//    public void Destroy() {
//        mPhotoHolderThumbnailDownloader.clearQueue();
//        mPhotoHolderThumbnailDownloader.quit();
//        Log.i(TAG,"Backeground thread destroyed");
//    }


    /**
     *后台任务、下载数据
     */
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
        private RollPagerView mRollViewPager;
        private ImageView mImagej;
        private ImageView mImagey;
        private View view ;


        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title_text_view);
            mAbstractTextView= (TextView) itemView.findViewById(R.id.abstract_text_view);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.author_text_view);
            mDateTextView= (TextView) itemView.findViewById(R.id.date_text_view);
            mCommentTextView= (TextView) itemView.findViewById(R.id.comment_text_view);
            mRollViewPager= (RollPagerView) itemView.findViewById(R.id.roll_view_pager);
            mImageView=(ImageView)itemView.findViewById(R.id.pinglun);
            mImagej=(ImageView)itemView.findViewById(R.id.today_image_view);
            mImagey=(ImageView)itemView.findViewById(R.id.yuan);
            view=(View)itemView.findViewById(R.id.view_id);
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
        public void bindCycleView(){
            mRollViewPager.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.GONE);
            mAuthorTextView.setVisibility(View.GONE);
            mDateTextView.setVisibility(View.GONE);
            mCommentTextView.setVisibility(View.GONE);
            mAbstractTextView.setVisibility(View.GONE);
            mImageView.setVisibility(View.GONE);
            mImagej.setVisibility(View.GONE);
            mImagey.setVisibility(View.GONE);
            view.setVisibility(View.GONE);

            //设置播放时间间隔
            mRollViewPager.setPlayDelay(1000);
            //设置透明度
            mRollViewPager.setAnimationDurtion(500);
            //设置适配器
            mRollViewPager.setAdapter(new TestNormalAdapter());

            //设置指示器（顺序依次）
            //自定义指示器图片
            //设置圆点指示器颜色
            //设置文字指示器
            //隐藏指示器
           // mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
            mRollViewPager.setHintView(new ColorPointHintView(mContext, Color.YELLOW,Color.WHITE));
            //mRollViewPager.setHintView(new TextHintView(mContext));
           // mRollViewPager.setHintView("*****************");

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
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
            if(position==0 ){
                holder.bindCycleView();//如果是第一个位置，就为它绑定 cycleViewPager
            }else{
                Sum sum=mSumList.get(position);
                holder.bindSumOthers(sum); // 其它位置，正常绑定数据
            }
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

    private class TestNormalAdapter extends StaticPagerAdapter {

        //推荐博客 轮播图片
        private int[] imgs = {
                R.drawable.bg_topic_12,
                R.drawable.bg_topic_22,
                R.drawable.bg_topic_32,
                R.drawable.bg_topic_42,
                R.drawable.bg_topic_52
        };

        //开源资讯 轮播图片
        private int[] imgs1 = {
                R.drawable.bg_topic_11,
                R.drawable.bg_topic_21,
                R.drawable.bg_topic_31,
                R.drawable.bg_topic_41,
                R.drawable.bg_topic_51
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());

            // "00"表示 开源资讯
            if(bigType.equals("00")){
                view.setImageResource(imgs1[position]);
            }
            else{
                view.setImageResource(imgs[position]);
            }

            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
