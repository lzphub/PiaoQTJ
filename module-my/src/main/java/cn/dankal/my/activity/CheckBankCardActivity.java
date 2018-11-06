package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.CHECKCARD;

@Route(path = CHECKCARD)
public class CheckBankCardActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.support.v7.widget.RecyclerView cardList;
    private android.widget.Button addBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_bank_card;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyProtocol.BINDCARD).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        cardList = (RecyclerView) findViewById(R.id.card_list);
        addBtn = (Button) findViewById(R.id.add_btn);
    }
}
