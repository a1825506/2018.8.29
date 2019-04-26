package com.gikee.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.SearchResultBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.type.ShowType;

import java.util.List;

public class SearchResultAdapter extends BaseMultiItemQuickAdapter<SearchResultBean,BaseViewHolder> {

    public static final int quotes = 0;

    public static final int project = 1;

    public static final int account =2;

    public static final int exchange = 3;

    public static final int fuzzysearch = 4;

    @Override
    protected int getDefItemViewType(int position) {

        String type = getItem(position).getShowType();

        if(type.equals(ShowType.quotes.getContent())){

            return quotes;

        }else if(type.equals(ShowType.project.getContent())){

            return project;

        }else if(type.equals(ShowType.account.getContent())){

            return account;

        }else if(type.equals(ShowType.exchange.getContent())){

            return exchange;

        }else if(type.equals(ShowType.fuzzysearch.getContent())){

            return fuzzysearch;

        }
        return super.getDefItemViewType(position);
    }

    public SearchResultAdapter(List data,Context context) {
        super(data);
        //搜索结果为行情的布局
        addItemType(quotes, R.layout.item_searchquateresult);

        //搜索结果为项目的布局
        addItemType(project, R.layout.item_searchprojectresult);

        //搜索结果为账户的布局
        addItemType(account, R.layout.item_searchaccountresult);

        //搜索结果为交易所的布局
        addItemType(exchange, R.layout.item_searchexchangeeresult);

        //模糊搜索的布局
        addItemType(fuzzysearch, R.layout.item_fuzzysearchresult);

        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultBean item) {

        switch (helper.getItemViewType()) {
            case fuzzysearch:
                if(!TextUtils.isEmpty(item.getSymbol())){
                    helper.setText(R.id.fuzzy_search,item.getSymbol());
                }
                if(!TextUtils.isEmpty(item.getLogo())) {
                    helper.getView(R.id.symbol_img).setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.symbol_img));
                }else
                    helper.getView(R.id.symbol_img).setVisibility(View.GONE);

                helper.addOnClickListener(R.id.layout);
                break;
            case quotes:
                if(!TextUtils.isEmpty(item.getExchange())){
                    helper.setText(R.id.exchange,item.getExchange());
                }
                if(!TextUtils.isEmpty(item.getTransaction_pair())){
                    helper.setText(R.id.symbol,item.getTransaction_pair());
                }
                if(!TextUtils.isEmpty(item.getTurnover())){
                    String value  = MyUtils.getUnit()?item.getTurnover():String.valueOf(Float.parseFloat(item.getTurnover())* Commons.rate);
                    helper.setText(R.id.turnover_amount,MyUtils.getUnitSymbol()+""+ MyUtils.fmtMicrometer(value));
                }
                if(!TextUtils.isEmpty(item.getPrice_pair())){
                    helper.setText(R.id.amount,MyUtils.fmtMicrometer1(item.getPrice_pair()));
                }

                if(!TextUtils.isEmpty(item.getPrice_usd())){
                    String value  = MyUtils.getUnit()?item.getPrice_usd():String.valueOf(Float.parseFloat(item.getPrice_usd())* Commons.rate);
                    helper.setText(R.id.amount_USD,MyUtils.getUnitSymbol()+""+MyUtils.fmtMicrometer1(value));
                }


                if(!TextUtils.isEmpty(item.getQuateChange())){
                    if(item.getQuateChange().contains("-")){

                        if(MyUtils.getQuateSymbol()){
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));
                        }else
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));


//                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
                        helper.setText(R.id.quatechange,"↓"+MyUtils.fmtMicrometer(item.getQuateChange())+"%");
                    }else{
                        if(MyUtils.getQuateSymbol()){
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
                        }else
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));

