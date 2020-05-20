package com.tools.Androidtool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.toolstest.MainActivity;
import com.example.toolstest.Mpandroidchartactivity;
import com.myservice.MyIntentService;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.BATTERY_SERVICE;


/**
 * 该类的主要功能是获取安卓系统里面的一些信息
 */
public class getAndroidinfo {
    public static final int NETWORK_NONE = 0; // 没有网络连接
    public static final int NETWORK_WIFI = 1; // wifi连接
    public static final int NETWORK_2G = 2; // 2G
    public static final int NETWORK_3G = 3; // 3G
    public static final int NETWORK_4G = 4; // 4G
    public static final int NETWORK_MOBILE = 5; // 手机流量


    /**
     * 获取外部存储可用内存，单位是bit   可以正常使用
     *
     * @return 返回机器还剩多好M的int值
     */
    public  long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        // 单位Byte
        return info.availMem;
    }

    /**
     * 获取安卓的内存的根目录
     * @return
     */
    public  String getExternalStorageDirectory( ) {
        String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xgd_cert.zip";
         Log.v("TAG",""+endpath);
        return endpath;
    }

    /**
     * 获取安卓根目录的file对象
     * @return
     */
    public  File getExternalStorageDirectoryfile( ) {
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        return sdcard_filedir;
    }





    /**
     * 获取外部存储可用内存，单位是M   可以正常使用
     *
     * @return 返回机器还剩多好M的int值
     */
    public int getAvailableExternalMemorySize2() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard状态是没有挂载的情况
//            Toast.makeText(this, "sdcard不存在或未挂载", Toast.LENGTH_SHORT).show();
            return -1;
        }
        File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
        long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
        long l = usableSpace / (1024 * 1024);
        Log.v("TAG", "空间剩余多少M" + l);
        if (usableSpace < 1024 * 1024 * 200) {//判断剩余空间是否小于200M
            return (int) l;
        }
        return (int) l;

    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
//            long blockSize = stat.getBlockSize();
            long blockSize = stat.getBlockSizeLong();
//            long availableBlocks = stat.getAvailableBlocks();
            long availableBlocks = stat.getAvailableBlocksLong();
            return availableBlocks * blockSize;
        } else {
            Log.v("TAG","SD卡不存在");
            return 1;

        }
    }



    /***
     * 需要添加权限
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * 获取的信息如下：
     * 1、机型
     * 2、安卓版本
     * @return  返回一个集合，该集合里面保存的都是些安卓信息
     */
    public ArrayList getandroidversion() {

        ArrayList<String> androidinfo=new ArrayList<String>();
        int sdk1= Build.VERSION.SDK_INT;
        Log.v("TAG","参照N5S机器输出值为：25"+sdk1);
        androidinfo.add(String.valueOf(sdk1));
        String model= Build.MODEL;
        Log.v("TAG","参照N5S机器输出值为：N5"+model);
        androidinfo.add(model);
        String release= Build.VERSION.RELEASE;
        Log.v("TAG","参照N5S机器输出值为：7.1.2..."+release);
        androidinfo.add(release);
        return androidinfo;
    }

    /**
     * @return 获取安卓WIFIMAC地址
     */
    private String getWifiMacAddress() {
        String defaultMac = "02:00:00:00:00:00";
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ntwInterface : interfaces) {

                if (ntwInterface.getName().equalsIgnoreCase("wlan0")) {//之前是p2p0，修正为wlan
                    byte[] byteMac = ntwInterface.getHardwareAddress();
                    if (byteMac == null) {
                        // return null;
                    }
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 0; i < byteMac.length; i++) {
                        strBuilder.append(String
                                .format("%02X:", byteMac[i]));
                    }

                    if (strBuilder.length() > 0) {
                        strBuilder.deleteCharAt(strBuilder.length() - 1);
                    }

                    return strBuilder.toString();
                }

            }
        } catch (Exception e) {
            //             Log.d(TAG, e.getMessage());
        }
        return defaultMac;
    }

    /**
     * 获取安卓就可以直接获取到运营商的名字
     * @param context
     * @return
     */
    public static String getOperatorName(Context context) {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // getSimOperatorName就可以直接获取到运营商的名字
        return telephonyManager.getSimOperatorName();
    }


    /**
     * 获取WIFI频段2.4，或者5G
     */
    public String   getWififrequency(){

        //获取wifi服务
        WifiManager wifiManager =(WifiManager) MainActivity.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int frequency = wifiInfo.getFrequency();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip+"p频段"+frequency;


    }

    /**获取当前网络连接的类型
     * @param context
     * @return 网络类型
     */
    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (null == connManager) { // 为空则认为无网络
            return NETWORK_NONE;
        }
        // 获取网络类型，如果为空，返回无网络
        @SuppressLint("MissingPermission") NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }
        // 判断是否为WIFI
        @SuppressLint("MissingPermission") NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            /*
             GPRS : 2G(2.5) General Packet Radia Service 114kbps
             EDGE : 2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
             UMTS : 3G WCDMA 联通3G Universal Mobile Telecommunication System 完整的3G移动通信技术标准
             CDMA : 2G 电信 Code Division Multiple Access 码分多址
             EVDO_0 : 3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
             EVDO_A : 3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
             1xRTT : 2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
             HSDPA : 3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
             HSUPA : 3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
             HSPA : 3G (分HSDPA,HSUPA) High Speed Packet Access
             IDEN : 2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
             EVDO_B : 3G EV-DO Rev.B 14.7Mbps 下行 3.5G
             LTE : 4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
             EHRPD : 3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
             HSPAP : 3G HSPAP 比 HSDPA 快些
             */
            // 2G网络
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_2G;
            // 3G网络
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_3G;
            // 4G网络
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_4G;
            default:
                return NETWORK_MOBILE;
        }
    }




    /**
     * @return 获取终端蓝牙MAC地址
     */
    public  String getBtAddressByReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Field field = null;
        try {
            field = BluetoothAdapter.class.getDeclaredField("mService");
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            if(method != null) {
                Object obj = method.invoke(bluetoothManagerService);
                if(obj != null) {
                    return obj.toString();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取移动网络的信号强度具体的值
     * 通过该方法String.valueOf(signalStrength.getGsmSignalStrength())返回一个string的值
     *
     * 同时需要在如下方法进行调用，比如在button的click里面
     *    mTelephony = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
     *    mTelephony.listen(MyPhoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
     *
     */
    public PhoneStateListener MyPhoneListener = new PhoneStateListener() {

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {

//            mStatisics.setSignal_strength(String.valueOf(signalStrength.getGsmSignalStrength()));
//            mTextView.setText(String.valueOf(signalStrength.getGsmSignalStrength()));


        }
    };

    /**
     * @return 获取终端WiFi地址，需要放在activity界面
     * 备注：
     * 可以正常使用的因为需要放在activity界面所以注释了
     */
    public String  getWifiadd(){

        //获取wifi服务
        WifiManager wifiManager =(WifiManager) MainActivity.context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;

    }
    //WiFi地址格式装换
    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /**
     * 获取机器电池电量
     * @return
     */
    public static int getbattery() {
//         MainActivity.context需要随机改变的
        BatteryManager batteryManager = (BatteryManager) MainActivity.context.getSystemService(BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        return battery;
    }

}
