package cn.dankal.my.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.basiclib.adapter.MyIntentionRvAdapter;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.MyIntentionBean;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.setting.R;

public class MyIntentionAllFragment extends BaseFragment {

    private android.support.v7.widget.RecyclerView intentionList;
    private MyIntentionRvAdapter myIntentionRvAdapter;
    private List<MyIntentionBean> intentionBeans=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_intention;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        for(int i=0;i<5;i++){
            MyIntentionBean myIntentionBean=new MyIntentionBean();
            intentionBeans.add(myIntentionBean);
        }
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        intentionList.setLayoutManager(linearLayoutManager);
        myIntentionRvAdapter=new MyIntentionRvAdapter();
        myIntentionRvAdapter.addMore(intentionBeans);
        intentionList.setAdapter(myIntentionRvAdapter);

        myIntentionRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<MyIntentionBean>() {
            @Override
            public void onItemClick(View v, int position, MyIntentionBean data) {
                ARouter.getInstance().build(MyProtocol.MYINTENTIONDETA).navigation();
            }
        });
        intentionList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!intentionList.canScrollVertically(1)){
                    myIntentionRvAdapter.addMore(intentionBeans);
                }
            }
        });
    }

    private void initView(View view) {
        intentionList = view.findViewById(R.id.intention_list);
    }
}
