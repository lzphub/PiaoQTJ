package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;

import static cn.xunzi.basiclib.protocol.HomeProtocol.ONEMONEY;

@Route(path = ONEMONEY)
public class OneMoneyActivity extends BaseActivity {


    private ImageView ivBack;
    private int title;
    private View view;
    private android.widget.TextView tvTitle;

    @Override
    protected int getLayoutId() {
        title=getIntent().getIntExtra("title",0);
        return R.layout.activity_one_money;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        if(title==1){
            tvTitle.setText("港货代购");
        }
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        view = findViewById(R.id.view);
        tvTitle = findViewById(R.id.tv_title);
    }
}
