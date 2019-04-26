package com.gikee.app.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.ValueBean;
import com.gikee.app.datas.Commons;

public class TradeDetailsAdapter extends BaseQuickAdapter<ValueBean,BaseViewHolder>{


    public TradeDetailsAdapter() {
        super(R.layout.item_todayadd_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ValueBean item) {

       if(Commons.bigTradeCountDis.equals(item.getType())){

            helper.getView(R.id.indicator_layout).setVisibility(View.VISIBLE);

            helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);

            helper.getView(R.id.item_today_per).setVisibility(View.VISIBLE);

            helper.getView(R.id.indicator_layout).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            helper.getView(R.id.item_today_new).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            helper.getView(R.id.item_today_per).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            if(!TextUtils.isEmpty(item.getTime())){

                if("hour".equals(item.getChooseType())||"5min".equals(item.getChooseType())){
                    helper.setText(R.id.item_today_date, item.getTime()+"");
                }else{
                    if(item.getTime().length()>10){
                        helper.setText(R.id.item_today_date, item.getTime().substring(0,10)+"");
                    }else
                        helper.setText(R.id.item_today_date, item.getTime()+"");
                }
            }
         //  ((TextView)(helper.getView(R.id.item_today_new))).setTextColor(mContext.getResources().getColor(R.color.title_color));

          // ((TextView)(helper.getView(R.id.item_today_per))).setTextColor(mContext.getResources().getColor(R.color.title_color));


           if(!TextUtils.isEmpty(item.getValue())){
                helper.setText(R.id.item_today_new, MyUtils.fmtMicrometer(item.getValue()));
            }


            if(!TextUtils.isEmpty(item.getValue1())){
                helper.setText(R.id.item_today_per, MyUtils.fmtMicrometer(item.getValue1()));
            }

        }else{

           helper.getView(R.id.indicator_layout).setVisibility(View.VISIBLE);

           helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);

           helper.getView(R.id.indicator_layout).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

           helper.getView(R.id.item_today_new).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

           if(!TextUtils.isEmpty(item.getTime())||!TextUtils.isEmpty(item.getValue())){
               if("hour".equals(item.getChooseType())||"5min".equals(item.getChooseType())){
                   if(item.getTime().length()>5){
                       helper.setText(R.id.item_today_date, item.getTime().substring(5,item.getTime().length())+"");
                   }else
                       helper.setText(R.id.item_today_date, item.getTime()+"");

               }else{
                   if(item.getTime().length()>10){
                       helper.setText(R.id.item_today_date, item.getTime().substring(0,10)+"");
                   }else
                       helper.setText(R.id.item_today_date, item.getTime()+"");
               }

              // ((TextView)(helper.getView(R.id.item_today_new))).setTextColor(mContext.getResources().getColor(R.color.title_color));

               if(Commons.price.equals(item.getType())||Commons.tradeValue.equals(item.getType())||Commons.avgTrdValue.equals(item.getType())){
                   String value  = MyUtils.getUnit()?item.getValue():String.valueOf(Float.parseFloat(item.getValue())*Commons.rate);
                   if(Commons.price.equals(item.getType())){
                       helper.setText(R.id.item_today_new, MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer1(value));
                   }else
                       helper.setText(R.id.item_today_new, MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer(value));

               }else{
                   if(Commons.marketValue.equals(item.getType())){
                       String value  = MyUtils.getUnit()?item.getValue():String.valueOf(Float.parseFloat(item.getValue())*Commons.rate);
                       if(item.getValue().contains(".")){
                           helper.setText(R.id.item_today_new, MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer2(value));
                       }else
                           helper.setText(R.id.item_today_new, MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer1(value));

                   }else
                       helper.setText(R.id.item_today_new, MyUtils.fmtMicrometer1(item.getValue()));
               }


           }


        }



    }
}
