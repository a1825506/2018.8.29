package com.gikee.app.views.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.Utils.ToastUtils;
import com.gikee.app.activity.SpecialAccountSearchActivity;
import com.gikee.app.adapter.SpinnerArrayAdapter;
import com.gikee.app.beans.CollectTrandBean;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;
import com.gikee.app.type.ShowType;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AddAccountDialog extends Dialog {

    private Context mcontext;

    private EditText input_nick_edit;

    private EditText input_account_edit;

    private TextView nick_title_text;

    private Button confirm_btn;

    private Spinner spinner2;

    private ArrayAdapter<String> mAdapter ;

    private String [] mStringArray;


    private String accout_type = ShowType.eth.getContent();

    private String address;

    private String addressname;

    private CollectBean mpCollBean;

    private boolean isEdit=false;

    public AddAccountDialog( Context context,int themeResId,CollectBean mpCollBean) {
        super(context,themeResId);
        this.mcontext = context;
        this.mpCollBean= mpCollBean;

    }

    public AddAccountDialog getInstance(){

        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addaccount);
        initView();
    }

    private void initView() {

        input_nick_edit = (EditText)findViewById(R.id.input_nick_edit);

        input_account_edit = (EditText)findViewById(R.id.input_account_edit);

        nick_title_text = (TextView) findViewById(R.id.nick_title_text);

        confirm_btn = (Button) findViewById(R.id.confirm_btn);

        spinner2 = (Spinner) findViewById(R.id.spinner2);

        mStringArray=mcontext.getResources().getStringArray(R.array.addaccount_type);
        //使用自定义的ArrayAdapter
        mAdapter = new SpinnerArrayAdapter(mcontext,mStringArray);

        onClick();

        spinner2.setAdapter(mAdapter);

        if(mpCollBean!=null){

            isEdit=true;

            address = mpCollBean.getAddressid();

            input_nick_edit.setText(mpCollBean.getName());

            nick_title_text.setText(mcontext.getString(R.string.edit_nickname));

            input_account_edit.setText(mpCollBean.getAddressid());

            confirm_btn.setText(mcontext.getString(R.string.confirm_edit));

            input_account_edit.setEnabled(false);

            if("eth".equals(mpCollBean.getAddress_type())){
                accout_type = ShowType.eth.getContent();
                spinner2.setSelection(0,true);


            }else if("btc".equals(mpCollBean.getAddress_type())){
                accout_type = ShowType.btc.getContent();
                spinner2.setSelection(1,true);
            }

        }



    }

    private void onClick() {


        spinner2.setOnItemSelectedListener(new ItemSelectedListenerImpl());


        input_account_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(input_account_edit.getText().toString().contains("....")){


                }else{
                    address = input_account_edit.getText().toString();

                    int length = address.length();

                    if(length>30){

                        input_account_edit.setText(input_account_edit.getText().toString().substring(0,20)+"...."+input_account_edit.getText().toString().substring(length-4,length));

                    }
                }

            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressname = input_nick_edit.getText().toString();

                if(TextUtils.isEmpty(addressname)){
                    ToastUtils.showCenter(mcontext.getString(R.string.input_nick));
                    return;
                }

                if(TextUtils.isEmpty(address)){
                    ToastUtils.showCenter(mcontext.getString(R.string.input_account));
                    return;
                }



                //收藏账户
                if (!SQLiteUtils.getInstance().isAddressCollect(address)) {
                    collectAddress();
                }else{
                    //更新记录
                    List<CollectBean> collectBeanList=SQLiteUtils.getInstance().getAddressID(address);
                    CollectBean collectBean = collectBeanList.get(0);
                    collectBean.setName(addressname);
                    if(accout_type.equals(collectBean.getAddress_type())){
                        updateAddress(collectBean);
                    }else{
                        if(isEdit){
                            if(accout_type.equals("btc")){
                                collectBean.setLogo("http://www.gikee.com/static/CoinLogo/bitcoin.png");
                            }else if(accout_type.equals("eth")){
                                collectBean.setLogo("http://www.gikee.com/static/CoinLogo/ethereum.png");
                            }
                            collectBean.setAddress_type(accout_type);
                            updateAddress(collectBean);
                        }else
                          collectAddress();

                    }

                      }
                MyUtils.hideKeyBoard((Activity) mcontext);
                dismiss();
            }
        });
    }

    private void updateAddress(CollectBean collectBean) {

        SQLiteUtils.getInstance().updateContacts(collectBean);
        CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);

    }

    private void collectAddress() {
        CollectBean collectBean = new CollectBean();
        collectBean.setName(addressname);
        collectBean.setAddressid(address);
        collectBean.setType("address");
        collectBean.setDetail(address);
        if(accout_type.equals("btc")){
            collectBean.setLogo("http://www.gikee.com/static/CoinLogo/bitcoin.png");
        }else if(accout_type.equals("eth")){
            collectBean.setLogo("http://www.gikee.com/static/CoinLogo/ethereum.png");
        }

        collectBean.setBalance(mcontext.getString(R.string.check_address_));
        collectBean.setAddress_type(accout_type);
        collectBean.setTag(address);
        collectBean.setIscollect(true);
        SQLiteUtils.getInstance().addContacts(collectBean);
        CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);

    }

    private class ItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position,long arg3) {

            if(position==0){
                accout_type = ShowType.eth.getContent();
            }else if(position==1){
                accout_type = ShowType.btc.getContent();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}

    }

}
