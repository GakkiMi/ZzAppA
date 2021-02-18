package com.huadingcloudpackage.www.bean


/**
 * 文 件 名：ShoppingCarBean
 * 描   述：购物车最外层实体
 */
data class ShoppingCarBean(

    var msg: String = "",
    var code: Int?,//used
    var totalPrice: Double = 0.0,
    var supplier: List<SupplierBean>?//used


)

/**
 * 描   述：购物车下的供货商实体
 */
data class SupplierBean(

    /**
    "searchValue":null,
    "createBy":"",
    "createTime":"2020-08-14 15:23:36",
    "updateBy":"",
    "updateTime":null,
    "remark":"",
    "params":{},
    "startTime":null,
    "endTime":null,
    "supplierId":82,
    "sysUserId":709,
    "supplierNo":"ErpS20200814eKy11s",
    "supplierName":"供应商01",
    "supplierTel":"17621371915",
    "supplierAddress":null,
    "supplierContacts":null,
    "licensePic":null,
    "licenseNum":null,
    "bankCards":"",
    "zxLinkageId":"6217001180000899115",
    "zxName":null,
    "zxStatus":"0",
    "zxShopNo":null,
    "password":null,
    "storeId":null,
    "supplierType":null,
    "deliverAddress":null,
    "itemIds":null,
    "cartsIds":[{}],
    "rank":null
    //新增的字段
    "supplierFireight"*/


    var searchValue: String?,
    var createBy: String?,
    var createTime: String?,
    var updateBy: String?,
    var updateTime: String?,
    var remark: String?,
    var params: Object?,
    var startTime: String?,
    var endTime: String?,
    var supplierId: Int?,
    var sysUserId: Int?,
    var supplierNo: String,
    var supplierName: String = "",//used
    var supplierTel: String?,
    var supplierAddress: String?,
    var supplierContacts: String?,
    var licensePic: String?,
    var licenseNum: String?,
    var bankCards: String?,
    var zxLinkageId: String?,
    var zxName: String?,
    var zxStatus: String?,
    var zxShopNo: String?,
    var password: String?,
    var storeId: String?,
    var supplierType: String?,
    var deliverAddress: String?,
    var itemIds: String?,
    //购物车商品实体
    var cartsIds: List<GoodsBean>?,//used
    var orderGoodsSupplier: List<GoodsBean>?,
    var supplierFireight: Double = 0.0,
    var rank: String?,

    //自加字段
    var isChecked: Boolean = false

)

/**
 * 描   述：供货商下的商品实体
 */
data class GoodsBean(
    /**
    "searchValue":null,
    "createBy":null,
    "createTime":null,
    "updateBy":null,
    "updateTime":null,
    "remark":null,
    "params":{},
    "startTime":null,
    "endTime":null,
    "cartId":42,
    "userId":52,
    "sessionId":null,
    "goodsId":72,
    "goodsSn":"HDG00000220",
    "goodsName":"电脑",
    "goodsPrice":39.99,
    "shopPrice":null,
    "memberGoodsPrice":null,
    "goodsNum":1,
    "specKey":null,
    "specKeyName":"台",
    "barCode":"",
    "selected":1,
    "addTime":"2020-08-19 00:00:00",
    "promType":null,
    "promId":null,
    "sku":"01040",
    "storeId":220,
    "shopId":null,
    "sgsId":null,
    "itemId":69,
    "thumbnailImageUrl":"http://47.92.11.135:8001/group1/M00/00/80/wKgAtV83O1KAUul6AAAyA_teFVo363_200x150.jpg",
    "item":"台",
    "storeCount":29,
    "itemPrice":39.99,
    "promStatus":"0",
    "district":null,
    "suppliersId":82,
    "itemNo":"HD1597455220358gUp",
    "goodsContent":"",
    "exhibiId":52,
    "exhibiNo":null,
    "unitType":"big",
    "type":null,
    "bigUnitNum":1,
    "bigKeyName":"10*个/箱",
    "bigUnitName":"箱",
    "bigPrice":"120.00",
    "smallPrice":null,
    "bigUnitPrice":"120.00",
    "smallUnitPrice":null,
    "smallUnitName":null,
    "rank":null*/

    var searchValue: String?,
    var createBy: String?,
    var createTime: String?,
    var updateBy: String?,
    var updateTime: String?,
    var remark: String?,
    var params: Object?,
    var startTime: String?,
    var endTime: String?,
    var cartId: Int?,//used
    var userId: Int?,
    var sessionId: Int?,
    var goodsId: Int?,//used
    var goodsSn: String?,
    var goodsName: String?,//used
    var goodsPrice: Double = 0.0,//used
    var shopPrice: Double = 0.0,
    var memberGoodsPrice: Double = 0.0,
    var goodsNum: Int = null ?: 0,//used
    var specKey: String?,
    var specKeyName: String?,//used
    var barCode: String?,
    var selected: Int = 0,//used
    var addTime: String?,
    var promType: String?,
    var promId: String?,
    var sku: String?,
    var storeId: Int?,
    var shopId: Int?,
    var sgsId: Int?,
    var itemId: Int?,
    var thumbnailImageUrl: String = "",//used
    var item: String?,
    var storeCount: Int = 0,//used
    var itemPrice: Double = 0.0,
    var promStatus: String?,
    var district: String?,
    var suppliersId: Int?,
    var itemNo: String?,
    var goodsContent: String?,
    var exhibiId: Int?,
    var exhibiNo: String?,

    var unitType: String?,
    var type: String?,
    var bigUnitNum: Int = null ?: 0,
    var bigKeyName: String?,
    var bigUnitName: String?,
    var bigPrice: String?,
    var smallPrice: String?,
    var bigUnitPrice: String?,
    var smallUnitPrice: String?,
    var smallUnitName: String?,


    var rank: String?
//        var isChecked: Boolean = true

)