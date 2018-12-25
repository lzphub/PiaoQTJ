package cn.dankal.basiclib.base.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.dankal.basiclib.R;
import cn.dankal.basiclib.R2;
import cn.dankal.basiclib.util.TitleBarUtils;
import cn.dankal.basiclib.util.image.PicUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static cn.dankal.basiclib.util.image.PicUtils.DEFAULT_AVATAR_ERRORHOLDER_RESID;
import static cn.dankal.basiclib.util.image.PicUtils.DEFAULT_AVATAR_PLACEHOLDER_RESID;


public class BigPhotoActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String URLS = "urls";
    public static final String PicIndex = "picindex";

    public static boolean hasJump = false;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    @BindView(R2.id.vp)
    ViewPager viewPager;

    @BindView(R2.id.tv_page_index)
    TextView tvPageIndex;
    int picIndex;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_photo;
    }

    @Override
    protected void initComponents() {
        ButterKnife.bind(this);
        TitleBarUtils.compat(this, getResources().getColor(R.color.color00));
        hasJump = true;

        //多张图片地址
        String[] urls = getIntent().getStringArrayExtra(URLS);
        picIndex=getIntent().getIntExtra(PicIndex,0);

        if (urls==null){
            urls=new String[1];
            //单张图片地址
            String stringExtra = getIntent().getStringExtra(URL);
            urls[0]= stringExtra;

            //点击网页
            Uri data=getIntent().getData();
            String imgUrl;
            if (data!=null) {
                imgUrl = data.getHost();
                urls[0]= imgUrl;
            }

        }
        if (urls.length>0) {
            initViewPager(urls);
        }
    }


    private ImageView loadImageVIew(String url) {
        if (!url.substring(0,1).equals("/")) {
            url = PicUtils.QINIU_DOMAIN + url;
        }

        PhotoView imageView =new PhotoView(this);

        progressBar.setVisibility(View.VISIBLE);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(DEFAULT_AVATAR_PLACEHOLDER_RESID);
        requestOptions.error(DEFAULT_AVATAR_ERRORHOLDER_RESID);
        Glide.with(this).asBitmap().apply(requestOptions).load(url).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target,
                                           DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
                photoViewAttacher.setOnViewTapListener((view, x, y) -> finish());
                return false;
            }
        }).into(imageView);
        return imageView;

    }

    private void initViewPager(String[] urls) {
        int length = urls.length;

        SparseArray<ImageView> views=new SparseArray<>();

        PagerAdapter pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = views.get(position);
                if (imageView ==null){
                    imageView = loadImageVIew(urls[position]);
                    views.append(position, imageView);
                }
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = views.get(position);
                if (view != null) {
                    views.removeAt(position);
                    container.removeView(view);
                }
            }
            public int getItemPosition(Object object) {
                return PagerAdapter.POSITION_NONE;
            }

        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setPageIndex(position,length);
            }
        });
        viewPager.setCurrentItem(picIndex);
        if (urls.length>1){
            setPageIndex(picIndex,length);
        }
    }

    private void setPageIndex(int position,int length) {
        tvPageIndex.setText((position+1)+"/"+length);
    }
    @Override
    protected void onDestroy() {
        hasJump = false;
        super.onDestroy();
    }
}
