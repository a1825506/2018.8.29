package com.gikee.app.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.BTCAddressFromBean;
import com.gikee.app.beans.ChooseItemBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;

import java.util.List;
import java.util.Locale;

public class ChooseItemAdapter extends BaseQuickAdapter<ChooseItemBean,BaseViewHolder> {

    private String language = "";
    public ChooseItemAdapter(){
        super(R.layout.item_choose, null);

        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType == Locale.ENGLISH) {
            language = "en";
        } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
            language = "zh_CN";
        }else if (savedLanguageType == Locale.CHINESE) {
            language = "zh_CN";
        }else
            language=savedLanguageType.getLanguage();
        if("zh".equals(language)){
            language="zh_CN";
        }


    }

    @Override
    protected void convert(BaseViewHolder helper, ChooseItemBean item) {

        if(!TextUtils.isEmpty(item.getTitle())){

            if(item.isChoose()){

                ((TextView)(helper.getView(R.id.title))).setTextColor(mContext.getResources().getColor(R.color.white));
                ((ImageView)(helper.getView(R.id.img))).setBackground(mContext.getResources().getDrawable(R.mipmap.whiteline));
                if(item.getId().equals(Commons.newAccount)||item.getId().equals(Commons.btcHashRate)){

                    ((RelativeLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));

                }

                if(item.getId().equals(Commons.activeDis)||item.getId().equals(Commons.bchHashRate)){

                    ((RelativeLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_activityaccount));

                }

                if(item.getId().equals(Commons.tradeCount)||item.getId().equals(Commons.ltcHashRate)){

                    ((RelativeLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));

                }


                if(item.getId().equals(Commons.tradeValue)){

                    ((RelativeLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradevalue));

                }

            }else{
                ((RelativeLayout)(helper.getView(R.id.layout))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));
                ((TextView)(helper.getView(R.id.title))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

                if(item.getId().equals(Commons.newAccount)||item.getId().equals(Commons.btcHashRate)){
                    ((ImageView)(helper.getView(R.id.img))).setBackground(mContext.getResources().getDrawable(R.mipmap.redline));
                }

                if(item.getId().equals(Commons.activeDis)||item.getId().equals(Commons.bchHashRate)){

                    ((ImageView)(helper.getView(R.id.img))).setBackground(mContext.getResources().getDrawable(R.mipmap.blueline));
                }


                if(item.getId().equals(Commons.tradeCount)||item.getId().equals(Commons.ltcHashRate)){
                    ((ImageView)(helper.getView(R.id.img))).setBackground(mContext.getResources().getDrawable(R.mipmap.greenline));
                }


                if(item.getId().equals(Commons.tradeValue)){
                    ((ImageView)(helper.getView(R.id.img))).setBackground(mContext.getResources().getDrawable(R.mipmap.purpleline));
                }

            }



            if(language.equals("en")){
                helper.setText(R.id.title,item.getId());
            }else
                helper.setText(R.id.title,item.getTitle());

            helper.addOnClickListener(R.id.title);

        }


    }
}
