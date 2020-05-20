package com.example.toolstest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myservice.servicetoactivity;
import com.other.eventbuscalss;
import com.tools.Androidtool.AppLogger;
import com.tools.Javatool.mytimeoperate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class secondactivity extends AppCompatActivity {
     private Button mButton;
    private Button meventservice,meventactivity;
    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        mButton=(Button) findViewById(R.id.gettime);
        meventservice=(Button) findViewById(R.id.bteventser);
        meventactivity=(Button) findViewById(R.id.bteventac);
        mTextView=(TextView) findViewById(R.id.TV1);
          final Intent intent = new Intent(this, servicetoactivity.class);
          intent.setAction("com.jay.example.service.TEST_SERVICE1");
          AppLogger.v("TAG","secondactivity");
        EventBus.getDefault().register(this);


        mButton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                 SimpleDateFormat sdf = new SimpleDateFormat("mm-ss", Locale.CHINA);// 日期格式名定义
                         String fname =sdf.format(new Date());
                      AppLogger.v("TAG","onStartCommand"+fname);
                      mytimeoperate name=new mytimeoperate();
                        String week = name.getWeek();
                        AppLogger.v("TAG",""+week);
                    }

        		});

        meventservice.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {

        				// TODO Auto-generated method stub
                  AppLogger.v("TAG","meventservice");
                          startService(intent);
        			}

        		});

        meventactivity.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub




        			}

        		});










    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING,priority = 5,sticky = true)
    public void name(eventbuscalss eventbuscalss) {
        mTextView.setText(eventbuscalss.getMes());
        }


}
