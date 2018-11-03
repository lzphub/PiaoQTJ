package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.WITHDRAWAL;

@Route(path = WITHDRAWAL)
public class WithdrawalActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout addCard;
    private android.widget.EditText moenyEt;
    private android.widget.TextView availableBalance;
    private android.widget.TextView allWith;
    private android.widget.Button withBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawal;
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
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        addCard = (RelativeLayout) findViewById(R.id.add_card);
        moenyEt = (EditText) findViewById(R.id.moeny_et);
        availableBalance = (TextView) findViewById(R.id.available_balance);
        allWith = (TextView) findViewById(R.id.all_with);
        withBtn = (Button) findViewById(R.id.with_btn);
    }
}
