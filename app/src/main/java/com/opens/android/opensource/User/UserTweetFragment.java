package com.opens.android.opensource.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opens.android.opensource.R;

/**
 * Created by ttc on 2017/3/16.
 */

public class UserTweetFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TweetAdapter mTweetAdapter;
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.recycler_view_list,container,false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        init();
        return view;
    }
    private void init(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTweetAdapter=new TweetAdapter();
        mRecyclerView.setAdapter(mTweetAdapter);
    }
    private class TweetHolder extends RecyclerView.ViewHolder{

        public TweetHolder(View itemView) {
            super(itemView);
        }
    }
    public class TweetAdapter extends RecyclerView.Adapter<TweetHolder>{

        @Override
        public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            View view=layoutInflater.inflate(R.layout.example_list_item,parent,false);
            return new TweetHolder(view);
        }

        @Override
        public void onBindViewHolder(TweetHolder holder, int position) {
            //set
        }

        @Override
        public int getItemCount() {
            //list size
            return 0;
        }
    }
}
