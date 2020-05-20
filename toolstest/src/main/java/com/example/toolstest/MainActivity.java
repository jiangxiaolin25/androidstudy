package com.example.toolstest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.broadcastrecives.getvoltagerecives;
import com.myservice.Myservices;
import com.tools.Androidtool.AppLogger;
import com.tools.Androidtool.getAndroidinfo;
import com.tools.Androidtool.mycamera;
import com.tools.Javatool.MyThread;
import com.xinguodu.ddiinterface.Ddi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class MainActivity extends AppCompatActivity  implements getvoltagerecives.vollatames{

     private Button mButton,installcert,getnetinfo ,scrennon,getbatter,stathread,stopthread,voltage,starmp,stasecond;
      private TextView mTextView;
      private Ddi mDdi;
      private MyThread mMyThread;
      private getvoltagerecives mgetvoltagerecives;
    private TelephonyManager mTelephony;
    getAndroidinfo mGetAndroidinfo;
    private final String CERTPATH = "/private/";
    public static Context context ;
    int i=1;
    private ArrayList levtries,volate;


    public void getMsg(int a,int b){
        AppLogger.v("TAG","a=="+a+"b=="+b);
        levtries.add(a);
        volate.add(b);

        for(int i=0;i<levtries.size();i++){
            AppLogger.v("TAG","电池数据"+levtries.get(i));
            AppLogger.v("TAG","电池数据"+levtries.size());

        }
        for(int i=0;i<volate.size();i++){
            AppLogger.v("TAG","电压数据"+volate.get(i));
            AppLogger.v("TAG","电池数据"+volate.size());

        }






    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mButton=(Button) findViewById(R.id.getwifiaddress);
        stathread=(Button) findViewById(R.id.stathread);
        stopthread=(Button) findViewById(R.id.stopthread);
        installcert=(Button) findViewById(R.id.installcert);
        getnetinfo=(Button) findViewById(R.id.getnetname);
        scrennon=(Button) findViewById(R.id.screenon);
        getbatter=(Button) findViewById(R.id.battery);
        voltage=(Button) findViewById(R.id.voltage);
        stasecond=(Button) findViewById(R.id.stasecond);
        starmp=(Button) findViewById(R.id.statmp);
        mGetAndroidinfo=new getAndroidinfo();
        mTextView=(TextView) findViewById(R.id.TV1);

//        mgetvoltagerecives=new getvoltagerecives();
//        mgetvoltagerecives.setMessage(this);
//        IntentFilter mFilter = new IntentFilter();
//        // 添加接收网络连接状态改变的Action
//        mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        registerReceiver(mgetvoltagerecives, mFilter);

//        levtries = new ArrayList<>();
//        volate = new ArrayList<>();
        mDdi=new Ddi();
        final mycamera name=new mycamera();
        final Intent intent = new Intent(this, Myservices.class);

         Log.v("TAG","");


        stasecond.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Intent intent=new Intent(MainActivity.this, secondactivity.class);
                        intent.setAction("android.intent.action.secondactivity");
                        intent.addCategory("android.intent.category.secondactivity");
                        startActivity(intent);
        			}
        		});
        starmp.setOnClickListener(new Button.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Intent intent=new Intent(MainActivity.this, Mpandroidchartactivity.class);
                        intent.setAction("android.intent.action.Mpandroidchartactivity");
                        intent.addCategory("android.intent.category.LAUNCHER");
                        startActivity(intent);
        			}
        		});

        //获取电池电压
        voltage.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        AppLogger.v("TAG","开始服务");

                        //注册广播
//                        IntentFilter mFilter = new IntentFilter();
//                        // 添加接收网络连接状态改变的Action
//                        mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
//                        registerReceiver(new getvoltagerecives(), mFilter);
//                        try {
//                            creattitle("电池曲线");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        //MyService.class自定义继承的服务类
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //android8.0以上通过startForegroundService启动service
                            startForegroundService(intent);
                        } else {
                            startService(intent);
                        }


//                        startService(intent);
                        Toast.makeText(MainActivity.this, "开始一个服务", Toast.LENGTH_SHORT).show();


                    }

        		});


        mButton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        String wifiadd = getWifiadd();
                        mTextView.setText(wifiadd);
                        String release= Build.VERSION.RELEASE;
                        Log.v("TAG","参照N5S机器输出值为：7.1.2..."+release);

                    }

        		});
        getbatter.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub

                        BatteryManager batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
