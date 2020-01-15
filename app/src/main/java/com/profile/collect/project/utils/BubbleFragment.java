package com.profile.collect.project.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.profile.collect.project.entity.BrowseEntity;
import com.profile.collect.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gouzhouping on 20-1-8.
 */

public class BubbleFragment extends FrameLayout {

    private static final int ANIM_START   = 1; // 单次
    private static final int ANIM_STOP    = 2; // 停止
    private static final int ANIM_REPEAT  = 3; // 循环

    // 这个position很重要 不断的取出图片资源 靠它累加完成的
    private int position = 0;

    private Context mContext;
    private Point controlPointOne;
    private Point controlPointTwo;
    private ImageView tempImageView;
    private TextView textView;

    private List<BrowseEntity> browseEntities = new ArrayList<>();

    private int viewWidth , viewHeight, marginLeft, marginBot, height;

    private float[] pos;
    private float[] tan;

    private boolean isStop = true;


    public BubbleFragment(Context context) {
        this(context,null);
    }

    public BubbleFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setFocusable(false);
        // 三阶贝塞尔曲线控制点一
        controlPointOne = new Point();
        // 三阶贝塞尔曲线控制点二
        controlPointTwo = new Point();
        // 每个子view的宽高是固定的
        viewWidth = viewHeight = SizeUtils.dp2px(context, 42);
        // 动画View的位置
        marginLeft = SizeUtils.dp2px(context, 30);
        marginBot = SizeUtils.dp2px(context, 21);
        // 父View的高度
        height = SizeUtils.dp2px(context, 130);
        // 用于从 PathMeasure 中不断的取出曲线的路径值
        pos = new float[2];
        tan = new float[2];

        // 初始化数据
        browseEntities.add(new BrowseEntity(R.drawable.budaow,"张三"));
        browseEntities.add(new BrowseEntity(R.drawable.creature_boy,"Boy"));
        browseEntities.add(new BrowseEntity(R.drawable.creature_dad,"Dad"));
        browseEntities.add(new BrowseEntity(R.drawable.creature_mom,"Mom"));
        browseEntities.add(new BrowseEntity(R.drawable.footballer,"足球员"));
        //

