package com.gikee.app.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LineChartEntity{

   private  MyLineChart mLineChart;

   private LineData mlineData;

   private List<String> xValue;

   private String choseType;

   private Context mComtext;

   private String type;

   private IAxisValueFormatter xAxisFormatter;

   private String language = "";

   private  XAxis xAxis;

   private  YAxis rightAxis;

   private YAxis axisLeft;

   public LineChartEntity(Context content,MyLineChart lineChart, LineData lineData, List<String> xValue, String choseType,String type){

       this.mLineChart = lineChart;

       this.mlineData = lineData;

       this.xValue = xValue;

       this.choseType = choseType;

       this.mComtext = content;

       this.type = type;

       initLineChart();

       initXline();

       initYline();

       mLineChart.setData(mlineData);

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

   }



    private void initXline() {


        //=======================设置X轴显示效果==================
        xAxis = mLineChart.getXAxis();
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
            if (xValue.size() < 4)
                xAxis.setLabelCount(xValue.size(), true);
            else
                xAxis.setLabelCount(4, true);

            if (xValue.size() > 0) {
                xAxisFormatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        if (value >= 0 && xValue.size() > 0 && value <= xValue.size() - 1) {
                            String x_Value = "";
                            switch (choseType) {
                                case "5min":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 15) {
                                        x_Value = xValue.get((int) value).substring(5, 16);
                                    } else
                                        x_Value = xValue.get((int) value).substring(5, 14);
                                    break;
                                case "hour":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 12) {
                                        x_Value = xValue.get((int) value).substring(5, 13);
                                    } else
                                        x_Value = xValue.get((int) value).substring(5, 13);
                                    break;
                                case "day":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                        x_Value = xValue.get((int) value).substring(0, 10);
                                    } else
                                        x_Value = xValue.get((int) value);

                                    break;
                                case "week":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                        x_Value = xValue.get((int) value).substring(5, 10);
                                    } else
                                        x_Value = xValue.get((int) value);
                                    break;
                                case "month":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                        x_Value = xValue.get((int) value).substring(5, 10);
                                    } else
                                        x_Value = xValue.get((int) value);
                                    break;
                                case "all":
                                    if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                        x_Value = xValue.get((int) value).substring(5, 10);
                                    } else
                                        x_Value = xValue.get((int) value);
                                    break;
                            }
                            return x_Value;
                        } else return "";
                    }
                };

                xAxis.setValueFormatter(xAxisFormatter);
                //图表第一个和最后一个label数据不超出左边和右边的Y轴
                xAxis.setAvoidFirstLastClipping(true);
            }
        }

    }

    public void maxYValue(){
        axisLeft.setAxisMaximum(100f);
    }


    public void initXValue(final List<String> xValue){

        if (xValue.size() > 0) {
            if (xValue.size() < 4)
                xAxis.setLabelCount(xValue.size(), true);
            else
                xAxis.setLabelCount(4, true);

            if (xValue.size() > 0) {
                xAxisFormatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        if (value >= 0 && xValue.size() > 0 && value <= xValue.size() - 1) {
                            String x_Value = "";

                            if (xValue.get((int) value) != null && xValue.get((int) value).length() > 9) {
                                x_Value = xValue.get((int) value).substring(0, 10);
                            } else
                                x_Value = xValue.get((int) value);


                            return x_Value;
                        } else return "";
                    }
                };

                xAxis.setValueFormatter(xAxisFormatter);
                //图表第一个和最后一个label数据不超出左边和右边的Y轴
                xAxis.setAvoidFirstLastClipping(true);
            }
        }

    }


    private void initYline() {

        axisLeft = mLineChart.getAxisLeft();
        axisLeft.setGranularityEnabled(true);
        axisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeft.setAxisLineWidth(2f);
        if(Commons.currectLine.equals(type)){
            axisLeft.setLabelCount(5,true);
        }else
            axisLeft.setLabelCount(5,false);


        axisLeft.setDrawZeroLine(true);
        axisLeft.setDrawAxisLine(false);
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
                            return  MyUtils.fmtMicrometer((value/100000000)+"")+"亿";
                        }else if(value>=10000){
                            return MyUtils.fmtMicrometer((value/10000)+"")+"万";
                        }else if(value<0){
                            return "0";

                        }else{
                            if(value==0){
                                return  (int)value+"";
                            }else
                                return MyUtils.fmtMicrometer(value+"");

                        }

                }else{


                        if(value>=100000){
                            return  MyUtils.fmtMicrometer(value/100000+"")+"M";
                        }else if(value>=1000){
                            return  MyUtils.fmtMicrometer(value/1000+"")+"K";
                        }else if(value<0){
                            return "0";

                        }else{
                            if(value==0){
                                return  (int)value+"";
                            }else
                                return MyUtils.fmtMicrometer1(value+"");
                        }

                }
            }

        });



    }



    private void initLineChart() {

        mLineChart.highlightValue(null);
        mLineChart.setDrawGridBackground(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setTouchEnabled(true);//// 设置是否可以触摸
        mLineChart.setDragEnabled(true);//// 是否可以拖拽
        mLineChart.setScaleEnabled(false);

        mLineChart.setScaleXEnabled(false); //是否可以缩放 仅x轴
        mLineChart.setScrollContainer(true);
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDragDecelerationEnabled(true);
        mLineChart.setHighlightPerDragEnabled(true);

        mLineChart.animateX(1000);


        mLineChart.invalidate();




    }

    public void showRight(boolean isRightY){

        rightAxis.setEnabled(isRightY);

       // rightAxis.isInverted();

    }

    public void showRightY(boolean isRightY, final String type){
        //右边
        rightAxis = mLineChart.getAxisRight();
        rightAxis.setGranularityEnabled(false);
        if(Commons.currectLine.equals(type)){
            rightAxis.setLabelCount(5,true);
        }else
            rightAxis.setLabelCount(5,false);

        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        rightAxis.setEnabled(isRightY);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setGridLineWidth(0.4f);
        rightAxis.setGridColor(Color.parseColor("#ededed"));
        rightAxis.setTextColor(mComtext.getResources().getColor(R.color.color_9b9b9b));


        //IAxisValueFormatter
        rightAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                    if(language=="zh_CN"){
                       if(value>=100000000){
                           return  MyUtils.fmtMicrometer(value/100000000+"")+"亿";
                       }else if(value>=10000){
                           return MyUtils.fmtMicrometer(value/10000+"")+"万";
                       }else if(value<0){
                           return "0";

                       }else{
                           if(value==0){
                               return  (int)value+"";
                           }else
                               return MyUtils.fmtMicrometer1(value+"");
                       }
                    }else{
                       if(value>100000){
                           return  (int)value/100000+"M";
                       }else if(value>=1000){
                           return  (int)value/1000+"K";
                       }else if(value<0){
                           return "0";

                       }else{
                           if(value==0){
                               return  (int)value+"";
                           }else
                               return MyUtils.fmtMicrometer1(value+"");
                       }
                   }
//                }else {
//                    if(value>100000000){
//                        return  value/100000000+"亿元";
//                    }else if(value>10000){
//                        return  value/10000+"万元";
//                    }else{
//                        if(value>0&&value<1){
//                            return value+"元";
//                        }else
//                            return   (int)value+"元";
//                    }
//                }

            }

        });

        mLineChart.getAxisRight().setEnabled(isRightY);

        mLineChart.invalidate();
    }


    /**
     * <p>说明文字是否可见</p>
     * @param enabled true 可见,默认可见
     */
    public void setLegendEnabled(boolean enabled) {

                //获得数据
        Legend legend = mLineChart.getLegend();
        //修改
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(11f);
        legend.setTextColor(Color.BLACK);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setXEntrySpace(30f);
        legend.setDrawInside(false);
        mLineChart.getLegend().setEnabled(enabled);
        mLineChart.invalidate();
    }


}
