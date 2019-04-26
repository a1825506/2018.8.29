package com.gikee.app.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.LeadItemBean;

import java.util.List;

public class HotSpecialAccountAdapter extends BaseQuickAdapter<LeadItemBean,BaseViewHolder>{


    public HotSpecialAccountAdapter() {
        super(R.layout.item_leader, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, LeadItemBean item) {

    }
}
