package com.huadingcloudpackage.www.activityfragment.orderdeclare;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.goods.GoodsDetailActivity;
import com.huadingcloudpackage.www.activity.order.ordercommit.OrderCommitActivity;
import com.huadingcloudpackage.www.activityfragment.BGouWuCarFragment;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.base.BaseLazyFragment;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.CartListBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.dialog.WarmTipsDialog;
import com.huadingcloudpackage.www.eventbus.AHomeEvent;
import com.huadingcloudpackage.www.eventbus.GouwucheEvent;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.GouwucheAnimator;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.SoftKeyBoardListener;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.ShapeTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

import static android.view.View.GONE;

/**
 * 文 件 名：DecOneMoreOrderFragment
 * 描   述：TODO
 */
public class DecOneMoreOrderFragment extends BaseLazyFragment {

    @BindView(R.id.gouwuche_iv_return)
    ImageView ivReturn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_edit)
    ShapeTextView tvEdit;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.iv_all_select)
    ImageView ivBottomAllSelect;
    @BindView(R.id.tv_all_select)
    TextView tvAllSelect;
    @BindView(R.id.ll_checked)
    LinearLayout llChecked;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_jiesuan)
    ShapeTextView tvJiesuan;
    @BindView(R.id.tv_delete)
    ShapeTextView tvDelete;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.tv_goods_total_count)
    TextView tvGoodsTotalCount;
    @BindView(R.id.rl_no_net)
    RelativeLayout rlNoNet;
    @BindView(R.id.tv_reload)
    ShapeTextView stvReload;

    private String TAG = "BGouWuCarFragment";

    private CommentAdapter<CartListBean.Item> commentAdapter;//购物车列表适配器
    private List<CartListBean.Item> cartList = new ArrayList<>();
    private boolean isEditStatus = false;//false为未编辑ou
    private boolean isBottomButtonSelect = false;//底部的全选按钮  默认为false 未全选
    private CommomDialog goodsCountErrorDialog;//当商品购买数量大于库存量 弹出此dialog
    private boolean isNetError = false;//网络异常的标志
    private boolean isAllSelect = true;
    private GouwucheAnimator animator;
    private SoftKeyBoardListener softKeyBoardListener;
    private int childPosition;
    private int parentPosition;
    private HashMap<String, String> changeParams = new HashMap<>();


    private Bundle mBundle;


    public DecOneMoreOrderFragment() {
    }

    public static DecOneMoreOrderFragment getInstance(Bundle bundle) {
        DecOneMoreOrderFragment oneMoreOrderFragment = new DecOneMoreOrderFragment();
        oneMoreOrderFragment.setArguments(bundle);
        return oneMoreOrderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBundle = getArguments();
        return inflater.inflate(R.layout.fragment_wait_for_do, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        title.setText("再次发起订单");
        ivReturn.setVisibility(View.VISIBLE);


        initAdapter();
        recycleView.addItemDecoration(new RvSpaceItemDecoration(getActivity(), 10));
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(commentAdapter);
        //防止rv在局部刷新条目造成的闪烁问题
        ((SimpleItemAnimator) recycleView.getItemAnimator()).setSupportsChangeAnimations(false);
        softKeyBoardListener = new SoftKeyBoardListener(getActivity());
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //键盘弹出对列表位置复位
                childPosition = -1;
                parentPosition = -1;
            }

            @Override
            public void keyBoardHide(int height) {
                if (parentPosition < 0) { //检查列表position,避免键盘弹出隐藏未对数据修改，发起无意义请求
                    return;
                }
                //对手动输入数量进行验证
                String numStr = changeParams.get("goodsNum");
                if (numStr.equals("")) {
                    T.showToastyCenter(getActivity(), "商品数量不能为空");
                    commentAdapter.notifyDataSetChanged();
                    return;
                }
                int num = Integer.parseInt(numStr);
                if (num <= 0) {
                    T.showToastyCenter(getActivity(), "商品数量不能小于1");
                    commentAdapter.notifyDataSetChanged();
                    return;
                }
                double storeCount = cartList.get(parentPosition).getCartGoods().get(childPosition).getStoreCount();
                int ratio = cartList.get(parentPosition).getCartGoods().get(childPosition).getSpecRatio();
                if (num > storeCount * ratio) {
                    Double n = storeCount * ratio;
                    changeParams.put("goodsNum", String.valueOf(n.intValue()));
                    num = n.intValue();
                    T.showToastyCenter(getActivity(), "商品最大数量为" + storeCount);
                }
                //键盘隐藏后，请求修改商品数量

                cartList.get(parentPosition).getCartGoods().get(childPosition).setGoodsNum(num);
                commentAdapter.notifyItemChanged(parentPosition);
                updateBaseInfo();


//                updateGoodsCount();
            }
        });


    }

    @Override
    protected void initData() {
        super.initData();
        requestCarList(true);
        animator = new GouwucheAnimator();
        animator.initAlphaAnimator();
    }


    @OnClick({R.id.gouwuche_iv_return, R.id.tv_edit, R.id.ll_checked, R.id.tv_delete, R.id.tv_jiesuan, R.id.tv_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gouwuche_iv_return:
                getActivity().finish();
                break;
            case R.id.tv_edit:
                if (animator.isAlphaAniming()) {//表示当前正在进行透明渐变动画
                    return;
                }
                isEditStatus = !isEditStatus;
                editInfoUpdate();
                break;
            case R.id.ll_checked:
                resetAllGoodsCheckStatus();
                break;
            case R.id.tv_delete:
                if (isFastClick()) return;
                deleteGoods();
                break;
            case R.id.tv_jiesuan:
                if (isFastClick()) return;
                settlementGoods();
                break;
            case R.id.tv_reload:
                if (isFastClick()) return;
                requestCarList(true);
                break;
            default:
                break;
        }
    }

    private void editInfoUpdate() {
        tvEdit.setText(isEditStatus ? "完成" : "编辑");
        animator.switchAnima(isEditStatus ? tvJiesuan : tvDelete, isEditStatus ? tvDelete : tvJiesuan, 200);
        if (isEditStatus) {
            animator.alphaAnima(tvAllPrice, 200, 1.0f, 0f);
        } else {
            animator.alphaAnima(tvAllPrice, 200, 0f, 1.0f);
        }
    }


    /**
     * 更新基本信息
     */
    private void updateBaseInfo() {
        commentAdapter.notifyDataSetChanged();
        rlNoData.setVisibility(isNetError ? GONE : cartList.size() == 0 ? View.VISIBLE : View.GONE);
        rlNoNet.setVisibility(isNetError ? View.VISIBLE : View.GONE);
        rlBottom.setVisibility(cartList.size() == 0 ? View.GONE : View.VISIBLE);

//        tvEdit.setVisibility(cartList.size() == 0 ? View.GONE : View.VISIBLE);


        if (cartList.size() == 0) {
            isEditStatus = false;
            editInfoUpdate();
        }
        //商品是否全选
        //选中商品累计总价
        isAllSelect = true;
        BigDecimal jieSuanPrice = new BigDecimal(0.00);
        int goodsTotalCount = 0;
        for (CartListBean.Item item : cartList) {
            for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                if (goods.getIsSelect() == 1) {
                    if (goods.getIsWhole().equals("0")) {
                        //结算订单价格
                        BigDecimal num = new BigDecimal(goods.getGoodsNum() / goods.getSpecRatio());
                        BigDecimal baseNum = new BigDecimal(goods.getGoodsNum() % goods.getSpecRatio());
                        BigDecimal price = new BigDecimal(goods.getGoodsPrice());
                        BigDecimal basePrice = new BigDecimal(goods.getGoodsBasicsSpecPrice());
                        jieSuanPrice = jieSuanPrice.add(price.multiply(num).add(basePrice.multiply(baseNum)));
                    } else {
                        BigDecimal num = new BigDecimal(goods.getGoodsNum() / goods.getSpecRatio());
                        BigDecimal price = new BigDecimal(goods.getGoodsPrice());
                        jieSuanPrice = jieSuanPrice.add(price.multiply(num));

                    }

                } else {
                    isAllSelect = false;
                }
                ++goodsTotalCount;
            }
        }

        ivBottomAllSelect.setImageResource(isAllSelect ? R.drawable.icon_selected : R.drawable.icon_no_select);
        //商品宝贝数量修改
        tvGoodsTotalCount.setText(goodsTotalCount > 0 ? "共" + goodsTotalCount + "件宝贝" : "");

        //MainActivity中订阅  更新购物车tab角标
