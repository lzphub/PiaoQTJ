package cn.xunzi.basiclib.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.xunzi.basiclib.bean.HomeBannerBean;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<HomeBannerBean.DataEntity.ListEntity> mImages; // 图片资源ID数组
    private List<ImageView> mImageViews; // ImageView集合
    public ViewPagerAdapter(Context mContext, List<HomeBannerBean.DataEntity.ListEntity> mImages, List<ImageView> mImageViews) {
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