package com.huadingcloudpackage.www;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.CrashHandler;
import com.huadingcloudpackage.www.util.DisPlayUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.permissionx.guolindev.PermissionX;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class AppApplication extends Application {
    public static AppApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        PreferenceManager.instance().init(this);//初始化sp
        DisPlayUtils.init(this);
        inintOKGO();
        //捕获异常crash，跳转至登陆页
        CrashHandler.getInstance().init(this);

        Context context = getApplicationContext();
// 获取当前包名
        String packageName = context.getPackageName();
// 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
// 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        Bugly.init(context, "7ba5175ad6", false, strategy);

        FileDownloader.setupOnApplicationOnCreate(this);

        //判断是否有读写文件的权限，如果有 则删除已下载的apk
        boolean flag = PermissionX.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (flag) {
            deleteApkFile();
        }

        PreferenceManager.instance().saveBaseUrl(UrlContent.INITBASEURL);

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }

    /**
     * 初始化OKGO
     */
    public void inintOKGO() {

        //----------------------------------------------------------------------------------------//
        //okGo网络框架初始化和全局配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));      //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));//使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));      //使用内存保持cookie，app退出后，cookie消失

        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("token", PreferenceManager.instance().getToken());    //header不支持中文，不允许有特殊字符
        headers.put("userType", "02");    //header不支持中文，不允许有特殊字符
        headers.put("currentUserId", PreferenceManager.instance().getUserId());    //header不支持中文，不允许有特殊字符
        headers.put("storeId", PreferenceManager.instance().getstoreId());    //header不支持中文，不允许有特殊字符
        headers.put("exhibitionId", PreferenceManager.instance().getexhibiId());    //header不支持中文，不允许有特殊字符
        headers.put("iscaptcha", "1");    //header不支持中文，不允许有特殊字符
//        headers.put("exhibiId",PreferenceManager.instance().getexhibiId());    //header不支持中文，不允许有特殊字符


        //设置请求参数
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)               //全局统一缓存模式，默认不使用缓存
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers);                      //全局公共头
//                .addCommonParams(params);                      //全局公共参数
    }


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.line_color, R.color.black333);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    /**
     * 删除已下载的apk
     */
    public void deleteApkFile() {
        String apkFilePath = AppApplication.instance.getExternalFilesDir("apk").getPath();
        Log.i("AppApplication", "--------------apkFilePath:" + apkFilePath);
        File file = new File(apkFilePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null) {
                    Log.i("AppApplication", "--------------无apk文件");
                    return;
                }
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        File apkFile=files[i];
                        Log.i("AppApplication", "--------apkName:"+apkFile.getName()+"---以apk结尾?"+apkFile.getName().endsWith("apk")+"-----全路径:"+apkFile.getAbsolutePath());
                        apkFile.delete();
                    }
                }
            }
        }
    }


}
