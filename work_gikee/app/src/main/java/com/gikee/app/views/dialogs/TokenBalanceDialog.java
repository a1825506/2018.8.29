package com.gikee.app.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
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
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.adapter.TokenListAdapter;
import com.gikee.app.beans.TokendBean;
import com.gikee.app.share.ShareChoseFragment;
import com.gikee.app.views.MyRefreshBottom;
import com.gikee.app.views.MyRefreshHeader;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.Bind;

public class TokenBalanceDialog extends AbsDialogFragment {


    RecyclerView recyclerView;
    TwinklingRefreshLayout twinklingRefreshLayout;

    TextView title_close,title_value;

    private  TokenListAdapter tokenBalanceAdapter;

    private static List<TokendBean> mtokendBeanList;

    private static int token_balance;



    public static TokenBalanceDialog instance(int strtoken_balance,List<TokendBean> tokendBeanList) {
        mtokendBeanList = tokendBeanList;
        token_balance = strtoken_balance;
        return new TokenBalanceDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        int h =(int)(dm.heightPixels*0.6);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, h);
    }

//    public TokenBalanceDialog(@NonNull Context context,List<TokendBean> tokendBeanList) {
//        super(context);
//        this.tokendBeanList = tokendBeanList;
//        initView();
//    }


    @Override
    protected View initView(LayoutInflater inflater) {

        final Window window = getDialog().getWindow();
        View v = inflater.inflate(R.layout.activity_ethassetlist, ((ViewGroup) window.findViewById(android.R.id.content)),
                false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(-1, -2);

        twinklingRefreshLayout = v.findViewById(R.id.address_recycle_layout);

        recyclerView= v.findViewById(R.id.assetlist_recycle);

        title_close = v.findViewById(R.id.title_close);

        title_value= v.findViewById(R.id.title_value);

        title_value.setText(token_balance+"");



        tokenBalanceAdapter = new TokenListAdapter();

        MyRefreshHeader headerView = new MyRefreshHeader(getContext());
        MyRefreshBottom bottomView = new MyRefreshBottom(getContext());

        twinklingRefreshLayout.setBottomView(bottomView);
        twinklingRefreshLayout.setAutoLoadMore(false);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setEnableLoadmore(false);


        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_gray));

        recyclerView.addItemDecoration(divider);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(tokenBalanceAdapter);

        tokenBalanceAdapter.setNewData(mtokendBeanList);


        title_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return v;
    }
}
