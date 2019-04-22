package cn.xunzi.home.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.HomeServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.LinkBean;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;

import static cn.xunzi.basiclib.protocol.HomeProtocol.SERVICE;

@Route(path = SERVICE)
public class ServiceActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvCall;
    private TextView tvPhone;
    private TextView tvWx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        HomeServiceFactory.link().safeSubscribe(new AbstractDialogSubscriber<LinkBean>(ServiceActivity.this) {
            @Override
            public void onNext(LinkBean linkBean) {
                tvCall.setText(linkBean.getData().getList().get(0).getServicePhone());
                tvPhone.setText(linkBean.getData().getList().get(0).getHotline());
                tvWx.setText(linkBean.getData().getList().get(0).getServiceCode());
            }
        });

        tvCall.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + tvCall.getText().toString().trim());
            intent.setData(data);
            startActivity(intent);
        });

        tvPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + tvPhone.getText().toString().trim());
            intent.setData(data);
            startActivity(intent);
        });

        tvWx.setOnLongClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(tvWx.getText());
            Toast.makeText(ServiceActivity.this, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        tvCall = findViewById(R.id.tv_call);
        tvPhone = findViewById(R.id.tv_phone);
        tvWx = findViewById(R.id.tv_wx);
    }
}
