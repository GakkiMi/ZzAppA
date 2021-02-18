package com.huadingcloudpackage.www.util;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.huadingcloudpackage.www.AppApplication;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.bean.AppDateBean;
import com.huadingcloudpackage.www.bean.AppUpdateBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.reciever.AppUpdateReceiver;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.RequestBody;

/**
 * 文 件 名：AppUpdateUtils
 * 描   述：app更新
 */
public class AppUpdateUtils {

    private static NotificationManager notificationManager;
    private static Notification notification; //下载通知进度提示
    private static NotificationCompat.Builder builder;

    private static boolean isForce = false;//是否强制更新  true为强制更新  false为非强制更新
    private static boolean isAutoInstall = true;//下载完成后是否自动安装   true为自动安装  false为手动点击通知进行安装
    private static String apkDownLoadUrl;

    private static int currentProgress = -1;
    private static boolean isDownloading = false;

    private static final String TAG = "AppUpdateUtils";

    public static ObservableEmitter<Integer> myEmitter;
    public static Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            myEmitter = emitter;
        }
    }).debounce(5, TimeUnit.SECONDS);


    /**
     * @param context
     * @param activity  在Activity权限的回调
     * @param fragment  在Fragment权限的回调
     * @param isShowTip 是否需要提示最新版本
     */
    public static void appCheckUpdate(final Context context, final FragmentActivity activity,
                                      final Fragment fragment, final boolean isShowTip) {


        AppUpdateReceiver.setReceiverImp(new AppUpdateReceiver.AppUpdateReceiverImp() {
            @Override
            public void appUpdateFail() {
                //下载失败用户点击通知进行的操作
                startDownApk();
            }
        });

        if (isIsDownloading()) {
            T.showToastyCenter(AppApplication.instance, "正在下载中，可在通知栏查看");
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("channel", "0");
        String json = JsonUtils.toJsonString(params);
        RequestBody body = RequestBody.create(UrlContent.JSON, json);

        OkGo.<String>post(UrlContent.BBGX)
                .tag(TAG)
                .upRequestBody(body)
                .execute(new DialogCallback(context, false) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String haha = "{\"msg\":\"操作成功\",\"code\":200,\"data\":{\"updateDescription\":\"1、优化api接口。\\r\\n2、添加使用demo演示。\\r\\n3、新增自定义更新服务API接口\",\"versionSize\":\"8.3M\",\"versionName\":\"1.0.2\",\"versionCode\":\"2\",\"versionUrl\":\"http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk\"}}";

                        AppUpdateBean baseBean = JsonUtils.parse(response.body(), AppUpdateBean.class);
                        if (baseBean.getCode() != 200) {
                            if (isShowTip) {
                                T.showToastyCenter(AppApplication.instance, baseBean.getMsg());
                            }
                            return;
                        }
                        AppDateBean appDateBean = baseBean.getData();

                        isForce = appDateBean.getForceUpdate() == 0 ? false : true;
                        String versionName = "v" + appDateBean.getVersionName();
                        String updateLog = appDateBean.getUpdateDescription();
                        String apkUrl = appDateBean.getVersionUrl();

                        /** 新版本 **/
                        int newVersionCode = appDateBean.getVersionCode();
                        long currentVersionCode = MyUtils.getAppVersionCode(context);
                        if (newVersionCode > currentVersionCode) {
                            String content = "";

                            if (TextUtils.isEmpty(updateLog)) {
                                content = "报货端" + versionName;
                            } else {
                                content = "报货端" + versionName + "新功能：\n" + updateLog;
                            }


                            int x = content.indexOf(versionName);
                            SpannableString spannableString = new SpannableString(content);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#084D8E")),
                                    x, x + versionName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            CommomDialog tipDialog = new CommomDialog(context);
                            tipDialog.setTitle("版本更新");

                            tipDialog.setContent(spannableString);
                            tipDialog.setPositiveButton("立即更新");
                            tipDialog.setNegativeButton("以后再说");
                            tipDialog.setPositiveColor("#084D8E");
                            tipDialog.setNegativeColor("#999999");
                            tipDialog.setNegativeButtonVisible(isForce ? View.GONE : View.VISIBLE);
                            tipDialog.setCancelable(isForce ? false : true);
                            tipDialog.setCanceledOnTouchOutside(isForce ? false : true);
                            tipDialog.setOnCloseListener((dialog, confirm) -> {
                                if (confirm) {
                                    //String url = "http://60.28.125.129/f1.market.xiaomi.com/download/AppStore/0ff41344f280f40c83a1bbf7f14279fb6542ebd2a/com.sina.weibo.apk";
                                    //String url = "http://mobile.d.appchina.com/McDonald/r/6715583/com.tmall.wireless.vapk";
                                    //String url = "http://mobile.d.appchina.com/McDonald/r/6716633/com.smile.gifmaker.vapk";
//                                    String url = "http://60.30.247.13:7777/dfzb.apk";
//                                    apkDownLoadUrl = url;
                                    apkDownLoadUrl = apkUrl;
                                    checkPermission(activity);
                                }
                            });
                            tipDialog.show();
                            tipDialog.getContentTxt().setGravity(TextUtils.isEmpty(updateLog) ? Gravity.CENTER : Gravity.LEFT);
                            tipDialog.getContentTxt().setLineSpacing(0f, 1.5f);
                            tipDialog.getContentTxt().setTextSize(15f);
                            tipDialog.getTitleTxt().setTextSize(18f);
                            tipDialog.getSubmitTxt().setTextSize(18f);
                            tipDialog.getSubmitTxt().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            tipDialog.getCancelTxt().setTextSize(18f);
                            tipDialog.getCancelTxt().setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        } else {
                            if (isShowTip) {
                                T.showToastyCenter(AppApplication.instance, "当前已是最新版本");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (isShowTip) {
                            T.showToastyCenter(AppApplication.instance, response.message());
                        }
                    }
                });
    }

    /**
     * 检查更新所需权限
     */
    private static void checkPermission(FragmentActivity activity) {
        PermissionX.init(activity)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason(new ExplainReasonCallback() {
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList) {
//                        PermissionRationaleDialog dialog = new PermissionRationaleDialog(context);
//                        dialog.setMessage("app更新需要存储权限功能");
//                        dialog.setPermission(deniedList);
//                        scope.showRequestReasonDialog(dialog);
                        scope.showRequestReasonDialog(deniedList, "app更新需要存储权限功能", "去授权", "拒绝");
                    }
                }).onForwardToSettings(new ForwardToSettingsCallback() {
            @Override
            public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                scope.showForwardToSettingsDialog(deniedList, "请至' 设置 -> 权限 -> 存储 '中打开该权限", "去开启", "取消");
            }
        }).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (allGranted) {
                    startDownApk();
                    Log.e("App", "----------可以下载了");
                } else {
                    T.showToastyCenter(AppApplication.instance, "该权限被拒绝，应用无法更新");
                }
            }
        });
    }


    private static void startDownApk() {
        downLoadApk();
        initNotification();
    }

    /**
     * 此方法为一个断点续传方法
     */
    private static void downLoadApk() {
        String apkPath = AppApplication.instance.getExternalFilesDir("apk") + "/huading.apk";

        //断点续传
        FileDownloader.getImpl().create(apkDownLoadUrl)
                .setPath(apkPath)
                //.setForceReDownload(true)//是否强制重新下载
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----------pending");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----------connected");
                        observable.subscribe(integer -> {
                            //此方法为断点续传方法
                            if (isDownloading) {//之后当apk正在下载中才会触发此操作
                                Log.i(TAG, "--------5s内进度没有变化，则重新调一遍下载方法，当前进度卡在" + integer + "%");
                                downLoadApk();
                            }
                        });
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int progress = (int) ((double) soFarBytes / (double) totalBytes * 100);
                        Log.i(TAG, "----------progress" + progress);
                        isDownloading = true;

                        if (currentProgress != progress) {
                            currentProgress = progress;
                            notifyNotification(progress);
                        }

                        myEmitter.onNext(progress);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "----------blockComplete");
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                        Log.e(TAG, "----------retry");
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG, "----------completed");
                        //下载完成进行相关逻辑操作
                        isDownloading = false;

                        String path = task.getTargetFilePath();
                        File file = new File(path);
                        Log.e(TAG, "----------apk Path：" + path);
                        successNotification();
                        installAPK(file);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----------paused");
                        isDownloading = false;
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        //下载异常进行相关提示操作
                        isDownloading = false;
                        //下载失败
                        T.showToastyCenter(AppApplication.instance, "下载失败，查看通知栏重新更新");
                        Log.e(TAG, "----------download apk failed" + e.toString());
                        failNotification();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        isDownloading = false;
                        Log.e(TAG, "----------warn");
                    }
                }).start();
    }


    /**
     * 初始化通知
     */
    public static void initNotification() {
        T.showToastyCenter(AppApplication.instance, "可在通知栏查看更新");
        String channelId = "111";
        String channelName = "更新";
        String channelDescription = "更新app";
        notificationManager = (NotificationManager) AppApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(AppApplication.instance, channelId)
                .setContentTitle("正在更新") //设置通知标题
                .setSmallIcon(R.mipmap.app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(AppApplication.instance.getResources(), R.mipmap.app_logo)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS) //设置通知的提醒方式： 呼吸灯
                .setContentText("下载进度:" + "0%")
                .setTicker("开始为您更新")
                .setOngoing(true)//设置通知是否可以滑动取消  false为可以取消 true不可滑动取消
                .setAutoCancel(false)//是否自动取消   true自动消失 false不消失
                .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                .setProgress(100, 0, false)
                .setWhen(System.currentTimeMillis());


        //通知8.0适配
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_LOW);
            channel.setBypassDnd(true);//设置可以绕过请勿打扰模式
            channel.canBypassDnd();//可否绕过请勿打扰模式
            channel.setDescription(channelDescription);
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            channel.shouldShowLights();//是否会闪光
            channel.enableLights(true);//闪光
            //指定闪光时的灯光颜色，为了兼容低版本在上面builder上通过setLights方法设置了
            //channel.setLightColor(Color.RED);
            channel.canShowBadge();//桌面launcher消息角标
            channel.enableVibration(true);//是否允许震动
            //震动模式，第一次100ms，第二次100ms，第三次200ms，为了兼容低版本在上面builder上设置了
            //channel.setVibrationPattern(new long[]{100,100,200});
            channel.getAudioAttributes();//获取系统通知响铃声音的配置
            channel.getGroup();//获取通知渠道组
            //绑定通知渠道
            notificationManager.createNotificationChannel(channel);
        }

        notification = builder.build();//构建通知对象
        notificationManager.notify(1, notification);
    }

    public static void notifyNotification(final int progress) {
        builder.setProgress(100, progress, false);
        builder.setContentText("已下载:" + progress + "%");
        notification = builder.build();
        notificationManager.notify(1, notification);
    }


    /**
     * 当下载失败时，设置一个重新下载的意图，可让用户进行重新下载。
     */
    public static void failNotification() {
        builder.setContentTitle("下载失败")
                .setProgress(0, 0, false);

        //下面两行代码有先后执行顺序
        builder.setContentText("点击重新下载")
                .setAutoCancel(false)//是否自动取消   true自动消失 false不消失
                .setOngoing(true);//设置通知是否可以滑动取消  false为可以取消 true不可滑动取消

        Intent intent = new Intent(AppApplication.instance, AppUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AppApplication.instance, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        notification = builder.build();
        notificationManager.notify(1, notification);
    }


    /**
     * 下载成功的通知
     */
    public static void successNotification() {
        builder.setContentTitle("下载完成")
                .setProgress(0, 0, false);

        if (isAutoInstall) {//如果为自动安装
            //下面两行代码有先后执行顺序
            builder.setContentText("即将安装")
                    .setAutoCancel(true)//是否自动取消   true自动消失 false不消失
                    .setOngoing(false);//设置通知是否可以滑动取消  false为可以取消 true不可滑动取消

            notification = builder.build();
            notificationManager.notify(1, notification);
            notificationManager.cancel(1);//取消通知
        } else {//如果为手动点击安装
            builder.setContentText("点击安装")
                    .setOngoing(true)//设置通知是否可以滑动取消  false为可以取消 true不可滑动取消
                    .setAutoCancel(false);//是否自动取消   true自动消失 false不消失
        }
    }


    /**
     * 点击安装代码块
     */
    public static void installAPK(File apkFile) {
        if (!apkFile.exists()) {
            T.showToastyCenter(AppApplication.instance, "apk不存在!");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (apkFile.getName().endsWith(".apk")) {
            //安装完成后，启动app（源码中少了这句话）
            try {
                //兼容7.0
                Uri uri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 适配Android 7系统版本
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    uri = FileProvider.getUriForFile(AppApplication.instance, AppApplication.instance.getPackageName() + ".fileprovider", apkFile);//通过FileProvider创建一个content类型的Uri
                } else {
                    uri = Uri.fromFile(apkFile);
                }
                intent.setDataAndType(uri, "application/vnd.android.package-archive"); // 对应apk类型
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            T.showToastyCenter(AppApplication.instance, "不是apk文件!");
        }

        if (isAutoInstall) {//自动安装
            AppApplication.instance.startActivity(intent);
        } else {//手动安装
            PendingIntent pi = PendingIntent.getActivity(AppApplication.instance, 0, intent, 0);
            builder.setContentIntent(pi);
            builder.setAutoCancel(true);
            notification = builder.build();
            notificationManager.cancel(1);//取消通知
            notificationManager.notify(1, notification);
        }
    }


    /**
     * 当前是否正在下载
     *
     * @return
     */
    public static boolean isIsDownloading() {
        return isDownloading;
    }


}



