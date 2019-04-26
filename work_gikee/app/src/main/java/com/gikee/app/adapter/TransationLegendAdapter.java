package com.gikee.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.LegendBean;

public class TransationLegendAdapter extends BaseQuickAdapter<LegendBean, BaseViewHolder> {

    public TransationLegendAdapter() {
        super(R.layout.item_grid_legend, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LegendBean item) {
        switch (helper.getLayoutPosition()) {
            case 0:
                helper.setTextColor(R.id.item_grid_legend_img, mContext.getResources().getColor(R.color.piechat1));
                break;
            case 1:
                helper.setTextColor(R.id.item_grid_legend_img, mContext.getResources().getColor(R.color.piechat2));
                break;
            case 2:
                helper.setTextColor(R.id.item_grid_legend_img, mContext.getResources().getColor(R.color.piechat3));
                break;
            case 3:
                helper.setTextColor(R.id.item_grid_legend_img, mContext.getResources().getColor(R.color.piechat4));
                break;
            case 4:
                helper.setTextColor(R.id.item_grid_legend_img, mContext.getResources().getColor(R.color.piechat5));
                break;
        }
        helper.setText(R.id.item_grid_legend_tx, item.getLegendName());
    }
}
