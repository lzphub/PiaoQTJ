package cn.xunzi.basiclib;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.xunzi.basiclib.pojo.UserInfoBean;
import cn.xunzi.basiclib.pojo.UserResponseBody;
import cn.xunzi.basiclib.util.PreferenceUtil;


/**
 * @author vane
 */
public class XZUserManager {

    private static Context mContext = XunziApplication.getContext();
    private static SharedPreferences mSpUserInfo;
    private static SharedPreferences mSpToken;
    private static final String PreferenceUserInfo = "userinfo";
    private static final String PreferenceToken = "token";
    private static UserResponseBody.DataEntity.UserEntity userInfo;
    private static UserResponseBody.TokenBean userToken;

    static {
        mSpUserInfo = mContext.getSharedPreferences(PreferenceUserInfo, Context.MODE_PRIVATE);
        mSpToken = mContext.getSharedPreferences(PreferenceToken, Context.MODE_PRIVATE);
    }
 

    /**
     * 最初mUserInfo各属性内容为空，
     * 如登录后可更新当前的UserInfo和本地的缓存
     */
    public static void saveUserInfo(UserResponseBody userResponseBody) {
        UserResponseBody.DataEntity.UserEntity userInfo = userResponseBody.getData().getUser();
        UserResponseBody.TokenBean token = userResponseBody.getToken();
        if (userInfo!=null) {
            PreferenceUtil.updateBean(mSpUserInfo, getUserInfo(), userInfo);
        }
        if (token!=null) {
            PreferenceUtil.updateBean(mSpToken, getUserToken(), token);
        }
    }

    public static void updateUserInfo(UserResponseBody.DataEntity.UserEntity userInfo) {
        if (userInfo!=null) {
            PreferenceUtil.updateBean(mSpUserInfo, getUserInfo(), userInfo);
        }
    }
    public static void updateUserToken(UserResponseBody.TokenBean token) {
        if (token!=null) {
            PreferenceUtil.updateBean(mSpToken, getUserToken(), token);
        }
    }
    /**
     * 如果从本地缓存中获取对象为空则实例化一个空对象
     * 判断是否登录全程通过user_id是不是为0来判断而不是通过
     * mUserInfo是否等于null，防止UserManager.getUserInfo出现空指针
     */
    private static void readUserInfo() {
        userInfo = (UserResponseBody.DataEntity.UserEntity) PreferenceUtil.getBeanValue(mSpUserInfo,UserResponseBody.DataEntity.UserEntity.class);
        if (userInfo == null) {
            userInfo = new UserResponseBody.DataEntity.UserEntity();
        }
    }

    private static void readUserToken() {
        userToken = (UserResponseBody.TokenBean) PreferenceUtil.getBeanValue(mSpToken,
                UserResponseBody.TokenBean.class);
        if (userToken == null) {
            userToken = new UserResponseBody.TokenBean();
        }
    }
    /**
     * 清空缓存时调用
     */
    public static void resetUserInfo() {
        SharedPreferences.Editor editor = mSpUserInfo.edit();
        editor.clear();
        editor.apply();
        userInfo = new UserResponseBody.DataEntity.UserEntity();
    }
    public static void resetToken() {
        SharedPreferences.Editor editor = mSpToken.edit();
        editor.clear();
        editor.apply();
        userToken = new UserResponseBody.TokenBean();
    }

    /**
     * 判断已经登录
     */
    public static boolean isLogined() {
//        return !TextUtils.isEmpty(getUserToken().getAccessToken());
        return !(getUserInfo().getId()==0);
    }

    public static UserResponseBody.TokenBean getUserToken() {
        if (userToken == null) {
            readUserToken();
        }
        return userToken;
    }

    public static UserResponseBody.DataEntity.UserEntity getUserInfo() {
        if (userInfo == null) {
            readUserInfo();
        }
        return userInfo;
    }
}
