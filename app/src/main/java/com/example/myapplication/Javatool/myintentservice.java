package com.example.myapplication.Javatool;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.CMDUtils;

import androidx.annotation.Nullable;

/**
 * Created by jiangxiaolin on 2019/6/21.
 */

public class myintentservice extends IntentService {
    private static final String TAG = "TAG";


    public myintentservice() {


        super("myintentservice");
        Log.v(TAG, "构造函数");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v(TAG, "onHandleIntent");
        Bundle extras = intent.getExtras();
        int tag = extras.getInt("TAG");
        String command = extras.getString("command");
        Log.v("TAG", "服务里面获取到的值" + command + "...." + tag);
        CMDUtils.CMD_Result rs1;
        if (intent.getAction().equalsIgnoreCase("com.test.intentservice")) {
            switch (tag) {
                case 1:
                    rs1 = CMDUtils.runCMD1(command, true, true);
                    break;
                case 2:
                    rs1 = CMDUtils.runCMD1(command, true, true);
                    try {
                        rebootmeth(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    break;
                default:
            }
        } else {
            Log.v("TAG", "action错误" + intent.getAction());
        }

//        //com.test.intentservice
//        if (intent.getAction().equalsIgnoreCase("com.test.intentservice"))
//        {
////            String command = generateCommand2(pkgName, clsName,mthName);
//            CMDUtils.CMD_Result rs = CMDUtils.runCMD1(command, true, true);
//            try {
//                rebootmeth(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void rebootmeth(int sleeptime) throws InterruptedException {
        Thread.sleep(sleeptime * 1000);
        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
        sendBroadcast(shutdownintent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(myintentservice.this, "机器正在重启....", Toast.LENGTH_LONG).show();
        Log.v("TAG", "onDestroy");

    }
}
