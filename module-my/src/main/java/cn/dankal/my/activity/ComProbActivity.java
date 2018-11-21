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
import cn.dankal.basiclib.base.BaseRvActivity;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.ComProbBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.my.presenter.ComProbEngPersenter;
import cn.dankal.my.presenter.ComProbPersenter;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.COMPROB;

@Route(path = COMPROB)
public class ComProbActivity extends BaseRvActivity<ComProbBean.DataBean> {
    private android.widget.ImageView backImg;
    private android.widget.TextView titleText;
    private TextRvAdapter textRvAdapter;
    private String type;

    @Override
    protected int getLayoutId() {
        type= SharedPreferencesUtils.getString(this,"identity","user");
        return R.layout.activity_transaction_records;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        initView();
        backImg.setOnClickListener(v -> finish());
        if(type.equals("user")){
            titleText.setText("FAQ");
        }else{
            titleText.setText("常见问题");
        }
    }

    private void initView() {
        backImg = (ImageView) findViewById(R.id.back_img);
        titleText = (TextView) findViewById(R.id.title_text);
    }

    @Override
    public BaseRecyclerViewPresenter<ComProbBean.DataBean> getPresenter() {
        if(type.equals("user")){
            return new ComProbPersenter();
        }else{
            return new ComProbEngPersenter();
        }
    }

    @Override
    public BaseRecyclerViewAdapter<ComProbBean.DataBean> getAdapter() {
        textRvAdapter=new TextRvAdapter();
        textRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ComProbBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, ComProbBean.DataBean data) {
                ARouter.getInstance().build(MyProtocol.COMPORDATA).navigation();
            }
        });
        return textRvAdapter;
    }
}
