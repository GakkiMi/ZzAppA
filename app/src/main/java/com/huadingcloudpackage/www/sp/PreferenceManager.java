package com.huadingcloudpackage.www.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.huadingcloudpackage.www.http.UrlContent;

/**
 * Created by hongchuanwei .
 * on 2017/2/23
 */
public class PreferenceManager {

    private static PreferenceManager mInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferenceManager() {
    }

    public static PreferenceManager instance() {
        if (mInstance == null) {
            mInstance = new PreferenceManager();
        }
        return mInstance;
    }

    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        if (mSharedPreferences != null) {
            mEditor = mSharedPreferences.edit();
            mEditor.apply();
        }
    }

    /**
     * 保存token
     *
     * @param token :
     */
    public void saveToken(String token) {
        if (mEditor != null) {
            mEditor.putString(Key.TOKEN, token);
            mEditor.commit();
        }
    }

    public String getToken() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.TOKEN, ""); //(测试token)
        }
        return "";
    }

    /**
     * 保存用户名
     *
     * @param exhibiName :
     */
    public void saveExhibiName(String exhibiName) {
        if (mEditor != null) {
            mEditor.putString(Key.EXHIBINAME, exhibiName);
            mEditor.commit();
        }
    }

    public String getExhibiName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.EXHIBINAME, "");
        }
        return "";
    }


    public void saveUniqueId(String token) {
        if (mEditor != null) {
            mEditor.putString(Key.uniqueId, token);
            mEditor.commit();
        }
    }

    public String getUniqueId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.uniqueId, "");
        }
        return "";
    }

    /**
     * @param isfirstin 第一次登录
     */
    public void saveIsFirstIn(boolean isfirstin) {
        if (mEditor != null) {
            mEditor.putBoolean(Key.ISFIRSTIN, isfirstin);
            mEditor.commit();
        }
    }

    public boolean getIsFirstIn() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getBoolean(Key.ISFIRSTIN, true);
        }
        return false;
    }

    /**
     * @param isShoper 是否是店主 ，true:店主 ，false ：非店主(店长)
     */
    public void saveIsShoper(boolean isShoper) {
        if (mEditor != null) {
            mEditor.putBoolean("shoper", isShoper);
            mEditor.commit();
        }
    }

    public boolean getIsShoper() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getBoolean("shoper", false);
        }
        return false;
    }


    /**
     * @param isfirstshowauth_dialog 第一次登录
     */
    public void save_dialog(boolean isfirstshowauth_dialog) {
        if (mEditor != null) {
            mEditor.putBoolean("isDialog", isfirstshowauth_dialog);
            mEditor.commit();
        }
    }

    public boolean is_dialog() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getBoolean("isDialog", true);
        }
        return false;
    }

    /**
     * 保存jpusdID
     *
     * @param jpusdID :
     */
    public void saveJpushId(String jpusdID) {
        if (mEditor != null) {
            mEditor.putString("jpusdID", jpusdID);
            mEditor.commit();
        }
    }

    public String getJpushId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("jpusdID", "");
        }
        return "";
    }


    /**
     * 保存用户头像url
     *
     * @param
     */
    public void saveUserIcon(String user_icon) {
        if (mEditor != null) {
            mEditor.putString("user_icon", user_icon);
            mEditor.commit();
        }
    }

    public String getUserIcon() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("user_icon", "");
        }
        return "";
    }

    /**
     * 纬度
     *
     * @param lat
     */
    public void saveLat(String lat) {
        if (mEditor != null) {
            mEditor.putString("lat", lat);
            mEditor.commit();
        }
    }

    public String getLat() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("lat", "0");
        }
        return "";
    }

    /**
     * 经度
     *
     * @param lng
     */
    public void saveLng(String lng) {
        if (mEditor != null) {
            mEditor.putString("lng", lng);
            mEditor.commit();
        }
    }

    public String getLng() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("lng", "0");
        }
        return "";
    }

    /**
     * 保存地址
     *
     * @param ：
     */
    public void saveAddress(String address) {
        if (mEditor != null) {
            mEditor.putString("address", address);
            mEditor.commit();
        }
    }

    public String getAddress() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("address", "");
        }
        return "";
    }

    /**
     * 保存国家
     *
     * @param ：
     */
    public void saveCountry(String country) {
        if (mEditor != null) {
            mEditor.putString("country", country);
            mEditor.commit();
        }
    }

    public String getCountry() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("country", "");
        }
        return "";
    }

    /**
     * 保存省份
     *
     * @param ：
     */
    public void saveProvince(String province) {
        if (mEditor != null) {
            mEditor.putString("province", province);
            mEditor.commit();
        }
    }

    public String getProvince() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("province", "");
        }
        return "";
    }

    /**
     * 保存地址
     *
     * @param ：
     */
    public void saveCity(String city) {
        if (mEditor != null) {
            mEditor.putString("city", city);
            mEditor.commit();
        }
    }

    public String getCity() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("city", "");
        }
        return "";
    }

    public void saveSearchHistory(String token) {
        if (mEditor != null) {
            mEditor.putString("searchhistory", token);
            mEditor.commit();
        }
    }

    public String getSearchHistory() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("searchhistory", "");
        }
        return "";
    }

    /**
     * @param ：
     */
    public void saveUserId(String useid) {
        if (mEditor != null) {
            mEditor.putString(Key.USEID, useid);
            mEditor.commit();
        }
    }

    public String getUserId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.USEID, "");
        }
        return "";
    }

    public void savestoreId(String storeId) {
        if (mEditor != null) {
            mEditor.putString("storeId", storeId);
            mEditor.commit();
        }
    }

    public String getstoreId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("storeId", "");
        }
        return "";
    }

    public void saveexhibiId(String exhibiId) {
        if (mEditor != null) {
            mEditor.putString("exhibiId", exhibiId);
            mEditor.commit();
        }
    }

    public String getexhibiId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("exhibiId", "");
        }
        return "";
    }

    /**
     * @param ：
     */
    public void savePayPwd(String useid) {
        if (mEditor != null) {
            mEditor.putString("pwd", useid);
            mEditor.commit();
        }
    }

    public String getPayPwd() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("pwd", "");
        }
        return "";
    }

    /**
     * @param ：
     */
    public void saveShopkeeper(String useid) {
        if (mEditor != null) {
            mEditor.putString(Key.SHOPKEEPER, useid);
            mEditor.commit();
        }
    }

    public String getShopkeeper() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.SHOPKEEPER, "");
        }
        return "";
    }

    /**
     * @param ：
     */
    public void saveSex(String sex) {
        if (mEditor != null) {
            mEditor.putString("sex", sex);
            mEditor.commit();
        }
    }

    public String getSex() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("sex", "");
        }
        return "";
    }


    public void savewxappid(String sex) {
        if (mEditor != null) {
            mEditor.putString("bind", sex);
            mEditor.commit();
        }
    }

    public String getwxappid() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("bind", "");
        }
        return "";
    }

    /**
     * 电话
     *
     * @param phone
     */
    public void savePhoneNum(String phone) {
        if (mEditor != null) {
            mEditor.putString(Key.PHONE, phone);
            mEditor.commit();
        }
    }

    public String getPhoneNum() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.PHONE, "");
        }
        return "";
    }

    /**
     * 支付密码
     *
     * @param paypsw
     */
    public void savePayPsw(String paypsw) {
        if (mEditor != null) {
            mEditor.putString(Key.PAYPSW, paypsw);
            mEditor.commit();
        }
    }

    public String getPayPsw() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.PAYPSW, "");
        }
        return "";
    }

    /**
     * 密码
     *
     * @param pwd
     */
    public void savePwd(String pwd) {
        if (mEditor != null) {
            mEditor.putString(Key.USERPASS, pwd);
            mEditor.commit();
        }
    }

    public String getPwd() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.USERPASS, "");
        }
        return "";
    }

    /**
     * 名字
     *
     * @param name
     */
    public void saveNickName(String name) {
        if (mEditor != null) {
            mEditor.putString("name", name);
            mEditor.commit();
        }
    }

    public String getNickName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("name", "");
        }
        return "";
    }

    /**
     * 职位名称
     *
     * @param positionName
     */
    public void savePositionName(String positionName) {
        if (mEditor != null) {
            mEditor.putString("positionName", positionName);
            mEditor.commit();
        }
    }

    public String getPositionName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("positionName", "");
        }
        return "";
    }

    /**
     * 职位id
     *
     * @param positionId
     */
    public void savePositionID(String positionId) {
        if (mEditor != null) {
            mEditor.putString("positionId", positionId);
            mEditor.commit();
        }
    }

    public String getPositionId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("positionId", "");
        }
        return "";
    }

    /**
     * 部门名称
     *
     * @param deptName
     */
    public void saveDeptName(String deptName) {
        if (mEditor != null) {
            mEditor.putString("deptName", deptName);
            mEditor.commit();
        }
    }

    public String getDeptName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("deptName", "");
        }
        return "";
    }

    /**
     * 部门id
     *
     * @param deptId
     */
    public void saveDeptId(String deptId) {
        if (mEditor != null) {
            mEditor.putString("deptId", deptId);
            mEditor.commit();
        }
    }

    public String getDeptId() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("deptId", "");
        }
        return "";
    }

    /**
     * 部门编号
     *
     * @param deptNo
     */
    public void saveDeptNo(String deptNo) {
        if (mEditor != null) {
            mEditor.putString("deptNo", deptNo);
            mEditor.commit();
        }
    }

    public String getDeptNo() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString("deptNo", "");
        }
        return "";
    }


    /**
     * 保存的key
     *
     * @param key
     */
    public void saveKey(String key) {
        if (mEditor != null) {
            mEditor.putString(Key.KEY, key);
            mEditor.commit();
        }
    }

    public String getKey() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.KEY, "");
        }
        return "";
    }

    /**
     * 移除token
     */
    public void removeToken() {
        if (mEditor != null) {
            mEditor.remove(Key.TOKEN);
            mEditor.commit();
        }
    }

    /**
     * 店铺名称
     *
     * @param shopname
     */
    public void saveShopName(String shopname) {
        if (mEditor != null) {
            mEditor.putString(Key.SHOPNAME, shopname);
            mEditor.commit();
        }
    }

    public String getShopName() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.SHOPNAME, "");
        }
        return "";
    }

    /**
     * 头像
     *
     * @param headpic
     */
    public void saveHeadPic(String headpic) {
        if (mEditor != null) {
            mEditor.putString(Key.HEADPIC, headpic);
            mEditor.commit();
        }
    }

    public String getHeadPic() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.HEADPIC, "");
        }
        return "";
    }

    /**
     * 头像
     *
     * @param cashierSystem
     */
    public void savecashierSystem(String cashierSystem) {
        if (mEditor != null) {
            mEditor.putString(Key.CASHIERSYSTEM, cashierSystem);
            mEditor.commit();
        }
    }

    public String getcashierSystem() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.CASHIERSYSTEM, "");
        }
        return "";
    }

    /**
     * 是否第一次打开
     *
     * @param isFirstOpen
     */
    public void saveIsFirstOpen(boolean isFirstOpen) {
        if (mEditor != null) {
            mEditor.putBoolean(Key.ISFIRSTOPEN, isFirstOpen);
            mEditor.commit();
        }
    }

    public boolean getIsFirstOpen() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getBoolean(Key.ISFIRSTOPEN, true);
        }
        return true;
    }

    /**
     * 邮箱
     */
    public void saveEmail(String email) {
        if (mEditor != null) {
            mEditor.putString(Key.EMAIL, email);
            mEditor.commit();
        }
    }

    public String getEmail() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.EMAIL, "");
        }
        return "";
    }

    /**
     * 微信
     */
    public void saveWeichar(String weichar) {
        if (mEditor != null) {
            mEditor.putString(Key.WEICHAR, weichar);
            mEditor.commit();
        }
    }

    public String getWeichar() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.WEICHAR, "");
        }
        return "";
    }

    /**
     * 生日
     */
    public void saveBirthday(String birthday) {
        if (mEditor != null) {
            mEditor.putString(Key.BIRTHDAY, birthday);
            mEditor.commit();
        }
    }

    public String getBirthday() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(Key.BIRTHDAY, "");
        }
        return "";
    }

    public void saveBaseUrl(String baseUrl) {
        if (mEditor != null) {
            mEditor.putString(Key.BASEURL, baseUrl);
            mEditor.commit();
        }
    }

    public String getBaseUrl() {
        if (mSharedPreferences != null) {
//            Log.i("getBaseUrl","----------baseUrl："+ mSharedPreferences.getString(Key.BASEURL, UrlContent.INITBASEURL));
            return mSharedPreferences.getString(Key.BASEURL, UrlContent.INITBASEURL);
        }
        return "";
    }

    //存储隐私政策是否已被同意
    public void savePolicyIsAgree(boolean policyIsAgree) {
        if (mEditor != null) {
            mEditor.putBoolean("policyIsAgree", policyIsAgree);
            mEditor.commit();
        }
    }

    //获取存储隐私政策是否已被同意 默认为false
    public boolean getPolicyIsAgree() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getBoolean("policyIsAgree", false);
        }
        return false;
    }


    public interface Key {
        String BIRTHDAY = "birthday";
        String WEICHAR = "weichar";
        String EMAIL = "email";
        String USERNAME = "username";
        String PHONE = "phone";
        String PAYPSW = "paypsw";
        String RESPONSE = "response";
        String TOKEN = "token";//保存的本地token
        String uniqueId = "unique_id";//保存的本地token
        String SHOPORDERID = "shopOrderId";
        String KEY = "key";
        String USEID = "user_id";
        String ISFIRSTIN = "isFirstIn";//第一次登录
        String LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String SHOPKEEPER = "shopkeeper";
        String SHOPNAME = "shopname";
        String HEADPIC = "headpic";
        String CASHIERSYSTEM = "cashierSystem";
        String ISFIRSTOPEN = "isFirstOpen";
        String EXHIBINAME = "exhibiName";//个人中心用户名
        String USERPASS = "password";//登录密码
        String BASEURL = "baseurl";
    }
}
