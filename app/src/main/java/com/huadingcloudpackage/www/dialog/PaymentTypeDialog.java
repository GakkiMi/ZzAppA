package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huadingcloudpackage.www.R;

/**
 * @author lige
 * 描述：支付类型弹窗
 */
public class PaymentTypeDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnItemListener listener;
    private final TextView mTvAllType;
    private final TextView mTvChongzhi;
    private final TextView mTvDingdanzhifu;
    private final TextView mTvTuikuan;
    private final TextView mTvBack;
    private final TextView mTvAfterSalePayment;
    public int index;

    public PaymentTypeDialog(Context context, OnItemListener onItemListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = onItemListener;

        setContentView(R.layout.dialog_payment_type);
        setCanceledOnTouchOutside(false);
        LinearLayout mLlClose = findViewById(R.id.ll_close);
        mLlClose.setOnClickListener(this);
        mTvBack = findViewById(R.id.tv_back);
        mTvAllType = findViewById(R.id.tv_all_type);
        mTvChongzhi = findViewById(R.id.tv_chongzhi);
        mTvDingdanzhifu = findViewById(R.id.tv_dingdanzhichu);
        mTvTuikuan = findViewById(R.id.tv_tuikuan);
        mTvAfterSalePayment = findViewById(R.id.tv_after_sale_payment);
        mTvAllType.setOnClickListener(this);
        mTvChongzhi.setOnClickListener(this);
        mTvDingdanzhifu.setOnClickListener(this);
        mTvTuikuan.setOnClickListener(this);
        mTvAfterSalePayment.setOnClickListener(this);
        mTvBack.setOnClickListener(this);
        TextView mTvSure = findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);

        //默认全部类型
        index = 2;
        setItemSelect(index);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_close:
                dismiss();
                break;
            case R.id.tv_all_type:
                index = 2;
                break;
            case R.id.tv_chongzhi:
                index = 0;
                break;
            case R.id.tv_dingdanzhichu:
                index = 1;
                break;
            case R.id.tv_tuikuan:
                index = 3;
                break;
            case R.id.tv_after_sale_payment:
                index = 4;
                break;
            case R.id.tv_back:
                index = 5;
                break;

            case R.id.tv_sure:
                listener.onItemSignin(index);
                dismiss();
                break;
        }
        setItemSelect(index);
    }


    public void setItemSelect(int index) {
        setNoSelect(mTvAllType);
        setNoSelect(mTvChongzhi);
        setNoSelect(mTvDingdanzhifu);
        setNoSelect(mTvTuikuan);
        setNoSelect(mTvAfterSalePayment);
        setNoSelect(mTvBack);
        if (index == 2) {
            setSelect(mTvAllType);
        } else if (index == 0) {
            setSelect(mTvChongzhi);
        } else if (index == 1) {
            setSelect(mTvDingdanzhifu);
        } else if (index == 3) {
            setSelect(mTvTuikuan);
        } else if (index == 4) {
            setSelect(mTvAfterSalePayment);
        } else if (index==5){
            setSelect(mTvBack);
        }
    }


    public void setNoSelect(TextView textView) {
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextColor(0xff999999);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
    }

    public void setSelect(TextView textView) {
        textView.setBackgroundColor(0xffF5F5F5);
        textView.setTextColor(0xff333333);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
    }


    public interface OnItemListener {
        void onItemSignin(int index);
    }
}
