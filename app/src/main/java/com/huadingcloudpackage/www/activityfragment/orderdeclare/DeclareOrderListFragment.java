package com.huadingcloudpackage.www.activityfragment.orderdeclare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.MyFragmentPagerAdapter;
import com.huadingcloudpackage.www.base.BaseLazyFragment;
import com.huadingcloudpackage.www.eventbus.DeclareOrderDetailEvent;
import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.StatusBarUtils;
import com.huadingcloudpackage.www.widget.MagicIndicatorCommNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 订单的fragment
 */
public class DeclareOrderListFragment extends BaseLazyFragment {
    @BindView(R.id.fragment_declare_order_iv_return)
    ImageView ivReturn;
    @BindView(R.id.fragment_declare_order_viewpager)
    ViewPager viewpager;

    @BindView(R.id.fragment_declare_order_edit_search)
    EditText etSearch;


    @BindView(R.id.fragment_declare_order_magic_indicator)
    MagicIndicator magicIndicator;

    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] listTitles;

    private DeclareOrderFragment allOrderFragment;
    private DeclareOrderFragment toBePaidFragment;
    private DeclareOrderFragment toBeDeliveredFragment;
    private DeclareOrderFragment toBeReceivedFragment;
    private DeclareOrderFragment completedFragment;

    //默认进来选中的页面 默认为0
    private int mTargetDeclarePageIndex = 0;

    private static final String TAG = "DeclareOrderListFg";


    private ObservableEmitter<Integer> myEmitter;
    private Observable<Integer> observable;

    public DeclareOrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_declare_order_list, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(View view) {
        super.initView(view);
        StatusBarUtils.changStatusIconCollor(getActivity(), false);

        Bundle bundle = getActivity().getIntent().getExtras();
        //0 全部 1 待付款 2 待发货 3 待收货 4 已完成
        if (bundle != null) {
            mTargetDeclarePageIndex = bundle.getInt("targetDeclarePageIndex", 0);
        }


        allOrderFragment = DeclareOrderFragment.getInstance(-1);
        toBePaidFragment = DeclareOrderFragment.getInstance(0);
        toBeDeliveredFragment = DeclareOrderFragment.getInstance(1);
        toBeReceivedFragment = DeclareOrderFragment.getInstance(2);
        completedFragment = DeclareOrderFragment.getInstance(3);

        allOrderFragment.setShowloadingWithView(true);
        toBePaidFragment.setShowloadingWithView(true);
        toBeDeliveredFragment.setShowloadingWithView(true);
        toBeReceivedFragment.setShowloadingWithView(true);
        completedFragment.setShowloadingWithView(true);


        fragmentList.add(allOrderFragment);
        fragmentList.add(toBePaidFragment);
        fragmentList.add(toBeDeliveredFragment);
        fragmentList.add(toBeReceivedFragment);
        fragmentList.add(completedFragment);

        listTitles = new String[]{"全部", "待付款", "待发货", "待收货", "已完成"};

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, listTitles);
        viewpager.setAdapter(pagerAdapter);
        MyVpPageChangeListener myVpPageChangeListener = new MyVpPageChangeListener();
        viewpager.addOnPageChangeListener(myVpPageChangeListener);

        initMagicIndicator();

        initObservable();

        if (mTargetDeclarePageIndex == 0) {
            myVpPageChangeListener.onPageSelected(0);
        } else {
            setViewPagerCurrentItem(mTargetDeclarePageIndex);
        }
    }

    private class MyVpPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (etSearch.getText().toString().length() > 0) {
                etSearch.setText("");
            }
            etSearch.clearFocus();

            mTargetDeclarePageIndex = position;
            myEmitter.onNext(position);
            Log.i(TAG, "-------onPageSelected" + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initObservable() {
        observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                myEmitter = emitter;
            }
        }).debounce(Constants.TabDebounceTime, TimeUnit.MILLISECONDS);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        requsetOrderData("");
                    }
                });
    }


    public void setViewPagerCurrentItem(int pageIndex) {
        viewpager.setCurrentItem(pageIndex);
    }


    private void initMagicIndicator() {
        MagicIndicatorCommNavigator commonNavigator = new MagicIndicatorCommNavigator(getActivity(), listTitles, viewpager, true);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewpager);
    }


    public void requsetOrderData(String searchOrderSn) {
        if (mTargetDeclarePageIndex == 0) {//全部
            allOrderFragment.tabSelectRefreshData(searchOrderSn);
        } else if (mTargetDeclarePageIndex == 1) {//待付款
            toBePaidFragment.tabSelectRefreshData(searchOrderSn);
        } else if (mTargetDeclarePageIndex == 2) {//待发货
            toBeDeliveredFragment.tabSelectRefreshData(searchOrderSn);
        } else if (mTargetDeclarePageIndex == 3) {//待收货
            toBeReceivedFragment.tabSelectRefreshData(searchOrderSn);
        } else if (mTargetDeclarePageIndex == 4) {//已完成
            completedFragment.tabSelectRefreshData(searchOrderSn);
        }
    }

    @OnClick({R.id.fragment_declare_order_iv_return, R.id.fragment_declare_order_iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_declare_order_iv_return:
                getActivity().finish();
                break;
            case R.id.fragment_declare_order_iv_search:
                String searchStr = etSearch.getText().toString();
                requsetOrderData(searchStr);
                break;
            default:
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void itemUpdate(DeclareOrderDetailEvent event) {
        Log.i(TAG, "----------event:" + JsonUtils.toJsonString(event));
        int tag = event.getTag();
        if (tag == 0) {//待付款（取消订单|立即支付）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            String pageTag = (String) map.get("pageTag");
            int indexItemUpdate = (int) map.get("groupItemIndex");
            if (indexItemUpdate == -1) {
                return;
            }
            if (doTag.equals("DecOrderCancel")) {
                if ("全部".equals(pageTag)) {//在全部tab更新该条目为已取消状态
                    allOrderFragment.notifyItemToCancel(indexItemUpdate);
                } else {//在待付款tab下则删除该条目
                    toBePaidFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                }
            } else if (doTag.equals("DecOrderPaySuccess")) {
                if ("全部".equals(pageTag)) {//在全部tab更新该条目为待发货状态
                    allOrderFragment.notifyItemToDeliver(indexItemUpdate);
                } else {//在待付款tab下则删除该条目
                    toBePaidFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                }
            }
        } else if (tag == 1) {//待发货（提醒发货）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            if (doTag.equals("DecOrderRemind")) {
                int indexItemUpdate = (int) map.get("groupItemIndex");
                if (indexItemUpdate == -1) {
                    return;
                }
                String pageTag = (String) map.get("pageTag");
                if ("全部".equals(pageTag)) {
                    allOrderFragment.notifyItemToReminded(indexItemUpdate);
                } else {
                    toBeDeliveredFragment.notifyItemToReminded(indexItemUpdate);
                }
            }
        } else if (tag == 2) {//待收货（确认收货）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            String pageTag = (String) map.get("pageTag");
            int indexItemUpdate = (int) map.get("groupItemIndex");
            if (indexItemUpdate == -1) {
                return;
            }
            if (doTag.equals("DecOrderReceivedSuccess")) {//订阅了从ConfirmgoodsActivity界面发布的事件（收货成功）
                if ("全部".equals(pageTag)) {//在全部tab更新该条目为已完成状态
                    allOrderFragment.notifyItemToCompleted(indexItemUpdate);
                } else {
                    toBeReceivedFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                }
            } else if (doTag.equals("DiffOrderCreated")) {//订阅了从ConfirmgoodsActivity界面发布的事件（差异单创建成功）
                if ("全部".equals(pageTag)) {//在全部tab删除该条目
                    allOrderFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                } else {//在待收货tab删除该条目
                    toBeReceivedFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                }
            }
        } else if (tag == 3) {//已完成（删除订单）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            String pageTag = (String) map.get("pageTag");
            int indexItemUpdate = (int) map.get("groupItemIndex");
            if (indexItemUpdate == -1) {
                return;
            }
            if (doTag.equals("DecOrderDelete")) {
                if ("全部".equals(pageTag)) {//在全部tab更新该条目为已完成状态
                    allOrderFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                } else {
                    completedFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
                }
            }
        } else if (tag == 4) {//已取消（删除订单）
            HashMap<String, Object> map = (HashMap<String, Object>) event.getObject();
            String doTag = (String) map.get("doTag");
            String pageTag = (String) map.get("pageTag");
            int indexItemUpdate = (int) map.get("groupItemIndex");
            if (indexItemUpdate == -1) {
                return;
            }
            if (doTag.equals("DecOrderDelete")) {
                allOrderFragment.notifyRemoveRvListWithAnima(indexItemUpdate);
            }
        }
    }

}