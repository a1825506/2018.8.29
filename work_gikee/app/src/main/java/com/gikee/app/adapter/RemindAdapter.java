package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.RemindInfoBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.RemindBean;

public class RemindAdapter extends BaseQuickAdapter<RemindInfoBean,BaseViewHolder>{

    private String language="";

    public RemindAdapter() {
        super(R.layout.item_remind, null);

        language = MyUtils.getLocalLanguage();
    }


    @Override
    protected void convert(BaseViewHolder helper, RemindInfoBean item) {





        if(!TextUtils.isEmpty(item.getTitle())){
            if(language.equals("en")){
                helper.setText(R.id.remind_title,item.getTitle_en());
            }else
                helper.setText(R.id.remind_title,item.getTitle());
        }


        if(item.getType().equals(Commons.info_totalmarket)||item.getType().equals(Commons.info_price)){
            helper.getView(R.id.remind_en_title).setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(item.getContext())){
                if(language.equals("en")){
                    helper.setText(R.id.remind_en_title,item.getContext_en());
                }else
                    helper.setText(R.id.remind_en_title,item.getContext());

            }
        }else
            helper.getView(R.id.remind_en_title).setVisibility(View.GONE);




        if(!TextUtils.isEmpty(item.getTime())){
            helper.setText(R.id.remind_time,item.getTime());
        }


        if(!TextUtils.isEmpty(item.getSymbol())){
            helper.setText(R.id.remind_form,item.getSymbol());
        }


        helper.addOnClickListener(R.id.remind_layout);

        if(item.isHot()){

            helper.getView(R.id.hot_img).setVisibility(View.VISIBLE);
        }else
            helper.getView(R.id.hot_img).setVisibility(View.GONE);
    }

}
