package cn.dankal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.TRANSACTIONRECORD;

@Route(path = TRANSACTIONRECORD)
public class TransactionRecordsActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.support.v7.widget.RecyclerView recordsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    protected void initComponents() {
        initView();
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        recordsList = (RecyclerView) findViewById(R.id.records_list);
    }
}
