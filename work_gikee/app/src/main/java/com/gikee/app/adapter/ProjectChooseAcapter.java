package com.gikee.app.adapter;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.IndexChooseBean;


public class ProjectChooseAcapter extends BaseQuickAdapter<IndexChooseBean,BaseViewHolder> {

    public ProjectChooseAcapter() {
        super(R.layout.item_projectchoose, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexChooseBean item) {

        if(!TextUtils.isEmpty(item.getValue())){



            helper.setText(R.id.selected_text_view,item.getValue());

        }

        if(item.getColor()!=0){

            ((ImageView)(helper.getView(R.id.selected_img_view))).setBackgroundColor(mContext.getResources().getColor(item.getColor()));


        }

        if(item.isEnable()){
            helper.getView(R.id.delete_image_view).setVisibility(View.VISIBLE);
        }else
            helper.getView(R.id.delete_image_view).setVisibility(View.GONE);

        if(item.isCheck()){
            if(!TextUtils.isEmpty(item.getType())){
                helper.getView(R.id.selected_img_view).setVisibility(View.GONE);
            }else{
                if(!TextUtils.isEmpty(item.getId())){
                    helper.getView(R.id.selected_img_view).setVisibility(View.VISIBLE);
                }else
                    helper.getView(R.id.selected_img_view).setVisibility(View.GONE);

            }

            ((LinearLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
            ((TextView)(helper.getView(R.id.selected_text_view))).setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            if(!TextUtils.isEmpty(item.getType())){
                helper.getView(R.id.selected_img_view).setVisibility(View.GONE);
                ((LinearLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));
            }else{
                if(!TextUtils.isEmpty(item.getId())){
                    helper.getView(R.id.selected_img_view).setVisibility(View.VISIBLE);
                }else
                    helper.getView(R.id.selected_img_view).setVisibility(View.GONE);
                ((LinearLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.btn_white_appcolor_5));

            }


            ((TextView)(helper.getView(R.id.selected_text_view))).setTextColor(mContext.getResources().getColor(R.color.gray_33));
        }



        helper.addOnClickListener(R.id.selected_text_view);

        helper.addOnClickListener(R.id.delete_image_view);

    }
}
