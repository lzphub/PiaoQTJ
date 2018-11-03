package cn.dankal.basiclib.util.image;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import cn.dankal.basiclib.ResultCode;

public class CheckImage {
    //选择图片
    public static void takePhotoPicker(Activity activity,int size) {
        AndPermission.with(activity.getBaseContext()).runtime().permission(Permission.CAMERA,Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                Matisse.from(activity)
                        .choose(MimeType.ofImage(), false)
                        .countable(true)
                        .maxSelectable(size)
                        .gridExpectedSize(0)
                        .capture(false)
                        .captureStrategy(new CaptureStrategy(true,"cn.dankal.address.fileprovider"))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new MygildeEngine())
                        .originalEnable(true)
                        .forResult(ResultCode.CheckImageCode);
            }
        }).start();
    }
}
