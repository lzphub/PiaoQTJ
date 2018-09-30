package cn.dankal.templates

import cn.dankal.basiclib.base.activity.BaseActivity
import cn.dankal.basiclib.protocol.LoginProtocol
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
       return R.layout.activity_main
    }

    override fun initComponents() {
        tv_login.setOnClickListener {
            ARouter.getInstance().build(LoginProtocol.LOGIN)
                    .navigation()
        }
    }
}
