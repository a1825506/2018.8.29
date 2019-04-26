package com.gikee.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.activity.AddressDetailActivity;
import com.gikee.app.activity.BTCAddressDetailActivity;
import com.gikee.app.activity.ETHAddressDetailActivity;
import com.gikee.app.activity.SpecialTradeActivity;
import com.gikee.app.activity.Top100TradeActivity;
import com.gikee.app.activity.TopPlayerActivity;
import com.gikee.app.beans.TopDisBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.resp.LineDataBean;
import com.gikee.app.resp.PieDataBean;
import com.gikee.app.resp.SummaryBean;
import com.gikee.app.type.ShowType;
import com.gikee.app.views.MyLineChartEntity;
import com.gikee.app.views.MyPieChartEntity;
import com.gikee.app.views.RiseNumberTextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * Created by THINKPAD on 2018/6/19.
 *
 *
 * http://47.100.218.3:3333/static/android/gikee.apk
 */

public class ShuJUFenXiAdapter extends BaseMultiItemQuickAdapter<SummaryBean, BaseViewHolder> {

    public static final int pieLeft = 0;

    public static final int pieRight = 1;

    public static final int table =2;

    public static final int lineWave = 3;

    private Context mContext;

    private RiseNumberTextView today_textView,yestoday_textView;

    TradeMonitorAdapter tradeMonitorAdapter;

    PieChart pieChart,pieChart1;

    LineChart lineChart;

    private String type;



    @Override
    protected int getDefItemViewType(int position) {

        String type = getItem(position).getShowType();

        if(type.equals(ShowType.pieLeft.getContent())){

            return pieLeft;

        }else if(type.equals(ShowType.pieRight.getContent())){

            return pieRight;

        }else if(type.equals(ShowType.table.getContent())){

            return table;

        }else if(type.equals(ShowType.lineWave.getContent())){

            return lineWave;

        }
        return super.getDefItemViewType(position);
    }

