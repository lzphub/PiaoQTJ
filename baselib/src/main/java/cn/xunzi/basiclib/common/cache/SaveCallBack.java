package cn.xunzi.basiclib.common.cache;

/**
 * 保存成功的回调
 * Created by Fred
 */
public interface SaveCallBack {
    void saveSuccess();
    void saveError(Throwable e);
}
