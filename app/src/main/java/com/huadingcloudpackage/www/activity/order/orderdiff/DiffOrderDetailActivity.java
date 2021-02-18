package com.huadingcloudpackage.www.activity.order.orderdiff;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.order.ordercommit.PayOderActivity;
import com.huadingcloudpackage.www.activity.order.BaseOrderDetailActivity;
import com.huadingcloudpackage.www.activity.order.CommonFunUtils;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.BravhListDivider;
import com.huadingcloudpackage.www.adapter.orderdiff.DiffOrderDetailGroupAdapter;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DiffOrderDetailOuterBean;
import com.huadingcloudpackage.www.bean.DiffOrderDetailOuterBean.DiffOrderDetailDataBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.eventbus.DiffOrderDetailEvent;
import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.PictureSelectUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.util.T;
import com.huadingcloudpackage.www.widget.NoScrollLinearLayoutManager;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class DiffOrderDetailActivity extends BaseOrderDetailActivity {

    @BindView(R.id.diff_order_detail_root_view)
    RelativeLayout rlRootView;
    @BindView(R.id.diff_order_detail_ll_footer_view)
    LinearLayout llFooterView;


    @BindView(R.id.diff_order_detail_iv_diff_type)
    ImageView ivDiffType;
    @BindView(R.id.diff_order_detail_tv_diff_type)
    TextView tvDiffType;
    @BindView(R.id.diff_order_detail_tv_diff_type_des)
    TextView tvDiffTypeDes;

    @BindView(R.id.diff_order_detail_tv_diff_pay_type)
    TextView tvDiffPayType;
    @BindView(R.id.diff_order_detail_tv_diff_price)
    TextView tvDiffPrice;

    @BindView(R.id.diff_order_detail_tv_diff_order_sn)
    TextView tvDiffOrderSn;//差异单编号
    @BindView(R.id.diff_order_detail_tv_order_sn)
    TextView tvOrderSn;//订单编号
    @BindView(R.id.diff_order_detail_tv_apply_time)
    TextView tvApplyTime;//申请时间
    @BindView(R.id.diff_order_detail_tv_pay_time)
    TextView tvPayTime;//申请时间
    @BindView(R.id.diff_order_detail_tv_tran_time)
    TextView tvTranTime;//完成时间
    @BindView(R.id.diff_order_detail_tv_diff_reason)
    TextView tvDiffReason;//差异原因
    @BindView(R.id.diff_order_detail_tv_diff_des)
    TextView tvDiffDes;//差异说明
    @BindView(R.id.diff_order_detail_tv_refuse_reason)
    TextView tvRefuseReason;//拒绝原因
    @BindView(R.id.diff_order_detail_tv_diff_pay_method)
    TextView tvDiffPayMethod;//差异支付方式

    @BindView(R.id.diff_order_detail_rv_list)
    RecyclerView rvList;
    @BindView(R.id.diff_order_detail_rv_img_show)
    RecyclerView rvImgShow;

    @BindView(R.id.diff_order_detail_rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.diff_order_detail_tv_click_left)
    TextView tvBottomLeft;
    @BindView(R.id.diff_order_detail_tv_click_right)
    TextView tvBottomRight;

    //差异单详情适配器
    private DiffOrderDetailGroupAdapter diffOrderDetailGroupAdapter;
    //差异单详情适配器的数据源
    private List<DiffOrderDetailDataBean> mDatas;


    private CommentAdapter<String> diffImgAdapter;
    private List<String> diffImgList;

    //从差异单列表点击传过来的数据
    private DiffOrderDetailDataBean mDiffOrderListBean;
    private int groupItemIndex;
    private String mPageTag;
    private String diffSn;

    private int orderState;//1待审核、2.已完成、3.已拒绝 4.已取消、5.待付款

    private CommonFunUtils commonFunUtils;
    private PictureSelectUtils pictureSelectUtils;

    private static final String TAG = "DiffOrderDetailActivity";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_diff_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtils.changStatusIconCollor(this, false);
        Bundle bundle = getIntent().getExtras();
        groupItemIndex = bundle.getInt("groupItemIndex");
        mPageTag = bundle.getString("pageTag");
        diffSn = bundle.getString("diffSn");


        mDatas = new ArrayList<>();
        initAdapter();

        diffImgList = new ArrayList<>();
        initImgShowAdapter();

        commonFunUtils = new CommonFunUtils(this);


        rlBottom.setVisibility(View.GONE);
        changePageState(Constants.PageState.INIT);
        getDiffOrderDetailData();
    }


    private void setDiffOrderInfo() {

        tvDiffOrderSn.setText(mDiffOrderListBean.getDifferenceSn());
        tvOrderSn.setText("订单编号：" + mDiffOrderListBean.getOrderSonSn());
        tvApplyTime.setText("申请时间：" + mDiffOrderListBean.getCreatedTime());

        String reasonSTR = "";
        for (String s : mDiffOrderListBean.getDifferencesReason()) {
            switch (s) {
                case "1":
                    reasonSTR += "商品破损" + ",";
                    break;
                case "2":
                    reasonSTR += "少发漏发" + ",";
                    break;
                case "3":
                    reasonSTR += "商品过期" + ",";
                    break;
                case "4":
                    reasonSTR += "商品近效期" + ",";
                    break;
                case "5":
                    reasonSTR += "发错货" + ",";
                    break;
                case "6":
                    reasonSTR += "多发超发" + ",";
                    break;
            }
        }
        tvDiffReason.setText(reasonSTR.length()>0?reasonSTR.substring(0, reasonSTR.length() - 1):"");
        tvDiffDes.setText(mDiffOrderListBean.getDifferencesExplain());
        tvRefuseReason.setText("拒绝原因：" + mDiffOrderListBean.getRefuseReason());


        tvPayTime.setVisibility(View.GONE);
        tvTranTime.setVisibility(View.GONE);
        tvRefuseReason.setVisibility(View.GONE);
        tvDiffPayMethod.setVisibility(View.GONE);


        if (orderState == 1) {
            tvDiffType.setText("待审核");
            ivDiffType.setImageResource(R.drawable.img_diff_order_status_daishenhe);
            setButtonStyle(0, 2, "取消申请", "");
        } else if (orderState == 2) {
            tvDiffType.setText("已完成");
            ivDiffType.setImageResource(R.drawable.img_order_status_completed);
            setButtonStyle(0, 2, "删除订单", "");
            tvTranTime.setVisibility(View.VISIBLE);
            tvDiffPayMethod.setVisibility(View.VISIBLE);
            tvTranTime.setText("完成时间：" + mDiffOrderListBean.getOverTime());
            if (TextUtils.isEmpty(mDiffOrderListBean.getRefund())) {
                tvDiffPayMethod.setText("付款方式：余额");
                tvPayTime.setVisibility(View.VISIBLE);
                tvPayTime.setText("付款时间："+mDiffOrderListBean.getPayTime());
            } else if (TextUtils.isEmpty(mDiffOrderListBean.getPayTotalPrice())) {
                tvDiffPayMethod.setText("退款方式：余额");
            }
        } else if (orderState == 3) {
            tvDiffType.setText("已拒绝");
            ivDiffType.setImageResource(R.drawable.img_diff_order_status_refused);
            setButtonStyle(0, 2, "删除订单", "");
            tvTranTime.setVisibility(View.VISIBLE);
            tvRefuseReason.setVisibility(View.VISIBLE);
            tvTranTime.setText("完成时间：" + mDiffOrderListBean.getOverTime());
        } else if (orderState == 4) {
            tvDiffType.setText("已取消");
            ivDiffType.setImageResource(R.drawable.img_order_status_cancelled);
            setButtonStyle(0, 2, "删除订单", "");
            tvTranTime.setVisibility(View.VISIBLE);
            tvTranTime.setText("完成时间：" + mDiffOrderListBean.getOverTime());
        } else if (orderState == 5) {
            tvDiffType.setText("待结算");
            ivDiffType.setImageResource(R.drawable.img_order_status_daifukuan);
            setButtonStyle(2, 1, "", "去付款");
        }
    }


    /**
     * 获取差异订单详情
     */
    private void getDiffOrderDetailData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("differenceSn", diffSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.DIFF_ORDER_DETAILS)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                changePageState(Constants.PageState.NORMAL);
                                DiffOrderDetailOuterBean diffOrderDetailOuterBean = JsonUtils.parse(response.body(), DiffOrderDetailOuterBean.class);
                                mDiffOrderListBean = diffOrderDetailOuterBean.getData();

                                orderState = Integer.parseInt(mDiffOrderListBean.getOrderState());
                                setDiffOrderInfo();

                                mDatas.clear();
                                mDatas.add(mDiffOrderListBean);
                                diffOrderDetailGroupAdapter.notifyDataSetChanged();

                                diffImgList.clear();
                                List<String> imgList = mDiffOrderListBean.getDifferenceImgs();
                                if (imgList != null) {
                                    diffImgList.addAll(imgList);
                                }
                                diffImgAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        changePageState(Constants.PageState.ERROR);
                    }
                });
    }

    private void initAdapter(){
        diffOrderDetailGroupAdapter = new DiffOrderDetailGroupAdapter(this, R.layout.item_diff_order_detail_rv_list_group, mDatas);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(diffOrderDetailGroupAdapter);
        rvList.addItemDecoration(new BravhListDivider(this, BravhListDivider.VERTICAL_LIST, BravhListDivider.DIVIDER_ALL));

        setOverScroll(rvList);
        rvListAddFooterView();
    }


    private void rvListAddFooterView() {
        rlRootView.removeView(llFooterView);
        llFooterView.setVisibility(View.VISIBLE);
        diffOrderDetailGroupAdapter.addFooterView(llFooterView);
    }


    private void initImgShowAdapter() {
        rvImgShow.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        diffImgAdapter = new CommentAdapter<String>(R.layout.item_img, diffImgList) {
            @Override
            public void setViewData(BaseViewHolder holder, String item, int position) {
                String img = diffImgList.get(position);
                GlideUtils.showRoundImgCenterCrop(getContext(), img, holder.getView(R.id.iv_goods_img));
            }

            @Override
            public void setEvent(BaseViewHolder holder, String item, int position) {
                holder.getView(R.id.iv_goods_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<LocalMedia> localMediaList = new ArrayList<>();
                        for (String imgUrl : diffImgList) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(imgUrl);
                            localMediaList.add(localMedia);
                        }
                        if (pictureSelectUtils == null) {
                            pictureSelectUtils = new PictureSelectUtils();
                        }
                        pictureSelectUtils.showImage(DiffOrderDetailActivity.this, position, localMediaList);
                    }
                });
            }
        };
        rvImgShow.setAdapter(diffImgAdapter);
    }


    @OnClick({R.id.diff_order_detail_iv_return, R.id.diff_order_detail_tv_copy, R.id.diff_order_detail_stv_call_fuke,
            R.id.state_layout_net_error, R.id.root_state_layout, R.id.diff_order_detail_tv_click_left, R.id.diff_order_detail_tv_click_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.diff_order_detail_iv_return:
                finish();
                break;
            case R.id.diff_order_detail_tv_copy:
                commonFunUtils.copyText(tvDiffOrderSn);
                break;
            case R.id.diff_order_detail_stv_call_fuke://联系客服跳转到拨打电话页面
                commonFunUtils.callKefu();
                break;
            case R.id.state_layout_net_error:
                getDiffOrderDetailData();
                break;
            case R.id.root_state_layout:
                break;
            case R.id.diff_order_detail_tv_click_left:
                if (orderState == 1 || orderState == 5) {//待审核（取消申请）|待结算（取消订单）
                    String content = orderState == 1 ? "确定取消申请？" : "确定取消订单？";
                    String title = orderState == 1 ? "取消申请" : "取消订单";
                    new CommomDialog(mContext, content, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                DiffOrderPublicRequest.cancelDiffOrder(mContext, mDiffOrderListBean.getDifferenceSn(), () -> {
                                    T.showToastyCenter(mContext, "操作成功");
                                    registEventBus(orderState, "DiffOrderCancel");
                                    getDiffOrderDetailData();
                                });
                            }
                        }
                    }).setTitle(title).setNegativeButton("取消").setPositiveButton("确定").show();
                } else if (orderState == 2 || orderState == 3 || orderState == 4) {//已完成（删除）|已取消（删除）|已拒绝（删除）
                    new CommomDialog(mContext, "确定删除此订单？", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            //删除订单接口
                            if (confirm) {
                                DiffOrderPublicRequest.deleteDiffOrder(mContext, mDiffOrderListBean.getDifferenceSn(), () -> {
                                    T.showToastyCenter(mContext, "操作成功");
                                    registEventBus(orderState, "DiffOrderDelete");
                                    finish();
                                });
                            }
                        }
                    }).setTitle("删除订单").setNegativeButton("取消").setPositiveButton("确定").show();
                }
                break;
            case R.id.diff_order_detail_tv_click_right:
                if (orderState == 5) {//待结算（去付款）
                    Bundle bundle = new Bundle();
                    bundle.putString("orderSonSn", mDiffOrderListBean.getOrderSonSn());
                    bundle.putString("totalAmount", mDiffOrderListBean.getPayTotalPrice() );
                    bundle.putString("differenceSn", mDiffOrderListBean.getDifferenceSn());

                    bundle.putInt("groupItemIndex", groupItemIndex);
                    bundle.putString("pageTag", mPageTag);

                    Intent intent = new Intent(this, PayOderActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);//跳转到订单结算页面
                }
                break;
        }
    }


    /**
     * @param leftStyle  左边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     * @param rightStyle 右边按钮样式  0标识灰色  1标识蓝色  2标识隐藏
     */
    public void setButtonStyle(int leftStyle, int rightStyle, String leftText, String rightText) {
        if (leftStyle == 2 && rightStyle == 2) {
            rlBottom.setVisibility(View.GONE);
            return;
        }
        rlBottom.setVisibility(View.VISIBLE);
        setBaseButtonStyle(tvBottomLeft, leftStyle, leftText);
        setBaseButtonStyle(tvBottomRight, rightStyle, rightText);

    }


    /**
     * 注册EventBus事件
     */
    private void registEventBus(int tag, String doTag) {
        DiffOrderDetailEvent event = new DiffOrderDetailEvent();
        event.setTag(tag);
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupItemIndex", groupItemIndex);
        map.put("doTag", doTag);
        map.put("pageTag", mPageTag);
        event.setObject(map);
        EventBus.getDefault().post(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DiffOrderDetailEvent event) {
        int tag = event.getTag();
        Log.i(TAG, "----------event:" + JsonUtils.toJsonString(event));
        if (tag == 5) {//待结算（去付款）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            if (doTag.equals("DiffOrderPaySuccess")) {//订阅了从PayOrderActivity界面发布的事件（支付成功）
//                finish();
                getDiffOrderDetailData();
            }
        }
    }


}