//                        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                        int batteryHealthOverVoltage = batteryManager.BATTERY_HEALTH_OVER_VOLTAGE;
                        mTextView.setText("当前电池电量为："+batteryHealthOverVoltage+"%");


        			}

        		});
        installcert.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        String externalStorageDirectory = mGetAndroidinfo.getExternalStorageDirectory();
                         Log.v("TAG","installcert..."+externalStorageDirectory);

//                        String externalStorageDirectory = "xgd_cert.zip";
                        try {
                            int i = setCertHashToK21(externalStorageDirectory);
                             Log.v("TAG",""+i);
                             if(i==0){
                                 copyFile(externalStorageDirectory, CERTPATH + "xinguodu");
                                  Log.v("TAG","copyFile运行完成+++++");
//                                 boolean b = mXgdSignData.verifyXgdSignApk(CERTPATH + "xinguodu", "");
//                                  Log.v("TAG",""+b);


                             }else{
                                  Log.v("TAG",""+i);
                             }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

        		});
        getnetinfo.setOnClickListener(new Button.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        //获取网络信息的
//                        String operatorName = getAndroidinfo.getOperatorName(MainActivity.this);
//                        int getNetworkState = getAndroidinfo.getNetworkState(MainActivity.this);
////                        mTelephony = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
////                        mTelephony.listen(MyPhoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
//                        mTextView.setText(operatorName+""+getNetworkState+"G网络");
                        //获取WIFI地址
//                        String wifiadd = getWifiadd();
//                        mTextView.setText(wifiadd);
//                        captureScreen();
//                        getindexpic();
                        SystemClock.sleep(6000);
//                        Bitmap bitmap = captureView(getWindow().getDecorView());
//                        savePic(bitmap,"sdcard/short5.png");
                        //图片比对
//                        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.a123);
//                        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//                        mycamera mycamera = new mycamera();
//                        boolean equals = mycamera.isEquals(bitmap1, bitmap2);
//                         Log.v("TAG",""+equals);

                           runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                Toast.makeText(getApplicationContext(), "FourActivity2 click button", Toast.LENGTH_SHORT).show();
                            }
                            });





                    }
        		});
        scrennon.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                         AppLogger.i("TAG", "屏幕被锁定保持常亮....");


        			}

        		});

//开始一个线程
        stathread.setOnClickListener(new Button.OnClickListener() {



        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, "开始测试", Toast.LENGTH_SHORT).show();
                        //这里要做一个判断不然又是新建了一个线程，又会重新开始一个线程
                        if (mMyThread==null){
                            mMyThread =new MyThread();
                        }

                        if(mMyThread.pause){
                            mMyThread.resumeThread();
                        }
                        mMyThread.start();
        			}

        		});
        //暂停一个线程
        stopthread.setOnClickListener(new Button.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Toast.makeText(MainActivity.this, "暂停测试", Toast.LENGTH_SHORT).show();
                        if(mMyThread!=null){
//                            mMyThread =new MyThread();
                             Log.v("TAG","mMyThread不为空");
                            mMyThread.pauseThread();
                        }
        			}
        		});

    }


    public Bitmap captureView(View view) {
        // 根据View的宽高创建一个空的Bitmap
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.RGB_565);
        // 利用该Bitmap创建一个空的Canvas
        Canvas canvas = new Canvas(bitmap);
        // 绘制背景(可选)
        canvas.drawColor(Color.WHITE);
        // 将view的内容绘制到我们指定的Canvas上
        view.draw(canvas);
        return bitmap;
    }



    public void getindexpic() {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1,10,10,10,10);
        savePic(bitmap2,"sdcard/short2.png");
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
        output.append("电池电量" +","+ "  电池电压"+","+ "  电池温度"+","+ "  电池状态"+","+ "  电池健康"+"\r\n");
        output.close();


    }

    private Bitmap bitmap = null;
    static ByteArrayOutputStream byteOut = null;
    public void captureScreen() {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                final View contentView = getWindow().getDecorView();
                try{
                     Log.v("TAG",contentView.getHeight()+":"+contentView.getWidth());

//                    Log.e("HEHE",);
                    bitmap = Bitmap.createBitmap(contentView.getWidth(),
                            contentView.getHeight(), Bitmap.Config.ARGB_4444);
                    contentView.draw(new Canvas(bitmap));
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOut);
                    savePic(bitmap, "sdcard/short.png");
                }catch (Exception e){e.printStackTrace();}
                finally {
                    try{
                        if (null != byteOut)
                            byteOut.close();
                        if (null != bitmap && !bitmap.isRecycled()) {
//                            bitmap.recycle();
                            bitmap = null;
                        }
                    }catch (IOException e){e.printStackTrace();}

                }
            }
        };
        try {
            action.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                boolean success= b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                if(success) {
                    Log.v("TAG","截屏成功");
                    Toast.makeText(MainActivity.this, "截屏成功", Toast.LENGTH_SHORT).show();
                }

//
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();e.printStackTrace();
        }
    }


