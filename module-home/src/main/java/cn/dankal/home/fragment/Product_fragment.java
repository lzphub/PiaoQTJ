package cn.dankal.home.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.dankal.address.R;
import cn.dankal.address.R2;
import cn.dankal.basiclib.adapter.ProductTabRvAdapter;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.ProductClassifyBean;
import cn.dankal.basiclib.bean.ProductTabBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.DisplayHelper;
import cn.dankal.home.persenter.ProductClassifyContact;
import cn.dankal.home.persenter.ProductClassifyPresenter;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class Product_fragment extends BaseFragment implements ProductClassifyContact.pcview {
    @BindView(R2.id.logo_img)
    ImageView logoImg;
    @BindView(R2.id.search_img)
    ImageView searchImg;
    @BindView(R2.id.res_text)
    TextView resText;
    @BindView(R2.id.need_ll)
    LinearLayout needLl;
    @BindView(R2.id.list_title)
    TextView li2stTitle;
    @BindView(R2.id.product_list)
    RecyclerView productList;
    Unbinder unbinder;
    @BindView(R2.id.tab_layout)
    VerticalTabLayout tabLayout;

    private ProductClassifyBean productClassBean;
    private ProductTabRvAdapter productTabRvAdapter;
    private ProductClassifyPresenter productClassifyPresenter = ProductClassifyPresenter.getPresenter();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        productClassifyPresenter.attachView(this);
        productClassifyPresenter.getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R2.id.search_img, R2.id.need_ll})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.search_img) {
            ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation();
        } else if (i == R.id.need_ll) {
            ARouter.getInstance().build(HomeProtocol.POSTREQUEST).navigation();
        }
    }

    @Override
    public void getDataSuccess(ProductClassifyBean productClassifyBean) {
        productList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productTabRvAdapter = new ProductTabRvAdapter();
        productClassBean = productClassifyBean;
        productTabRvAdapter.addMore(productClassifyBean.getRoot().get(0).getChildren());
        productList.setAdapter(productTabRvAdapter);
        productTabRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductClassifyBean.RootBean.ChildrenBean>() {
            @Override
            public void onItemClick(View v, int position, ProductClassifyBean.RootBean.ChildrenBean data) {
                ARouter.getInstance().build(ProductProtocol.SCREEN).withString("uuid", data.getUuid()).navigation();
            }
        });

        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                productTabRvAdapter.updateData(productClassBean.getRoot().get(position).getChildren());
                li2stTitle.setText(productClassBean.getRoot().get(position).getName());
                for(int i=0;i<tabLayout.getTabCount();i++){
                    tabLayout.getTabAt(i).setBackgroundColor(Color.parseColor("#F6F6F6"));
                }
                tab.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        tabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return productClassifyBean.getRoot().size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(productClassifyBean.getRoot().get(position).getName())
                        .setTextColor(Color.parseColor("#6fba27"), Color.parseColor("#999999"))
                        .setTextSize(13)
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        if(tabLayout!=null){
            tabLayout.getTabAt(0).setBackgroundColor(Color.WHITE);
            li2stTitle.setText(tabLayout.getTabAt(0).getTitle().getContent());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
