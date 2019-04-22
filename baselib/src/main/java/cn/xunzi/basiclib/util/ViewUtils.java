package cn.xunzi.basiclib.util;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xunzi.basiclib.R;

/**
 * Date: 2018/7/20.
 * Time: 10:53
 * classDescription:
 *
 * @author fred
 */
public class ViewUtils {
    public static void showTipsDialog(Activity context, @DrawableRes int res, String title, String content) {
        Dialog dialog = new Dialog(context, R.style.NormalDialogStyle);
        dialog.setContentView(R.layout.dialog_tips);
        ImageView iv_img = dialog.findViewById(R.id.iv_img);
        iv_img.setImageResource(res);

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        if (StringUtil.isValid(content)) {

            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        } else {
            tvContent.setVisibility(View.GONE);
        }

        (dialog.findViewById(R.id.tv_send)).setOnClickListener(view -> {
            dialog.dismiss();
            context.finish();
        });

        dialog.show();
    }

}
