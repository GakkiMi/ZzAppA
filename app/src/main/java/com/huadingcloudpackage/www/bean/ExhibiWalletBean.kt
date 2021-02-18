package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：ExhibiWalletBean
 * 描   述：可用余额的实体类
 */
class ExhibiWalletBean {

    /**
    "msg":"操作成功",
    "code":200,
    "data":{
    "walletId":430,
    "rechargeMoney":34846,
    "giveMoney":0,
    "freezMoney":0,
    "money":34846,
    "integral":0,
    "walletStatus":"0",
    "createBy":"",
    "createTime":"2020-11-28 15:38:05",
    "updateBy":"967",
    "updateTime":"2020-12-03 14:35:19",
    "remark":"",
    "customerNo":"KH2020110600005"
    }
     */


    var msg: String? = ""
    var code: Int? = -1
    var data: ExhibiWalletDataBean? = null


    class ExhibiWalletDataBean {
        var walletId: Int?=null
        var rechargeMoney: Double = 0.0
        var giveMoney: Double = 0.0
        var freezMoney: Double = 0.0
        var money: Double = 0.0//used
        var integral: Int = 0
        var walletStatus: String?=null
        var createBy: String?=null
        var createTime: String?=null
        var updateBy: String?=null
        var updateTime: String?=null
        var remark: String?=null
        var customerNo: String?=null

    }
}