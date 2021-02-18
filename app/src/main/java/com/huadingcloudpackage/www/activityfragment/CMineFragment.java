package com.huadingcloudpackage.www.activityfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.addresslist.AddressListActivity;
import com.huadingcloudpackage.www.activity.collection.MyCollectionActivity;
import com.huadingcloudpackage.www.activity.order.orderdiff.DiffOrderListActivity;
import com.huadingcloudpackage.www.activity.mine.MeSetActivity;
import com.huadingcloudpackage.www.activity.mine.MineAccountActivity;
import com.huadingcloudpackage.www.activity.order.orderdeclare.DeclareOrderListActivity;
import com.huadingcloudpackage.www.base.BaseLazyFragment;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.MineBean;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.makeramen.roundedimageview.RoundedImageView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CMineFragment extends BaseLazyFragment {

    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.img_head)
    RoundedImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_myaccount)
    LinearLayout llMyaccount;
    @BindView(R.id.tv_myaccount)
    TextView tvMyaccount;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_order)
    ImageView tvOrder;
    @BindView(R.id.iv_order_right)
    ImageView ivOrderRight;
    @BindView(R.id.rl_mine_order)
    RelativeLayout rlMineOrder;
    @BindView(R.id.tv_daifukuan_num)
    TextView tvDaifukuanNum;
    @BindView(R.id.ll_daifukuan)
    LinearLayout llDaifukuan;
    @BindView(R.id.tv_daifahuo_num)
    TextView tvDaifahuoNum;
    @BindView(R.id.ll_daifahuo)
    LinearLayout llDaifahuo;
    @BindView(R.id.tv_daishouhuo_num)
    TextView tvDaishouhuoNum;
    @BindView(R.id.ll_daishouhuo)
    LinearLayout llDaishouhuo;
    @BindView(R.id.tv_daipingjia_num)
    TextView tvDaipingjiaNum;
    @BindView(R.id.ll_daipingjia)
    LinearLayout llDaipingjia;
    @BindView(R.id.ll_shouhou)
    LinearLayout llShouhou;
    @BindView(R.id.tv_shouhou_num)
    TextView tvShouhouNum;
    @BindView(R.id.ll_myaddress)
    LinearLayout llMyaddress;
    @BindView(R.id.ll_mycollection)
    LinearLayout llMycollection;
    private View view;
    private String phonenumber;
    private String money;
    private boolean isFirstShow = true;
    public CMineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getMineData();
    }

    @OnClick({R.id.iv_setting, R.id.ll_myaccount, R.id.rl_mine_order, R.id.ll_daifukuan, R.id.ll_daifahuo, R.id.ll_daishouhuo, R.id.ll_daipingjia, R.id.ll_shouhou, R.id.ll_myaddress, R.id.ll_mycollection})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_setting://设置
                bundle.putString("phone", phonenumber);
                startActivity(MeSetActivity.class, bundle);
                break;
            case R.id.ll_myaccount://我的账户
                bundle.putString("money", money);
                startActivity(MineAccountActivity.class, bundle);
                break;
            /**
             * 订单
             * 0 全部  1 待付款  2 待发货  3 待收货  4 待评价（已完成）
             */
            case R.id.rl_mine_order:
                bundle.putInt("targetDeclarePageIndex", 0);
                startActivity(DeclareOrderListActivity.class, bundle);
                break;
            case R.id.ll_daifukuan:
                bundle.putInt("targetDeclarePageIndex", 1);
                startActivity(DeclareOrderListActivity.class, bundle);
                break;
            case R.id.ll_daifahuo:
                bundle.putInt("targetDeclarePageIndex", 2);
                startActivity(DeclareOrderListActivity.class, bundle);
                break;
            case R.id.ll_daishouhuo:
                bundle.putInt("targetDeclarePageIndex", 3);
                startActivity(DeclareOrderListActivity.class, bundle);
                break;
            case R.id.ll_daipingjia:
                bundle.putInt("targetDeclarePageIndex", 4);
                startActivity(DeclareOrderListActivity.class, bundle);
                break;
            case R.id.ll_shouhou://售后
                startActivity(DiffOrderListActivity.class);
                break;
            case R.id.ll_myaddress://我的地址列表
                startActivity(AddressListActivity.class);
                break;
            case R.id.ll_mycollection://我的收藏
                startActivity(MyCollectionActivity.class);
                break;
        }
    }


    /**
     * 我的主页
     */
    private void getMineData() {
        if (isFirstShow) {
            showLoading();
            isFirstShow = false;
        }
        OkGo.<String>post(UrlContent.MINEDATA)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                MineBean mineBean = JsonUtils.parse(response.body(), MineBean.class);
                                String exhibiName = mineBean.getExhibiName();
                                phonenumber = mineBean.getPhonenumber();
                                String address = mineBean.getAddress();
                                money = mineBean.getMoney();
                                int unpaid = mineBean.getUnpaid();
                                int notYetShipped = mineBean.getNotYetShipped();
                                int notConsignee = mineBean.getNotConsignee();
                                int afterSale = mineBean.getAfterSale();
                                int completed = mineBean.getCompleted();

                                tvName.setText(exhibiName);
                                tvMyaccount.setText("¥ " + money);//账
                                // 户余额
                                tvPhone.setText("电话：" + phonenumber);
                                tvAddress.setText("地址：" + address);
                                if (unpaid > 0) {
                                    setBage(tvDaifukuanNum, unpaid);
                                    tvDaifukuanNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvDaifukuanNum.setVisibility(View.GONE);
                                }

                                if (notYetShipped > 0) {
                                    setBage(tvDaifahuoNum, notYetShipped);
                                    tvDaifahuoNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvDaifahuoNum.setVisibility(View.GONE);
                                }

                                if (notConsignee > 0) {
                                    setBage(tvDaishouhuoNum, notConsignee);
                                    tvDaishouhuoNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvDaishouhuoNum.setVisibility(View.GONE);
                                }

                                if (completed > 0) {
                                    setBage(tvDaipingjiaNum, completed);
                                    tvDaipingjiaNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvDaipingjiaNum.setVisibility(View.GONE);
                                }

                                if (afterSale > 0) {
                                    setBage(tvShouhouNum, afterSale);
                                    tvShouhouNum.setVisibility(View.VISIBLE);
                                } else {
                                    tvShouhouNum.setVisibility(View.GONE);
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

    private void setBage(TextView tv, int num) {
        if (num > 99) {
            tv.setTextSize(7f);
            tv.setText("99+");
        } else {
            tv.setTextSize(9f);
            tv.setText(num + "");
        }
    }

}