//        MainEvent event = new MainEvent();
//        event.setTag(0);
//        EventBus.getDefault().post(event);
        String html = "<font color=\"#999999\">合计：</font>" + "<font color=\"#FD6D14\">" + "¥" + jieSuanPrice.setScale(2, BigDecimal.ROUND_DOWN) + "</font>";
        tvAllPrice.setText(Html.fromHtml(html));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shopCarListUpdate(GouwucheEvent event) {
        Log.i(TAG, "-------------EventBUS:" + event.getTag());
        int tag = event.getTag();
        switch (tag) {
            case 0:
                getActivity().finish();
                break;
            default:
                break;
        }
    }


    /**
     * 结算商品货物
     */
    private void settlementGoods() {
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (CartListBean.Item item : cartList) {
            for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                if (goods.getIsSelect() == 1) {
                    HashMap<String, Object> smallParams = new HashMap<>();
                    smallParams.put("goodsId", goods.getGoodsId());
                    smallParams.put("cartId", goods.getCartId());
                    smallParams.put("specId", goods.getSpecId());
                    smallParams.put("goodsNum", goods.getGoodsNum());
                    smallParams.put("specRatio", goods.getSpecRatio());
                    smallParams.put("goodsName", goods.getGoodsName());
                    list.add(smallParams);
                }
            }
        }
        if (list.size() == 0) {
            T.showToastyCenter(getActivity(), "您还没有选择商品哦");
            return;
        }
        checkCarGoodsStoreCount(list);
    }


    /**
     * 删除商品货物
     */
    private void deleteGoods() {
        List<String> list = new ArrayList<>();
        for (CartListBean.Item item : cartList) {
            for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                if (goods.getIsSelect() == 1) {
                    list.add(goods.getCartId());
                }
            }
        }

        if (list.size() == 0) {
            T.showToastyCenter(getActivity(), "您还没有选择商品哦");
        } else {
            requestDeleteGoods(list);
        }
    }


    /**
     * 商品全部选中/取消全部选中
     */
    private void resetAllGoodsCheckStatus() {
        boolean isAllSelected = true;
        List<String> list = new ArrayList<>();
        for (CartListBean.Item item : cartList) {
            for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                list.add(goods.getCartId());
                if (goods.getIsSelect() == 0) {
                    isAllSelected = false;
                }
            }
        }

        for (CartListBean.Item item : cartList) {
            for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                goods.setIsSelect(isAllSelected ? 0 : 1);
            }
        }
        commentAdapter.notifyDataSetChanged();
        updateBaseInfo();
