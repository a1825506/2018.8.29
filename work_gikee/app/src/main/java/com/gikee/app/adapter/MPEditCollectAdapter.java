package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.greendao.CollectBean;

import java.util.List;



public class MPEditCollectAdapter extends BaseItemDraggableAdapter<CollectBean, BaseViewHolder> {

    public MPEditCollectAdapter(int layoutResId, List<CollectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectBean item) {


//        if (!item.isCollect())
//            helper.setImageResource(R.id.item_mp_editproject_coll, R.mipmap.collect);
//        else
//            helper.setImageResource(R.id.item_mp_editproject_coll, R.mipmap.collected);

        if (item.getIscollect())
            helper.setTextColor(R.id.item_mp_editproject_coll, mContext.getResources().getColor(R.color.title_color));
        else
            helper.setTextColor(R.id.item_mp_editproject_coll, mContext.getResources().getColor(R.color.DFDFE4));


        if(!TextUtils.isEmpty(item.getLogo())){
            Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.item_mp_editproject_icon));
            helper.getView(R.id.item_mp_editproject_icon).setVisibility(View.VISIBLE);
        }else
            helper.getView(R.id.item_mp_editproject_icon).setVisibility(View.GONE);
        if(!TextUtils.isEmpty(item.getName())){
            helper.setText(R.id.item_mp_editproject_name, item.getName()).addOnClickListener(R.id.item_mp_editproject_coll).addOnClickListener(R.id.item_mp_editproject_top);

        }
        }
}