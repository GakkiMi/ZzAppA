package com.huadingcloudpackage.www.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.huadingcloudpackage.www.R;

import java.util.Calendar;

/**
 * @author lige
 * 描述：时间选择器
 */


public class MineDataDialog extends AlertDialog implements OnDateChangedListener {

    private NumberPicker np1, np2, np3;
    private final OnDateSetListener mCallBack;
    private final OnDateZeroSetListener mCallBackZero;
    private int maxDay;

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    public interface OnDateSetListener {
        void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }

    public interface OnDateZeroSetListener {
        void onDateZeroSet(int year, int monthOfYear);
    }

    public MineDataDialog(Context context, int theme, OnDateSetListener callBack, OnDateZeroSetListener callBackZero, int year, int monthOfYear,
                          int dayOfMonth, boolean isDayVisible) {
        super(context, R.style.dialog);

        mCallBack = callBack;
        mCallBackZero = callBackZero;

        Context themeContext = getContext();

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_mine_data, null);
        setView(view);
        LinearLayout llClose = view.findViewById(R.id.ll_close);
        llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineDataDialog.this.dismiss();
            }
        });
        np1 = view.findViewById(R.id.np1);
        np2 = view.findViewById(R.id.np2);
        np3 = view.findViewById(R.id.np3);

        //获取当前日期
        Calendar c = Calendar.getInstance();
        final int years = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH) + 1;//月份是从0开始算的
        final int day = c.get(Calendar.DAY_OF_MONTH);

        //设置年份
        np1.setMaxValue(2999);
        np1.setValue(years); //中间参数 设置默认值
        np1.setMinValue(1900);

        //设置月份
        np2.setMaxValue(12);
        np2.setValue(month);
        np2.setMinValue(1);

        //设置天数
        np3.setMaxValue(31);
        np3.setValue(day);
        np3.setMinValue(0);

        //年份滑动监听
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //平年闰年判断
                if (newVal % 4 == 0) {
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                //设置天数的最大值
                np3.setMaxValue(maxDay);
            }
        });

        //月份滑动监听
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //月份判断
                switch (newVal) {
                    case 2:
                        if (np1.getValue() % 4 == 0) {
                            maxDay = 29;
                        } else {
                            maxDay = 28;
                        }
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDay = 31;
                        break;
                    default:
                        maxDay = 30;
                        break;
                }
                //设置天数的最大值
                np3.setMaxValue(maxDay);
            }
        });

        TextView tvSure = view.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击确定 把当前获取到的年和月传递给我的账户页
                int years = np1.getValue();
                int months = np2.getValue();
                int days = np3.getValue();
                if (days == 0) {
                    mCallBackZero.onDateZeroSet(years, months);
                } else {
                    mCallBack.onDateSet(years, months, days);
                }
                MineDataDialog.this.dismiss();
            }
        });
    }

}
