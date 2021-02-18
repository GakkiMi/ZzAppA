package com.huadingcloudpackage.www.activity.search;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.goods.GoodsDetailActivity;
import com.huadingcloudpackage.www.activity.gouwuche.GouWuCheActivity;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.GoodsSpecDetail;
import com.huadingcloudpackage.www.bean.SearchResultBean;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.LogUtilsApp;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.recycleViewResult)
    RecyclerView recycleViewResult;

    @BindView(R.id.recycleViewHistory)
    RecyclerView recycleViewHistory;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_delete_history)
    ImageView iv_delete_history;
    @BindView(R.id.edit_search)
    EditText edit_search;
    @BindView(R.id.ll_history)
    LinearLayout ll_history;
    @BindView(R.id.ll_shuaixuan_tiaojian)
    LinearLayout ll_shuaixuan_tiaojian;

    @BindView(R.id.tv_search)
    TextView tv_search;

    //筛选
    @BindView(R.id.tv_zonghe_paixu)
    TextView tv_zonghe_paixu;

    @BindView(R.id.tv_jiage)
    TextView tv_jiage;

    @BindView(R.id.tv_zuixin_shangjia)
    TextView tv_zuixin_shangjia;

    @BindView(R.id.ll_jiage)
    LinearLayout ll_jiage;

    @BindView(R.id.ll_zuixin)
    LinearLayout ll_zuixin;
    @BindView(R.id.iv_jiage_select)
    ImageView iv_jiage_select;
    @BindView(R.id.iv_zuixin_select)
    ImageView iv_zuixin_select;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_gouwuche)
    ImageView iv_gouwuche;
    @BindView(R.id.tv_total)
    TextView tv_total;

    private int listPosition = -1;  //商品全局定位
    private int listItemGoodsNum = -1;       //商品全局数量
    private int itemGoodsSpecPosition = -1;  //二级商品全局规格定位
    private String goodsNameStr;
    private CommentAdapter<SearchResultBean.Item> commentAdapter;
    private CommentAdapter<String> commentAdapterHistory;
    private List<SearchResultBean.Item> data = new ArrayList<>();
    private CommentAdapter<GoodsSpecDetail.Item> specAdapter;
    private int page = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tv_zonghe_paixu.setOnClickListener(v -> {
            tv_zonghe_paixu.setTextColor(Color.parseColor("#084D8E"));
            tv_jiage.setTextColor(Color.parseColor("#999999"));
            tv_zuixin_shangjia.setTextColor(Color.parseColor("#999999"));

            iv_jiage_select.setImageResource(R.mipmap.icon_xu_defult);
            iv_zuixin_select.setImageResource(R.mipmap.icon_xu_defult);
            priceSign = "";
            releaseTimeSign = "";
            search(page);
        });

        iv_gouwuche.setOnClickListener(v -> {
            startActivity(GouWuCheActivity.class);
        });

        ll_jiage.setOnClickListener(v -> {
            tv_zonghe_paixu.setTextColor(Color.parseColor("#999999"));
            tv_jiage.setTextColor(Color.parseColor("#084D8E"));
            tv_zuixin_shangjia.setTextColor(Color.parseColor("#999999"));

            iv_zuixin_select.setImageResource(R.mipmap.icon_xu_defult);

            if (priceSign.equals("1") || priceSign.equals("")) {
                iv_jiage_select.setImageResource(R.mipmap.icon_xu_top_bottom);
                priceSign = "2";
            } else {
                iv_jiage_select.setImageResource(R.mipmap.icon_xu_bottom_top);
                priceSign = "1";
            }
            releaseTimeSign = "";
            search(page);

        });

        ll_zuixin.setOnClickListener(v -> {
            tv_zonghe_paixu.setTextColor(Color.parseColor("#999999"));
            tv_jiage.setTextColor(Color.parseColor("#999999"));
            tv_zuixin_shangjia.setTextColor(Color.parseColor("#084D8E"));
            iv_jiage_select.setImageResource(R.mipmap.icon_xu_defult);
            if (releaseTimeSign.equals("1") || releaseTimeSign.equals("")) {
                iv_zuixin_select.setImageResource(R.mipmap.icon_xu_top_bottom);
                releaseTimeSign = "0";

            } else {
                iv_zuixin_select.setImageResource(R.mipmap.icon_xu_bottom_top);
                releaseTimeSign = "1";
            }

            globalMap = new HashMap<>();
            globalMap.put("onTimeSign", releaseTimeSign);
            priceSign = "";
            search(page);

        });


        initAdapter();
        recycleViewResult.setLayoutManager(new GridLayoutManager(this, 2));
        RvSpaceItemDecoration rvSpaceItemDecoration = new RvSpaceItemDecoration(this, 10);
        recycleViewResult.addItemDecoration(rvSpaceItemDecoration);
        recycleViewResult.setAdapter(commentAdapter);

        iv_back.setOnClickListener(v -> {
            if (isFastClick()) {
                return;
            }
            finish();
        });
        iv_delete_history.setOnClickListener(v -> {
            mHistoryList.clear();
            commentAdapterHistory.notifyDataSetChanged();
            PreferenceManager.instance().saveSearchHistory("");
        });

        adapterHistory();
        getHistoryList();//获取搜索历史
        recycleViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recycleViewHistory.setAdapter(commentAdapterHistory);

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search = edit_search.getText().toString();

                    if (search.equals("")) {
                        T("请输入搜索内容");
                        return true;
                    }
                    hideSoftKeyboard();
                    page = 1;
                    search(page);
                    if (!mHistoryList.contains(search)) {
                        mHistoryList.add(search);
                    }
                    PreferenceManager.instance().saveSearchHistory(new Gson().toJson(mHistoryList));
