package com.gikee.app.adapter;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.beans.LeadItemBean;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.beans.TokendetailBean;

import java.util.ArrayList;
import java.util.List;

public class TokenBalanceAdapter extends BaseMultiItemQuickAdapter<TokendBean,BaseViewHolder> {

    public static final int item_type =0;

    public static final int title_type = 1;

    public static final int mulitem_type = 2;

    public static final int contract_type = 3;

    public  static int page=1;

    private IOnItemClick iOnItemClick;

    public TokenBalanceAdapter() {
        super( null);
        addItemType(item_type,R.layout.item_token_balance);
        addItemType(title_type,R.layout.item_token_title);
        addItemType(mulitem_type,R.layout.item_token_mulitem);
        addItemType(contract_type,R.layout.item_contract);
    }



    @Override
    protected int getDefItemViewType(int position) {

        int type = getItem(position).getType();

        if(type==item_type){

            return item_type;

        }else if(type==title_type){

            return title_type;

        }else if(type==mulitem_type){
            return mulitem_type;
        }else if(type==contract_type){
            return contract_type;
        }



        return super.getDefItemViewType(position);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final TokendBean item) {


        switch (helper.getItemViewType()) {

            case item_type:

                if(!TextUtils.isEmpty(item.getTitle())){

                    helper.setText(R.id.token_symbol,item.getTitle());

                }

                if(!TextUtils.isEmpty(item.getValue())){

                    if(item.getTitle().equals(mContext.getResources().getString(R.string.send))||item.getTitle().equals(mContext.getResources().getString(R.string.receive))){
                        ((TextView)(helper.getView(R.id.token_price))).setTextColor(mContext.getResources().getColor(R.color.Blue));
                        helper.getView(R.id.copy).setVisibility(View.VISIBLE);
                        helper.getView(R.id.copy).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ClipData   myClip = ClipData.newPlainText("text", item.getValue());
                                GikeeApplication.getMyApplicationContext().getMyClipboard().setPrimaryClip(myClip);
                                ToastUtils.show(mContext.getString(R.string.copied));

                            }
                        });

                        helper.addOnClickListener(R.id.token_price);
                    }else{
                        helper.getView(R.id.copy).setVisibility(View.GONE);
                        ((TextView)(helper.getView(R.id.token_price))).setTextColor(mContext.getResources().getColor(R.color.black));

                    }

                    helper.setText(R.id.token_price,item.getValue());

                }else
                    helper.setText(R.id.token_price,"");

                break;

            case title_type:

                if(!TextUtils.isEmpty(item.getTitle())){

                    helper.setText(R.id.item_title,item.getTitle());

                }
                break;
            case mulitem_type:

                if(!TextUtils.isEmpty(item.getTitle())){

                    helper.setText(R.id.title,item.getTitle());

                }

                final LeaderItemAdapter leaderItemAdapter = new LeaderItemAdapter();


                RecyclerView recyclerView = helper.getView(R.id.recycleview);

                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                recyclerView.setAdapter(leaderItemAdapter);

                leaderItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(leaderItemAdapter.getData().get(position).getItemName()));//Url 就是你要打开的网址
                        intent.setAction(Intent.ACTION_VIEW);
                        mContext.startActivity(intent); //启动浏览器

                    }
                });

                //StringBuilder sb = new StringBuilder();

                List<LeadItemBean> leadItemBeanList = new ArrayList<>();

                for(int i=0;i<item.getValue_list().size();i++){

                    LeadItemBean leadItemBean = new LeadItemBean();

                    leadItemBean.setItemName(item.getValue_list().get(i));

                    leadItemBean.setType("url");
                   // sb.append(item.getValue_list()+",");

                    leadItemBeanList.add(leadItemBean);
                }

                leaderItemAdapter.setNewData(leadItemBeanList);

               // helper.setText(R.id.token_price,sb.toString());

                break;

            case contract_type:

                if(item.getTrade_type().equals("erc20")){
                    helper.setText(R.id.item_title,mContext.getString(R.string.token_remove));
                    helper.getView(R.id.item_flag).setVisibility(View.GONE);
                    helper.getView(R.id.item_value).setVisibility(View.GONE);
                    helper.getView(R.id.copy).setVisibility(View.GONE);
                    helper.getView(R.id.left_img).setVisibility(View.GONE);


                }else{

                    helper.getView(R.id.item_flag).setVisibility(View.VISIBLE);
                    helper.getView(R.id.item_value).setVisibility(View.VISIBLE);
                    helper.getView(R.id.copy).setVisibility(View.VISIBLE);
                    helper.getView(R.id.left_img).setVisibility(View.VISIBLE);
                }

                if(TextUtils.isEmpty(item.getValue())){

                    helper.setText(R.id.item_value,item.getValue());

                    helper.getView(R.id.item_value).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            iOnItemClick.onItemClick(item.getValue());
                        }
                    });

                }


                final ContracttradeItemAdapter contracttradeItemAdapter = new ContracttradeItemAdapter();

                RecyclerView trade_recycler = helper.getView(R.id.trade_recycler);

                trade_recycler.setLayoutManager(new LinearLayoutManager(mContext));

                trade_recycler.setAdapter(contracttradeItemAdapter);

                final int limite = 10;

                final List<TokendetailBean> transferdetail = item.getTransferdetail();

                final int pageCount =transferdetail.size()/limite;



                if(transferdetail.size()<10){

                    helper.getView(R.id.more_data).setVisibility(View.GONE);

                    contracttradeItemAdapter.setNewData(transferdetail);

                }else
                    contracttradeItemAdapter.setNewData(transferdetail.subList(0,limite*page));


                helper.getView(R.id.more_data).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(page<=pageCount){

                            contracttradeItemAdapter.setNewData(transferdetail.subList(limite*page,limite*(page+1)));

                            page++;

                        }else{
                            page=1;
                            helper.getView(R.id.more_data).setVisibility(View.GONE);

                        }




                    }
                });



                contracttradeItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        switch (view.getId()) {
                            case R.id.from_value:
                                iOnItemClick.onItemClick(contracttradeItemAdapter.getData().get(position).getFrom());
//                                Log.e("Contracttrade", contracttradeItemAdapter.getData().get(position).getFrom());
                                break;
                            case R.id.to_value:
                                iOnItemClick.onItemClick(contracttradeItemAdapter.getData().get(position).getTo());
//                                Log.e("Contracttrade", contracttradeItemAdapter.getData().get(position).getTo());
                                break;
                        }


                    }
                });

                break;



        }

    }


    public void setIOnItemClick(IOnItemClick mListener) {
        this.iOnItemClick = mListener;
    }


    public interface IOnItemClick {
        void onItemClick(String address);
    }
}
