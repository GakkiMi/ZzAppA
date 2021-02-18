package com.huadingcloudpackage.www.activityfragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.goods.GoodsDetailActivity;
import com.huadingcloudpackage.www.activity.search.SearchActivity;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.base.BaseLazyFragment;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.GoodsListBean;
import com.huadingcloudpackage.www.bean.GoodsSpecDetail;
import com.huadingcloudpackage.www.bean.HomeClassBean;
import com.huadingcloudpackage.www.eventbus.AHomeEvent;
import com.huadingcloudpackage.www.eventbus.MainEvent;
import com.huadingcloudpackage.www.http.UrlContent;


import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.T;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;

/**
 * 首页
 */
public class AHomeFragment extends BaseLazyFragment {

    @BindView(R.id.recycle_left)
    RecyclerView recycleLeft;
    @BindView(R.id.recycle_right)
    RecyclerView recycleRight;
    @BindView(R.id.ll_to_search)
    LinearLayout llToSearch;
    /* @BindView(R.id.swipeRefre)
     SwipeRefreshLayout swipeRefre;*/
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private CommentAdapter<HomeClassBean.ListBean> commentAdapterLeft;
    private CommentAdapter<GoodsListBean.Data.Item> commentAdapterRight;
    private CommentAdapter<GoodsSpecDetail.Item> specAdapter;
    private List<GoodsSpecDetail.Item> specList = new ArrayList<>();

    private int levelTwoListPosition = -1;  //二级商品全局定位
    private int levelTwoItemGoodsNum = -1;       //二级商品全局商品数量
    private int levelTwoItemGoodsSpecPosition = -1;  //二级商品全局规格定位
    private int levelTwoListPageNum = 1;   //右侧商品加载页数
    private String levelOneItemId = null;
    private String goodsNameStr;

    public AHomeFragment() {
        // Required empty public constrductor
    }

    public List<GoodsListBean.Data.Item> levelTwoList = new ArrayList<>();//右边当前显示列表数据源
    public List<HomeClassBean.ListBean> levelOneList = new ArrayList<>();//左边列表数据源

