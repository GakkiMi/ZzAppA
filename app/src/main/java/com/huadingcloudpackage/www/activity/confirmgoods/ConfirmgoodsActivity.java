package com.huadingcloudpackage.www.activity.confirmgoods;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.ImageAddAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.DeclareOrderDetailOuterBean;
import com.huadingcloudpackage.www.bean.DeclareOrderOuterBean.OrderGoodsBean;
import com.huadingcloudpackage.www.bean.UpLoadIVBean;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.dialog.ConfirmGoodsDialog;
import com.huadingcloudpackage.www.dialog.DecimalCountModifyDialog;
import com.huadingcloudpackage.www.dialog.ReasonsDifferencesDialog;
import com.huadingcloudpackage.www.eventbus.DeclareOrderDetailEvent;
import com.huadingcloudpackage.www.http.PublicConstants;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.BigDecimalUtils;
import com.huadingcloudpackage.www.util.GlideUtils;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.LogUtilsApp;
import com.huadingcloudpackage.www.util.MyUtils;
import com.huadingcloudpackage.www.util.PictureSelectUtils;
import com.huadingcloudpackage.www.util.SimpleItemDecoration;
import com.huadingcloudpackage.www.util.SpannableUtils;
import com.huadingcloudpackage.www.util.T;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;


/**
 * 确认收货
 */
