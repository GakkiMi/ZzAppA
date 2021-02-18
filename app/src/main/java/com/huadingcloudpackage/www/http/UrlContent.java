package com.huadingcloudpackage.www.http;

import com.huadingcloudpackage.www.sp.PreferenceManager;

import okhttp3.MediaType;

public class UrlContent {


    public static String INITBASEURL = "https://hd-api.huadingyun.cn/";//正式
//    public static String INITBASEURL = "http://172.16.20.81:9527/";//本地
//    public static String INITBASEURL = "http://172.16.20.213:9527/";//本地
//    public static String INITBASEURL = "http://121.89.202.8:15009/";//测试

    //使用这个可以再app通过隐藏设置进行自行改动BASEURL
    public static String BASE_URL = PreferenceManager.instance().getBaseUrl();//本地

    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //隐私政策
    public static String YSZC = "http://zong.huadingyun.cn/about/yinsi.html";
    //服务协议
    public static String FWXY = "http://39.99.150.215/privacy.html";
    //版本更新


    /**
     * app更新接口
     */
    public static String BBGX = BASE_URL + "files/version/appExamineVersion";

    /**
     * 登录
     */
    public static String LOGIN = BASE_URL + "baseauth/login";//登录
    /**
     * 首页商品分类
     */
    public static String SELECTCATEBYLEVEL = BASE_URL + "basegoods/AppGoods/getCategorys";//商品一级分类列表
    public static String GOODS_SPEC_DETAIL = BASE_URL + "basegoods/AppGoods/getGoodsSpec?id=";//商品一规格详情
    public static String GETAPPGOODS = BASE_URL + "basegoods/AppGoods/getGoodsList";//商品二级分类列表
    public static String GETAPPGOODSBYGOODSID = BASE_URL + "basegoods/AppGoods/getByGoodsId?id=";//移动端APP商品详情页面
    public static String GETPHONE = BASE_URL + "businessorder/orderPhone/getPhone";//客服电话
    public static String GETAPPGOODSBYNAME = BASE_URL + "basegoods/AppGoods/getByName";//移动端APP搜索框
    public static String GOODS_COLLECT = BASE_URL + "basegoods/goodsCollection/isCollection";//收藏
    public static String COLLECT_CANCEL = BASE_URL + "goods/collect/remove";//取消收藏
    public static String COLLECT_DELETE = BASE_URL + "basegoods/goodsCollection/delect";//删除收藏
    public static String APPORDERACTTEST = BASE_URL + "order/order/appOrderActTest";//立即购买
    public static String CHANGE_NUM_FOR_CART = BASE_URL + "basegoods/cart/insert";//修改商品在购物车数量


    /**
     * 购物车
     */
    public static String CART_GOODS_COUNT = BASE_URL + "order/cart/countGoodsTotalNum";//获取购物车商品数量
    public static String CART_LIST = BASE_URL + "basegoods/cart/list";//APP用户购物车列表
    public static String UPDATE_CART = BASE_URL + "basegoods/cart/updateCart";//APP用户购物车商品数量修改
    public static String DELETE_GOODS = BASE_URL + "basegoods/cart/delCart";//APP用户购物车商品删除
    public static String CART_TOTAL = BASE_URL + "basegoods/cart/getGoodsNum";

    public static String UPDATE_GOODS_SELECTED = BASE_URL + "order/cart/updateSelected";//购物车商品选中状态修改
    public static String UPDATE_GOODS_GROUP_SELECTED = BASE_URL + "basegoods/cart/updSelect";//购物车商品选中分组/全选选中状态

    public static String ADD_GOODS_TO_CART = BASE_URL + "order/cart/addGoodsToCart";//向购物车添加商品
    public static String BUY_GOODS_NOW = BASE_URL + "order/order/appOrderActTest";//立即购买
    public static String CHECK_CART_GOODS_STOCK_NUM = BASE_URL + "basegoods/cart/checkStorage";//购物车结算校验库存接口


    /**
     * 订单结算
     */
    public static String GET_DEFALULT_ADDRESS = BASE_URL + "businessorder/busAppOrder/getShoppingAddress";//获取默认地址
    public static String SETTLE_ORDER = BASE_URL + "businessorder/busAppOrder/settle";//订单结算商品列表 get
    public static String CREATE_ORDER = BASE_URL + "businessorder/busAppOrder/createOrder";//创建订单商品 post


