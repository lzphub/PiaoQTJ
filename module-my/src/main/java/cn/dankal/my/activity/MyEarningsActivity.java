package cn.dankal.my.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.ResultCode;
import cn.dankal.basiclib.adapter.MyEar2Adapter;
import cn.dankal.basiclib.adapter.MyEarAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.MyEarBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.GenDialog;
import cn.dankal.my.presenter.MyEarContact;
import cn.dankal.my.presenter.MyEarPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYEARNING;

/**
 * 我的收益
 */
@Route(path = MYEARNING)
public class MyEarningsActivity extends BaseActivity implements MyEarContact.meView {

    private android.widget.ImageView backImg;
    private android.widget.TextView tardingList;
    private android.widget.TextView balanceText;
    private android.widget.TextView rankingText;
    private android.widget.TextView titleRank;
    private android.support.v7.widget.RecyclerView rankOnetothreeList;
    private android.support.v7.widget.RecyclerView rankFourtotenList;
    private android.widget.LinearLayout withdrawalLl;
    private List<MyEarBean.ChartBean> myEarBeansTop3 = new ArrayList<>(), myEarBeans4to10 = new ArrayList<>();
    private MyEarAdapter myEarAdapter;
    private MyEar2Adapter myEar2Adapter;
    private MyEarPersenter myEarPersenter = MyEarPersenter.getPersenter();
    private int issetpwd;

    //提现门槛
    public static double threshold = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_earnings;
    }

    @Override
    protected void initComponents() {
        initView();
        myEarPersenter.attachView(this);
        myEarPersenter.getData();

        SpannableString spannableString = new SpannableString("收益排名");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.login_btn_bg));
        spannableString.setSpan(colorSpan, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        titleRank.setText(spannableString);

        backImg.setOnClickListener(v -> finish());
        tardingList.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.TRANSACTIONRECORD).navigation());
        withdrawalLl.setOnClickListener(v -> {
            if (issetpwd == 1) {
                ARouter.getInstance().build(MyProtocol.WITHDRAWAL).withString("balance", balanceText.getText().toString().trim().substring(1, balanceText.getText().toString().trim().length() - 1)).navigation();
            } else {
                GenDialog.CustomBuilder customBuilder = new GenDialog.CustomBuilder(MyEarningsActivity.this);
                customBuilder.setContent(R.layout.unsetpwd_layout);
                Dialog dialog1 = customBuilder.create();
                ImageView close = dialog1.findViewById(R.id.close_img);
                close.setOnClickListener(v1 -> dialog1.dismiss());
                RelativeLayout setPwd = dialog1.findViewById(R.id.toSetPwd_Rl);
                setPwd.setOnClickListener(v12 -> {
                    dialog1.dismiss();
                    ARouter.getInstance().build(MyProtocol.SETWITHPEDCODE).withInt("type", ResultCode.myEarCode).navigation();
                });
                dialog1.show();
            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myEarPersenter.getData();
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleRank = findViewById(R.id.title_rank);
        tardingList = findViewById(R.id.tarding_list);
        balanceText = findViewById(R.id.balance_text);
        rankingText = findViewById(R.id.ranking_text);
        withdrawalLl = findViewById(R.id.withdrawal_ll);
        rankFourtotenList = findViewById(R.id.rank_fourtoten_list);
        rankOnetothreeList = findViewById(R.id.rank_onetothree_list);
    }

    @Override
    public void getDataSuccess(MyEarBean myEarBean) {
        balanceText.setText("$" + myEarBean.getSelf().getBalance());
        rankingText.setText(myEarBean.getSelf().getRank() + "");
        issetpwd = myEarBean.getSelf().getHas_withdrawal_pwd();
        MyEarBean.ChartBean bean;
        if (myEarBeansTop3 != null) {
            myEarBeansTop3.clear();
        }
        if (myEarBeans4to10 != null) {
            myEarBeans4to10.clear();
        }
        for (int i = 0; i < resize(myEarBean); i++) {
            bean = new MyEarBean.ChartBean();
            bean = myEarBean.getChart().get(i);
            myEarBeansTop3.add(bean);
        }
        if (myEarBean.getChart().size() > 3) {
            for (int n = 3; n < myEarBean.getChart().size(); n++) {
                bean = new MyEarBean.ChartBean();
                bean = myEarBean.getChart().get(n);
                myEarBeans4to10.add(bean);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        rankOnetothreeList.setLayoutManager(linearLayoutManager);
        rankFourtotenList.setLayoutManager(linearLayoutManager2);

        rankOnetothreeList.setNestedScrollingEnabled(false);
        rankFourtotenList.setNestedScrollingEnabled(false);

        rankOnetothreeList.setHasFixedSize(true);
        rankFourtotenList.setHasFixedSize(true);

        myEarAdapter = new MyEarAdapter();
        myEar2Adapter = new MyEar2Adapter();

        myEarAdapter.addMore(myEarBeansTop3);
        myEar2Adapter.addMore(myEarBeans4to10);

        rankOnetothreeList.setAdapter(myEarAdapter);
        rankFourtotenList.setAdapter(myEar2Adapter);
    }

    private int resize(MyEarBean myEarBean) {
        return myEarBean.getChart().size() >= 3 ? 3 : myEarBean.getChart().size();
    }
}
