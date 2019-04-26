package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.resp.LastTradeCountDisBean;

import java.text.DecimalFormat;

public class OwnerDistributeAdapter extends BaseQuickAdapter<LastTradeCountDisBean, BaseViewHolder> {

    public OwnerDistributeAdapter() {
        super(R.layout.item_todayadd_list, null);
    }


    public int[] indicator = {R.color.piechat1,R.color.piechat2,R.color.piechat3,R.color.piechat4,R.color.piechat5};

    @Override
    protected void convert(BaseViewHolder helper, final LastTradeCountDisBean item) {


        if(Commons.topDis.equals(item.getType())){

            helper.getView(R.id.indicator_layout).setVisibility(View.VISIBLE);

           // helper.getView(R.id.item_today_date).setVisibility(View.VISIBLE);

            helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);

            helper.getView(R.id.item_today_per).setVisibility(View.VISIBLE);


            helper.getView(R.id.indicator_layout).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            helper.getView(R.id.item_today_new).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            helper.getView(R.id.item_today_per).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));


            if(helper.getLayoutPosition()==1){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[0]);
                helper.setText(R.id.item_today_date,R.string.top10);
            }else if(helper.getLayoutPosition()==2){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[1]);
                helper.setText(R.id.item_today_date,R.string.top50r);

            }else if(helper.getLayoutPosition()==3){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[2]);
                helper.setText(R.id.item_today_date,R.string.top100);

            }else if(helper.getLayoutPosition()==4){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[3]);
                helper.setText(R.id.item_today_date,R.string.top1000);

            }else if(helper.getLayoutPosition()==5){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[4]);
                helper.setText(R.id.item_today_date,R.string.other_a);
            }

            if(!TextUtils.isEmpty(item.getValue2())){

               // ((TextView)(helper.getView(R.id.item_today_new))).setTextColor(mContext.getResources().getColor(R.color.title_color));


                helper.setText(R.id.item_today_new, MyUtils.fmtMicrometer(item.getValue2()));

            }

            if(!TextUtils.isEmpty(item.getValue())){

               // ((TextView)(helper.getView(R.id.item_today_per))).setTextColor(mContext.getResources().getColor(R.color.title_color));


                helper.setText(R.id.item_today_per,new DecimalFormat("0.00").format(Double.parseDouble(item.getValue())*100)+"%");


            }




        }else{

            helper.getView(R.id.indicator_layout).setVisibility(View.VISIBLE);

            helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);

           // helper.getView(R.id.item_today_date).setVisibility(View.VISIBLE);

            helper.getView(R.id.indicator_layout).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

            helper.getView(R.id.item_today_new).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));


            if(helper.getLayoutPosition()==1){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[0]);
                helper.setText(R.id.item_today_date,R.string.hugeCount);
            }else if(helper.getLayoutPosition()==2){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[1]);
                helper.setText(R.id.item_today_date,R.string.bigCount);

            }else if(helper.getLayoutPosition()==3){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[2]);
                helper.setText(R.id.item_today_date,R.string.midiumCount);

            }else if(helper.getLayoutPosition()==4){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[3]);
                helper.setText(R.id.item_today_date,R.string.smallCOunt);

            }else if(helper.getLayoutPosition()==5){
                helper.getView(R.id.indicator).setBackgroundResource(indicator[4]);
                helper.setText(R.id.item_today_date,R.string.tinyCount);

            }

            if(!TextUtils.isEmpty(item.getValue())){

              //  ((TextView)(helper.getView(R.id.item_today_new))).setTextColor(mContext.getResources().getColor(R.color.title_color));


                helper.setText(R.id.item_today_new,MyUtils.fmtMicrometer(item.getValue()));

            }



        }



    }
}
