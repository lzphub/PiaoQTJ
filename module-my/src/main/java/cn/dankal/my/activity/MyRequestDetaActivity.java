package cn.dankal.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.adapter.InternalImgRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.bean.RequestDataBean;
import cn.dankal.basiclib.util.StateUtil;
import cn.dankal.my.presenter.MyRequestPresenter;
import cn.dankal.my.presenter.RequestContact;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYREQUESTDETA;

/**
 * 我的需求详情
 */
@Route(path = MYREQUESTDETA)
public class  MyRequestDetaActivity extends BaseActivity implements RequestContact.RequestView {

    private android.widget.ImageView backImg;
    private android.widget.TextView requestTitle;
    private android.widget.TextView requestPrice;
    private android.widget.TextView requestPeriod;
    private android.support.v7.widget.RecyclerView addImgRv;
    private android.widget.TextView requestContent;
    private MyRequestPresenter myRequestPresenter=MyRequestPresenter.getPSPresenter();
    private String demandid;
    private InternalImgRvAdapter internalImgRvAdapter;
    private ImageView ivState;
    private TextView tvState;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_request_deta;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        demandid=getIntent().getStringExtra("demand_id");
        myRequestPresenter.attachView(this);
        myRequestPresenter.getRequestData(demandid);
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
        requestTitle = findViewById(R.id.request_title);
        requestPrice = findViewById(R.id.request_price);
        requestPeriod = findViewById(R.id.request_period);
        addImgRv = findViewById(R.id.add_img_rv);
        requestContent = findViewById(R.id.request_content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        addImgRv.setLayoutManager(linearLayoutManager);
        ivState = findViewById(R.id.iv_state);
        tvState = findViewById(R.id.tv_state);
    }

    @Override
    public void getDataSuccess(MyRequestBean myRequestBean) {

    }

    @Override
    public void updata(MyRequestBean myRequestBean) {

    }

    @Override
    public void getRequestDataSuccess(RequestDataBean databean) {
        requestTitle.setText(databean.getTitle());
        requestPrice.setText("$"+databean.getStart_price()+"~"+databean.getEnd_price());
        requestPeriod.setText(databean.getStart_date()+"~"+databean.getEnd_date());
        internalImgRvAdapter =new InternalImgRvAdapter();
        addImgRv.setAdapter(internalImgRvAdapter);
        addImgRv.setHasFixedSize(true);
        addImgRv.setNestedScrollingEnabled(false);
        internalImgRvAdapter.updateData(databean.getImages());
        ivState.setImageResource(StateUtil.requestStateImg(databean.getStatus()));
        tvState.setText(StateUtil.requestState(databean.getStatus()));
        requestContent.setText(databean.getDescription());
    }
}
