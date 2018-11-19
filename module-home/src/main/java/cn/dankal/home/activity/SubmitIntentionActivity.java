package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.ActivityUtils;
import cn.dankal.basiclib.util.SharedPreferencesUtils;

import static cn.dankal.basiclib.protocol.HomeProtocol.SUBMITINTENTION;

@Route(path = SUBMITINTENTION)
public class SubmitIntentionActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.LinearLayout tohomeLl;
    private String feedback="";
    private android.widget.TextView content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_intention;
    }

    @Override
    protected void initComponents() {
        initView();
        feedback= getIntent().getStringExtra("feedback");
        if(feedback.equals("feedback")){
            content.setText("Feedback success");
        }
        backImg.setOnClickListener(v -> finish());
        tohomeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ActivityUtils.finishOtherActivities(UserHomeActivity.class);
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        tohomeLl =  findViewById(R.id.tohome_ll);
        content = findViewById(R.id.content);
    }
}
