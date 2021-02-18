package com.huadingcloudpackage.www.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.huadingcloudpackage.www.R;

import java.text.NumberFormat;




/**
 * 自定义进度条
 *
 * apk下载进度条
 */

public class CommonProgressDialog extends AlertDialog implements View.OnClickListener {
    private ProgressBar mProgress;
    private TextView mProgressNumber;
    private TextView mProgressPercent;
    private TextView mProgressMessage;
    private Handler mViewUpdateHandler;
    private int mMax;
    private CharSequence mMessage;
    private boolean mHasStarted;
    private int mProgressVal;
    private String TAG = "CommonProgressDialog";
    private String mProgressNumberFormat;
    private NumberFormat mProgressPercentFormat;
    private TextView speed;
    private AppCompatTextView btn_chongshi, btn_anzhuang;
    private LinearLayout ll_progress;
    private Listener listener;
    private Context context;

    public CommonProgressDialog(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
        initFormats();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public interface Listener {
        void onClick(Dialog dialog, int button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_progress_dialog);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mProgressNumber = (TextView) findViewById(R.id.progress_number);
        mProgressPercent = (TextView) findViewById(R.id.progress_percent);
        mProgressMessage = (TextView) findViewById(R.id.progress_message);
        speed = (TextView) findViewById(R.id.speed);
        btn_anzhuang = (AppCompatTextView) findViewById(R.id.btn_anzhuang);
        btn_chongshi = (AppCompatTextView) findViewById(R.id.btn_chongshi);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);


        // LayoutInflater inflater = LayoutInflater.from(getContext());
        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                int progress = mProgress.getProgress();
                int max = mProgress.getMax();
                double dProgress = (double) progress / (double) (1024 * 1024);
                double dMax = (double) max / (double) (1024 * 1024);

                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(
                            mProgressPercentFormat.format(percent));
                    tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mProgressPercent.setText(tmp);
                } else {
                    mProgressPercent.setText("");
                }
            }
        };
        // View view = inflater.inflate(R.layout.common_progress_dialog, null);
        // mProgress = (ProgressBar) view.findViewById(R.id.progress);
        // mProgressNumber = (TextView) view.findViewById(R.id.progress_number);
        // mProgressPercent = (TextView)
        // view.findViewById(R.id.progress_percent);
        // setView(view);
        // mProgress.setMax(100);
        onProgressChanged();
        if (mMessage != null) {
            setMessage(mMessage);
        }
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal > 0) {
            setProgress(mProgressVal);
        }
    }

    private void initFormats() {
        mProgressNumberFormat = "%1.2fM/%2.2fM";
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);
    }

    public void setProgressStyle(int style) {
        // mProgressStyle = style;
    }

    public int getMax() {
        if (mProgress != null) {
            return mProgress.getMax();
        }
        return mMax;
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (mProgress != null) {
            mProgress.setIndeterminate(indeterminate);
        }
        // else {
        // mIndeterminate = indeterminate;
        // }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);

            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    public void setSpeed(String sudu, String downsize) {
        if (mHasStarted) {

            speed.setText(sudu);
            mProgressNumber.setText(downsize);
            onProgressChanged();
        }
    }


    @Override
    public void setMessage(CharSequence message) {
        // TODO Auto-generated method stub
        // super.setMessage(message);
        if (mProgressMessage != null) {
            mProgressMessage.setText(message);
        } else {
            mMessage = message;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mHasStarted = false;
    }

    public void seterror() {
        ll_progress.setVisibility(View.GONE);
        btn_anzhuang.setVisibility(View.GONE);
        btn_chongshi.setVisibility(View.VISIBLE);
        btn_chongshi.setOnClickListener(this);
    }

    public void setanzhuang() {
        ll_progress.setVisibility(View.GONE);
        btn_anzhuang.setVisibility(View.VISIBLE);
        btn_chongshi.setVisibility(View.GONE);
        btn_anzhuang.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_anzhuang:
                listener.onClick(CommonProgressDialog.this, 0);
                break;

            case R.id.btn_chongshi:
                listener.onClick(CommonProgressDialog.this, 1);
                break;

        }

    }

}