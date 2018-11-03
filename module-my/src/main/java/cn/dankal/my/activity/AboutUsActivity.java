package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
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

    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        usContent = (TextView) findViewById(R.id.us_content);
        imageRv = (RecyclerView) findViewById(R.id.image_rv);
    }
}
