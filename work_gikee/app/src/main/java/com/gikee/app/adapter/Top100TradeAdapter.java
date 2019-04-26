package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.resp.Top100Bean;

public class Top100TradeAdapter extends BaseQuickAdapter<Top100Bean,BaseViewHolder>{

    public Top100TradeAdapter() {
        super(R.layout.item_top100address, null);
    }

    public OnAddressClick onAddressClick;


    @Override
    protected void convert(BaseViewHolder helper, final Top100Bean item) {
        {

            if(helper.getLayoutPosition()<3){
                ((TextView)(helper.getView(R.id.rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_redlight));
                ((TextView)(helper.getView(R.id.rank))).setTextColor(mContext.getResources().getColor(R.color.title_color));

            }else{
                ((TextView)(helper.getView(R.id.rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));

                ((TextView)(helper.getView(R.id.rank))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

            }

                helper.setText(R.id.rank,"NO."+item.getRanking());

                if(!TextUtils.isEmpty(item.getAddress())){

                    helper.setText(R.id.address,item.getAddress());

                    ((TextView)(  helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.Blue));

                    helper.getView(R.id.address).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            onAddressClick.onAddress(item.getAddress());

                        }
                    });

                }

                if(!TextUtils.isEmpty(item.getValue())){
                    helper.setText(R.id.unit,item.getValue()+mContext.getResources().getString(R.string.ci));

                }
               // helper.addOnClickListener(R.id.topaddr);

            }
    }


    public void setOnAddressClick(OnAddressClick mListener) {

        this.onAddressClick = mListener;
    }


    public interface OnAddressClick {
        void onAddress(String address);
    }


}
