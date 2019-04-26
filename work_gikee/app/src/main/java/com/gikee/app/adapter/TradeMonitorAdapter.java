package com.gikee.app.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.datas.Commons;
import com.gikee.app.resp.TableDataBean;
import com.gikee.app.type.ShowType;

import java.text.DecimalFormat;
import java.util.List;


public class TradeMonitorAdapter extends BaseMultiItemQuickAdapter<TableDataBean,BaseViewHolder> {

    public static final int specialtrade = 0;

    public static final int bigtrade = 1;

    public static final int FrequentlyTrde =2;

    public static final int topFreqAddr=3;

    public static final int topPlayer=4;

    public OnAddressClick onAddressClick;


    @Override
    protected int getDefItemViewType(int position) {
        String type = getItem(position).getType();
        if(ShowType.specialAddress.getContent().equals(type)){

            return specialtrade;

        }else if(ShowType.bigTrade.getContent().equals(type)){

            return bigtrade;

        }else if(ShowType.frequentTrade.getContent().equals(type)){

            return FrequentlyTrde;

        }else if(ShowType.topFreqAddr.getContent().equals(type)){

            return topFreqAddr;

        }else if(ShowType.topPlayer.getContent().equals(type)){

            return topPlayer;
        }
        return super.getDefItemViewType(position);
    }


    public TradeMonitorAdapter(Context context) {
        super(null);
        addItemType(specialtrade, R.layout.item_specialtrade_item);
        addItemType(bigtrade, R.layout.item_specialtrade_item);
        addItemType(FrequentlyTrde, R.layout.item_frequenttrade_item);
        addItemType(topFreqAddr,R.layout.item_topfreqaddr);
        addItemType(topPlayer,R.layout.item_topfreqaddr);
        mContext = context;
    }




    @Override
    protected void convert(BaseViewHolder helper, final TableDataBean item) {

        switch (helper.getItemViewType()) {
            case specialtrade:

                if (!TextUtils.isEmpty(item.getFrom()) && !TextUtils.isEmpty(item.getTo())) {

                    if(item.getFrom().length()>30){

                        helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"...");
                    }

                    if(item.getTo().length()>30){
                        helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"...");
                    }

                } else if (!TextUtils.isEmpty(item.getFrom()) && TextUtils.isEmpty(item.getTo())) {

                    if(item.getFrom().length()>30){

                        helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"...");
                    }

                    //发散
                    ((TextView) (helper.getView(R.id.addrss_name_to))).setBackgroundResource(R.drawable.shape_btn_red);

                    ((TextView) (helper.getView(R.id.addrss_name_to))).setTextColor(mContext.getResources().getColor(R.color.white));

