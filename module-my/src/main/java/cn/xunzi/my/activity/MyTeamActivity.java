package cn.xunzi.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.protocol.MyProtocol;
import cn.xunzi.setting.R;

import static cn.xunzi.basiclib.protocol.MyProtocol.MYTEAM;

@Route(path = MYTEAM)
public class MyTeamActivity extends BaseActivity {

    private ImageView ivBack;
    private RelativeLayout rlMsg;
    private ImageView ivMsglogo;
    private RelativeLayout rlSysmsg;
    private ImageView ivSysmsglogo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        rlMsg.setOnClickListener(view -> ARouter.getInstance().build(MyProtocol.MYTEAMLIST).withInt("type",1).navigation());
        rlSysmsg.setOnClickListener(view -> ARouter.getInstance().build(MyProtocol.MYTEAMLIST).withInt("type",2).navigation());

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rlMsg = (RelativeLayout) findViewById(R.id.rl_msg);
        ivMsglogo = (ImageView) findViewById(R.id.iv_msglogo);
        rlSysmsg = (RelativeLayout) findViewById(R.id.rl_sysmsg);
        ivSysmsglogo = (ImageView) findViewById(R.id.iv_sysmsglogo);
    }
}
