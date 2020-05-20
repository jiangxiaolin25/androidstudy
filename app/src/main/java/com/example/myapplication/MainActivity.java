package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Javatool.POIexcelwrite;

import com.xgd.smartpos.manager.ICloudService;
import com.xgd.smartpos.manager.app.IAppDeleteObserver;
import com.xgd.smartpos.manager.app.IAppInstallObserver;
import com.xgd.smartpos.manager.app.IAppManager;
import com.xinguodu.ddiinterface.Ddi;
import com.xinguodu.ddiinterface.DdiConstant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import coumi.GetBTMac;
import tools.Androidtool.AppLogger;


public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private static final String TAG = "TAG";

    private String WLAN = "40c81F82b577";
    private String BT = "40C81F62B588";
    private String SN = "G3100000003";
    private String TUSN = "00000404G3100000003";
    //N6:563536434e4d4147313830333132303033
    private String card = "563536434e4d4147313830333132303033";//N5S
//	private String card ="563536434e4d4147313830333132303033";//N6

    private boolean end = true;
    static byte wParam[] = new byte[17];
    // private byte[] lpOut;
    String key1 = "12345678abcdEFBFABCDefBCabcdEFBF";
    byte[] innerkey1 = ByteUtils.hexString2ByteArray(key1);
    String key2 = "56248AAAAAAAAAAA9999efBCabcdEFBF";
    byte[] innerkey2 = ByteUtils.hexString2ByteArray(key2);
    String data0 = "4e168484e5c1c1d6380012839d2d766f";
    private int res0;
    byte[] DataOut = new byte[16];
    byte[] ipOut = new byte[200];
    private byte[] msg_summery;
    int sp;
    POIexcelwrite name;


    private int res2;
    private long reboottime = 51;
    private byte[] ipIn;
    private TextView mTextView;
    private Intent mIntent;
    private ICloudService mICloudService;
    private int APP_MANAGER = 1;
    private IBinder binder;
    private IAppManager mIAppManager = null;
    private int[] leOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Thread.sleep(2000);
            mTextView = (TextView) findViewById(R.id.text1);
            mTextView.setTextColor(android.graphics.Color.RED);
            verifyStoragePermissions(this);
            File file = new File("/mnt/sdcard/readmestest.xlsx");
            name = new POIexcelwrite();
            if (!file.exists()) {
                name.creatdowneexcel();
            }
            sp = getSP();
            sp = ++sp;
            putSP(sp);







            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


            //服务里面进行安装和重启，9.0以后的系统没有这个服务，所以不用
//            mIntent = new Intent();
//            Log.v("costtime", "Intent");
//            mIntent = new Intent();
//            mIntent.setAction("com.xgd.smartpos.service.SYSTEM_APIMANAGER");
//            // com.ccb.smartpos.service.CLOUD_MANAGER
//            mIntent.setPackage("com.xgd.possystemservice");
//            bindService(mIntent, this, Context.BIND_AUTO_CREATE);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xgdrebootsystem();

    }

    private void xgdrebootsystem() {
        Intent shutdownintent = new Intent();
        shutdownintent.setAction("com.xgd.powermanager.REBOOT");
        sendBroadcast(shutdownintent);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};


    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void putSP(int i) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("key", i);
        editor.commit();
    }
