package cn.xunzi.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.MyServiceFactory;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.MyCardList_Adapter;
import cn.xunzi.basiclib.api.MyService;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.BankListBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.protocol.MyProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.widget.GenDialog;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.MYCARD;

@Route(path = MYCARD)
public class MyCardActivity extends BaseActivity {

    private ImageView ivBack;
    private ImageView ivAdd;
    private RecyclerView bankRec;
    private TextView tvTip;
    private Button btAdd;
    private MyCardList_Adapter myCardList_adapter;
    private BankListBean bankListBean1;
    private String type="";

    @Override
    protected int getLayoutId() {
        type=getIntent().getStringExtra("type");
        return R.layout.activity_my_card;
    }

    @Override
    protected void initComponents() {
        initView();
        bankListBean1=new BankListBean();
        myCardList_adapter=new MyCardList_Adapter();
        ivBack.setOnClickListener(view -> finish());
        ivAdd.setOnClickListener(view -> ARouter.getInstance().build(MyProtocol.ADDBANKCARD).withSerializable("data",bankListBean1).navigation());
        btAdd.setOnClickListener(view -> ARouter.getInstance().build(MyProtocol.ADDBANKCARD).navigation());
        myCardList_adapter.setOnRvItemClickListener(new OnRvItemClickListener<BankListBean.DataEntity.ListEntity>() {
            @Override
            public void onItemClick(View v, int position, BankListBean.DataEntity.ListEntity data) {
                if("with".equals(type)){
                    Intent intent = new Intent();
                    intent.putExtra("name", bankListBean1.getData().getList().get(position).getBankName());
                    intent.putExtra("no",bankListBean1.getData().getList().get(position).getBankNo());
                    intent.putExtra("id",bankListBean1.getData().getList().get(position).getId());
                    setResult(2, intent);
                    finish();
                }
            }
        });
        myCardList_adapter.setOnClickListener(new MyCardList_Adapter.OnClickListener() {
            @Override
            public void OnClick(int pos) {
                GenDialog.CustomBuilder2 customBuilder2 = new GenDialog.CustomBuilder2(MyCardActivity.this);
                Dialog dialog1;
                customBuilder2.setContent(R.layout.delcard_dialog);
                dialog1 = customBuilder2.create();
                TextView btSetPwd = dialog1.findViewById(R.id.tv_ok);
                TextView cancal=dialog1.findViewById(R.id.tv_cancel);
                cancal.setOnClickListener(view -> dialog1.dismiss());
                dialog1.show();
                btSetPwd.setOnClickListener(view1 -> {
                    dialog1.dismiss();
                    MyServiceFactory.deleteBankCard(XZUserManager.getUserInfo().getId()+"",bankListBean1.getData().getList().get(pos).getId()).safeSubscribe(new AbstractDialogSubscriber<PostBean>(MyCardActivity.this) {
                        @Override
                        public void onNext(PostBean postBean) {
                            if(postBean.getCode().equals("200")){
                                ToastUtils.showShort("删除成功");
                                getData();
                            }else{
                                ToastUtils.showShort(postBean.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }
                    });
                });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        MyServiceFactory.bankCardList(XZUserManager.getUserInfo().getId() + "").safeSubscribe(new AbstractDialogSubscriber<BankListBean>(MyCardActivity.this) {
            @Override
            public void onNext(BankListBean bankListBean) {
                if (bankListBean.getCode().equals("200")) {
                    bankListBean1=bankListBean;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyCardActivity.this );
                    layoutManager.setOrientation(OrientationHelper. VERTICAL);
                    bankRec.setLayoutManager(layoutManager);
                    myCardList_adapter.updateData(bankListBean.getData().getList());
                    bankRec.setAdapter(myCardList_adapter);
                    tvTip.setVisibility(View.GONE);
                    btAdd.setVisibility(View.GONE);
                }else{
//                    ToastUtils.showShort(bankListBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivAdd = findViewById(R.id.iv_add);
        bankRec = findViewById(R.id.bank_rec);
        tvTip = findViewById(R.id.tv_tip);
        btAdd = findViewById(R.id.bt_add);
    }
}
