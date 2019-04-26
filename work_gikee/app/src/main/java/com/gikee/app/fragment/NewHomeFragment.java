package com.gikee.app.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gikee.app.R;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.views.AutoHeightViewPager;
import com.gikee.app.views.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NewHomeFragment extends BaseFragment{

    @Bind(R.id.tabslayout_top)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    AutoHeightViewPager viewPager;

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.img_homelogo)
    ImageView img_homelogo;

    @Bind(R.id.search)
    LinearLayout root;

    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] titles=new String[5];

    @Override
    protected void setupViews(LayoutInflater inflater) {
        setContentView(inflater, R.layout.newfragment_home);
    }

    @Override
    protected void initView() {
        titles[0] = getString(R.string.optional);
        titles[1] = getString(R.string.leader);
        titles[2] = getString(R.string.global);
        titles[3] = getString(R.string.public_chain);
        titles[4] = getString(R.string.dAPP);

        fragments.add(new MineProjectFragment(getContext()));

        fragments.add(LeaderboardFragment.getInstance("all"));

        fragments.add(HomeFragment.getInstance());

        fragments.add(LeaderboardFragment.getInstance("baseChain"));

        fragments.add(LeaderboardFragment.getInstance("erc20"));

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager()) {
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
        viewPager.setScanScroll(false);

        viewPager.setAdapter(baseFragmentPagerAdapter);

        viewPager.setOffscreenPageLimit(5);

        List<CollectBean> tags = SQLiteUtils.getInstance().selectAllContacts("project");

        if(tags.size()==0){
            viewPager.setCurrentItem(1);
        }else
            viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);

        initOnclick();

    }

    private void initOnclick() {

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intent);

            }
        });

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("LeaderboardFragment",verticalOffset+"------>"+ img_homelogo.getMeasuredHeight());
                if(-verticalOffset>img_homelogo.getHeight()){
                    img_homelogo.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {

    }
}
