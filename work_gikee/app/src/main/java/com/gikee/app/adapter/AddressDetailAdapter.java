package com.gikee.app.adapter;


import android.content.ClipData;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.HashTradeBean;
import com.gikee.app.datas.Commons;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddressDetailAdapter extends BaseQuickAdapter<HashTradeBean, BaseViewHolder> {



    private IOnItemClick iOnItemClick;


    public AddressDetailAdapter(String address) {

        super(R.layout.item_addressdetail, null);

    }
    @Override
    protected void convert(BaseViewHolder helper, final HashTradeBean item) {

        if(!TextUtils.isEmpty(item.getFrom())&&!TextUtils.isEmpty(item.getTo())){

            if(item.getFrom().length()>30){

                helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"..."+item.getFrom().substring(item.getFrom().length()-4,item.getFrom().length()));
            }else
                helper.setText(R.id.addrss_name, item.getFrom());

            if(!TextUtils.isEmpty(item.getDirect())){

                if(item.getDirect().toUpperCase().equals("IN")){
                    //转入
                    in_trade(helper, item);
                }else
                    //转出
                    out_trade( helper, item);

            }


            if(item.getTo().length()>30){
                helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"..."+item.getTo().substring(item.getTo().length()-4,item.getTo().length()));
            }else
                helper.setText(R.id.addrss_name_to, item.getTo());
        }


        if("ERC20".equals(item.getType())){
            helper.getView(R.id.token_layout).setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(item.getLogo())){
                Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.token_img));
            }
            if(!TextUtils.isEmpty(item.getSymbol())){
                helper.setText(R.id.token_title,item.getSymbol());
            }
        }else
            helper.getView(R.id.token_layout).setVisibility(View.GONE);


        if(!TextUtils.isEmpty(item.getTime())){

            helper.setText(R.id.addrss_tiem,item.getTime());
        }



        if(!TextUtils.isEmpty(item.getAmount())){

            String symbol = "";

            if(Commons.BASELINE.equals("EOS")){

                if(item.getAmount().contains("EOS")){
                    symbol = "";
                }else
                    symbol = "EOS";

                helper.setText(R.id.addrss_name_value,item.getAmount());

            }else if(Commons.BASELINE.equals("ETH")){

                symbol = "ETH";

                if(item.getAmount().contains("Ether")){

                    String amount;

                    if(item.getAmount().contains(",")){
                        amount = item.getAmount().replace(",","").replace(" Ether","");
                    }else
                        amount=item.getAmount().replace(" Ether","");

                    if(MyUtils.isNumeric(amount)){
                        helper.setText(R.id.addrss_name_value,MyUtils.fmtMicrometer1(amount)+symbol);
                    }else
                        helper.setText(R.id.addrss_name_value,amount.replace(" Ether","")+symbol);
                }else{
                    if(MyUtils.isNumeric(item.getAmount())){

                        helper.setText(R.id.addrss_name_value,MyUtils.fmtMicrometer1(item.getAmount())+symbol);

                    }else
                        helper.setText(R.id.addrss_name_value,item.getAmount()+symbol);
                }



            }

        }

           if(!TextUtils.isEmpty(item.getStatus())){
               helper.getView(R.id.trade_status).setVisibility(View.VISIBLE);
               String status=null;
               if(item.getStatus().equals("success")){
                   status = mContext.getString(R.string.success_status);
               }else if(item.getStatus().equals("padding")){
                   status =mContext.getString(R.string.padding);
               }else if(item.getStatus().equals("failure")){
                   status = mContext.getString(R.string.failure_status);
               }
               helper.setText(R.id.trade_status, status);
           }else
               helper.getView(R.id.trade_status).setVisibility(View.GONE);


        helper.addOnClickListener(R.id.address_detail);

    }

    private void in_trade(BaseViewHolder helper, final HashTradeBean item) {


        ((TextView)(helper.getView(R.id.addrss_name))).setTextColor(mContext.getResources().getColor(R.color.Blue));
        helper.getView(R.id.addrss_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(item.getFrom())){
                    if(iOnItemClick!=null){
                        iOnItemClick.onItemClick(item.getFrom());
                    }else{
                        String text = item.getFrom();
                        ClipData myClip = ClipData.newPlainText("text", text);
                        GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                        ToastUtils.show(mContext.getString(R.string.copied));
                    }

                }

            }
        });
        ((TextView)(helper.getView(R.id.addrss_name_to))).setTextColor(mContext.getResources().getColor(R.color.black));
        if("contract".equals(item.getType())){
            helper.getView(R.id.exchange_from_img).setVisibility(View.VISIBLE);
            helper.getView(R.id.exchange_from).setVisibility(View.GONE);
//                    ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_circle));
        }else{
            helper.getView(R.id.exchange_from_img).setVisibility(View.GONE);
            helper.getView(R.id.exchange_from).setVisibility(View.VISIBLE);
            helper.setText(R.id.exchange_from,mContext.getString(R.string.in));
            ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_blue));
        }

        if(!TextUtils.isEmpty(item.getToTitle())){
            helper.getView(R.id.to_title).setVisibility(View.VISIBLE);
            helper.getView(R.id.addrss_name_to).setVisibility(View.GONE);
            helper.setText(R.id.to_title,item.getToTitle());
            ((TextView)(helper.getView(R.id.to_title))).setTextColor(mContext.getResources().getColor(R.color.black));
        }else{
            helper.getView(R.id.addrss_name_to).setVisibility(View.VISIBLE);
            helper.getView(R.id.to_title).setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(item.getFrom_title())){
            ((TextView)(helper.getView(R.id.from_title))).setTextColor(mContext.getResources().getColor(R.color.Blue));
            helper.getView(R.id.from_title).setVisibility(View.VISIBLE);
            helper.setText(R.id.from_title,item.getFrom_title());
            helper.getView(R.id.addrss_name).setVisibility(View.GONE);
            helper.getView(R.id.from_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnItemClick.onItemClick(item.getFrom());
                }
            });
        }else{
            helper.getView(R.id.addrss_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.from_title).setVisibility(View.GONE);
        }
    }

    private void out_trade(BaseViewHolder helper, final HashTradeBean item) {

        ((TextView)(helper.getView(R.id.addrss_name_to))).setTextColor(mContext.getResources().getColor(R.color.Blue));
        ((TextView)(helper.getView(R.id.addrss_name))).setTextColor(mContext.getResources().getColor(R.color.black));

        if("contract".equals(item.getType())){
            helper.getView(R.id.exchange_from_img).setVisibility(View.VISIBLE);
            helper.getView(R.id.exchange_from).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.exchange_from_img).setVisibility(View.GONE);
            helper.getView(R.id.exchange_from).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.exchange_from))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_gray));
        }


        helper.setText(R.id.exchange_from,mContext.getString(R.string.out));
        helper.getView(R.id.addrss_name_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClick.onItemClick(item.getTo());
            }
        });

        //如果是有from_title则显示from——title不显示from_address
        if(!TextUtils.isEmpty(item.getFrom_title())){
            ((TextView)(helper.getView(R.id.from_title))).setTextColor(mContext.getResources().getColor(R.color.black));
            helper.getView(R.id.from_title).setVisibility(View.VISIBLE);
            helper.setText(R.id.from_title,item.getFrom_title());
            helper.getView(R.id.addrss_name).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.addrss_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.from_title).setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(item.getToTitle())){
            helper.getView(R.id.to_title).setVisibility(View.VISIBLE);
            ((TextView)(helper.getView(R.id.to_title))).setTextColor(mContext.getResources().getColor(R.color.Blue));
            helper.getView(R.id.addrss_name_to).setVisibility(View.GONE);
            helper.setText(R.id.to_title,item.getToTitle());

            helper.getView(R.id.to_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnItemClick.onItemClick(item.getTo());
                }
            });
        }else{
            helper.getView(R.id.addrss_name_to).setVisibility(View.VISIBLE);
            helper.getView(R.id.to_title).setVisibility(View.GONE);
        }

    }

    public void setIOnItemClick(IOnItemClick mListener) {
        this.iOnItemClick = mListener;
    }


    public interface IOnItemClick {
        void onItemClick(String address);
    }
}
