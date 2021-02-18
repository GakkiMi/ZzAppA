package com.huadingcloudpackage.www.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.huadingcloudpackage.www.R;

/**
 * @name 工程名：工具箱
 * @class 包名：com.yaosuce.yaosuwang.util
 * @describe 描述：
 * @anthor 作者：whffi QQ:84569945
 * @time 时间：2017/6/10 20:18
 * @change 变更：
 * @chang 时间：
 * @class 描述：
 */

public class NewLoadingDialog extends Dialog {


    private ImageView img;


    private boolean Cancelable = false;//不可关闭




    private float alphaa = 1.0f; //背景透明度

    public NewLoadingDialog(Context context) {

        super(context, R.style.loadingdialog);


    }

    public NewLoadingDialog(Context context, int theme) {
        super(context, theme);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_loading_dialog);
        initView();

    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(getContext()).load(R.mipmap.loading).apply(options).into(img);


        LinearLayout layout = (LinearLayout) findViewById(R.id.p);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alphaa;
        window.setAttributes(lp);

        setCancelable(true);

    }


    public void setCanClosed(Boolean Cancelable) {
        this.Cancelable = Cancelable;


    }




    public void setAlpha(float alpha) {
        this.alphaa = alpha;
    }

}