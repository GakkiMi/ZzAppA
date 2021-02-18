package com.huadingcloudpackage.www.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * app更新失败时点击事件的接收器
 */

public class AppUpdateReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        receiverImp.appUpdateFail();
    }

    static AppUpdateReceiverImp receiverImp;

    public static void setReceiverImp(AppUpdateReceiverImp receiverImp) {
        AppUpdateReceiver.receiverImp = receiverImp;
    }

    public interface AppUpdateReceiverImp {
        void appUpdateFail();
    }

}
