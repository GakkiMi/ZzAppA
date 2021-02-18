package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：DeclareOrderOuterBean
 * 描   述：报货订单列表实体
 */
class DeclareOrderOuterBean {

    /**
    "msg:""操作成功",
    "code:"200,
    "data:"[]
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: OrderDataBean? = null

    class OrderDataBean{
        /**
        "total":4,
        "list":[],
        "pageNum":1,
        "pageSize":10,
        "size":4,
        "startRow":1,
        "endRow":4,
        "pages":1,
        "prePage":0,
        "nextPage":0,
        "isFirstPage":true,
        "isLastPage":true,
        "hasPreviousPage":false,
        "hasNextPage":false,
        "navigatePages":8,
        "navigatepageNums":[1],
        "navigateFirstPage":1,
        "navigateLastPage":1
         */

        var total: String? = ""
        var list: List<OrderDataList>? = null
        var pageNum: String? = ""
        var pageSize: String? = ""
        var size: String? = ""
        var startRow: String? = ""
        var endRow: String? = ""
        var pages: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "1" else field
            }
        var prePage: String? = ""
        var nextPage: String? = ""
        var isFirstPage: String? = ""
        var isLastPage: String? = ""
        var hasPreviousPage: String? = ""
        var hasNextPage: String? = ""
        var navigatePages: String? = ""
        var navigatepageNums: List<String>? = null
        var navigateFirstPage: String? = ""
        var navigateLastPage: String? = ""
    }


    class OrderDataList {

        /**
        "orderSn:""OS2020120200004",
        "orderStatus:"1,
        "goodsTotalNum:"5,
        "orderAmount:"6520.00,
        "totalAmount:"6520.00,
        "orderRespList:"[]
         */


        var orderSn: String? = ""
        var orderStatus: String? = null
            get() {
                return if (field.isNullOrEmpty()) "-1" else field
            }
        var goodsTotalNum: Double? = 0.0
        var orderAmount: String? = ""
        var totalAmount: String? = ""
        var orderRespList: List<OrderRespBean>? = null
        var isExpand:Boolean=false;
    }

    class OrderRespBean {

        /**
        "orderSonAmount:"null,
        "totalSonAmount:"10.00,
        "orderSn:""OS2020120200004",
        "freightPrice:"0.00,
        "orderSonStatus:"1,
        "orderSonSn:""OSS2020120200006",
        "isRemind:"0,
        "suppliersId":"GY2020110400022",
        "suppliersName":"哈哈",
        "orderGoodsList:"[]
        "cusId":"KH2020110600005",
        "omsOutNo":null
         */

        var orderSonAmount: String? = ""
        var totalSonAmount: String? = ""
        var orderSn: String? = ""
        var freightPrice: String? = ""
        var orderSonStatus: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "-1" else field
            }
        var orderSonSn: String? = ""
        var isRemind: String? = ""//是否提醒发货 0未提醒 1已提醒
            get() {
                return if (field.isNullOrEmpty()) "0" else field
            }
        var orderGoodsList: List<OrderGoodsBean>? = null

        var suppliersId: String? = ""
        var suppliersName: String? = ""
        var cusId: String? = ""
        var omsOutNo: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
    }


    class OrderGoodsBean {
        /**
        "goodsSn": "SP2020111600001",
        "goodsName": "山药",
        "goodsImageUrl": "111",
        "goodsItem": "箱",
        "goodsSpecId": "GG2020111600002",
        "goodsPrice": 450.00,
        "goodsNum": 1,
        "isDifference": 1,
        "goodsSpecName": "100Kg/箱"
         */

        var goodsSn: String? = ""
        var goodsName: String? = ""
        var goodsImageUrl: String? = ""
        var goodsItem: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
        var goodsSpecId: String? = ""
        var goodsPrice: String? = ""
        var goodsNum: String? = ""
        var isDifference: String? = ""
        var goodsSpecName: String? = ""

        var orderSonSn: String? = ""
        var goodsBasicsSpecKey: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
        var goodsBasicsSpecPrice: String? = ""
        var goodsBasicsSpecName: String? = ""
        var isWhole: String? = ""//0标识拆零  1不拆零
            get() {
                return if (field.isNullOrEmpty()) "0" else field
            }
        var itemConversion: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "1" else field
            }

        var recvBigUnitNum: Double? =0.0
        var recvSmallUnitNum: Double? =0.0

        var actualBigUnitNum: Double? =0.0
        var actualSmallUnitNum: Double? = 0.0


    }

}



