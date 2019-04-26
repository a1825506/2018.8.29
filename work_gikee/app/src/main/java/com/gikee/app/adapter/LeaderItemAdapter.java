package com.gikee.app.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.LeadItemBean;

public class LeaderItemAdapter  extends BaseQuickAdapter<LeadItemBean,BaseViewHolder> {


    public LeaderItemAdapter() {
        super(R.layout.item_leader, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, LeadItemBean item) {


        if(!TextUtils.isEmpty(item.getItemName())){
            helper.setText(R.id.today_add_title1, item.getItemName());
            if(!TextUtils.isEmpty(item.getType())){
                if(item.getType().equals("url")){
                    ((TextView)(helper.getView(R.id.today_add_title1))).setTextColor(mContext.getResources().getColor(R.color.Blue));
                }
            }

        }

        helper.addOnClickListener(R.id.hot_search);
    }
}
