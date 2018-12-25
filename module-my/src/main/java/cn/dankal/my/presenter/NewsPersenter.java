package cn.dankal.my.presenter;

import api.MyServiceFactory;
import cn.dankal.basiclib.api.MyService;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.SystemMsgBean;
import cn.dankal.basiclib.exception.LocalException;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;

public class NewsPersenter extends BaseRecyclerViewPresenter<SystemMsgBean.DataBean> {

    @Override
    public void requestData(String pageIndex) {
        MyServiceFactory.getSystemMsg(Integer.valueOf(pageIndex), 20).safeSubscribe(new AbstractDialogSubscriber<SystemMsgBean>(mView) {
            @Override
            public void onNext(SystemMsgBean systemMsgBean) {
                mView.render(systemMsgBean.getData());
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                if (e instanceof LocalException) {
                    LocalException exception = (LocalException) e;
                    if(exception.getMsg().equals("网络错误")){
                        ToastUtils.showShort("Network error");
                    }
                }
            }
        });
    }
}
