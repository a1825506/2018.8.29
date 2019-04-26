package com.gikee.app.views;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class PieChartEntity {

    private PieChart mChart;
    private List<PieEntry> mEntries;
    private String[] labels;
    private ArrayList<Integer> mPieColors;
    private int mValueColor;
    private float mTextSize;
    private PieDataSet.ValuePosition mValuePosition;
    private String mSymbol;




    public PieChartEntity(PieChart chart, List<PieEntry> entries, String[] labels,
                          ArrayList<Integer> chartColor , float textSize, int valueColor, PieDataSet.ValuePosition valuePosition,String Symbol) {
        this.mChart = chart;
        this.mEntries = entries;
        this.labels= labels;
        this.mPieColors = chartColor;
        this.mTextSize= textSize;
        this.mValueColor = valueColor;
        this.mValuePosition = valuePosition;
        this.mSymbol = Symbol;
        initPieChart();
    }



    private void initPieChart() {
        mChart.setExtraTopOffset(10f);
        mChart.setExtraBottomOffset(10f);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawCenterText(false);
        mChart.getDescription().setEnabled(false);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setDrawEntryLabels(true);
        mChart.setDrawCenterText(true);
        // 触摸旋转
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.animateX(1000);
        setChartData();
       // mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);

//        Legend l = mChart.getLegend();
//        l.setTextColor(mValueColor);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(0.8f);
//        l.setYEntrySpace(0.8f);
//        l.setYOffset(0f);
        // entry label styling
        mChart.setEntryLabelColor(mValueColor);
        mChart.setEntryLabelTextSize(mTextSize);

    }

    public void setHoleDisenabled () {
        mChart.setDrawHoleEnabled(false);
    }

    /**
     * 中心圆是否可见
     * @param holeColor 中心圆颜色
     * @param holeRadius 半径
     * @param transColor 透明圆颜色
     * @param transRadius 透明圆半径
     */
    public void setHoleEnabled (int holeColor, float holeRadius, int transColor, float transRadius) {
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(holeColor);
        mChart.setTransparentCircleColor(transColor);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(holeRadius);
        mChart.setTransparentCircleRadius(transRadius);
    }

    public void updatePieData(List<PieEntry> entries){
        mEntries = entries;
        setChartData();
    }

    private void setChartData() {
        PieDataSet dataSet = new PieDataSet(mEntries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setColors(mPieColors);
        dataSet.setYValuePosition(mValuePosition);
        dataSet.setXValuePosition(mValuePosition);
        dataSet.setValueLineColor(Color.parseColor("#000000"));

        dataSet.setSelectionShift(12f);
        dataSet.setSliceSpace(2f);//设置每一块之间的间隔
        dataSet.setValueTextSize(mTextSize);
        dataSet.setValueTextColor(Color.parseColor("#000000"));
        dataSet.setValueLinePart1OffsetPercentage(50f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLineVariableLength(true);
        dataSet.setValueLineWidth(1f);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyPercentFormatter(mSymbol));

        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    /**
     * <p>说明文字是否可见</p>
     * @param enabled true 可见,默认可见
     */
    public void setLegendEnabled(boolean enabled) {
        mChart.getLegend().setEnabled(enabled);
        //获得数据
        Legend legend = mChart.getLegend();
        //修改
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setTextColor(Color.BLACK);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setXEntrySpace(30f);
        legend.setDrawInside(false);
        mChart.invalidate();
    }
    public void setPercentValues (boolean showPercent) {
        mChart.setUsePercentValues(showPercent);
    }


}
