package com.huadingcloudpackage.www.util;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * 文 件 名：CountDownTimeUtils
 * 描   述：倒计时 在界面destory时注意销毁
 */
public class CountDownTimeUtils extends CountDownTimer {


    public CountDownTimeUtils(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String str = timeConversion((int) (millisUntilFinished / 1000));
        Log.i("CountDownTimeUtils", "---------onTick:" + str);
        if (countDownTime != null) {
            countDownTime.onTick(str);
        }
    }

    @Override
    public void onFinish() {
        Log.i("CountDownTimeUtils", "---------onFinish");
        if (countDownTime != null) {
            countDownTime.onFinish();
        }
        cancel();
    }


    public String timeConversion(int time) {
        int hour = 0;
        int minutes = 0;
        int sencond = 0;
        int temp = time % 3600;
        if (time > 3600) {
            hour = time / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
            return (hour < 10 ? ("0" + hour) : hour) + ":" + (minutes < 10 ? ("0" + minutes) : minutes) + ":" + (sencond < 10 ? ("0" + sencond) : sencond);
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
            return (minutes < 10 ? ("0" + minutes) : minutes) + ":" + (sencond < 10 ? ("0" + sencond) : sencond);
        }
    }


    private CountDonwTimeImpl countDownTime;

    public void setCountDownTime(CountDonwTimeImpl countDownTime) {
        this.countDownTime = countDownTime;
    }

    public interface CountDonwTimeImpl {
        void onTick(String time);

        void onFinish();
    }


}
