package com.gikee.app.adapter;

import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.AssetBean;

public class AssestsDataAdapter extends BaseQuickAdapter<AssetBean,BaseViewHolder> {


    private String msymbol;

    public AssestsDataAdapter(String symbol) {

        super(R.layout.item_assestsdata, null);

        msymbol= symbol;
    }

    @Override
    protected void convert(BaseViewHolder helper, AssetBean item) {

        if(!TextUtils.isEmpty(item.getType())){

            if(item.getType().equals("in")){

                helper.setText(R.id.assest_title,mContext.getString(R.string.asset_transfer));

            }else if(item.getType().equals("out")){

                helper.setText(R.id.assest_title,mContext.getString(R.string.asset_transfer_out));
            }



        }

        if(!TextUtils.isEmpty(item.getAsset()+"")){

            helper.setText(R.id.assets_in, MyUtils.fmtMicrometer(item.getAsset()+""));
        }

        if(!TextUtils.isEmpty(item.getAsset_usd()+"")){

            helper.setText(R.id.assets_in_usd,MyUtils.fmtMicrometer(item.getAsset_usd()+""));
        }


        if(!TextUtils.isEmpty(msymbol)){

            helper.setText(R.id.symbol,msymbol.toUpperCase());

        }




        if(!TextUtils.isEmpty(item.getCount()+"")){

            String value = String.format(mContext.getString(R.string.asset_in_count),MyUtils.fmtMicrometer(item.getCount()+""));

            helper.setText(R.id.assets_in_count,value);
        }
    }
}