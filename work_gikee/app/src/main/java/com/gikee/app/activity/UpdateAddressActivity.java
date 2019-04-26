package com.gikee.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.gikee.app.Observer.CollectObserverService;
import com.gikee.app.Observer.base_observe.ConstObserver;
import com.gikee.app.R;
import com.gikee.app.base.BaseActivity;
import com.gikee.app.greendao.CollectBean;
import com.gikee.app.greendao.SQLiteUtils;

import java.util.List;

import butterknife.Bind;

public class UpdateAddressActivity extends BaseActivity{

    @Bind(R.id.update_addressname)
    EditText addressname;
    private  String newname;
    private String id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressupdate);
    }

    @Override
    protected void onChangeEvent(int type) {

    }


    @Override
    protected void setUpView() {

        showTitleBar();

        setTitle(getString(R.string.update_name));

        showRightTitle("",new IOnclik() {
            @Override
            public void OnClickSave() {

                String udatename=addressname.getText().toString();


                List<CollectBean> collectBeanList= SQLiteUtils.getInstance().getEntityID(newname);

                CollectBean collectBean = new CollectBean();
                collectBean.setName(udatename);
                collectBean.setAddressid(id);
                collectBean.setId(collectBeanList.get(0).getId());
                collectBean.setType("address");
                collectBean.setTag(udatename);
                SQLiteUtils.getInstance().updateContacts(collectBean);
                CollectObserverService.getInstance().notifyDataChanged(ConstObserver.COLLECT_ADDRESS_CHANGE);
                finish();
            }

            @Override
            public void OnImgClick(View view) {

            }

            @Override
            public void OnImgCollect() {

            }
        });

         newname = getIntent().getStringExtra("addressname");

         id = getIntent().getStringExtra("addressid");

        addressname.setText(newname);

    }

    @Override
    protected void initOnclick() {

    }
}
