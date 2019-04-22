package cn.xunzi.basiclib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.base.callback.DKCallBackBoolean;

/**
 * Date: 2018/7/23.
 * Time: 13:12
 * classDescription:
 *
 * @author fred
 */
public class GenderDialog extends Dialog {
    private final Context context;
    private final DKCallBackBoolean callBackBoolean;

    public GenderDialog(@NonNull Context context,DKCallBackBoolean callBackBoolean) {
//        super(context);
        super(context,  R.style.NormalDialogStyle);
        this.context=context;
        this.callBackBoolean=callBackBoolean;
    }

    private void configWindowStyle(View dialogView) {
        //获得dialog的window窗口
        Window window = this.getWindow();                //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();                 //获得window窗口的属性
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;                //设置窗口宽度为充满全屏
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;               //设置窗口高度为包裹内容
        window.setAttributes(lp); //将设置好的属性set回去
        setContentView(dialogView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_header, null);

        configWindowStyle(dialogView);
        setContentView(dialogView);

        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_gender, null);

        inflate.findViewById(R.id.tv_boy).setOnClickListener(v -> {
            dismiss();
            //男生
            callBackBoolean.action(DKCallBackBoolean.YES);
        });
        inflate.findViewById(R.id.tv_girl).setOnClickListener(v -> {
            dismiss();
            //女生
            callBackBoolean.action(DKCallBackBoolean.NO);
        });

        findViewById(R.id.tv_cancel).setVisibility(View.GONE);
        findViewById(R.id.tv_finish).setVisibility(View.GONE);

        ((LinearLayout) findViewById(R.id.ll_root)).addView(inflate);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("选择性别");
    }
}
