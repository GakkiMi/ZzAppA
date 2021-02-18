package com.huadingcloudpackage.www.bean

/**
 * 文 件 名：AppUpdateEntity
 * 描   述：app更新返回的实体
 */
data class AppUpdateBean(

    /**
     * {
    "msg":"操作成功",
    "code":200,
    "data":{
    "updateDescription":"1、优化api接口。2、添加使用demo演示。3、新增自定义更新服务API接口",
    "versionSize":"8.3M",
    "versionName":"1.0.1",
    "versionCode":"1",
    "versionUrl":"http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk"
    }
    }
     */

    var msg: String?,
    var code: Int?,
    var data: AppDateBean?//used

)

data class AppDateBean(

    var updateDescription: String?,//":"1、优化api接口。2、添加使用demo演示。3、新增自定义更新服务API接口",
    var versionSize: String?,//":"8.3M",
    var versionName: String?,//":"1.0.1",
    var versionCode: Int?,//":"1",
    var versionUrl: String?,//":"
    var forceUpdate: Int= null?:0//":"0  否   1是

)
