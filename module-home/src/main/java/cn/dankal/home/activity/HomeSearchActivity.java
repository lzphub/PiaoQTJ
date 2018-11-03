package cn.dankal.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.activity.BaseActivity;

import static cn.dankal.basiclib.protocol.HomeProtocol.HOMESEARCH;

@Route(path = HOMESEARCH)
public class HomeSearchActivity extends BaseActivity {

    private android.widget.ImageView searchFinish;
    private android.widget.ImageView searchLogo;
    private android.widget.EditText etSearch;
    private android.widget.ImageView deleteEliminate;
    private android.support.v7.widget.RecyclerView searchList;
    private ImageView search_delete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initComponents() {
        initView();
        searchFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             searchLogo.setVisibility(View.GONE);
             search_delete.setVisibility(View.VISIBLE);
         }
     });
     search_delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             etSearch.setText("");
         }
     });
    }

    private void initView() {
        searchFinish = (ImageView) findViewById(R.id.search_finish);
        searchLogo = (ImageView) findViewById(R.id.search_logo);
        etSearch = (EditText) findViewById(R.id.et_search);
        deleteEliminate = (ImageView) findViewById(R.id.delete_eliminate);
        searchList = (RecyclerView) findViewById(R.id.search_list);
        search_delete=findViewById(R.id.search_delete);
    }

}
