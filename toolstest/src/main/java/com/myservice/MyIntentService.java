package com.myservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;

import com.example.toolstest.MainActivity;
import com.example.toolstest.R;
import com.tools.Androidtool.AppLogger;
import com.tools.Androidtool.getAndroidinfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.jay.example.service.MyIntentService";
    private static final String ACTION_BAZ = "com.myservice.action.BAZ";

    int getbattery;
    Intent mIntent;

    String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
    String CHANNEL_ONE_NAME= "CHANNEL_ONE_ID";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.myservice.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.myservice.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.v("TAG","onCreate");
        mIntent=new Intent();
        mIntent.setAction("batter.level");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        AppLogger.v("TAG","onStartCommand");
//        getbattery = getAndroidinfo.getbattery();
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.xgd)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("电池曲线测试") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.xgd) // 设置状态栏内的小图标
                .setContentText("电池曲线测试...") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME,NotificationManager.IMPORTANCE_MIN);
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



    public static int getbattery() {
//         MainActivity.context需要随机改变的
        BatteryManager batteryManager = (BatteryManager) MainActivity.context.getSystemService(BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        return battery;
    }

    //耗时操作
    @Override
    protected void onHandleIntent(Intent intent) {
        AppLogger.v("TAG","onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("需要周期运行的代码下载下面");
                        getbattery = getAndroidinfo.getbattery();
                        AppLogger.v("TAG","getbattery"+getbattery);
                        mIntent.putExtra("lev",getbattery);
                        sendBroadcast(mIntent);
                    }

                    //2000根据时间间隔表示不一样，这里表示2秒中
                    //TimeUnit.MILLISECONDS  表示时间间隔，有分钟，秒钟等
                }, 0, 2, TimeUnit.SECONDS);


            } else {
                AppLogger.v("TAG","action不一致");



            }
        }else {
            AppLogger.v("TAG","action为空");

        }

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
