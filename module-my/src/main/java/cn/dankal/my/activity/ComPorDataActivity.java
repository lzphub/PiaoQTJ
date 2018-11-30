package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.COMPORDATA;

@Route(path = COMPORDATA)
public class ComPorDataActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView title;
    private String content;

    @Override
    protected int getLayoutId() {
        content=getIntent().getStringExtra("data");
        return R.layout.activity_com_por_data;
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
        CharSequence charSequence= Html.fromHtml(content);
        title.setText(charSequence);
        title.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        title = (TextView) findViewById(R.id.title);
    }
}
