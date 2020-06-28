package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinguodu.ddiinterface.Ddi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import coumi.GetBTMac;


/**
 * 作者：jiangxiaolin on 2020/5/15
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class inputinfoactivity extends AppCompatActivity {

    private Button mButton;
     private EditText EDBT,EDWLAN,EDSN,EDTUSN;
     static String sBT,sWLAN,sSN,sTUSN;
    byte[] lpOut = new byte[256];
    Ddi ddi;
    final byte[] data55 = new byte[19];
    final byte[] datalen = new byte[4];
    static String version;
    static String mode;
    static String BTmac="40:c8:1f:73:6f:f6";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputinfolayout);
        mButton = (Button) findViewById(R.id.start);
        EDBT = (EditText) findViewById(R.id.BTadd);
        EDWLAN = (EditText) findViewById(R.id.WIFIadd);
        EDTUSN = (EditText) findViewById(R.id.TUSN);
        EDSN = (EditText) findViewById(R.id.SN);
        version = Build.VERSION.RELEASE;
        mode = Build.MODEL;
        if (mode.equalsIgnoreCase("N86")) {
            EDBT.setText(BTmac);
        } else {
            String getbtmac = GetBTMac.getBtAddressByReflection();
            Log.v("TAG", "蓝牙地址：" + getbtmac);
            EDBT.setText(getbtmac);
        }

        String wifiMacAddress = getWifiMacAddress();
        ddi = new Ddi();
        int ret1 = ddi.ddi_sys_read_dsn(lpOut);
        String data = ByteUtils.asciiByteArray2String(lpOut);
        int res3 = Ddi.ddi_read_tusn_sn(data55, datalen);
        String tusn = ByteUtils.asciiByteArray2String(data55);
        EDWLAN.setText(wifiMacAddress);
        EDSN.setText(data);
        EDTUSN.setText(tusn);
        verifyStoragePermissions(this);
//        try {
//            testtoolclass.creattitle();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        final Intent intent = new Intent(this, MainActivity.class);
        mButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //需要添加一个异常，不然会报错
//                if(mode.equalsIgnoreCase("N86")){
//                    sBT =BTmac;
//                }else{
//
//                }
                sBT = EDBT.getText().toString().trim();
                sWLAN = EDWLAN.getText().toString().trim();
                sSN = EDSN.getText().toString().trim();
                sTUSN = EDTUSN.getText().toString().trim();
                Log.v("TAG", "" + sBT + sSN + sTUSN + sWLAN);
                intent.setAction("android.intent.action.autotest");
                intent.addCategory("android.intent.category.autotest");
                startActivity(intent);

            }

        });


    }

    /**获取蓝牙地址
     * @return
     */
    public static String getLocalMacIdFromIp(){
        String strMacAddr = "";
        try {
            InetAddress ip = getLocalInetAddress();

            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }

                String str = Integer.toHexString(b[i]&0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toLowerCase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strMacAddr;
    }


    /**
     * 获取设备本地IP
     */
    protected static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = (InetAddress) en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ip;
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
}
