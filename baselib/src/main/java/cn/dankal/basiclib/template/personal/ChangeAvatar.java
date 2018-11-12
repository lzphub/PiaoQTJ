package cn.dankal.basiclib.template.personal;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import cn.dankal.basiclib.adapter.ServiceRvAdapter;
import cn.dankal.basiclib.base.callback.DKCallBack;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.common.camera.CameraHandler;

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
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onChatPickPhoto(RecyclerView recyclerView, ServiceRvAdapter serviceRvAdapter, List<ServiceTextBean> serviceTextBeanList, int requestCode, int resultCode, Intent data);
}
