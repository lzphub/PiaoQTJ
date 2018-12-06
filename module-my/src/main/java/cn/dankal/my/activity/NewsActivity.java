package cn.dankal.my.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.basiclib.adapter.SystemMsgRvAdapter;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.activity.BaseRecyclerViewActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.SystemMsgBean;
import cn.dankal.basiclib.common.OnFinishLoadDataListener;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.my.presenter.EngineerNewsPersenter;
import cn.dankal.my.presenter.NewsPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SYSTEMNEWS;

@Route(path = SYSTEMNEWS)
public class NewsActivity extends BaseRvActivity<SystemMsgBean.DataBean> {

    private ImageView backImg;
    private TextView titleText;
    private SystemMsgRvAdapter systemMsgRvAdapter;
    private NewsPersenter newsPersenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        backImg.setOnClickListener(v -> finish());
        titleText.setText("SYSTEM MESSAGES");

    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        titleText = findViewById(R.id.title_text);
    }

    @Override
    public BaseRecyclerViewPresenter<SystemMsgBean.DataBean> getPresenter() {
        newsPersenter = new NewsPersenter();
        return newsPersenter;

    }

    @Override
    public BaseRecyclerViewAdapter<SystemMsgBean.DataBean> getAdapter() {
        systemMsgRvAdapter = new SystemMsgRvAdapter();
        systemMsgRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<SystemMsgBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, SystemMsgBean.DataBean data) {
                if(data.getKind()==1){
                    ARouter.getInstance().build(MyProtocol.SYSTEMNEWSCONTENT).withString("content",data.getContent()).navigation();
                }else{
                    ARouter.getInstance().build(MyProtocol.WORKDATA).withString("uuid",data.getUuid()).navigation();
                }
            }
        });
        return systemMsgRvAdapter;
    }

}
