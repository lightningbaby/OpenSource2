package com.opens.android.opensource;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opens.android.opensource.api_util.WebViewActivity;
import com.opens.android.opensource.tweet.Tweet;

import java.util.List;

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
    private RelativeLayout add_layout;
    private ImageView news_image;
    private ImageView tweet_image;
    private ImageView discover_image;
    private ImageView my_image;

    private TextView news_text;
    private TextView tweet_text;
    private TextView discover_text;
    private TextView my_text;


    private  String TAG="ButtomBarFragment";
    private String SEARCH_KEY="SEARCH_KEY";

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.main_search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                if(!s.isEmpty()) {
                    Intent intent = new Intent(getActivity(), SerachResultActivity.class);
                    intent.putExtra(SEARCH_KEY,s);
                    startActivity(intent);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buttom_bar, container, false);
        news_layout = (RelativeLayout) v.findViewById(R.id.news_layout);
        tweet_layout = (RelativeLayout) v.findViewById(R.id.tweet_layout);
        discover_layout = (RelativeLayout) v.findViewById(R.id.discover_layout);
        my_layout = (RelativeLayout) v.findViewById(R.id.my_layout);
        add_layout=(RelativeLayout)v.findViewById(R.id.add_layout);

        news_image= (ImageView) v.findViewById(R.id.news_image);
        tweet_image= (ImageView) v.findViewById(R.id.tweet_image);
        discover_image = (ImageView) v.findViewById(R.id.discover_image);
        my_image= (ImageView) v.findViewById(R.id.discover_image);

        news_text= (TextView) v.findViewById(R.id.news_text);
        tweet_text= (TextView) v.findViewById(R.id.tweet_text);
        discover_text = (TextView) v.findViewById(R.id.discover_text);
        my_text= (TextView) v.findViewById(R.id.my_text);

        news_layout.setOnClickListener(this);
        tweet_layout.setOnClickListener(this);
        discover_layout.setOnClickListener(this);
        my_layout.setOnClickListener(this);
        add_layout.setOnClickListener(this);




        return v;
    }
    @Override
    public void onClick(View view) {

        int position = 0;
        clean();
        switch (view.getId())

        {
            case R.id.news_layout:
                news_image.setImageResource(R.drawable.ic_nav_news_actived);
                news_text.setTextColor(this.getResources().getColor(R.color.green_24cf5f));
                position = 0;
                break;
            case R.id.tweet_layout:
                tweet_image.setImageResource(R.drawable.ic_nav_tweet_actived);
                tweet_text.setTextColor(this.getResources().getColor(R.color.green_24cf5f));
                position = 1;
                break;
            case R.id.discover_layout:
                discover_image.setImageResource(R.drawable.ic_nav_discover_actived);
                discover_text.setTextColor(this.getResources().getColor(R.color.green_24cf5f));
                position = 2;
                break;
            case R.id.my_layout:
                my_image.setImageResource(R.drawable.ic_nav_my_pressed);
                my_text.setTextColor(this.getResources().getColor(R.color.green_24cf5f));
                position = 3;
                break;
            case R.id.add_layout:
                position=4;
                break;
            default:
                position = 0;
                break;
        }
        mCallbacks.onItemSelected(position);
    }

    public void clean(){
        news_image.setImageResource(R.drawable.ic_nav_news_normal);
        tweet_image.setImageResource(R.drawable.ic_nav_tweet_normal);
        discover_image.setImageResource(R.drawable.ic_nav_discover_normal);
        my_image.setImageResource(R.drawable.ic_nav_my_normal);

        news_text.setTextColor(this.getResources().getColor(R.color.gray_2f2e35));
        tweet_text.setTextColor(this.getResources().getColor(R.color.gray_2f2e35));
        discover_text.setTextColor(this.getResources().getColor(R.color.gray_2f2e35));
        my_text.setTextColor(this.getResources().getColor(R.color.gray_2f2e35));

    }

}

