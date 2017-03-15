package com.opens.android.opensource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by ttc on 2017/3/14.
 */

//public class LoginActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher{
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
        private Button mLoginButton;
    private EditText mUserName;
    private EditText mPassword;
    private String get;
    private String UserName;
    private String PassWord;
//    boolean inputUserName=false;
//    boolean inputPassWord=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_btn:
//                UserName=mUserName.getText().toString();
//                PassWord=mPassword.getText().toString();
                //get="https://www.oschina.net/action/oauth2/authorize?client_id=gG2tBWF4iaunmNI7m6ZE&response_type=code&redirect_uri=https://my.oschina.net/lightningbaby";
                Intent intent=new Intent(LoginActivity.this,AuthActivity.class);

                startActivity(intent);
        }
    }
    public void initView(){
        mLoginButton=(Button)findViewById(R.id.login_btn);
        mLoginButton.setOnClickListener(this);
//        mUserName=(EditText)findViewById(R.id.login_username_edit);
//        mPassword=(EditText)findViewById(R.id.login_password_edit);
//        mUserName.addTextChangedListener(this);
//        mPassword.addTextChangedListener(this);
    }

//    @Override
//    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//    }
//
//    @Override
//    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        inputPassWord=mPassword.getText().length()>0;
//        inputUserName=mUserName.getText().length()>0;
//        if(inputUserName&&inputPassWord){
//            mLoginButton.setEnabled(true);
//        }else {
//            mLoginButton.setEnabled(false);
//        }
//    }
//
//    @Override
//    public void afterTextChanged(Editable editable) {
//
//    }
}
