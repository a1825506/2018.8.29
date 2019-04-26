package com.gikee.app.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gikee.app.R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

public class MyRefreshHeader extends FrameLayout implements IHeaderView {

    private View rootView;

    private ImageView loadingView;

    private Context mcontext;

    public MyRefreshHeader(@NonNull Context context) {
        super(context);
        this.mcontext = context;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (rootView == null) {
            rootView = View.inflate(getContext(), R.layout.view_sinaheader, null);
            //refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
            //refreshTextView = (TextView) rootView.findViewById(R.id.tv);
            loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
            addView(rootView);
        }

    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

        //refreshArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);


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
    public void startAnim(float maxHeadHeight, float headHeight) {

        if(loadingView!=null){
            Glide.with(mcontext)
                    .load(R.drawable.loading_02)
                    .into(loadingView);

            loadingView.setVisibility(VISIBLE);
        }

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

    }
}
