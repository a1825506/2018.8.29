package com.gikee.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.type.ShowType;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.taobao.accs.init.Launcher_InitAccs.mContext;

public class  SpecialAccountAdapter extends BaseQuickAdapter<SpecialAccountBean, BaseViewHolder> {


    public SpecialAccountAdapter() {
        super(R.layout.item_special_account, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final SpecialAccountBean item) {


        if (!TextUtils.isEmpty(item.getLogo())) {


            Glide.with(mContext).load(item.getLogo()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {


                    Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.item_allproject_logo));

                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    Glide.with(mContext).load(R.mipmap.exchange).into((CircleImageView) helper.getView(R.id.item_allproject_logo));
//                    super.onLoadFailed(errorDrawable);
                }
            });


        }else
            helper.getView(R.id.item_allproject_logo).setVisibility(View.GONE);

        if (!TextUtils.isEmpty(item.getName())) {
            if (!TextUtils.isEmpty(item.getName_cn())) {
                helper.setText(R.id.exchage_text, item.getName() + "/" + item.getName_cn()+"_"+item.getCoin().toUpperCase());
            } else
                helper.setText(R.id.exchage_text, item.getName()+"_"+item.getCoin().toUpperCase());

        }else{

            helper.setText(R.id.exchage_text, "");
        }

        if(!TextUtils.isEmpty(item.getEvent())){

            helper.getView(R.id.remarks).setVisibility(View.VISIBLE);

            helper.setText(R.id.remarks, item.getEvent());

        }else
            helper.getView(R.id.remarks).setVisibility(View.GONE);

        if(!TextUtils.isEmpty(item.getType())){

            if(item.getType().equals("exchange")){


                String value = String.format(mContext.getString(R.string.account_count), item.getCount() + "");

                helper.setText(R.id.exchange_count_text, value);

            }else{

                helper.setText(R.id.exchange_count_text, item.getAddress().get(0));
            }

        }else
            helper.setText(R.id.exchange_count_text, "");

        if(item.isChoose()){
            helper.setText(R.id.add_title, mContext.getString(R.string.monitored));
            ((RelativeLayout)(helper.getView(R.id.nocontent_repeat))).setBackground(mContext.getResources().getDrawable(R.drawable.btn_gray_side));
            ((TextView)(helper.getView(R.id.add_title))).setTextColor(mContext.getResources().getColor(R.color.gray_c7));
        }else{
            ((RelativeLayout)(helper.getView(R.id.nocontent_repeat))).setBackground(mContext.getResources().getDrawable(R.drawable.btn_white_appcolor_5));

            ((TextView)(helper.getView(R.id.add_title))).setTextColor(mContext.getResources().getColor(R.color.title_color));
            helper.setText(R.id.add_title, mContext.getString(R.string.monitor));
        }


        helper.addOnClickListener(R.id.layout);


    }
}
