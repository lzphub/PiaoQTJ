package cn.xunzi.basiclib.banner;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.xunzi.basiclib.R;

public class CustomBanner extends RelativeLayout {
    private ViewPager banner;
    private RadioGroup intener, intener_bottom_left, intener_bottom, intener_bottom_right;
    private RelativeLayout banner_rl;

    private List<Integer> imageList = new ArrayList<>();
    private List<ImageView> imageViews;
    private List<RadioButton> radioButtons;
    private ViewPagerAdapter mAdapter;

    private int pagerMargin;
    private boolean isGaller;
    private Context context;
    private int count = 10;
    private int speed2 = 1000;
    private CountDownTimer downTimer;
    private RadioButton fristRdButton;

    public CustomBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        context = context;
        LayoutInflater.from(context).inflate(R.layout.banner_layout, this, true);
        banner = findViewById(R.id.viewpager);
        intener = findViewById(R.id.radiogroup);
        banner_rl = findViewById(R.id.banner_rl);
        intener_bottom_left = findViewById(R.id.radioGroup_lower_left);
        intener_bottom = findViewById(R.id.radioGroup_lower);
        intener_bottom_right = findViewById(R.id.radioGroup_lower_right);
    }

    //是否为画廊式
    public void setGaller(boolean isGaller2) {
        isGaller = isGaller2;
        if (isGaller2) {
            banner.setPageTransformer(true, new CardTransformer());
            banner.setClipChildren(!isGaller2);
            banner_rl.setClickable(false);

        } else {
            banner.setPageMargin(0);
            banner.setClipChildren(true);
            banner_rl.setClickable(true);
            ViewGroup.LayoutParams layoutParams = banner.getLayoutParams();
            layoutParams.width = LayoutParams.MATCH_PARENT;
            banner.setLayoutParams(layoutParams);
        }
    }

    //设置图片数据
    public void setImageList(Context context1, final List<Integer> imageList2) {
        imageList = imageList2;

        imageViews = new ArrayList<>();
        for (int i = 0; i < 200 * imageList.size(); i++) {
            ImageView imageView = new ImageView(context1);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context1).load(imageList.get(i % imageList.size())).into(imageView);
            imageViews.add(imageView);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(finalI % imageList2.size());
                    }
                }
            });
        }

//        mAdapter = new ViewPagerAdapter(context1, imageList, imageViews);
        banner.setOffscreenPageLimit(2);
        if (imageList.size() > 0) {
            banner.setAdapter(mAdapter);
        }
        banner.setCurrentItem(10);
    }

    //设置页边距
    public void setPagerMargin(int margin) {
        pagerMargin = margin;
        banner.setPageMargin(margin);
    }

    //是否自动轮播以及设置轮播间隔
    public void isAutoPlay(boolean isAuto, Long time) {
        if (isAuto) {
            downTimer = new CountDownTimer(100000000, time) {
                @Override
                public void onTick(long millisUntilFinished) {
                    banner.setCurrentItem(count, true);
                    count++;
                }

                @Override
                public void onFinish() {
                    downTimer.start();
                }
            };

            downTimer.start();
        }
    }

    //设置切换动画速度
    public void setSpeed(int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(banner.getContext(), new AccelerateInterpolator());
            field.set(banner, scroller);
            scroller.setmDuration(speed);
        } catch (Exception e) {

        }
    }

    /**
     * @param context
     * @param drawable
     * @param style
     * 设置小圆点
     * 0在轮播图的外部下方
     * 1在轮播图内部的左下
     * 2在轮播图内部的下方
     * 3在轮播图内部的右下
     */

    public void setDot(Context context, int drawable, int style) {
        radioButtons = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setButtonDrawable(android.R.color.transparent);            // 设置按钮的样式
            radioButton.setBackgroundResource(drawable);  // 设置RadioButton的背景图片
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            layoutParams.setMargins(100, 0, 0, 0);
            layoutParams.leftMargin = 20;
            radioButton.setLayoutParams(layoutParams);
            radioButton.setHeight(30);
            radioButton.setWidth(30);
            radioButton.setPadding(10,10,10,10);
            radioButton.setEnabled(false);

            if (i == 0) {
                fristRdButton = radioButton;
                radioButtons.add(fristRdButton);
            }
            
            switch (style) {
                case 0:
                    intener.addView(radioButton, layoutParams);
                    break;
                case 1:
                    intener_bottom_left.addView(radioButton, layoutParams);
                    break;
                case 2:
                    intener_bottom.addView(radioButton, layoutParams);
                    break;
                case 3:
                    intener_bottom_right.addView(radioButton, layoutParams);
                    break;
            }
            if (i != 0) {
                radioButtons.add(radioButton);
            }

            if (fristRdButton != null) {
                fristRdButton.setChecked(true);
            }
        }

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                count=i%radioButtons.size();
                radioButtons.get(i % radioButtons.size()).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
