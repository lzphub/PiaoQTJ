package cn.dankal.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dankal.basiclib.adapter.OnlyImgRvAdapter;
import cn.dankal.basiclib.base.activity.BaseStateActivity;
import cn.dankal.basiclib.bean.MyWorkDataBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.StateUtil;
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
    @BindView(R2.id.rv_plan_finish_img)
    RecyclerView rvPlanFinishImg;
    @BindView(R2.id.tv_plan_finish_details)
    TextView tvPlanFinishDetails;
    @BindView(R2.id.tv_plan_refused_details)
    TextView tvPlanRefusedDetails;
    @BindView(R2.id.tv_refused)
    TextView tvRefused;
    @BindView(R2.id.tv_claim_refused)
    TextView tvClaimRefused;
    @BindView(R2.id.tv_claim_refused_details)
    TextView tvClaimRefusedDetails;
    private String orderId;
    private int statusId;

    private WorkDataPersenter workDataPersenter = WorkDataPersenter.getBankCardPersenter();
    private LinearLayout llFinish;
    private TextView tvFinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_work_list_data;
    }

    @Override
    protected void initComponents() {
        orderId = getIntent().getStringExtra("uuid");
        statusId = getIntent().getIntExtra("statusId", 1);
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
        initView();
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
        tvState.setText(StateUtil.WorkListState(statusId));
        if (statusId == 8) {
            llFinish.setVisibility(View.VISIBLE);
            tvFinish.setVisibility(View.VISIBLE);
            tvRefused.setVisibility(View.VISIBLE);
            tvPlanRefusedDetails.setVisibility(View.VISIBLE);
            tvFinish.setText("重新提交");
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvPlanFinishImg.setLayoutManager(linearLayoutManager1);
            OnlyImgRvAdapter onlyImgRvAdapter2 = new OnlyImgRvAdapter();
            onlyImgRvAdapter2.addMore(myWorkDataBean.getCpl().get(0).getCpl_images());
            rvPlanImg.setAdapter(onlyImgRvAdapter2);
            tvPlanFinishDetails.setText(myWorkDataBean.getCpl().get(0).getCpl_detail());
            tvRefused.setText(myWorkDataBean.getCpl().get(0).getRefuse_reason());
        }
        if (statusId == 6 || statusId == 7) {
            llFinish.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvPlanFinishImg.setLayoutManager(linearLayoutManager1);
            OnlyImgRvAdapter onlyImgRvAdapter2 = new OnlyImgRvAdapter();
            onlyImgRvAdapter2.addMore(myWorkDataBean.getCpl().get(0).getCpl_images());
            rvPlanImg.setAdapter(onlyImgRvAdapter2);
            tvPlanFinishDetails.setText(myWorkDataBean.getCpl().get(0).getCpl_detail());
        }
        if (statusId == 5) {
            tvClaimRefused.setVisibility(View.VISIBLE);
            tvClaimRefusedDetails.setVisibility(View.VISIBLE);
        }
        if (statusId == 4) {
            tvFinish.setVisibility(View.VISIBLE);
        }
        tvFinish.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.FINISHWORK).withString("project_uuid", myWorkDataBean.getProject().getUuid()).withString("plan_uuid", myWorkDataBean.getPlan().get(0).getPlan_uuid()).navigation());
    }

    private void initView() {
        llFinish = findViewById(R.id.ll_finish);
        tvFinish = findViewById(R.id.tv_finish);
    }
}
