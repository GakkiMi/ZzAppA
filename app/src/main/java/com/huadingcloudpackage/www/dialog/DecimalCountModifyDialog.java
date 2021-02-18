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
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;

/**
 * 文 件 名：DecimalCountModifyDialog
 * 描   述：支持小数和整数修改的dialog
 */
public class DecimalCountModifyDialog extends Dialog {

    private static final String TAG = "ConfirmGoodsDialog";

    public DecimalCountModifyDialog(@NonNull Context context) {
        super(context);
    }

    public DecimalCountModifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    //有执行顺序 先设置输入条件 在设置最大数量和原始数量
    public static class Builder {
        private Context context;
        private String negaText = "";
        private String posiText = "";
        //设置输入条件
        private boolean isSupportDecimalPlaces = false;//是否支持小数
        private boolean isHaveNumberLimit = false;//是否有数量限制(默认无限制)
        private int decimalPlaceLength = 2;//支持小数的长度 默认两位
        //设置最大数量和原始数量
        private double mMaxNumber = 0;//有数量限制时最大可输入的数
        private double mOriginalNumber = 0;//传入的初始数

        private String mNumberOverToastStr = "数量超出";//数量超出弹出的吐司  默认"数量超出"

        private DecimalCountModifyDialog modifyDialog;

        private PositiveBtClickImp positiveClick;
        private NegativeBtClickImp negativeClick;

        private EditText etInput;

        public Builder(Context context) {
            this.context = context;
        }

        //设置有数量限制时最大可输入的数
        public DecimalCountModifyDialog.Builder setMaxNumber(String maxNumber) {
            this.mMaxNumber = Double.valueOf(maxNumber);
            return this;
        }

        //设置传入的初始数
        public DecimalCountModifyDialog.Builder setOriginalNumber(String originalNumber) {
            this.mOriginalNumber = Double.valueOf(originalNumber);
            return this;
        }

        //设置是否支持小数
        public DecimalCountModifyDialog.Builder setSupportDecimalPlaces(boolean supportDecimalPlaces) {
            this.isSupportDecimalPlaces = supportDecimalPlaces;
            return this;
        }

        //设置是支持小数的长度
        public DecimalCountModifyDialog.Builder setDecimalPlaceLength(int decimalPlaceLength) {
            this.decimalPlaceLength = decimalPlaceLength;
            return this;
        }

        //设置输入的数量是否有限制
        public DecimalCountModifyDialog.Builder setHaveNumberLimit(boolean isHaveNumberLimit) {
            this.isHaveNumberLimit = isHaveNumberLimit;
            return this;
        }

        //设置数量超出弹出的吐司
        public DecimalCountModifyDialog.Builder setNumberOverToastStr(String numberOverToastStr) {
            this.mNumberOverToastStr = numberOverToastStr;
            return this;
        }


        //点击确定事件
        public DecimalCountModifyDialog.Builder setPositiveButton(String text, PositiveBtClickImp positiveClick) {
            this.posiText = text;
            this.positiveClick = positiveClick;
            return this;
        }

        //点击否定事件
        public DecimalCountModifyDialog.Builder setNegativeButton(String text, NegativeBtClickImp negativeClick) {
            this.negaText = text;
            this.negativeClick = negativeClick;
            return this;
        }


