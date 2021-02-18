package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：DiffOrderOuterBean
 * 描   述：差异单列表的实体
 */
class DiffOrderOuterBean {


    /**
    "msg:""操作成功",
    "code:"200,
    "data:"{}
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: DiffOrderDataBean? = null

    class DiffOrderDataBean {
        /**
        "total": 14,
        "list": [],
        "pageNum": 1,
        "pageSize": 10,
        "size": 10,
        "startRow": 1,
        "endRow": 10,
        "pages": 2,
        "prePage": 0,
        "nextPage": 2,
        "isFirstPage": true,
        "isLastPage": false,
        "hasPreviousPage": false,
        "hasNextPage": true,
        "navigatePages": 8,
        "navigatepageNums": [1,2],
        "navigateFirstPage": 1,
        "navigateLastPage": 2
         */

        var total: String? = ""
        var list: List<DiffOrderDataList>? = null
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


    class DiffOrderDataList {

        /**
        "orderSonSn": "OSS2020120500016",
        "differenceSn": "CY2020120500003",
        "list": [],
        "orderState": 1,
        "differencesTotalNum": 1,
        "refund": null,
        "payTotalPrice": 100.00
         */

        var orderSonSn: String? = ""
        var differenceSn: String? = ""
        var list: List<DiffOrderGoodsBean>? = null
        var orderState: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "-1" else field
            }
        var differencesTotalNum: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "0" else field
            }
        var refund: String? = ""
        var payTotalPrice: String? = ""
        var isExpand:Boolean=false;

    }

    class DiffOrderGoodsBean {

        /**
        "goodsSn": "SP2020111700002",
        "differenceGoodsNum": -2,
        "goodsName": "测试拆单",
        "goodsItem": "箱",
        "goodsSpecId": "GG2020111700002",
        "goodsPrice": 50.00,
        "goodsNum": 2,
        "goodsImageUrl": "111"
         */
        var goodsSn: String? = ""
        var differenceGoodsNum: String? = ""
        var goodsName: String? = ""
        var goodsItem: String? = ""
        var goodsSpecId: String? = ""
        var goodsPrice: String? = ""
        var goodsNum: String? = ""
        var goodsImageUrl: String? = ""

        var goodsBasicsSpecKey: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "" else field
            }
        var goodsBasicsSpecPrice: String? = ""
        var goodsBasicsSpecName: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "暂无规格" else field
            }
        var isWhole: String? = ""
            //0标识拆零  1不拆零
            get() {
                return if (field.isNullOrEmpty()) "0" else field
            }
        var itemConversion: String? = ""
            get() {
                return if (field.isNullOrEmpty()) "1" else field
            }

    }

}