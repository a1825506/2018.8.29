package com.gikee.app.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.TokenAddBean;
import com.gikee.app.views.RiseNumberTextView;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.taobao.accs.init.Launcher_InitAccs.mContext;

public class AllProjectCollListAdapter extends BaseItemDraggableAdapter<TokenAddBean, BaseViewHolder> {



    public AllProjectCollListAdapter() {
        super(R.layout.item_project, null);
    }

    public AllProjectCollListAdapter(int layoutResId, List<TokenAddBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TokenAddBean item) {

        if(item!=null){
            if(!TextUtils.isEmpty(item.getLogo())){
                Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.item_allproject_logo));
            }else
                Glide.with(mContext).load(R.mipmap.default_token).into((CircleImageView) helper.getView(R.id.item_allproject_logo));




            if(!TextUtils.isEmpty(item.getSymbol())){
                helper.setText(R.id.item_allproject_name, item.getSymbol());
            }

            if(item.isShowReal_timeData()){

                helper.getView(R.id.more_info).setVisibility(View.GONE);

                helper.getView(R.id.close).setVisibility(View.VISIBLE);

                helper.getView(R.id.real_time_info).setVisibility(View.VISIBLE);

            }else{

                helper.getView(R.id.more_info).setVisibility(View.VISIBLE);

                helper.getView(R.id.close).setVisibility(View.GONE);

                helper.getView(R.id.real_time_info).setVisibility(View.GONE);
            }

            RiseNumberTextView textView = ((RiseNumberTextView)(helper.getView(R.id.activity_account_txt)));

            if(!TextUtils.isEmpty(item.getActiveAccounts())){

                textView.setDuration(1500);

                textView.setFloat("",0,Integer.parseInt(item.getActiveAccounts()));

                textView.start();
            }else{
                textView.setDuration(1500);

                textView.setFloat("",0,0);

                textView.start();
            }
//                textView.setText("0");

            RiseNumberTextView textView1 = ((RiseNumberTextView)(helper.getView(R.id.new_account_txt)));

            if(!TextUtils.isEmpty(item.getNewAccounts())){

                textView1.setDuration(1500);

                textView1.setFloat("",0,Integer.parseInt(item.getNewAccounts()));

                textView1.start();
            }else{
                textView1.setDuration(1500);

                textView1.setFloat("",0,0);

                textView1.start();
            }
//                textView1.setText("0");

            RiseNumberTextView textView2 = ((RiseNumberTextView)(helper.getView(R.id.trade_count_txt)));


            if(!TextUtils.isEmpty(item.getTradeCount())){

                textView2.setDuration(1500);

                textView2.setFloat("",0,Integer.parseInt(item.getTradeCount()));

                textView2.start();
            }else{
                textView2.setDuration(1500);

                textView2.setFloat("",0,0);

                textView2.start();
            }


            if(!TextUtils.isEmpty(item.getMarketValue())){
                String markervalue =MyUtils.getUnit()?item.getMarketValue():item.getMarketValue_cny();
                helper.setText(R.id.marketvalue,MyUtils.getUnitSymbol()+""+MyUtils.getBigNumber(Double.parseDouble(markervalue)));
            }else
                helper.setText(R.id.marketvalue, "-");


            if(!TextUtils.isEmpty(item.getBlock())){

                helper.getView(R.id.block).setVisibility(View.VISIBLE);

                helper.getView(R.id.block_img).setVisibility(View.VISIBLE);

                helper.setText(R.id.block,item.getBlock());

            }else{
                helper.getView(R.id.block_img).setVisibility(View.GONE);

                helper.getView(R.id.block).setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(item.getQuateChange())){
                helper.getView(R.id.quatechange).setVisibility(View.VISIBLE);
                Drawable background = MyUtils.getUnit()?mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount):mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount);

                if(item.getQuateChange().contains("-")){
                    helper.setText(R.id.quatechange,"↓"+MyUtils.fmtMicrometer(item.getQuateChange().replace("-",""))+"%");
                    if(MyUtils.getQuateSymbol()){
                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));
                    }else
                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));

                }else{
                    ((TextView)(helper.getView(R.id.quatechange))).setBackground(background);
                    helper.setText(R.id.quatechange,"↑"+MyUtils.fmtMicrometer(item.getQuateChange())+"%");
                    if(MyUtils.getQuateSymbol()){
                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_newaccount));
                    }else
                        ((TextView)(helper.getView(R.id.quatechange))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_tradecount));

                }

            }else
                helper.getView(R.id.quatechange).setVisibility(View.GONE);

            String main_price = "";

            String price = "";



            /***/

            if(!TextUtils.isEmpty(item.getPrice())&&!TextUtils.isEmpty(item.getPrice_btc())&&!TextUtils.isEmpty(item.getPrice_cny())){



                if(MyUtils.getLocalLanguage().equals("en")){

                    price ="≈"+item.getPrice_btc()+"BTC";

                    if( MyUtils.getUnit()){
                        main_price ="$"+MyUtils.fmtMicrometer1(item.getPrice());

                    }else{

                        main_price ="¥"+MyUtils.fmtMicrometer1(item.getPrice_cny());
                    }

//                    main_price ="$"+MyUtils.fmtMicrometer(item.getPrice());


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
            }



            /***/


        if(!TextUtils.isEmpty(main_price)){
            helper.setText(R.id.main_price, main_price);
        }else
            helper.setText(R.id.main_price, "-");

            if(!TextUtils.isEmpty(price)){
                helper.setText(R.id.price, price);
            }else
                helper.setText(R.id.price, "-");

        helper.addOnClickListener(R.id.item_allproject_content);

        helper.addOnClickListener(R.id.more_info);

        helper.addOnClickListener(R.id.close);

        helper.addOnClickListener(R.id.more_opera);


        }

    }
}
