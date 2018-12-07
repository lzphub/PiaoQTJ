package cn.dankal.home.activity;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.dankal.address.R;
import cn.dankal.basiclib.adapter.DemandRvAdapter;
import cn.dankal.basiclib.adapter.ProductRvAdapter;
import cn.dankal.basiclib.adapter.ProductSearchRvAdapter;
import cn.dankal.basiclib.adapter.TextOnlyAdapter;
import cn.dankal.basiclib.adapter.TextRvAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.base.recyclerview.OnRvItemClickListener;
import cn.dankal.basiclib.bean.DemandListbean;
import cn.dankal.basiclib.bean.ProductHomeListBean;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.common.cache.SDCacheDirCompat;
import cn.dankal.basiclib.protocol.HomeProtocol;
import cn.dankal.basiclib.protocol.ProductProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.home.persenter.ProductSearchContact;
import cn.dankal.home.persenter.ProductSearchPresenter;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMESEARCH;

@Route(path = HOMESEARCH)
public class HomeSearchActivity extends BaseActivity implements ProductSearchContact.searchview {

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
    private ProductSearchRvAdapter productRvAdapter;
    private DemandRvAdapter demandRvAdapter;
    private ProductSearchPresenter productSearchPresenter = ProductSearchPresenter.getPresenter();
    private String type;
    private android.support.constraint.ConstraintLayout ctlSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initComponents() {
        initView();
        productSearchPresenter.attachView(this);
        type = SharedPreferencesUtils.getString(this, "identity", "enterprise");
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
        search_delete.setOnClickListener(v -> etSearch.setText(""));

        //搜索记录搜索
        textOnlyAdapter.setOnRvItemClickListener(new OnRvItemClickListener<String>() {
            @Override
            public void onItemClick(View v, int position, String data) {
                searchLogo.setVisibility(View.GONE);
                search_delete.setVisibility(View.VISIBLE);
                etSearch.setText(textOnlyAdapter.getDatas().get(position));
                if (searchRecord.size() >= 10) {
                    searchRecord.remove(9);
                }
                searchRecord.add(0,textOnlyAdapter.getDatas().get(position));
                if (type.equals("user")) {
                    SDCacheDirCompat.writeObject("search", searchRecord);
                    productSearchPresenter.search(etSearch.getText().toString().trim(), "","");
                } else {
                    SDCacheDirCompat.writeObject("enterpriseSearch", searchRecord);
                    productSearchPresenter.demandSearch(etSearch.getText().toString().trim());
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null){
                    searchLogo.setVisibility(View.GONE);
                    search_delete.setVisibility(View.VISIBLE);
                }else{
                    search_delete.setVisibility(View.GONE);
                    searchLogo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                if(StringUtil.isValid(etSearch.getText().toString().trim())){
                    if (searchRecord.size() >= 10) {
                        searchRecord.remove(9);
                    }
                    searchRecord.add(0,etSearch.getText().toString());
                    if (type.equals("user")) {
                        SDCacheDirCompat.writeObject("search", searchRecord);
                        productSearchPresenter.search(etSearch.getText().toString().trim(), "","");
                    } else {
                        SDCacheDirCompat.writeObject("enterpriseSearch", searchRecord);
                        productSearchPresenter.demandSearch(etSearch.getText().toString().trim());
                    }
                    textOnlyAdapter.updateData(searchRecord);
                }
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
            }
            return false;
        });
        deleteEliminate.setOnClickListener(v -> {
            searchRecord.clear();
            if (type.equals("user")) {
                SDCacheDirCompat.writeObject("search", searchRecord);
                textOnlyAdapter.updateData(searchRecord);
            } else {
                SDCacheDirCompat.writeObject("enterpriseSearch", searchRecord);
                textOnlyAdapter.updateData(searchRecord);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productSearchPresenter.detachView();
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
        ctlSearch = findViewById(R.id.ctl_search);
    }

    private void getRecent() {
        if (type.equals("user")) {
            if (SDCacheDirCompat.readObject("search") != null) {
                searchRecord.addAll(SDCacheDirCompat.readObject("search"));
            }
        } else {
            if (SDCacheDirCompat.readObject("enterpriseSearch") != null) {
                searchRecord.addAll(SDCacheDirCompat.readObject("enterpriseSearch"));
            }
        }
        textOnlyAdapter.updateData(searchRecord);

    }

    @Override
    public void serarchDataSuccess(ProductHomeListBean productListBean) {
        ctlSearch.setVisibility(View.GONE);
        productRvAdapter = new ProductSearchRvAdapter();
        productRvAdapter.updateData(productListBean.getData());
        searchList.setAdapter(productRvAdapter);
        productRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<ProductHomeListBean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, ProductHomeListBean.DataBean data) {
                ARouter.getInstance().build(ProductProtocol.PRODUCTDETA).withString("uuid", data.getUuid()).navigation();
            }
        });
    }

    @Override
    public void demandSearchSuccess(DemandListbean demandListbean) {
        ctlSearch.setVisibility(View.GONE);
        demandRvAdapter = new DemandRvAdapter();
        demandRvAdapter.addMore(demandListbean.getData());
        searchList.setAdapter(demandRvAdapter);
        demandRvAdapter.setOnRvItemClickListener(new OnRvItemClickListener<DemandListbean.DataBean>() {
            @Override
            public void onItemClick(View v, int position, DemandListbean.DataBean data) {
                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).withSerializable("demandData", data).navigation();
            }
        });
    }
}