//                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));
                        helper.setText(R.id.quatechange,"↑"+MyUtils.fmtMicrometer(item.getQuateChange())+"%");
                    }
                }

                helper.addOnClickListener(R.id.layout);

                break;
            case project:
                String language = MyUtils.getLocalLanguage();

                if(!TextUtils.isEmpty(item.getSymbol())){
                    helper.setText(R.id.symbol,item.getSymbol());
                }

                if(!TextUtils.isEmpty(item.getLogo())){
                    Glide.with(mContext).load( item.getLogo()).into((ImageView) helper.getView(R.id.symbol_img));
                }else
                    Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.moren)).into((ImageView) helper.getView(R.id.symbol_img));

                if(!TextUtils.isEmpty(item.getMarketValue_usd())){
                    String marketValue = MyUtils.getUnit()?item.getMarketValue_usd():item.getMarketValue_cny();
                    double value = Double.parseDouble(marketValue);
                    String str_value =null;

                    if(language=="zh_CN") {
                        if (value >= 100000000) {
                            str_value =  MyUtils.fmtMicrometer(value / 100000000 + "") + "亿";
                        } else if (value >= 10000) {
                            str_value =  MyUtils.fmtMicrometer(value / 10000 + "") + "万";
                        }else
                            str_value = MyUtils.fmtMicrometer(value+"");
                    }else{
                        if(value>100000){
                            str_value =  MyUtils.fmtMicrometer(value/100000+"")+"M";
                        }else if(value>=1000){
                            str_value = MyUtils.fmtMicrometer(value/1000+"")+"K";
                        }else
                            str_value = MyUtils.fmtMicrometer(value+"");
                    }

                    helper.setText(R.id.turnover_amount,MyUtils.getUnitSymbol()+""+str_value);
                }




                String main_price = "";

                String price = "";


                if(MyUtils.getLocalLanguage().equals("en")){

                    price ="≈"+item.getPrice_btc()+"BTC";

                    if( MyUtils.getUnit()){
                        main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice_usd());

                    }else{

                        main_price ="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
                    }

//                    main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice_usd());

                }else{
                    boolean ismain=false;

                    if( MyUtils.getUnit()){
                        main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice_usd());
                        ismain=true;
                    }else{
                        ismain=false;
                        main_price ="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
                    }

                    if(ismain){
                        price="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
                    }else
                        price="$"+MyUtils.fmtMicrometer1(item.getPrice_usd());


                }






                if(!TextUtils.isEmpty(main_price)){
                    helper.setText(R.id.amount,main_price);

                }

                if(!TextUtils.isEmpty(price)){

                    helper.setText(R.id.amount_USD,price);

                }






                if (item.isCollect())
                    helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.title_color));
                else
                    helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.DFDFE4));

                if(!TextUtils.isEmpty(item.getQuateChange())){
                    if(item.getQuateChange().contains("-")){
                        helper.setText(R.id.quatechange,"↓"+MyUtils.fmtMicrometer(item.getQuateChange())+"%");
                        if(MyUtils.getQuateSymbol()){
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));
                        }else
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));



                    }else{
                        helper.setText(R.id.quatechange,"↑"+MyUtils.fmtMicrometer(item.getQuateChange())+"%");
                        if(MyUtils.getQuateSymbol()){
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
                        }else
                            ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));


                    }
                }
                helper.addOnClickListener(R.id.item_search_list_coll);
                helper.addOnClickListener(R.id.layout);

                break;
            case account:
                if(!TextUtils.isEmpty(item.getBalance())){

                    helper.setText(R.id.amount,MyUtils.fmtMicrometer(item.getBalance())+" EOS");
                    if(!TextUtils.isEmpty(item.getSymbol())){
                        helper.setText(R.id.symbol,item.getSymbol());
                    }

                    if(!TextUtils.isEmpty(item.getLogo())){
                        Glide.with(mContext).load( item.getLogo()).into((ImageView) helper.getView(R.id.symbol_img));
                    }else
                        Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.moren)).into((ImageView) helper.getView(R.id.symbol_img));

                    if (item.isCollect())
                        helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.title_color));
                    else
                        helper.setTextColor(R.id.item_search_list_coll, mContext.getResources().getColor(R.color.DFDFE4));

                    helper.addOnClickListener(R.id.item_search_list_coll);
                    helper.addOnClickListener(R.id.layout);
                }

                break;
            case exchange:
                if(!TextUtils.isEmpty(item.getLogo())){
                    Glide.with(mContext).load( item.getLogo()).into((ImageView) helper.getView(R.id.symbol_img));
                }else
                    Glide.with(mContext).load(mContext.getResources().getDrawable(R.mipmap.moren)).into((ImageView) helper.getView(R.id.symbol_img));


                if(!TextUtils.isEmpty(item.getSymbol())){
                    helper.setText(R.id.symbol,item.getSymbol());
                }

                if(!TextUtils.isEmpty(item.getVolume_24H())){
                    helper.setText(R.id.turnover_amount,"$"+MyUtils.fmtMicrometer(item.getVolume_24H()));
                }

                if(!TextUtils.isEmpty(item.getVolume_24H_CNY())){
                    helper.setText(R.id.turnover_CNY,"/"+MyUtils.fmtMicrometer(item.getVolume_24H_CNY())+" CNY");
                }

                helper.addOnClickListener(R.id.layout);


                break;
        }


        }
}
