package cn.dankal.basiclib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.util.DKHandler;


/**
 * @author vane
 * @since 2018/5/9
 */

public class GenDialog extends Dialog {
    private Builder builder;

    public GenDialog(Context context) {
        this(context, R.style.GenDialog);
    }

    public GenDialog(Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(wmLp);
        }
    }


    /**
     * 生成默认的 {@link GenDialog}
     * <p>
     * 提供了一个图标和一行文字的样式, 其中图标有几种类型可选。见 {@link IconType}
     * </p>
     *
     * @see CustomBuilder
     */
    public static class Builder {
        /**
         * 不显示任何icon
         */
        public static final int ICON_TYPE_NOTHING = 0;
        /**
         * 显示 Loading 图标
         */
        public static final int ICON_TYPE_LOADING = 1;
        /**
         * 显示成功图标
         */
        public static final int ICON_TYPE_SUCCESS = 2;
        /**
         * 显示失败图标
         */
        public static final int ICON_TYPE_FAIL = 3;
        /**
         * 显示信息图标
         */
        public static final int ICON_TYPE_INFO = 4;

        @IntDef({ICON_TYPE_NOTHING, ICON_TYPE_LOADING, ICON_TYPE_SUCCESS, ICON_TYPE_FAIL, ICON_TYPE_INFO})
        @Retention(RetentionPolicy.SOURCE)
        public @interface IconType {
        }

        private @IconType
        int mCurrentIconType = ICON_TYPE_NOTHING;

        private Context mContext;

        private CharSequence mTipWord;

        private GenDialog dialog;


        public Builder(Context context) {
            mContext = context;
        }

        /**
         * 设置 icon 显示的内容
         *
         * @see IconType
         */
        public Builder setIconType(@IconType int iconType) {
            mCurrentIconType = iconType;
            return this;
        }

        /**
         * 设置显示的文案
         */
        public Builder setTipWord(CharSequence tipWord) {
            mTipWord = tipWord;
            return this;
        }

        public GenDialog create() {
            return create(true);
        }

        public GenDialog create(long millToDiss) {
            return create(true).delayDismiss(millToDiss);
        }

        /**
         * 修改图标/文字
         */
        public void showProgress() {

            ((TextView) dialog.findViewById(R.id.dialog_text)).setText(mTipWord);
        }


        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @param cancelable 按系统返回键是否可以取消
         * @return 创建的 Dialog
         */
        public GenDialog create(boolean cancelable) {
            GenDialog dialog = new GenDialog(mContext);
            dialog.setCancelable(cancelable);
            dialog.setContentView(R.layout.layout_tip_dialog);
            ViewGroup contentWrap = (ViewGroup) dialog.findViewById(R.id.contentWrap);

            this.dialog = dialog;
            return dialog;
        }

    }

    /**
     * 传入自定义的布局并使用这个布局生成 TipDialog
     */
    public static class CustomBuilder {
        private Context mContext;
        private int mContentLayoutId;

        public CustomBuilder(Context context) {
            mContext = context;
        }

        public CustomBuilder setContent(@LayoutRes int layoutId) {
            mContentLayoutId = layoutId;
            return this;
        }


        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @return 创建的 Dialog
         */
        public GenDialog create() {
            GenDialog dialog = new GenDialog(mContext);
            dialog.setContentView(R.layout.layout_gen_dialog);
            ViewGroup contentWrap = dialog.findViewById(R.id.contentWrap);
            LayoutInflater.from(mContext).inflate(mContentLayoutId, contentWrap, true);
            return dialog;
        }
    }

    public static class CustomBuilder2 {
        private Context mContext;
        private int mContentLayoutId;

        public CustomBuilder2(Context context) {
            mContext = context;
        }

        public CustomBuilder2 setContent(@LayoutRes int layoutId) {
            mContentLayoutId = layoutId;
            return this;
        }


        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @return 创建的 Dialog
         */
        public GenDialog create() {
            GenDialog dialog = new GenDialog(mContext);
            dialog.setContentView(R.layout.layout_finish_dialog);
            ViewGroup contentWrap = dialog.findViewById(R.id.contentWrap);
            LayoutInflater.from(mContext).inflate(mContentLayoutId, contentWrap, true);
            return dialog;
        }
    }

    //延时后隐藏
    private long millToDiss;

    public GenDialog delayDismiss(long millToDiss) {
        this.millToDiss = millToDiss;
        return this;
    }

    @Override
    public void dismiss() {
        if (millToDiss > 0) {
            new DKHandler(getContext()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    millToDiss = 0;
                    if (isShowing()) {
                        GenDialog.super.dismiss();
                    }
                }
            }, millToDiss);
        } else {
            super.dismiss();
        }
    }
}
