package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYREQUESTDETA;

@Route(path = MYREQUESTDETA)
public class MyRequestDetaActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView requestTitle;
    private android.widget.TextView requestPrice;
    private android.widget.TextView requestPeriod;
    private android.support.v7.widget.RecyclerView addImgRv;
    private android.widget.TextView requestContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_request_deta;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        requestTitle = (TextView) findViewById(R.id.request_title);
        requestPrice = (TextView) findViewById(R.id.request_price);
        requestPeriod = (TextView) findViewById(R.id.request_period);
        addImgRv = (RecyclerView) findViewById(R.id.add_img_rv);
        requestContent = (TextView) findViewById(R.id.request_content);
    }
}
