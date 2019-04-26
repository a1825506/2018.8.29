package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.BTCAddressFromBean;
import com.gikee.app.datas.Commons;

public class BTCAddressFromAdpter extends BaseQuickAdapter<BTCAddressFromBean,BaseViewHolder> {


    private String params;

    private IOnItemClick iOnItemClick;




    public BTCAddressFromAdpter(String params){
        super(R.layout.item_btctrade, null);
        this.params=params;
    }


    @Override
    protected void convert(BaseViewHolder helper, final BTCAddressFromBean item) {

        if(!TextUtils.isEmpty(item.getAddress())){

            if(!params.equals(item.getAddress())){

                if(item.getAddress().equals("Genesis of Bitcoin")){
                    helper.getView(R.id.address).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            iOnItemClick.onItemClick(ViewName.ADDRESS,"1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
                        }
                    });
                    ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.black));
                }else if(item.getAddress().equals("Coinbase")){
                    ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.black));
                }else{
                    ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.Blue));
                    helper.addOnClickListener(R.id.address);
                    helper.getView(R.id.address).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            iOnItemClick.onItemClick(ViewName.ADDRESS,item.getAddress());
                        }
                    });
                }

            }else{

                ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.black));
            }


            if(item.getAddress().length()>30){
                if(item.getAddress().length()>10){
                    helper.setText(R.id.address,item.getAddress().substring(0,10)+"...."+item.getAddress().substring(item.getAddress().length()-4,item.getAddress().length()));

                }
            }else
                helper.setText(R.id.address,item.getAddress());


        }

        if(!TextUtils.isEmpty(item.getAmount())){
            if(Commons.BASELINE.equals("BTC")){
                helper.setText(R.id.address_amount,item.getAmount()+"BTC");
            }else
                helper.setText(R.id.address_amount,item.getAmount());

        }

        helper.getView(R.id.trade_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClick.onItemClick(ViewName.ITEM,"");

            }
        });

    }

    public enum ViewName {
        ITEM,
        ADDRESS
    }




    public void setIOnItemClick(IOnItemClick mListener) {
        this.iOnItemClick = mListener;
    }



    public interface IOnItemClick {
        void onItemClick(ViewName viewName,String address);
    }
}
