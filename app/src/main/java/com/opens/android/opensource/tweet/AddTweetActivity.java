package com.opens.android.opensource.tweet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.opens.android.opensource.R;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ttc on 2017/3/13.
 */

public class AddTweetActivity extends AppCompatActivity implements View.OnClickListener{
    public EditText tweetContent;
    private GridView mGridView;
    private final int IMAGE_OPEN = 1;
    private String pathImage;
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;
    private LinearLayout AddPicPull;
    private View AddPicLayer;
    private LinearLayout AddPicOption;
    private TextView addpic;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_add);
        init();
    }
    public void init(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mGridView= (GridView) findViewById(R.id.gridView1);
        tweetContent=(EditText)findViewById(R.id.add_edit);
        AddPicPull=(LinearLayout)findViewById(R.id.add_pic_pull);
        addpic=(TextView)findViewById(R.id.add_pic_text) ;
        AddPicLayer=findViewById(R.id.photo_layer);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.addpic);
        AddPicOption=(LinearLayout)findViewById(R.id.add_pic_options);
        AddPicLayer.setOnClickListener(this);
        AddPicPull.setOnClickListener(this);
//        addpic.setOnClickListener(this);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.addpic,
                new String[] { "itemImage"}, new int[] { R.id.imageView1});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        mGridView.setAdapter(simpleAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if( imageItem.size() == 10) { //第一张为默认图片
                    Toast.makeText(AddTweetActivity.this, "图片数9张已满", Toast.LENGTH_SHORT).show();
                }
                else if(position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(AddTweetActivity.this, "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                    //通过onResume()刷新数据
                }
                else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //      Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTweetActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {

                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[] { MediaStore.Images.Media.DATA },
                        null,
                        null,
                        null);

                if (null == cursor) {
                    return;
                }

                cursor.moveToFirst();
                pathImage = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(pathImage)){
            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.addpic,
                    new String[] { "itemImage"}, new int[] { R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if(view instanceof ImageView && data instanceof Bitmap){
                        ImageView i = (ImageView)view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            mGridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();

            pathImage = null;
        }
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

    @Override
    public void onClick(View view) {
        System.out.println("......................................................");
        switch (view.getId()){
            case R.id.photo_layer:
            case R.id.add_pic_pull:
            //case R.id.add_pic_text:
                System.out.println("**************************************************************Clicked!");
                AddPicPull.animate().cancel();
                AddPicLayer.animate().cancel();
                int height=AddPicOption.getHeight();
                float progress=(AddPicLayer.getTag()!=null&&AddPicLayer.getTag()instanceof Float)?
                        (float)AddPicLayer.getTag():1;
                int time=(int)(360*progress);
                if(AddPicPull.getTag()!=null){
                    AddPicPull.setTag(null);
                    glide(height,progress,time);
                }else {
                    AddPicPull.setTag(true);
                    upGlide(height, progress, time);
                }
                break;
        }
    }
    private void glide(int height, float progress, int time) {
        AddPicPull.animate()
                .translationYBy(height - height * progress)
                .translationY(height)
                .setDuration(time)
                .start();

        AddPicLayer.animate()
                .alphaBy(1 * progress)
                .alpha(0)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            AddPicLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            AddPicLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                        AddPicLayer.setVisibility(View.GONE);
                    }
                })
                .start();
    }
    private void upGlide(int height, float progress, int time) {
        AddPicPull.animate()
                .translationYBy(height * progress)
                .translationY(0)
                .setDuration(time)
                .start();
        AddPicLayer.animate()
                .alphaBy(1 - progress)
                .alpha(1)
                .setDuration(time)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        AddPicLayer.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            AddPicLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animation instanceof ValueAnimator) {
                            AddPicLayer.setTag(((ValueAnimator) animation).getAnimatedValue());
                        }
                    }
                })
                .start();
    }
}
