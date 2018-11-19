package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import api.ProductServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.adapter.TextOnlyAdapter;
import cn.dankal.basiclib.adapter.TextRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.common.cache.SDCacheDirCompat;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.persenter.ProductSearchContact;
import cn.dankal.home.persenter.ProductSearchPresenter;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMESEARCH;

@Route(path = HOMESEARCH)
public class HomeSearchActivity extends BaseActivity implements ProductSearchContact.searchview{

    private android.widget.ImageView searchFinish;
    private android.widget.ImageView searchLogo;
    private android.widget.EditText etSearch;
    private android.widget.ImageView deleteEliminate;
    private android.support.v7.widget.RecyclerView searchList;
    private ImageView search_delete;
    private String identity;
    private android.widget.TextView title;
    private List<String> searchRecord = new ArrayList<>();
    private TextView jilu;
    private TextOnlyAdapter textOnlyAdapter;
    private ProductRvAdapter productRvAdapter;
    private ProductSearchPresenter productSearchPresenter=ProductSearchPresenter.getPresenter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initComponents() {
        initView();
        textOnlyAdapter = new TextOnlyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchList.setLayoutManager(linearLayoutManager);
        searchList.setAdapter(textOnlyAdapter);
        getRecent();

        identity = SharedPreferencesUtils.getString(this, "identity", "");
        if (identity.equals("user")) {
            title.setText("SEARCH");
            etSearch.setHint("PLEASE SEARCH FOR PRODUCTS");
            jilu.setText("RECENT SEARCHES");
        }
        searchFinish.setOnClickListener(v -> finish());
        etSearch.setOnFocusChangeListener((v, hasFocus) -> {
            searchLogo.setVisibility(View.GONE);
            search_delete.setVisibility(View.VISIBLE);
        });
        search_delete.setOnClickListener(v -> etSearch.setText(""));
        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (searchRecord.size() >= 10) {
                    searchRecord.remove(0);
                }
                searchRecord.add(etSearch.getText().toString());
                SDCacheDirCompat.writeObject("search", searchRecord);
                textOnlyAdapter.updateData(searchRecord);
                productSearchPresenter.search(etSearch.getText().toString().trim(),"");
            }
            return false;
        });
        deleteEliminate.setOnClickListener(v -> {
            searchRecord.clear();
            SDCacheDirCompat.writeObject("search", searchRecord);
            textOnlyAdapter.updateData(searchRecord);
        });
    }

    private void initView() {
        searchFinish = findViewById(R.id.search_finish);
        searchLogo = findViewById(R.id.search_logo);
        etSearch = findViewById(R.id.et_search);
        deleteEliminate = findViewById(R.id.delete_eliminate);
        searchList = findViewById(R.id.search_list);
        search_delete = findViewById(R.id.search_delete);
        title = findViewById(R.id.title);
        jilu = findViewById(R.id.jilu);
    }

    private void getRecent() {
        if (SDCacheDirCompat.readObject("search") != null) {
            searchRecord.addAll(SDCacheDirCompat.readObject("search"));
            textOnlyAdapter.updateData(searchRecord);
        }
    }

    @Override
    public void serarchDataSuccess(ProductListBean productListBean) {
        productRvAdapter=new ProductRvAdapter();
        productRvAdapter.updateData(productListBean.getData());
        searchList.setAdapter(productRvAdapter);
    }
}
