package com.gikee.app.presenter.base;

import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * @author tgh
 * @date 18-8-6
 * @time 上午11:39
 * @describe TODO
 * @email a18255064049@163.com
 */
public abstract class BasePresenter<V>{




    protected WeakReference<V> mViewRef; // view 的弱引用


    public void attachView(V view){
        mViewRef = new WeakReference<>(view);
    }

    public V getView() {
        return mViewRef != null ? mViewRef.get() : null;
    }

    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
            Log.i("BasePresenter","已经GC...");
        }
    }


    public void onDestroy(){

        detachView();
    }


}