public class ConfirmgoodsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.view_red_)
    View viewRed;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.iv_reason)
    ImageView ivReason;
    @BindView(R.id.create_dealer_ht_rv)
    RecyclerView createDealerHtRv;
    @BindView(R.id.ll_modify_content)
    LinearLayout llModifyContent;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.ll_chayireason)
    LinearLayout llChayireason;

    @BindView(R.id.confirm_goods_rl_diff_price)
    RelativeLayout rlDiffPrice;
    @BindView(R.id.confirm_goods_tv_diff_type)
    TextView tvDiffType;
    @BindView(R.id.confirm_goods_tv_diff_price)
    TextView tvDiffPrice;
    @BindView(R.id.et_diff_explain)
    EditText etDiffExplain;
    @BindView(R.id.tv_reason)
    TextView tvReason;

    private ImageAddAdapter imageAddAdapter;
    private List<LocalMedia> imgData;
    private List<LocalMedia> imgPrelist = new ArrayList<LocalMedia>();
    private boolean isModify = false;//控制头部右侧文字按钮

    private String diffReasonIndex = "";

    private CommentAdapter<OrderGoodsBean> commentAdapter;
    private List<OrderGoodsBean> mDatas;

    //实取数量修改dialog
    private DecimalCountModifyDialog decimalCountModifyDialog;

    private String mOrderSonSn;//子订单编号
    private int groupItemIndex;//记录从哪个条目进来的索引
    private String pageTag;//记录从哪个页面进来


    private ReasonsDifferencesDialog reasonsDifferencesDialog;
    private PictureSelectUtils pictureSelectUtils;

    private int upLoadImgCount = 0;//上传图片的数量
    private int upLoadImgSuccessCount = 0;//上传图片成功的数量
    private String imgUrl = "";//服务器返回的url


    private static final String TAG = "ConfirmgoodsActivity";

    @Override
    public int getLayoutResId() {
        return R.layout.activity_confirmgoods;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("确认收货");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);

        initData();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupItemIndex = bundle.getInt("groupItemIndex", -1);
            pageTag = bundle.getString("pageTag", "");
            mOrderSonSn = bundle.getString("orderSonSn", "");
            String dataJson = bundle.getString("dataJson");
            Log.i("dataJson", "----" + dataJson);
            Type type = new TypeToken<ArrayList<OrderGoodsBean>>() {
            }.getType();
            mDatas = JsonUtils.parseArray(dataJson, type);
        }


        for (OrderGoodsBean orderGoodsBean : mDatas) {
            int isWhole = Integer.parseInt(orderGoodsBean.isWhole());
            int conversion = Integer.parseInt(orderGoodsBean.getItemConversion());
            double goodsNum = Integer.parseInt(orderGoodsBean.getGoodsNum());

            double bigUnitCount = (int) (goodsNum / conversion);//取模

            if (isWhole == 0) {//去拆零
                double smallUnitCount = BigDecimalUtils.sub(goodsNum, (bigUnitCount * conversion));//取余

                orderGoodsBean.setRecvBigUnitNum(bigUnitCount);
                orderGoodsBean.setRecvSmallUnitNum(smallUnitCount);
                orderGoodsBean.setActualBigUnitNum(bigUnitCount);
                orderGoodsBean.setActualSmallUnitNum(smallUnitCount);
            } else {
                orderGoodsBean.setRecvSmallUnitNum(bigUnitCount);
                orderGoodsBean.setActualSmallUnitNum(bigUnitCount);
            }
        }


        tvRight.setText("修改");
        setTvRightTextColor(Color.parseColor("#ff084D8E"));

        setTvRightClickListener("修改", v -> {
//            isModify = !isModify;
            isModify = true;
            if (isModify) {
                llModifyContent.setVisibility(View.VISIBLE);
                setTvRightTextColor(Color.parseColor("#ff999999"));
            } else {
                llModifyContent.setVisibility(View.GONE);
                setTvRightTextColor(Color.parseColor("#ff084d8e"));
            }
        });

        initAdapter();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(commentAdapter);

        recycleView.setNestedScrollingEnabled(false);
        SimpleItemDecoration decoration = SimpleItemDecoration.getInstance(this)
                .setDividerColor(R.color.gray_view)//设置分割线的颜色
                .setPaddingLeft(15)//PaddingLeft 15dp
                .setPaddingRight(0);//PaddingRight 15dp
        recycleView.addItemDecoration(decoration);

        initImgAdapter();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDatas = new ArrayList<>();
        imgData = new ArrayList<>();
        imgData.add(new LocalMedia());//添加初始数据
    }


    @OnClick({R.id.ll_chayireason, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_chayireason:
                if (reasonsDifferencesDialog != null) {
                    reasonsDifferencesDialog.show();
                    return;
                }
                reasonsDifferencesDialog = new ReasonsDifferencesDialog(this, (index, reason) -> {
                    diffReasonIndex = index + "";
                    tvReason.setText(reason);
                });
                reasonsDifferencesDialog.show();
                break;
            case R.id.btn_commit:
                if (isFastClick()) {
                    return;
                }
                if (isModify) {//提交差异单
                    checkParams(diffReasonIndex, MyUtils.getEtText(etDiffExplain));
                } else {//确认收货
                    confirmReceipt();
                }
                break;
        }
    }


    private void initAdapter() {
        commentAdapter = new CommentAdapter<OrderGoodsBean>(R.layout.item_confirmgoods, mDatas) {
            @Override
            public void setViewData(BaseViewHolder holder, OrderGoodsBean item, int position) {

                GlideUtils.showGildeImg(getContext(), item.getGoodsImageUrl(), holder.getView(R.id.iv_icon));
                holder.setText(R.id.item_confirmgoods_tv_goods_name, item.getGoodsName());
                holder.setText(R.id.item_confirmgoods_tv_spec_name, item.getGoodsSpecName());
                holder.setText(R.id.item_confirmgoods_tv_goods_unit, item.getGoodsItem());
                holder.setText(R.id.item_confirmgoods_tv_goods_price, SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice()));


                RelativeLayout rlBigUnit = holder.getView(R.id.item_confirmgoods_unit_big_rl);
                RelativeLayout rlSmallUnit = holder.getView(R.id.item_confirmgoods_unit_small_rl);

                rlBigUnit.setVisibility(View.GONE);
                rlSmallUnit.setVisibility(View.GONE);

                TextView tvBigUnitPrice = holder.getView(R.id.item_confirmgoods_tv_big_unit_price);
                TextView tvSmallUnitPrice = holder.getView(R.id.item_confirmgoods_tv_small_unit_price);

                TextView tvBigUnit = holder.getView(R.id.item_confirmgoods_tv_big_unit);
                TextView tvSmallUnit = holder.getView(R.id.item_confirmgoods_tv_small_unit);

                TextView tvBigUnitName = holder.getView(R.id.item_confirmgoods_tv_big_unit_name);
                TextView tvSmallUnitName = holder.getView(R.id.item_confirmgoods_tv_small_unit_name);
                TextView tvBigUnitCount = holder.getView(R.id.item_confirmgoods_tv_big_unit_count);
                TextView tvSmallUnitCount = holder.getView(R.id.item_confirmgoods_tv_small_unit_count);

                String recvBigUnitNum = BigDecimalUtils.removeInvalidZero(item.getRecvBigUnitNum() + "");
                String recvSmallUnitNum = BigDecimalUtils.removeInvalidZero(item.getRecvSmallUnitNum() + "");
                String actualBigUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualBigUnitNum() + "");
                String actualSmallUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualSmallUnitNum() + "");


                int isWhole = Integer.parseInt(item.isWhole());
                if (isWhole == 0) {//去拆零

                    rlBigUnit.setVisibility(View.VISIBLE);
                    rlSmallUnit.setVisibility(View.VISIBLE);

                    tvBigUnitCount.setText(actualBigUnitNum);
                    tvBigUnitName.setText(item.getGoodsItem());
                    tvBigUnit.setText(item.getGoodsItem());

                    tvSmallUnitCount.setText(actualSmallUnitNum);
                    tvSmallUnitName.setText(item.getGoodsBasicsSpecKey());
                    tvSmallUnit.setText(item.getGoodsBasicsSpecKey());

                    tvBigUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice()));
                    tvSmallUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsBasicsSpecPrice()));

                    holder.setText(R.id.item_confirmgoods_tv_yingqu, recvBigUnitNum + " " + item.getGoodsItem() + " " + recvSmallUnitNum + " " + item.getGoodsBasicsSpecKey());//应取数量

                } else {//不拆零
                    rlBigUnit.setVisibility(View.GONE);
                    rlSmallUnit.setVisibility(View.VISIBLE);

                    tvSmallUnitPrice.setText(SpannableUtils.changeSpannableTv("¥" + item.getGoodsPrice()));
                    tvSmallUnit.setText(item.getGoodsItem());

                    holder.setText(R.id.item_confirmgoods_tv_yingqu, recvSmallUnitNum + " " + item.getGoodsItem());//应取数量

                    tvBigUnitName.setVisibility(View.GONE);
                    tvBigUnitCount.setVisibility(View.GONE);

                    tvSmallUnitName.setText(item.getGoodsItem());
                    tvSmallUnitCount.setText(actualSmallUnitNum);

                }
            }

            @Override
            public void setEvent(BaseViewHolder holder, OrderGoodsBean item, int position) {
                //实取数量
                TextView tvBigUnitCount = holder.getView(R.id.item_confirmgoods_tv_big_unit_count);//大单位数量
                TextView tvSmallUnitCount = holder.getView(R.id.item_confirmgoods_tv_small_unit_count);//小单位数量

                RelativeLayout rlDiffDes = holder.getView(R.id.item_confirm_goods_rl_diff_des);
                TextView tvDiffMethod = holder.getView(R.id.item_confirm_goods_tv_diff_method);
                TextView tvDiffPrice = holder.getView(R.id.item_confirm_goods_tv_diff_price);


                int conversion = Integer.parseInt(item.getItemConversion());
                int isWhole = Integer.parseInt(item.isWhole());
                tvBigUnitCount.setOnClickListener(view -> {
                    if (!isModify) {
                        T.showToastyCenter(mContext, "请点击右上角按钮进行修改");
                        return;
                    }
                    decimalCountModifyDialog = new DecimalCountModifyDialog.Builder(mContext)
                            .setSupportDecimalPlaces(isWhole != 0 && conversion == 1 ? true : false)
                            .setDecimalPlaceLength(2)
                            .setHaveNumberLimit(false)
                            .setMaxNumber(item.getRecvBigUnitNum()+"")
                            .setOriginalNumber(item.getActualBigUnitNum()+"")
                            .setNumberOverToastStr("实取数量不能大于应取数量")
                            .setPositiveButton("确定", modifyGoodsNum -> {

                                item.setActualBigUnitNum(Double.valueOf(modifyGoodsNum));
                                String actualBigUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualBigUnitNum() + "");
                                tvBigUnitCount.setText(actualBigUnitNum);//设置实取数量

                                //更新差异金额数据(单条)
                                updateSingleDiffeAmountInfo(item, rlDiffDes, tvDiffMethod, tvDiffPrice);
                                //更新差异金额数据(所有)
                                updateDiffeAmountInfo();
                                decimalCountModifyDialog.dismiss();
                            }).setNegativeButton("取消", () -> decimalCountModifyDialog.dismiss()).create();
                    decimalCountModifyDialog.show();
                });
                tvSmallUnitCount.setOnClickListener(view -> {
                    if (!isModify) {
                        T.showToastyCenter(mContext, "请点击右上角按钮进行修改");
                        return;
                    }
                    decimalCountModifyDialog = new DecimalCountModifyDialog.Builder(mContext)
                            .setSupportDecimalPlaces(isWhole != 0 && conversion == 1 ? true : false)
                            .setDecimalPlaceLength(2)
                            .setHaveNumberLimit(false)
                            .setMaxNumber(item.getRecvSmallUnitNum()+"")
                            .setOriginalNumber(item.getActualSmallUnitNum()+"")
                            .setNumberOverToastStr("实取数量不能大于应取数量")
                            .setPositiveButton("确定", modifyGoodsNum -> {
                                double modifySmallUnitCount = Double.valueOf(modifyGoodsNum);

                                if (isWhole == 0) {//拆零进制
                                    int x = (int) (modifySmallUnitCount / conversion);//取模
                                    double y = BigDecimalUtils.sub(modifySmallUnitCount, (x * conversion));//取余

//                                    int x = modifySmallUnitCount / conversion;//取模
//                                    int y = modifySmallUnitCount % conversion;//取余
                                    item.setActualBigUnitNum(x + item.getActualBigUnitNum());
                                    String actualBigUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualBigUnitNum() + "");
                                    tvBigUnitCount.setText(actualBigUnitNum);

                                    item.setActualSmallUnitNum(y);
                                    String actualSmallUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualSmallUnitNum() + "");
                                    tvSmallUnitCount.setText(actualSmallUnitNum);
                                } else {
                                    item.setActualSmallUnitNum(modifySmallUnitCount);
                                    String actualSmallUnitNum = BigDecimalUtils.removeInvalidZero(item.getActualSmallUnitNum() + "");
                                    tvSmallUnitCount.setText(actualSmallUnitNum);
                                }

                                //更新差异金额数据(单条)
                                updateSingleDiffeAmountInfo(item, rlDiffDes, tvDiffMethod, tvDiffPrice);
                                //更新差异金额数据(所有)
                                updateDiffeAmountInfo();
                                decimalCountModifyDialog.dismiss();
                            }).setNegativeButton("取消", () -> decimalCountModifyDialog.dismiss()).create();
                    decimalCountModifyDialog.show();
                });
            }
        };
    }

    private void initImgAdapter() {
        //上传图片逻辑功能
        imageAddAdapter = new ImageAddAdapter(R.layout.item_image_upload, imgData);
        createDealerHtRv.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        createDealerHtRv.setAdapter(imageAddAdapter);
        imageAddAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (isFastClick()) {
                    return;
                }
                if (ContextCompat.checkSelfPermission(ConfirmgoodsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(ConfirmgoodsActivity.this, Manifest.permission.CAMERA)) {
                        Toast.makeText(ConfirmgoodsActivity.this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_LONG).show();
                    }
                    // 申请权限
                    ActivityCompat.requestPermissions(ConfirmgoodsActivity.this, new String[]{Manifest.permission.CAMERA}, PublicConstants.REQ_PERM_CAMERA);
                    return;
                }
                if (position == imgData.size() - 1) {
                    pictureSelect();
                } else {
                    if (TextUtils.isEmpty(imgData.get(imgData.size() - 1).getPath()) && TextUtils.isEmpty(imgData.get(imgData.size() - 1).getRealPath())) {
                        imgPrelist.clear();
                        for (int i = 0; i < imgData.size() - 1; i++) {
                            imgPrelist.add(imgData.get(i));
                        }
                        pictureSelectUtils.showImage(ConfirmgoodsActivity.this, position, imgPrelist);
                    }
                }
            }
        });
    }


    /**
     * 判断传入数据的实收数量是否被编辑过
     *
     * @return
     */
    private boolean isReceiverGoodsCountEdited(List<OrderGoodsBean> dataBean) {
        boolean flag = false;//用于判断差异金额布局是否显示  false为不显示 true为显示
        for (int i = 0; i < dataBean.size(); i++) {
            OrderGoodsBean item = dataBean.get(i);
            double recvBigUnitCount = Double.valueOf(item.getRecvBigUnitNum());
            double actualBigUnitCount = Double.valueOf(item.getActualBigUnitNum());

            double recvSmallUnitCount = Double.valueOf(item.getRecvSmallUnitNum());
            double actualSmallUnitCount = Double.valueOf(item.getActualSmallUnitNum());

            if (recvBigUnitCount != actualBigUnitCount || recvSmallUnitCount != actualSmallUnitCount) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * 更新差异金额信息(总的)
     */
    private void updateDiffeAmountInfo() {
        boolean flag = isReceiverGoodsCountEdited(mDatas);
        rlDiffPrice.setVisibility(flag ? View.VISIBLE : View.GONE);

        double differenceAmount = calculateDifferenceAmount(mDatas);
        tvDiffType.setText(differenceAmount < 0 ? "应付总金额：" : "应退总金额：");
        tvDiffPrice.setText(SpannableUtils.changeSpannableTv("¥" + BigDecimalUtils.keepTwoDecimalPlaces(Math.abs(differenceAmount))));
    }


    /**
     * 更新差异金额信息(单个条目)
     */
    private void updateSingleDiffeAmountInfo(OrderGoodsBean item, RelativeLayout rlDiffDes, TextView tvDiffMethod, TextView tvDiffPrice) {
        List<OrderGoodsBean> singleItem = new ArrayList<>();
        singleItem.add(item);
        boolean flag = isReceiverGoodsCountEdited(singleItem);

        if (flag) {
            rlDiffDes.setVisibility(View.VISIBLE);
            double money = calculateDifferenceAmount(singleItem);
            String moneyStr = BigDecimalUtils.keepTwoDecimalPlaces(Math.abs(money));
            tvDiffMethod.setText(money < 0 ? "应付金额" : "应退金额");
            tvDiffPrice.setText(SpannableUtils.changeSpannableTv("¥" + moneyStr));
        } else {
            rlDiffDes.setVisibility(View.GONE);
        }
    }


    /**
     * 计算差异金额
     */
    public double calculateDifferenceAmount(List<OrderGoodsBean> dataBean) {
        double diffMoney = 0;
        for (OrderGoodsBean orderGoodsBean : dataBean) {

            int isWhole = Integer.parseInt(orderGoodsBean.isWhole());
            if (isWhole == 0) {//去拆零
                double recvBigUnitCount = Double.valueOf(orderGoodsBean.getRecvBigUnitNum());
                double actualBigUnitCount = Double.valueOf(orderGoodsBean.getActualBigUnitNum());

                double recvSmallUnitCount = Double.valueOf(orderGoodsBean.getRecvSmallUnitNum());
                double actualSmallUnitCount = Double.valueOf(orderGoodsBean.getActualSmallUnitNum());

                String bigUnitPrice = orderGoodsBean.getGoodsPrice();
                diffMoney += BigDecimalUtils.mul(bigUnitPrice, BigDecimalUtils.sub(recvBigUnitCount, actualBigUnitCount) + "");

                String smallUnitPrice = orderGoodsBean.getGoodsBasicsSpecPrice();
                diffMoney += BigDecimalUtils.mul(smallUnitPrice, BigDecimalUtils.sub(recvSmallUnitCount, actualSmallUnitCount) + "");

            } else {//不拆零

                double recvSmallUnitCount = Double.valueOf(orderGoodsBean.getRecvSmallUnitNum());
                double actualSmallUnitCount = Double.valueOf(orderGoodsBean.getActualSmallUnitNum());

                String smallUnitPrice = orderGoodsBean.getGoodsPrice();
                diffMoney += BigDecimalUtils.mul(smallUnitPrice, BigDecimalUtils.sub(recvSmallUnitCount, actualSmallUnitCount) + "");

            }
        }
        return diffMoney;
    }


    /**
     * 文件上传(单图for循环上传😵)
     */
    private void upLoadFile() {
        imgUrl = "";
        for (int i = 0; i < imgData.size(); i++) {
            File file = imageAddAdapter.getImgFile(imgData.get(i));
            if (file == null) {
                return;
            }
            if (file.exists()) {
                upLoadImgCount++;
                OkGo.<String>post(UrlContent.UPLOAD_IMG_SINGLE)
                        .params("file", file)
                        .tag(this)
                        .execute(new DialogCallback(this, true) {
                            @Override
                            public void onSuccess(Response<String> response) {
                                upLoadImgSuccessCount++;
                                String requestTag = "" + response.getRawCall().request().tag();
                                Log.i(TAG, "------------成功上传的数量:" + upLoadImgSuccessCount + "---requestTag:" + requestTag);
                                UpLoadIVBean imgsBean = JsonUtils.parse(response.body(), UpLoadIVBean.class);
                                imgsBean.getImageUrl();

                                for (int j = 0; j < imgData.size(); j++) {
                                    if ((j + "").equals(requestTag)) {
                                        //借用originalPath此字段
                                        imgData.get(j).setOriginalPath(imgsBean.getImageUrl());
                                        break;
                                    }
                                }
                                if (upLoadImgSuccessCount == upLoadImgCount) {
                                    upLoadImgCount = 0;
                                    upLoadImgSuccessCount = 0;

                                    for (LocalMedia localMedia : imgData) {
                                        if (!TextUtils.isEmpty(localMedia.getOriginalPath())) {
                                            imgUrl += localMedia.getOriginalPath() + ",";
                                        }
                                    }
                                    if (imgUrl.length() > 0) {
                                        imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
                                    }
                                    Log.i(TAG, "------------imgUrl:" + imgUrl);
                                    createDiffOrder();
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                T("网络开小差，请稍后重试 ~");
                            }
                        });
            }
        }
    }


    /**
     * 文件上传(多图上传😵)
     */
    private void upLoadMutiFile() {
        imgUrl = "";
        List<File> fileList = imageAddAdapter.getListFile();
        OkGo.<String>post(UrlContent.UPLOAD_IMG_MUTIBLE)
                .addFileParams("files", fileList)
                .tag(this)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.RequestCallBack() {
                            @Override
                            public void onSuccess() {
                                UpLoadIVBean upLoadIVBean = JsonUtils.parse(response.body(), UpLoadIVBean.class);
                                for (int i = 0; i < upLoadIVBean.getImgs().size(); i++) {
                                    UpLoadIVBean.ImgsBean imgsBean = upLoadIVBean.getImgs().get(i);
                                    if (i == 0) {
                                        imgUrl = imgsBean.getImageUrl();
                                    } else {
                                        imgUrl = imgUrl + "," + imgsBean.getImageUrl();
                                    }
                                }
                                if (!TextUtils.isEmpty(imgUrl)) {
                                    createDiffOrder();
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }


    /**
     * 图片选择
     */
    private void pictureSelect() {
        if (imgData.size() - 1 >= 3) {
            T.showToastyCenter(mContext, "最多可选3张");
            return;
        }
        if (pictureSelectUtils == null) {
            pictureSelectUtils = new PictureSelectUtils();
        }

        //再次选择的最大数量
        int maxSelectCount = 4 - imgData.size();

        pictureSelectUtils.pictureSelect(this, maxSelectCount);
        pictureSelectUtils.setPictureSelect(new PictureSelectUtils.PictureSelectImpl() {
            @Override
            public void onResult(List<LocalMedia> result) {
                for (LocalMedia localMedia : result) {
                    imgData.add(0, localMedia);
                }
                imageAddAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                Log.i("Picture", "------------oncancel" + imgData.size());
            }
        });
    }


    /**
     * 上传参数校验
     */
    private void checkParams(String diffReason, String diffExplain) {
        if (TextUtils.isEmpty(diffReason)) {
            T("请选择差异原因");
            return;
        }
        if ("".equals(diffExplain)) {
            T("请填写差异说明");
            return;
        }

        boolean flag = false;
        for (OrderGoodsBean declareOrderGoodsBean : mDatas) {
            double recvBigUnitCount = Double.valueOf(declareOrderGoodsBean.getRecvBigUnitNum());
            double actualBigUnitCount = Double.valueOf(declareOrderGoodsBean.getActualBigUnitNum());

            double recvSmallUnitCount = Double.valueOf(declareOrderGoodsBean.getRecvSmallUnitNum());
            double actualSmallUnitCount = Double.valueOf(declareOrderGoodsBean.getActualSmallUnitNum());

            if (recvBigUnitCount != actualBigUnitCount || recvSmallUnitCount != actualSmallUnitCount) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            T("请修改实取数量");
            return;
        }

        if (imgData.size() == 1) {
            T("请上传凭证");
            return;
        }
//        upLoadFile();
        upLoadMutiFile();
//        createDiffOrder();
    }

    /**
     * 正常确认收货
     */
    private void confirmReceipt() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSonSn", mOrderSonSn);
        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_CONFIRM)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                T("收货成功");
                                registEventBus(2, "DecOrderReceivedSuccess");
                                finish();
                            }
                        });
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }

    /**
     * 创建差异单
     */
    private void createDiffOrder() {
        //提交差异单商品参数
        List<HashMap<String, Object>> diffOrderGoodsList = new ArrayList<>();

        for (OrderGoodsBean orderGoodsBean : mDatas) {
            double recvBigUnitCount = Double.valueOf(orderGoodsBean.getRecvBigUnitNum());
            double actualBigUnitCount = Double.valueOf(orderGoodsBean.getActualBigUnitNum());

            double recvSmallUnitCount = Double.valueOf(orderGoodsBean.getRecvSmallUnitNum());
            double actualSmallUnitCount = Double.valueOf(orderGoodsBean.getActualSmallUnitNum());

            if (recvBigUnitCount != actualBigUnitCount || recvSmallUnitCount != actualSmallUnitCount) {
                HashMap<String, Object> diffOrderGoodParams = new HashMap<>();
                diffOrderGoodParams.put("goodsId", orderGoodsBean.getGoodsSn());
                diffOrderGoodParams.put("specId", orderGoodsBean.getGoodsSpecId());

                int conversion = Integer.parseInt(orderGoodsBean.getItemConversion());
                int isWhole = Integer.parseInt(orderGoodsBean.isWhole());
                double receivedGoodsNum;
                if (isWhole == 0) {//拆零
                    receivedGoodsNum = BigDecimalUtils.add(BigDecimalUtils.mul(conversion, actualBigUnitCount), actualSmallUnitCount);
                } else {//不拆零
                    receivedGoodsNum = BigDecimalUtils.mul(conversion, actualSmallUnitCount);
                }

                diffOrderGoodParams.put("receivedGoodsNum", receivedGoodsNum);
                diffOrderGoodParams.put("itemConversion", conversion);
                diffOrderGoodsList.add(diffOrderGoodParams);
            }
        }

        String[] imgArray = imgUrl.split(",");
        String[] diffReasonArray = diffReasonIndex.split(",");
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderSonSn", mOrderSonSn);//子订单编号
        params.put("differencesReason", diffReasonArray);//差异原因
        params.put("differencesExplain", MyUtils.getEtText(etDiffExplain));//差异单说明
        params.put("differenceImgs", imgArray);//图片集合(数组)
        params.put("list", diffOrderGoodsList);//商品集合

        Log.i(TAG, "-----------" + JsonUtils.toJsonString(params));

//        if (0 == 0) {
//            return;
//        }

        RequestBody body = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.BUSAPP_ORDER_CREATE_DIFF)
                .tag(this)
                .upRequestBody(body)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                T(baseBean.getMsg());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        registEventBus(2, "DiffOrderCreated");
                                        finish();
//                                        Intent intent = new Intent(ConfirmgoodsActivity.this, DiffOrderListActivity.class);
//                                        Bundle bundle = new Bundle();
//                                        bundle.putInt("targetDiffPageIndex", 1);
//                                        intent.putExtras(bundle);
//                                        startActivity(intent);
                                    }
                                }, 500);
                            }
                        });
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");
                    }
                });
    }

    private void registEventBus(int tag, String doTag) {
        DeclareOrderDetailEvent event = new DeclareOrderDetailEvent();
        event.setTag(tag);
        HashMap<String, Object> map = new HashMap<>();
        map.put("groupItemIndex", groupItemIndex);
        map.put("doTag", doTag);
        map.put("pageTag", pageTag);
        event.setObject(map);
        EventBus.getDefault().post(event);
    }

}
