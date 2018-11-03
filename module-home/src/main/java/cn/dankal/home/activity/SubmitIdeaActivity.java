package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.util.ActivityUtils;

import static cn.dankal.basiclib.protocol.HomeProtocol.SUBMITIDEA;

@Route(path = SUBMITIDEA)
public class SubmitIdeaActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.TextView toHomeText;
    private android.widget.RelativeLayout toHomeRl;
    private int type;
    private TextView returnText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_submit_idea;
    }

    @Override
    protected void initComponents() {
        initView();
        type=getIntent().getIntExtra("type",1);
        if(type!=1){
            returnText.setText("已提交认领该需求\n请等待后台审核");
        }
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toHomeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(ActivityUtils.isActivityExistsInStack(DemandDetailsActivity.class)){
                    ActivityUtils.finishActivity(DemandDetailsActivity.class);
                }
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        toHomeText = (TextView) findViewById(R.id.toHome_text);
        toHomeRl = (RelativeLayout) findViewById(R.id.toHome_Rl);
        returnText = (TextView) findViewById(R.id.return_text);
    }
}
