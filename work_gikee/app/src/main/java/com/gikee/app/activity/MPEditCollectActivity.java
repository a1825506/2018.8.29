package com.gikee.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.adapter.MPEditCollectAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.fragment.MineProjectFragment;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;

import java.util.ArrayList;
import java.util.List;

public class MPEditCollectActivity extends BaseActivity {
    private int starID = 0;
    private RecyclerView recyclerView;
    private MPEditCollectAdapter adapter;
    private int chooseType=0;
    public  String projectList="projectList";//项目排序列表；
    private CollectBean mpCollBean;
    private List<CollectBean> trandlist = new ArrayList<>();
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mp_editproject);
    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {
        showTitleBar();
        hideRightCollect(View.GONE);
        setTitleColor(R.color.black);
        chooseType = getIntent().getIntExtra("chooseType", -1);
       if(chooseType==1){
           setTitle(getResources().getString(R.string.mp_edit_title));
          //  ((TextView) findViewById(R.id.toolbar_text_right)).setText(getResources().getString(R.string.regist_sure));
            ((TextView) findViewById(R.id.edit_title)).setText(getResources().getString(R.string.mp_edit_pname));
             //trandlist = PreferenceUtil.getDataList(projectList);
             trandlist = SQLiteUtils.getInstance().selectAllContacts("project");




        }else if(chooseType==2){
           setTitle(getResources().getString(R.string.ma_edit_title));
           // ((TextView) findViewById(R.id.toolbar_text_title)).setText(R.string.ma_edit_title);
          //  ((TextView) findViewById(R.id.toolbar_text_right)).setText(getResources().getString(R.string.regist_sure));
            ((TextView) findViewById(R.id.edit_title)).setText(getResources().getString(R.string.ma_edit_pname));

             trandlist = SQLiteUtils.getInstance().selectAllContacts("address");


        }


        showRightTitle(getString(R.string.regist_sure), new IOnclik() {
            @Override
            public void OnClickSave() {

                Intent intent = new Intent();
                if(chooseType==1){

                    setResult(MineProjectFragment.REQUESCODE1, intent);

                }else if(chooseType==2){
                    finish();
                    // setResult(MineAddressFragment.REQUESCODE2, intent);
                }

                finish(); //结束当前的activity的生命周期

            }

            @Override
            public void OnImgClick(View view) {

            }

            @Override
            public void OnImgCollect() {

            }
        });


      //  findViewById(R.id.toolbar_text_layout).setBackgroundColor(Color.parseColor("#39384c"));

        recyclerView = findViewById(R.id.mp_editproject_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MPEditCollectAdapter(R.layout.item_mp_editproject, trandlist);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.enableDragItem(itemTouchHelper, R.id.item_mp_editproject_sort, true);
        adapter.setOnItemDragListener(onItemDragListener);
    }


    @Override
    protected void initOnclick() {
//        findViewById(R.id.toolbar_text_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        findViewById(R.id.toolbar_text_right).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent();
//                 if(chooseType==1){
//
//                    setResult(MineProjectFragment.REQUESCODE1, intent);
//
//                }else if(chooseType==2){
//                    finish();
//                   // setResult(MineAddressFragment.REQUESCODE2, intent);
//                }
//
//                finish(); //结束当前的activity的生命周期
//            }
//        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_mp_editproject_coll:
                        List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");
                        if(chooseType==1){

                            if(adapter.getData().get(position).getIscollect()){
                                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getEntityID(adapter.getData().get(position).getName());
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(adapter.getData().get(position).getName());
                                collectBean.setType("project");
                                collectBean.setId(collectBeanList.get(0).getId());
                                collectBean.setTag(adapter.getData().get(position).getName());
                                SQLiteUtils.getInstance().deleteContacts(collectBean);
                                ToastUtils.show(getString(R.string.collect_cannel));
                                adapter.getData().get(position).setIscollect(false);
                                //trandlist.remove(position);

                            }else{
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(adapter.getData().get(position).getName());
                                collectBean.setLogo(adapter.getData().get(position).getLogo());
                                collectBean.setTrandnum(tags.size()+1);
                                collectBean.setType("project");
                                collectBean.setTag(adapter.getData().get(position).getName());
                                SQLiteUtils.getInstance().addContacts(collectBean);
                                adapter.getData().get(position).setIscollect(true);
                                ToastUtils.show(getString(R.string.collect_success));



                            }
                           // PreferenceUtil.setDataList(projectList,trandlist);
                            adapter.notifyDataSetChanged();
                            CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_PROJECT_CHANGE);

                        }else if(chooseType==2){

                            if(adapter.getData().get(position).getIscollect()){
                                List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(adapter.getData().get(position).getAddressid());
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(adapter.getData().get(position).getName());
                                collectBean.setType("address");
                                collectBean.setId(collectBeanList.get(0).getId());
                                collectBean.setTag(adapter.getData().get(position).getName());
                                SQLiteUtils.getInstance().deleteContacts(collectBean);
                                ToastUtils.show(getString(R.string.collect_cannel));
                                adapter.getData().get(position).setIscollect(false);
                                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                               // trandlist.remove(position);

                            }else{
                                CollectBean collectBean = new CollectBean();
                                collectBean.setName(adapter.getData().get(position).getName());
                                collectBean.setLogo(adapter.getData().get(position).getLogo());
                                collectBean.setType("address");
                                collectBean.setTag(adapter.getData().get(position).getName());
                                SQLiteUtils.getInstance().addContacts(collectBean);
                                adapter.getData().get(position).setIscollect(true);
                                ToastUtils.show(getString(R.string.collect_success));

                            }
                           // PreferenceUtil.setDataList(projectList,trandlist);
                            adapter.notifyItemChanged(position);
                            CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);

                        }

                        break;
                    case R.id.item_mp_editproject_top:
                        if(position!=0){
                            mpCollBean = adapter.getData().get(position);

                            CollectBean collectBean = new CollectBean();


                            collectBean.setIscollect(adapter.getData().get(0).getIscollect());

                            collectBean.setId(adapter.getData().get(0).getId());

                            collectBean.setTrandnum(position);

                            if(chooseType==1){
                                collectBean.setType("project");
                            }else
                                collectBean.setType("address");



                            collectBean.setName(adapter.getData().get(0).getName());

                            collectBean.setLogo(adapter.getData().get(starID).getLogo());

                            collectBean.setAddressid(adapter.getData().get(starID).getAddressid());


                            adapter.getData().remove(mpCollBean);
                            adapter.getData().add(0, mpCollBean);
                            adapter.notifyDataSetChanged();

                            mpCollBean.setTrandnum(0);


                            SQLiteUtils.getInstance().updateContacts(collectBean);

                            SQLiteUtils.getInstance().updateContacts(mpCollBean);

                            mpCollBean = null;
                        }

                        break;
                }
            }
        });
    }

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
                CollectBean collectBean = new CollectBean();

                collectBean.setTrandnum(starID);

                collectBean.setAddressid(adapter.getData().get(starID).getAddressid());

                collectBean.setName(adapter.getData().get(starID).getName());

                collectBean.setLogo(adapter.getData().get(starID).getLogo());

                collectBean.setId(adapter.getData().get(starID).getId());

                collectBean.setIscollect(adapter.getData().get(starID).getIscollect());

                if(chooseType==1){
                    collectBean.setType("project");
                }else
                    collectBean.setType("address");

                adapter.getData().remove(mpCollBean);

                adapter.getData().add(pos, mpCollBean);

                adapter.notifyDataSetChanged();

                mpCollBean.setTrandnum(pos);

                SQLiteUtils.getInstance().updateContacts(collectBean);

                SQLiteUtils.getInstance().updateContacts(mpCollBean);

                mpCollBean = null;
            }

        }
    };

    @Override
    protected void initData() {
        // super.initData();
    }
}
