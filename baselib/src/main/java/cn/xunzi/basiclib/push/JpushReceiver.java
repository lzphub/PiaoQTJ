package cn.xunzi.basiclib.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.xunzi.basiclib.XunziApplication;
import cn.xunzi.basiclib.protocol.MyProtocol;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;
//import cn.jpush.android.api.JPushInterface;

/**
 * description:
 *
 * @author vane
 * @since 2018/9/1
 */
public class JpushReceiver extends BroadcastReceiver {

    private static final String TAG = "JpushReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
//        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            if (bundle != null) {
//                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                Log.d(TAG, "JPush-onReceive: 用户点击了通知：" + extras);
//                String type2= SharedPreferencesUtils.getString(context, "identity", "user");
//                if(type2.equals("user")){
//                    ARouter.getInstance().build(MyProtocol.SYSTEMNEWS).navigation();
//                }else{
//                    ARouter.getInstance().build(MyProtocol.ENGSYSTEMNEWS).navigation();
//                }
//                try {
                    //{"content":"用户接收到了通知","type":1,"uuid":"3dd74716505e513aa54dec3bd79c3acd"}

//                    JSONObject jsonObject = JSON.parseObject(extras);
//                    int type = jsonObject.getIntValue("type");
//                    String uuid = jsonObject.getString("uuid");
//                    String orderId = jsonObject.getString("order_id");

                    /*if (XunziApplication.getAppComponent() != null &&
                            XunziApplication.getAppComponent().getTokenManager() != null) {
                        TokenManager userManager = XunziApplication.getAppComponent().getTokenManager();
                        if (!userManager.isTokenEmpty()) {
                            //物流消息
                            if (type == 0) {
                                String url = Address.URL_ORDER_DETAIL + orderId;
                                ARouter.getInstance().build(OrderRouteProtocol.ORDER_LIST)
                                        .withString("url", url)
                                        .navigation();
                            }
                            //系统消息
                            else if (type == 1) {
                                ARouter.getInstance().build(PersonalRouteProtocol.MESSAGE_DETAIL)
                                        .withString("uuid", uuid).navigation();
                            }
                            return;
                        }
                    }

                    ARouter.getInstance().build(MainRouteProtocol.SPLASH)
                            .withString("push_json", extras).navigation();*/

//                } catch (Exception ignore) {
//                }
//            }
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "JPush-onReceive: 用户接收到了通知");
//        }
    }
}
