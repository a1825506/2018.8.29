package com.gikee.app.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.resp.NewAndInactivityBean;

public class ZhangHuListAdapter extends BaseQuickAdapter<NewAndInactivityBean, BaseViewHolder> {

    public ZhangHuListAdapter() {
        super(R.layout.item_todayadd_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewAndInactivityBean item) {
        helper.getView(R.id.item_today_per).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_today_des).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_today_date).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);

        if(item.getChoosetype().equals("hour")||item.getChoosetype().equals("5min")){
            helper.setText(R.id.item_today_date, item.getTime().substring(5,item.getTime().length())+"").setText(R.id.item_today_new, item.getNewCount() + "")
                    .setText(R.id.item_today_per, item.getInactiveCount() + "").setText(R.id.item_today_des, item.getWakeCount() + "");

        }else{
            if(item.getTime().length()>10){
                helper.setText(R.id.item_today_date, item.getTime().substring(0,10)+"").setText(R.id.item_today_new, item.getNewCount() + "")
                        .setText(R.id.item_today_per, item.getInactiveCount() + "").setText(R.id.item_today_des, item.getWakeCount() + "");

            }else{
                helper.setText(R.id.item_today_date, item.getTime()+"").setText(R.id.item_today_new, item.getNewCount() + "")
                        .setText(R.id.item_today_per, item.getInactiveCount() + "").setText(R.id.item_today_des, item.getWakeCount() + "");

            }
        }



    }
}
