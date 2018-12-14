package cn.dankal.home.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProjectDataBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.home.persenter.ProjectDetaContact;
import cn.dankal.home.persenter.ProjectDetaPersenter;

import static cn.dankal.basiclib.protocol.HomeProtocol.DEMANDDETA;

@Route(path = DEMANDDETA)
public class DemandDetailsActivity extends BaseActivity implements ProjectDetaContact.pcview {

    private android.widget.Button claimBtn;
    private android.widget.ImageView backImg;
    private android.widget.TextView demandTitle;
    private android.widget.TextView demandPrice;
    private android.widget.TextView demandTime;
    private WebView demandData;
    private DemandListbean.DataBean dataBean;
    private String project_uuid;
    private ProjectDetaPersenter projectDetaPersenter;
    private String time="";
    private String plan_uuid="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_demand_details;
    }

    @Override
    protected void initComponents() {
        initView();
        project_uuid=getIntent().getStringExtra("project_uuid");
        time=getIntent().getStringExtra("time");
        plan_uuid=getIntent().getStringExtra("plan_uuid");
        projectDetaPersenter=new ProjectDetaPersenter();
        projectDetaPersenter.attachView(this);
        projectDetaPersenter.getProjectData(project_uuid);
        backImg.setOnClickListener(v -> finish());
    }

    private void initView() {
        claimBtn = findViewById(R.id.claim_btn);
        backImg = findViewById(R.id.back_img);
        demandTitle = findViewById(R.id.demand_title);
        demandPrice = findViewById(R.id.demand_price);
        demandTime = findViewById(R.id.demand_time);
        demandData = findViewById(R.id.demand_data);

        WebSettings webSettings = demandData.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        demandData.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset(view);
            }
        });
    }
    private void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " + "}" + "})()");
    }

    @Override
    public void getProjectDataSuccess(ProjectDataBean projectDataBean) {
        demandTitle.setText(projectDataBean.getName());
        demandPrice.setText("定制价格：$"+StringUtil.isDigits(projectDataBean.getStart_price())+" ~ "+StringUtil.isDigits(projectDataBean.getEnd_price()));
        demandTime.setText("交付工期："+projectDataBean.getCpl_start_date()+" ~ "+projectDataBean.getCpl_end_date());
        demandData.loadDataWithBaseURL(null,projectDataBean.getDetail(), "text/html", "UTF-8", null);

        claimBtn.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.CLAIMDEMAND).withSerializable("demandData", projectDataBean).withString("time",time).withString("plan_uuid",plan_uuid).navigation());

    }
}
