package com.gikee.app.api;

import android.util.Log;

import com.gikee.app.Utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @author tgh
 * @date 18-8-7
 * @time 上午10:33
 * @describe TODO
 * @email a18255064049@163.com
 */
public class MyObserver<T> implements Observer<T> {
    private static final String TAG = "MyObserver";
    private ObserverOnNextListener listener;

    public MyObserver( ObserverOnNextListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
        //添加业务处理
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        String msg="";
        if(e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            msg = httpException.getMessage();
            int code =httpException.code();
            if(code ==504){
                msg = "网络不给力";
            }
            if(code ==502 ||code==404){
                msg = "服务器异常，请稍后再试";

            }
            if(code==500){
                msg = "请检查输入参数";
            }

            listener.onError();


        }

        if(e instanceof UnknownHostException){
            msg="请检查网络";
        }

        if (e instanceof SocketTimeoutException) {
            //判断超时异常
            msg="连接超时";
        }
        if (e instanceof ConnectException) {
            ////判断连接异常，
            msg="连接超时";
        }
        listener.onError();

        ToastUtils.showCenter(msg);





        //添加业务处理
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
        //添加业务处理
    }
}
