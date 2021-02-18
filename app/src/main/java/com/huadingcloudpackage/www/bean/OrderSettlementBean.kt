package com.huadingcloudpackage.www.bean

import java.util.*

/**
 * 文 件 名：OrderSettlementBean
 * 描   述：订单结算外层实体（购物车列表点进去）
 */
class OrderSettlementBean {
    /**
    "msg": "操作成功",
    "code": 200,
    "data": []
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: List<OrderSettlementData>? = null

    class OrderSettlementData {

        /**
        "freight": 0,
        "totalPrice": 900.0,
        "goodsItemNums": 1,
        "list": [],
        "supplierType": 2,
        "name": "随风倒"

         */
        var freight: String? = ""
        var totalPrice: String? = ""
        var goodsItemNums: String? = ""
        var list: List<OrderSettleGoodsList>? = null
        var supplierType: String? = ""
        var name: String? = ""

        //自加字段
        var deliveryType: String = ""//used配送方式
        var selfMentionTimeShow: String = ""//used自提时间(只用来展示在pickView)
        var selfMentionTimeUpLoad: String = ""//used自提时间(用来传递到后台)
        var orderRemark: String = ""//used订单备注


    }

    class OrderSettleGoodsList {

        /**
        "goodsId": "SP2020111600001",
        "specId": "GG2020111600002",
        "cartId": "CT2020120200031",
        "goodsNum": 2,
        "goodsName": "山药",
        "goodsImg": "111",
        "specName": "100Kg/箱",
        "specKey": "箱",
        "goodsPrice": 450.0
         */

        var goodsId: String? = ""
        var specId: String? = ""
        var cartId: String? = ""
        var goodsNum: String? = ""
        var goodsName: String? = ""
        var goodsImg: String? = ""
        var specName: String? = ""
        var specKey: String? = ""
        var goodsPrice: String? = ""


        var goodsBasicsSpecKey: String? = ""
        var goodsBasicsSpecPrice: String? = ""
        var goodsBasicsSpecName: String? = ""
        var isWhole: String? = ""//0标识拆零  1不拆零
        get() {
            return if (field.isNullOrEmpty()) "0" else field
        }
        var specRatio: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "1" else field
            }

    }

}

