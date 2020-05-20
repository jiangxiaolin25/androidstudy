package com.example.toolstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.myservice.MyIntentService;
import com.myservice.Myservices;
import com.tools.Androidtool.AppLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：jiangxiaolin on 2020/5/7
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Mpandroidchartactivity extends AppCompatActivity {
    LineChart mLineChart;
    List<Entry> entries;
     private Button btsta,btgenerater,btcappic;

    int v=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmpandroid);
         mLineChart = (LineChart) findViewById(R.id.mvDetailLineChart);
        btsta=(Button) findViewById(R.id.staservices);
        btgenerater=(Button) findViewById(R.id.genechart);
        btcappic=(Button) findViewById(R.id.jietu);
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
         entries = new ArrayList<>();
         IntentFilter intentFilter = new IntentFilter();
         intentFilter.addAction("batter.level");
         mybroadrecive receiver = new mybroadrecive();
         registerReceiver(receiver, intentFilter);




        final Intent intent = new Intent(this, MyIntentService.class);
        intent.setAction("com.jay.example.service.MyIntentService");

        btsta.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //android8.0以上通过startForegroundService启动service
                            AppLogger.v("TAG","startForegroundService");
                            startForegroundService(intent);
                        } else {
                            AppLogger.v("TAG","startService");
                            startService(intent);
                        }



        			}

        		});
        btgenerater.setOnClickListener(new Button.OnClickListener() {

        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
//                        for (int i = 0; i < 20; i++) {
//                            float v = (float) (Math.random()) * 80;
//
////            SimpleDateFormat sdf = new SimpleDateFormat("ss", Locale.US);// 日期格式名定义
////            String fname = sdf.format(new Date());
////            int i1 = Integer.parseInt(fname);
//                            entries.add(new Entry(i, (v)));
//                            AppLogger.v("TAG", i + "...." + v);
//                        }
//        一个LineDataSet就是一条线
                        LineDataSet lineDataSet = new LineDataSet(entries, "电池曲线");
                        //线条的效果
                        setLine(lineDataSet);
                        //设置提示信息
                        setLenged();
                        //限制线
//        maxLine();
                        //
                        XwangGe();
                        //
                        YwangGe();
                        //
//        yingcang();
                        LineData data = new LineData(lineDataSet);
                        mLineChart.setData(data);





        			}

        		});







    }
    class mybroadrecive extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {


                int lev = intent.getIntExtra("lev", 0);

                entries.add(new Entry(v,lev ));
                v++;
            AppLogger.v("TAG","lev"+lev+"v..."+v);






        }
    }



    //就是线条的效果
    private void setLine(LineDataSet set) {
        //设置线条的颜色
        set.setColor(Color.RED);
        //虚线模式下绘制直线
        set.enableDashedLine(20f, 5f, 0f);
        //点击后高亮线的显示颜色
        set.enableDashedHighlightLine(50f, 15f, 0f);

        //设置数据小圆点的颜色
        set.setCircleColor(Color.GREEN);
        //设置圆点是否有空心
        set.setDrawCircles(true);
        //设置线条的宽度，最大10f,最小0.2f
        set.setLineWidth(1f);
        //设置小圆点的半径，最小1f，默认4f
        set.setCircleRadius(5f);
        //设置是否显示小圆点
        set.setDrawCircles(true);
        //设置字体颜色
        set.setValueTextColor(Color.BLUE);
        //设置字体大小
        set.setValueTextSize(20f);
        //设置是否填充
        set.setDrawFilled(true);
    }

    private void setLenged(){
        Legend legend=mLineChart.getLegend();
        legend.setTextColor(Color.RED);
        legend.setTextSize(20f);
        //设置图例垂直对齐
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //设置图例居中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //设置图例方向
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置图例是在图内还是图外绘制
        legend.setDrawInside(false);
        //图例条目之间距离
        legend.setXEntrySpace(4f);
        //设置图例可否换行
        legend.setWordWrapEnabled(true);
        //设置图例现状为线.默认为方形
        // legend.setForm(Legend.LegendForm.LINE);
        //是否隐藏图例/true_否，false_是
        legend.setEnabled(true);
    }

    private void maxLine(){
        //设置限制线
        LimitLine l1=new LimitLine(60f,"限制线");
        l1.setLineWidth(4f);
        l1.setTextSize(20f);
        l1.setLineColor(Color.RED);
        //允许在虚线模式下绘制(线段长度，分隔长度，偏移量)
        l1.enableDashedLine(10f,10f,0f);
        //设置限制线标签的位置
        l1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        l1.setTextSize(10f);
        //添加限制线
        mLineChart.getAxisLeft().addLimitLine(l1);
        //是否隐藏限制线/true_否，false_是
        l1.setEnabled(true);
    }

    private void XwangGe(){
        //设置x轴网格线
        XAxis xAxis=mLineChart.getXAxis();
        //以虚线模式画网格线
        xAxis.enableGridDashedLine(10f,10f,0f);
        //设置x轴最大值
        xAxis.setAxisMaximum(400f);
        //设置x轴最小值
        xAxis.setAxisMinimum(0f);

        //撤销设置的最大值，让轴自动计算
        xAxis.resetAxisMaximum();
        //撤销设置的最小值，让轴自动计算
        xAxis.resetAxisMinimum();
//        //设置x轴标签数，默认为6个
        xAxis.setLabelCount(15);
//        //设置x轴标签数，若强制启用true，可能导致轴上的数字不均匀
//        xAxis.setLabelCount(10,true);

        //设置x轴之间的最小间隔。用于在图表放大后标签不至于重合
        xAxis.setGranularity(1f);
        //设置x轴轴线的宽度
        xAxis.setAxisLineWidth(1f);
        //设置轴线的颜色
        xAxis.setAxisLineColor(Color.BLUE);
        //设置x轴显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }
    //绘制Y轴网格线，有些方法上面也有，这里就不多加了
    private void YwangGe(){
        //y轴默认显示两个轴线，左右

        //获取图表左边y轴
        YAxis left=mLineChart.getAxisLeft();
        //是否绘制0所在的网格线/默认false绘制
        left.setDrawZeroLine(true);
        //将网格线设置为虚线模式
        left.enableGridDashedLine(10f,10f,0f);
        //获取图表右边y轴
        YAxis right=mLineChart.getAxisRight();
        //禁用图表右边y轴
        right.setEnabled(false);
    }

    private void yingcang() {
        //关闭边框矩形
        mLineChart.setDrawBorders(false);
        //不绘制y轴左边的线
        mLineChart.getAxisLeft().setDrawAxisLine(false);
        //不绘制y轴右边的线
//        lineChart.getAxisRight().setDrawAxisLine(false);
        //禁用图表右边y轴
        mLineChart.getAxisRight().setEnabled(false);
        //禁用x轴
        mLineChart.getXAxis().setEnabled(false);
        //隐藏图表左边y轴标签
        mLineChart.getAxisLeft().setDrawLabels(false);
        //关闭x轴网格线./即竖线
//        lineChart.getXAxis().setDrawGridLines(false);

        //隐藏图表右下角显示内容
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);
    }

}
