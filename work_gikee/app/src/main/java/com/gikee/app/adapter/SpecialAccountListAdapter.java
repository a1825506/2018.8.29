package com.gikee.app.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.SpecialAccountBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpecialAccountListAdapter extends BaseQuickAdapter<SpecialAccountBean, BaseViewHolder>{


    public SpecialAccountListAdapter() {

        super(R.layout.item_special_accountlist, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecialAccountBean item) {


        if(!TextUtils.isEmpty(item.getLogo())){

            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.item_allproject_logo));

        }

        if(!TextUtils.isEmpty(item.getCoin())){

            if(item.getCoin().equals("eth")){

                Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.eth)).into((CircleImageView) helper.getView(R.id.img_type));

            }else if(item.getCoin().equals("btc")){

                Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.btc)).into((CircleImageView) helper.getView(R.id.img_type));

            }else if(item.getCoin().equals("eos")){

                Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.eos)).into((CircleImageView) helper.getView(R.id.img_type));

            }


        }

        if(!TextUtils.isEmpty(item.getName())){

            helper.setText(R.id.exchage_text,item.getName()+"/"+item.getName_cn());

        }

//        if(!TextUtils.isEmpty(item.getAddress())){
//
//        helper.setText(R.id.exchange_count_text,item.getAddress());
//
//        }

        if(item.isChoose()){
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.choose)).into((ImageView) helper.getView(R.id.choose_img));
        }else
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.no_choose)).into((ImageView) helper.getView(R.id.choose_img));

        helper.addOnClickListener(R.id.layout);

    }


}
