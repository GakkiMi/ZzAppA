package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：DeclareOrderDetailOuterBean
 * 描   述：TODO
 */
class DeclareOrderDetailOuterBean {

    /**
    "msg": "操作成功",
    "code": 200,
    "data": []
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: List<DeclareOrderDetailDataBean>? = null


    class DeclareOrderDetailDataBean {

        /**
        "orderSn": "OS2020120200003",
        "orderStatus": 0,
        "consigneeName": "谢威1111",
        "consigneePhone": "15335465465",
        "consigneeAddress": "河北省石家庄市市辖区454",
        "remark": null,
        "totalAmount": 6520.00,
        "addTime": "2020-12-02 11:17:38",
        "payTime": null,
        "countdownTime": 0,
        "payEndTime": "2020-12-02 11:47:38",
        "orderSonDetailsRespList": []
         */

        var orderSn: String? = ""
        var orderStatus: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "-1" else field
            }
        var consigneeName: String? = ""
        var consigneePhone: String? = ""
        var consigneeAddress: String? = ""
        var remark: String? = ""
        var totalAmount: String? = ""
        var addTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var payTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var countdownTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "0" else field
            }
        var payEndTime: String? = ""
        var orderSonDetailsRespList: List<DeclareOrderDetailRespBean>? = null
    }

    class DeclareOrderDetailRespBean {

        /**
        "orderSn": "OS2020120200003",
        "totalSonAmount": 10.00,
        "freightPrice": 0.00,
        "orderSonAmount": null,
        "orderSonStatus": 0,
        "orderSonSn": "OSS2020120200003",
        "splitName": "谢威1111",
        "shipmentsTime": null,
        "overTime": null,
        "deliveryMethod": null,
        "remark": null,
        "isRemind": 0,
        "omsOutNo":null,
        "orderGoodsDetailsRespList": []
         */

        var orderSn: String? = ""
        var totalSonAmount: String? = ""
        var freightPrice: String? = ""
        var orderSonAmount: String? = ""
        var orderSonStatus: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "-1" else field
            }
        var orderSonSn: String? = ""
        var splitName: String? = ""
        var shipmentsTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var overTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var cancelTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var deliveryMethod: String? = ""
        var remark: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var isRemind: String? = ""
        var omsOutNo: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
        var orderGoodsDetailsRespList: List<DeclareOrderOuterBean.OrderGoodsBean>? = null
    }



}