//        updateGroupGoodsSelected(isAllSelected ? 0 : 1, list);
    }


    /**
     * 父适配器
     */
    private void initAdapter() {
        commentAdapter = new CommentAdapter<CartListBean.Item>(R.layout.item_shopping_car, cartList) {
            @Override
            public void setViewData(BaseViewHolder holder, CartListBean.Item item, int position) {
                TextView tvWhName = holder.getView(R.id.warehouse_tv_name);
                tvWhName.setText(item.getSuppliersName());
                ImageView ivCheckStatus = holder.getView(R.id.warehouse_iv_check_status);
                boolean allSelect = true;
                for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                    if (goods.getIsSelect() == 0) {
                        allSelect = false;
                    }
                }
                ivCheckStatus.setImageResource(allSelect ? R.drawable.icon_selected : R.drawable.icon_no_select);
                RecyclerView recycleViewInner = holder.getView(R.id.recycleViewInner);
                recycleViewInner.setLayoutManager(new LinearLayoutManager(getContext()));
                DecOneMoreOrderFragment.GoodsAdapter gouwucarInnerAdapter = new DecOneMoreOrderFragment.GoodsAdapter(R.layout.item_gouwuche_layout, item.getCartGoods(), position);
                recycleViewInner.setAdapter(gouwucarInnerAdapter);
            }

            @Override
            public void setEvent(BaseViewHolder holder, CartListBean.Item item, int position) {
                ImageView ivCheckStatus = holder.getView(R.id.warehouse_iv_check_status);
                //父级选中图标点击事件
                ivCheckStatus.setOnClickListener(v -> {
                    boolean isChecked = true;
                    List<String> list = new ArrayList<>();
                    for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                        list.add(goods.getCartId());
                        if (goods.getIsSelect() == 0) isChecked = false;
                    }
                    for (CartListBean.Item.CartGoods goods : item.getCartGoods()) {
                        goods.setIsSelect(isChecked ? 0 : 1);
                    }
                    commentAdapter.notifyItemChanged(position);
                    updateBaseInfo();

//                    updateGroupGoodsSelected(isChecked ? 0 : 1, list);
                });
            }
        };
    }


    /**
     * 子适配器
     */
    private class GoodsAdapter extends BaseQuickAdapter<CartListBean.Item.CartGoods, BaseViewHolder> implements BGouWuCarFragment.InnerAdapterImp {

        //子条目所在的父条目的索引
        private int groupPosition;

        public GoodsAdapter(int layoutResId, List<CartListBean.Item.CartGoods> beanList, int groupPosition) {
            super(layoutResId, beanList);
            this.groupPosition = groupPosition;
        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, CartListBean.Item.CartGoods item) {
            int position = holder.getLayoutPosition();//子索引
            TextView tvGoodsName = holder.getView(R.id.goods_tv_name);
            TextView tvGoodsGuige = holder.getView(R.id.goods_tv_guige);
            TextView tvGoodsKey = holder.getView(R.id.goods_tv_new_key);
            TextView tvGoodsNewPrice = holder.getView(R.id.goods_tv_new_price);
            TextView tvBaseKey = holder.getView(R.id.tv_new_key);
            TextView tvBasePrice = holder.getView(R.id.tv_new_price);
            TextView tv_empty = holder.getView(R.id.tv_empty);
            if (item.getStoreCount() > 0) {
                tv_empty.setVisibility(GONE);
            } else {
                tv_empty.setVisibility(View.VISIBLE);
            }
            tvBaseKey.setText("/" + item.getGoodsBasicsSpecKey());
            tvBasePrice.setText(item.getGoodsBasicsSpecPrice());
            RelativeLayout rl_second_spce = holder.getView(R.id.rl_second_spce);
            boolean isWhole = item.getIsWhole().equals("1"); //1 不拆分
            rl_second_spce.setVisibility(isWhole ? GONE : View.VISIBLE);
            tvGoodsName.setText(item.getGoodsName());//商品名称
            tvGoodsGuige.setText(item.getSpecName());//商品规格
            tvGoodsNewPrice.setText(SpannableUtils.changeSpannableTv("" + item.getGoodsPrice()));//商品价格
            tvGoodsKey.setText("/" + item.getSpecKey());
            //加载图片
            GlideUtils.showRoundBorderGildeImg(getContext(), item.getGoodsImg(), holder.getView(R.id.goods_iv_show), 0.2f, Color.parseColor("#80000000"), 20, 0);
            //item点击跳转
            holder.getView(R.id.rl_all).setOnClickListener(v -> {
//                if (isFastClick()) return;
//                Bundle bundle = new Bundle();
//                bundle.putString("goods_id", item.getGoodsId());
//                bundle.putString("intentPageTag", "gouwuche");//去往GoodsDetailActivity增加一个标识
//                startActivity(GoodsDetailActivity.class, bundle);
            });

            holder.getView(R.id.goods_iv_check_status).setBackgroundResource(item.getIsSelect() == 1 ? R.drawable.img_check : R.drawable.img_check_no);

            //改变选中状态
            holder.getView(R.id.goods_iv_check_status).setOnClickListener(v -> {
                ArrayList<String> list = new ArrayList<>();
                list.add(item.getCartId());
                item.setIsSelect(item.getIsSelect() == 1 ? 0 : 1);
                notifyItemChanged(position);
                updateBaseInfo();
//                updateGroupGoodsSelected(item.getIsSelect() == 1 ? 0 : 1, list);
            });

            EditText et_number = holder.findView(R.id.et_number);
            EditText et_num = holder.findView(R.id.et_num);
            et_number.setText(String.valueOf(item.getGoodsNum() / item.getSpecRatio()));
            et_num.setText(String.valueOf(item.getGoodsNum() % item.getSpecRatio()));
            //增加商品数量
            holder.findView(R.id.goods_num_iv_jia).setOnClickListener(v -> {
                changeParams.put("cartId", item.getCartId());
                int num = item.getGoodsNum() + item.getSpecRatio();
                if (num / item.getSpecRatio() > item.getStoreCount()) {
                    Double storeCount = item.getStoreCount() * item.getSpecRatio();
                    T.showToastyCenter(getActivity(), "超出库存");
                    num = storeCount.intValue();
                }
                changeParams.put("goodsNum", num + "");

                item.setGoodsNum(num);
                notifyItemChanged(position);
                updateBaseInfo();

//                updateGoodsCount();
            });

            //减少商品数量
            holder.findView(R.id.goods_num_iv_jian).setOnClickListener(v -> {
                changeParams.put("cartId", item.getCartId());
                int num = item.getGoodsNum() - item.getSpecRatio();
                if (num <= 0) {
                    T.showToastyCenter(getActivity(), "商品最大数量不能小于1");
                    return;
                }
                changeParams.put("goodsNum", num + "");

                item.setGoodsNum(num);
                notifyItemChanged(position);
                updateBaseInfo();
//                updateGoodsCount();
            });

            //基础规格增加商品数量
            holder.findView(R.id.iv_jia).setOnClickListener(v -> {
                changeParams.put("cartId", item.getCartId());
                int num = item.getGoodsNum() + 1;
                if (num > item.getStoreCount() * item.getSpecRatio()) {
                    Double storeCount = item.getStoreCount() * item.getSpecRatio();
                    T.showToastyCenter(getActivity(), "超出库存");
                    num = storeCount.intValue();
                }
                changeParams.put("goodsNum", num + "");

                item.setGoodsNum(num);
                notifyItemChanged(position);
                updateBaseInfo();
//                updateGoodsCount();

            });

            //基础规格减少商品数量
            holder.findView(R.id.iv_jian).setOnClickListener(v -> {
                if (item.getGoodsNum() % item.getSpecRatio() == 0) {
                    return;
                }
                changeParams.put("cartId", item.getCartId());
                int num = item.getGoodsNum() - 1;
                if (num <= 0) {
                    T.showToastyCenter(getActivity(), "商品最大数量不能小于1");
                    num = 1;
                }
                changeParams.put("goodsNum", num + "");

                item.setGoodsNum(num);
                notifyItemChanged(position);
                updateBaseInfo();
//                updateGoodsCount();
            });


            //数量输入框事件监听
            et_number.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    childPosition = position;
                    parentPosition = groupPosition;
                    if (changeParams != null) {
                        changeParams.put("cartId", item.getCartId());
                        String ns = s.toString().equals("") ? "0" : s.toString();
                        int num = (Integer.valueOf(ns) * item.getSpecRatio()) + (item.getGoodsNum() % item.getSpecRatio());
                        changeParams.put("goodsNum", String.valueOf(num));
                    }
                }
            });


            //数量输入框事件监听
            et_num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    childPosition = position;
                    parentPosition = groupPosition;
                    if (changeParams != null) {
                        changeParams.put("cartId", item.getCartId());
                        int sn = s.toString().equals("") ? 0 : Integer.valueOf(s.toString());
                        int num = item.getGoodsNum() - (item.getGoodsNum() % item.getSpecRatio()) + sn;
                        changeParams.put("goodsNum", String.valueOf(num));
                    }
                }
            });


        }

        @Override
        protected void convert(@NotNull BaseViewHolder holder, CartListBean.Item.CartGoods item, @NotNull List<?> payloads) {
            super.convert(holder, item, payloads);
           /* Log.i(TAG, "--------局部刷新");
            CountModifyView countModifyView = holder.getView(R.id.goods_count_modify_view);
            countModifyView.setCurrentCount(item.getGoodsNum());
            countModifyView.setTvCount();*/
        }

        @Override
        public void notifyGoodsNum(int goodsNum, int groupIndex, int childIndex) {
            Log.i(TAG, "-----------" + groupIndex + "==" + childIndex);
            //    GoodsBean goodsBean = mRealDatas.get(groupIndex).getCartsIds().get(childIndex);
            //   goodsBean.setGoodsNum(goodsNum);
            //   notifyItemChanged(childIndex, "payload");
            //  updateBaseInfo();
        }
    }

    /**
     * 请求购物车商品列表
     */
    public void requestCarList(boolean isShowDialog) {

        String orderSn = mBundle.getString("orderSn");
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSn", orderSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_AGAIN)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(getActivity(), isShowDialog) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {
                            CartListBean cartBean = JsonUtils.parse(response.body(), CartListBean.class);
                            isNetError = false;
                            if (cartBean != null) {
                                for (CartListBean.Item item : cartBean.getData()) {
                                    for (CartListBean.Item.CartGoods cartGood : item.getCartGoods()) {
                                        cartGood.setIsSelect(1);
                                    }
                                }
                                cartList.clear();
                                cartList.addAll(cartBean.getData());
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        updateBaseInfo();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(getActivity(), "网络开小差，请稍后重试 ~");
                        isNetError = true;
                    }
                });
    }

    /**
     * 用户购物车列表商品数量修改
     */
    private void updateGoodsCount() {
        RequestBody requestBody = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(changeParams));
        OkGo.<String>post(UrlContent.UPDATE_CART)
                .tag(this)
                .upRequestBody(requestBody)
                .execute(new DialogCallback(getActivity(), true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {
                            MainEvent event = new MainEvent(0);
                            EventBus.getDefault().post(event);
                            AHomeEvent AHEvent = new AHomeEvent(0);
                            EventBus.getDefault().post(AHEvent);
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        requestCarList(true);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(getActivity(), "网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 购物车商品库存校验
     */
    private void checkCarGoodsStoreCount(List list) {
        HashMap map = new HashMap();
        map.put("list", list);
        String jsonStr = JsonUtils.toJsonString(map);
        RequestBody body = RequestBody.create(UrlContent.JSON, jsonStr);
        OkGo.<String>post(UrlContent.CHECK_CART_GOODS_STOCK_NUM)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(getActivity(), true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        if (baseBean.getCode() == 200) {
                            Bundle bundle = new Bundle();
                            bundle.putString("data", jsonStr);
                            startActivity(OrderCommitActivity.class, bundle);
                        } else if (baseBean.getCode() == 3000) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                JSONArray understocks = dataObject.getJSONArray("understocks");
                                JSONArray sellOuts = dataObject.getJSONArray("sellOuts");
                                String content = "";
                                if (understocks.length() > 0) {
                                    for (int i = 0; i < understocks.length(); i++) {
                                        content += understocks.getString(i);
                                    }
                                    content += "已售罄，请去掉已售罄商品再提交结算，谢谢！\n";
                                }
                                if (sellOuts.length() > 0) {
                                    for (int i = 0; i < sellOuts.length(); i++) {
                                        content += sellOuts.getString(i);
                                    }
                                    content += "报货数量超出库存范围";
                                }
                                new WarmTipsDialog
                                        .Builder(getActivity())
                                        .setContent(content)
                                        .create().show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            T.showToastyCenter(getActivity(), baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(getActivity(), "网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 用户购物车列表删除商品
     */
    private void requestDeleteGoods(List list) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("cartId", list);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DELETE_GOODS)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(getActivity(), true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {
                            requestCarList(false);
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(getActivity(), "网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 用户购物车列表商品选中状态修改(单个/分组/全选)
     */
    private void updateGroupGoodsSelected(int state, List<String> list) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("selected", state);
        map.put("list", list);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(map));
        OkGo.<String>post(UrlContent.UPDATE_GOODS_GROUP_SELECTED)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(getActivity(), true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> requestCarList(false));
                    }

                    @Override
                    public void onError(Response<String> response) {
                        T.showToastyCenter(getActivity(), "网络开小差，请稍后重试 ~");
                    }
                });
    }


}
