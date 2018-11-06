package cn.dankal.my.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.TextRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.COMPROB;

@Route(path = COMPROB)
public class ComProbActivity extends BaseActivity {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private android.support.v7.widget.RecyclerView recordsList;
    private List<String> stringList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_records;
    }

    @Override
    protected void initComponents() {
        initView();
        for(int i=0;i<10;i++){
            stringList.add("常见问题"+i);
        }
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recordsList.setLayoutManager(linearLayoutManager);
        TextRvAdapter textRvAdapter=new TextRvAdapter();
        textRvAdapter.addMore(stringList);
        recordsList.setAdapter(textRvAdapter);
        textRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<String>() {
            @Override
            public void onItemClick(View v, int position, String data) {
                ARouter.getInstance().build(MyProtocol.COMPORDATA).navigation();
            }
        });
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
        recordsList = (RecyclerView) findViewById(R.id.records_list);
    }
}
