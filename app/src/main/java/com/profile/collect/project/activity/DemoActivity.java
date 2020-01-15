package com.profile.collect.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.profile.collect.project.R;


/**
 * Created by gouzhouping on 20-1-4.
 */

public class DemoActivity extends Activity implements View.OnClickListener {

    private Button mAnimBtn;
    private Button mGSensorBtn;
    private Button mNotiBtn;
    private Button mMediaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_demo_structure);

        initViews();
        //ButterKnife.bind(this);
    }

    private void initViews() {

        mAnimBtn = (Button) findViewById(R.id.anim_demo);
        mGSensorBtn = (Button) findViewById(R.id.gsensor_demo);
        mNotiBtn = (Button) findViewById(R.id.noti_demo);
        mMediaBtn = (Button) findViewById(R.id.media_demo);
        mAnimBtn.setOnClickListener(this);
        mGSensorBtn.setOnClickListener(this);
        mNotiBtn.setOnClickListener(this);
        mMediaBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.anim_demo:
                //Toast.makeText(getBaseContext(), " AnimActivity ", Toast.LENGTH_SHORT).show();
                Intent animIntent = new Intent();
                animIntent.setClass(getBaseContext(), AnimActivity.class);
                startActivity(animIntent);
                break;

            case R.id.gsensor_demo:
                //Toast.makeText(getBaseContext(), " GSensorActivity ", Toast.LENGTH_SHORT).show();
                Intent gsensorIntent = new Intent();
                gsensorIntent.setClass(getBaseContext(), GSensorActivity.class);
                startActivity(gsensorIntent);
                break;

            case R.id.noti_demo:
                //Toast.makeText(getBaseContext(), " NotiActivity ", Toast.LENGTH_SHORT).show();
                Intent notiIntent = new Intent();
                notiIntent.setClass(getBaseContext(), NotiActivity.class);
                startActivity(notiIntent);
                break;

            case R.id.media_demo:
                //Toast.makeText(getBaseContext(), " MediaActivity ", Toast.LENGTH_SHORT).show();
                Intent mediaIntent = new Intent();
                mediaIntent.setClass(getBaseContext(), MediaActivity.class);
                startActivity(mediaIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
