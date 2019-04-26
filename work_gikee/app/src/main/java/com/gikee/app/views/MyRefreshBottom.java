package com.gikee.app.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gikee.app.R;
import com.lcodecore.tkrefreshlayout.IBottomView;

public class MyRefreshBottom extends FrameLayout implements IBottomView {
    private View rootView;

    private ImageView loadingView;

    private Context mcontext;

    public MyRefreshBottom(@NonNull Context context) {
        super(context);
        this.mcontext = context;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (rootView == null) {
            rootView = View.inflate(getContext(), R.layout.view_sinaheader, null);
//            refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
//            refreshTextView = (TextView) rootView.findViewById(R.id.tv);
            loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
            addView(rootView);
        }

    }



    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

        if (fraction < 1f) {

            if(loadingView!=null){

                loadingView.setVisibility(GONE);

            }

        }


    }

    @Override
    public void onFinish() {

    }


    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

        if(loadingView!=null){
            Glide.with(mcontext)
                    .load(R.drawable.loading_02)
                    .into(loadingView);

            loadingView.setVisibility(VISIBLE);
        }

    }



    @Override
    public void reset() {

    }
}
