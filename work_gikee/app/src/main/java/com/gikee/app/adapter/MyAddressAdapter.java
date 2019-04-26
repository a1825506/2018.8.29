package com.gikee.app.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.greendao.CollectBean;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAddressAdapter extends BaseItemDraggableAdapter<CollectBean, BaseViewHolder> {



    public MyAddressAdapter() {
        super(R.layout.item_mineaddress, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CollectBean item) {


        if (!TextUtils.isEmpty(item.getLogo())) {

            Glide.with(mContext).load(item.getLogo()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {


                    Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.item_allproject_logo));

                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    Glide.with(mContext).load(R.mipmap.exchange).into((CircleImageView) helper.getView(R.id.item_allproject_logo));
//                    super.onLoadFailed(errorDrawable);
                }
            });



        }

            if(!TextUtils.isEmpty(item.getName())){

                helper.setText(R.id.address_name,item.getName()+"_"+item.getAddress_type().toUpperCase());
            }

            if(!TextUtils.isEmpty(item.getAddressid())){

                if(item.getAddressid().contains("exchange")){
                    String value = String.format(mContext.getString(R.string.account_count),item.getCount()+"");
                    helper.setText(R.id.address_id,value);
                    helper.getView(R.id.copy).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.copy).setVisibility(View.VISIBLE);
                    helper.setText(R.id.address_id,item.getAddressid());
                }

            }

            if(!TextUtils.isEmpty(item.getBalance())){
                helper.getView(R.id.loading).setVisibility(View.GONE);
                helper.getView(R.id.price_layout).setVisibility(View.VISIBLE);
                if(MyUtils.isNumeric(item.getBalance())){
                    helper.setText(R.id.balance, MyUtils.fmtMicrometer1(item.getBalance())+item.getAddress_type().toUpperCase());
                    ((TextView)helper.getView(R.id.balance)).setTextColor(mContext.getResources().getColor(R.color.gray_33));
                    float balance_usd=0;
                    if(item.getAddress_type().toUpperCase().equals("ETH")){
                         balance_usd = MyUtils.getUnit()?Float.parseFloat(item.getBalance())* Commons.rateUSD_eth:Float.parseFloat(item.getBalance())* Commons.rateCNY_eth;

                    }else if(item.getAddress_type().toUpperCase().equals("BTC")){
                         balance_usd = MyUtils.getUnit()?Float.parseFloat(item.getBalance())* Commons.rateUSD_btc:Float.parseFloat(item.getBalance())* Commons.rateCNY_btc;
                    }

                    helper.setText(R.id.balance_usd,MyUtils.getUnitSymbol()+MyUtils.getBigNumber(balance_usd));
                }else{

                    helper.setText(R.id.balance, mContext.getString(R.string.check_address_));
                    ((TextView)helper.getView(R.id.balance)).setTextColor(mContext.getResources().getColor(R.color.title_color));
                }


            }else{
                helper.getView(R.id.price_layout).setVisibility(View.GONE);
                helper.getView(R.id.loading).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.loading_err).into((ImageView) helper.getView(R.id.loading));
            }

            if(item.getTrade_count()<1){
                helper.getView(R.id.trade_count).setVisibility(View.GONE);
            }else{
                helper.getView(R.id.trade_count).setVisibility(View.VISIBLE);
                if(item.getTrade_count()>99){
                    helper.setText(R.id.trade_count,"99+");
                }else
                  helper.setText(R.id.trade_count,item.getTrade_count()+"");
            }

            helper.addOnClickListener(R.id.item_allproject_content);

            helper.addOnClickListener(R.id.more_opera);

            helper.addOnClickListener(R.id.copy);
        }


}
