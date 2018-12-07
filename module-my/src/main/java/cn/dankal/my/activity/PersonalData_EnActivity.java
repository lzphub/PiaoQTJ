package cn.dankal.my.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import api.MyServiceFactory;
import cn.dankal.basiclib.base.activity.BaseActivity;
import cn.dankal.basiclib.bean.PersonalData_EnBean;
import cn.dankal.basiclib.common.qiniu.QiniuUpload;
import cn.dankal.basiclib.common.qiniu.UploadHelper;
import cn.dankal.basiclib.protocol.MyProtocol;
import cn.dankal.basiclib.rx.AbstractDialogSubscriber;
import cn.dankal.basiclib.util.Logger;
import cn.dankal.basiclib.util.ToastUtils;
import cn.dankal.basiclib.util.UriUtils;
import cn.dankal.basiclib.util.image.AvatarUtil;
import cn.dankal.basiclib.util.image.PicUtils;
import cn.dankal.basiclib.widget.CircleImageView;
import cn.dankal.basiclib.widget.TipDialog;
import cn.dankal.setting.R;

import static cn.dankal.basiclib.protocol.MyProtocol.PERSONALDATAEN;
import static cn.dankal.basiclib.widget.TipDialog.Builder.ICON_TYPE_FAIL;

/**
 * 用户端个人信息
 */
@Route(path = PERSONALDATAEN)
public class PersonalData_EnActivity extends BaseActivity {

