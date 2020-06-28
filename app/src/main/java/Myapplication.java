import android.content.Context;

import com.example.myapplication.ByteUtils;
import com.xinguodu.ddiinterface.Ddi;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import androidx.multidex.MultiDexApplication;
import coumi.GetBTMac;

public class Myapplication extends MultiDexApplication {
    public Myapplication() {
        super();
    }

   static String appSN;
    String appTUSN;
    String appBT;
     String  appWLAN;
     String  mode;
    byte[] lpOut = new byte[256];

    final byte[] data55 = new byte[19];
    final byte[] datalen = new byte[4];
    Ddi ddi;




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        appWLAN = getWifiMacAddress();
        appBT = GetBTMac.getBtAddressByReflection();
        ddi = new Ddi();
        int ret1 = ddi.ddi_sys_read_dsn(lpOut);
        appSN = ByteUtils.asciiByteArray2String(lpOut);
        int res3 = Ddi.ddi_read_tusn_sn(data55, datalen);
        appTUSN = ByteUtils.asciiByteArray2String(data55);



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
