package cn.dankal.basiclib.base.recyclerview;

import android.support.annotation.NonNull;

/**
 * @author Dankal Android Developer
 */
public abstract class BaseRecyclerViewPresenter<M> implements BaseRecyclerViewContract.RecyclerViewPresenter {

    public BaseRecyclerViewContract.RecyclerViewView<M> mView;

    @Override
    public void attachView(@NonNull BaseRecyclerViewContract.RecyclerViewView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


}