        initView();
    }

    private void initView() {
        // 这个ImageView将不执行动画 用于底部不断切换图片展示
        tempImageView = getImageView();
        textView = getTextView();
        initData(tempImageView);
    }

    // 创建执行动画的具体角色
    private ImageView getImageView() {
        LayoutParams layoutParams = new LayoutParams(viewWidth, viewHeight);
        ImageView roundedImageView = new ImageView(getContext());
        roundedImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        layoutParams.setMargins(0, 0, marginLeft, marginBot);
        addView(roundedImageView, layoutParams);
        return roundedImageView;
    }

    // 创建用于显示坐标xx来过的TextView
    private TextView getTextView() {
        int bottom = SizeUtils.dp2px(mContext, 25);
        int right = SizeUtils.dp2px(mContext, 100);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.END | Gravity.BOTTOM;
        layoutParams.setMargins(0, 0, right, bottom);

        TextView tv_name = new TextView(mContext);
        tv_name.setTextSize(12);
        tv_name.setTextColor(Color.GREEN);
        addView(tv_name, layoutParams);
        return tv_name;
    }

    // 第一次加载数据
    private void initData(ImageView roundedImageView) {
        if (null != browseEntities && browseEntities.size() > 0) {
            // 第一次去第0个数据
            BrowseEntity browseEntity = browseEntities.get(position);
            if (null != browseEntity) {
                roundedImageView.setBackgroundResource(browseEntity.drawableId);
                String username = browseEntity.name;
                if (!TextUtils.isEmpty(username)) {
                    textView.setText(username);
                }
            }
        }
    }

    private void createAnimView() {
        ImageView imageView = getImageView();
        // 创建好后 设置缩放到最小
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        initData(imageView);
        startScaleAnim(imageView);
    }

    // 执行缩放动画
    private void startScaleAnim(final ImageView imageView) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                imageView.setScaleX(0.1f + animatedValue);
                imageView.setScaleY(0.1f + animatedValue);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (position == browseEntities.size() - 1) {
                    position = 0;
                } else {
                    position++;
                }
                BrowseEntity browseEntity = browseEntities.get(position);
                // 动画执行完后要立马取出下一个图片 把底部的图片显示更新
                tempImageView.setBackgroundResource(browseEntity.drawableId);
                // 动画执行完执行平移动画
                startTranslationAnimator(imageView);
                // 更新数据
                initData(tempImageView);
            }
        });
        valueAnimator.start();
    }

    private void startTranslationAnimator(final ImageView imageView) {
        Path path;
        int seed = (int) (Math.random() * browseEntities.size());
        // 根据随机数来确定是走左边曲线还是右边曲线
        if (seed % 2 == 0) {
            // 曲线路径右移动的封装
            path = createRightPath();
        } else {
            // 曲线路径左移动的封装
            path = createLeftPath();
        }
        // 通过PathMeasure和ValueAnimator结合在不同的阶段取出运动路径的x,y值
        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimator.setDuration(/*riseDuration*/2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                int length = (int) (pathMeasure.getLength() * animatedValue);
                // 在不同的阶段取出运动路径的x,y值
                pathMeasure.getPosTan(length, pos, tan);
                imageView.setTranslationX(pos[0]);
                imageView.setTranslationY(pos[1]);
                // 同时做透明度动画
                imageView.setAlpha(animatedValue);
                if (animatedValue >= 0.5f) {
                    imageView.setScaleX(0.2f + animatedValue);
                    imageView.setScaleY(0.2f + animatedValue);
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画执行完就移除View
                removeView(imageView);
            }
        });
        valueAnimator.start();
    }

    private Path createLeftPath() {
        Path path = new Path();
        float nextFloat = new Random().nextFloat();
        path.moveTo(nextFloat, -height * 1.0f / 1.8f);
        // 曲线控制点一
        controlPointOne.x = -(viewWidth);
        controlPointOne.y = -height / 5;
        // 曲线控制点二
        controlPointTwo.x = -(viewWidth + marginLeft / 2);
        controlPointTwo.y = (int) (-height * 0.15);
        // 生成三阶贝塞尔曲线
        path.cubicTo(controlPointOne.x, controlPointOne.y, controlPointTwo.x, controlPointTwo.y, 0, 0);
        return path;
    }

    private Path createRightPath() {
        Path path = new Path();
        float nextFloat = new Random().nextFloat();
        path.moveTo(nextFloat, -height * 1.0f / 1.8f);
        // 曲线控制点一
        controlPointOne.x = (viewWidth);
        controlPointOne.y = height / 5;
        // 曲线控制点二
        controlPointTwo.x = (viewWidth + marginLeft / 2);
        controlPointTwo.y = (int) (-height * 0.15);
        // 生成三阶贝塞尔曲线
        path.cubicTo(controlPointOne.x, controlPointOne.y, controlPointTwo.x, controlPointTwo.y, 0, 0);
        return path;
    }

    private void startAnimations() {
        if (!isStop) {
            return;
        }
        createAnimView();
    }

    private void stopAnimations() {
        if (mHandler.hasMessages(ANIM_REPEAT) || mHandler.hasMessages(ANIM_START)) {
            mHandler.removeMessages(ANIM_REPEAT);
            mHandler.removeMessages(ANIM_START);
        }
        isStop = false;
    }

    public void startCyclicAnimations() {
        isStop = true;
        startAnimations();
        Message msg = Message.obtain();
        msg.what = ANIM_REPEAT;
        mHandler.sendMessageDelayed(msg, 2000);
    }

    public void startAnim() {
        isStop = true;
        Message msg = Message.obtain();
        msg.what = ANIM_START;
        mHandler.sendMessage(msg);
    }

    public void stopAnim() {
        Message msg = Message.obtain();
        msg.what = ANIM_STOP;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ANIM_START:
                    startAnimations();
                    break;

                case ANIM_STOP:
                    stopAnimations();
                    break;

                case ANIM_REPEAT:
                    startCyclicAnimations();
                    break;
            }
        }
    };

}
