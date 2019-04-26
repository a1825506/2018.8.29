package com.gikee.app.views;

import android.content.Context;

import com.gikee.app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

/*全部趋势里面的饼图统一样式*/
public class MyPieChartEntity {

    private PieChart pieChart;

    private Context mcontext;


    public MyPieChartEntity(Context content, PieChart pieChart, PieData pieData){

        this.mcontext = content;

        this.pieChart = pieChart;

        initPieChart();

        pieChart.setData(pieData);

        pieChart.invalidate();
    }

    private void initPieChart() {
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(false);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.setCenterTextColor(mcontext.getResources().getColor(R.color.gray_33));
        pieChart.setCenterTextSize(10f);
        pieChart.setHoleRadius(60);
        pieChart.setFocusable(false);
        pieChart.setTouchEnabled(false);
        pieChart.animateX(1000);


    }
}
