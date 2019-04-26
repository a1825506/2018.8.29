package com.gikee.app.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gikee.app.R;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.OnChangeLanguageEvent;
import com.gikee.app.presenter.base.BasePresenter;
import com.gikee.app.presenter.base.BaseView;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


/**
 * @author tgh
 * @date 18-8-6
 * @time 上午11:58
 * @describe TODO
 * @email a18255064049@163.com
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView{

    protected View mainView;
    protected RelativeLayout contentView;
    protected P mPresenter;
    protected ImmersionBar mImmersionBar;

    //Fragment的View加载完毕的标记
    protected boolean isViewCreated;

    //Fragment对用户可见的标记
    protected boolean isUIVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_base, container, false);

        EventBus.getDefault().register(this);

        isViewCreated=true;
        if (isImmersionBarEnabled())
            initImmersionBar();
        initBaseViews();
//        setStatesBar(getResources().getColor(R.color.widget_tab_bar_bg));
        setupViews(inflater);
        mPresenter = createPresenter();
        ButterKnife.bind(this,mainView);
        initView();
        lazyLoad();
        return mainView;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLanguageEvent(OnChangeLanguageEvent event){

        onChangeEvent(event.languageType);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    protected boolean isImmersionBarEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            return true;
        else
            return false;
    }


    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (checkDeviceHasNavigationBar(getContext())) {
            mImmersionBar.statusBarDarkFont(true).hideBar(BarHide.FLAG_SHOW_BAR).statusBarDarkFont(false).statusBarColorTransform(R.color.appcolors).navigationBarColor(R.color.transparent).init();
        } else {
            mImmersionBar.statusBarDarkFont(true).statusBarDarkFont(false).statusBarColorTransform(R.color.appcolors).init();
        }
    }


    public boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        if (hasSoftKeys())
            return true;
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasSoftKeys() {
        WindowManager windowManager = getActivity().getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }


    protected abstract void setupViews(LayoutInflater inflater);

    protected abstract void initView();

    protected  abstract void lazyLoad();


    protected abstract void onChangeEvent(int type);

    private void initBaseViews() {
        contentView = (RelativeLayout) mainView.findViewById(R.id.re_basecontent);
    }


    public void setContentView(LayoutInflater inflater, int layoutId) {
        contentView.removeAllViews();
        inflater.inflate(layoutId, contentView, true);
        }

    public void setContentView(View view) {
        contentView.removeAllViews();
        contentView.addView(view, new ViewGroup.LayoutParams(-1, -1));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;

        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected P createPresenter() {
        return null;
    }

    @Override
    public void showErrorMessage(String message) {
        ToastUtils.showCenter(message);
    }

}
