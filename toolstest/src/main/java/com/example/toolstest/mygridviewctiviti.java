package com.example.toolstest;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：jiangxiaolin on 2020/6/28
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class mygridviewctiviti extends AppCompatActivity {

    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.install, R.drawable.n5s,
            R.drawable.n5s, R.drawable.n6, R.drawable.n6,
            R.drawable.p100, R.drawable.p100, R.drawable.smservices,
            R.drawable.ccb, R.drawable.p100, R.drawable.p100,R.drawable.world,
            R.drawable.youtube };

    private String[] iconName = { "预安装应用","N5S/N86扫码", "N5S/N86非接", "N6扫码", "N6非接", "P100扫码", "P100非接", "扫码服务",
            "建行招标", "P200扫码", "P200非接", "浏览器", "视频" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygridview);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        data_list= getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.mygridviewitem, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);


    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }
}
