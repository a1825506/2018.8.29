package com.gikee.app.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountListAdapter extends BaseQuickAdapter<SpecialAccountBean,BaseViewHolder> {

    public AccountListAdapter() {

        super(R.layout.item_accountlist, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialAccountBean item) {

        if(!TextUtils.isEmpty(item.getLogo())){

            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.logo));

        }


        if(!TextUtils.isEmpty(item.getAddress_item())){

                helper.setText(R.id.address,item.getAddress_item());
        }

        if(item.isChoose()){
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.choose)).into((ImageView) helper.getView(R.id.choose_img));
        }else
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.no_choose)).into((ImageView) helper.getView(R.id.choose_img));

        helper.addOnClickListener(R.id.choose_img);

        helper.addOnClickListener(R.id.copy);

    }
}
