package com.gikee.app.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gikee.app.R;
import com.gikee.app.Utils.MyUtils;
import com.gikee.app.beans.HomeBaseLineBean;
import com.gikee.app.beans.LineBean;
import com.gikee.app.datas.Commons;
import com.gikee.app.views.MyLineChartEntity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseLineAdapter extends BaseQuickAdapter<HomeBaseLineBean,BaseViewHolder>{

    public BaseLineAdapter() {
        super(R.layout.item_baseline, null);
    }

    private  LineChart lineChart;


    private TextView textView;

    private String type="";


    @Override
    protected void convert(BaseViewHolder helper, HomeBaseLineBean item) {
//        if ("BCH".equals(item.getSymbol())) {
//            helper.setText(R.id.baseline_symbol,"BCH");
//            Glide.with(mContext).load(R.mipmap.bch_gray).into((CircleImageView) helper.getView(R.id.baseline_img));
//            ((TextView) (helper.getView(R.id.baseline_symbol))).setTextColor(mContext.getResources().getColor(R.color.cbcbcb));
//            ((TextView) (helper.getView(R.id.baseline_value))).setTextColor(mContext.getResources().getColor(R.color.cbcbcb));
//            ((TextView) (helper.getView(R.id.baseline_name))).setTextColor(mContext.getResources().getColor(R.color.cbcbcb));
//            helper.getView(R.id.base_img).setVisibility(View.VISIBLE);
//            helper.getView(R.id.baseline_linechat).setVisibility(View.GONE);
//            imageView = helper.getView(R.id.base_img);
//            RelativeLayout.LayoutParams lp1 = ( RelativeLayout.LayoutParams)imageView.getLayoutParams();
//            lp1.width = (int)(MyUtils.getWidth()/4);
//            imageView.setLayoutParams(lp1);
//        }else
            {

            if(!TextUtils.isEmpty(item.getSymbol())){

                helper.setText(R.id.baseline_symbol,item.getSymbol());
            }

            if(!TextUtils.isEmpty(item.getLogo())){
                Glide.with(mContext).load(item.getLogo()).into((CircleImageView) helper.getView(R.id.baseline_img));

            }else
                Glide.with(mContext).load(R.mipmap.moren).into((CircleImageView) helper.getView(R.id.baseline_img));

            helper.addOnClickListener(R.id.baseline_layout);
            ((TextView) (helper.getView(R.id.baseline_symbol))).setTextColor(mContext.getResources().getColor(R.color.black));
            ((TextView) (helper.getView(R.id.baseline_value))).setTextColor(mContext.getResources().getColor(R.color.black));
            ((TextView) (helper.getView(R.id.baseline_name))).setTextColor(mContext.getResources().getColor(R.color.black));
            helper.getView(R.id.base_img).setVisibility(View.GONE);
            helper.getView(R.id.baseline_linechat).setVisibility(View.VISIBLE);


            lineChart = helper.getView(R.id.baseline_linechat);
            RelativeLayout.LayoutParams lp1 = ( RelativeLayout.LayoutParams)lineChart.getLayoutParams();
            lp1.width = (int)(MyUtils.getWidth()/4);
            lineChart.setLayoutParams(lp1);
            initSingleLineChart(item.getLineData());
        }

        if(!TextUtils.isEmpty(item.getLineData().getCurrentValue())){

                helper.setText(R.id.baseline_value,MyUtils.fmtMicrometer(item.getLineData().getCurrentValue()));

        }


        if(!TextUtils.isEmpty(item.getLineData().getCurrentName())){

            helper.setText(R.id.baseline_name,getItemName(item.getLineData().getCurrentName()));

        }

        textView = helper.getView(R.id.baseline_name);
        RelativeLayout.LayoutParams lp2 = ( RelativeLayout.LayoutParams)textView.getLayoutParams();
        lp2.width = (int)(MyUtils.getWidth()/4);
        textView.setLayoutParams(lp2);

    }


    private String getItemName( String item_name) {

        String name="";
        //服务器返回的始终是中文
        if ("交易笔数".equals(item_name)) {
            name =  mContext.getString(R.string.tradeCount_title);

        }else if ("新增地址".equals(item_name)) {
            name = mContext.getString(R.string.newCount);

        } else if ("活跃地址".equals(item_name)) {
            name = mContext.getString(R.string.activeDis);

        } else if ("交易量".equals(item_name)) {
            name = mContext.getString(R.string.tradevolume_title);
        }else if("总地址".equals(item_name)){
            type = Commons.allAddr;
            name = mContext.getString(R.string.allAddr_title);
        }else if(mContext.getResources().getString(R.string.recently_launched).equals(item_name)){
            name =mContext.getString(R.string.recently_launched);
        }

        return name;
    }

    private void initSingleLineChart(LineBean lineDatabean) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        LineData lineData;

        float min_Yvalue=0;

        if(lineDatabean!=null&&lineDatabean.getDot()!=null){

        for(int i = 0; i < lineDatabean.getDot().size(); i++){

            if(i==0){
                if(!TextUtils.isEmpty(lineDatabean.getDot().get(i).getValue())&&MyUtils.isNumeric(lineDatabean.getDot().get(i).getValue())){

                    min_Yvalue = Float.parseFloat(lineDatabean.getDot().get(i).getValue());
                }
            }

            values.add(new Entry(i, Float.parseFloat(lineDatabean.getDot().get(i).getValue())));

        }

        if(values.size()!=0){

            //设置折线的样式
            LineDataSet dataSet = new LineDataSet(values, "");
            //用y轴的集合来设置参数
            dataSet.setColor(mContext.getResources().getColor(R.color.F398C0));
            dataSet.setLineWidth(1f); // 线宽
            dataSet.setDrawCircles(false);
            dataSet.setDrawValues(false);
            dataSet.setDrawFilled(true);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setFillDrawable(mContext.getResources().getDrawable(R.drawable.line_background01));


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            //构建一个LineData  将dataSets放入
            lineData = new LineData(dataSets);

            new MyLineChartEntity(mContext,lineChart,lineData,min_Yvalue,type);

        }


        }

    }
}
