package com.opens.android.opensource.sum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opens.android.opensource.R;
import com.opens.android.opensource.topbar.BaseAdapter;
import com.opens.android.opensource.topbar.HorizontalScrollMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ttc on 2017/3/13.
 */

public class SumFragment extends Fragment {
    private HorizontalScrollMenu hsm_container;

    private List<RecyclerView> mPhotoRecyclerView=new ArrayList<RecyclerView>();
    private static final String TAG="SumFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//need to learn more
        Log.i(TAG,"Background thread started");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_news,container,false);
        hsm_container = (HorizontalScrollMenu) v.findViewById(R.id.hsm_container);
        hsm_container.setSwiped(true);
        hsm_container.setAdapter(new MenuAdapter());
        Log.v("sum","*****************");
        return v;
    }

    class MenuAdapter extends BaseAdapter{
        String[] names = new String[]
                { "菜单一", "菜单二", "菜单三", "菜单四", "菜单五", "菜单六", "菜单七","菜单八" };

        @Override
        public List<String> getMenuItems() {
            return Arrays.asList(names);
        }

        @Override
        public List<View> getContentViews() {
            List<View> views = new ArrayList<View>();

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
                        new SumItem(recy,getActivity(),SumFragment.this,"00");//
                        break;
                    case 1:
                        new SumItem(recy,getActivity(),SumFragment.this,"01");//
                        break;
                    case 2:
                        new SumItem(recy,getActivity(),SumFragment.this,"02");//
                        break;
                }
            }
            return views;
        }

        @Override
        public void onPageChanged(int position, boolean visitStatus) {

        }
    }


}
