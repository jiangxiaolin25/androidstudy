package tools.Androidtool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 获取安卓可用内存的大小
 */
public class getAndroid {
    public  long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        // 单位Byte
        return info.availMem;
    }

//    public  long getTotalMem() {
//        try {
//            String FILE_MEMORY;
//            FileReader fr = new FileReader(FILE_MEMORY);
//            BufferedReader br = new BufferedReader(fr);
//            String text = br.readLine();
//            String[] array = text.split("\\s+");
//            Log.w("TAG", text);
//            // 单位为KB
//            return Long.valueOf(array[1]);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }


    /**
     * 获取安卓的内存，单位是G
     * @return
     */
//public  String getAvailableExternalMemorySize2() {
//    if(!Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)){
//        //sdcard状态是没有挂载的情况
//        return  ""+-1 ;
//    }
//    File sdcard_filedir = Environment.getExternalStorageDirectory();//得到sdcard的目录作为一个文件对象
//    long usableSpace = sdcard_filedir.getUsableSpace();//获取文件目录对象剩余空间
//    long totalSpace = sdcard_filedir.getTotalSpace();
//    //将一个long类型的文件大小格式化成用户可以看懂的M，G字符串
//    String usableSpace_str = Formatter.formatFileSize(this, usableSpace);
//    String totalSpace_str = Formatter.formatFileSize(this, totalSpace);
//    if(usableSpace < 1024 * 1024 * 200){//判断剩余空间是否小于200M
//
//        return usableSpace_str;
//    }
//    return usableSpace_str;
//
//}

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @return 获取安卓内部存储
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

    /**
     * @return 获取安卓内部存储
     */
//    private String getAvailMemory() {// 获取android当前可用内存大小
//
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//        am.getMemoryInfo(mi);
//        //mi.availMem; 当前系统的可用内存
//
//        return Formatter.formatFileSize(getBaseContext(), mi.availMem);// 将获取的内存大小规格化
//    }

}
