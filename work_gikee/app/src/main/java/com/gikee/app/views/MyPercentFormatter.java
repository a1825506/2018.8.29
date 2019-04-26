package com.gikee.app.views;

import android.text.TextUtils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

   public class MyPercentFormatter implements IValueFormatter, IAxisValueFormatter
    {

        protected DecimalFormat mFormat;

        private String smybol;

        public MyPercentFormatter(String smybol) {
            mFormat = new DecimalFormat("###,###,##0.0");
            smybol = smybol;
        }

        /**
         * Allow a custom decimalformat
         *
         * @param format
         */
        public MyPercentFormatter(DecimalFormat format) {
            this.mFormat = format;
        }

        // IValueFormatter
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if(!TextUtils.isEmpty(smybol)){
                return mFormat.format(value) + smybol.toUpperCase();
            }else
                return mFormat.format(value) ;

        }

        // IAxisValueFormatter
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if(!TextUtils.isEmpty(smybol)){
                return mFormat.format(value) + smybol.toUpperCase();
            }else
                return mFormat.format(value) ;

        }

        public int getDecimalDigits() {
            return 1;
        }
    }

