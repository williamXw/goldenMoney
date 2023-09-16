package com.loan.golden.cash.money.loan.ui.fragment.login

import android.os.Bundle
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.AesUtils
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.ChainBridgeParam
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
import com.loan.golden.cash.money.loan.databinding.FragmentLoginBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.LoginViewModel
import me.hgj.mvvmhelper.ext.setOnclickNoRepeat
import me.hgj.mvvmhelper.ext.showDialogMessage
import me.hgj.mvvmhelper.net.LoadStatusEntity
import me.hgj.mvvmhelper.util.LogUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject


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
        setOnclickNoRepeat(mBind.btnLogin, mBind.tvLoginOTP) { it ->
            when (it) {
                mBind.btnLogin -> {
                    login()
                }

                mBind.tvLoginOTP -> {
                    sendOTP()
                }
            }
        }
    }

    //发送验证码
    private fun sendOTP() {
        val phoneNum = mBind.etLoginPhoneNumber.text.toString().trim()
        if (phoneNum.isEmpty()) {
            RxToast.showToast("Enter your phone")
            return
        }
        val body = ChainBridgeParam(
            model = ChainBridgeParam.Model(
                phone = phoneNum,
                phoneCode = "+86"
            )
        )
        val strData = Gson().toJson(body)
        val paramsBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), AesUtils.encrypt(strData))
        LogUtils.debugInfo("-------------->>>>>>>$strData")
        mViewModel.chainBridgeCallBack(paramsBody)
    }


    //登录
    private fun login() {
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

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.loginResult.observe(viewLifecycleOwner) {

        }
        mViewModel.otpResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                RxToast.showToast(msg)
                LogUtils.debugInfo("---------response----->>>>>>>$mResponse")
            }
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

