package com.gikee.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.datas.Commons;
import com.gikee.app.views.MyTextView;

import butterknife.Bind;

public class RemindDetailActivty extends BaseActivity{

    @Bind(R.id.title)
    MyTextView title_tv;
    @Bind(R.id.title_en)
    MyTextView title_en_tv;
    @Bind(R.id.remind_form)
    TextView from_tv;
    @Bind(R.id.context)
    MyTextView context_tv;
    @Bind(R.id.context_en)
    MyTextView context_en_tv;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.jump_to)
    RelativeLayout jump_to;
    @Bind(R.id.adds_from)
    TextView adds_from;
    @Bind(R.id.adds_to)
    TextView adds_to;
    @Bind(R.id.trade_hash)
    TextView trade_hash;

    private String title;

    private String title_en;

    private String from;

    private String context;

    private String context_en;

    private String str_time;

    private String type;

    private String addr_from;

    private String addr_to;

    private String tradehash;

    private String language;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminddetail);

    }

    @Override
    protected void onChangeEvent(int type) {

    }

    @Override
    protected void setUpView() {

        language = MyUtils.getLocalLanguage();

        setTitle(getString(R.string.remind_datail));

        showRightCollect();

        setTitleColor(R.color.gray_33);

        title =getIntent().getStringExtra("title");

        title_en=getIntent().getStringExtra("title_en");

        from = getIntent().getStringExtra("from");

        context = getIntent().getStringExtra("context");

        context_en= getIntent().getStringExtra("context_en");

        str_time = getIntent().getStringExtra("time");

        if(!TextUtils.isEmpty(title)){

            if(language.equals("en")){
                title_tv.setText(title_en);
            }else
                title_tv.setText(title);

        }

//        if(!TextUtils.isEmpty(title_en)){
//
//            title_en_tv.setText(title_en);
//
//        }

           // title_tv.setText(remindBean.getTitle());

        if(!TextUtils.isEmpty(title)){

            from_tv.setText(from);

        }
           // from_tv.setText(remindBean.getFrom());

        if(!TextUtils.isEmpty(context)){

            if(language.equals("en")){
                context_tv.setText(context_en);
            }else
                context_tv.setText(context);

        }


//        if(!TextUtils.isEmpty(context_en)){
//
//            context_en_tv.setText(context_en);
//
//        }

        if(!TextUtils.isEmpty(str_time)){

            time.setText(str_time);

        }

        if(!TextUtils.isEmpty(title)){

            if(title.contains("出现链上大额转账")){

                jump_to.setVisibility(View.VISIBLE);

                if(!TextUtils.isEmpty(context)){

                    addr_from= context.substring(context.indexOf("从")+1,context.indexOf("转"));

                    addr_to= context.substring(context.indexOf("至")+1,context.indexOf("交")-1);

                    tradehash= context.substring(context.indexOf("hash")+5,context.indexOf("详")-1);

                }

                jump_to.setVisibility(View.VISIBLE);

                adds_from.setText(addr_from);

                adds_to.setText(addr_to);

                trade_hash.setText(tradehash);

            }

        }



           // context_tv.setText(remindBean.getContext());

    }

    @Override
    protected void initOnclick() {



        adds_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=null;

                if("btc".equals(title.substring(0,title.indexOf("出")))||(title.substring(0,title.indexOf("出")).toUpperCase().contains("USDT"))){

                      intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

                    intent.putExtra("type","BTC");

                }else if("eos".equals(title.substring(0,title.indexOf("出")))){
                    intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

                    intent.putExtra("type","EOS");
                }else {
                    intent = new Intent(getApplicationContext(), ETHAddressDetailActivity.class);

                    intent.putExtra("type","ETH");
                }


                intent.putExtra("paramstype","address");

                intent.putExtra("address",addr_from);

                startActivity(intent);

            }
        });

        adds_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=null;

                if("btc".equals(title.substring(0,title.indexOf("出")))||(title.substring(0,title.indexOf("出")).toUpperCase().contains("USDT"))){

                    intent = new Intent(getApplicationContext(), BTCAddressDetailActivity.class);

                    intent.putExtra("type","BTC");

                }else if("eos".equals(title.substring(0,title.indexOf("出")))){
                    intent = new Intent(getApplicationContext(), AddressDetailActivity.class);

                    intent.putExtra("type","EOS");
                }else {
                    intent = new Intent(getApplicationContext(), ETHAddressDetailActivity.class);

                    intent.putExtra("type","ETH");
                }


                intent.putExtra("paramstype","address");

                intent.putExtra("address",addr_to);

                startActivity(intent);

            }
        });

        trade_hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=null;



                if("btc".equals(title.substring(0,title.indexOf("出")))||(title.substring(0,title.indexOf("出")).toUpperCase().contains("USDT"))){

                    intent = new Intent(getApplicationContext(), BTCTradeDetailActivity.class);


                    intent.putExtra("address",tradehash);
                    intent.putExtra("type","BTC");

                }else if("eos".equals(title.substring(0,title.indexOf("出")))){
                    intent = new Intent(getApplicationContext(), TradeDetailActivity.class);
                    intent.putExtra("hash",tradehash);
                    intent.putExtra("type","EOS");
                }else {

                    intent = new Intent(getApplicationContext(), TradeDetailActivity.class);
                    intent.putExtra("type","ETH");
                    intent.putExtra("hash",tradehash);
                }


                intent.putExtra("paramstype","hash");



                startActivity(intent);

            }
        });

    }
}
