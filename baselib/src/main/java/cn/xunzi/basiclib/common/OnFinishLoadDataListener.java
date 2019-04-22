package cn.xunzi.basiclib.common;

/**
 * @author vane
 * @since 2018/7/17
 */

public interface OnFinishLoadDataListener {

    /**
     * 完成刷新
     */
    void finishRefresh();

    /**
     * 完成加载更多
     */
    void finishLoadMore();

}
