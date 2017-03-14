package com.opens.android.opensource.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opens.android.opensource.R;
import com.opens.android.opensource.shake.ShakeActivity;

/**
 * Created by ttc on 2017/3/12.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private TextView mTextView;
    private LinearLayout mShakeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_mine,container,false);
        mShakeLayout=(LinearLayout)v.findViewById(R.id.my_shake);
        mShakeLayout.setOnClickListener(this);

        //mTextView=(TextView)v.findViewById(R.id.text1);
        //mTextView.setText("摇一摇");
        /*
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ShakeActivity.class);
                startActivity(intent);
            }
        });
        */
        return v;
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.my_shake:
                Intent intent=new Intent(getActivity(), ShakeActivity.class);
                startActivity(intent);
        }
    }
}
