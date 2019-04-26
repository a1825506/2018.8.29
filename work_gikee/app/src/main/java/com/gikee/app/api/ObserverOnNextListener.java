package com.gikee.app.api;

/**
 * @author tgh
 * @date 18-8-7
 * @time 上午10:33
 * @describe TODO
 * @email a18255064049@163.com
 */
public interface ObserverOnNextListener<T> {

    void onNext(T t);

    void onError();
}
