package com.huadingcloudpackage.www.activity.addresslist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.activity.newaddress.NewAddressActivity;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.AddressListBean;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.dialog.CommomDialog;
import com.huadingcloudpackage.www.eventbus.OrderCommitEvent;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * @author lige
 * 描述：我的地址
 */
public class AddressListActivity extends BaseActivity {
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
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;

    CommentAdapter<AddressListBean.DataBean> commentAdapter;
    public List<AddressListBean.DataBean> addressList = new ArrayList<>();

    private String pageTag = "";//从订单结算界面进来为OrderCommitActivity   默认为""

    @Override
    public int getLayoutResId() {
        return R.layout.activity_addresslist;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setTitle("我的地址");
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        getPageTag();
        initAdapter();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(commentAdapter);

    }

    private void getPageTag() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pageTag = bundle.getString("pageTag");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getListData(PreferenceManager.instance().getUserId());
    }

    private void initAdapter() {
        commentAdapter = new CommentAdapter<AddressListBean.DataBean>(R.layout.item_address_list, addressList) {

            private LinearLayout llDel;
            private LinearLayout llEdit;

            @Override
            public void setViewData(BaseViewHolder holder, AddressListBean.DataBean item, int position) {
                //编辑地址
            /*    llEdit = holder.getView(R.id.ll_edit);
                llDel = holder.getView(R.id.ll_del);
                if ("1".equals(item.getAddressStatus())) {
                    holder.getView(R.id.iv_default_address).setBackgroundResource(R.mipmap.icon_select);
                } else {
                    holder.getView(R.id.iv_default_address).setBackgroundResource(R.mipmap.icon_normal);
                }
*/
                holder.setText(R.id.tv_address_name, item.getLinkName());
                holder.setText(R.id.tv_address_phone, item.getCusPhone());
                holder.setText(R.id.tv_address, item.getCusAddressAll());
            }

            @Override
            public void setEvent(BaseViewHolder holder, AddressListBean.DataBean item, int position) {

          /*      holder.getView(R.id.iv_default_address).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUptDefaData(item.getId());
                    }
                });


                llEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//编辑地址
                        Bundle bundleEdit = new Bundle();
                        bundleEdit.putString("index", "0");
                        bundleEdit.putInt("id", item.getId());
                        bundleEdit.putString("name", item.getReceiveMember());
                        bundleEdit.putString("phone", item.getReceiveCellphone());
                        bundleEdit.putString("adddress", item.getAddress());
                        bundleEdit.putString("province", item.getProvince());
                        bundleEdit.putString("city", item.getCity());
                        bundleEdit.putString("county", item.getCounty());

                        startActivity(NewAddressActivity.class, bundleEdit);
                    }
                });


                llDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CommomDialog(AddressListActivity.this, "确定删除此地址？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    getDelData(item.getId());
                                }
                            }
                        }).setTitle("删除地址").setNegativeButton("取消").setPositiveButton("确定").show();
                    }
                });

               //从结算界面进来 可点击整个条目切换收货地址
                holder.getView(R.id.item_address_list_root).setOnClickListener(view -> {
                    if ("OrderCommitActivity".equals(pageTag)) {
                        OrderCommitEvent event = new OrderCommitEvent();
                        event.setTag(0);
                        event.setObject(item);
                        EventBus.getDefault().post(event);
                        finish();
                    }

                    //从待付款详情界面进来 可点击整个条目切换收货地址
                    if ("DaiFuKuanDetailActivity".equals(pageTag)) {
                        OrderCommitEvent event = new OrderCommitEvent();
                        event.setTag(0);
                        event.setObject(item);
                        EventBus.getDefault().post(event);
                        finish();
                    }
                });
*/
            }
        };
        View view = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        ImageView ivEmpty = view.findViewById(R.id.iv_empty);
        TextView tvEmpty = view.findViewById(R.id.tv_empty);
        ivEmpty.setImageResource(R.mipmap.no_address);
        tvEmpty.setText("您还没有收货地址哦~");
        commentAdapter.setEmptyView(view);
    }

    @OnClick({R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address://新增地址
                Bundle bundleNewAdd = new Bundle();
                bundleNewAdd.putString("index", "1");
                startActivity(NewAddressActivity.class, bundleNewAdd);
                break;
        }
    }

    /**
     * 列表数据请求
     */
    private void getListData(String memberId) {
        showLoading();
        OkGo.<String>post(UrlContent.ADDRESSLIST)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                AddressListBean listBean = JsonUtils.parse(response.body(), AddressListBean.class);
                                addressList.clear();
                                addressList.add(listBean.getData());
                                commentAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }
                });

    }

    /**
     * 删除收货地址
     */
    private void getDelData(int id) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("id", id);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.DELADDRESS)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                getListData(PreferenceManager.instance().getUserId());
                                T("删除成功啦~");
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }
                });
    }

    /**
     * 设置默认收货地址
     */
    private void getUptDefaData(int id) {
        showLoading();

        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("id", id);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.UPTDEFA)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                getListData(PreferenceManager.instance().getUserId());
                            }
                        });
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dissMissLoading();
                    }
                });
    }
}
