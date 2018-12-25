package cn.dankal.basiclib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.dankal.basiclib.bean.UserHomeBannerBean;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.image.PicUtils;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<UserHomeBannerBean.CarouselsBean> mImages; // 图片资源ID数组
    private List<ImageView> mImageViews; // ImageView集合
    public ViewPagerAdapter(Context mContext, List<UserHomeBannerBean.CarouselsBean> mImages, List<ImageView> mImageViews) {
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