package com.huadingcloudpackage.www.widget;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huadingcloudpackage.www.R;
import com.permissionx.guolindev.dialog.RationaleDialog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 文 件 名：PermissionReasonDialog
 * 描   述：TODO
 */
public class PermissionRationaleDialog extends RationaleDialog {

//    context: Context, val message: String, val permissions: List<String>

    private Context context;
    private String message;
    private List<String> permissionList;

    private TextView tvSure;
    private TextView tvMessage;
    private LinearLayout permissionsLayout;
    private TextView tvCacle;

    private HashMap<String, Object> permissionMap = new HashMap<>();

    private HashSet<String> groupSet =new HashSet<String>();


    public PermissionRationaleDialog(@NonNull Context context) {
        this(context, R.style.dialog);
        this.context=context;
    }

    public PermissionRationaleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    private void initPermissionMap() {
        permissionMap.put(Manifest.permission.READ_CALENDAR, Manifest.permission_group.CALENDAR);
        permissionMap.put(Manifest.permission.WRITE_CALENDAR, Manifest.permission_group.CALENDAR);
        permissionMap.put(Manifest.permission.READ_CALL_LOG, Manifest.permission_group.CALL_LOG);
        permissionMap.put(Manifest.permission.WRITE_CALL_LOG, Manifest.permission_group.CALL_LOG);
        permissionMap.put(Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission_group.CALL_LOG);
        permissionMap.put(Manifest.permission.CAMERA, Manifest.permission_group.CAMERA);
        permissionMap.put(Manifest.permission.READ_CONTACTS, Manifest.permission_group.CONTACTS);
        permissionMap.put(Manifest.permission.WRITE_CONTACTS, Manifest.permission_group.CONTACTS);
        permissionMap.put(Manifest.permission.GET_ACCOUNTS, Manifest.permission_group.CONTACTS);
        permissionMap.put(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission_group.LOCATION);
        permissionMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission_group.LOCATION);
        permissionMap.put(Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission_group.LOCATION);
        permissionMap.put(Manifest.permission.RECORD_AUDIO, Manifest.permission_group.MICROPHONE);
        permissionMap.put(Manifest.permission.READ_PHONE_STATE, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.CALL_PHONE, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.ADD_VOICEMAIL, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.USE_SIP, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.ACCEPT_HANDOVER, Manifest.permission_group.PHONE);
        permissionMap.put(Manifest.permission.BODY_SENSORS, Manifest.permission_group.SENSORS);
        permissionMap.put(Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission_group.ACTIVITY_RECOGNITION);
        permissionMap.put(Manifest.permission.SEND_SMS, Manifest.permission_group.SMS);
        permissionMap.put(Manifest.permission.RECEIVE_SMS, Manifest.permission_group.SMS);
        permissionMap.put(Manifest.permission.READ_SMS, Manifest.permission_group.SMS);
        permissionMap.put(Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission_group.SMS);
        permissionMap.put(Manifest.permission.RECEIVE_MMS, Manifest.permission_group.SMS);
        permissionMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.STORAGE);
        permissionMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission_group.STORAGE);
        permissionMap.put(Manifest.permission.ACCESS_MEDIA_LOCATION, Manifest.permission_group.STORAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_rationale_dialog_layout);
        tvSure = findViewById(R.id.positiveBtn);
        tvCacle = findViewById(R.id.negativeBtn);
        tvMessage=findViewById(R.id.messageText);
        permissionsLayout = findViewById(R.id.permissionsLayout);
        tvMessage.setText(message);
        initPermissionMap();
        buildPermissionsLayout();

    }

    public PermissionRationaleDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public PermissionRationaleDialog setPermission(List<String> permission) {
        this.permissionList = permission;
        return this;
    }

    @NonNull
    @Override
    public View getPositiveButton() {
        return tvSure;
    }

    @Nullable
    @Override
    public View getNegativeButton() {
        return tvCacle;
    }

    @NonNull
    @Override
    public List<String> getPermissionsToRequest() {
        return permissionList;
    }


    private void buildPermissionsLayout() {
        for (String permission : permissionList) {
            String permissionGroup = (String) permissionMap.get(permission);
            if (permissionGroup != null && !groupSet.contains(permissionGroup)) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.permission_rationale_item, permissionsLayout, false);
                try {
                    textView.setText(context.getPackageManager().getPermissionGroupInfo(permissionGroup, 0).loadLabel(context.getPackageManager()));
                    permissionsLayout.addView(textView);
                    groupSet.add(permissionGroup);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
