package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gikee.app.R;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.beans.SpecialAccountBean;
import com.gikee.app.fragment.AccountStatisticsFragment;
import com.gikee.app.fragment.AccountTradeListFragment;
import com.gikee.app.fragment.BaseFragment;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.presenter.project.MineProjectPresenter;
import com.gikee.app.presenter.project.MineProjectView;
import com.gikee.app.resp.AddressAddedResp;
import com.gikee.app.resp.AddressCountResp;
import com.gikee.app.resp.AssetResp;
import com.gikee.app.resp.HashTradeResp;
import com.gikee.app.resp.TokensAddedResp;
import com.gikee.app.views.BaseFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * 地址详情页面。包含流入流出统计
 *
 */

public class AccountDetailActivity extends BaseActivity<MineProjectPresenter> implements MineProjectView {

    @Bind(R.id.tabslayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;


    private String balance,symbol,type;
    private String[] titles=new String[1];
    private List<BaseFragment> fragments = new ArrayList<>();
    private int[] images={R.mipmap.trend,R.mipmap.proportion};
    private int[] images_gray={R.mipmap.trend_gray,R.mipmap.proportion_gray};
    private  List<CollectBean> collectBeanList;
    private  List<SpecialAccountBean> mSpecialAccountBean = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetail);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        titles[0]=getString(R.string.statistics);
//        titles[1]=getString(R.string.ethtraderecord);

        hideRightCollect(View.GONE);

        type =  getIntent().getStringExtra("type");

        balance =  getIntent().getStringExtra("balance");

        symbol =  getIntent().getStringExtra("symbol");

        //collectBeanList长度始终为1.
        collectBeanList = MineAddressActivity.getCollectBeanList();

        setTitle(getString(R.string.remind_datail));

        setTitleColor(R.color.black);


        String[] list_address  = collectBeanList.get(0).getAddress_list().split(",");

        String logo = collectBeanList.get(0).getLogo();

        boolean isCollect =  collectBeanList.get(0).getIscollect();


        for(String address:list_address){


            SpecialAccountBean specialAccountBean = new SpecialAccountBean();

            specialAccountBean.setLogo(logo);

            specialAccountBean.setAddress_item(address);

            specialAccountBean.setChoose(isCollect);

            mSpecialAccountBean.add(specialAccountBean);
        }








        fragments.add(AccountStatisticsFragment.getInstance(mSpecialAccountBean,balance,symbol,type));

//        fragments.add(AccountTradeListFragment.getInstance(mSpecialAccountBean));


        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            protected Fragment getItemFragment(int position) {
                return fragments.get(position);
            }

            @Override
            protected String[] getMTitles() {
                return  titles;
            }

            @Override
            protected List<String> getAutoMTitles() {
                return null;
            }
        };


        viewPager.setAdapter(baseFragmentPagerAdapter);

        viewPager.setOffscreenPageLimit(1);

        viewPager.setCurrentItem(0);

        //将tablelayout与viewpager一起绑定
        tabLayout.setupWithViewPager(viewPager);


//        for(int i=0;i<titles.length;i++){
//
//            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
//            View view = LayoutInflater.from(AccountDetailActivity.this).inflate(R.layout.tab_bottom_item, null);
//            {
//                TextView textView = view.findViewById(R.id.tab_tx);
//
//                ImageView imageView = view.findViewById(R.id.tab_img);
//
//                imageView.setVisibility(View.VISIBLE);
//
//                textView.setText(titles[i]);
//
//                if(i==0){
//                    imageView.setBackground(getResources().getDrawable(images[i]));
//                    textView.setTextColor(getResources().getColor(R.color.title_color));
//                }else{
//                    imageView.setBackground(getResources().getDrawable(images_gray[i]));
//                    textView.setTextColor(getResources().getColor(R.color.gray_33));
//                }
//
//
//
//            }
//
//            tab.setCustomView(view);//给每一个tab设置view
//
//        }

    }

    @Override
    protected void initOnclick() {


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                View view =  tab.getCustomView();

                // View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                {

                    ImageView imageView = view.findViewById(R.id.tab_img);

                    TextView textView = view.findViewById(R.id.tab_tx);

                    textView.setTextColor(getResources().getColor(R.color.title_color));

                    imageView.setVisibility(View.VISIBLE);

                    imageView.setBackground(getResources().getDrawable(images[tab.getPosition()]));

                }

                tab.setCustomView(view);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                View view = tab.getCustomView();

                ImageView imageView = view.findViewById(R.id.tab_img);

                TextView textView = view.findViewById(R.id.tab_tx);

                textView.setTextColor(getResources().getColor(R.color.gray_33));

                imageView.setVisibility(View.VISIBLE);

                imageView.setBackground(getResources().getDrawable(images_gray[tab.getPosition()]));

                tab.setCustomView(view);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onMineProject(TokensAddedResp allProjectCollBean) {

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

    @Override
    public void onError() {

    }
}
