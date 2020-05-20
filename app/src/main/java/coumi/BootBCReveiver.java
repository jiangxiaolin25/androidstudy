package coumi;

import com.example.myapplication.MainActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBCReveiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Intent intent = new Intent(arg0, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(intent);
	}

}
