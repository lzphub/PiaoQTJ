package cn.xunzi.basiclib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.pojo.LocalAddressBean;
import cn.xunzi.basiclib.util.ToastUtils;
import cn.xunzi.basiclib.widget.wheelview.OnWheelChangedListener;
import cn.xunzi.basiclib.widget.wheelview.WheelView;
import cn.xunzi.basiclib.widget.wheelview.adapters.ArrayWheelAdapter;

/**
 * Class description : 地址选择器
 *
 * @author Vane
 * @since 17/10/24
 */

public class ChoiceAddressDialog extends Dialog implements OnWheelChangedListener, View.OnClickListener {

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    //所有省
    protected String[] mProvinceDatas;
    //所有市
    protected String[] mCityDatas;
    //所有区
    protected String[] mAreaDatas;

    //key - 省 value - 市
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    //key - 市 values - 区
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    // 当前省的名称
    protected String mCurrentProviceName;
    //当前市的名称
    protected String mCurrentCityName;
    //当前区的名称
    protected String mCurrentDistrictName = "";

    private String laststr = "";

    private Context mContext;

    private OnConfirmAddressListener onConfirmAddressListener;


    public ChoiceAddressDialog(@NonNull Context context,OnConfirmAddressListener confirmAddressListener) {
        this(context,  R.style.NormalDialogStyle);
        onConfirmAddressListener=confirmAddressListener;
    }

