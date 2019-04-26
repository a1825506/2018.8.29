package com.gikee.app.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.TopDisBean;

public class TopDisAdapter extends BaseQuickAdapter<TopDisBean,BaseViewHolder> {


    public TopDisAdapter() {
        super(R.layout.item_topdis, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopDisBean item) {

        helper.getView(R.id.flag).setBackgroundResource(item.getColor());

        if(!TextUtils.isEmpty(item.getTitle())){
            helper.setText(R.id.title,item.getTitle());
        }

        if(!TextUtils.isEmpty(item.getValue())){
            helper.setText(R.id.value,item.getValue());
        }



    }
}
