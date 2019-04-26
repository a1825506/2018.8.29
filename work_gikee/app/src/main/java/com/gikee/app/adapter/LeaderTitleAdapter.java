package com.gikee.app.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.resp.RankingLabelBean;

public class LeaderTitleAdapter extends BaseQuickAdapter<RankingLabelBean,BaseViewHolder> {

    public LeaderTitleAdapter() {
        super(R.layout.tab_leaderitem, null);

        savedLanguageType = MultiLanguageUtil.getInstance().getLanguageType();
    }

    public static final int line_one = 0;

    public static final int line_two = 1;

    public int savedLanguageType = 0;



    @Override
    protected int getDefItemViewType(int position) {

        int type = getItem(position).getCnLabel().length();

        if(type>20){
            return line_two;
        }else
            return line_one;

    }

    @Override
    protected void convert(BaseViewHolder helper, RankingLabelBean item) {

        if(!TextUtils.isEmpty(item.getCnLabel())){
            String title = "";

            if("tradeCount".equals(item.getEnLabel())||"ownAddress".equals(item.getEnLabel())||"newAddress".equals(item.getEnLabel())||"fromAddress".equals(item.getEnLabel())||"marketValue".equals(item.getEnLabel())) {


                title = item.getCnLabel();
            }else{
                ((TextView)(helper.getView(R.id.tab_tx))).setTextColor(mContext.getResources().getColor(R.color.gray_33));
                title = item.getCnLabel();
                helper.getView(R.id.hot_search).setBackgroundResource(R.drawable.shape_btn_graylight);
            }

//            if (title.length() > 30 && title.startsWith("0x")) {
//                helper.setText(R.id.tab_tx,title.substring(0,8)+"...");
//            }else
                helper.setText(R.id.tab_tx,title);



        }

        if(!"history".equals(item.getType())){
            if(helper.getLayoutPosition()==0){
                helper.getView(R.id.hot_search).setBackgroundResource(R.drawable.shape_btn_leader_selected);
            }

            if(helper.getLayoutPosition()>1){
                helper.getView(R.id.hot_search).setBackgroundResource(R.drawable.shape_btn_leadernu);
            }
            helper.addOnClickListener(R.id.hot_search);
        }else{

                helper.getView(R.id.hot_search).setBackgroundResource(R.drawable.shape_btn_history);

                helper.addOnClickListener(R.id.hot_search);

        }



    }
}
