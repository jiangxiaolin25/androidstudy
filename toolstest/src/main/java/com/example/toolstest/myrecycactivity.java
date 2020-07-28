package com.example.toolstest;

import android.os.Bundle;

import com.other.smartproject;

import com.tools.Androidtool.RecycleViewDivider;
import com.tools.Androidtool.myrecyclerviewadapte;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 作者：jiangxiaolin on 2020/6/30
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class myrecycactivity  extends AppCompatActivity {

    List<smartproject>  smartprojectlist;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrecyview);

        smartprojectlist =new ArrayList<smartproject>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        initdata();
        myrecyclerviewadapte recyclerAdapter =new myrecyclerviewadapte(smartprojectlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));
        //设置item增加和删除的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);



    }

    public void initdata() {
        smartproject N5SSM=new smartproject("N5S/N86扫码测试",R.mipmap.n5s);
        smartprojectlist.add(N5SSM);
        smartproject N5SRF=new smartproject("N5S/N86非接测试",R.mipmap.n5s);
        smartprojectlist.add(N5SRF);
        smartproject N6SM =new smartproject("N6扫码测试",R.mipmap.n6);
        smartprojectlist.add(N6SM);
        smartproject N6RF=new smartproject("N6非接测试",R.mipmap.n6);
        smartprojectlist.add(N6RF);
        smartproject P100SM=new smartproject("P100扫码测试",R.mipmap.p100);
        smartprojectlist.add(P100SM);
        smartproject P100RF=new smartproject("P100非接测试",R.mipmap.p100);
        smartprojectlist.add(P100RF);
        smartproject SMservices=new smartproject("扫码服务",R.mipmap.smservices);
        smartprojectlist.add(SMservices);
        smartproject ccb=new smartproject("建行招标",R.mipmap.ccb);
        smartprojectlist.add(ccb);



        }

}
