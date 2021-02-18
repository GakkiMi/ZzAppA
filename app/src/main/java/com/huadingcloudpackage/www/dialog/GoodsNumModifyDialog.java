package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activityfragment.BGouWuCarFragment;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;


/**
 * 文 件 名：GoodsNumModifyDialog
 * 描   述：购物车商品数量修改dialog
 */
public class GoodsNumModifyDialog extends Dialog {

    public GoodsNumModifyDialog(@NonNull Context context) {
        super(context);
    }

    public GoodsNumModifyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String negaText = "";
        String posiText = "";
        int storeCount = 0;//库存量
        int currentGoodsNum;//当前选择的商品数量
        GoodsNumModifyDialog modifyDialog;

        PositiveBtClickImp positiveClick;
        NegativeBtClickImp negativeClick;

        public Builder(Context context) {
            this.context = context;
        }

        //设置商品库存量
        public GoodsNumModifyDialog.Builder setStroeCount(int storeCount) {
            this.storeCount = storeCount;
            return this;
        }

        //设置商品当前数量
        public GoodsNumModifyDialog.Builder setGoodsNum(int goodsNum) {
            this.currentGoodsNum = goodsNum;
            return this;
        }


        //点击确定事件
        public GoodsNumModifyDialog.Builder setPositiveButton(String text, PositiveBtClickImp positiveClick) {
            this.posiText = text;
            this.positiveClick = positiveClick;
            return this;
        }

        //点击否定事件
        public GoodsNumModifyDialog.Builder setNegativeButton(String text, NegativeBtClickImp negativeClick) {
            this.negaText = text;
            this.negativeClick = negativeClick;
            return this;
        }


        public GoodsNumModifyDialog create() {

            View v = LayoutInflater.from(context).inflate(R.layout.dialog_goods_num_modify, null);
            //数量增加按钮
            ShapeTextView stvJia = v.findViewById(R.id.goods_num_stv_jia);
            //数量减少按钮
            ShapeTextView stvJian = v.findViewById(R.id.goods_num_stv_jian);

            TextView tvSure = v.findViewById(R.id.sure);
            //negativeButton
            TextView tvCancel = v.findViewById(R.id.cancel);
            EditText etInput = v.findViewById(R.id.goods_num_et_edit);


            etInput.setText(currentGoodsNum + "");
            etInput.requestFocus();
            etInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().isEmpty()) {
                        return;
                    } else {
                        etInput.setSelection(s.toString().length());
                    }
                    int num = Integer.valueOf(s.toString());
                    if (num > storeCount) {
                        num = storeCount;
                        etInput.setText(num + "");
//
                        T.showToastyCenter(context, "最多只能选" + storeCount + "件哦~");
                        return;
                    }
                }
            });
            stvJia.setOnClickListener(view -> {
                int showNum = Integer.parseInt(etInput.getText().toString());
                if (showNum < storeCount) {
                    int modifyGoodsNum = showNum + 1;
                    etInput.setText(modifyGoodsNum + "");
                } else {
                    T.showToastyCenter(context, "最多只能选" + storeCount + "件哦~");
                }
            });
            stvJian.setOnClickListener(view -> {
                int showNum = Integer.parseInt(etInput.getText().toString());
                if (showNum <= 1) {
                    T.showToastyCenter(context, "数量不能小于1");
                } else {
                    int modifyGoodsNum = showNum - 1;
                    etInput.setText(modifyGoodsNum + "");
                }
            });

            tvSure.setText(posiText);
            tvCancel.setText(negaText);
            tvSure.setOnClickListener(v1 -> {
                String str = etInput.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    T.showToastyCenter(context, "请输入您要购买的商品数量");
                    return;
                }
                int modifyGoodsNum = Integer.parseInt(str);
                modifyGoodsNum = modifyGoodsNum == 0 ? 1 : modifyGoodsNum;
                positiveClick.positiveClick(modifyGoodsNum);
            });
            tvCancel.setOnClickListener(v12 -> negativeClick.negativeClick());
            modifyDialog = new GoodsNumModifyDialog(context, R.style.softInputModeShow);
            //设置点击空白部分不消失  点击返回键可消失
            modifyDialog.setCanceledOnTouchOutside(false);
            modifyDialog.setCancelable(true);
            modifyDialog.setContentView(v);

            return modifyDialog;
        }


        public interface PositiveBtClickImp {
            void positiveClick(int modifyGoodsNum);
        }

        public interface NegativeBtClickImp {
            void negativeClick();
        }


    }

}