//    public static void result(String txt) throws IOException {
//        File file = new File("/mnt/sdcard/result.txt");
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
//        output.append(txt + "\n");
//        output.close();
//    }

    private int getSP() {
        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
        int u = preferences.getInt("key", 0);
        return u;
//		String p = preferences.getString("psw","");
    }

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

    private void testWIFImac(){
        String mac = getWifiMacAddress();
//			mTextView.setText(mac);
        if (mac.equalsIgnoreCase(WLAN)) {
            name.writeExcel3("" + sp, "获取终端的WI-FI地址", "获取终端的WI-FI地址成功");
//            result("获取终端的WI-FI地址成功\r\n");
            Log.v("costtime", "WLAN地址获取成功" + mac);

        } else {
            name.writeExcel3("" + sp, "获取终端的WI-FI地址", "获取终端的WI-FI地址失败");
//            result("获取终端的WI-FI地址失败：" + mac + "\r\n");
            Log.v("costtime", "WLAN地址获取失败" + mac);
        }
    }

    //AES算法测试
    private void testgroup53() {
        try {
            Log.v("costtime", "开始测试" + "\r\n");
            name.writeExcel3("" + sp, "开始testgroup53测试", "成功");
//            result("开始测试" + res0+"\r\n");
            String key1 = "A12345678A12345678A12345678A1234";
            byte[] innerkey1 = ByteUtils.hexString2ByteArray(key1);
            String key2 = "A12345678A12345678A12345678A1234";
            byte[] innerkey2 = ByteUtils.hexString2ByteArray(key2);
            String plain = "12345678abcdefbfabcdefbcabcdefbf";
            byte[] m_plain = ByteUtils.hexString2ByteArray(plain);
            byte[] cipher = new byte[16];
            byte[] cipher1 = new byte[16];
            String data = "7B0EE5CBF353412449F580C49FC82865";
            byte[] m_data = ByteUtils.hexString2ByteArray(data);
            Ddi.ddi_innerkey_open();
            int res0 = Ddi.ddi_innerkey_update_mk((byte) 0x04, 77, innerkey1, 16);
            if (res0 == 0) {
                name.writeExcel3("" + sp, "更新主密钥", "更新主密钥成功");
                Log.v("costtime", "更新主密钥成功" + res0 + "\r\n");
                name.writeExcel3("" + sp, "开始testgroup53测试", "成功");
//                result("更新主密钥成功" + res0+"\r\n");
                int res1 = Ddi.ddi_innerkey_update_wk((byte) 0x04, 77, 225, 0, ipIn, innerkey2, 16, innerkey1, 16);
                if (res1 == 0) {
                    name.writeExcel3("" + sp, "更新工作密钥加密", "更新工作密钥加密成功");
                    Log.v("costtime", "更新工作密钥加密成功" + res1 + "\r\n");
//                    result("更新工作密钥加密成功" + res1+"\r\n");
                    int res2 = Ddi.ddi_innerkey_aes_encrypt(1, 225, (byte) 0, ipIn, m_plain, m_plain.length, cipher);
                    String data1 = ByteUtils.byteArray2HexString(cipher);
                    if (res2 == 0 && data1.equalsIgnoreCase(data)) {
                        Log.v("costtime", "工作密钥加密成功" + res2 + "\r\n");
                        name.writeExcel3("" + sp, "工作密钥加密测试", "工作密钥加密测试成功");
//                        result("工作密钥加密成功" + res2+"\r\n");
                        int res3 = Ddi.ddi_innerkey_aes_decrypt(1, 225, 1, (byte) 0, ipIn, m_data, m_data.length, cipher1);
                        String data2 = ByteUtils.byteArray2HexString(cipher1);
                        if (res3 == 0 && data2.equalsIgnoreCase(plain)) {
                            name.writeExcel3("" + sp, "工作密钥解密测试", "工作密钥解密成功");
                            Log.v("costtime", "工作密钥解密成功" + res3 + "\r\n");
//                            result("工作密钥解密成功" + res3+"\r\n");
                        } else {
                            name.writeExcel3("" + sp, "工作密钥解密测试", "工作密钥解密失败");
                            Log.v("costtime", "group53工作密钥解密失败" + res3 + "\r\n");
//                            result("group53工作密钥解密失败" + res3+"\r\n");
                        }
                    } else {
                        Log.v("costtime", "group53工作密钥加密失败" + res2 + "\r\n");
                        name.writeExcel3("" + sp, "工作密钥加密测试", "工作密钥加密失败");
//                        result("group53工作密钥加密失败" + res2+"\r\n");
                    }
                } else {
                    name.writeExcel3("" + sp, "group53更新工作密钥测试", "group53更新工作密钥失败");
                    Log.v("costtime", "group53更新工作密钥失败" + res1 + "\r\n");
//                    result("group53更新工作密钥失败" + res1+"\r\n");
                }
            } else {
                name.writeExcel3("" + sp, "group53更新主密钥测试", "group53更新主密钥失败");
                Log.v("costtime", "group53更新主密钥失败" + res0 + "\r\n");
//                result("group53更新主密钥失败" + res0+"\r\n");
            }
            Ddi.ddi_innerkey_close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void testSM2update2() {
        try {
            Log.v("costtime", "testSM2update2开始测试");
            name.writeExcel3("" + sp, "开始testSM2update2", "成功");
//            result("testSM2update2" + "\r\n");
            byte[] puk = ByteUtils.hexString2ByteArray("C3DC13CF45A57D86D5AD844EC13491A71B9861EEC3A8CB4752EF88DECA9B779660FA86F02F276ABA4DEA8A10C5FC2D055521B71A36488309E454C01AFF775E61");
            byte[] prk = ByteUtils.hexString2ByteArray("84441980D22E558CF9B46D9D93814E135E30D2DD18B6F2D3BFF950CD8D871A18");
            byte[] pn = ByteUtils.hexString2ByteArray("30303030abcdef99a1da25f1e411fea5");
            byte[] ipOut4 = new byte[112];
            byte[] ipOut5 = new byte[16];
            Ddi.ddi_innerkey_open();
            int res1 = Ddi.ddi_innerkey_nes_sm2_gen_keypair(3, 0, puk, prk);
            if (res1 == 0) {
//                result("SM2更新密钥成功" + "\r\n");
                name.writeExcel3("" + sp, "SM2更新密钥测试", "SM2更新密钥成功");
                int res2 = Ddi.ddi_innerkey_nes_sm2_encrypt(0, pn, pn.length, ipOut4, leOut);
                if (res2 == 0) {
//                    result("SM2公钥加密成功" + "\r\n");
                    name.writeExcel3("" + sp, "SM2公钥加密测试", "SM2公钥加密成功");
                } else {
                    name.writeExcel3("" + sp, "SM2update2公钥加密测试", "SM2update2公钥加密成功");
//                    result("SM2update2公钥加密失败" +res2+ "\r\n");

                }
                int res3 = Ddi.ddi_innerkey_nes_sm2_decrypt(0, ipOut4, ipOut4.length, ipOut5, leOut);
                String str = ByteUtils.byteArray2HexString(ipOut5);
                if (res3 == 0 && str.equals("30303030abcdef99a1da25f1e411fea5")) {
//                    result("SM2私钥解密成功" +res3+ "\r\n");
                    name.writeExcel3("" + sp, "SM2私钥解密测试", "SM2私钥解密成功");
                } else {
                    name.writeExcel3("" + sp, "SM2update2私钥解密", "SM2私钥解密失败");
//                    result("SM2update2私钥解密失败" +res3+ "\r\n");
                }
            } else {
                name.writeExcel3("" + sp, "SM2update2私钥解密", "SM2私钥解密失败");
//                result("SM2update2更新密钥失败" +res1+ "\r\n");
            }
            Log.v("costtime", "testSM2update2测试完成");
            Ddi.ddi_innerkey_close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    //DUKPT测试
    private void testDukptEncrypt1() {
        try {
            Log.v("costtime", "开始测试testDukptEncrypt1" + "\r\n");
            name.writeExcel3("" + sp, "开始测试testDukptEncrypt1", "成功");
//            result("开始测试testDukptEncrypt1" + "\r\n");
            byte[] m_plain;
            byte[] cipher = new byte[24];
            byte[] m_initkey1;
            byte[] m_initksn;
            byte[] iv;
            m_initkey1 = ByteUtils.hexString2ByteArray("01234567899876543210112233445566");
            m_initksn = ByteUtils.hexString2ByteArray("FFFFFFFFFFFFFFFFFFFF");
            iv = ByteUtils.hexString2ByteArray("30303030abCDef99");
            m_plain = ByteUtils.hexString2ByteArray("A12345678A12345678A12345678A12343232323232323232");

            String data = "f1acee231d370a171543cd366bb731349eeacd6590270b43";
            int res1 = Ddi.ddi_dukpt_open();
            if (res1 == 0) {
                Log.v("costtime", "打开设备成功" + res1 + "\r\n");
//                result("打开设备成功" + res1 + "\r\n");
                name.writeExcel3("" + sp, "打开设备成功测试", "打开设备成功成功");

                int res2 = Ddi.ddi_dukpt_inject((byte) 0, (byte) 0, 2, m_initkey1, (byte) 0x10, m_initksn, (byte) 0xa);
                if (res2 == 0) {
                    Log.v("costtime", "打开设备成功" + res1 + "\r\n");
                    name.writeExcel3("" + sp, "打开设备成功测试", "打开设备成功成功");
//                    result("打开设备成功" + res1 + "\r\n");

                    int res3 = Ddi.ddi_dukpt_encrypt(0, 0, (byte) 0, iv, 0, m_plain, m_plain.length, cipher);
                    String str = ByteUtils.byteArray2HexString(cipher);
                    if (res3 == 0 && str.equals(data)) {
                        Log.v("costtime", "加密成功" + res3 + "\r\n");
                        name.writeExcel3("" + sp, "加密成功测试", "加密成功成功");
//                        result("加密成功" + res3 + "\r\n");
                        int res4 = Ddi.ddi_dukpt_close();
                        if (res4 == 0) {
                            Log.v("costtime", "关闭设备成功" + res4 + "\r\n");
                            name.writeExcel3("" + sp, "关闭设备测试", "关闭设备成功");
//                            result("关闭设备成功" + res4 + "\r\n");
                        } else {
                            Log.v("costtime", "DukptEncrypt1关闭设备失败" + res4 + "\r\n");
                            name.writeExcel3("" + sp, "DukptEncrypt1关闭设备测试", "关闭设备失败");
//                            result("DukptEncrypt1关闭设备失败" + res4 + "\r\n");
                        }
                    } else {
                        Log.v("costtime", "DukptEncrypt1加密失败败" + res3 + "\r\n");
                        name.writeExcel3("" + sp, "DukptEncrypt1加密测试", "DukptEncrypt1加密失败");
//                        result("DukptEncrypt1加密失败" + res3 + "\r\n");
                    }

                } else {
                    Log.v("costtime", "DukptEncrypt1灌注密钥失败" + res2 + "\r\n");
                    name.writeExcel3("" + sp, "DukptEncrypt1灌注密钥测试", "DukptEncrypt1灌注密钥失败");
//                    result("DukptEncrypt1灌注密钥失败" + res2 + "\r\n");
                }

            } else {
                Log.v("costtime", "DukptEncrypt1打开设备失败" + res1 + "\r\n");
                name.writeExcel3("" + sp, "DukptEncrypt1打开设备测试", "DukptEncrypt1打开设备失败");
//                result("DukptEncrypt1打开设备失败" + res1 + "\r\n");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    //SM4算法测试
    private void testgroup52() {
        try {
            Log.v("costtime", "testgroup52开始测试");
            name.writeExcel3("" + sp, "testgroup52测试", "成功");
//            result("testgroup52" + "\r\n");
            String data123 = "30303030abCDef99a1da25f1e411fea5";
            byte[] ipIn123 = ByteUtils.hexString2ByteArray(data123);

            String key4 = "ffffffffFFFFFFFFffffffffFFFFFFFF";
            byte[] innerkey4 = ByteUtils.hexString2ByteArray(key4);
            byte[] DataOut = new byte[16];
            String data0 = "EC2E4096473E89AFBD46ADD315411E50";
            byte[] msg_summery1 = ByteUtils.hexString2ByteArray("85e9d210105327171927e8303ef028e1");
            Ddi.ddi_innerkey_open();
            int res0 = Ddi.ddi_innerkey_update_mk((byte) 0x03, 0, innerkey4, 16);
            if (res0 == 0) {
                Log.v("costtime", "SM4更新主密钥成功" + res0 + "\r\n");
                name.writeExcel3("" + sp, "SM4更新主密钥测试", "SM4更新主密钥成功");
//                result("SM4更新主密钥成功" + res0+"\r\n");
                Thread.sleep(1000);
                int res1 = Ddi.ddi_innerkey_update_wk((byte) 0x03, 0, 0, 0, ipIn123, innerkey4, 16, ipOut, 16);
//				工明文：32D00D9AC34566540D2A291DEB71932A

                if (res1 == 0) {
                    Log.v("costtime", "SM4更新工作秘钥成功" + res1 + "\r\n");
                    name.writeExcel3("" + sp, "SM4更新工作秘钥测试", "SM4更新工作秘钥成功");
//                    result("SM4更新工作秘钥成功" + res1+"\r\n");
                    int res5 = Ddi.ddi_innerkey_nes_sm4_encrypt(1, 0, (byte) 0, ipIn, innerkey4, innerkey4.length, DataOut, leOut);//工作密钥加密
                    String data = ByteUtils.byteArray2HexString(DataOut);
                    if (res5 == 0 && data.equalsIgnoreCase(data0)) {
                        Log.v("costtime", "SM4更新工作秘钥加密成功" + res5 + "\r\n");
                        name.writeExcel3("" + sp, "SM4更新工作秘钥加密测试", "SM4更新工作秘钥加密成功");
//                        result("SM4更新工作秘钥加密成功" + res5+"\r\n");
                    } else {
                        Log.v("costtime", "更新工作秘钥加密失败" + res5 + "\r\n");
                        name.writeExcel3("" + sp, "SM4更新工作秘钥加密测试", "SM4更新工作秘钥加密失败");
//                        result("SM4更新工作秘钥加密失败" + res5+"\r\n");
                    }
                } else {
                    name.writeExcel3("" + sp, "更新工作秘钥测试", "更新工作秘钥失败");
                    Log.v("costtime", "更新工作秘钥失败" + res1 + "\r\n");
//                    result("更新工作秘钥失败" + res1+"\r\n");
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private void testBTmac()  {
        Log.v("costtime", "kaishi");

        String getbtmac = GetBTMac.getBtAddressByReflection();

//			mTextView.setText(getbtmac);
        if (getbtmac.equalsIgnoreCase(BT)) {
            name.writeExcel3("" + sp, "获取终端的蓝牙地址测试", "获取终端的蓝牙地址成功");
//            result("获取终端的蓝牙地址成功\r\n");
            Log.v("costtime", "蓝牙地址获取成功" + getbtmac);

        } else {
            name.writeExcel3("" + sp, "获取终端的蓝牙地址测试", "获取终端的蓝牙地址失败");
//            result("获取终端的蓝牙地址失败" + getbtmac + "\r\n");
            Log.v("costtime", "蓝牙地址获取失败" + getbtmac);

        }
    }

    public void second() {


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


    public void android9reboot() {

        Class<?> serviceManager = null;
        try {
            serviceManager = Class.forName("android.os.ServiceManager");

            Method getService = serviceManager.getMethod("getService", String.class);
            Object remoteService = getService.invoke(null, Context.POWER_SERVICE);
            Class<?> stub = Class.forName("android.os.IPowerManager$Stub");
            Method asInterface = stub.getMethod("asInterface", IBinder.class);
            Object powerManager = asInterface.invoke(null, remoteService);
//        Method shutdown = powerManager.getClass().getDeclaredMethod("reboot", boolean.class, String.class, boolean.class);
            Method shutdown = powerManager.getClass().getMethod("reboot", boolean.class, boolean.class);
            shutdown.invoke(powerManager, false, true);
            AppLogger.v("TAG", "开始重启");
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.v("TAG", "重启发生异常");
        }
    }

    public String getExternalStorageDirectory() {
        String endpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xgd_cert.zip";
        Log.v("TAG", "" + endpath);
        return endpath;
    }

    /**
     * @param zipFileName
     * @return
     * @throws Exception 安装证书
     */
    private int setCertHashToK21(String zipFileName) throws Exception {
        ZipFile zip = new ZipFile(zipFileName);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream fis = zip.getInputStream(entry);
            if (entry.getName().contains("xgdandroid.txt") == true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                String certStr = "";
                boolean tmpflag = false;
                int cnt = 0;
                while ((line = br.readLine()) != null) {
                    if (line.contains("[SIGNATURE]")) {
                        line = br.readLine();
                        tmpflag = true;
                    }
                    if (tmpflag == true) {
                        certStr += line + '\r' + '\n';
                    }
                }
                certStr += '\r' + '\n';
                if (tmpflag == true) {
//					Log.i("TAG", "ddi_sys_setCertHash certStr:" + certStr);
//					Log.i("TAG", "ddi_sys_setCertHash certStr len:" + certStr.length());
                    int ret = Ddi.ddi_sys_setCertHash(certStr.getBytes());
//                    Log.i("TAG", "ddi_sys_setCertHash ret:" + ret);
                    Log.v("TAG", "ddi_sys_setCertHash ret:" + ret);
                    return ret;

//					return 0;
                }
            }
        }
        return -1;
    }

    private void testtusn(byte[] data55, byte[] datalen)  {
        int res3 = Ddi.ddi_read_tusn_sn(data55, datalen);
        String str = ByteUtils.asciiByteArray2String(data55);
        Log.v("costtime", "机器tusn为" + str);
        if (res3 == 0 && str.equalsIgnoreCase(TUSN)) {
            name.writeExcel3("" + sp, "机器TUSN获取测试", "机器TUSN获取成功");
//            result("机器TUSN获取成功\r\n");
        } else {
            name.writeExcel3("" + sp, "机器TUSN获取测试", "机器TUSN获取失败" + str);
//            result("机器TUSN获取失败" + str +res3+ "\r\n");
        }
    }


    private void testmainkey()   {
        Log.v("costtime", "开始秘钥测试");
        Ddi.ddi_innerkey_open();
        Log.v("costtime", " 密钥接口打开");
        int res0 = Ddi.ddi_innerkey_update_mk((byte) 0x01, 149, innerkey1, 16);
        Log.v("costtime", " 更新主密钥成功" + res0);

        int res1 = Ddi.ddi_innerkey_update_wk((byte) 0x01, 149, 449, 0, ipIn, innerkey2, 16, ipOut, 16);
        Log.v("costtime", " 更新工作密钥成功" + res1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int res2 = Ddi.ddi_innerkey_des_encrypt(0, 149, 0, ipIn, msg_summery, 16, DataOut);
        // 主密钥加密
        String data = ByteUtils.byteArray2HexString(DataOut);
        Log.v("costtime", " DES加密成功" + data);
        if (res2 == 0 && data.equalsIgnoreCase(data0)) {
            name.writeExcel3("" + sp, "主密钥加密测试", "主密钥加密成功");
//            result("主密钥加密成功：\r\n");
            Log.v("costtime", "开始获取TUSN");
        } else {
            name.writeExcel3("" + sp, "主密钥加密测试", "主密钥加密失败");
//            result("主密钥加密失败：" + data + "\r\n");
        }
    }

    private void getcardversion()  {
        int res0 = Ddi.ddi_mag_open();
        if (res0 == 0) {
            name.writeExcel3("" + sp, "磁卡打开测试", "磁卡打开成功");
//            result("磁卡打开成功\r\n");
        }
        Arrays.fill(wParam, (byte) 0);
        int ret10 = Ddi.ddi_mag_ioctl_getVer(wParam);
        if (ret10 == 0) {


            String byteArray2HexString = ByteUtils.byteArray2HexString(wParam);
            if (byteArray2HexString.equalsIgnoreCase(card)) {
                Log.v("costtime", "磁卡获取版本成功\\r\\n");
                name.writeExcel3("" + sp, "磁卡获取版本测试", "磁卡获取版本成功");
//                result("磁卡获取版本成功\r\n");

            }
        } else {
            Log.v("costtime", "磁卡打开失败\\r\\n");
            name.writeExcel3("" + sp, "磁卡打开测试", "磁卡打开失败");
//            result("磁卡打开失败\r\n"+"返回值"+ret10);
        }
    }

    /**
     * 测试用例执行测试
     *
     * @throws Exception
     */
    // star方法
    private void start() throws Exception {
        Ddi ddi = new Ddi();

        Ddi.ddi_ddi_sys_init();

        final String m_cipherauk = "12345678abcdefbfabcdefbcabcdefbf";
        final byte[] cipherauk = ByteUtils.hexString2ByteArray(m_cipherauk);
        final byte[] DataOut3 = new byte[16];
        final byte[] DataOut4 = new byte[16];
        final String data6 = "8b18c930601f4ad94573f487b9406d95";
        int hashsucesstime = 0;
        int dsnsucesstime = 0;
        int total = 10;
        leOut = new int[1];
        String m_cipher = "12345678abcdefbf";
        final byte[] cipher = ByteUtils.hexString2ByteArray(m_cipher);
        final String data3 = "23b4e1818650c0f39baab6669e063956";
        final byte[] mk_cipher = new byte[8];
        int ret = -2;
        String macAddress2 = null;
        String data2 = "12345678abcdEFBFABCDefBCabcdEFBF";
        msg_summery = ByteUtils.hexString2ByteArray(data2);
        String BTMacaddress = "";
        int ret1 = -2;
        long retsult = 25000;
//		String data0 = "8b18c930601f4ad94573f487b9406d95";
        final byte[] data55 = new byte[19];
        final byte[] datalen = new byte[4];
        long while_time = 0, while_time1, while_time2;
        try {
            long begin = System.currentTimeMillis();
            name.writeExcel3("" + sp, "开始循环测试", "成功");
//            result("开始循环");
            Log.v("costtime", "开始循环测试============");





                while_time = System.currentTimeMillis();
                // 获取证书是否存在
                ret = ddi.ddi_sys_getCertHash(new byte[512]);

                Log.v("costtime", "ddi_sys_getCertHash:" + ret + "  i = " );
                if (ret == 0) {
                    name.writeExcel3("" + sp, "ddi_sys_getCertHash" , "成功");
                }
                while_time1 = System.currentTimeMillis();
                // 读取机身号
                byte[] lpOut = new byte[256];
                ret1 = ddi.ddi_sys_read_dsn(lpOut);
                String data = ByteUtils.asciiByteArray2String(lpOut);

                if(data.equalsIgnoreCase(inputinfoactivity.sSN )|| inputinfoactivity.sSN.equalsIgnoreCase(SN)){
                    name.writeExcel3("" + sp, "获取机身号测试", "证书获取成功");
                }else{
                    name.writeExcel3("" + sp, "获取机身号测试", "证书获取失败");
                }
                Log.v("costtime", "ddi_sys_read_dsn:" + ret + "  i = "  + data);
                while_time2 = System.currentTimeMillis();
                if (ret1 != DdiConstant.DDI_TIMEOUT && ret != DdiConstant.DDI_TIMEOUT ) {
                    dsnsucesstime++;
                    name.writeExcel3("" + sp, "证书获取测试", "证书获取成功");
//                    result("证书获取成功\r\n");
//                    name.writeExcel3(""+sp,"机身号获取测试","证书获取成功");
//                    result("机身号获取成功\r\n");
                    name.writeExcel3("" + sp, "机身号获取成功", "成功");
                }
                Log.v("costtime", "show time 1 " + (while_time1 - while_time) + " 2 = " + (while_time2 - while_time1));



            String externalStorageDirectory = getExternalStorageDirectory();
            int i = setCertHashToK21(externalStorageDirectory);
            if (i == 0) {
                name.writeExcel3("" + sp, "证书保存测试", "成功");
            } else {
                name.writeExcel3("" + sp, "证书保存测试", "失败");
            }



            Thread getwifimac=new Thread(new Runnable() {
                @Override
                public void run() {
                    // 获取WIFIMAC地址
                    testWIFImac();
                    // 获取蓝牙地址
                    testBTmac();

                }
            });
            getwifimac.start();
            getwifimac.join();

              Thread innerkey=new Thread(new Runnable() {
                              @Override
                              public void run() {
                                  // 检查主密钥
                                  testmainkey();
                                  testtusn(data55, datalen);
                                  //获取磁卡版本
                                  getcardversion();
                                  //国密SM2加密
                                  testSM2update2();
                                  //国密SM4加密测试
                                  testgroup52();
                                  //AES算法测试
                                  testgroup53();
                                  //DUKPT秘钥测试
                                  testDukptEncrypt1();
                              }
                          });
              innerkey.start();
              innerkey.join();

                Thread TEKAUK=new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Ddi.ddi_innerkey_open();
                                    int res4 = Ddi.ddi_innerkey_update_mk_cipher((byte) 0, (byte) 0, (byte) 2, (byte) 0, ipIn, cipher, cipher.length, (byte) 0, mk_cipher);
                                    if (res4 == 0) {
                                        Log.v("costtime", "TEk更新主密钥成功");
                                        name.writeExcel3("" + sp, "TEk更新主密钥测试", "TEk更新主密钥成功");
//                result("TEk更新主密钥成功\r\n");
                                        int res5 = Ddi.ddi_innerkey_des_encrypt(0, 2, 0, ipIn, msg_summery, 16, DataOut);
                                        String data4 = ByteUtils.byteArray2HexString(DataOut);
                                        if (res5 == 0 && data4.equalsIgnoreCase(data3)) {
                                            Log.v("costtime", "TEk校验成功");
                                            name.writeExcel3("" + sp, "TEK校验测试", "TEK校验成功");
//                    result("TEK校验成功\r\n");
                                        } else {
                                            name.writeExcel3("" + sp, "TEK校验测试", "TEK校验失败");
                                            Log.v("costtime", "TEk校验失败");
//                    result("TEK校验失败" + "\r\n");
                                        }
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        int res6 = Ddi.ddi_auk_data_process((byte) 1, (byte) 0, (byte) 0, 2, (byte) 0, ipIn, cipherauk,
                                                cipherauk.length, DataOut3);
                                        String data1 = ByteUtils.byteArray2HexString(DataOut3);
                                        if (res6 == 0 && data1.equalsIgnoreCase(data6)) {
                                            Log.v("costtime", "AUK加密校验成功");
                                            name.writeExcel3("" + sp, "AUK加密测试", "AUK加密成功");
//                    result("AUK加密成功\r\n");
                                        } else {
                                            Log.v("costtime", "AUK加密校验失败");
                                            name.writeExcel3("" + sp, "AUK加密测试", "AUK加密失败");
//                    result("AUK加密加密失败"+data1+"\r\n");
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        int res7 = Ddi.ddi_auk_data_process((byte) 0, (byte) 0, (byte) 0, 2, (byte) 0, ipIn, DataOut3,
                                                DataOut3.length, DataOut4);
                                        String data7 = ByteUtils.byteArray2HexString(DataOut4);
                                        if (res7 == 0 && data7.equalsIgnoreCase(m_cipherauk)) {
                                            Log.v("costtime", "AUK解密成功");
                                            name.writeExcel3("" + sp, "AUK解密测试", "AUK解密成功");
                                        } else {
                                            Log.v("costtime", "AUK解密失败");
                                            name.writeExcel3("" + sp, "AUK解密测试", "AUK解密失败");
                                        }
                                    } else {
                                        name.writeExcel3("" + sp, "TEk更新主密钥测试", "TEk更新主密钥失败");
                                    }
                                }
                            });

                TEKAUK.start();
                TEKAUK.join();
    } catch (Exception e) {

    }
        Ddi.ddi_innerkey_close();
        // retsult <= 25000

//			Intent shutdownintent = new Intent();
//			shutdownintent.setAction("com.xgd.powermanager.REBOOT");
//			sendBroadcast(shutdownintent);

    }

    /**
     * 点击按钮对应的方法
     *
     * @param v
     */
    public void runMyUiautomator(View v) {
        Log.i(TAG, "runMyUiautomator: ");
        new UiautomatorThread().start();
        Toast.makeText(this, "start run", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceConnected(ComponentName name1, IBinder service) {
        {
            // TODO Auto-generated method stub

            Log.v("costtime", "onServiceConnected");
            // mBinder = DataBackup.Stub.asInterface(service);
            mICloudService = ICloudService.Stub.asInterface(service);
            if (mICloudService != null) {
                try {
                    Log.v("costtime", "mICloudService");
                    binder = mICloudService.getManager(APP_MANAGER);

                    mIAppManager = IAppManager.Stub.asInterface(binder);
                    // 安装APP

                    File file = new File("/sdcard/umsapp-release.apk");
                    if (file.exists()) {
                        file.delete();
                    }

                    InputStream is = getAssets().open("umsapp-release.apk");
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] temp = new byte[1024];
                    int i = 0;
                    while ((i = is.read(temp)) > 0) {
                        fos.write(temp, 0, i);
                    }
                    fos.close();
                    is.close();
                    IAppInstallObserver.Stub observer1 = new IAppInstallObserver.Stub() {
                        @Override
                        public void onInstallFinished(String packageName, int returnCode, String msg)
                                throws RemoteException {
                            Log.v("costtime", "IAppDeleteObserver basePackageName:" + packageName);
                            Log.v("costtime", "IAppDeleteObserver returnCode:" + returnCode);
                            Log.v("costtime", "IAppDeleteObserver msg:" + msg);
                            try {
                                Log.v("costtime", "开始安装应用");
//                                name.writeExcel3(""+sp,"开始安装应用测试","开始安装应用成功");
//                                result("开始安装应用"+"\r\n");
//                                result("检验完成耗时：" + packageName + "\r\n");
//                                result("检验完成耗时：" + returnCode + "\r\n");
//                                result("检验完成耗时：" + msg + "\r\n");
                                Log.v("TAG", "应用安装完成");

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }

                        ;
                    };
                    mIAppManager.installApp("/sdcard/umsapp-release.apk", observer1, "com.example.readinformation");
                    name.writeExcel3("" + sp, "安装应用测试", "安装应用成功");
                    // 接口测试
                    start();
                    IAppDeleteObserver.Stub observer = new IAppDeleteObserver.Stub() {

                        @Override
                        public void onDeleteFinished(String packageName, int returnCode, String msg)
                                throws RemoteException {
                            Log.v("costtime", "IAppDeleteObserver basePackageName:" + packageName);
                            Log.v("costtime", "IAppDeleteObserver returnCode:" + returnCode);
                            Log.v("costtime", "IAppDeleteObserver msg:" + msg);
                            try {
//                                result("检验完成耗时：" + packageName + "\r\n");
//                                result("检验完成耗时：" + returnCode + "\r\n");
//                                result("检验完成耗时：" + msg + "\r\n");
//                                result("卸载应用完成"+"\r\n");
                                Log.v("costtime", "应用卸载完成");
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    };
//这个com.xgd.umsapp是包名所以N5和N6是一样的
                    mIAppManager.uninstallApp("com.xgd.umsapp", observer);
                    name.writeExcel3("" + sp, "卸载应用测试", "卸载应用成功");
                    Log.v("costtime", "卸载完成");
                    mIAppManager.installAppReboot("/sdcard/umsapp-release.apk", null, true);
                    name.writeExcel3("" + sp, "卸载应用并重启测试", "卸载应用并重启测试成功");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.v("TAG", "服务断开连接");


    }

    @Override
    public void onBindingDied(ComponentName name) {

    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = generateCommand("zhepan.com.mytestcase", "ExampleInstrumentedTest", "useAppContext");
            CMDUtils.CMD_Result rs = CMDUtils.runCMD1(command, true, true);
//            Log.e(TAG, "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         *
         * @param pkgName uiautomator包名
         * @param clsName uiautomator类名
         * @param mtdName uiautomator方法名
         * @return
         */
        public String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument -w -r -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }

    }
}
