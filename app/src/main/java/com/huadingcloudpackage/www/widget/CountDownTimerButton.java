package com.huadingcloudpackage.www.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.huadingcloudpackage.www.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by whffi on 2017/5/31.
 */

public class CountDownTimerButton extends AppCompatTextView {

    private Context mContext;
    private OnClickListener mOnClickListener;
    private Timer mTimer;//调度器
    private TimerTask mTask;
    private long duration = 60000;//倒计时时长 设置默认60秒
    //private long duration = 6000;
    private long temp_duration;
    private String clickBeffor = "重新发送";//点击前
    private String clickAfter = "s后重试";//点击后

    private Drawable ClickBackound = getResources().getDrawable(R.drawable.shape_white_preferences_item);  //可点击背景
    private Drawable unClickBackound = getResources().getDrawable(R.drawable.shape_white_preferences_item); //不可点击背景
    private int ClickTextColor = getResources().getColor(R.color.theme_color); //可点击时字体颜色
    private int unClickTextColor = getResources().getColor(R.color.color_C1C9D1); //不点击时字体颜色

    private Boolean isduration = false;

    public CountDownTimerButton(Context context) {
        super(context);
        mContext = context;
        //   setOnClickListener(this);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            CountDownTimerButton.this.setText(clickAfter + temp_duration / 1000 + ")");
               CountDownTimerButton.this.setText(temp_duration/1000 + clickAfter);

            temp_duration -= 1000;
            if (temp_duration < 0) {//倒计时结束

                stopTimer();
            }
        }
    };

//    @Override
//    public void setOnClickListener(OnClickListener onClickListener) {//提供外部访问方法
//        if (onClickListener instanceof CountDownTimerButton) {
//            super.setOnClickListener(onClickListener);
//        } else {
//            this.mOnClickListener = onClickListener;
//        }
//    }

//    @Override
//    public void onClick(View view) {
//        if (mOnClickListener != null) {
//            mOnClickListener.onClick(view);
//            startTimer();
//        }


    //  }

    //计时开始
    public void startTimer() {
        setIsduration(true);
        temp_duration = duration;
        CountDownTimerButton.this.setEnabled(false);
        CountDownTimerButton.this.setTextColor(unClickTextColor);
        CountDownTimerButton.this.setBackground(unClickBackound);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x01);
            }
        };
        mTimer.schedule(mTask, 0, 1000);//调度分配，延迟0秒，时间间隔为1秒
    }

    //计时结束
    public void stopTimer() {
        CountDownTimerButton.this.setTextColor(ClickTextColor);
        CountDownTimerButton.this.setBackground(ClickBackound);
        CountDownTimerButton.this.setEnabled(true);
        CountDownTimerButton.this.setText(clickBeffor);

        setIsduration(false);
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public Boolean getIsduration() {
        return isduration;
    }

    public void setIsduration(Boolean isduration) {
        this.isduration = isduration;
    }

    public void setClickBackound(Drawable clickBackound) {
        ClickBackound = clickBackound;
    }

    public void setUnClickBackound(Drawable unClickBackound) {
        this.unClickBackound = unClickBackound;
    }

    public void setClickTextColor(int clickTextColor) {
        ClickTextColor = clickTextColor;
    }

    public void setUnClickTextColor(int unClickTextColor) {
        this.unClickTextColor = unClickTextColor;
    }



}
