package com.gikee.app.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompareAdapter extends BaseQuickAdapter<IndexChooseBean, BaseViewHolder> {

    private String language = "";

    private Map<String,Integer> map_color;
    public CompareAdapter() {
        super(R.layout.item_comparechoose, null);


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
    protected void convert(BaseViewHolder helper, IndexChooseBean item) {

        if(!TextUtils.isEmpty(item.getLogo())){

            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.img_choose));

        }


        if(item.getColor()!=0){
            ((ImageView)(helper.getView(R.id.img))).setBackgroundColor(mContext.getResources().getColor(item.getColor()));

        }
        helper.getView(R.id.img_choose).setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(item.getTitle())){

                helper.setText(R.id.text_title,item.getTitle());

        }

        if(!TextUtils.isEmpty(item.getValue())&&!"None".equals(item.getValue())){


            double value = Double.parseDouble(item.getValue());

            if(item.getId().equals(Commons.price)||item.getId().equals(Commons.marketValue)||item.getId().equals(Commons.tradeValue)||item.getId().equals(Commons.avgTrdValue)){

                value = MyUtils.getUnit()?Double.parseDouble(item.getValue()):Double.parseDouble(item.getValue())*Commons.rate;
            }


            String str_value =null;

            if(language=="zh_CN") {
                if (value >= 100000000) {
                    str_value =  MyUtils.fmtMicrometer(value / 100000000 + "") + "亿";
                } else if (value >= 10000) {
                    str_value =  MyUtils.fmtMicrometer(value / 10000 + "") + "万";
                }else
                    str_value = MyUtils.fmtMicrometer(value+"");
            }else{
                if(value>100000){
                    str_value =  MyUtils.fmtMicrometer((int)value/100000+"M");
                }else if(value>=1000){
                    str_value = MyUtils.fmtMicrometer((int)value/1000+"K");
                }else
                    str_value = MyUtils.fmtMicrometer(value+"");
            }

            if(item.getType().equals(Commons.price)||item.getType().equals(Commons.marketValue)||item.getId().equals(Commons.tradeValue)||item.getId().equals(Commons.avgTrdValue)){

                helper.setText(R.id.text_value,MyUtils.getUnitSymbol()+""+str_value);
            }else
                helper.setText(R.id.text_value,str_value);

//            if(item.getId().equals(Commons.price)||item.getId().equals(Commons.marketValue)){
//                helper.setText(R.id.text_value,"$"+ MyUtils.fmtMicrometer1(item.getValue()));
//            }else
//                helper.setText(R.id.text_value,MyUtils.fmtMicrometer1(item.getValue()));
        }

        if(!TextUtils.isEmpty(item.getQuate())){

            if(item.getQuate().contains("-")){
                helper.setText(R.id.text_quote,"↓"+MyUtils.fmtMicrometer(item.getQuate().replace("-",""))+"%");

                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.text_quote))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                }else
                    ((TextView)(helper.getView(R.id.text_quote))).setTextColor(mContext.getResources().getColor(R.color.red));

            }else{
                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.text_quote))).setTextColor(mContext.getResources().getColor(R.color.red));
                }else
                    ((TextView)(helper.getView(R.id.text_quote))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                helper.setText(R.id.text_quote,"↑"+MyUtils.fmtMicrometer(item.getQuate())+"%");
            }


            //  helper.setText(R.id.text_quote,MyUtils.fmtMicrometer(item.getQuate()));
        }


        helper.addOnClickListener(R.id.choose_layout);


    }
}


