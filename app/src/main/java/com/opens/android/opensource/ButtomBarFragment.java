package com.opens.android.opensource;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by ttc on 2017/3/12.
 */

public class ButtomBarFragment extends Fragment implements View.OnClickListener {
    private Callbacks mCallbacks;
    //定义底部导航栏的三个布局
    private RelativeLayout news_layout;
    private RelativeLayout discover_layout;
    private RelativeLayout tweet_layout;
    private RelativeLayout my_layout;

  //  private Enum NEWS,TWEET,DISCOVER,MINE;


    public interface Callbacks {
        void onItemSelected(int num);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buttom_bar, container, false);
        news_layout = (RelativeLayout) v.findViewById(R.id.news_layout);
        tweet_layout = (RelativeLayout) v.findViewById(R.id.tweet_layout);
        discover_layout = (RelativeLayout) v.findViewById(R.id.discover_layout);
        my_layout = (RelativeLayout) v.findViewById(R.id.my_layout);

        news_layout.setOnClickListener(this);
        tweet_layout.setOnClickListener(this);
        discover_layout.setOnClickListener(this);
        my_layout.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View view) {

        int position = 0;
        switch (view.getId())

        {
            case R.id.news_layout:
                position = 0;
                break;
            case R.id.tweet_layout:
                position = 1;
                break;
            case R.id.discover_layout:
                position = 2;
                break;
            case R.id.my_layout:
                position = 3;
                break;
            default:
                position = 0;
                break;
        }
        mCallbacks.onItemSelected(position);
    }
}

