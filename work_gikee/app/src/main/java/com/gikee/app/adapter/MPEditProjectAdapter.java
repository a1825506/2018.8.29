package com.gikee.app.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.greendao.TrandBean;
import com.gikee.app.language.MultiLanguageUtil;

import java.util.List;
import java.util.Locale;

/**
 * Created by THINKPAD on 2018/7/25.
 */

public class MPEditProjectAdapter extends BaseItemDraggableAdapter<TrandBean, BaseViewHolder> {

    public MPEditProjectAdapter(int layoutResId, List<TrandBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TrandBean item) {


//        if (!item.getIscollect())
//            helper.setImageResource(R.id.item_mp_editproject_coll, R.mipmap.collect);
//        else
//            helper.setImageResource(R.id.item_mp_editproject_coll, R.mipmap.collected);

        if (item.getIscollect())
            helper.setTextColor(R.id.item_mp_editproject_coll, mContext.getResources().getColor(R.color.title_color));
        else
            helper.setTextColor(R.id.item_mp_editproject_coll, mContext.getResources().getColor(R.color.DFDFE4));

        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType.equals(Locale.SIMPLIFIED_CHINESE)) {
            helper.setText(R.id.item_mp_editproject_name, item.getTrandname()).addOnClickListener(R.id.item_mp_editproject_coll).addOnClickListener(R.id.item_mp_editproject_top);
        } else {
            helper.setText(R.id.item_mp_editproject_name, item.getTrandname_en()).addOnClickListener(R.id.item_mp_editproject_coll).addOnClickListener(R.id.item_mp_editproject_top);
        }

    }
}
