package cn.dankal.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;

import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.CheckBankCarkAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.del.DeleteRecyclerView;
import cn.dankal.basiclib.base.recyclerview.del.OnDelItemClickListener;
import cn.dankal.basiclib.bean.BankCardListBean;
import cn.dankal.basiclib.bean.ContactSortModel;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.my.presenter.BankCardContact;
import cn.dankal.my.presenter.BankCardPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.CHECKCARD;

/**
 * 选择银行卡
 */
@Route(path = CHECKCARD)
public class CheckBankCardActivity extends BaseActivity implements BankCardContact.bcView {

    private android.widget.ImageView backImg;
    private RecyclerView cardList;
    private android.widget.Button addBtn;
    private BankCardPersenter bankCardPersenter=BankCardPersenter.getBankCardPersenter();
    private CheckBankCarkAdapter checkBankCarkAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_bank_card;
    }

    @Override
    protected void initComponents() {
        initView();
//        bankCardPersenter.getBankCardList();
        backImg.setOnClickListener(v -> finish());
        addBtn.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.BINDCARD).navigation());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.d("smmmmm","onrestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardPersenter.attachView(this);
        bankCardPersenter.getBankCardList();
        Logger.d("smmmmm","onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("smmmmm","onStart");
    }

    private void initView() {
        addBtn = findViewById(R.id.add_btn);
        backImg = findViewById(R.id.back_img);
        cardList =  findViewById(R.id.card_list);
    }

    @Override
    public void getBankCardListSuccess(BankCardListBean bankCardListBean) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(linearLayoutManager);
        checkBankCarkAdapter=new CheckBankCarkAdapter();
        checkBankCarkAdapter.updata(bankCardListBean);
        cardList.setAdapter(checkBankCarkAdapter);
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
                showDeleteDialog(bankCardListBean.getCards().get(position).getCard_number());
            }
        });
    }

    private void showDeleteDialog(String number){
        GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(CheckBankCardActivity.this);
        customBuilder2.setContent(R.layout.dialog_delete_bankcard);
        Dialog dialog1 = customBuilder2.create();
        TextView ok_btn = dialog1.findViewById(R.id.tv_submit);
        ok_btn.setOnClickListener(v -> {
            bankCardPersenter.deleteCard(number);
            dialog1.dismiss();
        });
        TextView tv_cancel=dialog1.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(v -> dialog1.dismiss());

        dialog1.show();
    }

    @Override
    public void bindCardSuccess() {

    }

    @Override
    public void bindCardFail() {

    }
}
