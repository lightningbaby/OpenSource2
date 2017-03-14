package com.opens.android.opensource;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.opens.android.opensource.discover.DiscoverFragment;
import com.opens.android.opensource.mine.MineFragment;
import com.opens.android.opensource.news.NewsFragment;
import com.opens.android.opensource.sum.SumFragment;
import com.opens.android.opensource.tweet.AddTweetActivity;
import com.opens.android.opensource.tweet.TweetFragment;

public class MainActivity extends AppCompatActivity implements ButtomBarFragment.Callbacks{
    Fragment newFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_bottombar);
        if (fragment == null) {
            fragment = new ButtomBarFragment();
            fm.beginTransaction()
                    .add(R.id.container_bottombar, fragment)
                    .commit();
        }
        newFragment = new SumFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_top, newFragment)
                .commit();
    }
    @Override
    public void onItemSelected(int num) {
        switch (num){
            case 0:
                newFragment=new SumFragment();//综合
                break;
            case 1:
                newFragment=new NewsFragment();//动弹
                break;
            case 2:
                newFragment =new DiscoverFragment();
                break;
            case 3:
                newFragment = new MineFragment();
                break;
            case 4:
                Intent intent=new Intent(MainActivity.this, AddTweetActivity.class);
                startActivity(intent);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_top, newFragment)
                .commit();
        Log.v("onItemSelected",""+num);
    }

}
