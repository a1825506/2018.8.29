package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.SearchBean;

public class SearchAdapter extends BaseQuickAdapter<SearchBean, BaseViewHolder> {

    public SearchAdapter() {
        super(R.layout.item_search_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchBean item) {
        helper.setText(R.id.item_search_list_name, item.getCoinname()).addOnClickListener(R.id.item_search_list_name).addOnClickListener(R.id.item_search_list_coll)
                .addOnClickListener(R.id.item_search_list_icon).addOnClickListener(R.id.eos_address).addOnClickListener(R.id.collect_layout);

       if(!TextUtils.isEmpty(item.getLogo())){
           Glide.with(mContext).load( item.getLogo()).into((ImageView) helper.getView(R.id.item_search_list_icon));
       }else
           Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.moren)).into((ImageView) helper.getView(R.id.item_search_list_icon));

        if (item.getIsCollect())
            helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.title_color));
        else
            helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.DFDFE4));


        if("eos_address".equals(item.getType())){
            helper.getView(R.id.search_title).setVisibility(View.VISIBLE);
            helper.getView(R.id.eos_address).setVisibility(View.VISIBLE);
        }else if("add".equals(item.getType())){
            helper.getView(R.id.search_title).setVisibility(View.GONE);
            helper.getView(R.id.item_search_list_coll).setVisibility(View.GONE);
            helper.addOnClickListener(R.id.layout);

        }



    }
}
