package cn.dankal.my.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;

import cn.dankal.basiclib.adapter.CheckBankCarkAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.del.DeleteRecyclerView;
import cn.dankal.basiclib.base.recyclerview.del.OnDelItemClickListener;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.ContactSortModel;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.my.presenter.BankCardContact;
import cn.dankal.my.presenter.BankCardPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.CHECKCARD;

@Route(path = CHECKCARD)
public class CheckBankCardActivity extends BaseActivity implements BankCardContact.bcView {

    private android.widget.ImageView backImg;
    private RecyclerView cardList;
    private android.widget.Button addBtn;
    private BankCardPersenter bankCardPersenter=BankCardPersenter.getBankCardPersenter();
    private CheckBankCarkAdapter checkBankCarkAdapter;
    private BankCardListBean bankCardListBean2=new BankCardListBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_bank_card;
    }

    @Override
    protected void initComponents() {
        initView();
        bankCardPersenter.attachView(this);
        bankCardPersenter.getBankCardList();
        backImg.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.BINDCARD).navigation());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bankCardPersenter.getBankCardList();
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        cardList =  findViewById(R.id.card_list);
        addBtn = findViewById(R.id.add_btn);
    }

    @Override
    public void bindCardSuccess() {

    }

    @Override
    public void bindCardFail() {

    }

    @Override
    public void getBankCardListSuccess(BankCardListBean bankCardListBean) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(linearLayoutManager);
        checkBankCarkAdapter=new CheckBankCarkAdapter(bankCardListBean);
        cardList.setAdapter(checkBankCarkAdapter);
        bankCardListBean2=bankCardListBean;
        checkBankCarkAdapter.setOnItemClickLitener(new CheckBankCarkAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                checkBankCarkAdapter.setSelection(position);
                Intent intent=new Intent();
                intent.putExtra("card", bankCardListBean.getCards().get(position));
                setResult(20,intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {
                    bankCardPersenter.deleteCard(bankCardListBean.getCards().get(position).getCard_number());
            }
        });
    }
}
