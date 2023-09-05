package com.loan.golden.cash.money.loan.ui.fragment.login

import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
import com.loan.golden.cash.money.loan.databinding.FragmentLoginBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.LoginViewModel
import me.hgj.mvvmhelper.ext.setOnclickNoRepeat
import me.hgj.mvvmhelper.ext.showDialogMessage
import me.hgj.mvvmhelper.net.LoadStatusEntity
import me.hgj.mvvmhelper.net.interception.logging.util.LogUtils

/**
 * created by : huxiaowei
 * @date : 2022/09/13
 * Describe :
 */
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).keyboardEnable(true).init()
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.btnLogin) { it ->
            when (it) {
                mBind.btnLogin -> {
                    when {
                        mBind.etLoginPhoneNumber.text.toString().trim().isEmpty() -> showDialogMessage("Enter your phone number")
                        mBind.etLoginPhoneOtp.text.toString().trim().isEmpty() -> showDialogMessage("Enter OTP")
                        else -> {
                            val bodyLogin = LoginRegisterReq(
                                name = mBind.etLoginPhoneNumber.text.toString().trim(),
                                password = mBind.etLoginPhoneOtp.text.toString().trim(),
                                deviceId = "22d0a4f1d71b4b06bbeea8b418b1cdc",
                            )
                            mViewModel.loginCallBack(bodyLogin)
                        }
                    }
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.loginResult.observe(this) {
            LogUtils.debugInfo("sdsdsdsdsd")
        }
    }

    override fun onRequestError(loadStatus: LoadStatusEntity) {
        when (loadStatus.requestCode) {
            NetUrl.LOGIN -> {
                showDialogMessage(loadStatus.errorMessage)
            }
        }
    }

}