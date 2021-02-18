package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 文 件 名：ConfirmGoodsDialog
 * 描   述：确认收货商品数量修改dialog
 */
public class ConfirmGoodsDialog extends Dialog {

    private static final String TAG = "ConfirmGoodsDialog";

    public ConfirmGoodsDialog(@NonNull Context context) {
        super(context);
    }

    public ConfirmGoodsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String negaText = "";
        String posiText = "";
        double storeCount = 0;//库存量
        double currentGoodsNum = 0;//当前选择的商品数量
        boolean isSupportDecimalPlaces = false;//是否支持小数
        int decimalPlaceLength = 2;//支持小数的长度


        String tag = "";//一个标识符

        ConfirmGoodsDialog modifyDialog;

        public PositiveBtClickImp positiveClick;
        public NegativeBtClickImp negativeClick;

        public Builder(Context context) {
            this.context = context;
        }

        //设置商品库存量
        public ConfirmGoodsDialog.Builder setStoreCount(String storeCount) {
            this.storeCount = Double.valueOf(storeCount);
            return this;
        }

        //设置商品当前数量
        public ConfirmGoodsDialog.Builder setGoodsNum(String goodsNum) {
            this.currentGoodsNum = Double.valueOf(goodsNum);
            return this;
        }

        //设置是否支持小数
        public ConfirmGoodsDialog.Builder setSupportDecimalPlaces(boolean supportDecimalPlaces) {
            this.isSupportDecimalPlaces = supportDecimalPlaces;
            return this;
        }

        //设置标识符
        public ConfirmGoodsDialog.Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }


        //点击确定事件
        public ConfirmGoodsDialog.Builder setPositiveButton(String text, PositiveBtClickImp positiveClick) {
            this.posiText = text;
            this.positiveClick = positiveClick;
            return this;
        }

        //点击否定事件
        public ConfirmGoodsDialog.Builder setNegativeButton(String text, NegativeBtClickImp negativeClick) {
            this.negaText = text;
            this.negativeClick = negativeClick;
            return this;
        }


        public ConfirmGoodsDialog create() {

            View v = LayoutInflater.from(context).inflate(R.layout.dialog_goods_num_modify, null);
            //数量增加按钮
            ShapeTextView stvJia = v.findViewById(R.id.goods_num_stv_jia);
            //数量减少按钮
            ShapeTextView stvJian = v.findViewById(R.id.goods_num_stv_jian);
            //positiveButton
            TextView tvSure = v.findViewById(R.id.sure);
            //negativeButton
            TextView tvCancel = v.findViewById(R.id.cancel);
            EditText etInput = v.findViewById(R.id.goods_num_et_edit);

            etInput.setInputType(isSupportDecimalPlaces ? InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL : InputType.TYPE_CLASS_NUMBER);


            setEtText(etInput, currentGoodsNum + "");
            etInput.addTextChangedListener(new TextWatcher() {
                boolean deleteLastChar;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (isSupportDecimalPlaces) {
                        if (s.toString().contains(".")) {
                            // 如果点后面有超过三位数值,则删掉最后一位
                            int length = s.length() - s.toString().lastIndexOf(".");
                            // 说明后面有三位数值
                            deleteLastChar = length >= decimalPlaceLength+2;//>=5 支持三位小数
                        } else {
                            deleteLastChar = false;
                        }
                    }
                    Log.i(TAG, "---------deleteLastChar" + deleteLastChar);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty()) {
                        return;
                    } else {
                        Log.i(TAG, "---------setSelection");
//                        etInput.setSelection(s.toString().length());
                    }
                    etInput.removeTextChangedListener(this);
                    if (isSupportDecimalPlaces) {

                        // 以小数点开头，前面自动加上 "0"
                        if (s.toString().startsWith(".")) {
                            etInput.setText("0" + s);
                            etInput.setSelection(etInput.getText().length());
                        } else {
                            if (deleteLastChar) {
                                // 设置新的截取的字符串
                                int length = s.toString().lastIndexOf(".");
//                                double num = Double.valueOf(s.toString().substring(0, length +  decimalPlaceLength+1));
//                                if (num > storeCount) {
//                                    showToast();
//                                    num = storeCount;
//                                }

//                                setEtText(etInput, num + "");
                                setEtText(etInput, s.toString().substring(0, length + decimalPlaceLength+1)+"");
                            } else {
//                                double num = Double.valueOf(s.toString());
//                                if (num > storeCount) {
//                                    showToast();
//                                    num = storeCount;
//                                    setEtText(etInput, num + "");
//
//                                }
                            }
                        }
                    } else {
                        double num = Double.valueOf(s.toString());
//                        if (num > storeCount) {
//                            num = storeCount;
//                            showToast();
//                            setEtText(etInput, num + "");
//                        }
                        setEtText(etInput, num + "");
                    }
                    etInput.addTextChangedListener(this);
                }
            });
            stvJia.setOnClickListener(view -> {
                double showNum = Double.valueOf(etInput.getText().toString());
//                if (showNum + 1 <= storeCount) {
                    double modifyGoodsNum = BigDecimalUtils.add(showNum, 1);
                    setEtText(etInput, modifyGoodsNum + "");
//                } else {
//                    showToast();
//                }
            });
            stvJian.setOnClickListener(view -> {
                double showNum = Double.valueOf(etInput.getText().toString());
                if (showNum - 1 < 0) {
                    T.showToastyCenter(context, "数量不能小于0");
                } else {
                    double modifyGoodsNum = BigDecimalUtils.sub(showNum, 1);
//                    etInput.setText(modifyGoodsNum + "");
                    setEtText(etInput, modifyGoodsNum + "");
                }
            });

            tvSure.setText(posiText);
            tvCancel.setText(negaText);
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = etInput.getText().toString();
                    if (TextUtils.isEmpty(str)) {
                        T.showToastyCenter(context, tag.equals("saleOrderDeliver") ? "请输入您发货的商品数量" : "请输入您实取的商品数量");
                        return;
                    }
                    double modifyGoodsNum = Double.valueOf(str);
                    modifyGoodsNum = modifyGoodsNum == 0 ? 0 : modifyGoodsNum;
                    positiveClick.positiveClick(modifyGoodsNum);
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeClick.negativeClick();
                }
            });
            modifyDialog = new ConfirmGoodsDialog(context, R.style.dialog);
            //设置点击空白部分不消失  点击返回键可消失
            modifyDialog.setCanceledOnTouchOutside(false);
            modifyDialog.setCancelable(true);
            modifyDialog.setContentView(v);

            return modifyDialog;
        }


        public interface PositiveBtClickImp {
            void positiveClick(double modifyGoodsNum);
        }

        public interface NegativeBtClickImp {
            void negativeClick();
        }

        //设置到输入框的商品数量去除无效0
        public void setEtText(EditText etInput, String str) {
            etInput.setText(BigDecimalUtils.removeInvalidZero(str + ""));
            etInput.setSelection(etInput.getText().length());
        }


        public void showToast() {
            if ("saleOrderDeliver".equals(tag)) {
                T.showToastyCenter(context, "发货数量不能大于购买数量");
            } else {
                T.showToastyCenter(context, "实取数量不能大于应取数量");
            }
        }

    }

}
