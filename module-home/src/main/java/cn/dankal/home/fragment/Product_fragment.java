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
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.widget.VerticalTablayout.VerticalTabLayout;
import cn.dankal.home.persenter.ProductClassifyContact;
import cn.dankal.home.persenter.ProductClassifyPresenter;

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
        for (int i = 0; i < productClassifyBean.getRoot().size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(productClassifyBean.getRoot().get(i).getName()));
        }
        if(productClassifyBean.getRoot()!=null){
            tabLayout.setSelectedTab(0);
            li2stTitle.setText(productClassBean.getRoot().get(0).getName());
        }

        tabLayout.setOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(VerticalTabLayout.Tab tab, int position) {
                productTabRvAdapter.updateData(productClassBean.getRoot().get(position).getChildren());
                productList.setAdapter(productTabRvAdapter);
                li2stTitle.setText(productClassBean.getRoot().get(position).getName());
            }

            @Override
            public void onTabReleased(VerticalTabLayout.Tab tab, int position) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
