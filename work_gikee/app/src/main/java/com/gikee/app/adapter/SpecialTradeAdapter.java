package com.gikee.app.adapter;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.resp.SpecialAddressBean;


//http://101.132.47.194/api/v2/token/frequentTrade?symbol=XNK&start=0&limit=10&startDate=2018-07-21&endDate=2018-08-20
//http://101.132.47.194/api/v2/token/frequentTrade?symbol=XNK&startDate=2018-03-01&endDate=2018-03-31&start=0&limit=10
public class SpecialTradeAdapter extends BaseQuickAdapter<SpecialAddressBean,BaseViewHolder>{

    public SpecialTradeAdapter() {
        super(R.layout.item_specialtrade, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialAddressBean item) {

        if(!TextUtils.isEmpty(item.getFrom())&&!TextUtils.isEmpty(item.getTo())){

            if(item.getFrom().length()>30){

                helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"..."+item.getFrom().substring(item.getFrom().length()-4,item.getFrom().length()));
            }

            if(item.getTo().length()>30){
                helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"..."+item.getTo().substring(item.getTo().length()-4,item.getTo().length()));
            }



        }else if(!TextUtils.isEmpty(item.getFrom())&&TextUtils.isEmpty(item.getTo())){
            //发散
            if(item.getFrom().length()>30){
                helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"..."+item.getFrom().substring(item.getFrom().length()-4,item.getFrom().length()));

            }

            ((TextView)(helper.getView(R.id.addrss_name_to))).setBackgroundResource(R.drawable.shape_btn_red);
            //helper.setBackgroundColor(R.id.addrss_name_to,R.drawable.shape_btn_red);
            helper.setText(R.id.addrss_name_to,R.string.more_radiation);
        }else if(TextUtils.isEmpty(item.getFrom())&&!TextUtils.isEmpty(item.getTo())){
            //聚集
            helper.setText(R.id.addrss_name,R.string.more_addresscollect);
            //helper.setBackgroundColor(R.id.addrss_name,R.drawable.shape_btn_green);
            ((TextView)(helper.getView(R.id.addrss_name))).setBackgroundResource(R.drawable.shape_btn_green);
            if(item.getTo().length()>30){
                helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"..."+item.getTo().substring(item.getTo().length()-4,item.getTo().length()));

            }

        }
        if(TextUtils.isEmpty(item.getFromExchange())){
            helper.getView(R.id.exchange_from).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.exchange_from).setVisibility(View.VISIBLE);
            helper.setText(R.id.exchange_from,item.getFromExchange());
        }

        if(TextUtils.isEmpty(item.getToExchange())){
            helper.getView(R.id.exchange_to).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.exchange_to).setVisibility(View.VISIBLE);
            helper.setText(R.id.exchange_to,item.getToExchange());
        }


        if(!TextUtils.isEmpty(item.getLatestTime())){

            helper.setText(R.id.addrss_tiem,item.getLatestTime());
        }

       // if(!TextUtils.isEmpty(item.getUnit())){

            helper.setText(R.id.addrss_name_value,item.getValue()+" "+item.getUnit());

     //   }

        helper.addOnClickListener(R.id.specialaddress_layout);



    }

}
