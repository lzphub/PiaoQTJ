package cn.dankal.my.presenter;

import android.widget.Button;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.BankCardListBean;

public interface WithdrawalContact {
    interface bcView extends BaseView{
        void withDarawalSuccess();
        void withDarawalFail();
    }
    interface idPresenter extends BasePresenter<bcView>{
        void withDarawal(String withdrawal_password,String withdrawal_money,String bank_card_number);

    }
}
