package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.Observer.base_observe.IObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.MPEditCollectActivity;
import com.gikee.app.activity.MPEditTrandActivity;
import com.gikee.app.activity.ProjectDetailActivity;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.adapter.AllProjectCollListAdapter;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.beans.TokenAddBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.preference_config.PreferenceUtil;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.project.MineProjectView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.PriceChangeBean;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.views.MyRefreshHeader;
import com.gikee.app.views.dialogs.ChoseUnitDialog;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by THINKPAD on 2018/3/14.
 */

@SuppressLint("ValidFragment")
public class MineProjectFragment extends BaseFragment<MineProjectPresenter> implements MineProjectView,IObserver {

    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private TwinklingRefreshLayout twinklingRefreshLayout;
    private AllProjectCollListAdapter adapter;
    private MineProjectPresenter  mPresenter;
    List<TokenAddBean>  listlocal = new ArrayList<TokenAddBean>();;
    private TokenAddBean mpCollBean;
    public static int REQUESCODE1=401;
    public View footerView;

    private int choosePosition=-1;

    private int starID = 0;

    public MineProjectFragment () {

    }



    public MineProjectFragment(Context context) {
        this.context = context;
    }



    //    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        CollectObserverService.getInstance().registerObserver(this);
        view = LayoutInflater.from(context).inflate(R.layout.fm_mineproject, container,false);
        EventBus.getDefault().register(this);
        MyRefreshHeader headerView = new MyRefreshHeader(context);
        mPresenter=new MineProjectPresenter(this);
        twinklingRefreshLayout = view.findViewById(R.id.mineproject_refreshLayout);
        twinklingRefreshLayout.setAutoLoadMore(true);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);
        recyclerView = view.findViewById(R.id.mineproject_recyclerview);





        footerView = LayoutInflater.from(context).inflate(R.layout.view_nocontent, null);
        ((TextView)(footerView.findViewById(R.id.add_title))).setText(getString(R.string.addproject));
        footerView.findViewById(R.id.nocontent_repeat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });

        adapter = new AllProjectCollListAdapter(R.layout.item_project,listlocal);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.enableDragItem(itemTouchHelper, R.id.item_allproject_content, true);
        adapter.setOnItemDragListener(onItemDragListener);
        adapter.addFooterView(footerView);
        twinklingRefreshLayout.startRefresh();
        initOnclick();
        return view;

    }



    @Override
    protected void setupViews(LayoutInflater inflater) {







    }

    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveEventBus(PriceChangeBean messageEvent) {

        if(adapter!=null){
            for(int i=0;i<listlocal.size();i++){

                if(listlocal.get(i).getSymbol().equals(messageEvent.getSymbol())){


                    listlocal.get(i).setPrice(messageEvent.getPrice());

                    adapter.notifyItemChanged(i);
                }

            }
        }



    }

    @Override
    protected void initView() {

        CollectObserverService.getInstance().registerObserver(this);

    }

    @Override
    protected void lazyLoad() {



    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().register(this);
        CollectObserverService.getInstance().unRegisterObserver(this);
    }

    private void initOnclick() {
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getData();
            }
        });
        view.findViewById(R.id.mineproject_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SearchActivity.class));
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                switch (view.getId()) {
                    case R.id.more_opera:
                        choosePosition = position;
                        (new ChoseUnitDialog((Activity) context, ChoseUnitDialog.type_project)).show();
                        break;
                    case R.id.close:
                        adapter.getData().get(position).setShowReal_timeData(false);

                        adapter.notifyItemChanged(position);

                        break;

                    case R.id.more_info:

                        if(adapter.getData().get(position).isShowReal_timeData()){
                            adapter.getData().get(position).setShowReal_timeData(false);
                        }else
                            adapter.getData().get(position).setShowReal_timeData(true);

                        adapter.notifyItemChanged(position);


                        break;
                    case R.id.item_allproject_content:
                        startActivity(new Intent(context, ProjectDetailActivity.class)
                                .putExtra("id", adapter.getData().get(position).getSymbol_id())
                                .putExtra("symbol", adapter.getData().get(position).getSymbol())
                                .putExtra("logo", adapter.getData().get(position).getLogo()));
                        break;
                }

            }
        });
    }

    public void getData() {
//        if(adapter!=null){
//            adapter.getData().clear();
//            adapter.notifyDataSetChanged();
//        }
        StringBuffer coinSymbols = new StringBuffer();

        List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");

        listlocal.clear();

        for (int i = 0; i < tags.size(); i++) {

            TokenAddBean tokenAddBean =new TokenAddBean();

            tokenAddBean.setActiveAccounts("");

            tokenAddBean.setLogo(tags.get(i).getLogo());

            tokenAddBean.setNewAccounts("");

            tokenAddBean.setPrice("");

            tokenAddBean.setTradeCount("");

            tokenAddBean.setSymbol(tags.get(i).getTag());

            listlocal.add(tokenAddBean);



            if(i==tags.size()-1){
                coinSymbols.append(tags.get(i).getAddressid());
            }else
               coinSymbols.append(tags.get(i).getAddressid()+",");

        }

//        if (listlocal.size() == 0) {
//
//            if (view.findViewById(R.id.nocontent_layout) == null) {
//
//                ((ViewStub) view.findViewById(R.id.viewstub)).inflate();
//                view.findViewById(R.id.nocontent_layout).setVisibility(View.VISIBLE);
//                view.findViewById(R.id.nocontent_repeat).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(context, SearchActivity.class));
//                    }
//                });
//                ((TextView)view.findViewById(R.id.add_title)).setText(getString(R.string.addproject));
//                ((TextView)view.findViewById(R.id.add_title)).setTextColor(getContext().getResources().getColor(R.color.title_color));
////                footerView.setVisibility(View.GONE);
//            } else {
//                view.findViewById(R.id.nocontent_layout).setVisibility(View.VISIBLE);
//            }
//
//        }
//        else {
//
//            if (view.findViewById(R.id.nocontent_layout) != null) {
//                view.findViewById(R.id.nocontent_layout).setVisibility(View.GONE);
////                footerView.setVisibility(View.VISIBLE);
//            }
//
//        }

        if(listlocal.size()!=0){
            adapter.setNewData(listlocal);
            adapter.notifyDataSetChanged();
            mPresenter.getMineProject(coinSymbols.toString());
        }else
            twinklingRefreshLayout.finishRefreshing();

    }

    @Override
    public void onMineProject(TokensAddedResp items) {

        twinklingRefreshLayout.finishRefreshing();

        //更新数据源

        if(TextUtils.isEmpty(items.getErrInfo())){

            for (int i = 0; i <listlocal.size(); i++) {

                if(items.getResult().getData()!=null){

                    for (int j = 0; j < items.getResult().getData().size(); j++) {

                        if(items.getResult().getData().get(j)!=null){

                            if(items.getResult().getData().get(j).getSymbol().equals(listlocal.get(i).getSymbol())){

                                listlocal.get(i).setLogo(items.getResult().getData().get(j).getLogo());

                                listlocal.get(i).setActiveAccounts(items.getResult().getData().get(j).getActiveAccounts());

                                listlocal.get(i).setNewAccounts(items.getResult().getData().get(j).getNewAccounts());

                                listlocal.get(i).setPrice(items.getResult().getData().get(j).getPrice());

                                listlocal.get(i).setPrice_cny(items.getResult().getData().get(j).getPrice_cny());

                                listlocal.get(i).setPrice_btc(items.getResult().getData().get(j).getPrice_cny());

                                listlocal.get(i).setTradeCount(items.getResult().getData().get(j).getTradeCount());

                                listlocal.get(i).setSymbol_id(items.getResult().getData().get(j).getId());

                                listlocal.get(i).setMarketValue(items.getResult().getData().get(j).getMarketValue());

                                listlocal.get(i).setMarketValue_cny(items.getResult().getData().get(j).getMarketValue_cny());

                                listlocal.get(i).setBlock(items.getResult().getData().get(j).getBlock());

                                listlocal.get(i).setQuateChange(items.getResult().getData().get(j).getQuateChange());

                                adapter.notifyItemChanged(i);

                            }

                        }

                    }

                }

            }



        }

    }

    @Override
    public void onMineAddress(AddressAddedResp resp) {

    }

    @Override
    public void onMineCount(AddressCountResp resp) {

    }

    @Override
    public void onAccountTrade(HashTradeResp resp) {

    }

    @Override
    public void onAssetData(AssetResp resp) {

    }


    //adapter 拖拽
    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            starID = pos;
            mpCollBean =  adapter.getData().get(pos);

        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            if (mpCollBean != null) {

                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(listlocal.get(pos).getSymbol());

                List<CollectBean> collectBeanList1=SQLiteUtils.getInstance().getEntityID(adapter.getData().get(pos).getSymbol());

                CollectBean collectBean = new CollectBean();

                collectBean.setTrandnum(starID);

                collectBean.setName(mpCollBean.getSymbol());

                collectBean.setTag(mpCollBean.getSymbol());

                collectBean.setAddressid(mpCollBean.getSymbol_id());

                collectBean.setLogo(mpCollBean.getLogo());

                collectBean.setId(collectBeanList.get(0).getId());

                collectBean.setType("project");


                CollectBean collectBean1 = new CollectBean();
                collectBean1.setId(collectBeanList1.get(0).getId());
                collectBean1.setTrandnum(pos);
                collectBean1.setType("project");
                collectBean1.setAddressid(adapter.getData().get(pos).getSymbol_id());
                collectBean1.setTag(adapter.getData().get(pos).getSymbol());
                collectBean1.setName(adapter.getData().get(pos).getSymbol());
                collectBean1.setLogo(adapter.getData().get(pos).getLogo());



                adapter.getData().remove(mpCollBean);

                adapter.getData().add(pos, mpCollBean);

                adapter.notifyDataSetChanged();


                SQLiteUtils.getInstance().updateContacts(collectBean1);

                SQLiteUtils.getInstance().updateContacts(collectBean);

                mpCollBean = null;
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESCODE1){
            twinklingRefreshLayout.startRefresh();

        }
    }

    @Override
    public void onError() {
            twinklingRefreshLayout.finishRefreshing();

    }

    @Override
    public void onChange(Object o, int actionCode, int requestCode) {

        if(requestCode== ConstObserver.COLLECT_PROJECT_CHANGE){
            //收藏成功或者取消，刷新数据源
            getData();
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        if(event.languageType==LanguageType.PROJECT_DELETE){


            List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(listlocal.get(choosePosition).getSymbol());
            CollectBean collectBean = new CollectBean();
            collectBean.setName(listlocal.get(choosePosition).getSymbol());
            collectBean.setType("project");
            collectBean.setId(collectBeanList.get(0).getId());
            SQLiteUtils.getInstance().deleteContacts(collectBean);

            adapter.remove(choosePosition);

            adapter.notifyDataSetChanged();

        }else if(event.languageType==LanguageType.PROJECT_TOP){

            if(choosePosition!=0){

                mpCollBean = adapter.getData().get(choosePosition);

                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(mpCollBean.getSymbol());

                List<CollectBean> collectBeanList1=SQLiteUtils.getInstance().getEntityID(listlocal.get(0).getSymbol());

                CollectBean collectBean = new CollectBean();
                collectBean.setId(collectBeanList.get(0).getId());
                collectBean.setTrandnum(0);
                collectBean.setType("project");
                collectBean.setAddressid(mpCollBean.getSymbol_id());
                collectBean.setName(mpCollBean.getSymbol());
                collectBean.setTag(mpCollBean.getSymbol());
                collectBean.setLogo(mpCollBean.getLogo());


                CollectBean collectBean1 = new CollectBean();
                collectBean1.setId(collectBeanList1.get(0).getId());
                collectBean1.setTrandnum(choosePosition);
                collectBean1.setType("project");
                collectBean1.setAddressid(listlocal.get(0).getSymbol_id());
                collectBean1.setTag(listlocal.get(0).getSymbol());
                collectBean1.setName(listlocal.get(0).getSymbol());
                collectBean1.setLogo(listlocal.get(0).getLogo());


                adapter.getData().remove(mpCollBean);
                adapter.getData().add(0, mpCollBean);
                adapter.notifyDataSetChanged();

                SQLiteUtils.getInstance().updateContacts(collectBean);

                SQLiteUtils.getInstance().updateContacts(collectBean1);

                mpCollBean = null;
            }

        }else if(event.languageType==LanguageType.UNIT_USD||event.languageType==LanguageType.UNIT_CNY){
            twinklingRefreshLayout.startRefresh();
            adapter.notifyDataSetChanged();
        }

    }
}
