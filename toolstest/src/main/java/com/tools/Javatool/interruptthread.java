package com.tools.Javatool;

import android.os.SystemClock;
import android.util.Log;

import com.tools.Androidtool.AppLogger;

/**
 * 作者：jiangxiaolin on 2020/5/27
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class interruptthread extends Thread {
    Object mObject;

    public interruptthread(Object obj) {
        super();
        this.mObject=obj;
    }
    boolean flag=false;

    public  void mwait() {
        synchronized (mObject){
            try {
                mObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                AppLogger.v("TAG","wait");
            }


        }



    }
    public void setflag() {
        flag=true;

        }
        public void setflagfalse() {
        flag=false;

        }



    @Override
    public void run() {
        super.run();
        for(int i=0;i<1000;i++){
            if(flag){
                mwait();
            }
            if(currentThread().isInterrupted()){
//                Log.v("TAG","isInterrupted"+i);
                AppLogger.v("TAG","Interrupted"+i);
                SystemClock.sleep(1000);
            }else{
//                Log.v("TAG","isInterrupted"+i);
                AppLogger.v("TAG","没有Interrupted"+i);
                SystemClock.sleep(1000);
            }
        }






    }
}
