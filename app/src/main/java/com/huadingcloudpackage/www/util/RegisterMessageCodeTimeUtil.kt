package com.retail.dxt.util

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.widget.TextView

class RegisterMessageCodeTimeUtil(
        millisInFuture: Long,
        countDownInterval: Long,
        private val tvCode: TextView,
        private val tickColor: Int,
        private val normalColor: Int
) : CountDownTimer(millisInFuture, countDownInterval) {

    @SuppressLint("HandlerLeak")
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> cancel()
            }
        }
    }

    //点击之后等候过程中发生的状态
    override fun onTick(millisUntilFinished: Long) {
        tvCode.text = "剩余" + (millisUntilFinished / 1000).toString() + "秒"
        tvCode.setTextColor(tickColor)
        tvCode.isClickable = false
    }

    //结束之后状态
    override fun onFinish() {
        tvCode.setTextColor(normalColor)
        tvCode.text = "重新获取"
        tvCode.isClickable = true
    }

    fun onCancel(time: String) {
        val msg = Message.obtain()
        msg.what = 1
        msg.obj = time
        handler.sendMessage(msg)
    }
}
