package com.gikee.app.adapter;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.beans.LeadItemBean;
import com.gikee.app.beans.MarketRateBean;

import java.util.List;

public class MarketRateAdapter extends BaseQuickAdapter<MarketRateBean,BaseViewHolder> {

    public MarketRateAdapter() {

        super(R.layout.item_marketrate, null);

    }
    @Override
    protected void convert(BaseViewHolder helper, MarketRateBean item) {

        if(!TextUtils.isEmpty(item.getSymbol())){

            helper.setText(R.id.text_symbol,item.getSymbol());
        }


        if(!TextUtils.isEmpty(item.getValue())){

            helper.setText(R.id.text_value,item.getValue());

            ProgressBar mProgressBar = (ProgressBar) helper.getView(R.id.progress_rate);

//            ClipDrawable drawable = new ClipDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.title_color)), Gravity.LEFT, ClipDrawable.HORIZONTAL);
//            mProgressBar.setProgressDrawable(drawable);//必须先设置到progressbar上再设置level，告诉这个drawable的宽度有多宽，这个level才能生效


            if(item.getValue().contains("%")){

                float progress = Float.parseFloat(item.getValue().replace("%",""));

                mProgressBar.setProgress((int)progress);

            }
        }

        helper.addOnClickListener(R.id.layout);

    }
}
