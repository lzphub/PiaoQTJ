package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import api.ProductServiceFactory;
import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.ProductListBean;
import cn.dankal.basiclib.bean.ServiceTextBean;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.SharedPreferencesUtils;
import cn.dankal.basiclib.util.StringUtil;
import cn.dankal.basiclib.util.ToastUtils;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMESEARCH;

@Route(path = HOMESEARCH)
public class HomeSearchActivity extends BaseActivity {

    private android.widget.ImageView searchFinish;
    private android.widget.ImageView searchLogo;
    private android.widget.EditText etSearch;
    private android.widget.ImageView deleteEliminate;
    private android.support.v7.widget.RecyclerView searchList;
    private ImageView search_delete;
    private String identity;
    private android.widget.TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initComponents() {
        initView();
        identity= SharedPreferencesUtils.getString(this,"identity","");
        if(identity.equals("user")){
            title.setText("SEARCH");
            etSearch.setHint("PLEASE SEARCH FOR PRODUCTS");
        }
        searchFinish.setOnClickListener(v -> finish());
     etSearch.setOnFocusChangeListener((v, hasFocus) -> {
         searchLogo.setVisibility(View.GONE);
         search_delete.setVisibility(View.VISIBLE);
     });
     search_delete.setOnClickListener(v -> etSearch.setText(""));
     etSearch.setOnKeyListener((v, keyCode, event) -> {
         if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
             ProductServiceFactory.getProductlist(etSearch.getText().toString().trim(),"").safeSubscribe(new AbstractDialogSubscriber<ProductListBean>(HomeSearchActivity.this) {
                 @Override
                 public void onNext(ProductListBean productListBean) {
                     Logger.d("onnext",productListBean.getData().get(0).getName());
                 }
             });
         }
         return false;
     });
    }

    private void initView() {
        searchFinish = findViewById(R.id.search_finish);
        searchLogo = findViewById(R.id.search_logo);
        etSearch = findViewById(R.id.et_search);
        deleteEliminate = findViewById(R.id.delete_eliminate);
        searchList = findViewById(R.id.search_list);
        search_delete=findViewById(R.id.search_delete);
        title = findViewById(R.id.title);
    }

}
