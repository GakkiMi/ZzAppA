package com.huadingcloudpackage.www.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.huadingcloudpackage.www.R;


/**
 * 文 件 名：MyToastyUtils
 * 描   述：TODO
 */
public class MyToasty {
    private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private static Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    private static int textSize = 16; // in SP

    private static boolean tintIcon = true;
    private static boolean allowQueue = true;

    private static Toast lastToast = null;

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private MyToasty() {
        // avoiding instantiation
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message) {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, Drawable icon) {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration) {
        return normal(context, context.getString(message), duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon) {
        return normal(context, context.getString(message), duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration,
                               Drawable icon, boolean withIcon) {
        return custom(context, context.getString(message), icon, MyToastyUtils.getColor(context, R.color.normalColor),
                MyToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration,
                               Drawable icon, boolean withIcon) {
        return custom(context, message, icon, MyToastyUtils.getColor(context, R.color.normalColor),
                MyToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message) {
        return warning(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration) {
        return warning(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), MyToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_24dp),
                MyToastyUtils.getColor(context, R.color.warningColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, MyToastyUtils.getDrawable(context, R.drawable.ic_error_outline_white_24dp),
                MyToastyUtils.getColor(context, R.color.warningColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message) {
        return info(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration) {
        return info(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), MyToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_24dp),
                MyToastyUtils.getColor(context, R.color.infoColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, MyToastyUtils.getDrawable(context, R.drawable.ic_info_outline_white_24dp),
                MyToastyUtils.getColor(context, R.color.infoColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message) {
        return success(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration) {
        return success(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), MyToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp),
                MyToastyUtils.getColor(context, R.color.successColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, MyToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp),
                MyToastyUtils.getColor(context, R.color.successColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message) {
        return error(context, context.getString(message), Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration) {
        return error(context, context.getString(message), duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @StringRes int message, int duration, boolean withIcon) {
        return custom(context, context.getString(message), MyToastyUtils.getDrawable(context, R.drawable.ic_clear_white_24dp),
                MyToastyUtils.getColor(context, R.color.errorColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, MyToastyUtils.getDrawable(context, R.drawable.ic_clear_white_24dp),
                MyToastyUtils.getColor(context, R.color.errorColor), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               int duration, boolean withIcon) {
        return custom(context, context.getString(message), icon, -1, MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               int duration, boolean withIcon) {
        return custom(context, message, icon, -1, MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, false);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, @DrawableRes int iconRes,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), MyToastyUtils.getDrawable(context, iconRes),
                MyToastyUtils.getColor(context, tintColorRes), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, @DrawableRes int iconRes,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, message, MyToastyUtils.getDrawable(context, iconRes),
                MyToastyUtils.getColor(context, tintColorRes), MyToastyUtils.getColor(context, R.color.defaultTextColor),
                duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               @ColorRes int tintColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), icon, MyToastyUtils.getColor(context, tintColorRes),
                MyToastyUtils.getColor(context, R.color.defaultTextColor), duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @StringRes int message, Drawable icon,
                               @ColorRes int tintColorRes, @ColorRes int textColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context, context.getString(message), icon, MyToastyUtils.getColor(context, tintColorRes),
                MyToastyUtils.getColor(context, textColorRes), duration, withIcon, shouldTint);
    }

    @SuppressLint("ShowToast")
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.view_toast_layout, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;
//
//        if (shouldTint)
//            drawableFrame = MyToastyUtils.tint9PatchDrawableFrame(context, tintColor);
//        else
//            drawableFrame = MyToastyUtils.getDrawable(context, R.drawable.toast_frame);
//        MyToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            MyToastyUtils.setBackground(toastIcon, tintIcon ? MyToastyUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        currentToast.setView(toastLayout);

        if (!allowQueue){
            if (lastToast != null)
                lastToast.cancel();
            lastToast = currentToast;
        }

        return currentToast;
    }

    public static class Config {
        private Typeface typeface = MyToasty.currentTypeface;
        private int textSize = MyToasty.textSize;

        private boolean tintIcon = MyToasty.tintIcon;
        private boolean allowQueue = true;

        private Config() {
            // avoiding instantiation
        }

        @CheckResult
        public static MyToasty.Config getInstance() {
            return new MyToasty.Config();
        }

        public static void reset() {
            MyToasty.currentTypeface = LOADED_TOAST_TYPEFACE;
            MyToasty.textSize = 16;
            MyToasty.tintIcon = true;
            MyToasty.allowQueue = true;
        }

        @CheckResult
        public MyToasty.Config setToastTypeface(@NonNull Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        @CheckResult
        public MyToasty.Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public MyToasty.Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        @CheckResult
        public MyToasty.Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public void apply() {
            MyToasty.currentTypeface = typeface;
            MyToasty.textSize = textSize;
            MyToasty.tintIcon = tintIcon;
            MyToasty.allowQueue = allowQueue;
        }
    }


    static class MyToastyUtils {
        private MyToastyUtils() {
        }

        private  static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
            drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            return drawable;
        }

        private  static Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
            return tintIcon(toastDrawable, tintColor);
        }

        private static void setBackground(@NonNull View view, Drawable drawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else
                view.setBackgroundDrawable(drawable);
        }

        private  static Drawable getDrawable(@NonNull Context context, @DrawableRes int id) {
            return AppCompatResources.getDrawable(context, id);
        }

        private static int getColor(@NonNull Context context, @ColorRes int color) {
            return ContextCompat.getColor(context, color);
        }
    }
    
}
