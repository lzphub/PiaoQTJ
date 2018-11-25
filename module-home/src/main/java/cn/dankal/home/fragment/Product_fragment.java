package cn.dankal.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.ProductTabBean;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.persenter.ProductClassifyContact;
import cn.dankal.home.persenter.ProductClassifyPresenter;

public class Product_fragment extends BaseFragment implements ProductClassifyContact.pcview{
    @BindView(R2.id.logo_img)
    ImageView logoImg;
    @BindView(R2.id.search_img)
    ImageView searchImg;
    @BindView(R2.id.res_text)
    TextView resText;
    @BindView(R2.id.need_ll)
    LinearLayout needLl;
    @BindView(R2.id.light_fix)
    RadioButton lightFix;
    @BindView(R2.id.led_bulbs)
    RadioButton ledBulbs;
    @BindView(R2.id.light_bulbs)
    RadioButton lightBulbs;
    @BindView(R2.id.christmas)
    RadioButton christmas;
    @BindView(R2.id.dricers)
    RadioButton dricers;
    @BindView(R2.id.electrical)
    RadioButton electrical;
    @BindView(R2.id.rope)
    RadioButton rope;
    @BindView(R2.id.emetgency)
    RadioButton emetgency;
    @BindView(R2.id.specialty_items)
    RadioButton specialtyItems;
    @BindView(R2.id.tab_rg)
    RadioGroup tabRg;
    @BindView(R2.id.list_title)
    TextView li2stTitle;
    @BindView(R2.id.product_list)
    RecyclerView productList;
    private List<ProductTabBean> productTabBeanList=new ArrayList<>();
    private ProductClassifyBean productClassBean;
    private ProductTabRvAdapter productTabRvAdapter;
    private ProductClassifyPresenter productClassifyPresenter=ProductClassifyPresenter.getPresenter();

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
        lightBulbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("LIGHT BULBS");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(2).getChildren());
                }
            }
        });
        ledBulbs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("LED BULBS");
                    productTabRvAdapter.updateData(productClassBean.getRoot().get(1).getChildren());
                }
            }
        });
        lightFix.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("LIGHT FIXTURES");
                    productTabRvAdapter.updateData(productClassBean.getRoot().get(0).getChildren());
                }
            }
        });
        christmas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("CHRISTMAS");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(3).getChildren());
                }
            }
        });
        dricers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("DRIVERS AND CONTROLLER");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(4).getChildren());
                }
            }
        });
        electrical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("ELECTRICAL");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(5).getChildren());
                }
            }
        });
        rope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("ROPE AND TAPE LIGHT");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(6).getChildren());
                }
            }
        });
        emetgency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("EXIT AND EMERGENCY");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(7).getChildren());
                }
            }
        });
        specialtyItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    li2stTitle.setText("SPECIALTY ITEMS");
//                    productTabRvAdapter.updateData(productClassBean.getRoot().get(8).getChildren());
                }
            }
        });
        searchImg.setOnClickListener(v -> ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R2.id.search_img, R2.id.need_ll})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.search_img) {
        } else if (i == R.id.need_ll) {
        }
    }

    @Override
    public void getDataSuccess(ProductClassifyBean productClassifyBean) {
        productList.setLayoutManager(new GridLayoutManager(getContext(),2));
        productTabRvAdapter=new ProductTabRvAdapter();
        productClassBean=productClassifyBean;
        productTabRvAdapter.addMore(productClassifyBean.getRoot().get(0).getChildren());
        productList.setAdapter(productTabRvAdapter);
        productTabRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductClassifyBean.RootBean.ChildrenBean>() {
            @Override
            public void onItemClick(View v, int position, ProductClassifyBean.RootBean.ChildrenBean data) {
                ARouter.getInstance().build(ProductProtocol.SCREEN).navigation();
            }
        });
    }
}
