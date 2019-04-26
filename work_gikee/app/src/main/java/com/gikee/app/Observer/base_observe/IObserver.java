package com.gikee.app.Observer.base_observe;

/**
 * Created by Administrator on 2015/10/10.
 */
public interface IObserver<T> {
    public void onChange(T t, int actionCode, int requestCode);
}
