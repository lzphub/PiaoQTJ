package cn.xunzi.pqtj

import cn.xunzi.basiclib.base.activity.BaseActivity
import cn.xunzi.basiclib.protocol.LoginProtocol
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
       return R.layout.activity_main
    }

    override fun initComponents() {
            ARouter.getInstance().build(LoginProtocol.USERSLOGIN)
                    .navigation()
                finish()
    }
}
