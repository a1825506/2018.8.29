package com.gikee.app.receiver;

import android.content.Context;
import android.content.Intent;

import com.gikee.app.activity.HomePageActivity;
import com.gikee.app.datas.Commons;

public class NotificationBarEvent {

    //接受推送下来的通知，消息到达时触发
    public void receivingNotification(Context context, String message1) {
        String message = message1; //获取等到通知栏消息

       // HomePageActivity.mainActivity_instance.GetStoreUnreadMessages(); //获取商家未读消息

    }
    //通知栏点击监听事件，用户点击时触发
    public void openNotification(Context context, String description) {

        String alert = description;

        //根据消息内容跳转到指定页面
        Intent i = new Intent(context, HomePageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("message1", Commons.Remind);
        context.startActivity(i);
    }
}
