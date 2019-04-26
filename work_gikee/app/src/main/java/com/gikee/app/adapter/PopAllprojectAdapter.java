package com.gikee.app.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.ZhanghuPopBean;


public class PopAllprojectAdapter extends BaseQuickAdapter<ZhanghuPopBean, BaseViewHolder> {

    public PopAllprojectAdapter() {
        super(R.layout.item_pop_allproject_recycler, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhanghuPopBean item) {
        helper.setText(R.id.item_pop_project_recycler_tx, item.getName()).addOnClickListener(R.id.item_pop_project_recycler_tx);
        if (item.isChose())
            helper.setBackgroundRes(R.id.item_pop_project_recycler_tx, R.drawable.shape_btn_redlight);
        else
            helper.setBackgroundRes(R.id.item_pop_project_recycler_tx, R.drawable.btn_allproject_gray);
    }
}
