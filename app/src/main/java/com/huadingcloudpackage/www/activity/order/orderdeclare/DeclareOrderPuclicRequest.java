package com.huadingcloudpackage.www.activity.order.orderdeclare;

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
 * 文 件 名：DeclareOrderPuclicRequest
 * 描   述：报货订单
 */
public class DeclareOrderPuclicRequest {


    public static ComTools comTools = new ComTools();

    /**
     * 取消报货订单
     */
    public static void cancelDeclareOrder(Context context, String orderSn, DeclareOrderPublicRequestSuccessImpl publicRequest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSn", orderSn);
//        params.put("orderStatus", );
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_CANCEL)
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
    public static void deleteDeclareOrder(Context context, String orderSn, DeclareOrderPublicRequestSuccessImpl publicRequest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSn", orderSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_DELETE)
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
     * 订单提醒发货
     */
    public static void remindDeclareOrder(Context context, String orderSonSn, DeclareOrderPublicRequestSuccessImpl publicRequest) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSonSn", orderSonSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_REMIND)
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


    public static DeclareOrderPublicRequestSuccessImpl publicRequestImpl;

    public static void setPublicRequestImpl(DeclareOrderPublicRequestSuccessImpl publicRequest) {
        publicRequestImpl = publicRequest;
    }

    public interface DeclareOrderPublicRequestSuccessImpl {
        void requsetSuccess();
    }

}
