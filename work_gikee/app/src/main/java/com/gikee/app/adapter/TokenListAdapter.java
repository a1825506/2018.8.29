package com.gikee.app.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.datas.Commons;

public class TokenListAdapter extends BaseQuickAdapter<TokendBean,BaseViewHolder> {

      public TokenListAdapter() {

        super(R.layout.item_tokenlist, null);
        }

    @Override
    protected void convert(BaseViewHolder helper, TokendBean item) {

        if(!TextUtils.isEmpty(item.getTitle())){


            helper.setText(R.id.token_symbol,item.getTitle());

        }


        if(!TextUtils.isEmpty(item.getValue())){

            if(Commons.BASELINE.equals("EOS")){
                helper.setText(R.id.token_price,item.getValue()+" "+item.getTitle());
            }else
                helper.setText(R.id.token_price,item.getValue());
        }

    }
}
