package com.huadingcloudpackage.www.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by hongchuanwei .
 * on 2017/6/1
 */

public class LogUtilsApp {
    /**
     * 方法说明:
     * @param  str  要输出的信息
     */
    public static void d(String str){
        int segmentSize = 3 * 1024;
        long length = str.length();
        if(!TextUtils.isEmpty(str)){
            if (length <= segmentSize) {
                // 长度小于等于限制直接打印
                Log.d("okgo", str);
            } else {
                while (str.length() > segmentSize) {// 循环分段打印日志
                    String logContent = str.substring(0, segmentSize);
                    str = str.replace(logContent, "");
                    Log.d("okgo", logContent);
                }
                Log.d("okgo", str);// 打印剩余日志 } }
            }
        }
    }
}
