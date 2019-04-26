package com.gikee.app.views.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.gikee.app.adapter.AccountListAdapter;
import com.gikee.app.R;

import com.gikee.app.adapter.TokenListAdapter;
import com.gikee.app.beans.SpecialAccountBean;

import java.util.List;

public class AccountChooseDialog extends AbsDialogFragment {

    private RecyclerView recyclerView;

    private static List<SpecialAccountBean> mSpecialAccountBean;

    private AccountListAdapter AccountListAdapter;


    public static AccountChooseDialog instance(List<SpecialAccountBean> SpecialAccountBean) {

        mSpecialAccountBean = SpecialAccountBean;

        return new AccountChooseDialog();
    }



    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setGravity(Gravity.TOP);
        int h =(int)(dm.heightPixels*0.6);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, h);
    }


    @Override
    protected View initView(LayoutInflater inflater) {
        final Window window = getDialog().getWindow();
        View v = inflater.inflate(R.layout.dialog_accountchoose, ((ViewGroup) window.findViewById(android.R.id.content)),
                false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(-1, -2);

        recyclerView= v.findViewById(R.id.assetlist_recycle);

        AccountListAdapter = new AccountListAdapter();

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        recyclerView.addItemDecoration(divider);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(AccountListAdapter);

        AccountListAdapter.setNewData(mSpecialAccountBean);

        return v;
    }
}
