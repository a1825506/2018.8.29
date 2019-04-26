package com.gikee.app.adapter;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.resp.HotProiectBean;

import de.hdodenhof.circleimageview.CircleImageView;

public class HotProjectAdapter extends BaseQuickAdapter<HotProiectBean, BaseViewHolder> {

    public HotProjectAdapter() {
        super(R.layout.tab_hotitem, null);
    }
    @Override
    protected void convert(BaseViewHolder helper, HotProiectBean item) {

        if(!TextUtils.isEmpty(item.getLogo())){
            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.hot_img));

        }


        if(!TextUtils.isEmpty(item.getSymbol())){

            helper.setText(R.id.hot_txt,item.getSymbol());

        }

        helper.addOnClickListener(R.id.hot_search);

    }
}
