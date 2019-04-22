package cn.xunzi.basiclib.common.camera;

/**
 * Created by leaflc on 2018/6/23.
 */

public class RequestCodes {
    public static final int PICTURE_CUT = 3;//裁剪
    public static final int TAKE_PHOTO = 4;
    public static final int PICK_PHOTO = 5;
    public static final int TAKE_VODEO = 6;

    public static final int SCAN = 7;

    public static final int TAKE_PHOTO_IDCARD_FIRST = TAKE_PHOTO << 1;
    public static final int TAKE_PHOTO_IDCARD_SECOND = TAKE_PHOTO_IDCARD_FIRST << 1;

}
