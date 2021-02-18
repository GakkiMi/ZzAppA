package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：OrderCreatBean
 * 描   述：TODO
 */
class OrderCreatBean {

    var msg: String? = ""
    var code: Int? = -1
    var data: OrderCreateBean? = null


    class OrderCreateBean {
        /**
        "total":450.0,
        "orderSn":"OS2020120300020"
         */


        var total: String? = ""
        var orderSn: String? = ""
    }


}