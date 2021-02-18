package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：DiffOrderDetailOuterBean
 * 描   述：差异单详情的实体
 */
class DiffOrderDetailOuterBean {
    /**
    "msg": "操作成功",
    "code": 200,
    "data": {}
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: DiffOrderDetailDataBean? = null


    class DiffOrderDetailDataBean {



        var differenceSn: String? = ""
        var orderSonSn: String? = ""
        var orderState: String? = ""
        var examineState: String? = ""
        var payTotalPrice: String? = ""
        var differenceImgs:List<String>?=null
        var overTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var payTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var refund: String? = ""
        var differencesExplain: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
        var differencesTotalNum: String? = ""
        var differencesReason:List<String>?=null
        var refuseReason: String? = ""
        var orderGoodsRespList:List<DiffOrderOuterBean.DiffOrderGoodsBean>?=null
        var createdTime: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无" else field
            }
        var name: String? = ""
    }


}