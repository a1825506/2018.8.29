package com.gikee.app.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.gikee.app.R;

public class InfoDialog  extends Dialog {

    private TextView info;

    private Context mcontext;

    private String message;


    public InfoDialog(Context context,int themeResId,String message) {
        super(context,themeResId);

        this.mcontext = context;
        this.message = message;
    }

    public InfoDialog setTitle(){

        return this;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {

        info = findViewById(R.id.info);

        info.setText(message);

    }
}
