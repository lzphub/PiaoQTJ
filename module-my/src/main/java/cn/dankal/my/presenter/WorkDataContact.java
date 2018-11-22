package cn.dankal.my.presenter;

import android.widget.Button;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.MyWorkDataBean;

public interface WorkDataContact {
    interface bcView extends BaseView{
        void getWordDataSuccess(MyWorkDataBean myWorkDataBean);
    }
    interface idPresenter extends BasePresenter<bcView>{
        void getWorkData(String uuid);
    }
}
