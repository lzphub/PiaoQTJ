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
import cn.dankal.my.activity.MyWorkListDataActivity;

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
        if(type==2){
            returnText.setText("已提交认领该需求\n请等待后台审核");
        }else if(type==3){
            returnText.setText("已提交完成该需求\n请等待后台审核");
        }
        backImg.setOnClickListener(v -> finish());
        toHomeRl.setOnClickListener(v -> {
            finish();
            if(ActivityUtils.isActivityExistsInStack(DemandDetailsActivity.class)){
                ActivityUtils.finishActivity(DemandDetailsActivity.class);
            }
            if(ActivityUtils.isActivityExistsInStack(MyWorkListDataActivity.class)){
                ActivityUtils.finishActivity(MyWorkListDataActivity.class);
            }
        });
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        toHomeText = findViewById(R.id.toHome_text);
        toHomeRl = findViewById(R.id.toHome_Rl);
        returnText = findViewById(R.id.return_text);
    }
}
