package com.gikee.app.Observer.base_observe;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2015/10/10.
 */
public abstract class Observerable<T> {
    private List<IObserver<T>> observerList = new CopyOnWriteArrayList<>();
    private Object o = new Object();
    private T info;
    private int requestCode;

    public Observerable() {
    }

    public void setData(T info){
        this.info = info;
    }

    public void setRequestCode(int requestCode){
        this.requestCode = requestCode;
    }

    public  void registerObserver(IObserver<T> observer){
            observerList.add(observer);

    }

    public  void unRegisterObserver(IObserver<T> observer){
            observerList.remove(observer);

    }

    public void unRegisterAll(){
        observerList.clear();
    }

    protected  void notifyDataChanged(int actionCode){
            Iterator<IObserver<T>> it = observerList.iterator();
            while (it.hasNext()){
                IObserver<T> observer = it.next();
                if (null != observer){
                    observer.onChange(info,actionCode,requestCode);
                } else {
                it.remove();
                }
            }
    }

    public abstract void notifyDataChanged();

}
