package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.MarkerViewBean;
import com.gikee.app.datas.Commons;

import java.util.Map;

public class MarkViewAdapter extends BaseQuickAdapter<MarkerViewBean,BaseViewHolder> {

    private Map<String,Integer> map_color ;
    public MarkViewAdapter(Map<String,Integer> color_map) {
        super(R.layout.item_markerview, null);
        this.map_color = color_map;
    }


    @Override
    protected void convert(BaseViewHolder helper, MarkerViewBean item) {

        if(!TextUtils.isEmpty(item.getId())){
            helper.getView(R.id.markerview_img).setVisibility(View.VISIBLE);
            for(Map.Entry<String,Integer> color_entry :map_color.entrySet()){

                if(color_entry.getKey().equals(item.getId())){
                    helper.getView(R.id.markerview_img).setVisibility(View.VISIBLE);
                    ((ImageView)(helper.getView(R.id.markerview_img))).setBackgroundColor(mContext.getResources().getColor(color_entry.getValue()));
                }

            }
        }else
            helper.getView(R.id.markerview_img).setVisibility(View.GONE);



        if(!TextUtils.isEmpty(item.getTitle())){
            helper.setText(R.id.markerview_title,item.getTitle()+":");
        }else
            helper.setText(R.id.markerview_title,"");


        if(!TextUtils.isEmpty(item.getValue())){
            if(item.getId().equals(Commons.price)||item.getId().equals(Commons.marketValue)||item.getId().equals(Commons.tradeValue)||item.getId().equals(Commons.avgTrdValue)){
                helper.setText(R.id.markerview_value, MyUtils.getUnitSymbol()+""+item.getValue());
            }else
                helper.setText(R.id.markerview_value,item.getValue());

            if(!TextUtils.isEmpty(item.getType())){
                if(item.getType().equals(Commons.price)||item.getType().equals(Commons.marketValue)||item.getType().equals(Commons.tradeValue)||item.getType().equals(Commons.avgTrdValue)){
                    helper.setText(R.id.markerview_value,MyUtils.getUnitSymbol()+""+item.getValue());
                }
            }
        }else
            helper.setText(R.id.markerview_value,"");




    }
}
