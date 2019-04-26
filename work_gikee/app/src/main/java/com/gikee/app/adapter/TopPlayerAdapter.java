package com.gikee.app.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.resp.Top100Bean;

public class TopPlayerAdapter extends BaseQuickAdapter<Top100Bean,BaseViewHolder> {

    public TopPlayerAdapter() {
        super(R.layout.item_topplayer, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Top100Bean item) {

        if(helper.getLayoutPosition()<3){
            ((TextView)(helper.getView(R.id.rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_redlight));
            ((TextView)(helper.getView(R.id.rank))).setTextColor(mContext.getResources().getColor(R.color.title_color));

        }else{
            ((TextView)(helper.getView(R.id.rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));

            ((TextView)(helper.getView(R.id.rank))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

        }


        helper.setText(R.id.rank,"NO."+item.getRanking());

        if(!TextUtils.isEmpty(item.getAddress())){

                helper.setText(R.id.address,item.getAddress());

        }

        if(!TextUtils.isEmpty(item.getValue())){
            helper.setText(R.id.addrss_flag, MyUtils.fmtMicrometer(item.getValue()));

        }



        if(!TextUtils.isEmpty(item.getRatio())){
            if(item.getRatio().contains("%")){
                helper.setText(R.id.unit,item.getRatio());
            }else
                helper.setText(R.id.unit,MyUtils.fmtMicrometer((Double.parseDouble(item.getRatio())*100)+"")+"%");

        }


        boolean isBaseLine=false;
        for(int i = 0; i< Commons.baseLine2.length; i++){
            if(Commons.id.equals(Commons.baseLine2[i])){
                isBaseLine=true;
            }

        }

        if(!isBaseLine){
            helper.addOnClickListener(R.id.address);
            ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.Blue));
        }else
            ((TextView)(helper.getView(R.id.address))).setTextColor(mContext.getResources().getColor(R.color.black));


    }
}
