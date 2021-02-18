package com.huadingcloudpackage.www.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("AppCompatCustomView")
public class EmojiEditText extends EditText {
    // 输入表情前的光标位置
    private int cursorPos; // 输入表情前EditText中的文本
    private String inputAfterText; // 是否重置了EditText的内容
    private boolean resetText;
    private Context mContext;


    public EmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }


    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }


    public EmojiEditText(Context context, AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    } // 初始化edittext 控件


    private void initEditText() {
        this.setFilters(filters);
    }

    int num = 1000;

    public void setNum(int nums){
        this.num = nums;
        filters = new InputFilter[]{getInputFilterProhibitEmoji(), getInputFilterProhibitSP(), setMaxEms()};
        initEditText();
    }

    public InputFilter setMaxEms() {
        return new InputFilter.LengthFilter(num);
    }

    InputFilter[] filters = new InputFilter[]{getInputFilterProhibitEmoji(), getInputFilterProhibitSP(), setMaxEms()};

    public InputFilter getInputFilterProhibitEmoji() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuffer buffer = new StringBuffer();
                for (int i = start; i < end; i++) {
                    char codePoint = source.charAt(i);
                    if (!getIsEmoji(codePoint)) {
                        buffer.append(codePoint);
                    } else {
//                        ToastUtil.show("群组昵称不能含有第三方表情");
                        i++;
                        continue;
                    }
                }
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(buffer);
                    TextUtils.copySpansFrom((Spanned) source, start, end, null,
                            sp, 0);
                    return sp;
                } else {
                    return buffer;
                }
            }
        };
        return filter;
    }


    public boolean getIsEmoji(char codePoint) {
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }


    public InputFilter getInputFilterProhibitSP() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
//                String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@①#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

                String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/~@①#￥%……&*——+|{}【】‘；：”“’、]";

                Pattern pattern = Pattern.compile(limitEx);
                Matcher m = pattern.matcher(source);

                if (m.find()) {
//                    ToastUtil.show("不能输入特殊字符");
                    return "";
                }
                return null;

            }
        };
        return filter;
    }

    public boolean getIsSp(char codePoint) {
        if (Character.getType(codePoint) > Character.LETTER_NUMBER) {

            return true;

        }

        return false;

    }
}
