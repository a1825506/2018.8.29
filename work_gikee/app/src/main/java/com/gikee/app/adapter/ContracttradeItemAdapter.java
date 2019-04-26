package com.gikee.app.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.beans.TokendetailBean;
import com.gikee.app.language.MultiLanguageUtil;

import java.util.Locale;

public class ContracttradeItemAdapter extends BaseQuickAdapter<TokendetailBean, BaseViewHolder> {


    private String language = MyUtils.getLocalLanguage();
    public ContracttradeItemAdapter(){
        super(R.layout.contract_item, null);



    }


    @Override
    protected void convert(BaseViewHolder helper, TokendetailBean item) {

        if(!TextUtils.isEmpty(item.getFrom())){

            helper.setText(R.id.from_value,item.getFrom());

            helper.addOnClickListener(R.id.from_value);

        }


        if(!TextUtils.isEmpty(item.getTo())){

            helper.setText(R.id.to_value,item.getTo());

            helper.addOnClickListener(R.id.to_value);

        }

        if(!TextUtils.isEmpty(item.getToken())){

            if(item.getToken().contains("$")){
                helper.setText(R.id.value,(item.getToken()));
            }else if(item.getToken().contains("EOS")){
                helper.setText(R.id.value,item.getToken());
            }else{
                if(MyUtils.isNumeric(item.getToken())){
                    helper.setText(R.id.value,MyUtils.getNumber(item.getToken())+"ETH");
                }else
                    helper.setText(R.id.value,item.getToken());
            }


        }




    }
}
