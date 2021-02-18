package com.huadingcloudpackage.www.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 文 件 名：DefaultAddressBean
 * 描   述：默认收货地址实体
 */
data class DefaultAddressBean(
    /**
    {
    "msg": "操作成功",
    "code": 200,
    "data": {
    "id": 7,
    "memberId": 2,
    "memberCellphone": null,
    "receiveMember": "朱晨曦",
    "receiveCellphone": "15569852369",
    "province": "辽宁省",
    "city": "沈阳市",
    "county": "沈河区",
    "address": "786945",
    "remark": null,
    "addressStatus": "1",
    "creater": null,
    "createTime": 1596510655817,
    "modify": null,
    "modifyTime": null
    }
    }
     */

    var msg: String?,
    var code: Int?,
    var data: AddressListBean.DataBean?//used

)

//暂时没用到
data class AddressDataBean(

    var id: Int?,
    var memberId: Int?,
    var memberCellphone: String?,
    var receiveMember: String?,
    var receiveCellphone: String?,
    var province: String?,
    var city: String?,
    var county: String?,
    var address: String?,
    var remark: String?,
    var addressStatus: String?,
    var creater: String?,
    var createTime: String?,
    var modify: String?,
    var modifyTime: String?

)






