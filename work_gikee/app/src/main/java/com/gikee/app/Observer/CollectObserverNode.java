package com.gikee.app.Observer;


import com.gikee.app.Observer.base_observe.Observerable;
import com.gikee.app.Observer.base_observe.ConstObserver;

public class CollectObserverNode<T> extends Observerable<T> {

    @Override
    public void notifyDataChanged() {
        notifyDataChanged(ConstObserver.OBSERVER_ACTION_LOGIN_CODE);
    }
}
