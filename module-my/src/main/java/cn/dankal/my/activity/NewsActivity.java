package cn.dankal.my.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.bean.NewsBean;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.SYSTEMNEWS;

@Route(path = SYSTEMNEWS)
public class NewsActivity extends BaseRvActivity<NewsBean> {

    private ImageView backImg;
    private TextView titleText;
    private RecyclerView recordsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    public void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleText.setText("系统消息");
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
    }


    @Override
    public BaseRecyclerViewPresenter<NewsBean> getPresenter() {
        return null;
    }

    @Override
    public BaseRecyclerViewAdapter<NewsBean> getAdapter() {
        return null;
    }
}
