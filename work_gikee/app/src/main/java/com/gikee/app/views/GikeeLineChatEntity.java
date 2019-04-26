package com.gikee.app.views;

import android.content.Context;
import android.graphics.Color;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GikeeLineChatEntity {

    private Context mComtext;

    private  MyLineChart lineChart;

    private YAxis axisLeft;

    private YAxis rightAxis;

    private XAxis xAxis;

    private LineDataSet lineDataSet;

    private List<ILineDataSet> lineDataSets = new ArrayList<>();

    private LineData lineData;

    private int[] color = {R.color.F398C0,R.color.piechat1,R.color.piechat4,R.color.A1DF79,R.color.piechat6};

    private List<String> xValue;

    private String language = "";

    private IAxisValueFormatter xAxisFormatter;



    public GikeeLineChatEntity(Context mComtext, MyLineChart mLineChart,List<String> xValue,int count){

        this.mComtext = mComtext;

        this.lineChart = mLineChart;

        this.xValue = xValue;

        axisLeft = lineChart.getAxisLeft();

        rightAxis = lineChart.getAxisRight();

        rightAxis.setEnabled(false);

        xAxis = lineChart.getXAxis();

        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType == Locale.ENGLISH) {
            language = "en";
        } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
            language = "zh_CN";
        }else if (savedLanguageType == Locale.CHINESE) {
            language = "zh_CN";
        }else
            language=savedLanguageType.getLanguage();
        if("zh".equals(language)){
            language="zh_CN";
        }

        initLineChart();

        initLineDataSet(count);

        initXline();

        initYline();

    }

    private void initYline() {

        axisLeft.setGranularityEnabled(true);
        axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeft.setAxisLineWidth(2f);
        axisLeft.setLabelCount(5,true);
        axisLeft.setAxisMinimum(0f);
        axisLeft.setDrawZeroLine(true);
        axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(true);
        axisLeft.setGridColor(Color.parseColor("#ededed"));
        axisLeft.setGridLineWidth(1f);
        axisLeft.setZeroLineColor(Color.parseColor("#ededed"));
        axisLeft.setZeroLineWidth(1f);
        axisLeft.setTextColor(Color.parseColor("#9b9b9b"));
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(language=="zh_CN"){
                        if(value>=100000000){
                            return  "$"+ MyUtils.fmtMicrometer(value/100000000+"")+"亿";
                        }else if(value>=10000){
                            return "$"+MyUtils.fmtMicrometer(value/10000+"")+"万";
                        }else{
                            if(value==0){
                                return   "$"+(int)value+"";
                            }else
                                return "$"+MyUtils.fmtMicrometer(value+"");

                        }

                }else{
                        if(value>=100000){
                            return  "$"+MyUtils.fmtMicrometer(value/100000+"")+"M";
                        }else if(value>=1000){
                            return  "$"+MyUtils.fmtMicrometer(value/1000+"")+"K";
                        }else{
                            if(value==0){
                                return   "$"+(int)value+"";
                            }else
                                return "$"+MyUtils.fmtMicrometer(value+"");
                        }
                }
            }

        });
    }

    private void initXline() {

        //是否启用X轴
        xAxis.setEnabled(true);

        //是否绘制X轴线
        xAxis.setDrawAxisLine(false);
        //设置X轴上每个竖线是否显示
        xAxis.setDrawGridLines(false);
        //设置是否绘制X轴上的对应值(标签)
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(Color.parseColor("#9b9b9b"));
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if(xValue.size()>0) {
            if (xValue.size() < 5)
                xAxis.setLabelCount(xValue.size(), true);
            else
                xAxis.setLabelCount(5, true);

            if (xValue.size() > 0) {
                xAxisFormatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        if (value >= 0 && xValue.size() > 0 && value <= xValue.size() - 1) {
                            String x_Value = "";

                            if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                x_Value = xValue.get((int) value).substring(5, 10);
                            } else
                                x_Value = xValue.get((int) value);

                            return x_Value;
                        } else
                            return "";
                    }
                };

                xAxis.setValueFormatter(xAxisFormatter);
                //图表第一个和最后一个label数据不超出左边和右边的Y轴
                xAxis.setAvoidFirstLastClipping(true);
            }
        }
    }

    private void initLineChart() {
        lineChart.setNoDataText(mComtext.getString(R.string.getdata));
        lineChart.highlightValue(null);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);//// 设置是否可以触摸
        lineChart.setDragEnabled(true);//// 是否可以拖拽
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(false); //是否可以缩放 仅x轴
        lineChart.setScrollContainer(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setDragDecelerationEnabled(true);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.animateX(1000);
    }


    /**
     * 初始化折线（多条线）

     */
    private void initLineDataSet(int count) {

        for (int i = 0; i < count; i++) {
            lineDataSet = new LineDataSet(null, "");
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setColor(mComtext.getResources().getColor(color[i]));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            lineDataSet.setLineWidth(0.8f);
            lineDataSet.setFillAlpha(40);
            lineDataSet.setDrawCircles(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.enableDashedHighlightLine(5f, 5f, 0);
            lineDataSet.setHighLightColor(mComtext.getResources().getColor(color[i]));
            lineDataSet.setDrawValues(false);
            // lineData.addDataSet(lineDataSet4);
            lineDataSets.add(lineDataSet);
        }
        //添加一个空的 LineData
        lineData = new LineData(lineDataSets);
    }



    /**
     * 动态添加数据（多条折线图）
     *
     * @param numbers
     */
    public void addEntry( List<ArrayList<Entry>> numbers) {

//        if (lineDataSets.get(0).getEntryCount() == 0) {
//            lineData = new LineData(lineDataSets);
//            lineChart.setData(lineData);
//        }

       // xValue.add(df.format(System.currentTimeMillis()));
        for (int i = 0; i < numbers.size(); i++) {

            for(int j=0;j<numbers.get(i).size();j++){
                Entry entry = numbers.get(i).get(j);
                lineData.addEntry(entry, j);
                lineData.notifyDataChanged();
                lineChart.notifyDataSetChanged();
            }

        }
    }



}
