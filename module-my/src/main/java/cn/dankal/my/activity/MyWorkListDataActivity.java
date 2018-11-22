package cn.dankal.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dankal.basiclib.adapter.OnlyImgRvAdapter;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.bean.MyWorkDataBean;
import cn.dankal.my.presenter.WorkDataContact;
import cn.dankal.my.presenter.WorkDataPersenter;
import cn.dankal.setting.R;
import cn.dankal.setting.R2;

import static cn.dankal.basiclib.protocol.MyProtocol.WORKDATA;

@Route(path = WORKDATA)
public class MyWorkListDataActivity extends BaseStateActivity implements WorkDataContact.bcView {

    @BindView(R2.id.img_back)
    ImageView imgBack;
    @BindView(R2.id.tv_state)
    TextView tvState;
    @BindView(R2.id.tv_price)
    TextView tvPrice;
    @BindView(R2.id.tv_date)
    TextView tvDate;
    @BindView(R2.id.tv_plan_details)
    TextView tvPlanDetails;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.rv_idea_img)
    RecyclerView rvIdeaImg;
    @BindView(R2.id.ll_content)
    LinearLayout llContent;
    @BindView(R2.id.rv_plan_img)
    RecyclerView rvPlanImg;
    private String orderId;

    private WorkDataPersenter workDataPersenter = WorkDataPersenter.getBankCardPersenter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list_data;
    }

    @Override
    protected void initComponents() {
        orderId = getIntent().getStringExtra("uuid");
        imgBack.setOnClickListener(v -> finish());
        workDataPersenter.attachView(this);
        workDataPersenter.getWorkData(orderId);
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

    @Override
    public void getWordDataSuccess(MyWorkDataBean myWorkDataBean) {
        tvPlanDetails.setText(myWorkDataBean.getPlan().get(0).getPlan_detail());
        tvPrice.setText("预计价格：$" + myWorkDataBean.getProject().getStart_price() + "~" + myWorkDataBean.getProject().getEnd_price());
        tvDate.setText("交付工期：" + myWorkDataBean.getProject().getCpl_start_date() + "~" + myWorkDataBean.getProject().getCpl_end_date());
        tvTitle.setText(myWorkDataBean.getProject().getName());
        tvContent.setText(myWorkDataBean.getProject().getDesc());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPlanImg.setLayoutManager(linearLayoutManager);
        OnlyImgRvAdapter onlyImgRvAdapter = new OnlyImgRvAdapter();
        onlyImgRvAdapter.addMore(myWorkDataBean.getPlan().get(0).getPlan_images());
        rvPlanImg.setAdapter(onlyImgRvAdapter);
    }
}
