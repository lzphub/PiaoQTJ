package cn.xunzi.basiclib.common.camera;

import android.net.Uri;

/**
 * Created by leaflc on 2018/2/7.
 */

public class CamerImageBean {

    private Uri mPath = null;

    private static final CamerImageBean INSTANCE = new CamerImageBean();

    public static CamerImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
