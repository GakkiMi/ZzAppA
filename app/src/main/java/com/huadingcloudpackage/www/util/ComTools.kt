package com.guoquan.yunpu.util

import com.huadingcloudpackage.www.AppApplication
import com.huadingcloudpackage.www.bean.BaseBean
import com.huadingcloudpackage.www.eventbus.EventUnLogin
import com.huadingcloudpackage.www.sp.PreferenceManager
import com.huadingcloudpackage.www.util.T
import org.greenrobot.eventbus.EventBus
import java.util.*
import java.util.regex.Pattern

class ComTools {

    /**
     * 解析服务器返回数据，不同状态码不同操作
     */
    fun parsingReturnData(bean: BaseBean, parsingReturnListener: OnParsingReturnListener) {
        when (bean.code) {
            200 -> {
                parsingReturnListener.onParsingSuccess()
            }
            204 -> {
//                T.show(AppApplication.instance, bean.msg)
                parsingReturnListener.onParsingSuccess()
            }
            -101 -> {//冻结账号提示并退出登录ne
                T.show(AppApplication.instance, bean.msg)
//                PreferenceManager.instance().saveToken("")
//                EventBus.getDefault().postSticky(EventUnLogin())
            }
            401 -> {  //登录失效，请重新登录
                PreferenceManager.instance().saveToken("")
                EventBus.getDefault().post(EventUnLogin())
            }
            500 -> {  //服务器报错
                T.show(AppApplication.instance, bean.msg)
            }
            -102 -> {  //提示并退出登录
                T.show(AppApplication.instance, bean.msg)
                PreferenceManager.instance().saveToken("")
                EventBus.getDefault().post(EventUnLogin())
            }
            -200 -> {  //提示并停止往下运行
                T.show(AppApplication.instance, bean.msg)
                PreferenceManager.instance().saveToken("")
                EventBus.getDefault().post(EventUnLogin())
//                Constans.token = ""
            }
            else -> {
                var isShowToast: Boolean = bean.isShowToast;
                if (isShowToast) {
                    T.show(AppApplication.instance, bean.msg)
                }
            }

        }
    }


    /**
     * 解析服务器返回数据，不同状态码不同操作
     */
    fun parsingReturnData(bean: BaseBean, requestCallBack: RequestCallBack) {
        when (bean.code) {
            200 -> {
                requestCallBack.onSuccess()
            }
            204 -> {
                requestCallBack.onSuccess()
            }
            401 -> {  //登录失效，请重新登录
                PreferenceManager.instance().saveToken("")
                EventBus.getDefault().post(EventUnLogin())
            }
            500 -> {  //服务器报错
                var isShowToast: Boolean = bean.isShowToast;
                if (isShowToast) {
                    T.show(AppApplication.instance, bean.msg)
                }
                requestCallBack.onError()
            }
            else -> {
                var isShowToast: Boolean = bean.isShowToast;
                if (isShowToast) {
                    T.show(AppApplication.instance, bean.msg)
                }
                requestCallBack.onError()
            }

        }
    }

    interface OnParsingReturnListener {
        fun onParsingSuccess()
    }

    interface RequestCallBack {
        fun onSuccess()
        fun onError()
    }


    /**
     * 是否是手机号
     */
    fun isMobileNumber(mobile: String): Boolean {
        if (mobile.length != 11) return false
        // "^((\\d{7,8})|(0\\d{2,3}-\\d{7,8})|(1[34578]\\d{9}))$"
        val p =
            Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9]|166|198|199|(147))\\d{8}$")
        val m = p.matcher(mobile)
        return m.matches()
    }

    /**
     * 将请求参数包裹上params
     *
     * @param paramsMap
     * @return
     */
    fun getBodyMapForParams(
        key: String,
        paramsMap: Map<String, Any>
    ): Map<String, Map<String, Any>> {
        var bodyMap = HashMap<String, Map<String, Any>>()
        bodyMap[key] = paramsMap
        return bodyMap
    }


}