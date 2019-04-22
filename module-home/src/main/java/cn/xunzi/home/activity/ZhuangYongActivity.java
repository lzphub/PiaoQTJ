package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;

import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;

import static cn.xunzi.basiclib.protocol.HomeProtocol.ZHUANGYONG;

@Route(path = ZHUANGYONG)
public class ZhuangYongActivity extends BaseActivity {

    private ImageView ivContent;
    private ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhuang_yong;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        Glide.with(this).load(R.mipmap.tupian).into(ivContent);
    }

    private void initView() {
        ivContent = (ImageView) findViewById(R.id.iv_content);
        ivBack = (ImageView) findViewById(R.id.iv_back);
    }
}
