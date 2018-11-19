package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.TRANSACTIONRECORD;

@Route(path = TRANSACTIONRECORD)
public class TransactionRecordsActivity extends BaseRvActivity<String> {

    private android.widget.ImageView backImg;
    private android.support.v7.widget.RecyclerView recordsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    public void initComponents() {
        initView();
        backImg.setOnClickListener(v -> finish());
    }

    private void initView() {
        backImg = findViewById(R.id.back_img);
    }

    @Override
    public BaseRecyclerViewPresenter<String> getPresenter() {
        return null;
    }

    @Override
    public BaseRecyclerViewAdapter<String> getAdapter() {
        return null;
    }
}
