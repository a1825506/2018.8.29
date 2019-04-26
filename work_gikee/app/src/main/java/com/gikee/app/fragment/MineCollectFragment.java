package com.gikee.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gikee.app.R;
import com.gikee.app.activity.SearchActivity;
import com.gikee.app.views.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class  MineCollectFragment extends BaseFragment{

    private View view;
    private Context mcontext;
    private RadioGroup radioGroup;
    private RadioButton project_rd,address_rd;
    private ViewPager viewPager;
    private List<BaseFragment> collectlist = new ArrayList<>();


    public MineCollectFragment(Context context) {
        this.mcontext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = LayoutInflater.from(mcontext).inflate(R.layout.fm_minecollect, container,false);

        radioGroup  = view.findViewById(R.id.collect_rg);

        project_rd = view.findViewById(R.id.project);

        address_rd = view.findViewById(R.id.address);

        viewPager = view.findViewById(R.id.collect_vp);

        initView();

        initOnclick();

        return view;
    }

    private void initOnclick() {

        view.findViewById(R.id.mineproject_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(mcontext, SearchActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);

            }
        });


        project_rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
                project_rd.setBackgroundResource(R.drawable.sharp_btn_project);
                project_rd.setTextColor(getContext().getResources().getColor(R.color.white));
                address_rd.setBackgroundResource(R.drawable.sharp_btn_rightline);
                address_rd.setTextColor(getContext().getResources().getColor(R.color.gray_33));
            }
        });

        address_rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
                project_rd.setBackgroundResource(R.drawable.shape_btn_leftline);
                project_rd.setTextColor(getContext().getResources().getColor(R.color.gray_33));
                address_rd.setBackgroundResource(R.drawable.sharp_btn_address);
                address_rd.setTextColor(getContext().getResources().getColor(R.color.white));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//

            }

            @Override
            public void onPageSelected(int position) {

                if(position==0){

                    project_rd.setBackgroundResource(R.drawable.sharp_btn_project);
                    project_rd.setTextColor(getContext().getResources().getColor(R.color.white));
                    address_rd.setBackgroundResource(R.drawable.sharp_btn_rightline);
                    address_rd.setTextColor(getContext().getResources().getColor(R.color.gray_33));
                }else if(position==1){

                    project_rd.setBackgroundResource(R.drawable.shape_btn_leftline);
                    project_rd.setTextColor(getContext().getResources().getColor(R.color.gray_33));
                    address_rd.setBackgroundResource(R.drawable.sharp_btn_address);
                    address_rd.setTextColor(getContext().getResources().getColor(R.color.white));

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void setupViews(LayoutInflater inflater) {

    }


    protected void initView() {

        final String[] mTitle = {"项目","地址"};

        MineProjectFragment mineProjectFragment = new MineProjectFragment(getContext());

//        MineAddressFragment mineAddressFragment = new MineAddressFragment(getContext());

        collectlist.add(mineProjectFragment);

//        collectlist.add(mineAddressFragment);

        BaseFragmentPagerAdapter baseFragmentPagerAdapter = new BaseFragmentPagerAdapter(getFragmentManager()) {
            @Override
            protected Fragment getItemFragment(int position) {
                return collectlist.get(position);
            }

            @Override
            protected String[] getMTitles() {
                return  mTitle;
            }

            @Override
            protected List<String> getAutoMTitles() {
                return null;
            }
        };
        viewPager.setAdapter(baseFragmentPagerAdapter);



    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    public void getData() {


    }
}