        public DecimalCountModifyDialog create() {

            View v = LayoutInflater.from(context).inflate(R.layout.dialog_goods_num_modify, null);
            //数量增加按钮
            ShapeTextView stvJia = v.findViewById(R.id.goods_num_stv_jia);
            //数量减少按钮
            ShapeTextView stvJian = v.findViewById(R.id.goods_num_stv_jian);
            //positiveButton
            TextView tvSure = v.findViewById(R.id.sure);
            //negativeButton
            TextView tvCancel = v.findViewById(R.id.cancel);
            etInput = v.findViewById(R.id.goods_num_et_edit);

            etInput.setInputType(isSupportDecimalPlaces ? InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL : InputType.TYPE_CLASS_NUMBER);

            etInput.addTextChangedListener(new MyTextWather());
            setEtText(etInput, mOriginalNumber + "");

            stvJia.setOnClickListener(view -> {
                double showNum = Double.valueOf(etInput.getText().toString());

                if (isHaveNumberLimit) {
                    if (showNum + 1 <= mMaxNumber) {
                        double modifyGoodsNum = BigDecimalUtils.add(showNum, 1);
                        setEtText(etInput, modifyGoodsNum + "");
                    } else {
                        showToast();
                    }
                } else {
                    double modifyGoodsNum = BigDecimalUtils.add(showNum, 1);
                    setEtText(etInput, modifyGoodsNum + "");
                }
            });
            stvJian.setOnClickListener(view -> {
                double showNum = Double.valueOf(etInput.getText().toString());
                if (showNum - 1 < 0) {
                    T.showToastyCenter(context, "数量不能小于0");
                } else {
                    double modifyGoodsNum = BigDecimalUtils.sub(showNum, 1);
                    setEtText(etInput, modifyGoodsNum + "");
                }
            });

            tvSure.setText(posiText);
            tvCancel.setText(negaText);
            tvSure.setOnClickListener(v12 -> {
                String str = etInput.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    T.showToastyCenter(context, "请输入要修改的数量");
                    return;
                }
                double modifyGoodsNum = Double.valueOf(str);
                modifyGoodsNum = modifyGoodsNum == 0 ? 0 : modifyGoodsNum;
                positiveClick.positiveClick(modifyGoodsNum);
            });
            tvCancel.setOnClickListener(v1 -> negativeClick.negativeClick());

            modifyDialog = new DecimalCountModifyDialog(context, R.style.dialog);
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
            T.showToastyCenter(context, mNumberOverToastStr);
        }


        public class MyTextWather implements TextWatcher {
            boolean isDecimalLengOver;//输入的小数长度是否大于支持长度

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
                        isDecimalLengOver = length >= decimalPlaceLength + 2;//>=5 支持三位小数
                    } else {
                        isDecimalLengOver = false;
                    }
                }
                Log.i(TAG, "---------isDecimalLengOver" + isDecimalLengOver);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    return;
                } else {
                    Log.i(TAG, "---------setSelection");
                    //etInput.setSelection(s.toString().length());
                }
                etInput.removeTextChangedListener(this);
                if (isSupportDecimalPlaces) {
                    // 以小数点开头，前面自动加上 "0"
                    if (s.toString().startsWith(".")) {
                        etInput.setText("0" + s);
                        etInput.setSelection(etInput.getText().length());
                    } else {
                        if (isDecimalLengOver) {
                            // 设置新的截取的字符串
                            int theDecimalPointIndex = s.toString().lastIndexOf(".");//小数点出现的索引
                            String theSubString = s.toString().substring(0, theDecimalPointIndex + decimalPlaceLength + 1);//截掉多余小数后的字符串
                            if (isHaveNumberLimit) {
                                double num = Double.valueOf(theSubString);
                                if (num > mMaxNumber) {
                                    showToast();
                                    num = mMaxNumber;
                                }
                                setEtText(etInput, num + "");
                            } else {
                                setEtText(etInput, theSubString);
                            }
                        } else {
                            if (isHaveNumberLimit) {
                                double num = Double.valueOf(s.toString());
                                if (num > mMaxNumber) {
                                    showToast();
                                    num = mMaxNumber;
                                    setEtText(etInput, num + "");
                                }
                            }
                        }
                    }
                } else {
                    int num = (int) Math.floor(Double.valueOf(s.toString()));
                    if (isHaveNumberLimit) {
                        if (num > mMaxNumber) {
                            showToast();
                            num = (int) Math.floor(mMaxNumber);//取小于或等于自己最大的整数
                        }
                        setEtText(etInput, num + "");
                    } else {
                        setEtText(etInput, num + "");
                    }
                }
                etInput.addTextChangedListener(this);
            }
        }
    }
}
