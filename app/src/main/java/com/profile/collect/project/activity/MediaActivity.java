package com.profile.collect.project.activity;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.profile.collect.project.R;

/**
 * Created by gouzhouping on 20-1-13.
 */

public class MediaActivity extends Activity {


    private TextView mTitle;
    private Button   mGainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_media_demo);

        mTitle = (TextView) findViewById(R.id.media_title);
        mGainBtn = (Button) findViewById(R.id.media_btn_gain);
        mGainBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateTitle();
            }
        });

    }

    private void updateTitle() {
        final Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
                getBaseContext(), RingtoneManager.TYPE_RINGTONE);

        final CharSequence summary;
        if (ringtoneUri == null) {
            summary = getBaseContext().getString(R.string.ringtone_silent);
        } else {
            summary = RingtoneManager.getRingtone(getBaseContext(), ringtoneUri).getTitle(
                    getBaseContext()/*, ringtoneUri, false *//* followSettingsUri *//*, true *//* allowRemote */);
        }
        if (summary != null) {
            mTitle.setText(summary);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
