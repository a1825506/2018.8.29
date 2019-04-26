package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.TokendetailBean;

public class EosTokenTradeAdapter extends BaseQuickAdapter<TokendetailBean,BaseViewHolder>{

    private IOnItemClick iOnItemClick;


    public EosTokenTradeAdapter() {
        super(R.layout.item_eos_tokentrade, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, final TokendetailBean item) {

        if(!TextUtils.isEmpty(item.getFrom())){
            helper.getView(R.id.from_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.token_trade_from,mContext.getString(R.string.from));
            helper.setText(R.id.from_value,item.getFrom());
            helper.getView(R.id.from_value).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnItemClick.onItemClick(item.getFrom());
                }
            });
        }else
            helper.getView(R.id.from_layout).setVisibility(View.GONE);


        if(!TextUtils.isEmpty(item.getTo())){
            helper.setText(R.id.token_trade_to,mContext.getString(R.string.to));
            helper.getView(R.id.to_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.to_value,item.getTo());
            helper.getView(R.id.to_value).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iOnItemClick.onItemClick(item.getTo());
                }
            });
        }else
            helper.getView(R.id.to_layout).setVisibility(View.GONE);


        if(!TextUtils.isEmpty(item.getToken())){
            helper.setText(R.id.token_trade_token,mContext.getString(R.string.token));
            helper.getView(R.id.token_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.token_value,item.getToken());
            if(!TextUtils.isEmpty(item.getType())){

                helper.getView(R.id.token_type).setVisibility(View.VISIBLE);
                helper.setText(R.id.token_type,item.getType());
            }else{
                helper.setText(R.id.token_type,"Ether");
                helper.getView(R.id.token_type).setVisibility(View.VISIBLE);
            }


        }else
            helper.getView(R.id.token_layout).setVisibility(View.GONE);

        if(!TextUtils.isEmpty(item.getRemarks())){
            helper.setText(R.id.remark_trade_token,mContext.getString(R.string.remake));
            helper.getView(R.id.remark_layout).setVisibility(View.VISIBLE);
            helper.setText(R.id.remark_value,item.getRemarks());
        }else
            helper.getView(R.id.remark_layout).setVisibility(View.GONE);




    }

    public void setIOnItemClick(IOnItemClick mListener) {
        this.iOnItemClick = mListener;
    }


    public interface IOnItemClick {
        void onItemClick(String address);
    }
}
