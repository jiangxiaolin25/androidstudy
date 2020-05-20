package com.broadcastrecives;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.toolstest.MainActivity;
import com.myservice.Myservices;
import com.tools.Androidtool.AppLogger;

public class BootBCReveiver extends BroadcastReceiver {
	private static final String TAG = "MainActivity123";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		//启动服务
		AppLogger.v("TAG","重启开始一个A服务");
		final Intent intent = new Intent(arg0, Myservices.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			//android8.0以上通过startForegroundService启动service
			arg0.startForegroundService(intent);
		} else {
			arg0.startService(intent);
		}
    //启动应用
//		Log.v(TAG,"开始打开应用");
//		Intent intent = new Intent(arg0, MainActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		arg0.startActivity(intent);



	}

}
