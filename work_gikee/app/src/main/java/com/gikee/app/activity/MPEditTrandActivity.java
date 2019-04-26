package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.adapter.MPEditProjectAdapter;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.datas.Commons;
import com.gikee.app.fragment.MineProjectFragment;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.greendao.TrandBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by THINKPAD on 2018/7/25.
 */

public class MPEditTrandActivity extends BaseActivity {

    private int starID = 0;
    private RecyclerView recyclerView;
    private MPEditProjectAdapter adapter;
    private int chooseType=0;
    public static String projectList="projectList";//项目排序列表；
    private TrandBean   mpCollBean;
    private List<TrandBean> trandBeanList = new ArrayList<>();

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

       // hideTitleBar();
        showTitleBar();
        hideRightCollect(View.GONE);
        setTitleColor(R.color.black);
        setTitle(getResources().getString(R.string.trend_name));
        showRightTitle(getString(R.string.regist_sure), new IOnclik() {
            @Override
            public void OnClickSave() {

                Intent intent = new Intent();

                if(chooseType==0){

                    setResult(ConstObserver.REQUESCODE, intent);

                }else if(chooseType==1){

                    setResult(MineProjectFragment.REQUESCODE1, intent);

                }else if(chooseType==2){
                    finish();
                    //setResult(MineAddressFragment.REQUESCODE2, intent);
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
        chooseType = getIntent().getIntExtra("chooseType", -1);
        trandBeanList=SQLiteUtils.getInstance().getTrand(Commons.smybl);
//        if(chooseType==0){
//            ((TextView) findViewById(R.id.toolbar_text_title)).setText(R.string.edit_choose);
//            ((TextView) findViewById(R.id.toolbar_text_right)).setText(getResources().getString(R.string.regist_sure));
          //  ((TextView) findViewById(R.id.edit_title)).setText(getResources().getString(R.string.trend_name));
//
//
//        }


      //  findViewById(R.id.toolbar_text_layout).setBackgroundColor(Color.parseColor("#39384c"));
        recyclerView = findViewById(R.id.mp_editproject_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_gray));
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new MPEditProjectAdapter(R.layout.item_mp_editproject, trandBeanList);
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
//
//                if(chooseType==0){
//
//                    setResult(ConstObserver.REQUESCODE, intent);
//
//                }else if(chooseType==1){
//
//                    setResult(MineProjectFragment.REQUESCODE1, intent);
//
//                }else if(chooseType==2){
//                    finish();
//                    //setResult(MineAddressFragment.REQUESCODE2, intent);
//                }
//
//
//
//                finish(); //结束当前的activity的生命周期
//            }
//        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_mp_editproject_coll:
                        if(chooseType==0){

                            TrandBean trandBean = new TrandBean();

                            if (adapter.getData().get(position).getIscollect()) {
                                //  取消收藏
                                adapter.getData().get(position).setIscollect(false);

                                trandBean.setIscollect(false);
                            } else {

                                adapter.getData().get(position).setIscollect(true);

                                trandBean.setIscollect(true);
                            }
                            //更新UI
                            adapter.notifyItemChanged(position);
                            //更新数据库
                            trandBean.setId(trandBeanList.get(position).getId());

                            trandBean.setSymbol(trandBeanList.get(position).getSymbol());

                            trandBean.setTrandname(trandBeanList.get(position).getTrandname());

                            trandBean.setTrandname_en(trandBeanList.get(position).getTrandname_en());

                            trandBean.setTrandid(trandBeanList.get(position).getTrandid());

                            SQLiteUtils.getInstance().updateTrand(trandBean);



                        }else if(chooseType==1){



                        }else if(chooseType==2){



                        }

                        break;
                    case R.id.item_mp_editproject_top:
                        if(position!=0){
                            mpCollBean = adapter.getData().get(position);

                            TrandBean trandBean = new TrandBean();

                            trandBean.setIscollect(adapter.getData().get(0).getIscollect());

                            trandBean.setId(adapter.getData().get(0).getId());

                            trandBean.setTrandnum(position);

                            trandBean.setTrandname(adapter.getData().get(0).getTrandname());

                            trandBean.setTrandname_en(adapter.getData().get(0).getTrandname_en());

                            trandBean.setTrandid(adapter.getData().get(0).getTrandid());

                            trandBean.setSymbol(adapter.getData().get(0).getSymbol());


                            adapter.getData().remove(mpCollBean);
                            adapter.getData().add(0, mpCollBean);
                            adapter.notifyDataSetChanged();

                            mpCollBean.setTrandnum(0);


                            SQLiteUtils.getInstance().updateTrand(trandBean);

                            SQLiteUtils.getInstance().updateTrand(mpCollBean);



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
                TrandBean trandBean = new TrandBean();

                trandBean.setIscollect(adapter.getData().get(starID).getIscollect());

                trandBean.setId(adapter.getData().get(starID).getId());

                trandBean.setTrandnum(starID);

                trandBean.setTrandname(adapter.getData().get(starID).getTrandname());

                trandBean.setTrandname_en(adapter.getData().get(starID).getTrandname_en());

                trandBean.setTrandid(adapter.getData().get(starID).getTrandid());

                trandBean.setSymbol(adapter.getData().get(starID).getSymbol());

                adapter.getData().remove(mpCollBean);

                adapter.getData().add(pos, mpCollBean);

                adapter.notifyDataSetChanged();

                mpCollBean.setTrandnum(pos);

                SQLiteUtils.getInstance().updateTrand(trandBean);

                SQLiteUtils.getInstance().updateTrand(mpCollBean);

                mpCollBean = null;


            }
        }
    };

    @Override
    protected void initData() {
       // super.initData();
    }
}
