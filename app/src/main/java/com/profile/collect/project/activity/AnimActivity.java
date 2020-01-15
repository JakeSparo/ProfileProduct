package com.profile.collect.project.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.profile.collect.project.R;
import com.profile.collect.project.utils.BubbleFragment;
import com.profile.collect.project.utils.ButtonUtils;


/**
 * Created by gouzhouping on 20-1-7.
 */

public class AnimActivity extends Activity {

    private BubbleFragment mBubbleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mafengwo_anim_demo);

        mBubbleFragment = findViewById(R.id.mafengwo_anim);

    }

    public void start(View v) {
        if (mBubbleFragment != null && !ButtonUtils.isFastClick()) {
            mBubbleFragment.startAnim();
        }
    }

    public void stop(View v) {
        if (mBubbleFragment != null) {
            mBubbleFragment.stopAnim();
        }
    }

    public void repeat(View v) {
        if (mBubbleFragment != null && !ButtonUtils.isFastClick()) {
            mBubbleFragment.startCyclicAnimations();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBubbleFragment != null) {
            mBubbleFragment.stopAnim();
        }
    }
}
