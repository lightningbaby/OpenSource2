package com.opens.android.opensource.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.opens.android.opensource.R;

/**
 * Created by ttc on 2017/3/16.
 */

public class UserListActivity extends FragmentActivity {
    public static int mposition=0;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        if(fragment==null){
            fragment=newFragment(mposition);
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
    public static Intent newIntent(Context context,int position){
        Intent intent =new Intent(context,UserListActivity.class);
        mposition=position;
        System.out.println("User List Get:"+mposition);
        return intent;
    }
    public Fragment newFragment(int position){
        return UserListFragment.newInstance(position);
    }
}
