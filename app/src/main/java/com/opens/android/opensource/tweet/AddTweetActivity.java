package com.opens.android.opensource.tweet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.opens.android.opensource.R;


/**
 * Created by ttc on 2017/3/13.
 */

public class AddTweetActivity extends AppCompatActivity {
    public EditText tweetContent;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_add);
        tweetContent=(EditText)findViewById(R.id.add_edit);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tweet_add,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_item_OK:
                String str=tweetContent.getText().toString();
                Log.i("string:",str);
                //Toast.makeText(this,"消息内容："+str,Toast.LENGTH_SHORT);
                AddTweetActivity.this.finish();
                //new TweetFetch().fetchaddItems(str);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
