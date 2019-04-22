package cn.xunzi.basiclib.util.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 目前仅支持圆形带边框、圆角矩形转换
 *
 * @author vane
 * @since 2018/8/6
 */

public class GlideTransform extends BitmapTransformation {

    /**
     * 边框宽度
     */
    private float mBorderWidth;
    /**
     * 边框颜色
     */
    private int mBorderColor;

    /**
     * 剪切圆形
     */
    public static final int TRANSFORM_TYPE_CIRCLE = 0;
    /**
     * 剪切圆角矩形
     */
    public static final int TRANSFORM_TYPE_ROUND = 1;
    /**
     * 圆角度数
     */
    private float mRadius = 0f;

    /**
     * 剪切类型
     */
    private int mTransformType = TRANSFORM_TYPE_CIRCLE;

    private Paint mBorderPaint;

    public GlideTransform setBorderWidth(float borderWidth) {
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        return this;
    }

    public GlideTransform setBorderColor(int borderColor) {
        mBorderColor = borderColor;
        return this;
    }

    public GlideTransform setTransformType(int transformType) {
        mTransformType = transformType;
        return this;
    }

    public GlideTransform setRoundRadius(float radius) {
        mRadius = Resources.getSystem().getDisplayMetrics().density * radius;
        return this;
    }

    public void init() {
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        if (mTransformType == TRANSFORM_TYPE_CIRCLE) {
            return circleCrop(pool, toTransform);
        } else if (mTransformType == TRANSFORM_TYPE_ROUND) {
            return roundCrop(pool, toTransform);
        }
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }

    /**
     * 剪切圆形
     */
    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    /**
     * 剪切圆角矩形
     */
    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        return result;
    }
}
