package com.tools.Androidtool;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

import com.example.toolstest.MainActivity;

import java.lang.reflect.Method;

/**
 * 主要是对安卓系统的一些操作
 * 作者：jiangxiaolin on 2020/3/18
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Androidoperate {

    /**
     * 主要是让屏幕保持常亮的状态，在APK开发的界面
     * 备注：
     * 功能可以使用，需要放在activity界面使用
     */
    public void keepscreenon() {

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        }

    /**
     * 安卓9.0实现系统重启操作
     */
    public void android9reboot() {
        Class<?> serviceManager = null;
        try {
            serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getMethod("getService", String.class);
            Object remoteService = getService.invoke(null, Context.POWER_SERVICE);
            Class<?> stub = Class.forName("android.os.IPowerManager$Stub");
            Method asInterface = stub.getMethod("asInterface", IBinder.class);
            Object powerManager = asInterface.invoke(null, remoteService);
            Method shutdown = powerManager.getClass().getDeclaredMethod("reboot",
                    boolean.class, String.class, boolean.class);
            shutdown.invoke(powerManager, false, "", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 第二种重启系统的方法,还没成功过，不知道怎么样
     */
    public void secondrebootsys() {


        try {
            //获得ServiceManager类
            Class ServiceManager = Class.forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法

            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);


            //调用getService获取RemoteService

            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);


            //获得IPowerManager.Stub类

            Class cStub = Class

                    .forName("android.os.IPowerManager$Stub");

            //获得asInterface方法

            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);

            //调用asInterface方法获取IPowerManager对象

            Object oIPowerManager = asInterface.invoke(null, oRemoteService);

            //获得shutdown()方法

            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);

            //调用shutdown()方法

            shutdown.invoke(oIPowerManager, false, true);


        } catch
        (Exception e) {

            AppLogger.v("TAG","second");

        }

    }

    /**
     * xgd重启系统的方式
     */
    public void xgdrebootsystem() {

        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
//        sendBroadcast(shutdownintent);  需要放到主界面才行
        }



}