    /**
     * 订单支付
     */
    //获取可用余额
    public static String GET_EXHIBI_WALLET = BASE_URL + "businessorder/customerWallet/getCustomerWalletByOn";//获取可用余额
    //是否设置支付密码
    public static String SELECT_SETTING_PASSWORD = BASE_URL + "businessorder/customerPassword/selectSettingPassword";//是否设置支付密码

    public static String VERIFY_PAY_PASSWORD = BASE_URL + "store/exhibiPassword/verifyPayPassword";//校验支付密码
    public static String APP_PAY_MONEY = BASE_URL + "businessorder/busAppOrder/payOrder";//订单支付接口 post


    public static String ADDRESSLIST = BASE_URL + "businessorder/busAppOrder/getShoppingAddress";//地址列表
    public static String SAVEADDRESS = BASE_URL + "order/order/address/saveAdress";//添加收货地址
    public static String DELADDRESS = BASE_URL + "order/order/address/deletAddress";//删除收货地址
    public static String UPTDEFA = BASE_URL + "order/order/address/uptDefa";//设置默认收货地址
    public static String UPTADDRESS = BASE_URL + "order/order/address/uptAddress";//修改收货地址
    public static String EXHIBITIONUPTADDRESS = BASE_URL + "order/order/address/exhibitionAddressData";//查询收货地址
    public static String MINEDATA = BASE_URL + "baseconsumer/customer/selectAppDetailed";//我的主页数据


    public static String RESETPWD = BASE_URL + "baseauth/sysUser/resetPwd";//修改手机密码
    public static String SETPWD = BASE_URL + "businessorder/customerPassword/addOrUptPassword";//修改支付密码
    public static String VERIFYPHONECODE = BASE_URL + "businessorder/customerPassword/verifyPhoneAndCode";//获取验证码


    public static String FORGERPASSCODE = BASE_URL + "auth/forgetPassword";//忘记密码


    public static String WALLETWATE = BASE_URL + "businessorder/customerWalletLog/walletWater";//我的账户
    public static String COLLECTLIST = BASE_URL + "basegoods/goodsCollection/getCollection";//我的收藏


    /**
     * 报货订单
     */
    public static String BUSAPP_ORDER_LIST = BASE_URL + "businessorder/busAppOrder/getList";//订单列表
    public static String BUSAPP_ORDER_DETAILS = BASE_URL + "businessorder/busAppOrder/selOrderDetails";//订单详细
    public static String BUSAPP_ORDER_CANCEL = BASE_URL + "businessorder/busAppOrder/cancelOrder";//取消订单
    public static String BUSAPP_ORDER_DELETE = BASE_URL + "businessorder/busAppOrder/delOrder";//删除订单
    public static String BUSAPP_ORDER_REMIND = BASE_URL + "businessorder/busAppOrder/remindOrder";//订单提醒发货
    public static String BUSAPP_ORDER_CONFIRM = BASE_URL + "businessorder/busAppOrder/confirmOrder";//确认收货
    public static String BUSAPP_ORDER_CREATE_DIFF = BASE_URL + "businessorder/busAppOrder/createDifference";//创建售后单
    public static String BUSAPP_ORDER_AGAIN = BASE_URL + "businessorder/busAppOrder/again";//再来一单


    public static String UPLOAD_IMG_SINGLE = "https://hd-api.huadingyun.cn/file/file/uploadimg";//单张上传图片
    public static String UPLOAD_IMG_MUTIBLE = BASE_URL + "file/file/uploadImgList";//多张图片上传

    public static String GET_LOGISTICS_INFO = BASE_URL + "businessorder/busAppOrder/getLogisticsInfo";//获取物流信息


    /**
     * 售后差异单
     */
    public static String DIFF_ORDER_LIST = BASE_URL + "businessorder/busAppOrder/differenceOrderList";//售后单列表
    public static String DIFF_ORDER_DETAILS = BASE_URL + "businessorder/busAppOrder/differenceDetail";//售后单详细
    public static String DIFF_ORDER_CANCEL = BASE_URL + "businessorder/busAppOrder/cancelDifferenceOrder";//取消售后单
    public static String DIFF_ORDER_DELETE = BASE_URL + "businessorder/busAppOrder/delDifferenceOrder";//删除售后单
    public static String DIFF_ORDER_PAY = BASE_URL + "businessorder/busAppOrder/payDifferenceOrder";//售后单支付


    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }
}

