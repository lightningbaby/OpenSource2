package com.opens.android.opensource.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opens.android.opensource.R;

/**
 * Created by ttc on 2017/3/17.
 */

public class UserListFragment extends Fragment {
    public static int mPosition;
    private RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.recycler_view_list,container,false);
        Log.v("ListFragment",mPosition+"*****************");
        mRecyclerView=(RecyclerView)v.findViewById(R.id.recycler_view);
        init(mPosition);
        return v;
    }
    public static UserListFragment newInstance(int position){
        mPosition=position;
        return new UserListFragment();
    }
    public void init(int position){
        switch (position){
            case 40:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"40");
                break;
            case 41:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"41");
                break;
            case 42:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"42");
                break;
            case 43:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"43");
                break;
            case 44:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"44");
                break;
            case 45:
                new UserItemOne(mRecyclerView,getActivity(),UserListFragment.this,"45");
                break;

        }

    }
}
