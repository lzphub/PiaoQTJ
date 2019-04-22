package cn.xunzi.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import api.HomeServiceFactory;
import cn.xunzi.address.R;
import cn.xunzi.basiclib.XZUserManager;
import cn.xunzi.basiclib.adapter.MessageListAdapter;
import cn.xunzi.basiclib.adapter.SysMessageListAdapter;
import cn.xunzi.basiclib.base.activity.BaseActivity;
import cn.xunzi.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.xunzi.basiclib.bean.MessageBean;
import cn.xunzi.basiclib.bean.PostBean;
import cn.xunzi.basiclib.bean.SystemMsgBean;
import cn.xunzi.basiclib.protocol.HomeProtocol;
import cn.xunzi.basiclib.rx.AbstractDialogSubscriber;
import cn.xunzi.basiclib.util.ToastUtils;

import static cn.xunzi.basiclib.protocol.HomeProtocol.MESSAGELIST;

@Route(path = MESSAGELIST)
public class MessageListActivity extends BaseActivity {

    private ImageView ivBack;
    private RecyclerView messageList;
    private int type;
    private TextView tvTitle;
    private MessageListAdapter messageListAdapter;
    private SysMessageListAdapter sysMessageListAdapter;

    @Override
    protected int getLayoutId() {
        type = getIntent().getIntExtra("type", 0);
        return R.layout.activity_message_list;
    }

    @Override
    protected void initComponents() {
        initView();
        ivBack.setOnClickListener(view -> finish());

        messageList.setLayoutManager(new LinearLayoutManager(this));

        if (type == 1) {
            tvTitle.setText("个人消息");
            messageListAdapter = new MessageListAdapter();
            messageList.setAdapter(messageListAdapter);
            HomeServiceFactory.getMessage(XZUserManager.getUserInfo().getId() + "", type + "").safeSubscribe(new AbstractDialogSubscriber<MessageBean>(MessageListActivity.this) {
                @Override
                public void onNext(MessageBean messageBean) {
                    if (messageBean.getCode().equals("200")) {
                        messageListAdapter.updateData(messageBean.getData().getList());
                    }else{
                        ToastUtils.showShort(messageBean.getMsg());
                    }
                }
            });
        } else {
            tvTitle.setText("系统消息");
            sysMessageListAdapter=new SysMessageListAdapter();
            messageList.setAdapter(sysMessageListAdapter);
            sysMessageListAdapter.setOnRvItemClickListener(new OnRvItemClickListener<SystemMsgBean.DataEntity.ListEntity>() {
                @Override
                public void onItemClick(View v, int position, SystemMsgBean.DataEntity.ListEntity data) {
                    ARouter.getInstance().build(HomeProtocol.SYSTEMMSG).withString("data",data.getDetail()).navigation();
                }
            });
            HomeServiceFactory.getSysMsg("2").safeSubscribe(new AbstractDialogSubscriber<SystemMsgBean>(MessageListActivity.this) {
                @Override
                public void onNext(SystemMsgBean systemMsgBean) {
                    if(systemMsgBean.getCode().equals("200")){
                        sysMessageListAdapter.updateData(systemMsgBean.getData().getList());
                    }else{
                        ToastUtils.showShort(systemMsgBean.getMsg());
                    }
                }
            });
        }

    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        messageList = findViewById(R.id.message_list);
        tvTitle = findViewById(R.id.tv_title);
    }
}
