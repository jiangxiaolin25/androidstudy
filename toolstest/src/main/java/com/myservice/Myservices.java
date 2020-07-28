package com.myservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.os.IBinder;

import com.broadcastrecives.getvoltagerecives;
import com.example.toolstest.MainActivity;
import com.example.toolstest.R;
import com.tools.Androidtool.AppLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import androidx.annotation.Nullable;

/**
 * 作者：jiangxiaolin on 2020/5/6
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Myservices extends Service {
    String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
    String CHANNEL_ONE_NAME= "CHANNEL_ONE_ID";
    NotificationChannel notificationChannel= null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLogger.v("TAG","onStartCommand");
//        //进行8.0的判断
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
////            AppLogger.v("TAG","安卓8.0");
////            notificationChannel= new NotificationChannel(CHANNEL_ONE_ID,
////                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
////            notificationChannel.enableLights(true);
////            notificationChannel.setLightColor(Color.RED);
////            notificationChannel.setShowBadge(true);
////            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
////            NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////            manager.createNotificationChannel(notificationChannel);
////        }




        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.xgd)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("电池曲线测试") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.xgd) // 设置状态栏内的小图标
                .setContentText("电池曲线正在测试中....") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME,                    NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110,notification);
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.v("TAG", "服务onCreate");
        //注册广播
        IntentFilter mFilter = new IntentFilter();
        // 添加接收网络连接状态改变的Action
        mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(new getvoltagerecives(), mFilter);
        try {
            creattitle("电池曲线");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppLogger.v("TAG", "服务关闭");

    }

    private String getstringSP() {
        String u;

        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        u = preferences.getString("key", "空");
        return u;

    }

    public static void creattitle(String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, result + ".txt");
        if (file.exists()){
            file.delete();
            file.createNewFile();
        }else {
            file.createNewFile();
        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));
        output.append("时间" +","+"电池电量" +","+ "  电池电压"+","+ "  电池温度"+","+ "  电池状态"+","+ "  电池健康"+"\r\n");
        output.close();


    }
}
