package com.opens.android.opensource;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opens.android.opensource.api_util.Api;
import com.opens.android.opensource.api_util.FetchJson;
import com.opens.android.opensource.api_util.JudgeType;
import com.opens.android.opensource.api_util.ParseJson;
import com.opens.android.opensource.api_util.WebViewActivity;
import com.opens.android.opensource.discover.DiscoverItemOne;
import com.opens.android.opensource.tweet.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttc on 2017/3/16.
 */

public class SerachResultActivity  extends AppCompatActivity {
    private String search_key;
    private String SEARCH_KEY="SEARCH_KEY";
    private RecyclerView mPhotoRecyclerView;
    private static final String TAG="NewsItemOne";
    private List<Tweet> mItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        search_key = (String)this.getIntent().getSerializableExtra(SEARCH_KEY);
        setContentView(R.layout.activity_serach_result);
        mPhotoRecyclerView=(RecyclerView)findViewById(R.id.serach_result_list);
        new  FetchItemsTask().execute();
        Handler responseHandler=new Handler();
        mPhotoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
    }


    private class FetchItemsTask extends AsyncTask<Void,Void,List<Tweet>> {
        private List<Tweet> item;
        @Override
        protected List<Tweet> doInBackground(Void... voids) {
            Api api=new Api();
            JSONObject jsonBody= null;
            try {
                jsonBody = new FetchJson(api.getSearchNewsApi(search_key)).getUrlString();
                item= new ParseJson(jsonBody).parseSearchNewsResult();
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
        protected void onPostExecute(List<Tweet> items) {
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
        private Tweet mTweet;

        private String EXTRA_CRIME_URL ="EXTRA_CRIME_URL";

        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView=(TextView)itemView.findViewById(R.id.title_text_view);
            mAbstractTextView= (TextView) itemView.findViewById(R.id.abstract_text_view);
            mAuthorTextView= (TextView) itemView.findViewById(R.id.author_text_view);
            mDateTextView= (TextView) itemView.findViewById(R.id.date_text_view);
            mCommentTextView= (TextView) itemView.findViewById(R.id.comment_text_view);
        }

        public void bindSumOthers( Tweet tweet){
            mTweet=tweet;

            mTitleTextView.setText(tweet.getTweetTitle().toString());
            mAuthorTextView.setText("@"+tweet.getTweetAuthorName().toString());
            mDateTextView.setText(tweet.getTweetPubDate().toString());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SerachResultActivity.this, WebViewActivity.class);
            intent.putExtra(EXTRA_CRIME_URL,mTweet.getPortraitUrl());
            startActivity(intent);
        }

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<Tweet> mSumList = new ArrayList<>();

        public PhotoAdapter(List<Tweet> sumList) {
            mSumList = sumList;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(SerachResultActivity.this);
            View view = inflater.inflate(R.layout.sum_list_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            Tweet sum=mSumList.get(position);
            holder.bindSumOthers(sum);
        }

        @Override
        public int getItemCount() {
            return mSumList.size();
        }
    }
    private void setupAdapter(){
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
    }
}
