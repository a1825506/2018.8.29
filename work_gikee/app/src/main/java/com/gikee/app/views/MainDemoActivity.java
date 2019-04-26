package com.gikee.app.views;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gikee.app.R;
import com.github.mikephil.charting.charts.LineChart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainDemoActivity extends Activity {
    private DynamicLineChartManager dynamicLineChartManager1;
    private DynamicLineChartManager dynamicLineChartManager2;
    private List<Integer> list = new ArrayList<>(); //数据集合
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        LineChart mChart1 = (LineChart) findViewById(R.id.dynamic_chart1);
        LineChart mChart2 = (LineChart) findViewById(R.id.dynamic_chart2);
        //折线名字
        names.add("温度");
        names.add("压强");
        names.add("其他");
        //折线颜色
        colour.add(Color.CYAN);
        colour.add(Color.GREEN);
        colour.add(Color.BLUE);

        dynamicLineChartManager1 = new DynamicLineChartManager(mChart1, names.get(0), colour.get(0));
        dynamicLineChartManager2 = new DynamicLineChartManager(mChart2, names, colour);

        dynamicLineChartManager1.setYAxis(100, 0, 10);
        dynamicLineChartManager2.setYAxis(100, 0, 10);

        //死循环添加数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (count<20) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.add((int) (Math.random() * 50) + 10);
                            list.add((int) (Math.random() * 80) + 10);
                            list.add((int) (Math.random() * 100));
                            dynamicLineChartManager2.addEntry(list);
                            list.clear();
                        }
                    });
                    count++;
                }
            }
        }).start();
    }

    //按钮点击添加数据
    public void addEntry(View view) {
        for(int i=0;i<100;i++){
            list.add((int) (Math.random() * 5*i) + 10);
            list.add((int) (Math.random() * 8*i) + 10);
            list.add((int) (Math.random() * 10*i));
            dynamicLineChartManager2.addEntry(list);
            list.clear();
        }

    }

}