    public ChoiceAddressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_address, null);

        ButterKnife.bind(this, dialogView);

        configWindowStyle(dialogView);
        setUpViewsAndListener();
        parseProvinceDatas(null, null, null);
        setContentView(dialogView);
    }

    /**
     * 设置Dialog样式
     */
    private void configWindowStyle(View dialogView) {
        //获得dialog的window窗口
        Window window = this.getWindow();                //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);                //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();                 //获得window窗口的属性
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;                //设置窗口宽度为充满全屏
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;               //设置窗口高度为包裹内容
        window.setAttributes(lp); //将设置好的属性set回去
        setContentView(dialogView);
    }

    /**
     * 注册监听
     */
    private void setUpViewsAndListener() {
        mViewProvince = (WheelView) findViewById(R.id.wv_province);
        mViewCity = (WheelView) findViewById(R.id.wv_city);
        mViewDistrict = (WheelView) findViewById(R.id.wv_district);

        findViewById(R.id.tv_finish).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);

        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
    }

    /**
     * 解析省市区数据
     */
    protected void parseProvinceDatas(final String defaultProvice, final String defaultCity, final String defaultArea) {
        try {
            laststr = readAssetsFile("city_data.txt");
            final List<LocalAddressBean> provinceList = JSON.parseArray(laststr, LocalAddressBean.class);
            showData(provinceList, defaultProvice, defaultCity, defaultArea);
        } catch (Exception e) {
            ToastUtils.showShort("地址解析失败");
            e.printStackTrace();
        }
    }

    private void showData(List<LocalAddressBean> provinceList, final String defaultProvice,
                          final String defaultCity, final String defaultArea) {

        if (provinceList == null || provinceList.isEmpty()) {
            return;
        }

        //当遍历完省市区后用于显示带入地址
        int defProIndex = -1;
        int defCityIndex = -1;
        int defDistIndex = -1;


        // 初始化默认选中的省、市、区
        mCurrentProviceName = provinceList.get(0).text;//默认省名
        List<LocalAddressBean.AddressCity> firstCity = provinceList.get(0).children;
        if (firstCity != null && !firstCity.isEmpty()) {
            mCurrentCityName = firstCity.get(0).text;//默认市名
            List<LocalAddressBean.AddressCity.AddressArea> firstArea = firstCity.get(0).children;
            if (firstArea != null && !firstArea.isEmpty()) {
                mCurrentDistrictName = firstArea.get(0).text;//默认区名
            }
        }

        //String[] 省
        mProvinceDatas = new String[provinceList.size()];
        for (int i = 0; i < provinceList.size(); i++) {
            // 遍历所有省的数据
            mProvinceDatas[i] = provinceList.get(i).text;
            if (defaultProvice != null && defaultProvice.equals(provinceList.get(i).text)) {
                defProIndex = i;
            }

            //String[] 市
            List<LocalAddressBean.AddressCity> cityList = provinceList.get(i).children;
            mCityDatas = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                // 遍历省下面的所有市的数据
                mCityDatas[j] = cityList.get(j).text;
                if (defaultCity != null && defaultProvice != null && defaultCity.equals(cityList.get(j).text) &&
                        defaultProvice.equals(provinceList.get(i).text)) {
                    defCityIndex = j;
                }

                //String[] 区
                List<LocalAddressBean.AddressCity.AddressArea> districtList = cityList.get(j).children;
                mAreaDatas = new String[districtList != null ? districtList.size() : 1];
                if (districtList != null) {
                    for (int k = 0; k < districtList.size(); k++) {
                        mAreaDatas[k] = districtList.get(k).text;
                        if (defaultCity != null && defaultProvice != null && defaultArea != null &&
                                defaultProvice.equals(provinceList.get(i).text) &&
                                defaultCity.equals(cityList.get(j).text) &&
                                defaultArea.equals(districtList.get(k).text)) {
                            defDistIndex = k;
                        }

                    }
                } else {
                    mAreaDatas[0] = "";
                }
                // 市-区/县的数据，保存到mDistrictDatasMap
                mDistrictDatasMap.put(mCityDatas[j], mAreaDatas);
            }
            // 省-市的数据，保存到mCitisDatasMap
            mCitisDatasMap.put(provinceList.get(i).text, mCityDatas);
        }

        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);

        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(mContext, mProvinceDatas));

        /**
         * 如果有地址带入，则带入地址
         * 没有则默认第一个
         */
        mViewProvince.setCurrentItem(defProIndex != -1 ? defProIndex : 0);
        mCurrentProviceName = mProvinceDatas[defaultProvice != null ? defProIndex : 0];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(mContext, cities));
        mViewCity.setCurrentItem(defCityIndex != -1 ? defCityIndex : 0);
        mCurrentCityName = (mCitisDatasMap.get(mCurrentProviceName))[defCityIndex != -1 ? defCityIndex : 0];

        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null || areas.length <= 0) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(mContext, areas));
        mViewDistrict.setCurrentItem(defDistIndex != -1 ? defDistIndex : 0);
        mCurrentDistrictName = (mDistrictDatasMap.get(mCurrentCityName))[defDistIndex != -1 ? defDistIndex : 0];
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        try {
            int pCurrent = mViewCity.getCurrentItem();
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
            String[] areas = mDistrictDatasMap.get(mCurrentCityName);

            if (areas == null || areas.length <= 0) {
                areas = new String[]{""};
                mCurrentDistrictName = "";
            } else {
                mCurrentDistrictName = areas[mViewDistrict.getCurrentItem()];
            }
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(mContext, areas));
            mViewDistrict.setCurrentItem(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        try {
            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProviceName = mProvinceDatas[pCurrent];
            String[] cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
            mViewCity.setViewAdapter(new ArrayWheelAdapter<>(mContext, cities));
            mViewCity.setCurrentItem(0);
            updateAreas();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将Assets文件转化为字符串
     */
    public String readAssetsFile(String path) {
        String str = null;
        try {
            InputStream is = mContext.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            str = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        try {
            if (wheel == mViewProvince) {
                updateCities();
            } else if (wheel == mViewCity) {
                updateAreas();
            } else if (wheel == mViewDistrict) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i ==  R.id.tv_finish) {
            dismiss();
            if (onConfirmAddressListener != null) {
                onConfirmAddressListener.onConfirm(mCurrentProviceName, mCurrentCityName, mCurrentDistrictName);
            }

        } else if (i == R.id.tv_cancel) {
            dismiss();
        } else {
        }
    }

    public interface OnConfirmAddressListener {
        void onConfirm(String curProvice, String curCity, String curArea);
    }

    public void setOnConfirmAddressListener(OnConfirmAddressListener onConfirmAddressListener) {
        this.onConfirmAddressListener = onConfirmAddressListener;
    }
}
