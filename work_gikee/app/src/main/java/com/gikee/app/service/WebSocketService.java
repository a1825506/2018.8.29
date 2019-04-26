package com.gikee.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gikee.app.datas.Commons;
import com.gikee.app.presenter.project.RemindInfoPresenter;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WebSocketService extends Service {



    private String TAG="WebSocketAdapter";

    private WebSocket ws;

    private WsListener wsListener =new WsListener();




        @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null){

            initSocket();


        }



        Thread thread1 =   new Thread(new Runnable() {

            String str_send = "{\"data\":{\"address\":[{\"address\":\"0x3f5ce5fbfe3e9af3971dd833d26ba9b5c936f0be\"}],\"amount\":\"50801.278038005253164256\",\"coin_name\":\"ETH\",\"height\":\"7596758\",\"time_stamp\":\"1555629324\",\"to_address\":[{\"address\":\"0x4e9ce36e442e55ecd9025b9a6e0d88485d628a67\"}],\"tx_id\":\"0x3328943e90548172c45ff306a4db4e25ba466cea4b0d14cb8f0ff308fd152e0a\"},\"symbol\":\"ETH\"}";

            @Override
            public void run() {

                while (true){

                    if(ws!=null){
                        ws.sendText(str_send);
                    }



                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

//        thread1.start();


        return super.onStartCommand(intent, flags, startId);
    }







    protected void initSocket() {



        if(ws!=null){
            ws=null;
        }

        try {
            ws = new WebSocketFactory().createSocket(Commons.WEBSOCKET_URL, 300000) //ws地址，和设置超时时间
                    .setFrameQueueSize(5)//设置帧队列最大值为5
                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                    .addListener(wsListener)//添加回调监听
                    .connectAsynchronously();//异步连接

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    class WsListener extends WebSocketAdapter {
        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            Log.e(TAG,"接搜到"+text);

        }



        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers)
                throws Exception {
            super.onConnected(websocket, headers);
            Log.e(TAG,"连接成功");

        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception)
                throws Exception {
            super.onConnectError(websocket, exception);
            Log.e(TAG,"连接错误：" + exception.getMessage());
            Thread.sleep(2000);
            initSocket();
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer)
                throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            Log.e(TAG,"断开连接：" );
            Thread.sleep(2000);
            initSocket();
        }

    }

    public class ServiceBinder extends Binder {
        public WebSocketService getService() {
            return WebSocketService.this;
        }
    }
}
