package com.huadingcloudpackage.www.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huadingcloudpackage.www.AppApplication;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.Logger;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.T;


public class MyJIaJianView extends LinearLayout {
    private String TAG = "MyJIaJianView";
    private ImageView jiaTv, jiantv;
    private EditText numEt;
    private int num = 1;

    private boolean flag = true;


    public MyJIaJianView(Context context) {
        this(context, null);
    }

    public MyJIaJianView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyJIaJianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化自定义的布局
     */
    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.jia_jian_layout, this, true);
//        addView(view);
        jiantv = view.findViewById(R.id.jian);
        jiaTv = view.findViewById(R.id.jia);
        numEt = view.findViewById(R.id.num);


        numEt.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    numEt.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });


        numEt.addTextChangedListener(new TextWatcher() {
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
                }
                num = Integer.parseInt(s.toString());
                if (maxNumer < 1) {
                    return;
                }


                if (num > maxNumer) {
                    num = maxNumer;
                    numEt.setText(num + "");
                    return;
                }
            }


        });

        jiaTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num < maxNumer) {
                    num = num + upNum;
                } else {
                    T.showToastyCenter(getContext(), "库存不足");
                    return;
                }
                //当这句执行后 会去上面的输入框监听事件去执行afterTextChanged 再进行getNum的回调,所以下面的被注释掉了
                numEt.setText(num + "");
            }
        });
        jiantv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num <= upNum) {
                    T.show(getContext(), "数量不能小于" + upNum);
                    num = upNum;
                    return;
                }
                num = num - upNum;
                //当这句执行后 会去上面的输入框监听事件去执行afterTextChanged 再进行getNum的回调,所以下面的被注释掉了
                numEt.setText(num + "");
            }
        });


    }

    int maxNumer = 0;//默认99

    int upNum = 1;

    /**
     * 设置editext数量
     *
     * @param
     */
    public void setNumEt(int n) {
        numEt.setText(n + "");
        num = Integer.parseInt(numEt.getText().toString());
    }

    public void maxNumber(int n) {
        if (n > 0) {
            maxNumer = n;
        } else {
            maxNumer = 0;
        }
    }

    /**
     * @param n 设置报货倍数
     */
    public void setUpNum(int n) {
        upNum = n;
    }

    /**
     * @param ：设置报货倍数
     */
    public int getUpNum() {
        return upNum;
    }

    public int getNumer() {
        return num;

    }

    public int getCurrentNumer() {
        if (!MyUtils.isEtEmpty(numEt)) {
            return Integer.valueOf(MyUtils.getEtText(numEt));
        } else {
            return 0;
        }

    }

    public void setEtNoFocus(boolean focusable) {
        numEt.setFocusableInTouchMode(focusable);
        numEt.setFocusable(focusable);
    }


    public int getMaxNumber() {
        return maxNumer;

    }

    public EditText return_edit() {
        return numEt;

    }

    public void setEtCursorVisible(boolean isShow) {
        numEt.setCursorVisible(isShow);
    }


    private EditTextListener editTextListener;


    public interface EditTextListener {
        void onClick(View view);
    }

    public void seteditTextListener(EditTextListener edListener) {
        this.editTextListener = edListener;
    }
}
