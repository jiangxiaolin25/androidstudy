package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



/**
 * 作者：jiangxiaolin on 2020/5/15
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class inputinfoactivity extends AppCompatActivity {

    private Button mButton;
     private EditText EDBT,EDWLAN,EDSN,EDTUSN;
     static String sBT,sWLAN,sSN,sTUSN;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputinfolayout);
        mButton=(Button) findViewById(R.id.start);
        EDBT=(EditText) findViewById(R.id.BTadd);
        EDWLAN=(EditText) findViewById(R.id.WIFIadd);
        EDSN=(EditText) findViewById(R.id.SN);
        EDTUSN=(EditText) findViewById(R.id.TUSN);
        final Intent intent=new Intent(this, MainActivity.class);



        mButton.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        //需要添加一个异常，不然会报错

                        sBT = EDBT.getText().toString().trim();
                        sWLAN = EDWLAN.getText().toString().trim();
                        sSN = EDSN.getText().toString().trim();
                        sTUSN = EDTUSN.getText().toString().trim();
                        intent.setAction("android.intent.action.autotest");
                        intent.addCategory("android.intent.category.autotest");
                        startActivity(intent);

        			}

        		});






    }
}