//    public String  getWifiadd(){
//
//        //获取wifi服务
//        WifiManager wifiManager =(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
////判断wifi是否开启
//        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
//        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ipAddress = wifiInfo.getIpAddress();
//        String ip = intToIp(ipAddress);
//        return ip;
//
//    }
//        //WiFi地址格式装换
//        private String intToIp(int i) {
//
//            return (i & 0xFF ) + "." +
//                    ((i >> 8 ) & 0xFF) + "." +
//                    ((i >> 16 ) & 0xFF) + "." +
//                    ( i >> 24 & 0xFF) ;
//        }

    public PhoneStateListener MyPhoneListener = new PhoneStateListener() {

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {

//            mStatisics.setSignal_strength(String.valueOf(signalStrength.getGsmSignalStrength()));
            mTextView.setText(String.valueOf(signalStrength.getGsmSignalStrength()));


        }
    };


















    /*==================================================================
* Function	: PosCredentialsManagement.copyFile
* Description	: Copy files to a specified directory
* Input Para	: 
* Return Value: 
* date		: lizejin--20151211
==================================================================*/
    public int copyFile(String oldPath, String newPath) {

        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                 Log.v("TAG",".....copyFile完成");

                inStream.close();
            }
            Process process = Runtime.getRuntime().exec("chmod 644 " + newPath);
            Log.v("TAG",".....process开始");
            int exitValue = process.waitFor();
            Log.v("TAG",".....process完成");
            if (0 != exitValue) {
                Log.v("TAG", "call shell failed. error code is :" + exitValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            Log.v("TAG", "chmod of cert failed!" + e);
        }
        try {
            Runtime.getRuntime().exec("sync");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;

    }


    /*==================================================================
* Function	: PosCredentialsManagement.setCertHashToK21
* Description	: The certificate information is written K21
* Input Para	:
* Return Value:
* date		: lizejin--20151216
==================================================================*/
    private int setCertHashToK21(String zipFileName) throws Exception{
        ZipFile zip = new ZipFile(zipFileName);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream fis = zip.getInputStream(entry);
            if(entry.getName().contains("xgdandroid.txt") == true){
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                String certStr = "";
                boolean tmpflag = false;
                int cnt = 0;
                while ((line = br.readLine()) != null) {
                    if(line.contains("[SIGNATURE]")){
                        line = br.readLine();
                        tmpflag = true;
                    }
                    if(tmpflag == true){
                        certStr += line + '\r' + '\n';
                    }
                }
                certStr += '\r' + '\n';
                if(tmpflag == true){
//					Log.i("TAG", "ddi_sys_setCertHash certStr:" + certStr);
//					Log.i("TAG", "ddi_sys_setCertHash certStr len:" + certStr.length());
                    int ret = mDdi.ddi_sys_setCertHash(certStr.getBytes());
//                    Log.i("TAG", "ddi_sys_setCertHash ret:" + ret);
                     Log.v("TAG","ddi_sys_setCertHash ret:" + ret);
                    return ret;

//					return 0;
                }
            }
        }
        return -1;
    }



    public String  getWifiadd(){

        //获取wifi服务
        WifiManager wifiManager =(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
    //WiFi地址格式装换
    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    /*
     * 验证 新国都签名文件;
     * 证书包里的 根证书 后缀为.pem;
     */
    
}
