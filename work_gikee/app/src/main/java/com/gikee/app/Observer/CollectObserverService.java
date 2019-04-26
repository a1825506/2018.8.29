package com.gikee.app.Observer;


import com.gikee.app.Observer.base_observe.IObserver;

public class CollectObserverService {
    private static CollectObserverService mService;
    private CollectObserverNode mObserverNode;

    public static CollectObserverService getInstance() {
        if (null == mService) {
            synchronized (CollectObserverService.class) {
                if (null == mService) {
                    mService = new CollectObserverService();
                }
            }
        }
        return mService;
    }

    private CollectObserverService() {
        initService();
    }

    private void initService() {
        mObserverNode = new CollectObserverNode();
    }

    public void registerObserver(IObserver observer) {
        mObserverNode.registerObserver(observer);
    }

    public void unRegisterObserver(IObserver observer) {
        mObserverNode.unRegisterObserver(observer);
    }

    public void setData(String data) {
        mObserverNode.setData(data);
    }


    public void notifyDataChanged(int requestCode) {
        mObserverNode.setRequestCode(requestCode);
        mObserverNode.notifyDataChanged();
    }

}
