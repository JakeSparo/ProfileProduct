package com.profile.collect.project;


import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;


public class AudioTestActivity extends Activity {


    private TextView mTele;
    private TextView mSystem;
    private TextView mRing;
    private TextView mMusic;
    private TextView mAlarm;
    private TextView mMark;

    private AudioManager am;

    private int max;
    private int current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_test);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mTele = (TextView) findViewById(R.id.tele);
        mSystem = (TextView) findViewById(R.id.system);
        mRing = (TextView) findViewById(R.id.ring);
        mMusic = (TextView) findViewById(R.id.music);
        mAlarm = (TextView) findViewById(R.id.alarm);
        mMark = (TextView) findViewById(R.id.mark);


        update();
    }

    private void update() {

        max = am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);// 0
        current= am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        mTele.setText("通话音量值：" + max + "-" + current);

        max = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);// 1
        current = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
        mSystem.setText("系统音量值：" + max + "-" + current);

        max = am.getStreamMaxVolume(AudioManager.STREAM_RING);// 2
        current = am.getStreamVolume(AudioManager.STREAM_RING);
        mRing.setText("系统铃声值：" + max + "-" + current);

        max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 3
        current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        mMusic.setText("音乐音量值：" + max + "-" + current);

        max = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);// 4
        current = am.getStreamVolume(AudioManager.STREAM_ALARM);
        mAlarm.setText("闹铃音量值：" + max + "-" + current);

        max = am.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);// 5
        current = am
                .getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        mMark.setText("提示声音音量值：" + max + "-" + current);
    }


}

