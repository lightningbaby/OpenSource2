package com.opens.android.opensource.news;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opens.android.opensource.R;
import com.opens.android.opensource.fetchsource.ThumbnailDownloader;
import com.opens.android.opensource.topbar.BaseAdapter;
import com.opens.android.opensource.topbar.HorizontalScrollMenu;
import com.opens.android.opensource.tweet.Tweet;
import com.opens.android.opensource.tweet.TweetFetch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ttc on 2017/3/12.
 */

public class NewsFragment  extends Fragment{
    private HorizontalScrollMenu hsm_container;

    private List<RecyclerView> mPhotoRecyclerView=new ArrayList<RecyclerView>();
    private static final String TAG="TweetFetchFragment";



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//need to learn more
        Log.i(TAG,"Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news,container,false);
        hsm_container = (HorizontalScrollMenu) v.findViewById(R.id.hsm_container);
        hsm_container.setSwiped(true);
        hsm_container.setAdapter(new MenuAdapter());
        Log.v("news","*****************");
        return v;
    }


    class MenuAdapter extends BaseAdapter
    {
        String[] names = new String[]
                { "菜单一", "菜单二", "菜单三", "菜单四", "菜单五", "菜单六", "菜单七","菜单八" };

        @Override
        public List<String> getMenuItems()
        {
            // TODO Auto-generated method stub
            return Arrays.asList(names);
        }

        @Override
        public List<View> getContentViews()
        {
            List<View> views = new ArrayList<View>();
            // TODO Auto-generated method stub

            for (String str : names)
            {
                View v = LayoutInflater.from(getActivity()).inflate(
                        R.layout.content_view, null);
                RecyclerView Recycle=(RecyclerView)v.findViewById(R.id.crime_recycler_view);
                mPhotoRecyclerView.add(Recycle);
                views.add(v);
            }
            int size = mPhotoRecyclerView.size();
            for( int i=0;i<size;i++){
                RecyclerView recy =mPhotoRecyclerView.get(i);
                recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                switch(i){
                    case 0:
                        new NewItemOne(recy,getActivity(),NewsFragment.this);
                        break;
                    case 1:
                        new NewItemOne(recy,getActivity(),NewsFragment.this);
                        break;
                    case 3:
                        break;
                }


            }
            return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus)
        {
//            // TODO Auto-generated method stub
//            Toast.makeText(getActivity(),
//                    "内容页：" + (position + 1) + " 访问状态：" + visitStatus,
//                    Toast.LENGTH_SHORT).show();

        }
    }
}
