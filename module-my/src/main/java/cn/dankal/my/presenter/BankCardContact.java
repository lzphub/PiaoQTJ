package cn.dankal.my.presenter;

import android.widget.Button;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.IntentionDateBean;

public interface BankCardContact {
    interface bcView extends BaseView{
        void bindCardSuccess();
        void bindCardFail();
        void getBankCardListSuccess(BankCardListBean bankCardListBean);
    }
    interface idPresenter extends BasePresenter<bcView>{
        void sendCode(Button button,String phone);
        void bindCard(String card_number,String cardholer,String id_card_number,String reserved_mobile,String bank_name,String code);
        void getBankCardList();
        void deleteCard(String cardNumber);
    }
}
