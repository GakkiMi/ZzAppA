package com.huadingcloudpackage.www.activity.newaddress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.guoquan.yunpu.util.ComTools;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.base.BaseActivity;
import com.huadingcloudpackage.www.bean.AddressExhibitionBean;
import com.huadingcloudpackage.www.bean.AddressListBean;
import com.huadingcloudpackage.www.bean.BaseBean;
import com.huadingcloudpackage.www.bean.JsonBean;
import com.huadingcloudpackage.www.http.Constants;
import com.huadingcloudpackage.www.http.UrlContent;
import com.huadingcloudpackage.www.sp.PreferenceManager;
import com.huadingcloudpackage.www.util.GetJsonDataUtil;
import com.huadingcloudpackage.www.util.JsonUtils;
import com.huadingcloudpackage.www.util.MyUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

/**
 * @author lige
 * 描述：
 */
public class NewAddressActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_consignee)
    EditText etConsignee;
    @BindView(R.id.et_phonenum)
    EditText etPhonenum;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    private String index;
    private int id;

    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private String pickerViewText1;
    private String pickerViewText2;
    private String pickerViewText3;

    private String province;
    private String city;
    private String county;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        setBackBtn();
        setToolbarBgColor(getResources().getColor(R.color.colorPageBg), false);
        Intent intent = getIntent();
        //0 编辑地址 1 新增地址
        index = intent.getStringExtra("index");
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String adddress = intent.getStringExtra("adddress");
        String province = intent.getStringExtra("province");
        String city = intent.getStringExtra("city");
        String county = intent.getStringExtra("county");

        if ("0".equals(index)) {//编辑地址
            setTitle("修改收货地址");
            etConsignee.setText(name);
            etPhonenum.setText(phone);
           /* tvSelect.setText(province + "  " + city + "  " + county);
            tvDetailAddress.setText(adddress);*/
            tvAddAddress.setText("确认修改");
        } else if ("1".equals(index)) {//新增地址
            setTitle("新增收货地址");
            etConsignee.setText("");
            etPhonenum.setText("");
            /*tvSelect.setText(province + "  " + city + "  " + county);
            tvDetailAddress.setText(adddress);*/
            tvAddAddress.setText("确认新增");
        }
        getexhibitionAddressData();
        initJsonData();
    }

    @OnClick({R.id.tv_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address:
                if ("1".equals(index)) {//新增
                    getData(MyUtils.getText(tvDetailAddress), pickerViewText2, pickerViewText3, Integer.parseInt(PreferenceManager.instance().getUserId()), pickerViewText1, MyUtils.getEtText(etPhonenum), MyUtils.getEtText(etConsignee));
                } else {
                    getUptAddressData(MyUtils.getText(tvDetailAddress), city, county, id, province, MyUtils.getEtText(etPhonenum), MyUtils.getEtText(etConsignee));
                }
                break;
        }
    }

    /**
     * 添加收货地址
     */
    private void getData(String address, String city, String county, int memberId, String province, String receiverCellphone, String receiverMembre) {
        if ("".equals(receiverMembre)) {
            T("请输入收货人名称");
            return;
        }
        if ("".equals(receiverCellphone)) {
            T("请输入手机号");
            return;
        }
        if (receiverCellphone.length() != 11) {
            T("请输入11位手机号");
            return;
        }
        /*if ("".equals(province) || province == null) {
            T("请选择地区");
            return;
        }
        if ("".equals(address)) {
            T("请输入详细地址");
            return;
        }*/
        showLoading();
        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            /*json1.put("address", address);
            json1.put("city", city);
            json1.put("county", county);*/
            json1.put("memberId", memberId);
//            json1.put("province", province);
            json1.put("receiverCellphone", receiverCellphone);
            json1.put("receiverMembre", receiverMembre);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.SAVEADDRESS)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                finish();
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
     * 修改收货地址
     */
    private void getUptAddressData(String address, String city, String county, int id, String province, String receiverCellphone, String receiverMember) {
        if ("".equals(receiverMember)) {
            T("请输入收货人名称");
            return;
        }
        if ("".equals(receiverCellphone)) {
            T("请输入手机号");
            return;
        }
        if (receiverCellphone.length() != 11) {
            T("请输入11位手机号");
            return;
        }
        /*if ("".equals(address)) {
            T("请输入详细地址");
            return;
        }*/
        showLoading();
        RequestBody body = null;
        try {
            JSONObject json1 = new JSONObject();
            json1.put("address", address);
            json1.put("city", city);
            json1.put("county", county);
            json1.put("province", province);
            json1.put("id", id);
            json1.put("receiverCellphone", receiverCellphone);
            json1.put("receiverMembre", receiverMember);
            body = RequestBody.create(UrlContent.JSON, String.valueOf(json1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(UrlContent.UPTADDRESS)
                .tag(this)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                finish();
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
     * 查询地址
     */
    private void getexhibitionAddressData() {
        showLoading();
        OkGo.<String>get(UrlContent.EXHIBITIONUPTADDRESS)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        BaseBean baseBean = JsonUtils.parse(response.body(), BaseBean.class);
                        comTools.parsingReturnData(baseBean, new ComTools.OnParsingReturnListener() {
                            @Override
                            public void onParsingSuccess() {
                                AddressExhibitionBean listBean = JsonUtils.parse(response.body(), AddressExhibitionBean.class);
                                province=listBean.getData().getProvince();
                                city=listBean.getData().getCity();
                                county=listBean.getData().getCounty();
                                tvSelect.setText(province+city+county);
                                tvDetailAddress.setText(listBean.getData().getAddress());
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


    private void showPickerView() {// 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                pickerViewText1 = options1Items.get(options1).getPickerViewText();
                pickerViewText2 = options2Items.get(options1).get(options2);
                pickerViewText3 = options3Items.get(options1).get(options2).get(options3);
                //返回的分别是三个级别的选中位置
                tvSelect.setText(pickerViewText1 + "  "
                        + pickerViewText2 + "  "
                        + pickerViewText3);

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(0x1A000000)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
