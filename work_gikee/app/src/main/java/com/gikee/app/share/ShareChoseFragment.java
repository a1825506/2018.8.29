package com.gikee.app.share;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.views.dialogs.AbsDialogFragment;


/**
 * Created by yh on 15/11/12.
 */
public class ShareChoseFragment extends AbsDialogFragment {
    private IShareListener mListener;


    public static ShareChoseFragment instance() {
        return new ShareChoseFragment();
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        final Window window = getDialog().getWindow();
        View v = inflater.inflate(R.layout.fragment_share_weixin, ((ViewGroup) window.findViewById(android.R.id.content)),
                false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(-1, -2);
        LinearLayout wxTalkIv = (LinearLayout) v.findViewById(R.id.wx_talk_tv);
        LinearLayout wxFriendIv = (LinearLayout) v.findViewById(R.id.wx_friend_tv);
        LinearLayout qqTalkIv = (LinearLayout) v.findViewById(R.id.qq_talk_tv);
        LinearLayout weiBoIv = (LinearLayout) v.findViewById(R.id.weibo_tv);
        TextView share_cancle_btn = (TextView)v.findViewById(R.id.share_cancle_btn);

        weiBoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.IOnweiBo();
            }
        });

        qqTalkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.IQQTalkListener();
            }
        });

        wxTalkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.ITalkListener();
            }
        });

        wxFriendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.IFriendListener();
            }
        });

        share_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mListener.Cancel();
                dismiss();
            }
        });

        return v;
    }

    public void setIShareListener(IShareListener mListener) {
        this.mListener = mListener;
    }



    public interface IShareListener {

        void ITalkListener();

        void IFriendListener();

        void IQQTalkListener();

        void IOnweiBo();

        void Cancel();


    }
}
