package com.broadcastrecives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Environment;

import com.tools.Androidtool.AppLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * 作者：jiangxiaolin on 2020/5/6
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class getvoltagerecives extends BroadcastReceiver {

    private int mvoltage, mtemperature;
    private String voltagemes,mes;
    private String BatteryStatus,BatteryTemp;
    private vollatames vollatames;

   public interface vollatames{
        public void getMsg(int lev,int volate);

    }
    public void setMessage(vollatames message) {
               this.vollatames = message;
             }

    @Override
    public void onReceive(Context context, Intent intent) {


        AppLogger.v("TAG","onReceive");
        int level = intent.getIntExtra("level", 0);
        AppLogger.v("TAG","level..."+level);
//        voltagemes=level+",";

        mvoltage=intent.getIntExtra("voltage", 0) ;
        AppLogger.v("TAG","voltage..."+mvoltage);


        mtemperature=intent.getIntExtra("temperature",0)/10 ;
        AppLogger.v("TAG","temperature..."+mtemperature);

        int  status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
        AppLogger.v("TAG","status..."+status);
        int  health = intent.getIntExtra("health",
                BatteryManager.BATTERY_HEALTH_UNKNOWN);
        AppLogger.v("TAG","health..."+health);

        switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN))
        {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                AppLogger.v("TAG","充电状态...");
                BatteryStatus = "充电中";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                AppLogger.v("TAG","放电状态...");
                BatteryStatus = "放电中";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                AppLogger.v("TAG","未充电...");
                BatteryStatus = "未充电";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                AppLogger.v("TAG","充满电...");
                BatteryStatus = "充满电";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                AppLogger.v("TAG","未知道状态...");
                BatteryStatus = "未知道";
                break;
        }

        switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN))
        {
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                AppLogger.v("TAG","未知错误...");
                BatteryTemp = "未知错误";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                AppLogger.v("TAG","状态良好...");
                BatteryTemp = "状态良好";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                AppLogger.v("TAG","电池没有电...");
                BatteryTemp = "电池没有电";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                AppLogger.v("TAG","电池电压过高...");
                BatteryTemp = "电池电压过高";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                AppLogger.v("TAG","电池过热...");
                BatteryTemp =  "电池过热";
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM:dd_HH:mm", Locale.CHINA);// 日期格式名定义
            String fname =sdf.format(new Date());
        voltagemes=fname+","+level+","+mvoltage+","+mtemperature+","+BatteryStatus+","+BatteryTemp;

//        mes="电池电量："+level+"电池电压："+mvoltage;
//        vollatames.getMsg(level,mvoltage);





        try {
            resultaddbatter(voltagemes);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public getvoltagerecives() {
        super();


    }

    public static void resultaddbatter(String result) throws Exception {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "电池曲线" + ".txt");
//        if (file.exists()){
//            file.delete();
//            file.createNewFile();
//        }else {
//            file.createNewFile();
//        }
//        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);// 日期格式名定义
//        String fname =sdf.format(new Date());

        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GBK"));

        output.append(result+"\r\n");
        output.close();


    }
//    public void putstringSP(int i) {
//        SharedPreferences.Editor editor = this.getSharedPreferences("data", MODE_PRIVATE).edit();
//        editor.putInt("key", i);
//        editor.commit();
//
//
//    }

}




