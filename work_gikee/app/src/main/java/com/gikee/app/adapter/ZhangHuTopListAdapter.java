package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.resp.Top100Bean;

import java.text.DecimalFormat;

public class ZhangHuTopListAdapter extends BaseQuickAdapter<Top100Bean, BaseViewHolder> {

    public int[] indicator = {R.color.piechat1,R.color.piechat2,R.color.piechat3,R.color.piechat4,R.color.F398C0,R.color.piechat5};

    public ZhangHuTopListAdapter() {
        super(R.layout.item_todayadd_list, null);
    }


    private OnAddressClick onAddressClick;


    @Override
    protected void convert(BaseViewHolder helper, final Top100Bean item) {


        if(Integer.parseInt(item.getRanking())<6){
            helper.getView(R.id.indicator).setVisibility(View.VISIBLE);
            helper.getView(R.id.indicator).setBackgroundResource(indicator[Integer.parseInt(item.getRanking())-1]);
        }else{
            helper.getView(R.id.indicator).setVisibility(View.VISIBLE);
            helper.getView(R.id.indicator).setBackgroundResource(indicator[5]);
        }




        helper.getView(R.id.item_today_per).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_today_des).setVisibility(View.VISIBLE);
        helper.getView(R.id.item_today_new).setVisibility(View.VISIBLE);
        helper.getView(R.id.indicator_layout).setVisibility(View.VISIBLE);

        helper.getView(R.id.item_today_per).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

        helper.getView(R.id.item_today_des).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

        helper.getView(R.id.item_today_new).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

        helper.getView(R.id.indicator_layout).setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));

        if(!TextUtils.isEmpty(item.getRanking())){
            helper.setText(R.id.item_today_date, item.getRanking()+"");
        }

        ((TextView)(helper.getView(R.id.item_today_new))).setTextColor(mContext.getResources().getColor(R.color.Blue));


        if(!TextUtils.isEmpty(item.getAddress())){
            helper.setText(R.id.item_today_new, item.getAddress());

            helper.getView(R.id.item_today_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddressClick.onClickAddress(item.getAddress());
                }
            });

        }

        //((TextView)(helper.getView(R.id.item_today_per))).setTextColor(mContext.getResources().getColor(R.color.title_color));

       // ((TextView)(helper.getView(R.id.item_today_des))).setTextColor(mContext.getResources().getColor(R.color.title_color));


        if(!TextUtils.isEmpty(item.getVolume())){
           helper.setText(R.id.item_today_per, MyUtils.fmtMicrometer(item.getVolume()));

       }if(!TextUtils.isEmpty(item.getRatio())){
            helper.setText(R.id.item_today_des,   new DecimalFormat("0.00").format(Double.parseDouble(item.getRatio())*100)+ "%");
        }
    }


    public void setOnAddressClick(OnAddressClick mListener) {
        this.onAddressClick = mListener;
    }


    public interface OnAddressClick {
        void onClickAddress(String address);
    }
}
