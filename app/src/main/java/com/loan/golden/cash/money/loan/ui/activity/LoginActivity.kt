package com.loan.golden.cash.money.loan.ui.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.loan.golden.cash.money.loan.app.base.BaseActivity
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.databinding.ActivityLoginBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.LoginViewModel

/**
 * 作者　: hxw
 * 时间　: 2020/11/18
 * 描述　:
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    var exitTime = 0L

    override fun initView(savedInstanceState: Bundle?) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    RxToast.showToast("再按一次退出程序")
                    exitTime = System.currentTimeMillis()
                } else {
                    finish()
                }
            }
        })
    }

}