    public ShuJUFenXiAdapter(List data,Context context) {
        super(data); //必须绑定type和layout的关系
        addItemType(lineWave, R.layout.item_fm_all_shuju_one);
        addItemType(table, R.layout.item_fm_all_shuju_category);
        addItemType(pieLeft, R.layout.item_fm_all_shuju_three);
        addItemType(pieRight, R.layout.item_fm_all_shuju_three);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SummaryBean item) {

        switch (helper.getItemViewType()) {
            case lineWave:
                type = item.getItemName();
                //填充折线图
                lineChart = helper.getView(R.id.item_fm_all_shuju_one_linechart);
                initSingleLineChart(item.getLineData());

                //根据接口返回title 显示中文名或英文名
                helper.setText(R.id.tem_fm_all_shuju_title, getItemTitle(item));
                helper.setText(R.id.chat_item_title, getItemName(item,item.getLineData().get(0).getCurrentName().get(0)));
                //今日数据
                today_textView = ((RiseNumberTextView)(helper.getView(R.id.item_fm_all_shuju_one_per)));

                if(!TextUtils.isEmpty(item.getLineData().get(0).getCurrentValue().get(0))){
                    if(type.equals(Commons.price)||type.equals(Commons.marketValue)) {
                        String value = MyUtils.getUnit()?item.getLineData().get(0).getCurrentValue().get(0):item.getLineData().get(0).getCurrentValue().get(2);
                        setTodayValue(type,today_textView,value);
                    }else
                        setTodayValue(type,today_textView,item.getLineData().get(0).getCurrentValue().get(0));

                }
                //折线图显示样式

                if(Commons.bigTradeCountDis.equals(item.getItemName())){
                    //大额转账和总地址数
                   // helper.getView(R.id.change_title).setVisibility(View.GONE);
                    helper.getView(R.id.change_layout).setVisibility(View.GONE);
                    helper.getView(R.id.change_text).setVisibility(View.GONE);;
                    helper.getView(R.id.chat_item_title2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.chat_item_title).setVisibility(View.VISIBLE);
                    helper.getView(R.id.chat_flag2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.chat_flag1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.item_fm_all_shuju_one_num).setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(item.getLineData().get(0).getCurrentValue().get(1))){

                        yestoday_textView = ((RiseNumberTextView)(helper.getView(R.id.item_fm_all_shuju_one_num)));

                       // yestoday_textView.setInteger(0,0);

                        if(item.getLineData().get(0).getCurrentValue().size()>1){

                            if(type.equals(Commons.price)||type.equals(Commons.marketValue)) {
                                String value = MyUtils.getUnit()?item.getLineData().get(0).getCurrentValue().get(1):String.valueOf(Float.parseFloat(item.getLineData().get(0).getCurrentValue().get(1))*Commons.rate);
                                setTodayValue(type,yestoday_textView,value);
                            }else
                                setTodayValue(type,yestoday_textView,item.getLineData().get(0).getCurrentValue().get(1));

                            helper.setText(R.id.chat_item_title2, getItemName(item,item.getLineData().get(0).getCurrentName().get(1)));

                        }
                    }
                }else {
                    helper.getView(R.id.chat_item_title2).setVisibility(View.VISIBLE);
                    helper.getView(R.id.chat_item_title).setVisibility(View.VISIBLE);
                    ((TextView)(helper.getView(R.id.chat_item_title))).setText(mContext.getString(R.string.today));
                    ((TextView)(helper.getView(R.id.chat_item_title2))).setText(mContext.getString(R.string.yestaday));
                    ((TextView)(helper.getView(R.id.item_fm_all_shuju_one_num))).setTextColor(mContext.getResources().getColor(R.color.AEAEAE));
                    (helper.getView(R.id.item_fm_all_shuju_one_num)).setVisibility(View.VISIBLE);
                    helper.getView(R.id.chat_flag2).setVisibility(View.GONE);
                    helper.getView(R.id.chat_flag1).setVisibility(View.GONE);

                    if(!TextUtils.isEmpty(item.getLineData().get(0).getQuoteChange())){
                        helper.getView(R.id.change_layout).setVisibility(View.VISIBLE);
                        if(item.getLineData().get(0).getQuoteChange().contains("-")){
                            helper.setText(R.id.change_text,"↓"+MyUtils.fmtMicrometer(item.getLineData().get(0).getQuoteChange().replace("-",""))+"%");
                           // ((TextView)(helper.getView(R.id.change_text))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_green));
//                            ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.red));
                            if(MyUtils.getQuateSymbol()){
                                ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                            }else
                                ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.red));


                        }else if("max".equals(item.getLineData().get(0).getQuoteChange())){

                            helper.setText(R.id.change_text,"——");

                            ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

                        }else{
                            helper.setText(R.id.change_text,"↑"+MyUtils.fmtMicrometer(item.getLineData().get(0).getQuoteChange())+"%");


//                            ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                            if(MyUtils.getQuateSymbol()){
                                ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.red));
                            }else
                                ((TextView)(helper.getView(R.id.change_text))).setTextColor(mContext.getResources().getColor(R.color.faa3c));

                        }

                    }else{
                        helper.getView(R.id.change_layout).setVisibility(View.GONE);
                        helper.setText(R.id.change_text,"");
                    }


                    if(item.getLineData().get(0).getCurrentValue().size()>1){

                        if(!TextUtils.isEmpty(item.getLineData().get(0).getCurrentValue().get(1))){

                              yestoday_textView = ((RiseNumberTextView)(helper.getView(R.id.item_fm_all_shuju_one_num)));

                               boolean isBaseLine=false;

                            for(int i=0;i<Commons.baseLine.length;i++){
                                if(Commons.id!=null){
                                    if(Commons.id.equals(Commons.baseLine[i])){
                                        isBaseLine=true;
                                    }
                                }

                            }

                            if(!isBaseLine){

                                if(item.getItemName().equals(Commons.inactiveCount)||item.getItemName().equals(Commons.wakeCount)||item.getItemName().equals(Commons.avgTrdValue)||item.getItemName().equals(Commons.avgTrdVol)){//

                                    helper.getView(R.id.chat_item_title2).setVisibility(View.GONE);
                                    ((TextView)(helper.getView(R.id.chat_item_title))).setText(mContext.getString(R.string.yestaday));
                                    ((TextView)(helper.getView(R.id.chat_item_title))).setTextColor(mContext.getResources().getColor(R.color.AEAEAE));
                                    today_textView.setTextColor(mContext.getResources().getColor(R.color.AEAEAE));
                                    yestoday_textView.setVisibility(View.GONE);

                                }else{
                                    yestoday_textView.setVisibility(View.VISIBLE);
                                    if(type.equals(Commons.price)||type.equals(Commons.marketValue)) {
                                        String value = MyUtils.getUnit()?item.getLineData().get(0).getCurrentValue().get(1):String.valueOf(Float.parseFloat(item.getLineData().get(0).getCurrentValue().get(1))*Commons.rate);
                                        setTodayValue(type,yestoday_textView,value);
                                    }else
                                        setTodayValue(type,yestoday_textView,item.getLineData().get(0).getCurrentValue().get(1));
//                                    setTodayValue(type,yestoday_textView,item.getLineData().get(0).getCurrentValue().get(1));
                                    ((TextView)(helper.getView(R.id.chat_item_title))).setTextColor(mContext.getResources().getColor(R.color.black));
                                    today_textView.setTextColor(mContext.getResources().getColor(R.color.black));
                                    helper.getView(R.id.chat_item_title2).setVisibility(View.VISIBLE);
                                    ((TextView)(helper.getView(R.id.chat_item_title))).setText(mContext.getString(R.string.today));
//
//                                    helper.getView(R.id.chat_item_title2).setVisibility(View.GONE);
//                                    ((TextView)(helper.getView(R.id.chat_item_title))).setText(mContext.getString(R.string.today));
//                                    ((TextView)(helper.getView(R.id.chat_item_title))).setTextColor(mContext.getResources().getColor(R.color.black));
//                                    today_textView.setTextColor(mContext.getResources().getColor(R.color.black));
                                }

                            }else{
                                yestoday_textView.setVisibility(View.VISIBLE);
                                today_textView.setTextColor(mContext.getResources().getColor(R.color.black));
//                                setTodayValue(type,yestoday_textView,item.getLineData().get(0).getCurrentValue().get(1));
                                if(type.equals(Commons.price)||type.equals(Commons.marketValue)) {
                                    String value = MyUtils.getUnit()?item.getLineData().get(0).getCurrentValue().get(1):String.valueOf(Float.parseFloat(item.getLineData().get(0).getCurrentValue().get(1))*Commons.rate);
                                    setTodayValue(type,yestoday_textView,value);
                                }else
                                    setTodayValue(type,yestoday_textView,item.getLineData().get(0).getCurrentValue().get(1));
                                helper.getView(R.id.chat_item_title2).setVisibility(View.VISIBLE);
                                ((TextView)(helper.getView(R.id.chat_item_title))).setText(mContext.getString(R.string.today));

                            }

                        }

                    }

                }

                helper.addOnClickListener(R.id.new_price_layout).addOnClickListener(R.id.item_fm_all_shuju_one_linechart);
                break;
            case table:
                type = item.getItemName();

                if(!Commons.topPlayer.equals(type)||!Commons.specialTrade.equals(type)){

                    String title_table = item.getTitile();

                    if (Commons.specialTrade.equals(item.getItemName())) {
                        title_table = mContext.getString(R.string.specialTrade_title);
                    } else if (Commons.topFreqAddr.equals(item.getItemName())) {
                        title_table = mContext.getString(R.string.topFreqAdd_title);
                    } else if (Commons.topPlayer.equals(item.getItemName())) {
                        title_table = mContext.getString(R.string.topPlayer);
                    }


                    helper.setText(R.id.table_title, title_table);

                    tradeMonitorAdapter = new TradeMonitorAdapter(mContext);

                    RecyclerView recyclerView = helper.getView(R.id.trade_recycler);

                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                    recyclerView.setAdapter(tradeMonitorAdapter);

                    tradeMonitorAdapter.setNewData(item.getTableData());

                    tradeMonitorAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            if( tradeMonitorAdapter.getData().get(position).getType().equals(ShowType.specialAddress.getContent())||tradeMonitorAdapter.getData().get(position).getType().equals(ShowType.bigTrade.getContent())||tradeMonitorAdapter.getData().get(position).getType().equals(ShowType.frequentTrade.getContent())){

                                Intent intent = new Intent(mContext,SpecialTradeActivity.class);

                                intent.putExtra("id", Commons.smybl);

                                intent.putExtra("title", mContext.getString(R.string.specialTrade_title));

                                intent.putExtra("type", tradeMonitorAdapter.getData().get(position).getType());

                                mContext.startActivity(intent);

                            }else if(tradeMonitorAdapter.getData().get(position).getType().equals(ShowType.topFreqAddr.getContent())){

                                mContext.startActivity(new Intent(mContext, Top100TradeActivity.class).putExtra("id", Commons.id));

                            }else if(tradeMonitorAdapter.getData().get(position).getType().equals(ShowType.topPlayer.getContent())){

                                mContext.startActivity(new Intent(mContext, TopPlayerActivity.class).putExtra("symbol", Commons.smybl).putExtra("id", Commons.id));

                            }

                        }
                    });

                    tradeMonitorAdapter.setOnAddressClick(new TradeMonitorAdapter.OnAddressClick() {
                        @Override
                        public void onAddress(String address) {

                            //基础链不跳转地址详情
                            boolean isBaseLine=false;
                            for(int i=0;i<Commons.baseLine2.length;i++){
                                if(Commons.id.equals(Commons.baseLine2[i])){
                                    isBaseLine=true;
                                }

                            }

                            if(!isBaseLine){

                                if(Commons.smybl.equals("BTC")){
                                    Intent intent = new Intent(mContext, BTCAddressDetailActivity.class);

                                    intent.putExtra("address",address);

                                    intent.putExtra("type",Commons.smybl);

                                    mContext.startActivity(intent);

                                }else if(Commons.smybl.equals("EOS")){
                                    Intent intent = new Intent(mContext, AddressDetailActivity.class);

                                    intent.putExtra("address",address);

                                    intent.putExtra("type",Commons.smybl);

                                    mContext.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(mContext, ETHAddressDetailActivity.class);

                                    intent.putExtra("address",address);

                                    intent.putExtra("type","ETH");

                                    mContext.startActivity(intent);
                                }




                            }

                        }
                    });

                    helper.getView(R.id.table_loadmore).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(Commons.specialTrade.equals(item.getItemName())){
                                Intent intent = new Intent(mContext,SpecialTradeActivity.class);

                                intent.putExtra("id", Commons.smybl);

                                intent.putExtra("title", mContext.getString(R.string.specialTrade_title));

                                intent.putExtra("type", ShowType.specialAddress.getContent());

                                mContext.startActivity(intent);
                            }else if(Commons.topFreqAddr.equals(item.getItemName())){
                                mContext.startActivity(new Intent(mContext, Top100TradeActivity.class).putExtra("symbol", Commons.smybl).putExtra("id", Commons.id));

                            }else if(Commons.topPlayer.equals(item.getItemName())){
                                mContext.startActivity(new Intent(mContext, TopPlayerActivity.class).putExtra("symbol", Commons.smybl).putExtra("id", Commons.id));
                            }
                        }
                    });

                }

                break;

            case pieLeft:
                type = item.getItemName();
                pieChart = helper.getView(R.id.item_fm_all_shuju_three_piechart);
                setData(item.getPieData(), item.getItemName(),pieChart);

                String title_pie=item.getTitile();


                if(Commons.top100.equals(item.getItemName())){
                    title_pie = mContext.getString(R.string.top100_title);
                }else if(Commons.topDis.equals(item.getItemName())){
                    title_pie = mContext.getString(R.string.topDis_title);
                }else if(Commons.tradeVolDis.equals(item.getItemName())){
                    title_pie = mContext.getString(R.string.tradeVolDis_title);
                }else if(Commons.tradeCountDis.equals(item.getItemName())){
                    title_pie = mContext.getString(R.string.tradeCountDis);
                }


                TopDisAdapter topDisAdapter = new TopDisAdapter();

                RecyclerView item_recycle = helper.getView(R.id.item_recycle);

                item_recycle.setLayoutManager(new LinearLayoutManager(mContext));

                item_recycle.setAdapter(topDisAdapter);

                List<TopDisBean> topDisBeanList = new ArrayList<>();

                for(int i=0;i<item.getPieData().size();i++){

                    TopDisBean topDisBean = new TopDisBean();

                    if(i==0){

                        topDisBean.setColor(R.color.piechat1);

                    }else if(i==1){
                        topDisBean.setColor(R.color.piechat2);

                    }else if(i==2){
                        topDisBean.setColor(R.color.piechat3);

                    }else if(i==3){
                        topDisBean.setColor(R.color.piechat4);

                    }else if(i==4){
                        topDisBean.setColor(R.color.piechat6);

                    }

                    topDisBean.setTitle(item.getPieData().get(i).getName());

                    topDisBean.setValue(item.getPieData().get(i).getPercent());

                    topDisBeanList.add(topDisBean);

                }

                topDisAdapter.setNewData(topDisBeanList);

                helper.setText(R.id.item_fm_all_shuju_three_name, title_pie);


                helper.addOnClickListener(R.id.item_fm_all_shuju_three_content).addOnClickListener(R.id.item_fm_all_shuju_three_piechart);
                break;
            case pieRight:
                type = item.getItemName();
                pieChart1 = helper.getView(R.id.item_fm_all_shuju_three_piechart);

                setData(item.getPieData(), item.getItemName(),pieChart1);

                String title_pieR=item.getTitile();

                if(Commons.top100.equals(item.getItemName())){
                    title_pieR = mContext.getString(R.string.top100_title);
                }else if(Commons.topDis.equals(item.getItemName())){
                    title_pieR = mContext.getString(R.string.topDis_title);
                }else if(Commons.tradeVolDis.equals(item.getItemName())){
                    title_pieR = mContext.getString(R.string.tradeVolDis_title);
                }else if(Commons.tradeCountDis.equals(item.getItemName())){
                    title_pieR = mContext.getString(R.string.tradeCountDis);
                }

                helper.setText(R.id.item_fm_all_shuju_three_name, title_pieR);


                helper.addOnClickListener(R.id.item_fm_all_shuju_three_content).addOnClickListener(R.id.item_fm_all_shuju_three_piechart);

        }
    }

    private void setTodayValue(final String type, final RiseNumberTextView today_textView, final String todayvalue) {

        if(!TextUtils.isEmpty(todayvalue)){

            if(MyUtils.isNumeric(todayvalue)){
                today_textView.setFloat(type,0,Float.parseFloat(todayvalue));
            }

            today_textView.start();

            today_textView.setDuration(1500);


            //动画结束添加价格符号
            today_textView.setOnEndListener(new RiseNumberTextView.EndListener() {
                @Override
                public void onEndFinish() {
                      if(type.equals(Commons.price)||type.equals(Commons.marketValue)||type.equals(Commons.tradeValue)||type.equals(Commons.avgTrdValue)){

                          double value = Double.parseDouble(todayvalue);

                         if(type.equals(Commons.tradeValue)||type.equals(Commons.avgTrdValue)){

                             value = MyUtils.getUnit()?Double.parseDouble(todayvalue):Double.parseDouble(todayvalue)*Commons.rate;

                         }


                          String str_value =null;

                          String language = MyUtils.getLocalLanguage();

                          if(type.equals(Commons.marketValue)||type.equals(Commons.avgTrdValue)||type.equals(Commons.tradeValue)){


                              if(language=="en") {

                                  if(value>100000){
                                      str_value =  MyUtils.fmtMicrometer(value/100000+"")+"M";
                                  }else if(value>=1000){
                                      str_value = MyUtils.fmtMicrometer(value/1000+"")+"K";
                                  }else
                                      str_value = MyUtils.fmtMicrometer(value+"");

                              }else{
                                  if (value >= 100000000) {
                                      str_value =  MyUtils.fmtMicrometer(value / 100000000 + "") + "亿";
                                  } else if (value >= 10000) {
                                      str_value =  MyUtils.fmtMicrometer(value / 10000 + "") + "万";
                                  }else
                                      str_value = MyUtils.fmtMicrometer(value+"");
                              }



                              today_textView.setText(MyUtils.getUnitSymbol()+""+str_value);
                          }else
                              today_textView.setText(MyUtils.getUnitSymbol()+""+todayvalue);


                    }else
                        today_textView.setText(MyUtils.fmtMicrometer(todayvalue));

                }
            });
        }
    }

    private String getItemName(SummaryBean item,String item_name) {

        String name="";
        //服务器返回的始终是中文
        if ("总地址数".equals(item_name)) {
            name =  mContext.getString(R.string.alladdress);

        }else if ("持币地址".equals(item_name)) {
            name = mContext.getString(R.string.onweraddressnum);

        } else if ("新增".equals(item_name)) {
            name = mContext.getString(R.string.today_add_add);

        } else if ("超大额".equals(item_name)) {
            name = mContext.getString(R.string.HugeNum);
        }else if("大额".equals(item_name)){
            name = mContext.getString(R.string.BigNum);
        }

        return name;
    }

    private String getItemTitle(SummaryBean item) {

        String title="";

        if(Commons.activeDis.equals(item.getItemName())){
            title = mContext.getString(R.string.activeDis_title);
        }else if(Commons.avgTrdVol.equals(item.getItemName())){
            title = mContext.getString(R.string.avgTrdVol_title);
        }else if(Commons.marketValue.equals(item.getItemName())){
            title = mContext.getString(R.string.marketValue_title);
        }else if(Commons.tradevolume.equals(item.getItemName())){
            title = mContext.getString(R.string.tradevolume_title);
        }else if(Commons.tradeCount.equals(item.getItemName())){
            title = mContext.getString(R.string.tradeCount_title);
        }else if(Commons.allAddr.equals(item.getItemName())){
            title = mContext.getString(R.string.allAddr_title);
        }else if(Commons.ownAddr.equals(item.getItemName())){
            title = mContext.getString(R.string.ownAddr_title);
        }else if(Commons.newAndInactivity.equals(item.getItemName())){
            title = mContext.getString(R.string.newAndInactivity_title);
        }else if(Commons.bigTradeCountDis.equals(item.getItemName())){
            title = mContext.getString(R.string.bigTradeCountDis_title);
        }else if(Commons.newAccount.equals(item.getItemName())){
            title = mContext.getString(R.string.newCount);
        }else if(Commons.wakeCount.equals(item.getItemName())){
            title = mContext.getString(R.string.wakeCount);
        }else if(Commons.inactiveCount.equals(item.getItemName())){
            title = mContext.getString(R.string.inactiveCount);
        }else if(Commons.price.equals(item.getItemName())){
            title = mContext.getString(R.string.price_title);
        }else if(Commons.participateAddress.equals(item.getItemName())){
            title = mContext.getString(R.string.participateAddress);
        }else if(Commons.tradeValue.equals(item.getItemName())){
            title = mContext.getString(R.string.tradeValue);
        }else if(Commons.avgTrdValue.equals(item.getItemName())){
            title = mContext.getString(R.string.avgTrdValue);
        }else if(Commons.ownAddr.equals(item.getItemName())){

        }

        return title;
    }


    private LineDataBean lineDataBean;



    public LineData initSingleLineChart(List<LineDataBean> items) {

        ArrayList<Entry>  values = new ArrayList<Entry>();

        ArrayList<Entry>  values1 = new ArrayList<Entry>();

        ArrayList<ILineDataSet> dataSets= new ArrayList<>();


       lineDataBean = items.get(0);//item始终只有一条数据

        float min_Yvalue=0;

        float value1=0,value2=0;


        for(int i = 0; i < lineDataBean.getDot().size(); i++){

            if(i==0){
                if(!TextUtils.isEmpty(lineDataBean.getDot().get(i).getValue())&&MyUtils.isNumeric(lineDataBean.getDot().get(i).getValue())){
                    min_Yvalue = Float.parseFloat(lineDataBean.getDot().get(i).getValue());
                }

            }

            if(!TextUtils.isEmpty(lineDataBean.getDot().get(i).getValue())&&MyUtils.isNumeric(lineDataBean.getDot().get(i).getValue())){
                value1=Float.parseFloat(lineDataBean.getDot().get(i).getValue());

            }

            values.add(new Entry(i, value1));

            if(!TextUtils.isEmpty(lineDataBean.getDot().get(i).getValue1())&&MyUtils.isNumeric(lineDataBean.getDot().get(i).getValue1())) {

                value2=Float.parseFloat(lineDataBean.getDot().get(i).getValue1());
            }
            values1.add(new Entry(i, value2));

        }

        //设置折线的样式
        LineDataSet dataSet = new LineDataSet(values, "");
        //用y轴的集合来设置参数
        dataSet.setColor(mContext.getResources().getColor(R.color.F398C0));
        dataSet.setLineWidth(1f); // 线宽
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setDrawFilled(true);
       // dataSet.setFillColor(mContext.getResources().getColor(R.color.linewave1));
        dataSet.setFillDrawable(mContext.getResources().getDrawable(R.drawable.line_background01));
        dataSets.add(dataSet);

        if(lineDataBean.getDot().get(0).getValue1()!=null) {

            //设置折线的样式
            LineDataSet dataSet1 = new LineDataSet(values1, "");
            //用y轴的集合来设置参数
            dataSet1.setColor(mContext.getResources().getColor(R.color.piechat5));
            dataSet1.setLineWidth(1f); // 线宽
            dataSet1.setDrawCircles(false);
            dataSet1.setDrawValues(false);
            dataSet1.setDrawFilled(true);
           // dataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            dataSet1.setFillAlpha(1);
            dataSets.add(dataSet1);
        }
        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(dataSets);

        MyLineChartEntity  lineChartEntity = new MyLineChartEntity(mContext,lineChart,lineData,min_Yvalue,type);


        return lineData;
    }


    private void setData(List<PieDataBean> pies,String itemName, PieChart mChart) {


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        float otherRatio=0;

        for(int i=0;i<pies.size();i++){

            otherRatio=otherRatio+ Float.parseFloat(pies.get(i).getPercent())*100;

            if(itemName.equals(Commons.top100)||itemName.equals(Commons.topDis)){
                entries.add(new PieEntry(Float.valueOf(pies.get(i).getPercent())*100, pies.get(i).getPercent()));
            } else
                entries.add(new PieEntry(Float.valueOf(pies.get(i).getPercent()), pies.get(i).getPercent()));


        }


        ArrayList<Integer> colors = new ArrayList<Integer>();

        if(itemName.equals(Commons.top100)){
            entries.add(new PieEntry(100-otherRatio, ""));
            colors.add(mContext.getResources().getColor(R.color.piechat1));
            colors.add(mContext.getResources().getColor(R.color.piechat2));
            colors.add(mContext.getResources().getColor(R.color.piechat3));
            colors.add(mContext.getResources().getColor(R.color.piechat4));
            colors.add(mContext.getResources().getColor(R.color.piechat6));
            colors.add(mContext.getResources().getColor(R.color.piechat5));
        }else if(itemName.equals(Commons.topDis)){
            entries.add(new PieEntry(100-otherRatio, ""));
            colors.add(mContext.getResources().getColor(R.color.piechat1));
            colors.add(mContext.getResources().getColor(R.color.piechat2));
            colors.add(mContext.getResources().getColor(R.color.piechat3));
            colors.add(mContext.getResources().getColor(R.color.piechat4));
            colors.add(mContext.getResources().getColor(R.color.piechat5));
        }else if(itemName.equals(Commons.tradeVolDis)||itemName.equals(Commons.tradeCountDis)){
            colors.add(mContext.getResources().getColor(R.color.piechat1));
            colors.add(mContext.getResources().getColor(R.color.piechat2));
            colors.add(mContext.getResources().getColor(R.color.piechat3));
            colors.add(mContext.getResources().getColor(R.color.piechat4));
            colors.add(mContext.getResources().getColor(R.color.piechat5));
        }else{
            colors.add(Color.parseColor("#ffaf2c"));
            colors.add(Color.parseColor("#D6D6D9"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(1f);
        dataSet.setValueTextColor(Color.parseColor("#00000000"));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);

        MyPieChartEntity myPieChart = new MyPieChartEntity(mContext,mChart,data);
    }

}
