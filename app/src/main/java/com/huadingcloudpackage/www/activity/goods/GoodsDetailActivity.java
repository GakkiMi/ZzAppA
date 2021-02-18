package com.huadingcloudpackage.www.activity.goods;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.gouwuche.GouWuCheActivity;
import com.huadingcloudpackage.www.activity.order.ordercommit.OrderCommitActivity;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.GoodsDetailBean;
import com.huadingcloudpackage.www.bean.GoodsSpecDetail;
import com.huadingcloudpackage.www.bean.KefuBean;
import com.huadingcloudpackage.www.eventbus.AHomeEvent;
import com.huadingcloudpackage.www.eventbus.GoodsDetailEvent;
import com.huadingcloudpackage.www.eventbus.GouwucheEvent;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.SoftKeyBoardListener;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.bannerDetail)
    Banner banner;
    @BindView(R.id.tv_add_car)
    TextView tv_add_car;
    @BindView(R.id.tv_buy_now)
    TextView tv_buy_now;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;
    @BindView(R.id.tv_brand)
    TextView tv_brand;//品牌
    @BindView(R.id.tv_shelf_life)
    TextView tv_shelf_life;//保质期
    @BindView(R.id.tv_storage_method)
    TextView tv_storage_method;//储存方式
    @BindView(R.id.tv_kefu)
    TextView tv_kefu;
    @BindView(R.id.tv_gouwuche)
    TextView tvGouwuche;
    @BindView(R.id.tv_gouwuche_bage)
    TextView tvGouwucheBage;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_goods_num)
    TextView tv_goods_num;
    @BindView(R.id.tv_baseSpce)
    TextView tv_baseSpce;
    @BindView(R.id.web_view_detail)
    WebView web_view_detail;
    private String intentPageTag = "";//标识从哪个界面打开的商品详细
    GoodsDetailBean bean;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        Bundle bundle = getIntent().getExtras();
        intentPageTag = bundle.getString("intentPageTag");

        iv_back.setOnClickListener(v -> {
            finish();
        });
        tv_add_car.setOnClickListener(v -> {
            if (isFastClick()) {
                return;
            }
            if (specList.size() == 0) {
                getGoodsSpecDetails(bean.getData().getId());
            } else {
                showDailog();
            }
        });


        tv_buy_now.setOnClickListener(v -> {
            if (isFastClick()) {
                return;
            }
            if (specList.size() == 0) {
                getGoodsSpecDetails(bean.getData().getId());
            } else {
                showDailog();
            }
        });


        setBanner();
        if (getIntent().getExtras() != null) {
            getGoodsDetails(getIntent().getStringExtra("goods_id"));
        }
