package cn.xunzi.basiclib.base;

/**
 * @author vane
 * @since 2018/1/30
 */

public interface BaseStateView extends BaseView {

    /**
     * 需要替换的View
     */
    Object contentView();

    /**
     * 加载重试界面
     */
    void showRetry();

    /**
     * 加载空数据界面
     */
    void showEmpty();

    /**
     * 加载内容界面
     */
    void showContent();

    /**
     * 加载等待进度界面
     */
    void showLoading();


    /**
     * 加载英文版空数据界面
     */
    void showEnEmpty();
}
