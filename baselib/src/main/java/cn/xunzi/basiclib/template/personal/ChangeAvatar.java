package cn.xunzi.basiclib.template.personal;

import android.content.Intent;
import android.widget.ImageView;

import cn.xunzi.basiclib.base.callback.DKCallBack;
import cn.xunzi.basiclib.common.camera.CameraHandler;

/**
 * Date: 2018/8/2.
 * Time: 11:05
 * classDescription:
 * 修改头像
 *
 * @author fred
 */
public interface ChangeAvatar {
    void checkPermission(CameraHandler cameraHandler, DKCallBack callBack);
    void setIvHead(ImageView imageView);
}
