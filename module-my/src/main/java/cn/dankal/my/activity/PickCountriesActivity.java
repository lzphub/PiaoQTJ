package cn.dankal.my.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.dankal.basiclib.adapter.SortAdapter;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.ContactSortModel;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.widget.SideBar;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.PICKCOUNTRIES;

/**
 * 选择国家
 */
@Route(path = PICKCOUNTRIES)
public class PickCountriesActivity extends BaseActivity {

    private android.widget.ListView lvContact;
    private cn.dankal.basiclib.widget.SideBar sidrbar;
    private List<ContactSortModel> SourceDateList;
    private SortAdapter adapter;
    private android.widget.ImageView backImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pick_countries;
    }

    @Override
    protected void initComponents() {
        initView();
        initEvents();
        setAdapter();
        backImg.setOnClickListener(v -> finish());
    }

    private void initView() {
        sidrbar = findViewById(R.id.sidrbar);
        backImg = findViewById(R.id.back_img);
        lvContact = findViewById(R.id.lv_contact);
    }

    private void initEvents() {
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                lvContact.setSelection(position);
            }
        });

        lvContact.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent();
            intent.putExtra("countries",((ContactSortModel) adapter.getItem(position)).getName());
            setResult(2,intent);
            finish();
        });
    }

    private void setAdapter() {
        SourceDateList = filledData(getResources().getStringArray(R.array.countrieslist));
        adapter = new SortAdapter(this, SourceDateList);
        lvContact.setAdapter(adapter);
    }
    private List<ContactSortModel> filledData(String[] date) {
        List<ContactSortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            ContactSortModel sortModel = new ContactSortModel();
            sortModel.setName(date[i]);
            String pinyin = date[i].substring(0,1);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sidrbar.setIndexText(indexString);
        return mSortList;
    }
}
