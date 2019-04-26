package com.gikee.app.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.BaseChainBean;
import com.gikee.app.beans.HomeBaseLineBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.MultiLanguageUtil;

import java.util.Locale;

public class BaseChainTodayAdapter extends BaseQuickAdapter<BaseChainBean,BaseViewHolder> {


    private String language = "";

    public BaseChainTodayAdapter() {
        super(R.layout.item_basechain, null);
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
    protected void convert(BaseViewHolder helper, BaseChainBean item) {

        if(!TextUtils.isEmpty(item.getTitle())){

            if(language.equals("en")){
                helper.setText(R.id.title,item.getItemname());
            }else
                helper.setText(R.id.title,item.getTitle());

        }

        if(!TextUtils.isEmpty(item.getValue())){

            if("power".equals(item.getType())){
                if("ltc算力".equals(item.getTitle())){
                    helper.setText(R.id.value,MyUtils.fmtMicrometer1(item.getValue())+"EH/s");
                }else
                    helper.setText(R.id.value,MyUtils.fmtMicrometer(item.getValue())+"EH/s");
            }else{
                if(item.getTitle().equals("价格")){
                    String value = MyUtils.getUnit()?item.getValue():String.valueOf(Float.parseFloat(item.getValue())*Commons.rate);
                    helper.setText(R.id.value,MyUtils.getUnitSymbol()+""+value);
                }else if(item.getItemname().equals(Commons.tradeValue)){
                    Double value = MyUtils.getUnit()?Double.parseDouble(item.getValue()):Double.parseDouble(item.getValue())*Commons.rate;

                    helper.setText(R.id.value,MyUtils.getUnitSymbol()+""+MyUtils.getBigNumber(value));
                }else{
                    helper.setText(R.id.value,MyUtils.fmtMicrometer1(item.getValue()));
                }


            }


        }

        if(!TextUtils.isEmpty(item.getQuote())){

            if(item.getQuote().contains("-")){
                helper.setText(R.id.quote,"↓"+MyUtils.getNumber(item.getQuote().replace("-",""))+"%");
                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.quote))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                }else
                    ((TextView)(helper.getView(R.id.quote))).setTextColor(mContext.getResources().getColor(R.color.red));


            }else{
//                ((TextView)(helper.getView(R.id.quote))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.quote))).setTextColor(mContext.getResources().getColor(R.color.red));
                }else
                    ((TextView)(helper.getView(R.id.quote))).setTextColor(mContext.getResources().getColor(R.color.faa3c));
                helper.setText(R.id.quote,"↑"+MyUtils.getNumber(item.getQuote())+"%");
            }



        }

    }
}
