package com.opens.android.opensource.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opens.android.opensource.R;

/**
 * Created by ttc on 2017/3/12.
 */

public class MineFragment extends Fragment{
    private TextView mTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_recycleview,container,false);
        mTextView=(TextView)v.findViewById(R.id.text1);
        mTextView.setText("mine");
        return v;
    }
}
