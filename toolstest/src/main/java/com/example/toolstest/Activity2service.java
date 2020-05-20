package com.example.toolstest;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.myservice.Myservices;
import com.myservice.servicetoactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class Activity2service extends AppCompatActivity {
     private Button meventservice,meventactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventbus);
        meventservice=(Button) findViewById(R.id.BTeventbus);
        meventactivity=(Button) findViewById(R.id.BTactivity);
        final Intent intent = new Intent(this, servicetoactivity.class);
        startService(intent);
        meventservice.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                          startService(intent);
        			}
        		});
        meventactivity.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
//                        Intent intent=new Intent(this,MyActivity.class);
//                        startActivity(intent);


        			}

        		});






    }

}
