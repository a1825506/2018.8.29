package com.gikee.app.views;

/*全部趋势里面的折线图统一样式*/

import android.content.Context;
import android.widget.RelativeLayout;

import com.gikee.app.datas.Commons;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

public class MyLineChartEntity {

    private LineChart lineChart;

    private float min_Yvalue;

    private String type;

    public MyLineChartEntity(Context content, LineChart lineChart, LineData lineData,float min_Yvalue,String type){


        this.lineChart = lineChart;

        this.min_Yvalue = min_Yvalue;

        this.type = type;

        initLineChart();

        lineChart.setData(lineData);

        lineChart.invalidate();
    }

    private void initLineChart() {

        RelativeLayout.LayoutParams lp1 = ( RelativeLayout.LayoutParams)lineChart.getLayoutParams();
        lineChart.setLayoutParams(lp1);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.getXAxis().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setMinOffset(0);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.setFocusable(false);
        lineChart.animateX(1000);



    }

    }
