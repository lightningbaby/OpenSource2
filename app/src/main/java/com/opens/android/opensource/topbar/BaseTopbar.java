package com.opens.android.opensource.topbar;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.opens.android.opensource.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ttc on 2017/3/12.
 */

public abstract class BaseTopbar {
    private HorizontalScrollMenu hsm_container;
    private String[] names;
    Activity mActivity_basetopbar;

    public BaseTopbar(String[] str, Activity content){
        names = str;
        mActivity_basetopbar=content;
    }


    public  void initView()
    {
        hsm_container.setSwiped(true);
        hsm_container.setAdapter(new MenuAdapter(names,mActivity_basetopbar));
    }

    class MenuAdapter extends BaseAdapter
    {
        String[] names;
        Activity mActivity;

        public MenuAdapter(  String[] str ,Activity content){
            names= str;
            mActivity=content;
        }

        @Override
        public List<String> getMenuItems()
        {
            // TODO Auto-generated method stub
            return Arrays.asList(names);
        }

        @Override
        public List<View> getContentViews()
        {
            // TODO Auto-generated method stub
            List<View> views = new ArrayList<View>();
            for (String str : names)
            {
                View v = LayoutInflater.from(mActivity).inflate(
                        R.layout.fragment_recycleview, null);
                RecyclerView tv = (RecyclerView) v.findViewById(R.id.example_recycler_view);
                views.add(v);
            }
            return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus)
        {
//            // TODO Auto-generated method stub
//            Toast.makeText(MainActivity.this,
//                    "内容页：" + (position + 1) + " 访问状态：" + visitStatus,
//                    Toast.LENGTH_SHORT).show();
        }

    }


}
