package com.huadingcloudpackage.www.activity.logisticsdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.JsonSyntaxException;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.LogisticsDetailBean;
import com.huadingcloudpackage.www.bean.LogisticsInfoBean;
import com.huadingcloudpackage.www.bean.LogisticsInfoBean.LogisticsInfoList;
import com.huadingcloudpackage.www.callback.DialogCallback;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.SimpleDataFormatUtils;
import com.huadingcloudpackage.www.util.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;

/**
 *
 */
public class LogisticsDetailActivity extends BaseActivity {

    @BindView(R.id.logistics_detail_tv_driver_name)
    TextView tvDriverName;
    @BindView(R.id.logistics_detail_tv_driver_tel)
    TextView tvDriverTel;
    @BindView(R.id.logistics_detail_rv_list)
    RecyclerView rvList;
    @BindView(R.id.logistics_detail_tv_receiver_address)
    TextView tvReceiverAddress;
    @BindView(R.id.logistics_detail_ll_top)
    LinearLayout llTop;
    @BindView(R.id.logistics_detail_rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.logistics_detail_iv)
    ImageView iv;
    @BindView(R.id.logistics_detail_tv_no_data)
    TextView tvNoData;

    private CommentAdapter<LogisticsInfoList> commentAdapter;
    public List<LogisticsInfoList> mDatas = new ArrayList<>();


    private String omsOutSn;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistics_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("物流详情");

        Bundle bundle = getIntent().getExtras();
        omsOutSn = bundle.getString("omsOutSn","");

        initAdapter();
        rvList.setLayoutManager(new LinearLayoutManager(LogisticsDetailActivity.this));
        rvList.setNestedScrollingEnabled(false);
        rvList.setAdapter(commentAdapter);

        getLogisticsInfo();
    }


    private void initAdapter() {
        commentAdapter = new CommentAdapter<LogisticsInfoList>(R.layout.item_logistics_detail, mDatas) {
            @Override
            public void setViewData(BaseViewHolder holder, LogisticsInfoList item, int position) {
                String time = item.getCreateTime();//2020-08-22 15:25:51
                String formatTimeMMdd = SimpleDataFormatUtils.dateFormatNeed(time, "yyyy-MM-dd HH:mm:ss", "MM-dd");
                String formatTimeHHmm = SimpleDataFormatUtils.dateFormatNeed(time, "yyyy-MM-dd HH:mm:ss", "HH:mm");
                ImageView ivStatus = holder.getView(R.id.item_logistics_detail_rv_list_iv_status);

                holder.setText(R.id.item_logistics_detail_rv_list_tv_data, formatTimeMMdd);
                holder.setText(R.id.item_logistics_detail_rv_list_tv_time, formatTimeHHmm);
                holder.setText(R.id.item_logistics_detail_rv_list_tv_type, item.getOrderRemark());
                holder.setText(R.id.item_logistics_detail_rv_list_tv_location, time);

                View lineMatch = holder.getView(R.id.item_logistics_detail_rv_list_line_bottom);
                View lineTop = holder.getView(R.id.item_logistics_detail_rv_list_line_top);

                lineMatch.setVisibility(position == mDatas.size() - 1 ? View.GONE : View.VISIBLE);
//                lineTop.setVisibility(position == mDatas.size() - 1 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void setEvent(BaseViewHolder holder, LogisticsInfoList item, int position) {

            }
        };
    }

    /**
     * 获取物流详细(自营订单)
     */
    private void getLogisticsInfo() {
        HashMap<String, Object> params = new HashMap<>();
//        params.put("omsOutSn", "OCK00008556");
        params.put("omsOutSn", omsOutSn);
        RequestBody requestBody = RequestBody.create(UrlContent.JSON, JsonUtils.toJsonString(params));
        OkGo.<String>post(UrlContent.GET_LOGISTICS_INFO)
                .tag(this)
                .upRequestBody(requestBody)
                .execute(new DialogCallback(this, true) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, () -> {

                            LogisticsInfoBean infoBean;
                            try {
                                infoBean = JsonUtils.parse(response.body(), LogisticsInfoBean.class);
                                tvNoData.setVisibility(View.GONE);
                                llTop.setVisibility(View.VISIBLE);
                                rlContent.setVisibility(View.VISIBLE);

                                LogisticsInfoBean.LogisticsInfoData data = infoBean.getData();

                                LogisticsInfoBean.LogisticsInfoDriver driver = data.getDriver();

                                tvDriverName.setText("司机姓名：" + driver.getName());
                                tvDriverTel.setText("手机号码：" + driver.getTel());
                                tvReceiverAddress.setText("收货地址：" + driver.getDeliveryAddress());
                                mDatas.clear();
                                mDatas.addAll(data.getList());
                                if (mDatas.size() > 0) {
                                    rlContent.setVisibility(View.VISIBLE);
                                }
                                commentAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                tvNoData.setVisibility(View.VISIBLE);
                                llTop.setVisibility(View.GONE);
                                rlContent.setVisibility(View.GONE);
                                return;
                            }

//                            tvNoData.setVisibility(View.GONE);
//                            llTop.setVisibility(View.VISIBLE);
//                            rlContent.setVisibility(View.VISIBLE);
//
//                            LogisticsInfoBean.LogisticsInfoData data = infoBean.getData();
//
//                            LogisticsInfoBean.LogisticsInfoDriver driver = data.getDriver();
//
//                            tvDriverName.setText("司机姓名：" + driver.getName());
//                            tvDriverTel.setText("手机号码：" + driver.getTel());
//                            tvReceiverAddress.setText("收货地址：" + driver.getDeliveryAddress());
//                            mDatas.clear();
//                            mDatas.addAll(data.getList());
//                            if (mDatas.size() > 0) {
//                                rlContent.setVisibility(View.VISIBLE);
//                            }
//                            commentAdapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T("网络开小差，请稍后重试 ~");

                    }
                });
    }

}
