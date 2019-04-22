package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.HomeServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.bean.SystemMsgBean;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;

import static cn.xunzi.basiclib.protocol.HomeProtocol.MESSAGE;

@Route(path = MESSAGE)
public class MessageActivity extends BaseActivity {

    private ImageView ivBack;
    private RelativeLayout rlMsg;
    private RelativeLayout rlSysmsg;
    private ImageView ivMsglogo;
    private ImageView ivSysmsglogo;
    private android.widget.TextView tvNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());
        rlMsg.setOnClickListener(view -> ARouter.getInstance().build(HomeProtocol.MESSAGELIST).withInt("type",1).navigation());
        rlSysmsg.setOnClickListener(view -> ARouter.getInstance().build(HomeProtocol.MESSAGELIST).withInt("type",2).navigation());

        HomeServiceFactory.getSysMsg("2").safeSubscribe(new AbstractDialogSubscriber<SystemMsgBean>(MessageActivity.this) {
            @Override
            public void onNext(SystemMsgBean systemMsgBean) {
                if(systemMsgBean.getCode().equals("200")){
                   tvNum.setText(systemMsgBean.getData().getList().size()+"");
                   if(systemMsgBean.getData().getList().size()<=0){
                       tvNum.setVisibility(View.GONE);
                   }
                   if(null==systemMsgBean.getData()){
                       tvNum.setVisibility(View.GONE);
                   }
                }else{
                    ToastUtils.showShort(systemMsgBean.getMsg());
                    tvNum.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        rlMsg = findViewById(R.id.rl_msg);
        rlSysmsg = findViewById(R.id.rl_sysmsg);
        ivMsglogo = findViewById(R.id.iv_msglogo);
        ivSysmsglogo = findViewById(R.id.iv_sysmsglogo);
        tvNum = findViewById(R.id.tv_num);
    }
}
