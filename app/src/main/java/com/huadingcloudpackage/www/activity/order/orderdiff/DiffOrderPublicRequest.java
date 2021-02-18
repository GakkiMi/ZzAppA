package com.huadingcloudpackage.www.activity.order.orderdiff;

import android.content.Context;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * 文 件 名：DiffOrderPublicRequest
 * 描   述：TODO
 */
public class DiffOrderPublicRequest {


    public static ComTools comTools = new ComTools();

    /**
     * 取消报货订单
     */
    public static void cancelDiffOrder(Context context, String diffOrderSn, DiffOrderPublicRequestSuccessImpl publicRequest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("differenceOrderSn", diffOrderSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DIFF_ORDER_CANCEL)
                .tag(context)
                .upRequestBody(body)
                .execute(new DialogCallback(context, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                if (publicRequest != null) {
                                    publicRequest.requsetSuccess();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.show(context, "网络开小差，请稍后重试 ~");

                    }
                });
    }

    /**
     * 删除报货订单
     */
    public static void deleteDiffOrder(Context context, String diffOrderSn, DiffOrderPublicRequestSuccessImpl publicRequest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("differenceOrderSn", diffOrderSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DIFF_ORDER_DELETE)
                .tag(context)
                .upRequestBody(body)
                .execute(new DialogCallback(context, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                if (publicRequest != null) {
                                    publicRequest.requsetSuccess();
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.show(context, "网络开小差，请稍后重试 ~");
                    }
                });
    }



    public static DiffOrderPublicRequestSuccessImpl publicRequestImpl;

    public static void setPublicRequestImpl(DiffOrderPublicRequestSuccessImpl publicRequest) {
        DiffOrderPublicRequest.publicRequestImpl = publicRequest;
    }

    public interface DiffOrderPublicRequestSuccessImpl {
        void requsetSuccess();
    }



}
