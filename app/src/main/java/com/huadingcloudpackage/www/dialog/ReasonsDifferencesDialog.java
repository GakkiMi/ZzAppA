package com.huadingcloudpackage.www.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huadingcloudpackage.www.R;
import com.huadingcloudpackage.www.adapter.CommentAdapter;
import com.huadingcloudpackage.www.adapter.itemdecoration.RvSpaceItemDecoration;
import com.huadingcloudpackage.www.widget.ShapeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 */
public class ReasonsDifferencesDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private OnItemListener listener;


    private RecyclerView rvReasonGrid;
    private CommentAdapter<HashMap<String, Object>> reasonAdapter;
    private List<HashMap<String, Object>> reasonList;

    public ReasonsDifferencesDialog(Context context, OnItemListener onItemListener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.listener = onItemListener;

        setContentView(R.layout.dialog_reasons_differences);
        setCanceledOnTouchOutside(false);
        ImageView mIvClose = findViewById(R.id.iv_close);
        mIvClose.setOnClickListener(this);

        rvReasonGrid = findViewById(R.id.dialog_reason_differences_rv_grid);
        rvReasonGrid.setLayoutManager(new GridLayoutManager(context, 2));
        RvSpaceItemDecoration spaceItemDecoration = new RvSpaceItemDecoration(context, 20);
        rvReasonGrid.addItemDecoration(spaceItemDecoration);
        TextView mTvSure = findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(this);


        reasonList = new ArrayList<>();
        String reasonArray[] = new String[]{"商品破损", "少发漏发", "商品过期", "商品近效期", "发错货", "多发超发"};
        for (String reason : reasonArray) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("reason", reason);
            map.put("isChecked", false);
            reasonList.add(map);
        }
        initReasonAdapter();
        rvReasonGrid.setAdapter(reasonAdapter);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        dialogWindow.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.mypopwindow_anim_style);
    }


    private void initReasonAdapter() {
        reasonAdapter = new CommentAdapter<HashMap<String, Object>>(R.layout.item_dialog_reason_defferences_rv_grid, reasonList) {
            @Override
            public void setViewData(BaseViewHolder holder, HashMap<String, Object> item, int position) {
                ShapeTextView stvReason = holder.getView(R.id.item_dialog_reason_rv_grid_tv);
                boolean isChecked = (boolean) item.get("isChecked");
                stvReason.setText((String) item.get("reason"));
                if (isChecked) {
                    stvReason.setBgNormalColor(Color.parseColor("#ffdce9f6"));
                    stvReason.setTextNormalColor(Color.parseColor("#084D8E"));
                } else {
                    stvReason.setBgNormalColor(Color.parseColor("#fff2f2f2"));
                    stvReason.setTextNormalColor(Color.parseColor("#666666"));
                }
            }

            @Override
            public void setEvent(BaseViewHolder holder, HashMap<String, Object> item, int position) {
                ShapeTextView stvReason = holder.getView(R.id.item_dialog_reason_rv_grid_tv);
                stvReason.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = (boolean) item.get("isChecked");
                        item.put("isChecked", !isChecked);
                        notifyItemChanged(position);
                    }
                });
            }
        };
    }


    @Override
    public void onClick(View view) {//1：商品破损  2：少发漏发  3：商品过期 4：商品近效期 5：发错货 6：多发超发
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_sure:
                if (listener != null) {
                    String indexArray = "";
                    String reasonArray = "";
                    for (int i = 0; i < reasonList.size(); i++) {
                        boolean isChecked = (boolean) reasonList.get(i).get("isChecked");
                        String reason = (String) reasonList.get(i).get("reason");
                        if (isChecked) {
                            indexArray += (i + 1) + ",";
                            reasonArray += reason + ",";
                        }
                    }
                    if (indexArray.length() > 0 && reasonArray.length() > 0) {
                        indexArray = indexArray.substring(0, indexArray.length() - 1);
                        reasonArray = reasonArray.substring(0, reasonArray.length() - 1);
                    }
                    listener.onItemSignin(indexArray, reasonArray);
                    dismiss();
                }
                break;
        }
    }


    public interface OnItemListener {
        void onItemSignin(String indexArray, String reasonArray);
    }
}
