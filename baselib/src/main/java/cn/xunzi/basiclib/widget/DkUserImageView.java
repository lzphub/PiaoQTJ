package cn.xunzi.basiclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by luhaibing
 * <p>
 * Date: 2017-07-21.
 * Time: 10:10
 * <p>
 * className: DkUserImageView
 * classDescription: 用户头像
 */
@SuppressLint("AppCompatCustomView")
public class DkUserImageView extends ImageView implements View.OnClickListener {
    private String uuid;

    public DkUserImageView(Context context) {
        super(context,null);

    }

    public DkUserImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DkUserImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setOnClickListener(this);
    }

    @Override
//    @CheckLogin
    public void onClick(View v) {
/*        if (UserManager.getUserInfo().getUuid().equals(uuid)){
            return;
        }
        ARouter.getInstance().build(ACTIVITY_ADDFIREND)
                .withString(UUID, uuid)
                .navigation();*/
    }

    public void bindData(String uuid) {
        this.uuid=uuid;
    }
}
