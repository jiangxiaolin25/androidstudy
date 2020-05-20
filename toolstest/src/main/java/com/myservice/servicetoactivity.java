package com.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.greenrobot.eventbus.EventBus;

import com.other.eventbuscalss;
import com.tools.Androidtool.AppLogger;

public class servicetoactivity extends Service {
    eventbuscalss updateMain ;

    public servicetoactivity() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.v("TAG","onCreate");
        updateMain= new eventbuscalss("你好activity，我是服务器端，");
        EventBus.getDefault().post(updateMain);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLogger.v("TAG","onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.




        throw new UnsupportedOperationException("Not yet implemented");


    }
}