    /* List<GoodsDetailsBean.DataBean.ItemListBean> itemListBeans = new ArrayList<>();
     List<GoodsDetailsBean.DataBean.UnitListBean> unitListBeans = new ArrayList<>();*/
    private String TAG = "AHomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        initAdapterLeft();
        recycleLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleLeft.setAdapter(commentAdapterLeft);
        initAdapterRight();
        recycleRight.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleRight.setAdapter(commentAdapterRight);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getRightData(levelOneItemId, levelTwoListPageNum + 1);
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (levelOneList.size() <= 0) {
                    getLeftData();
                } else {
                    getRightData(levelOneItemId, 1);
                }
            }
        });

     /*   swipeRefre.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeftData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefre.setRefreshing(false);
                    }
                }, 1000);
            }
        });*/

        recycleRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            /*    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int[] pos = getRecyclerViewLastPosition((LinearLayoutManager) layoutManager);
                if (rightDataList.size() > 0) {
                    HashMap<String, Object> map = rightDataList.get(currentLeftIndex);
                    map.put("scrollPostion", pos);
                }*/
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shopCarBageNumUpdate(AHomeEvent event) {
        Log.i(TAG, "-------------EventBUS:" + event.getTag());
        int tag = event.getTag();
        switch (tag) {
            case 0:
                getRightData(levelOneItemId, 1);
                break;
            case 1:   //商品详情添加购物车发出更新角标事件
                HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
                String id = (String) map.get("id");
                int num = (int) map.get("num");
                for (GoodsListBean.Data.Item item : levelTwoList) {
                    if(item.getId().equals(id)){
                        item.setCarCount(num);
                        break;
                    }
                }
                levelTwoList.size();
                commentAdapterRight.notifyDataSetChanged();
                break;

        }
    }


    @Override
    protected void initData() {
        super.initData();
        llToSearch.setOnClickListener(v -> {
            startActivity(SearchActivity.class);
        });
        getLeftData();
    }


    public void onSelected() {
        getLeftData();
    }


    private void initAdapterLeft() {
        commentAdapterLeft = new CommentAdapter<HomeClassBean.ListBean>(R.layout.item_home_left_list, levelOneList) {
            @Override
            public void setViewData(BaseViewHolder holder, HomeClassBean.ListBean item, int position) {
                holder.setText(R.id.item_tv_name, item.getName());
            }

            @Override
            public void setEvent(BaseViewHolder holder, HomeClassBean.ListBean item, int position) {
                holder.getView(R.id.rl_parent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (HomeClassBean.ListBean data : levelOneList) {
                            data.setSelect(false);
                        }
                        levelOneList.get(position).setSelect(true);
                        notifyDataSetChanged();
                        levelOneItemId = item.getId();
                        getRightData(levelOneItemId, 1);
                       /* if (rightDataList.size() > 0) {
                            HashMap<String, Object> map = rightDataList.get(currentLeftIndex);
                            boolean isAlreadyLoad = (boolean) map.get("isAlreadyLoad");
                            Log.i(TAG, "---------:" + isAlreadyLoad);
                            if (!isAlreadyLoad) {//没加载过 进行请求
                                getRightData(item.getId());
                            } else {//加载过则直接从rightDataList中取值
                                notifyRightRvData(map);
                            }
                        }*/
                    }
                });
                holder.getView(R.id.rl_parent).setSelected(levelOneList.get(position).isSelect());

                TextView textView = holder.getView(R.id.item_tv_name);
                View view_line = holder.getView(R.id.view_line);
                if (item.isSelect()) {
                    view_line.setVisibility(View.VISIBLE);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    textView.setTextSize(14);
                } else {
                    //设置不为加粗
                    view_line.setVisibility(View.INVISIBLE);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    textView.setTextSize(13);

                }
            }
        };

    }

    /**
     * 修改刷新二级列表
     */
    private void notifyRightRvData() {
        GoodsListBean.Data.Item item = levelTwoList.get(levelTwoListPosition);
        item.setCarCount(levelTwoItemGoodsNum);
        levelTwoList.set(levelTwoListPosition, item);
        commentAdapterRight.notifyDataSetChanged();
    }


    private void initAdapterRight() {
        commentAdapterRight = new CommentAdapter<GoodsListBean.Data.Item>(R.layout.item_home_right_list, levelTwoList) {
            @Override
            public void setViewData(BaseViewHolder holder, GoodsListBean.Data.Item item, int position) {
                GlideUtils.showGildeImg(getContext(), item.getImg(), holder.getView(R.id.item_iv_icon));
                holder.setText(R.id.item_tv_name, item.getGoodsName());
                holder.setText(R.id.item_tv_guige, "/" + item.getSpecKey());
                holder.setText(R.id.item_tv_price, "¥" + item.getShopPrice());
                TextView tv_empty = holder.findView(R.id.tv_empty);
                double shopCount = 0;
                if (item.getStoreCount() != null && !item.getStoreCount().equals("")) {
                    shopCount = Double.valueOf(item.getStoreCount());
                }
                if (shopCount > 0) {
                    tv_empty.setVisibility(View.GONE);
                } else {
                    tv_empty.setVisibility(View.VISIBLE);
                }

                if (!item.isSpecFlag()) {  //判断商品是否有多个规格，单规格商品只显示加减和数量，多规格商品显示"选择规格"
                    holder.findView(R.id.ll_single_num).setVisibility(View.VISIBLE);
                    holder.findView(R.id.rl_mult_spce_num).setVisibility(View.GONE);
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
            }


            @Override
            public void setEvent(BaseViewHolder holder, GoodsListBean.Data.Item item, int position) {
                holder.getView(R.id.ll_all).setOnClickListener(v -> {
                    if (isFastClick()) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("goods_id", item.getId());
                    startActivity(GoodsDetailActivity.class, bundle);
                });

                //规格选择
                holder.getView(R.id.rl_mult_spce_num).setOnClickListener(v -> {
                    levelTwoListPosition = position;
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
                    levelTwoListPosition = position;
                    levelTwoItemGoodsNum = num;
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num * item.getSpecRatio());
                    changeNumForCart(params);
                });

                holder.getView(R.id.iv_less).setOnClickListener(v -> {

                    levelTwoListPosition = position;
                    int num = item.getCarCount() - 1;
                    levelTwoItemGoodsNum = num;
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("goodsId", item.getId());
                    params.put("specKeyId", item.getSpecId());
                    params.put("goodsNum", num * item.getSpecRatio());
                    changeNumForCart(params);
                });


            }
        };
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
                                    notifyRightRvData();
                                } else {
                                    getGoodsSpecDetails(goodsId);
                                }
                                MainEvent event = new MainEvent(0);
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
     * 移动端APP查询商品分类列表根据 (level)分类等级
     */
    private void getLeftData() {
        showLoading();
        OkGo.<String>get(UrlContent.SELECTCATEBYLEVEL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                HomeClassBean classBean = JsonUtils.parse(response.body(), HomeClassBean.class);
                                levelOneList.clear();
                                levelOneList.addAll(classBean.getData());

                                if (levelOneList.size() > 0) {
                                    int p = 0;
                                    if (levelOneItemId != null) {
                                        for (int i = 0; i < levelOneList.size(); i++) {
                                            String id = levelOneList.get(i).getId();
                                            if (id.equals(levelOneItemId)) {
                                                p = i;
                                            }
                                        }
                                    }

                                    levelOneItemId = levelOneList.get(p).getId();
                                    getRightData(levelOneItemId, 1);
                                    levelOneList.get(p).setSelect(true);
                                }
                                commentAdapterLeft.notifyDataSetChanged();
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
     * 移动端APP首页查询商品
     */
    private void getRightData(String id, int p) {
        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("catId3", id);
        params.put("pageNum", p);
        params.put("pageSize", 10);
        OkGo.<String>post(UrlContent.GETAPPGOODS)
                .tag(this)
                .upRequestBody(RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params)))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                levelTwoListPageNum = p; //加载成功后再赋值
                                GoodsListBean goodsListBean = JsonUtils.parse(response.body(), GoodsListBean.class);
                                if (p == 1) {
                                    levelTwoList.clear();
                                }
                                levelTwoList.addAll(goodsListBean.getData().getList());
                                commentAdapterRight.notifyDataSetChanged();
                                if (p == 1 && levelTwoList.size() > 0) {
                                    recycleRight.scrollToPosition(0);
                                }
                            }
                        });
                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                        smartRefreshLayout.finishRefresh();
                        smartRefreshLayout.finishLoadMore();
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


    private AlertDialog dialog;
    private int specPosition = 0;
    private String goodsId = null;

    /**
     * 展示和刷dialog,当bean为null则刷新
     *
     * @param bean
     */


    private ImageView add, less, close, goods_add, goods_less;
    private TextView tv_price, tv_num, tv_spec, tv_goods_price, tv_goods_spec, tv_goods_num, tv_storeNum;

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
                T.showToastyCenter(getContext(), "库存不足");
                return;
            }
            int num = goodsNum + specRatio;
            if (num / specRatio > spec.getStoreCount()) {
                T.showToastyCenter(getContext(), "超出库存");
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
                T.showToastyCenter(getContext(), "库存不足");
                return;
            }
            int num = goodsNum + 1;
            if (num / spec.getSpecRatio() > spec.getStoreCount()) {
                T.showToastyCenter(getContext(), "超出库存");
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

    private void initDialog() {
        //初始化dialog
        ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(getContext())
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();//实现自适应布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_spce_detail, null);
        add = view.findViewById(R.id.iv_add);
        tv_storeNum = view.findViewById(R.id.tv_storeNum);
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
                num += (item.getGoodsNum() / item.getSpecRatio() + item.getGoodsNum() % item.getSpecRatio());
            }
            levelTwoItemGoodsNum = num;
            notifyRightRvData();
        });

        initSpecAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(spanLayoutManager);
        recyclerView.setAdapter(specAdapter);
        dialog = new AlertDialog.Builder(getContext()).setView(view).create();
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


}