    private android.widget.ImageView backImg;
    private android.widget.RelativeLayout picRl;
    private CircleImageView headPic;
    private android.widget.RelativeLayout nameRl;
    private android.widget.TextView nameText;
    private android.widget.RelativeLayout ageRl;
    private android.widget.TextView ageText;
    private android.widget.RelativeLayout contactRl;
    private android.widget.TextView contactText;
    private android.widget.RelativeLayout eMailRl;
    private android.widget.TextView eMailText;
    private android.widget.RelativeLayout companyRl;
    private android.widget.TextView companyText;
    private android.widget.RelativeLayout countryRl;
    private android.widget.TextView countryText;
    private android.widget.RelativeLayout positionRl;
    private android.widget.TextView positionText;
    private String[] positions = {"IMPORTER", "WHOLESALERS", "RETAILERS", "DESIGNER", "PERSONAL", "OTHER"};
    private List<String> positionList = new ArrayList<>();
    private int ageitemcount = 0, positioncount = 0;
    private PersonalData_EnBean personalDataEnBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data__en;
    }

    @Override
    protected void initComponents() {
        initView();

        getData();

        backImg.setOnClickListener(v -> finish());
        ageRl.setOnClickListener(v -> initAgeDialog());
        picRl.setOnClickListener(v -> initBottomDialog());
        positionRl.setOnClickListener(v -> initPositionDialog());
        countryRl.setOnClickListener(v -> startActivityForResult(new Intent(PersonalData_EnActivity.this, PickCountriesActivity.class), 2));
        nameRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data", "name").withSerializable("bean", personalDataEnBean).navigation());
        contactRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data", "number").withSerializable("bean", personalDataEnBean).navigation());
        eMailRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data", "email").withSerializable("bean", personalDataEnBean).navigation());
        companyRl.setOnClickListener(v -> ARouter.getInstance().build(MyProtocol.EDITDATAEN).withString("data", "company").withSerializable("bean", personalDataEnBean).navigation());

    }

    private void initView() {
        ageRl = findViewById(R.id.age_rl);
        picRl = findViewById(R.id.pic_rl);
        nameRl = findViewById(R.id.name_rl);
        ageText = findViewById(R.id.age_text);
        backImg = findViewById(R.id.back_img);
        headPic = findViewById(R.id.head_pic);
        eMailRl = findViewById(R.id.e_mail_rl);
        nameText = findViewById(R.id.name_text);
        contactRl = findViewById(R.id.contact_rl);
        companyRl = findViewById(R.id.company_rl);
        countryRl = findViewById(R.id.country_rl);
        eMailText = findViewById(R.id.e_mail_text);
        positionRl = findViewById(R.id.position_rl);
        contactText = findViewById(R.id.contact_text);
        companyText = findViewById(R.id.company_text);
        countryText = findViewById(R.id.country_text);
        positionText = findViewById(R.id.position_text);
    }

    private void initBottomDialog() {
        AvatarUtil avatarUtil = new AvatarUtil(this);
        avatarUtil.beginCameraDialog(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AvatarUtil.REQUEST_CODE_ALBUM://相册存储权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AvatarUtil.openAlbum(this);
                } else {
                    Toast.makeText(this, "PERMISSION TO SELECT GALLERY IS REQUIRED", Toast.LENGTH_LONG).show();
                }
                break;
            case AvatarUtil.REQUEST_CODE_CAMERA://相机拍照权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许
                    AvatarUtil.openCamera(this);
                } else {//拒绝
                    Toast.makeText(this, "YOU CAN ONLY USE THE PHOTO FEATURE IF YOU AGREE TO CAMERA PERMISSIONS", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //正确返回
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case AvatarUtil.TAKE_PHOTO://相机返回
                        //相机返回图片，调用裁剪的方法
                        AvatarUtil.startUCrop(PersonalData_EnActivity.this, AvatarUtil.imageUri, "CUTTING HEAD");
                        break;
                    case AvatarUtil.CHOOSE_PHOTO://相册返回
                        try {
                            if (data != null) {
                                Uri uri = data.getData();
                                //相册返回图片，调用裁剪的方法
                                AvatarUtil.startUCrop(PersonalData_EnActivity.this, uri, "CUTTING HEAD");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "IMAGE SELECTION FAILED", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case UCrop.REQUEST_CROP://剪切返回
                        Uri resultUri = UCrop.getOutput(data);
                        uploadPic(resultUri, personalDataEnBean);
                        break;
                }
            } else {
                Toast.makeText(this, "IMAGE SELECTION FAILED", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == 2) {
            if (requestCode == 2) {
                countryText.setText(data.getStringExtra("countries"));
                personalDataEnBean.setCountry(data.getStringExtra("countries"));
                updata();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    private void initPositionDialog() {
        if (positionList != null) {
            positionList.clear();
        }
        for (int i = 0; i < positions.length; i++) {
            positionList.add(positions[i]);
        }
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.dialog_pick_position);
        sheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        sheetDialog.show();
        TextView cancel = sheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> sheetDialog.dismiss());
        WheelPicker position = sheetDialog.getDelegate().findViewById(R.id.postion_wheel);
        position.setData(positionList);
        TextView save = sheetDialog.getDelegate().findViewById(R.id.tv_save);
        position.setSelectedItemPosition(positioncount);
        position.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                positioncount = position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
        save.setOnClickListener(v -> {
            positionText.setText(positionList.get(positioncount));
            personalDataEnBean.setPosition(positions[positioncount]);
            updata();
            sheetDialog.dismiss();
        });
    }

    //上传数据
    private void updata() {
        MyServiceFactory.updateInfo(personalDataEnBean).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                getData();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Logger.d(e + "");
            }
        });
    }

    private void initAgeDialog() {
        if (positionList != null) {
            positionList.clear();
        }
        for (int i = 1; i < 100; i++) {
            positionList.add(i + "");
        }
        BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
        sheetDialog.setContentView(R.layout.dialog_pick_position);
        sheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        sheetDialog.show();
        TextView title = sheetDialog.getDelegate().findViewById(R.id.dialog_title);
        title.setText("AGE");
        TextView cancel = sheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> sheetDialog.dismiss());
        WheelPicker position = sheetDialog.getDelegate().findViewById(R.id.postion_wheel);
        position.setData(positionList);
        TextView save = sheetDialog.getDelegate().findViewById(R.id.tv_save);
        position.setSelectedItemPosition(ageitemcount);
        position.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int offset) {
            }

            @Override
            public void onWheelSelected(int position) {
                ageitemcount = position;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });
        save.setOnClickListener(v -> {
            ageText.setText(positionList.get(ageitemcount));
            personalDataEnBean.setAge(Integer.valueOf(positionList.get(ageitemcount)));
            updata();
            sheetDialog.dismiss();
        });
    }

    //获取信息
    private void getData() {
        MyServiceFactory.getUserData().safeSubscribe(new AbstractDialogSubscriber<PersonalData_EnBean>(this) {
            @Override
            public void onNext(PersonalData_EnBean personalData_enBean) {
                PicUtils.loadAvatar(PicUtils.getUrl(personalData_enBean.getAvatar()), headPic);
                nameText.setText(personalData_enBean.getName());
                ageText.setText(personalData_enBean.getAge() + "");
                contactText.setText(personalData_enBean.getContact());
                eMailText.setText(personalData_enBean.getEmail());
                companyText.setText(personalData_enBean.getCompany());
                countryText.setText(personalData_enBean.getCountry());
                positionText.setText(personalData_enBean.getPosition());

                personalDataEnBean = personalData_enBean;

                ageitemcount = personalData_enBean.getAge();

                for (int i = 0; i < positions.length; i++) {
                    if (personalData_enBean.getPosition().equals(positions[i])) {
                        positioncount = i;
                        return;
                    }
                }
            }
        });
    }

    private void uploadPic(Uri photoUris, PersonalData_EnBean personalData_enBean) {
        final File tempFile = new File(photoUris.getPath());

        TipDialog.Builder builder = new TipDialog.Builder(this);
        loadingDialog = builder.setIconType(TipDialog.Builder.ICON_TYPE_LOADING).setTipWord("UpLoading").create();
        loadingDialog.show();

        boolean b = UriUtils.getPath(this, photoUris) == null;

        new UploadHelper().uploadQiniuPic(new QiniuUpload.UploadListener() {
            @Override
            public void onSucess(String localPath, String key) {
                loadingDialog.dismiss();
                personalData_enBean.setAvatar(key);
                setAvatar(personalData_enBean);
                File deletefile = new File(localPath);
                getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{localPath});//删除系统缩略图
                deletefile.delete();
            }

            @Override
            public void onUpload(double percent) {
                DecimalFormat df = new DecimalFormat("#0.00");
                builder.setTipWord(df.format(percent * 100) + "%").showProgress();
            }

            @Override
            public void onError(String string) {
                ToastUtils.showLong(string);
                loadingDialog.dismiss();
                TipDialog dialog = builder.setIconType(ICON_TYPE_FAIL).setTipWord("上传失败").create(2000);
                dialog.show();
                dialog.dismiss();
            }
        }, b ? photoUris.getPath() : UriUtils.getPath(this, photoUris));
    }

    private void setAvatar(PersonalData_EnBean personalData_enBean) {
        MyServiceFactory.updateInfo(personalData_enBean).safeSubscribe(new AbstractDialogSubscriber<String>(this) {
            @Override
            public void onNext(String s) {
                PicUtils.loadAvatar(personalData_enBean.getAvatar(), headPic);
                ToastUtils.showShort("Uploaded successfully");
            }
        });
    }
}
