package com.gikee.app.adapter;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.resp.RankingDetailBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *首页排行榜适配器
 */

public class HomeLeaderboardAdapter  extends BaseQuickAdapter<RankingDetailBean,BaseViewHolder> {

    public HomeLeaderboardAdapter() {
        super(R.layout.item_homerank, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankingDetailBean item) {

        if(!TextUtils.isEmpty(item.getRanking()+"")){

            helper.setText(R.id.rank,item.getRanking()+"");

        }


        if(!TextUtils.isEmpty(item.getLogo())){
            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.rank_img));

        }else
            Glide.with(mContext).load(R.mipmap.moren).into((CircleImageView) helper.getView(R.id.rank_img));


        if(!TextUtils.isEmpty(item.getSymbol())){

            helper.setText(R.id.rank_symbol,item.getSymbol());

        }


        if(!TextUtils.isEmpty(item.getPrice()+"")){

            helper.setText(R.id.rank_value, MyUtils.fmtMicrometer(item.getPrice()+""));

        }

        helper.addOnClickListener(R.id.rank_layout);

    }
}