//        web_view_detail.setWebViewClient(new MyWebViewClient());

        tv_kefu.setOnClickListener(v -> {
            getPhone();
        });

        iv_collect.setOnClickListener(v -> {
            if (getIs_collect == 0) {
                collect(1);
            } else {
                collect(0);
            }

        });
        tvGouwuche.setOnClickListener(v -> {
            //如果当前界面是从GouWuCheActivity/BGouWuCheFragment进来的  再次进入购物车界面直接finish
            if (!TextUtils.isEmpty(intentPageTag)) {
                if (intentPageTag.equals("gouwuche")) {
                    finish();
                    return;
                }
            }
            startActivity(GouWuCheActivity.class);
        });


        softKeylistener = new SoftKeyBoardListener(this);

        softKeylistener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                keyBoardIsShow = true;
            }

            @Override
            public void keyBoardHide(int height) {
                keyBoardIsShow = false;
                if (onDialogClose) {
                    onDialogClose = false;
                    return;
                }
                int num = 0;
                int baseNum = 0;
                int sn = et_num.getText().toString().equals("") ? 0 : Integer.parseInt(et_num.getText().toString());
                int sbn = et_baseNum.getText().toString().equals("") ? 0 : Integer.parseInt(et_baseNum.getText().toString());
                int ratio = specList.get(specPosition).getSpecRatio();
                double storeCount = specList.get(specPosition).getStoreCount();
                if (sn * ratio + sbn > storeCount * ratio) {
                    T.showToastyCenter(GoodsDetailActivity.this, "超出库存");
                    specList.get(specPosition).setGoodsNum(String.valueOf(storeCount * ratio));
                    showDailog();
                    return;
                }
                if (sn * ratio + sbn <= 0) {
                    showDailog();
                    return;
                }
                specList.get(specPosition).setGoodsNum(String.valueOf(sn * ratio + sbn));
                showDailog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCartGoodsCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateBageNum(GoodsDetailEvent event) {
        int tag = event.getTag();
        if (tag == 0) {
            requestCartGoodsCount();
        }
    }


    int getIs_collect = 0;
    String goodsId = "";


    /**
     * 商品详情规格参数
     */
    private void getGoodsSpecDetails(String id) {
        showLoading();
        OkGo.<String>get(UrlContent.GOODS_SPEC_DETAIL + id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                GoodsSpecDetail bean = JsonUtils.parse(response.body(), GoodsSpecDetail.class);
                                specList.clear();
                                specList.addAll(bean.getData());
                                showDailog();
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 请求多规格数量，更新商品列表角标
     */
    private void getGoodsSpecDetailsForUpdateNum(String id) {
        showLoading();
        OkGo.<String>get(UrlContent.GOODS_SPEC_DETAIL + id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                GoodsSpecDetail bean = JsonUtils.parse(response.body(), GoodsSpecDetail.class);
                                List<GoodsSpecDetail.Item> list = bean.getData();
                                int num = 0;
                                for (int i = 0; i < list.size(); i++) {
                                    GoodsSpecDetail.Item item = list.get(i);
                                    num += (item.getGoodsNum() / item.getSpecRatio() + item.getGoodsNum() % item.getSpecRatio());
                                }
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("id", id);
                                map.put("num", num);
                                AHomeEvent event = new AHomeEvent(1);
                                event.setObject(map);
                                EventBus.getDefault().post(event);
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 移动端APP商品详情页面
     */
    private void getGoodsDetails(String id) {
        showLoading();
        OkGo.<String>get(UrlContent.GETAPPGOODSBYGOODSID + id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                bean = JsonUtils.parse(response.body(), GoodsDetailBean.class);
                                setData(bean.getData());
                            }
                        });
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 获取客服电话
     * type: 0 :取消收藏 ， 1：收藏
     */
    private void collect(int type) {
        showLoading();
        RequestBody body = null;
        try {
            JSONObject json = new JSONObject();
            json.put("flag", type);
            json.put("goodsId", bean.getData().getId());
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.GOODS_COLLECT)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                getGoodsDetails(getIntent().getStringExtra("goods_id"));
                            }
                        });
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 获取客服电话
     */
    private void getPhone() {
        showLoading();
        OkGo.<String>post(UrlContent.GETPHONE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                KefuBean kefuBean = JsonUtils.parse(response.body(), KefuBean.class);
                                if (kefuBean.getData() == null || kefuBean.getData().equals("")) {
                                    return;
                                }
                                callPhone(kefuBean.getData());
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    private void setData(GoodsDetailBean.DataBean bean) {
        tv_goods_num.setText("库存:" + bean.getStoreCount());
        tv_price.setText(String.valueOf(bean.getShopPrice()));
        getIs_collect = bean.getFlag();
        tv_baseSpce.setText(bean.getBaseSpecName());
        goodsId = bean.getId();
        if (getIs_collect == 0) {
            iv_collect.setImageResource(R.mipmap.icon_collect_no_check);
        } else {
            iv_collect.setImageResource(R.mipmap.icon_collect_check);
        }

        /*设置数据*/
        tv_goods_name.setText(bean.getGoodsName());
        tv_brand.setText(bean.getBrandName());
        tv_shelf_life.setText(bean.getQualityTerm() + "(月)");
        tv_storage_method.setText(bean.getStorageMode());


//        web_view_detail.loadDataWithBaseURL(null, getHtmlData(bean.getGoodsRemark()), "text/html", "UTF-8", null);

        if (bean.getImgs() != null && bean.getImgs().length > 0) {
            imageList.clear();
            for (int i = 0; i < bean.getImgs().length; i++) {
                imageList.add(bean.getImgs()[i]);
            }
            banner.setImages(imageList);
            banner.start();
        } else {

        }


    }

    //=====================购买弹窗=======================
    Dialog dialog;
    private SoftKeyBoardListener softKeylistener;
    private boolean keyBoardIsShow = false;
    private boolean onDialogClose = false;

    private void showDailog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.dialogFullscreenStyle);
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_car_layout, null, true);
            initSpecAdapter();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(view);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = ActionBar.LayoutParams.MATCH_PARENT;//设置宽度
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setWindowAnimations(R.style.mypopwindow_anim_style);
            rl_base_spce = view.findViewById(R.id.rl_base_spce);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            TextView tv_child_add_car = view.findViewById(R.id.tv_child_add_car);
            TextView tv_buy_now = view.findViewById(R.id.tv_buy_now);
            TextView dialog_tv_goods_name = view.findViewById(R.id.dialog_tv_goods_name);//商品名称
            dialog_tv_goods_name.setText(tv_goods_name.getText().toString().trim());
            jia = view.findViewById(R.id.iv_jia);
            jian = view.findViewById(R.id.iv_jian);
            base_jia = view.findViewById(R.id.iv_base_jia);
            base_jian = view.findViewById(R.id.iv_base_jian);
            et_num = view.findViewById(R.id.et_num);
            et_baseNum = view.findViewById(R.id.et_base_num);
            basePrice = view.findViewById(R.id.tv_dialog_base_price);
            baseSpce = view.findViewById(R.id.tv_dialog_base_spce);
            price = view.findViewById(R.id.tv_dialog_price);
            spce = view.findViewById(R.id.tv_dialog_spce);
            dialog_tv_kucun = view.findViewById(R.id.dialog_tv_kucun);//商品库存
            RecyclerView recycle_rule = view.findViewById(R.id.recycle_rule);
            ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .build();//实现自适应布局
            recycle_rule.setLayoutManager(spanLayoutManager);
            recycle_rule.setAdapter(specAdapter);
            iv_close.setOnClickListener(v -> {
                onDialogClose = true;
                hintKeyboard();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            });
            tv_child_add_car.setOnClickListener(v -> {
                double d = Double.valueOf(specList.get(specPosition).getStoreCount());
                if (d <= 0) {
                    T.showToastyCenter(this, "当前商品库存为0，无法操作");
                    return;
                }
                changeGoodsNum();
            });

            tv_buy_now.setOnClickListener(v -> {
                double d = Double.valueOf(specList.get(specPosition).getStoreCount());
                if (d == 0) {
                    T.showToastyCenter(this, "当前商品库存为0，无法操作");
                    return;
                }
                List<HashMap<String, Object>> list = new ArrayList<>();
                GoodsSpecDetail.Item goods = specList.get(specPosition);
                if (goods.getGoodsNum() <= 0) {
                    T.showToastyCenter(this, "商品不能为空");
                    return;
                }
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                HashMap<String, Object> smallParams = new HashMap<>();
                smallParams.put("goodsId", goods.getGoodsId());
                smallParams.put("cartId", "");
                smallParams.put("specId", goods.getId());
                smallParams.put("goodsNum", goods.getGoodsNum());
                smallParams.put("goodsName", bean.getData().getGoodsName());
                smallParams.put("specRatio", goods.getSpecRatio());
                list.add(smallParams);
                HashMap map = new HashMap();
                map.put("list", list);
                Bundle bundle = new Bundle();
                bundle.putString("data", JsonUtils.toJsonString(map));
                startActivity(OrderCommitActivity.class, bundle);
            });

        }

        if (!dialog.isShowing()) {
            dialog.show();
        }

        GoodsSpecDetail.Item item = specList.get(specPosition);
        boolean isWhole = item.getIsWhole().equals("1");
        jian.setOnClickListener(v -> {
            hintKeyboard();
            if (item.getGoodsNum() - item.getSpecRatio() >= 0) {
                item.setGoodsNum(String.valueOf(item.getGoodsNum() - item.getSpecRatio()));
                specList.set(specPosition, item);
                showDailog();
            }
        });

        jia.setOnClickListener(v -> {
            if (keyBoardIsShow) {
                hintKeyboard();
                return;
            }

            int n = specList.get(specPosition).getGoodsNum() + item.getSpecRatio();
            if (n <= item.getStoreCount() * specList.get(specPosition).getSpecRatio()) {
                item.setGoodsNum(String.valueOf(n));
                specList.set(specPosition, item);
                showDailog();
            } else {
                T.showToastyCenter(this, "超出库存");
            }

        });

        base_jian.setOnClickListener(v -> {
            if (keyBoardIsShow) {
                hintKeyboard();
                return;
            }
            if (item.getGoodsNum() - 1 >= 0) {
                item.setGoodsNum(String.valueOf(item.getGoodsNum() - 1));
                specList.set(specPosition, item);
                showDailog();
            }

        });

        base_jia.setOnClickListener(v -> {
            if (keyBoardIsShow) {
                hintKeyboard();
                return;
            }
            if (item.getGoodsNum() + 1 <= item.getStoreCount() * item.getSpecRatio()) {
                item.setGoodsNum(String.valueOf(item.getGoodsNum() + 1));
                specList.set(specPosition, item);
                showDailog();
            } else {
                T.showToastyCenter(this, "超出库存");
            }
        });


        rl_base_spce.setVisibility(isWhole ? View.GONE : View.VISIBLE);
        if (isWhole) {
            et_num.setText(item.getGoodsNum() / item.getSpecRatio() + "");
        } else {
            et_num.setText(item.getGoodsNum() / item.getSpecRatio() + "");
            et_baseNum.setText(item.getGoodsNum() % item.getSpecRatio() + "");
        }
        price.setText(item.getShopPrice() + "");
        spce.setText("/" + item.getSpecKey());
        baseSpce.setText("/" + item.getGoodsSpecKey());
        basePrice.setText(String.valueOf(item.getGoodsSpecPrice()));
        dialog_tv_kucun.setText("库存: " + item.getStoreCount() + item.getSpecKey());
        specAdapter.notifyDataSetChanged();
    }

    TextView dialog_tv_kucun;

    TextView price;
    TextView spce;
    TextView basePrice;
    TextView baseSpce;
    RelativeLayout rl_base_spce;
    ImageView jia, jian, base_jia, base_jian;
    EditText et_num, et_baseNum;


    private void hintKeyboard() {
        if (keyBoardIsShow) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    /**
     * 请求购物车商品数量  更新底部购物车角标
     */
    public void requestCartGoodsCount() {
        HashMap<String, Object> params = new HashMap<>();
        OkGo.<String>post(UrlContent.CART_TOTAL)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        int goodsTotalCount = 0;//购物车商品总数量
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 200 || baseBean.getCode() == 201) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                goodsTotalCount = jsonObject.getInt("data");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            goodsTotalCount = 0;
                        }
                        setShopCarBageNum(goodsTotalCount);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    private void setShopCarBageNum(int count) {
        tvGouwucheBage.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
        tvGouwucheBage.setText(count + "");

    }


    private void changeGoodsNum() {
        HashMap<String, Object> params = new HashMap<>();
        GoodsSpecDetail.Item item = specList.get(specPosition);
        int num = item.getGoodsNum();
        int ratio = specList.get(specPosition).getSpecRatio();
        if (num > item.getStoreCount() * ratio) {
            T.showToastyCenter(this, "超出库存");
            return;
        }

        if (num == 0) {
            T.showToastyCenter(this, "购买数量不能为0");
            return;
        }
        showLoading();
        params.put("goodsId", item.getGoodsId());
        params.put("specKeyId", item.getId());
        params.put("goodsNum", num + "");
        RequestBody body = RequestBody.create(UrlContent.JSON, String.valueOf(JsonUtils.toJsonString(params)));
        OkGo.<String>post(UrlContent.CHANGE_NUM_FOR_CART)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                //MainActivity中订阅
                                //更新购物车角标
                                EventBus.getDefault().post(new MainEvent(0));
                                //刷新购物车列表
                                EventBus.getDefault().post(new GouwucheEvent(0));
                                requestCartGoodsCount();
                                dialog.dismiss();
                                getGoodsSpecDetailsForUpdateNum(bean.getData().getId());
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });


    }


    private CommentAdapter<GoodsSpecDetail.Item> specAdapter;
    private List<GoodsSpecDetail.Item> specList = new ArrayList<>();
    private int specPosition = 0;

    private void initSpecAdapter() {
        specAdapter = new CommentAdapter<GoodsSpecDetail.Item>(R.layout.item_spce, specList) {
            @Override
            public void setViewData(BaseViewHolder holder, GoodsSpecDetail.Item item, int position) {
                TextView goodsName = holder.findView(R.id.tv_spec_name);
                goodsName.setText(item.getSpecName());
                if (position == specPosition) {
                    goodsName.setBackground(getResources().getDrawable(R.drawable.shape_new_bule_10dp));
                    goodsName.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    goodsName.setBackground(getResources().getDrawable(R.drawable.shape_new_grey_10dp));
                    goodsName.setTextColor(getResources().getColor(R.color.black333));
                }
            }

            @Override
            public void setEvent(BaseViewHolder holder, GoodsSpecDetail.Item item, int position) {
                holder.findView(R.id.tv_spec_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        specPosition = position;
                        showDailog();
                    }
                });
            }
        };
    }



