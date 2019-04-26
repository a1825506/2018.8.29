package com.gikee.app.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.resp.RankingDetailBean;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardAdapter extends BaseQuickAdapter<RankingDetailBean,BaseViewHolder>{


    public LeaderboardAdapter() {
        super(R.layout.item_rankdateil, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, RankingDetailBean item) {


        if(!TextUtils.isEmpty(item.getLogo())){
            helper.getView(R.id.ranking_img).setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.ranking_img));
        }else
            Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.hot)).into((CircleImageView) helper.getView(R.id.ranking_img));


        String rank = String.format(mContext.getResources().getString(R.string.value_rank),item.getRanking()+"");

        if(!TextUtils.isEmpty(item.getRanking()+"")){
            helper.setText(R.id.market_title,rank);
        }

        if(!TextUtils.isEmpty(item.getSymbol())){
            helper.setText(R.id.title2_context,item.getSymbol());
        }



        if(!TextUtils.isEmpty(item.getMarketValue())&&!TextUtils.isEmpty(item.getMarketValue_cny())){
            String marketValue = MyUtils.getUnit()?"$"+MyUtils.getBigNumber(Float.parseFloat(item.getMarketValue())):"¥"+MyUtils.getBigNumber(Float.parseFloat(item.getMarketValue_cny()));
            helper.setText(R.id.market_value,marketValue);
        }

        String main_price = "";

        String price = "";


        if(MyUtils.getLocalLanguage().equals("en")){

            price ="≈"+item.getPriceBtc();

            if( MyUtils.getUnit()){
                main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice());

            }else{

                main_price ="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
            }

//            main_price ="$"+MyUtils.fmtMicrometer(item.getPrice());

        }else{
            boolean ismain=false;

            if( MyUtils.getUnit()){
                main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice());
                ismain=true;
            }else{
                ismain=false;
                main_price ="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
            }

            if(ismain){
                price="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
            }else
                price="$"+MyUtils.fmtMicrometer1(item.getPrice());


        }





        if(!TextUtils.isEmpty(item.getPrice()+"")&&!TextUtils.isEmpty(item.getPrice_cny())){

//            String price = MyUtils.getUnit()?"$"+MyUtils.fmtMicrometer1(item.getPrice()+""):"¥"+MyUtils.fmtMicrometer1(item.getPrice_cny()+"");
            helper.setText(R.id.title3_context, main_price+"");
        }

        if(!TextUtils.isEmpty(item.getPriceBtc())){
            helper.setText(R.id.price_btc,price);
        }



        if(!TextUtils.isEmpty(item.getPriceChange())){

            Drawable background = MyUtils.getUnit()?mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount):mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount);


            if(item.getPriceChange().contains("-")){
                helper.setText(R.id.title4_context,"↓"+MyUtils.fmtMicrometer(item.getPriceChange().replace("-",""))+"%");
                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.title4_context))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));
                }else
                    ((TextView)(helper.getView(R.id.title4_context))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));

            }else{
                ((TextView)(helper.getView(R.id.title4_context))).setBackground(background);
                helper.setText(R.id.title4_context,"↑"+MyUtils.fmtMicrometer(item.getPriceChange())+"%");
                if(MyUtils.getQuateSymbol()){
                    ((TextView)(helper.getView(R.id.title4_context))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
                }else
                    ((TextView)(helper.getView(R.id.title4_context))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));

            }



        }

        helper.addOnClickListener(R.id.rank_layout);

    }

}
