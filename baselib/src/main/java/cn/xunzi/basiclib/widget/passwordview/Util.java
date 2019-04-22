package cn.xunzi.basiclib.widget.passwordview;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 *
 * @author vane
 * @since 2018/4/9
 */

public class Util {

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }
}
