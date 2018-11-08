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
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyRequestBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.my.entity.TabEntity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.MYREQUEST;

@Route(path = MYREQUEST)
public class MyRequestActivity extends BaseActivity {

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
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
        titleText.setText("MY REQUEST");
        initView();
        initRv();
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        recordsList = (RecyclerView) findViewById(R.id.records_list);
    }

    private void initRv(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordsList.setLayoutManager(linearLayoutManager);
        myRequestRvAdapter=new MyRequestRvAdapter();
        for(int x=0;x<3;x++){
            MyRequestBean.urli urli=new MyRequestBean.urli();
            urli.setImgurl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541421297535&di=7ada3a21db141cb769f8bce9d407686b&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fdbfb6a5c4276ef323c6b140f40b688363f7c52dd.jpg");
            urliList.add(urli);
        }
        for(int i=0;i<4;i++){

            MyRequestBean myRequestBean=new MyRequestBean();
            myRequestBean.setUrlString(urliList);
            requestBeans.add(myRequestBean);
        }
        myRequestRvAdapter.addMore(requestBeans);
        recordsList.setAdapter(myRequestRvAdapter);
        myRequestRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyRequestBean>() {
            @Override
            public void onItemClick(View v, int position, MyRequestBean data) {
                ARouter.getInstance().build(MyProtocol.MYREQUESTDETA).navigation();
            }
        });
        recordsList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
                    myRequestRvAdapter.addMore(requestBeans);
                }
            }
        });
    }
}
