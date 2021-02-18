package com.huadingcloudpackage.www.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.MainActivity;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.login.LoginActivity;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.eventbus.EventUnLogin;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.NewLoadingDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.qincis.slideback.SlideBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Unbinder;

;


/**
 * 文 件 名: BaseActivity
 * 创 建 人: hcw
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 日志输出标志getSupportActionBar().
     **/
    public Context mContext;
    protected InputMethodManager inputMethodManager;

    public ComTools comTools;

    public int page = 1;

    private LinearLayout rootLayout;
    public HttpParams params = new HttpParams();

    private TextView title;
    private ImageView back;
    private ImageView imgRight;
    public TextView tvRight;
    private LinearLayout toolbar;
    private View viewDivider;//分割线
    private View viewRed;
    private Unbinder mUnbinder;

    public boolean isHideStatusBar = true;//是否隐藏状态栏  默认为true隐藏 | 不隐藏状态栏为false
    private CommomDialog reSignInDialog;//重新登录的dialog


    @SuppressLint("SourceLockedOrientationActivity")
    @Subscribe
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕为竖屏, 设置后会锁定方向

        setContentView(getLayoutResId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        comTools = new ComTools();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        StatusBarUtils.setTranslucentStatus(this);
        loadingDialog = new NewLoadingDialog(this);
        //开启滑动返回
        SlideBack.create().attachToActivity(this);
        //eventbus使用
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView(savedInstanceState);
    }

    public NewLoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new NewLoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dissMissLoading() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
        }
    }

    /**
     * 返回当前activity对应的布局id
     *
     * @return
     */
    public abstract int getLayoutResId();


    /**
     * 初始化view:findViewById操
     */
    public abstract void initView(Bundle savedInstanceState);


    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 800;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        OkGo.getInstance().cancelTag(this);
        mUnbinder.unbind();
    }

    /**
     * 重写getResources()方法，让APP的字体不受系统设置字体大小影响
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 吐司
     *
     * @param msg 内容
     */
    public void T(CharSequence msg) {
        T.show(mContext, msg);
    }


    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 关软键盘
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void initToolbar() {

        back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
        imgRight = findViewById(R.id.img_right);
        tvRight = findViewById(R.id.tv_right);
        toolbar = findViewById(R.id.toolbar);
        viewDivider = findViewById(R.id.toolbar_divider);
        viewRed = findViewById(R.id.view_red_);
    }

    /**
     * 设置界面标题
     *
     * @param msg
     */

    protected void setTitle(String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }

    /**
     * 返回
     */
    protected void setBackBtn() {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFastClick()) {
                        return;
                    }
                    finish();
                }
            });
        } else {

        }

    }


    protected void setBackClickListener(View.OnClickListener l) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(l);
        } else {

        }

    }

    /**
     * 设置 toolbar 背景色
     *
     * @param bgColor       背景色
     * @param isShowDivider 是否显示分割线
     */
    protected void setToolbarBgColor(int bgColor, boolean isShowDivider) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(bgColor);
            viewDivider.setVisibility(isShowDivider ? View.VISIBLE : View.GONE);
            back.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        }
    }


    protected void setWhiteTheme() {
        if (toolbar != null) {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
            title.setTextColor(getResources().getColor(R.color.black000));
//            StatusBarUtils.changeStatusBarTextColor(this, true);
            back.setImageResource(R.mipmap.return_black_left);
        }

    }

    protected void setNoTitle() {
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);

        } else {

        }

    }

    protected void setImgLeft(int img, View.OnClickListener l) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setImageResource(img);
            back.setOnClickListener(l);
        } else {

        }

    }

    protected void setImgRight(int img, View.OnClickListener l) {
        if (imgRight != null) {
            imgRight.setVisibility(View.VISIBLE);
            imgRight.setImageResource(img);
            imgRight.setOnClickListener(l);
        } else {

        }

    }

    protected void setImgRightVisibility(int visibility) {
        if (imgRight != null) {
            imgRight.setVisibility(visibility);

        } else {

        }

    }

    protected void setViewRed(int visibility) {
        if (viewRed != null) {
            viewRed.setVisibility(visibility);

        } else {

        }

    }

    protected void setImgRightClickListener(View.OnClickListener l) {
        if (imgRight != null) {
            imgRight.setVisibility(View.VISIBLE);
            imgRight.setOnClickListener(l);
        } else {

        }

    }

    protected void setTvRightClickListener(String text, View.OnClickListener l) {
        if (tvRight != null) {
            tvRight.setText(text);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setOnClickListener(l);
        } else {

        }

    }

    protected void setTvRightTextColor(int color) {
        if (tvRight != null) {
            tvRight.setTextColor(color);
        }

    }


    @Subscribe
    public void getLoginOutEvent(EventUnLogin eventUnLogin) {
        Log.i("BaseActivtiy", "------------EventUnLogin重新登陆----" + getClass().getName());

        showDialog();
    }

    private void showDialog() {
        if (reSignInDialog != null) {
            if (!reSignInDialog.isShowing()) {
                reSignInDialog.show();
            }
            return;
        }
        reSignInDialog = new CommomDialog(this, "登录失效，请重新登陆!", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {

                if (confirm) {
                    PreferenceManager.instance().saveToken("");
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        reSignInDialog.setCanceledOnTouchOutside(false);
        reSignInDialog.setTitle("提示");
        reSignInDialog.setPositiveButton("重新登录");
        reSignInDialog.setNegativeButtonVisible(View.GONE);
        reSignInDialog.show();
        reSignInDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // you code here;
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * @param editText :设置输入框两位小数
     */
    public void setEditTextTwoPoint(EditText editText) {
//        editText.setFilters(new InputFilter[]{inputFilter});
        TextWatcher textWatcher = new TextWatcher() {//核销价格设置
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果第一个数字为0，第二个不为点，就不允许输入
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
                //如果第一为点，直接显示0.
                if (s.toString().startsWith(".")) {
                    editText.setText("0.");
                    editText.setSelection(2);
                    return;
                }
                //限制输入小数位数(2位)
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 2 + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }


    public InputFilter inputFilter = new InputFilter() {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = pattern.matcher(source);
            if (!matcher.find()) {
                return null;
            } else {
//                T("");
                return "";
            }

        }

    };



}
