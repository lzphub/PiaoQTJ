package cn.dankal.basiclib.util.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.File;

import cn.dankal.basiclib.GlideApp;
import cn.dankal.basiclib.GlideRequest;
import cn.dankal.basiclib.R;

/**
 * @author vane
 * @since 2018/8/3
 */

public class PicUtils {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    //private static final String QINIU_DEBUG_DOMAIN = "http://phpzt6pty.bkt.clouddn.com/";
    private static final String QINIU_DEBUG_DOMAIN = "http://phpzt6pty.bkt.clouddn.com/";
    public static final String QINIU_DOMAIN = QINIU_DEBUG_DOMAIN;

    /**
     * 默认边宽
     */
    public static final float DEFAULT_BORDER_WIDTH = 1;
    /**
     * 默认边的颜色
     */
    public static final int DEFAULT_BORDER_COLOR = Color.WHITE;
    /**
     * 默认圆角角度
     */
    public static final float DEFAULT_ROUND_RADIUS = 2;

    /**
     * 默认通用的加载前占位图
     */
    public static final int DEFAULT_NORMAL_PLACEHOLDER_RESID = R.mipmap.pic_photo_holder;
    /**
     * 默认通用的加载失败占位图
     */
    public static final int DEFAULT_NORMAL_ERRORHOLDER_RESID = R.mipmap.pic_photo_holder;

    /**
     * 默认头像加载前占位图
     */
    public static final int DEFAULT_AVATAR_PLACEHOLDER_RESID = R.mipmap.pic_head_holder;
    /**
     * 默认头像加载失败占位图
     */
    public static final int DEFAULT_AVATAR_ERRORHOLDER_RESID = R.mipmap.pic_head_holder;

    /**
     * 加载头像(默认占位图、边宽颜色)
     */
    public static void loadAvatar(String s,@NonNull ImageView iv) {
        loadCirclePic(iv.getContext(), s, iv, DEFAULT_BORDER_WIDTH, DEFAULT_BORDER_COLOR,
                DEFAULT_AVATAR_PLACEHOLDER_RESID, DEFAULT_AVATAR_ERRORHOLDER_RESID);
    }

    /**
     * 加载头像
     */
    public static void loadAvatar(Context context, Object o, ImageView iv,
                                  float borderWidth, int borderColor) {
        loadCirclePic(context, o, iv, borderWidth, borderColor,
                DEFAULT_AVATAR_PLACEHOLDER_RESID, DEFAULT_AVATAR_ERRORHOLDER_RESID);
    }


    /**
     * 加载普通图片(默认占位图)
     */
    public static void loadNormal(String s,@NonNull ImageView iv) {
        load(iv.getContext(), s, iv, -1, -1, -1, -1,
                DEFAULT_NORMAL_PLACEHOLDER_RESID, DEFAULT_NORMAL_ERRORHOLDER_RESID);
    }

    /**
     * 加载普通图片
     */
    public static void loadNormal(Context context, Object o, ImageView iv,
                                  int placeHolder, int errorHolder) {
        load(context, o, iv, -1, -1, -1, -1, placeHolder, errorHolder);
    }

    /**
     * 加载裁剪圆形图片(默认占位图)
     */
    public static void loadCirclePic(Context context, Object o, ImageView iv) {
        load(context, o, iv, GlideTransform.TRANSFORM_TYPE_CIRCLE, DEFAULT_BORDER_WIDTH, DEFAULT_BORDER_COLOR,
                -1, DEFAULT_NORMAL_PLACEHOLDER_RESID, DEFAULT_NORMAL_ERRORHOLDER_RESID);
    }

    /**
     * 加载裁剪圆形图片
     */
    public static void loadCirclePic(Context context, Object o, ImageView iv, float borderWidth,
                                     int borderColor, int placeHolder, int errorHolder) {
        load(context, o, iv, GlideTransform.TRANSFORM_TYPE_CIRCLE, borderWidth, borderColor,
                -1, placeHolder, errorHolder);
    }

    /**
     * 加载裁剪圆角矩形图片(默认占位图)
     */
    public static void loadRoundPic(Context context, Object o, ImageView imageView) {
        loadRoundPic(context, o, imageView, DEFAULT_ROUND_RADIUS,
                DEFAULT_NORMAL_PLACEHOLDER_RESID, DEFAULT_NORMAL_ERRORHOLDER_RESID);
    }

    /**
     * 加载裁剪圆角矩形图片
     */
    public static void loadRoundPic(Context context, Object o, ImageView iv, float radius,
                                    int placeHolder, int errorHolder) {
        load(context, o, iv, GlideTransform.TRANSFORM_TYPE_ROUND, -1, -1,
                radius, placeHolder, errorHolder);
    }


    /**
     * 加载图片
     *
     * @param context     上下文
     * @param o           资源对象
     * @param iv          target
     * @param type        GlideTransform的类型、目前仅支持圆形和圆角矩形
     * @param borderWidth 边宽、目前仅支持圆形裁剪下显示
     * @param borderColor 边颜色、目前仅支持圆形裁剪下显示
     * @param radius      角度、目前仅支持圆角矩形裁剪下显示
     * @param placeHolder 加载前占位图资源
     * @param errorHolder 加载错误占位图资源
     */
    @SuppressLint("CheckResult")
    public static void load(Context context, Object o, ImageView iv, int type, float borderWidth,
                            int borderColor, float radius, int placeHolder, int errorHolder) {
        GlideTransform transform = new GlideTransform();
        if (type != -1) {
            transform.setTransformType(type);
            if (borderWidth != -1) transform.setBorderWidth(borderWidth);
            if (borderColor != -1) transform.setBorderColor(borderColor);
            if (radius != -1) transform.setRoundRadius(radius);
            transform.init();
        }

        GlideRequest<Drawable> request;

        if (o instanceof Uri) {
            request = GlideApp.with(context)
                    .load((Uri) o);
        } else if (o instanceof String) {
            request = GlideApp.with(context)
                    .load(getUrl((String) o));
        } else if (o instanceof File) {
            request = GlideApp.with(context)
                    .load((File) o);
        } else if (o instanceof Integer) {
            request = GlideApp.with(context)
                    .load((Integer) o);
        } else {
            request = GlideApp.with(context)
                    .load(o);
        }

        if (placeHolder != 0) request = request.placeholder(placeHolder);
        if (errorHolder != 0) request = request.error(errorHolder);
        if (type != -1) request = request.transform(transform);
        request.into(iv);
    }

    /**
     * 处理路径方法
     * <p>
     * 若路径为全路径则直接返回，否则拼接七牛域名
     *
     * @param imgPath 原路径
     * @return 处理过的路径
     */
    public static String getUrl(String imgPath) {
        if (TextUtils.isEmpty(imgPath)) return "";
        if (imgPath.startsWith(HTTP_PREFIX) || imgPath.startsWith(HTTPS_PREFIX)) {
            return imgPath;
        }
        return QINIU_DOMAIN + imgPath;
    }

}
