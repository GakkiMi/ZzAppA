package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：LogisticsInfoBean
 * 描   述：TODO
 */
class LogisticsInfoBean {

    /**
    {
    "msg":":"操作成功",
    "code":":200,
    "data":":{}
     */

    var msg: String? = ""
    var code: Int? = -1
    var data: LogisticsInfoData? = null


    class LogisticsInfoData {

        /**
        "driver":{},
        "list":":[],
        "order":{}
         */


        var driver: LogisticsInfoDriver? = null
        var list: List<LogisticsInfoList>? = null
        var order: LogisticsInfoOrder? = null


    }


    class LogisticsInfoDriver {

        /**
        "address":":"暂无最新地址",
        "deliveryAddress":":"河南省开封市杞县开封市杞县银河路欣泰百货五楼",
        "name":":"李目永",
        "tel":":"18258201193"
         */


        var address: String? = ""
        var deliveryAddress: String? = ""
        var name: String? = ""
        var tel: String? = ""

    }

    class LogisticsInfoList {

        /**
        "createMan":":"胡延涛",
        "createTime":":"2020-09-20 03:23:53",
        "orderRemark":":"分配中",
        "orderStatus":":"5",
        "id":":324429
         */


        var createMan: String? = ""
        var createTime: String? = ""
        var orderRemark: String? = ""
        var orderStatus: String? = ""
        var id: String? = ""
    }


    class LogisticsInfoOrder {
        /**
        "assignCarNumber":":"浙A6R9J3",
        "consignee":":"张鹏飞",
        "consignorLon":":"113.900183",
        "frozenProducts":":0,
        "orderRemark":":"送货上门，代收运费40元，客服收",
        "assignDriverPhone":":"18258201193",
        "homoeothermy":":0,
        "warehouse":":"普洛斯",
        "realCount":":6,
        "customerName":":"焖菜青年开封杞县银河路店",
        "freshKeeping":":0,
        "consignorLat":":"34.699987",
        "consignorAddress":":"河南省郑州市中牟县祥达路普洛斯郑州物流园A区A-2仓库",
        "assignDriver":":"李目永",
        "consigneeLon":":"114.774148",
        "consigneePhone":":"18238222885",
        "consigneeLat":":"34.548457",
        "invoiceNo":":"LP2020091900220",
        "openDate":":"2020-09-16 16:46:51",
        "customerNo":":"OCK00008556",
        "consigneeAddress":":"河南省开封市杞县开封市杞县银河路欣泰百货五楼"
         */

        var assignCarNumber: String? = ""
        var consignee: String? = ""
        var consignorLon: String? = ""
        var frozenProducts: String? = ""
        var orderRemark: String? = ""
        var assignDriverPhone: String? = ""
        var homoeothermy: String? = ""
        var warehouse: String? = ""
        var realCount: String? = ""
        var customerName: String? = ""
        var freshKeeping: String? = ""
        var consignorLat: String? = ""
        var consignorAddress: String? = ""
        var assignDriver: String? = ""
        var consigneeLon: String? = ""
        var consigneePhone: String? = ""
        var consigneeLat: String? = ""
        var invoiceNo: String? = ""
        var openDate: String? = ""
        var customerNo: String? = ""
        var consigneeAddress: String? = ""


    }

}

