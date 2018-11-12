package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.AboutUsBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.ABOUTUS;

@Route(path = ABOUTUS)
public class AboutUsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView usContent;
    private android.support.v7.widget.RecyclerView imageRv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
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
        getData();
    }

    private void getData(){
        MyServiceFactory.getAboutUs().safeSubscribe(new AbstractDialogSubscriber<AboutUsBean>(this) {
            @Override
            public void onNext(AboutUsBean aboutUsBean) {
                usContent.setText(aboutUsBean.getValue());
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        usContent = findViewById(R.id.us_content);
        imageRv = findViewById(R.id.image_rv);
    }
}
