package cn.dankal.my.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.MyServiceFactory;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dankal.basiclib.adapter.MyIdeaDataRvAdapter;
import cn.dankal.basiclib.adapter.MyIdeaListRvAdapter;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.bean.MyIdeaListBean;
import cn.dankal.basiclib.util.StateUtil;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

import static cn.dankal.basiclib.protocol.MyProtocol.IDEADATA;
import static cn.dankal.basiclib.protocol.MyProtocol.MYIDEA;

@Route(path = IDEADATA)
public class MyIdeaDataActivity extends BaseStateActivity {

    @BindView(R2.id.img_back)
    ImageView imgBack;
    @BindView(R2.id.tv_state)
    TextView tvState;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.rv_idea_img)
    RecyclerView rvIdeaImg;
    @BindView(R2.id.ll_content)
    LinearLayout llContent;

    private MyIdeaListBean.DataBean dataBean;
    private MyIdeaDataRvAdapter myIdeaDataRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_idea_data;
    }

    @Override
    protected void initComponents() {
        imgBack.setOnClickListener(v -> finish());
        dataBean= (MyIdeaListBean.DataBean) getIntent().getSerializableExtra("data");
        tvTitle.setText(dataBean.getTitle());
        tvContent.setText(dataBean.getDetail());
        tvState.setText(StateUtil.ideaState(1));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvIdeaImg.setLayoutManager(linearLayoutManager);
        myIdeaDataRvAdapter=new MyIdeaDataRvAdapter();
        myIdeaDataRvAdapter.addMore(dataBean.getImages());
        rvIdeaImg.setAdapter(myIdeaDataRvAdapter);
        rvIdeaImg.setNestedScrollingEnabled(false);
        rvIdeaImg.setHasFixedSize(true);
    }

    @Override
    public Object contentView() {
        return R.id.ll_content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
