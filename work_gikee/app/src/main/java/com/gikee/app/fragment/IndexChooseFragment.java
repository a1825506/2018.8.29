package com.gikee.app.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.OwnerDistributeActivity;
import com.gikee.app.activity.SpecialTradeActivity;
import com.gikee.app.activity.Top100CurrencyActivity;
import com.gikee.app.activity.Top100TradeActivity;
import com.gikee.app.activity.TransactionDistributionActivity;
import com.gikee.app.activity.ZhanghuNumActivity;
import com.gikee.app.adapter.IndexAdapter;
import com.gikee.app.beans.IndexChooseBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;


public class IndexChooseFragment extends BaseFragment{


    @Bind(R.id.recyclerview_indexchoose)
    RecyclerView recyclerview_indexchoose;

    private IndexAdapter indexAdapter;

    private static IndexChooseFragment indexChooseFragment;

    private OnUpdateChoose onUpdateChoose;

    private Intent intent;

    private String coinSymbol;
    private String id;

    private boolean isShow=false;

    private List<String> choose_title=new ArrayList();



    public IndexChooseFragment(){

    }


    public static IndexChooseFragment getInstance(){


        if(indexChooseFragment == null){
            synchronized (IndexChooseFragment.class){
                if(indexChooseFragment == null){
                    indexChooseFragment = new IndexChooseFragment();
                }
            }
        }
        return indexChooseFragment;

    }


    public void setId(String symbol,String id){
        this.coinSymbol = symbol;
        this.id = id;

    }


    public void setData(List<IndexChooseBean> data){

        if(!isShow){
            isShow=true;
            indexAdapter.setNewData(data);
        }






    }


    public void setChooseTitle(List<String> data){


        this.choose_title = data;

    }


    @Override
    protected void setupViews(LayoutInflater inflater) {

        setContentView(inflater, R.layout.fragment_indexchoose);
    }

    @Override
    protected void initView() {

        choose_title.clear();

        choose_title.add(Commons.price);

        choose_title.add(Commons.allAddr);

        indexAdapter = new IndexAdapter();

        recyclerview_indexchoose.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        recyclerview_indexchoose.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerview_indexchoose.setAdapter(indexAdapter);

        onClick();

    }

    private void onClick() {

        indexAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.checkbox_choose){



                    if(indexAdapter.getData().get(position).isCheck()){
                        indexAdapter.getData().get(position).setCheck(false);
                        onUpdateChoose.updateChoose(indexAdapter.getData().get(position).getId());
                    }else{
                        if(choose_title.size()>=2) {

                            ToastUtils.show(getString(R.string.index_num));

                            indexAdapter.getData().get(position).setCheck(false);

                            indexAdapter.notifyItemChanged(position);

                            return;
                        }else{
                            indexAdapter.getData().get(position).setCheck(true);
                            onUpdateChoose.updateChoose(indexAdapter.getData().get(position).getId());
                        }

                    }


                    indexAdapter.notifyItemChanged(position);






                }else{

                    if(indexAdapter.getData().get(position).getId().equals(Commons.participateAddress)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.participateAddress));

                        intent.putExtra("type", Commons.participateAddress);

                        startActivity(intent);


                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.ownAddr)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.ownAddr_title));

                        intent.putExtra("type", Commons.ownAddr);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.avgTrdValue)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.avgTrdValue));

                        intent.putExtra("type", Commons.avgTrdValue);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.tradeValue)){
                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.tradeValue));

                        intent.putExtra("type", Commons.tradeValue);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.price)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.price_title));

                        intent.putExtra("type", Commons.price);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.newAccount)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.newCount));

                        intent.putExtra("type", Commons.newAccount);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.wakeCount)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.wakeCount));

                        intent.putExtra("type", Commons.wakeCount);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.inactiveCount)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.inactiveCount));

                        intent.putExtra("type", Commons.inactiveCount);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.topDis)){

                        intent = new Intent(getContext(),OwnerDistributeActivity.class);

                        intent.putExtra("id", coinSymbol);

                        intent.putExtra("title", getString(R.string.topDis_title));

                        intent.putExtra("type", Commons.topDis);


                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.specialTrade)){

                        intent = new Intent(getContext(),SpecialTradeActivity.class);

                        intent.putExtra("id", coinSymbol);

                        intent.putExtra("title",getString(R.string.specialTrade_title));

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.activeDis)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.activeDis_title));

                        intent.putExtra("type", Commons.activeDis);

                        startActivity(intent);
                        // startActivity(new Intent(context, ZhanghuNumActivity.class).putExtra("id", coinSymbol));
                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.tradevolume)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.tradevolume_title));

                        intent.putExtra("type", Commons.tradevolume);


                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.tradeCount)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.tradeCount_title));

                        intent.putExtra("type", Commons.tradeCount);


                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.bigTradeCountDis)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.bigTradeCountDis_title));

                        intent.putExtra("type", Commons.bigTradeCountDis);

                        startActivity(intent);



                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.topFreqAddr)){

                        startActivity(new Intent(getContext(), Top100TradeActivity.class).putExtra("id", coinSymbol));

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.allAddr)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.allAddr_title));

                        intent.putExtra("type", Commons.allAddr);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.ownAddr)) {

                        intent = new Intent(getContext(), ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title", getString(R.string.ownAddr_title));

                        intent.putExtra("type", Commons.ownAddr);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.avgTrdVol)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title",getString(R.string.avgTrdVol_title));

                        intent.putExtra("type", Commons.avgTrdVol);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.allGas)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title",getString(R.string.allGas_title));

                        intent.putExtra("type", Commons.allGas);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.avgGas)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title",getString(R.string.avgGas_title));

                        intent.putExtra("type", Commons.avgGas);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.marketValue)){

                        intent = new Intent(getContext(),ZhanghuNumActivity.class);

                        intent.putExtra("id", id);

                        intent.putExtra("symbol", coinSymbol);

                        intent.putExtra("title",getString(R.string.marketValue_title));

                        intent.putExtra("type", Commons.marketValue);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.tradeVolDis)){

                        intent = new Intent(getContext(),TransactionDistributionActivity.class);

                        intent.putExtra("id", coinSymbol);

                        intent.putExtra("title",getString(R.string.tradeVolDis_title));

                        intent.putExtra("type", Commons.tradeVolDis);

                        startActivity(intent);

                    }else if(indexAdapter.getData().get(position).getId().equals(Commons.tradeCountDis)){

                        intent = new Intent(getContext(),TransactionDistributionActivity.class);

                        intent.putExtra("id", coinSymbol);

                        intent.putExtra("title", getString(R.string.tradeCountDis_title));

                        intent.putExtra("type", Commons.tradeCountDis);

                        startActivity(intent);

                    }



                }

            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isShow=false;
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    public void setOnUpdateChoose(OnUpdateChoose mListener) {
        this.onUpdateChoose = mListener;
    }


    public interface OnUpdateChoose {
        void updateChoose(String address);
    }
}
