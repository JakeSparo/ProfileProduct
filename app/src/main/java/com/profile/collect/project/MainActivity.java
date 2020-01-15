package com.profile.collect.project;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.broadcast_test);

        mText = (TextView) findViewById(R.id.textView);
        SharedPreferences shre = getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shre.edit();
        editor.putFloat("ss",1.0f);
        editor.putString("name","MKK");
        editor.commit();

    }

    ///////////////////// 广播测试（正常 && 粘性）
    public void normalClick(View v) {
        Toast.makeText(getBaseContext(), "注册正常广播", Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(getBaseContext(), "电池广播", Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(receiver, filter);
    }


    public void stickyClick(View v) {
        Toast.makeText(getBaseContext(), "注册粘性广播", Toast.LENGTH_SHORT).show();
    }
    /////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    public void jump(View view) {
        startActivity(new Intent(MainActivity.this, AudioTestActivity.class));
    }


    public void sendNotification(View view) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(getString(R.string.app_name), getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription("此处填写通知渠道介绍");
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
//        //再创建通知
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.app_name));
//
//        //设置通知栏大图标，上图中右边的大图
//        builder.setLargeIcon(BitmapFactory.decodeResource(
//                getResources(), R.mipmap.ic_launcher))
//                // 设置状态栏和通知栏小图标
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                // 设置通知栏应用名称
//                .setTicker("通知栏应用名称")
//                // 设置通知栏显示时间
//                .setWhen(System.currentTimeMillis())
//                // 设置通知栏标题
//                .setContentTitle("通知栏标题")
//                // 设置通知栏内容
//                .setContentText("通知栏内容")
//                // 设置通知栏点击后是否清除，设置为true，当点击此通知栏后，它会自动消失
//                .setAutoCancel(false)
//                // 将Ongoing设为true 那么左滑右滑将不能删除通知栏
//                //.setOngoing(true)
//                // 设置通知栏点击意图
//                //.setContentIntent(pendingIntent)
//                // 铃声、闪光、震动均系统默认
//                //.setDefaults(Notification.DEFAULT_ALL)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                // 设置为public后，通知栏将在锁屏界面显示
//                //.setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
//                // 从Android4.1开始，可以通过以下方法，设置通知栏的优先级，优先级越高的通知排的越靠前，
//                // 优先级低的，不会在手机最顶部的状态栏显示图标
//                // 设置优先级为PRIORITY_MAX，将会在手机顶部显示通知栏
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

        mText.setText(getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE).getString("name","www"));
    }
}
