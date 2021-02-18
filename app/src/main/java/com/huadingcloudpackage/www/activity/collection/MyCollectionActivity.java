package com.huadingcloudpackage.www.activity.collection;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;


import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.goods.GoodsDetailActivity;
import com.huadingcloudpackage.www.activity.gouwuche.GouWuCheActivity;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.GoodsSpecDetail;
import com.huadingcloudpackage.www.bean.MyCollectionBean;
import com.huadingcloudpackage.www.bean.SearchResultBean;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;


/**
 * @author lige
 * 描述：
 */
public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.iv_gouwuche)
    ImageView iv_gouwuche;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_collected)
    RecyclerView rlCollected;
    @BindView(R.id.iv_select)
    CheckBox ivSelect;
    @BindView(R.id.tv_collected_num)
    TextView tvCollectedNum;
    @BindView(R.id.tv_delete)
    ShapeTextView tvDelete;
    @BindView(R.id.ll_collection_select)
    LinearLayout llCollectionSelect;
    @BindView(R.id.tv_num)
    TextView tv_cartTotal;
    private boolean isSelected = false;//控制头部右侧文字按钮（管理--完成）
    private boolean collectSelected = false;//底部全选是否选中
    CommentAdapter<MyCollectionBean.ListBean> commentAdapter;
    public List<MyCollectionBean.ListBean> collectionList;
    private CheckBox ivSelectItem;
    private MyCollectionBean listBean;
    private CommentAdapter<GoodsSpecDetail.Item> specAdapter;
    private int listPosition = -1;  //商品全局定位
    private int listItemGoodsNum = -1;       //商品全局数量
    private int levelTwoItemGoodsSpecPosition = -1;  //二级商品全局规格定位
    private String goodsNameStr;
    List<MyCollectionBean.ListBean> goodsIds = new ArrayList<>();//数据源

    @Override
    public int getLayoutResId() {
        return R.layout.activity_mycollection;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        collectionList = new ArrayList<>();
        initToolbar();
        setBackBtn();
        setTitle("我的收藏");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        tvRight.setText("管理");
        setTvRightClickListener("管理", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectionList != null && collectionList.size() > 0) {
                    if (isSelected) {
                        isSelected = false;
                        llCollectionSelect.setVisibility(View.GONE);
                        commentAdapter.notifyDataSetChanged();
                        tvRight.setText("管理");
                    } else {
                        isSelected = true;
                        llCollectionSelect.setVisibility(View.VISIBLE);
                        commentAdapter.notifyDataSetChanged();
                        tvRight.setText("完成");
                    }
                } else {
                    tvRight.setText("");
                }
                commentAdapter.notifyDataSetChanged();
            }
        });
        setTvRightTextColor(Color.parseColor("#ff084D8E"));

        iv_gouwuche.setOnClickListener(v -> {
            startActivity(GouWuCheActivity.class);
        });

        initAdapter();
        rlCollected.setLayoutManager(new GridLayoutManager(this, 2));
        int spanCount = 2;
        int spacing = 50;
        boolean includeEdge = false;
        RvSpaceItemDecoration rvSpaceItemDecoration = new RvSpaceItemDecoration(this, 10);
        rlCollected.addItemDecoration(rvSpaceItemDecoration);
        rlCollected.setAdapter(commentAdapter);
        // 第一种，直接取消动画
        RecyclerView.ItemAnimator animator = rlCollected.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        // 第二种，设置动画时间为0
        rlCollected.getItemAnimator().setChangeDuration(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getCollectionData();
        requestCartGoodsCount();
    }

    private void initAdapter() {
        commentAdapter = new CommentAdapter<MyCollectionBean.ListBean>(R.layout.item_my_collection, collectionList) {
            @Override
            public void setViewData(BaseViewHolder holder, MyCollectionBean.ListBean item, int position) {
                ImageView ivGoodsImg = holder.getView(R.id.iv_goods_img);
                ivSelectItem = holder.getView(R.id.iv_select_item);
                holder.findView(R.id.tv_empty).setVisibility(item.getStoreCount() == 0 ? View.VISIBLE : View.GONE);
                if (!item.isSpecFlag()) {  //判断商品是否有多个规格，单规格商品只显示加减和数量，多规格商品显示"选择规格"
                    holder.setVisible(R.id.ll_single_num, true);
                    holder.setVisible(R.id.rl_mult_spce_num, false);
                    if (item.getCarCount() <= 0) {
                        holder.setVisible(R.id.iv_less, false);
                        holder.setVisible(R.id.tv_num, false);
                    } else {
                        holder.setVisible(R.id.iv_less, true);
                        holder.setVisible(R.id.tv_num, true);
                        holder.setText(R.id.tv_num, item.getCarCount() + "");
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

                if (item.isChecked()) {
                    ivSelectItem.setChecked(true);
                } else {
                    ivSelectItem.setChecked(false);
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

                if (isSelected) {
                    ivSelectItem.setVisibility(View.VISIBLE);
                } else {
                    ivSelectItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void setEvent(BaseViewHolder holder, MyCollectionBean.ListBean item, int position) {

                //规格选择
                holder.getView(R.id.rl_mult_spce_num).setOnClickListener(v -> {
                    goodsNameStr = item.getGoodsName();
                    goodsId = item.getId();
                    getGoodsSpecDetails(item.getId());
                });


                holder.getView(R.id.iv_add).setOnClickListener(v -> {
                    int num = item.getCarCount() + 1;
                    if (num > Double.valueOf(item.getStoreCount())) {
                        T.showToastyCenter(getContext(), "超出库存");
                        return;
                    }
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num * item.getSpecRatio() + "");
                    changeNumForCart(params);
                });

                holder.getView(R.id.iv_less).setOnClickListener(v -> {
                    int num = item.getGoodsNum() - 1;
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num * item.getSpecRatio() + "");
                    changeNumForCart(params);
                });


                holder.getView(R.id.iv_select_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyCollectionBean.ListBean bean = collectionList.get(position);
                        bean.setChecked(!item.isChecked());
                    }
                });

                holder.getView(R.id.ll_colection_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSelected) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("goods_id", item.getId());
                        startActivity(GoodsDetailActivity.class, bundle);
                    }
                });


            }
        };
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        ImageView ivEmpty = view.findViewById(R.id.iv_empty);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        ivEmpty.setImageResource(R.mipmap.no_collection);
        tvEmpty.setText("您还没有收藏内容哦~");
        commentAdapter.setEmptyView(view);
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
                        tv_cartTotal.setText(goodsTotalCount + "");
                        tv_cartTotal.setVisibility(goodsTotalCount == 0 ? View.INVISIBLE : View.VISIBLE);
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
                                    getCollectionData();
                                } else {
                                    getGoodsSpecDetails(goodsId);
                                }
                                requestCartGoodsCount();
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

    private void initDialog() {
        //初始化dialog
        ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();//实现自适应布局
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_spce_detail, null);
        add = view.findViewById(R.id.iv_add);
        less = view.findViewById(R.id.iv_less);
        tv_storeNum = view.findViewById(R.id.tv_storeNum);
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
            getCollectionData();
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


    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }


    public void setDatasCheck(boolean check) {
        for (MyCollectionBean.ListBean bean : collectionList) {
            bean.setChecked(check);
        }
        commentAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_select, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_select:
                if (collectSelected) {
                    collectSelected = false;
                    setDatasCheck(collectSelected);
                    goodsIds.removeAll(collectionList);
                } else {
                    collectSelected = true;
                    setDatasCheck(collectSelected);
                    goodsIds.addAll(collectionList);
                }
                ivSelect.setChecked(collectSelected);
                break;

            case R.id.tv_delete:
                ArrayList<String> list = new ArrayList<>();
                for (MyCollectionBean.ListBean bean : collectionList) {
                    if (bean.isChecked()) {
                        list.add(bean.getId());
                    }
                }
                collect(list);
                break;
        }
    }


    private void loadData(MyCollectionBean bean) {
        collectionList.clear();
        collectionList.addAll(listBean.getData());
        commentAdapter.notifyDataSetChanged();
        int num = 0;
        for (MyCollectionBean.ListBean b : bean.getData()) {
            num += b.getGoodsNum();
        }

        if (collectionList.size() == 0) {
            llCollectionSelect.setVisibility(View.GONE);
            tvRight.setText("");
        }
    }


    /**
     * 我的收藏
     */
    private void getCollectionData() {
        showLoading();
        OkGo.<String>get(UrlContent.COLLECTLIST)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                listBean = JsonUtils.parse(response.body(), MyCollectionBean.class);
                                loadData(listBean);
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
     * 删除收藏
     */
    private void collect(List<String> list) {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", list);
        OkGo.<String>post(UrlContent.COLLECT_DELETE)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                getCollectionData();
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
}
