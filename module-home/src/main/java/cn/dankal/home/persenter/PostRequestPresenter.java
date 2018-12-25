package cn.dankal.home.persenter;

import android.support.annotation.NonNull;

import api.HomeServiceFactory;
import cn.dankal.basiclib.bean.PostRequestBean;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PostRequestPresenter implements PostRequestContact.productPresenter {

    private PostRequestContact.pcview pcview;

    @Override
    public void attachView(@NonNull PostRequestContact.pcview view) {
        pcview = view;
    }

    @Override
    public void detachView() {
        pcview = null;
    }

    @Override
    public void post(PostRequestBean postRequestBean) {
        HomeServiceFactory.postRequest(postRequestBean).safeSubscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                pcview.postSuccess();
                ToastUtils.showShort("Release success");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