//                    getHistoryList();
                }

                return false;
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = edit_search.getText().toString();
                if (search.equals("")) {
                    T("请输入搜索内容");
                    return;
                }
                hideSoftKeyboard();
                page = 1;
                search(page);
                if (!mHistoryList.contains(search)) {
                    mHistoryList.add(search);
                }
                PreferenceManager.instance().saveSearchHistory(new Gson().toJson(mHistoryList));
            }
        });


        View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_empty_view, null);
        TextView tvNoData = view.findViewById(R.id.tv_empty);
        tvNoData.setText("未找到结果...");
        commentAdapter.setEmptyView(view);


        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                search(page + 1);
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                search(1);
            }
        });

    }


    String search = "";
    private List<String> mHistoryList = new ArrayList<>();

    private void getHistoryList() {
        LogUtilsApp.d("历史记录:" + PreferenceManager.instance().getSearchHistory());
        if (!PreferenceManager.instance().getSearchHistory().equals("")) {
            ll_history.setVisibility(View.VISIBLE);
            mHistoryList = JsonUtils.parseArray(PreferenceManager.instance().getSearchHistory(), new TypeToken<List<String>>() {
            }.getType());
            Collections.reverse(mHistoryList);
            if (mHistoryList.size() >= 7) {
                mHistoryList = mHistoryList.subList(0, 6);
            }
            commentAdapterHistory.setNewInstance(mHistoryList);
        } else {
            ll_history.setVisibility(View.GONE);
        }
    }


    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    private AlertDialog dialog;
    private int specPosition = 0;
    private String goodsId = null;
    private List<GoodsSpecDetail.Item> specList = new ArrayList<>();

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
                                if (dialog == null || !dialog.isShowing()) {
                                    showDailog(bean);
                                } else {
                                    specList.clear();
                                    specList.addAll(bean.getData());
                                    showDailog(null);
                                }
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


    private void showDailog(GoodsSpecDetail bean) {
        if (dialog == null) {
            initDialog();
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }

        if (bean != null) {
            //数据不为null时，修改商品名，数量，等信息
            specList.clear();
            specList.addAll(bean.getData());
            TextView goodsName = dialog.findViewById(R.id.tv_goodsName);
            goodsName.setText(goodsNameStr);
        }
        initDialogDetail();
    }

    private ImageView add, less, close, goods_add, goods_less;
    private TextView tv_price, tv_num, tv_spec, tv_goods_price, tv_goods_spec, tv_goods_num, tv_storeNum;

    private void initDialogDetail() {
        GoodsSpecDetail.Item spec = specList.get(specPosition);
        boolean isWhole = spec.getIsWhole().equals("0");
        dialog.findViewById(R.id.rl_split).setVisibility(isWhole ? View.VISIBLE : View.GONE);
        specAdapter.notifyDataSetChanged();
        HashMap<String, Object> params = new HashMap<>();
        params.put("goodsId", specList.get(specPosition).getGoodsId());
        params.put("specKeyId", specList.get(specPosition).getId());
        int specRatio = spec.getSpecRatio();
        int goodsNum = spec.getGoodsNum();
        tv_storeNum.setText("库存:" + spec.getStoreCount());
        add.setOnClickListener(v -> {  //增加商品数量
            if (specList.get(specPosition).getStoreCount() <= 0) {
                T.showToastyCenter(this, "库存不足");
                return;
            }
            int num = goodsNum + specRatio;
            if (num / specRatio > spec.getStoreCount()) {
                T.showToastyCenter(this, "超出库存");
                return;
            }
            params.put("goodsNum", num);
            changeNumForCart(params);
        });


        less.setOnClickListener(v -> {
            //减少商品数量
            int num = goodsNum - specRatio;
            params.put("goodsNum", num);
            changeNumForCart(params);
        });

        goods_add.setOnClickListener(v -> {  //增加商品数量
            if (spec.getStoreCount() <= 0) {
                T.showToastyCenter(this, "库存不足");
                return;
            }
            int num = goodsNum + 1;
            if (num / spec.getSpecRatio() > spec.getStoreCount()) {
                T.showToastyCenter(this, "超出库存");
            }
            params.put("goodsNum", num);
            changeNumForCart(params);
        });

        goods_less.setOnClickListener(v -> {
            //减少商品数量
            //减少商品数量
            int num = goodsNum - 1;
            params.put("goodsNum", num);
            changeNumForCart(params);
        });


        if (isWhole) {//判断是否拆零，0拆零/1不拆零并隐藏拆零规格
            if (goodsNum < specRatio) {  //商品数量小于倍数
                less.setVisibility(View.GONE);
                tv_num.setVisibility(View.GONE);
                if (goodsNum == 0) {
                    tv_goods_num.setVisibility(View.GONE);
                    goods_less.setVisibility(View.GONE);
                } else {
                    tv_goods_num.setVisibility(View.VISIBLE);
                    goods_less.setVisibility(View.VISIBLE);
                }
                tv_goods_num.setText(spec.getGoodsNum() + "");
                tv_num.setText("0");
            }

            if (goodsNum >= specRatio) {
                tv_num.setVisibility(View.VISIBLE);
                less.setVisibility(View.VISIBLE);
                tv_num.setText(String.valueOf(goodsNum / specRatio));
                int n = goodsNum % specRatio;
                tv_goods_num.setText(n + "");
                tv_goods_num.setVisibility(n == 0 ? View.GONE : View.VISIBLE);
                goods_less.setVisibility(n == 0 ? View.GONE : View.VISIBLE);
            }

        } else {
            //不拆零
            if (goodsNum / specRatio > 0) {
                tv_num.setVisibility(View.VISIBLE);
                less.setVisibility(View.VISIBLE);
                tv_num.setText(String.valueOf(goodsNum / specRatio));
            } else {
                tv_num.setVisibility(View.GONE);
                less.setVisibility(View.GONE);
            }
        }
        tv_price.setText(spec.getShopPrice() + "");
        tv_spec.setText("/" + spec.getSpecKey());
        tv_goods_price.setText(spec.getGoodsSpecPrice() + "");
        tv_goods_spec.setText("/" + spec.getGoodsSpecKey());

    }


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
                        showDailog(null);
                    }
                });
            }
        };
    }


    private void initDialog() {
        //初始化dialog
        ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();//实现自适应布局
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_spce_detail, null);
        tv_storeNum = view.findViewById(R.id.tv_storeNum);

        add = view.findViewById(R.id.iv_add);
        less = view.findViewById(R.id.iv_less);
        close = view.findViewById(R.id.iv_close);
        goods_add = view.findViewById(R.id.iv_goods_add);
        goods_less = view.findViewById(R.id.iv_goods_less);
        tv_price = view.findViewById(R.id.tv_price);
        tv_num = view.findViewById(R.id.tv_num);
        tv_spec = view.findViewById(R.id.tv_spec);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);
        tv_goods_spec = view.findViewById(R.id.tv_goods_spec);
        tv_goods_num = view.findViewById(R.id.tv_goods_num);
        close.setOnClickListener(v -> {
            dialog.dismiss();
            int num = 0;
            for (GoodsSpecDetail.Item item : specList) {
                num += item.getGoodsNum();
            }
            listItemGoodsNum = num;
            notifyData();
        });

        initSpecAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(spanLayoutManager);
        recyclerView.setAdapter(specAdapter);
        dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = ActionBar.LayoutParams.MATCH_PARENT;//设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setWindowAnimations(R.style.mypopwindow_anim_style);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                specPosition = 0;
            }
        });
    }


    /**
     * 修改刷新列表
     */
    private void notifyData() {
        SearchResultBean.Item item = data.get(listPosition);
        item.setCarCount(listItemGoodsNum);
        data.set(listPosition, item);
        commentAdapter.notifyDataSetChanged();
    }


    /**
     * 修改商品在购物车的数量
     */
    private void changeNumForCart(Map map) {
        showLoading();
        OkGo.<String>post(UrlContent.CHANGE_NUM_FOR_CART)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(map)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                if (dialog == null || !dialog.isShowing()) {
                                    notifyData();
                                } else {
                                    getGoodsSpecDetails(goodsId);
                                }
                                MainEvent event = new MainEvent(0);
                                EventBus.getDefault().post(event);
                                requestCartTotal();
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


    private void initAdapter() {
        commentAdapter = new CommentAdapter<SearchResultBean.Item>(R.layout.item_search_list, data) {
            @Override
            public void setViewData(BaseViewHolder holder, SearchResultBean.Item item, int position) {
                ImageView ivGoodsImg = holder.getView(R.id.iv_goods_img);
                TextView tv_empty = holder.findView(R.id.tv_empty);
                double storeCount = item.getStoreCount();
                if (storeCount > 0) {
                    tv_empty.setVisibility(View.GONE);
                } else {
                    tv_empty.setVisibility(View.VISIBLE);
                }

                if (!item.isSpecFlag()) {  //判断商品是否有多个规格，单规格商品只显示加减和数量，多规格商品显示"选择规格"
                    holder.setVisible(R.id.ll_single_num, true);
                    holder.setVisible(R.id.rl_mult_spce_num, false);
                    if (item.getCarCount() <= 0) {
                        holder.setVisible(R.id.iv_less, false);
                        holder.setVisible(R.id.tv_num, false);
                    } else {
                        holder.setVisible(R.id.iv_less, true);
                        holder.setVisible(R.id.tv_num, true);
                        holder.setText(R.id.tv_num, item.getCarCount() / item.getSpecRatio() + "");
                    }
                } else {
                    if (item.getCarCount() <= 0) {
                        holder.setVisible(R.id.tv_mult_spce_num, false);
                    } else {
                        holder.setVisible(R.id.tv_mult_spce_num, true);
                        holder.setText(R.id.tv_mult_spce_num, item.getCarCount() + "");
                    }
                    holder.setVisible(R.id.ll_single_num, false);
                    holder.setVisible(R.id.rl_mult_spce_num, true);
                }

                /**
                 * 设置图片圆角1
                 */
//                RoundedCornersTransform transform = new RoundedCornersTransform(mContext, dp2px(10));
//                transform.setNeedCorner(true, true, false, false);
//                RequestOptions options = new RequestOptions().transform(transform);
//                Glide.with(mContext).asBitmap().load(item.getThumbnailImageUrl()).apply(options).into(ivGoodsImg);
                GlideUtils.showRoundBorderGildeImg(getContext(), item.getImg(), ivGoodsImg, 0f, Color.parseColor("#80000000"), 20, 12);
                holder.setText(R.id.tv_goods_title, item.getGoodsName());
                holder.setText(R.id.tv_goods_price, changTVsize("" + item.getShopPrice()));
            }

            @Override
            public void setEvent(BaseViewHolder holder, SearchResultBean.Item item, int position) {

                //规格选择
                holder.getView(R.id.rl_mult_spce_num).setOnClickListener(v -> {
                    listPosition = position;
                    goodsNameStr = item.getGoodsName();
                    goodsId = item.getId();
                    getGoodsSpecDetails(item.getId());
                });


                holder.getView(R.id.iv_add).setOnClickListener(v -> {

                    int num = item.getCarCount() + item.getSpecRatio();
                    if (num / item.getSpecRatio() > Double.valueOf(item.getStoreCount())) {
                        T.showToastyCenter(getContext(), "超出库存");
                        return;
                    }
                    listPosition = position;
                    listItemGoodsNum = num;
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num);
                    changeNumForCart(params);
                });

                holder.getView(R.id.iv_less).setOnClickListener(v -> {

                    listPosition = position;
                    int num = item.getCarCount() - item.getSpecRatio();
                    listItemGoodsNum = num;
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num);
                    changeNumForCart(params);
                });

                holder.getView(R.id.ll_colection_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("goods_id", item.getId());
                        startActivity(GoodsDetailActivity.class, bundle);
                    }
                });


            }
        };

    }


    private void adapterHistory() {

        commentAdapterHistory = new CommentAdapter<String>(R.layout.item_history_layout, null) {
            @Override
            public void setViewData(BaseViewHolder holder, String item, int position) {
                holder.setText(R.id.item_tv_search_content, item);
            }

            @Override
            public void setEvent(BaseViewHolder holder, String item, int position) {
                holder.getView(R.id.item_iv_error).setOnClickListener(v -> {
                    if (mHistoryList.contains(item)) {
                        mHistoryList.remove(item);
                        commentAdapterHistory.notifyDataSetChanged();
                        PreferenceManager.instance().saveSearchHistory(new Gson().toJson(mHistoryList));

                    }
                });

                holder.getView(R.id.item_tv_search_content).setOnClickListener(v -> {
                    search = item;
                    page = 1;
                    search(page);
                    edit_search.setText(item);
                    if (!mHistoryList.contains(item)) {
                        mHistoryList.add(item);
                    }
                    PreferenceManager.instance().saveSearchHistory(new Gson().toJson(mHistoryList));
                });
            }
        };

    }

    String priceSign = ""; //商品价格升降序标识：1升序 2 降序
    String releaseTimeSign = ""; //商品上架时间升降序标识：1升序 2 降序
    HashMap<String, Object> globalMap;

    /**
     * 移动端APP搜索框
     */
    private void search(int p) {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        if (globalMap != null) {
            params.putAll(globalMap);
        }
        params.put("name", search);
        params.put("pageSize", "10");
        params.put("pageNum", p + "");
        OkGo.<String>post(UrlContent.GETAPPGOODSBYNAME)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                page = p;
                                ll_history.setVisibility(View.GONE);
                                SearchResultBean bean = JsonUtils.parse(response.body(), SearchResultBean.class);
                                if (p == 1) {
                                    data.clear();
                                }
                                data.addAll(bean.getData().getList());
                                commentAdapter.notifyDataSetChanged();
                                recycleViewResult.setVisibility(View.VISIBLE);
                                if (data.size() > 0) {
                                    recycleViewResult.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPageBg));
                                    ll_shuaixuan_tiaojian.setVisibility(View.VISIBLE);
                                } else {
                                    View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_empty_view, null);
                                    recycleViewResult.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                                    TextView tvNoData = view.findViewById(R.id.tv_empty);
                                    tvNoData.setText("未找到结果...");
                                    commentAdapter.setEmptyView(view);
                                    ll_shuaixuan_tiaojian.setVisibility(View.GONE);
                                }
                            }
                        });


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                        smartRefreshLayout.finishLoadMore();
                        smartRefreshLayout.finishRefresh();
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
    private void requestCartTotal() {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        OkGo.<String>post(UrlContent.CART_TOTAL)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body());
                                    int num = jsonObject.getInt("data");
                                    tv_total.setVisibility(num > 0 ? View.VISIBLE : View.INVISIBLE);
                                    tv_total.setText(num + "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
     * 展示和刷dialog,当bean为null则刷新
     *
     * @param bean
     */
/*
    private void showDailog(GoodsSpecDetail bean) {
        if (dialog == null) {
            //初始化dialog
            ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .build();//实现自适应布局
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_spce_detail, null);
            ImageView add = view.findViewById(R.id.iv_add);
            ImageView less = view.findViewById(R.id.iv_less);
            ImageView close = view.findViewById(R.id.iv_close);
            add.setOnClickListener(v -> {  //增加商品数量
                HashMap<String, Object> params = new HashMap<>();
                params.put("goodsId", specList.get(specPosition).getGoodsId());
                params.put("specKeyId", specList.get(specPosition).getId());
                params.put("goodsNum", specList.get(specPosition).getGoodsNum() + 1);
                changeNumForCart(params);
            });
            less.setOnClickListener(v -> {
                //减少商品数量
                HashMap<String, Object> params = new HashMap<>();
                params.put("goodsId", specList.get(specPosition).getGoodsId());
                params.put("specKeyId", specList.get(specPosition).getId());
                params.put("goodsNum", Integer.valueOf(specList.get(specPosition).getGoodsNum()).intValue() - 1);
                changeNumForCart(params);
            });
            close.setOnClickListener(v -> {
                dialog.dismiss();
            });

            initSpecAdapter();
            RecyclerView recyclerView = view.findViewById(R.id.recycleView);
            recyclerView.setLayoutManager(spanLayoutManager);
            recyclerView.setAdapter(specAdapter);
            dialog = new AlertDialog.Builder(this).setView(view).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = ActionBar.LayoutParams.MATCH_PARENT;//设置宽度
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().setWindowAnimations(R.style.mypopwindow_anim_style);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    specPosition = 0;
                }
            });
        }
        dialog.show();
        if (bean != null) {
            //数据不为null时，修改商品名，数量，等信息
            specList.clear();
            specList.addAll(bean.getData());
            TextView goodsName = dialog.findViewById(R.id.tv_goodsName);
            goodsName.setText(goodsNameStr);
        }
        specAdapter.notifyDataSetChanged();
        GoodsSpecDetail.Item spec = specList.get(specPosition);
        TextView tv_price = dialog.findViewById(R.id.tv_price);
        TextView tv_num = dialog.findViewById(R.id.tv_num);
        TextView tv_spec = dialog.findViewById(R.id.tv_spec);
        tv_price.setText(spec.getShopPrice() + "");
        tv_spec.setText(spec.getSpecKey());
        tv_num.setText(spec.getGoodsNum() + "");
        int num = spec.getGoodsNum();
        if (num == 0) {
            tv_num.setVisibility(View.GONE);
            dialog.findViewById(R.id.iv_less).setVisibility(View.GONE);
        } else {
            tv_num.setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.iv_less).setVisibility(View.VISIBLE);
        }
    }
*/


/*
    private void initSpecAdapter() {
        specAdapter = new CommentAdapter<GoodsSpecDetail.Item>(R.layout.item_spce, specList) {
            @Override
            public void setViewData(BaseViewHolder holder, GoodsSpecDetail.Item item, int position) {
                TextView goodsName = holder.findView(R.id.tv_spec_name);
                goodsName.setText(item.getSpecName());
                if (position == specPosition) {
                    goodsName.setBackground(getResources().getDrawable(R.drawable.shape_new_bule_10dp));
                    goodsName.setTextColor(getResources().getColor(R.color.white));
                } else {
                    goodsName.setBackground(getResources().getDrawable(R.drawable.shape_new_grey_10dp));
                    goodsName.setTextColor(getResources().getColor(R.color.black999));
                }
            }

            @Override
            public void setEvent(BaseViewHolder holder, GoodsSpecDetail.Item item, int position) {
                holder.findView(R.id.tv_spec_name).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        specPosition = position;
                        showDailog(null);
                    }
                });
            }
        };
    }
*/


}
