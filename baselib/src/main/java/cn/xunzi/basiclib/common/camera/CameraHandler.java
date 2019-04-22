package cn.xunzi.basiclib.common.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

import cn.xunzi.basiclib.R;
import cn.xunzi.basiclib.util.FileUtil;
import cn.xunzi.basiclib.util.SharedPreferencesUtils;


/**
 * Created by leaflc on 2018/2/7.
 */

public class CameraHandler implements View.OnClickListener {
    private final AlertDialog DIALOG;
    private Context context;
    private Activity activity;

    private String type;

    public static final int TAKE_PHOTO = 4;
    public static final int PICK_PHOTO = 5;

    public CameraHandler(Activity activity) {
        this.context = activity;
        this.activity = activity;
        DIALOG = new AlertDialog.Builder(context).create();
    }

    public Activity getActivity() {
        return activity;
    }

    public void beginCameraDialog() {
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        type = SharedPreferencesUtils.getString(getActivity(), "identity", "user");
        if (window != null) {
            window.setContentView(R.layout.dialog_photo_picker);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            //弹出一个窗口，让背后的窗口变暗一点
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            //dialog背景层
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            AppCompatButton appCompatButton = window.findViewById(R.id.photodialog_btn_cancel);
            AppCompatButton appCompatButton1 = window.findViewById(R.id.photodialog_btn_take);
            AppCompatButton appCompatButton2 = window.findViewById(R.id.photodialog_btn_native);

            if (type.equals("user")) {
                appCompatButton1.setText("PHOTOGRAPH");
                appCompatButton2.setText("SELECT FROM ALBUM");
                appCompatButton.setText("CANCEL");
            }

            appCompatButton.setOnClickListener(this);
            appCompatButton1.setOnClickListener(this);
            appCompatButton2.setOnClickListener(this);
        }
    }

    public int takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);

        /**
         * 兼容7.0以上
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = context.getContentResolver().
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            final File realFile = getFileByPath(FileUtil.getRealFilePath(context, uri));
            final Uri realUri = Uri.fromFile(realFile);
            CamerImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CamerImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        activity.startActivityForResult(intent, TAKE_PHOTO);
        return RequestCodes.TAKE_PHOTO;
    }

    public int pickPhoto() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片获取方式"), PICK_PHOTO);
        return RequestCodes.PICK_PHOTO;
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.photodialog_btn_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (i == R.id.photodialog_btn_native) {
            pickPhoto();
            DIALOG.cancel();

        } else if (i == R.id.photodialog_btn_cancel) {
            DIALOG.cancel();
        }
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    private File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
