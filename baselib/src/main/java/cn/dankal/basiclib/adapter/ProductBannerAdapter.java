package cn.dankal.basiclib.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.dankal.basiclib.bean.UserHomeBannerBean;

public class ProductBannerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mImages; // 图片资源ID数组
    private List<ImageView> mImageViews; // ImageView集合
    public ProductBannerAdapter(Context mContext, List<String> mImages, List<ImageView> mImageViews) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mImageViews = mImageViews;
    }



    /**
     * 初始化ImageViews集合
     * @param
     */
    public int getCount() {
        return 10000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public float getPageWidth(int position) {
        return (float)1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        try {
            container.addView(mImageViews.get(position%mImageViews.size()));
        }catch (Exception e){

        }
        return mImageViews.get(position%mImageViews.size());
    }


}