                    helper.setText(R.id.addrss_name_to, R.string.more_radiation);


                } else if (TextUtils.isEmpty(item.getFrom()) && !TextUtils.isEmpty(item.getTo())) {

                    helper.setText(R.id.addrss_name_to, item.getTo());

                    helper.getView(R.id.addrss_name).setVisibility(View.INVISIBLE);

                    if(item.getTo().length()>30){

                        helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"...");
                    }

                    ((TextView) (helper.getView(R.id.addrss_name))).setBackgroundResource(R.drawable.shape_btn_green);

                    ((TextView) (helper.getView(R.id.addrss_name))).setTextColor(mContext.getResources().getColor(R.color.white));


                }

                if(TextUtils.isEmpty(item.getFromName())){
                    helper.getView(R.id.exchange_from).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.exchange_from).setVisibility(View.VISIBLE);
                    helper.setText(R.id.exchange_from,item.getFromName());
                }

                if(TextUtils.isEmpty(item.getToName())){
                    helper.getView(R.id.exchange_to).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.exchange_to).setVisibility(View.VISIBLE);
                    helper.setText(R.id.exchange_to,item.getToName());
                }



                helper.setText(R.id.addrss_tiem, item.getLatestTime());

                helper.setText(R.id.item_title, mContext.getString(R.string.specialtrade));



                helper.setText(R.id.addrss_name_value,   MyUtils.fmtMicrometer(item.getValue())+ " " + item.getUnit());


                helper.setText(R.id.addrss_name_value, new DecimalFormat("0.00").format(Double.parseDouble(item.getValue())) + " " + item.getUnit());


                helper.addOnClickListener(R.id.specialaddress_layout);
                break;

            case bigtrade:


                if (!TextUtils.isEmpty(item.getFrom()) && !TextUtils.isEmpty(item.getTo())) {

                    if(item.getFrom().length()>30){

                        helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"...");
                    }

                    if(item.getTo().length()>30){
                        helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"...");
                    }


                } else if (!TextUtils.isEmpty(item.getFrom()) && TextUtils.isEmpty(item.getTo())) {
                    //发散

                    if(item.getFrom().length()>30){

                        helper.setText(R.id.addrss_name, item.getFrom().substring(0,6)+"...");
                    }


                    ((TextView) (helper.getView(R.id.addrss_name_to))).setBackgroundResource(R.drawable.shape_btn_red);
                    //helper.setBackgroundColor(R.id.addrss_name_to,R.drawable.shape_btn_red);
                    helper.setText(R.id.addrss_name_to, R.string.more_radiation);

                } else if (TextUtils.isEmpty(item.getFrom()) && !TextUtils.isEmpty(item.getTo())) {
                    //聚集
                    helper.setText(R.id.addrss_name, R.string.more_addresscollect);

                    ((TextView) (helper.getView(R.id.addrss_name))).setBackgroundResource(R.drawable.shape_btn_green);

                    if(item.getTo().length()>30){

                        helper.setText(R.id.addrss_name_to, item.getTo().substring(0,6)+"...");
                    }


                }
                if(TextUtils.isEmpty(item.getFromName())){
                    helper.getView(R.id.exchange_from).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.exchange_from).setVisibility(View.VISIBLE);
                    helper.setText(R.id.exchange_from,item.getFromName());
                }

                if(TextUtils.isEmpty(item.getToName())){
                    helper.getView(R.id.exchange_to).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.exchange_to).setVisibility(View.VISIBLE);
                    helper.setText(R.id.exchange_to,item.getToName());
                }

                helper.setText(R.id.item_title, mContext.getString(R.string.bigTradeCountDis));


                helper.setText(R.id.addrss_tiem, item.getLatestTime());

                helper.setText(R.id.addrss_name_value,   MyUtils.fmtMicrometer(item.getValue())+ " " + item.getUnit());




                helper.addOnClickListener(R.id.specialaddress_layout);
                break;
                case FrequentlyTrde:

                    helper.setText(R.id.addrss_tiem,item.getAddress());

                    String unit= "";

                    if(!TextUtils.isEmpty(item.getUnit())){
                        if(item.getUnit().equals("次")){
                            unit = mContext.getString(R.string.ci);
                        }else
                            unit= item.getUnit();
                    }

                    helper.setText(R.id.addrss_name_value,   MyUtils.fmtMicrometer(item.getValue())+ " " + unit);


                    helper.setText(R.id.addrss_time,item.getLatestTime());


                    helper.addOnClickListener(R.id.specialaddress_layout);

                    helper.setText(R.id.item_title, mContext.getString(R.string.frequentlyTrade));

                    if(TextUtils.isEmpty(item.getAddressName())){
                        helper.getView(R.id.addrss_flag).setVisibility(View.GONE);
                    }else{
                        helper.getView(R.id.addrss_flag).setVisibility(View.VISIBLE);
                        helper.setText(R.id.addrss_flag,item.getAddressName());
                    }

                    break;
            case topFreqAddr:


                if(helper.getLayoutPosition()<3){
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_redlight));
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setTextColor(mContext.getResources().getColor(R.color.title_color));

                }else{
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));

                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

                }

                helper.setText(R.id.freqaddr_rank,"NO."+item.getRanking());

                if(!TextUtils.isEmpty(item.getAddress())){
                    helper.setText(R.id.freqaddr_address,item.getAddress());

                  //  ((TextView)(helper.getView(R.id.freqaddr_address))).setTextColor(mContext.getResources().getColor(R.color.Blue));

                    helper.getView(R.id.freqaddr_address).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            onAddressClick.onAddress(item.getAddress());

                        }
                    });

                }else
                    helper.setText(R.id.freqaddr_address, mContext.getResources().getString(R.string.noyestday_trade));

                if(!TextUtils.isEmpty(item.getValue())){
                    helper.setText(R.id.freqaddr_unit,   MyUtils.fmtMicrometer(item.getValue())+ " " + mContext.getString(R.string.ci));
                }


               // helper.addOnClickListener(R.id.specialaddress_layout);

                if(TextUtils.isEmpty(item.getAddressName())){
                    helper.getView(R.id.addrss_flag).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.addrss_flag).setVisibility(View.VISIBLE);
                    helper.setText(R.id.addrss_flag,item.getAddressName());
                }

                break;
            case topPlayer:

                helper.setText(R.id.freqaddr_rank,"NO."+item.getRanking());

                helper.getView(R.id.freqaddr_address).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onAddressClick.onAddress(item.getAddress());

                    }
                });


                if(helper.getLayoutPosition()<3){
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_redlight));
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setTextColor(mContext.getResources().getColor(R.color.title_color));

                }else{
                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_history));

                    ((TextView)(helper.getView(R.id.freqaddr_rank))).setTextColor(mContext.getResources().getColor(R.color.gray_33));

                }



                if(!TextUtils.isEmpty(item.getAddress())){
                    helper.setText(R.id.freqaddr_address,item.getAddress());


                }else
                    helper.setText(R.id.freqaddr_address, mContext.getResources().getString(R.string.noyestday_trade));

                if(!TextUtils.isEmpty(item.getValue())){
                    helper.setText(R.id.freqaddr_unit,   MyUtils.fmtMicrometer(item.getValue())+ " " + Commons.smybl);
                }


                helper.addOnClickListener(R.id.specialaddress_layout);

                if(TextUtils.isEmpty(item.getAddressName())){
                    helper.getView(R.id.addrss_flag).setVisibility(View.GONE);
                }else{
                    helper.getView(R.id.addrss_flag).setVisibility(View.VISIBLE);
                    helper.setText(R.id.addrss_flag,item.getAddressName());
                }

                break;
        }


    }




    public void setOnAddressClick(OnAddressClick mListener) {

        this.onAddressClick = mListener;
    }


    public interface OnAddressClick {
        void onAddress(String address);
    }

}
