package com.gikee.app.myinterface;

import com.gikee.app.views.RiseNumberTextView;

public interface IRaiseNumber {

    void start();
    void setFloat(String type,float fromNum, float toNum);
    void setInteger(String type,int fromNum, int toNum);
    void setDuration(long duration);
    void setOnEndListener(RiseNumberTextView.EndListener callback);
}
