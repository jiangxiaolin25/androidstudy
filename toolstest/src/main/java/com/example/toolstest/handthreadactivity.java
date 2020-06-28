package com.example.toolstest;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：jiangxiaolin on 2020/6/3
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class handthreadactivity extends AppCompatActivity {
     private Button bt3,bt5,handpost;
     private TextView mTextView;
    private Handler workHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handpostactivity);
        bt3=(Button) findViewById(R.id.btdelay3);
        bt5=(Button) findViewById(R.id.btdelay5);
        handpost=(Button) findViewById(R.id.bthandpost);
        mTextView=(TextView) findViewById(R.id.TV1);
         final Handler mainHandler = new Handler();
        HandlerThread myhandthread = new HandlerThread("myhandthread");
        myhandthread.start();
        workHandler = new Handler(myhandthread.getLooper()){
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg)
            {
                //设置了两种消息处理操作,通过msg来进行识别
                switch(msg.what){
                    // 消息1
                    case 1:
                        try {
                            //延时操作
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                mTextView.setText("我爱学习");
                            }
                        });
                        break;

                    // 消息2
                    case 2:
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                mTextView.setText("我不喜欢学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
        handpost.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        SystemClock.sleep(10000);

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                mTextView.setText("handpost");
                            }
                        });



        			}

        		});
        bt3.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        // a. 定义要发送的消息
                        Message msg = Message.obtain();
                        msg.what = 1; //消息的标识
                        msg.obj = "A"; // 消息的存放
                        // b. 通过Handler发送消息到其绑定的消息队列
                        workHandler.sendMessage(msg);


        			}

        		});
        bt5.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        Message msg = Message.obtain();
                        msg.what = 2; //消息的标识
                        msg.obj = "B"; // 消息的存放
                        // b. 通过Handler发送消息到其绑定的消息队列
                        workHandler.sendMessage(msg);


        			}

        		});


    }
}
