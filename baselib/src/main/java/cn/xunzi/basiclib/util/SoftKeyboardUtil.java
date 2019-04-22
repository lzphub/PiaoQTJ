package cn.xunzi.basiclib.util;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Dylan on 2016/10/9.
 * Edited by Fred on 2017/02/25
 */
public class SoftKeyboardUtil {


    public static void hideSoftInput(View view) {
        if (isKeyboardShown(view)){
            Context context = view.getContext().getApplicationContext();
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }


    public static void showSoftInput(View view) {
        Context context = view.getContext().getApplicationContext();
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, 0);
    }

    /**
     * InputMethodManager imm = (InputMethodManager) rootView.getContext().getSystemService(INPUT_METHOD_SERVICE);
     * return !imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
     * @param rootView
     * @return
     */
    //通过判断根布局的可视区域与屏幕底部的差值
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }





}
