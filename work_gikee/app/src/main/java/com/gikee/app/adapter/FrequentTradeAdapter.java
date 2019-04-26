package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.FrequenTradeBean;

public class FrequentTradeAdapter extends BaseQuickAdapter<FrequenTradeBean,BaseViewHolder>{

    public FrequentTradeAdapter() {
        super(R.layout.item_frequenttrade, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, FrequenTradeBean item) {

        if(TextUtils.isEmpty(item.getExchange())){
            helper.getView(R.id.addrss_flag).setVisibility(View.GONE);
        }else
            helper.setText(R.id.addrss_flag,item.getExchange());

        if(!TextUtils.isEmpty(item.getAddress())){
            helper.setText(R.id.addrss_tiem,item.getAddress());
        }

        if(!TextUtils.isEmpty(item.getExchange())){

            helper.setText(R.id.addrss_flag,item.getExchange());
        }


        if(!TextUtils.isEmpty(item.getValue())){

            helper.setText(R.id.addrss_name_value,item.getValue()+mContext.getResources().getString(R.string.ci));
        }else
            helper.setText(R.id.addrss_name_value,"0"+mContext.getResources().getString(R.string.ci));




        helper.addOnClickListener(R.id.specialaddress_layout);

    }
}
