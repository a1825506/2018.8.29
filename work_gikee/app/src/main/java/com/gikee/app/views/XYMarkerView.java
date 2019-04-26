package com.gikee.app.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.adapter.MarkViewAdapter;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.datas.Commons;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XYMarkerView extends MarkerView {

//    private TextView tvyContent;
//    private TextView tvxContent;
    private Context context;
    private List<String>  xValue ;
    private Map<String,List<Entry>> point_list;
    private RecyclerView markview_title;
    private DecimalFormat format;
    private MarkViewAdapter markViewAdapter;
    private List<MarkerViewBean>  markerViewBeanList = new ArrayList<>();
    private Map<String,Integer> map_color ;
    private String choose_title;
    public XYMarkerView(Context context) {
        super(context, R.layout.custom_marker_view);

        this.context = context;
//        tvyContent = (TextView) findViewById(R.id.yValue);
//        tvxContent=(TextView) findViewById(R.id.xValue);
        markview_title=(RecyclerView)findViewById(R.id.markview_title);

        markview_title.setLayoutManager(new LinearLayoutManager(getContext()));

        format = new DecimalFormat("###,##0.00");
    }


    public void setColormap(Map<String,Integer> map_color){
        this.map_color = map_color;
        if(markViewAdapter!=null){
            markViewAdapter = null;
        }
        markViewAdapter = new MarkViewAdapter(map_color);
        markview_title.setAdapter(markViewAdapter);
    }


    public void setIAxisValueFormatter(List<String>  xValue){
        this.xValue = xValue;

    }


    public void setLineData(Map<String,List<Entry>> point_list ){

        this.point_list = point_list;

    }

    public void setChoose_title(String choose_title){
        this.choose_title = choose_title;

    }

    //回调函数每次MarkerView重绘,可以用来更新内容(用户界面)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if(xValue!=null){
            markerViewBeanList.clear();
            MarkerViewBean markerViewBean;


            for(int i=0;i<xValue.size();i++){

                if(e!=null&&i==e.getX()){

                    markerViewBean=new MarkerViewBean();
                    markerViewBean.setId("");
                    markerViewBean.setValue(xValue.get(i));
                    markerViewBean.setType("");
                    markerViewBeanList.add(markerViewBean);

                }

            }

            for(Map.Entry<String,List<Entry>> entry :point_list.entrySet()){

                for(int i=0;i<entry.getValue().size();i++){

                    if(e!=null&&entry.getValue().get(i).getX()==e.getX()){

                        markerViewBean=new MarkerViewBean();

                        markerViewBean.setId(entry.getKey());

                        String title="";
                        if(entry.getKey().equals(Commons.price)){
                            title = context.getString(R.string.price_title);
                        }else if(entry.getKey().equals(Commons.marketValue)){
                            title = context.getString(R.string.marketValue_title);
                        }else if(entry.getKey().equals(Commons.allAddr)){
                            title = context.getString(R.string.allAddr_title);
                        }else if(entry.getKey().equals(Commons.ownAddr)){
                            title = context.getString(R.string.ownAddr_title);
                        }else if(entry.getKey().equals(Commons.newAccount)){
                            title = context.getString(R.string.newCount);
                        }else if(entry.getKey().equals(Commons.activeDis)){
                            title = context.getString(R.string.activeDis_title);
                        }else if(entry.getKey().equals(Commons.participateAddress)){
                            title = context.getString(R.string.participateAddress);
                        }else if(entry.getKey().equals(Commons.wakeCount)){
                            title = context.getString(R.string.wakeCount);
                        }else if(entry.getKey().equals(Commons.inactiveCount)){
                            title = context.getString(R.string.inactiveCount);
                        }else if(entry.getKey().equals(Commons.tradeCount)){
                            title = context.getString(R.string.tradecount);
                        }else if(entry.getKey().equals(Commons.tradevolume)){
                            title = context.getString(R.string.tradenum);
                        }else if(entry.getKey().equals(Commons.tradeValue)){
                            title = context.getString(R.string.tradeValue);
                        }else if(entry.getKey().equals(Commons.avgTrdVol)){
                            title = context.getString(R.string.avgTrdVol_title);
                        }else if(entry.getKey().equals(Commons.avgTrdValue)){
                            title = context.getString(R.string.avgTrdValue);
                        }else if(entry.getKey().equals(Commons.btcHashRate)){
                            title = context.getString(R.string.pow_btc);
                        }else if(entry.getKey().equals(Commons.bchHashRate)){
                            title = context.getString(R.string.pow_bch);
                        }else if(entry.getKey().equals(Commons.ltcHashRate)){
                            title = context.getString(R.string.pow_ltc);
                        }else if(entry.getKey().equals(Commons.ethHashRate)){
                            title = context.getString(R.string.pow_eth);
                        }else if(entry.getKey().equals(Commons.assest_in)){
                            title = context.getString(R.string.asset_transfer);
                        }else if(entry.getKey().equals(Commons.assest_out)){
                            title = context.getString(R.string.asset_transfer_out);
                        }else
                            title = entry.getKey();

                        markerViewBean.setTitle(title);
                        BigDecimal bd = new BigDecimal(entry.getValue().get(i).getY()+"");
                        markerViewBean.setValue(MyUtils.fmtMicrometer(bd.toPlainString()));
                        markerViewBean.setType(choose_title);
                        markerViewBeanList.add(markerViewBean);

                    }

                }

            }
            markViewAdapter.setNewData(markerViewBeanList);

        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
