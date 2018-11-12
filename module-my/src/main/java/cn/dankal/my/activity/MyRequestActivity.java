package cn.dankal.my.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyRequestRvAdapter;
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.my.presenter.MyRequestPresenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYREQUEST;

@Route(path = MYREQUEST)
public class MyRequestActivity extends BaseRvActivity<MyRequestBean> {

    private ImageView backImg;
    private TextView titleText;
    private android.support.v7.widget.RecyclerView recordsList;
    private List<MyRequestBean> requestBeans=new ArrayList<>();
    private List<MyRequestBean.urli> urliList=new ArrayList<>();
    private MyRequestRvAdapter myRequestRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        backImg.setOnClickListener(v -> finish());
        titleText.setText("MY REQUEST");
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
    }



    @Override
    public BaseRecyclerViewPresenter<MyRequestBean> getPresenter() {
        return new MyRequestPresenter();
    }

    @Override
    public BaseRecyclerViewAdapter<MyRequestBean> getAdapter() {
        myRequestRvAdapter=new MyRequestRvAdapter();
        myRequestRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyRequestBean>() {
            @Override
            public void onItemClick(View v, int position, MyRequestBean data) {
                ARouter.getInstance().build(MyProtocol.MYREQUESTDETA).navigation();
            }
        });
        return myRequestRvAdapter;
    }
}