/*
    private void initAdapter() {

        commentAdapterGuiGe = new CommentAdapter<GoodsDetailsBean.DataBean.UnitListBean>(R.layout.item_tv_guige, unitListBeans) {
            @Override
            public void setViewData(BaseViewHolder holder, GoodsDetailsBean.DataBean.UnitListBean item, int position) {
                holder.setText(R.id.item_tv_guige, item.getName());

            }

            @Override
            public void setEvent(BaseViewHolder holder, GoodsDetailsBean.DataBean.UnitListBean item, int position) {
                holder.getView(R.id.item_tv_guige).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isSelect = item.isSelect();
                        if (isSelect) {//被选中的情况下不可再次点击
                            return;
                        }
                        for (GoodsDetailsBean.DataBean.UnitListBean data : unitListBeans) {
                            data.setSelect(false);
                        }
                        item.setSelect(true);
                        notifyDataSetChanged();

                        //================设置数据=====================
                        dialog_tv_kucun.setText("库存:" + item.getStoreCount());
                        dialog_tv_goods_price.setText("¥ " + item.getPrice());
                        myJIaJianView.setNumEt(1);
                        myJIaJianView.maxNumber(new Double(item.getStoreCount()).intValue());

                        unitType = item.getType();
                        storeCount = item.getStoreCount();
                        setBaseInfo(item);
                    }
                });
                holder.getView(R.id.item_tv_guige).setSelected(item.isSelect());
            }
        };
    }
*/


    //=======================banner设置==================
    private List<String> imageList = new ArrayList<>();

    private void setBanner() {
        //设置banner样式(显示圆形指示器)
        //设置指示器位置（指示器居zhong）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setDelayTime(3000);
        banner.setImageLoader(new GlideImageLoader());
    }


    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//            imageView.setImageResource((Integer) path);
            RequestOptions o = new RequestOptions().placeholder(R.mipmap.icon_loading_fail).error(R.mipmap.icon_loading_fail).centerInside();
            Glide.with(context).load((String) path).apply(o).into((ImageView) imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            return new ImageView(context);
        }
    }


    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private class MyWebViewClient extends WebViewClient {

        // 网页加载错误时回调，这个方法会在 onPageFinished 之前调用
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {
//            showError();
        }

        // 开始加载网页
        @Override
        public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
        }

        // 完成加载网页
        @Override
        public void onPageFinished(WebView view, String url) {
//            showComplete();
            web_view_detail.loadUrl("javascript:(function(){" + "document.getElementsByTagName('body')[0].style.height = window.innerHeight+'px';" + "})()");
            super.onPageFinished(view, url);


        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
            // handler.cancel();// Android默认的处理方式
            handler.proceed();// 接受所有网站的证书
            // handleMessage(Message msg);// 进行其他处理

        }

        // 跳转到其他链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            String scheme = Uri.parse(url).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }
            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
                web_view_detail.loadUrl(url);
            }
            // 已经处理该链接请求
            return true;
        }
    }
}
