package com.gikee.app.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.BTCAddressTradeBean;

public class BTCAddressDetailAdapter extends BaseQuickAdapter<BTCAddressTradeBean,BaseViewHolder>{

    private IOnItemClick iOnItemClick;

    private String params;


    public BTCAddressDetailAdapter(String params) {
        super(R.layout.item_btcaddressdetail, null);
        this.params = params;
    }


    public void setParams(String params){

        this.params = params;
    }


    @Override
    protected void convert(final BaseViewHolder helper, BTCAddressTradeBean item) {


        if(!TextUtils.isEmpty(item.getTime())){
            helper.setText(R.id.trade_time,item.getTime());
        }

        if(!TextUtils.isEmpty(item.getConfirmations())){
            if(item.getConfirmations().contains("Confirmations")){
                helper.setText(R.id.Confirmations_text,item.getConfirmations().replace("Confirmations",""));
            }else
                 helper.setText(R.id.Confirmations_text,item.getConfirmations());
        }


        RecyclerView recyclerView = helper.getView(R.id.address_from);

        final  BTCAddressFromAdpter btcAddressFromAdpter = new BTCAddressFromAdpter(params);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.setAdapter(btcAddressFromAdpter);

        btcAddressFromAdpter.setNewData(item.getAddress_from());

        btcAddressFromAdpter.setIOnItemClick(new BTCAddressFromAdpter.IOnItemClick() {
            @Override
            public void onItemClick(BTCAddressFromAdpter.ViewName viewName,String address) {

                iOnItemClick.onItemClick(viewName,address,helper.getLayoutPosition());

            }
        });

        helper.getView(R.id.details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClick.onItemClick(BTCAddressFromAdpter.ViewName.ITEM,"",helper.getLayoutPosition());

            }
        });



        boolean is_out=false;

        int out_index=0;

        for(int i=0;i<item.getAddress_from().size();i++){

            if(item.getAddress_from().get(i).getAddress().equals(params)){
                is_out=true;
                out_index=i;
               break;
            }

        }




        RecyclerView recyclerVie_to = helper.getView(R.id.address_to);

        final  BTCAddressFromAdpter btcAddressFromAdpter_to = new BTCAddressFromAdpter(params);

        recyclerVie_to.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerVie_to.setAdapter(btcAddressFromAdpter_to);

        btcAddressFromAdpter_to.setNewData(item.getAddress_to());

        btcAddressFromAdpter_to.setIOnItemClick(new BTCAddressFromAdpter.IOnItemClick() {
            @Override
            public void onItemClick(BTCAddressFromAdpter.ViewName viewName,String address) {
                iOnItemClick.onItemClick(viewName,address,0);

            }
        });

       boolean is_in=false;

       int in_index=0;

        for(int i=0;i<item.getAddress_to().size();i++){

            if(item.getAddress_to().get(i).getAddress().equals(params)){
                is_in=true;
                in_index=i;
                break;
            }

        }

        if(is_out&&!is_in){
            helper.setText(R.id.exchange_from,mContext.getString(R.string.out));
            ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_red));

        }
//        else if(is_out&&is_in){
//            if(Float.parseFloat(item.getAddress_to().get(in_index).getAmount())<Float.parseFloat(item.getAddress_from().get(out_index).getAmount())){
//                helper.setText(R.id.exchange_from,mContext.getString(R.string.out));
//                ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_red));
//
//            }else{
//                helper.setText(R.id.exchange_from,mContext.getString(R.string.in));
//                ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_blue));
//
//            }
//
//        }
        else if(is_in&&!is_out){
            helper.setText(R.id.exchange_from,mContext.getString(R.string.in));
            ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_blue));
        }






        if(!TextUtils.isEmpty(item.getTime())){

            helper.setText(R.id.trade_time,item.getTime());
        }

        helper.getView(R.id.address_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClick.onItemClick(BTCAddressFromAdpter.ViewName.ITEM,"",helper.getLayoutPosition());

            }
        });
        //helper.addOnClickListener(R.id.trade_layout);


    }





    public void setIOnItemClick(IOnItemClick mListener) {
        this.iOnItemClick = mListener;
    }



    public interface IOnItemClick {

        void onItemClick(BTCAddressFromAdpter.ViewName viewNmae,String address,int position);
    }

    }
