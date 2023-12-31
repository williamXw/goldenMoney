package com.loan.golden.cash.money.loan.ui.fragment.login

import android.os.Bundle
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.CacheUtil
import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.ChainBridgeParam
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
import com.loan.golden.cash.money.loan.data.response.LoginResponse
import com.loan.golden.cash.money.loan.databinding.FragmentLoginBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.activity.MainActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.LoginViewModel
import me.hgj.mvvmhelper.ext.finishActivity
import me.hgj.mvvmhelper.ext.setOnclickNoRepeat
import me.hgj.mvvmhelper.ext.showDialogMessage
import me.hgj.mvvmhelper.net.LoadStatusEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import rxhttp.internal.userAgent


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
        setOnclickNoRepeat(mBind.btnLogin, mBind.tvLoginOTP) {
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
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.chainBridgeCallBack(paramsBody)
    }


    //登录
    private fun login() {
        when {
            mBind.etLoginPhoneNumber.text.toString().trim().isEmpty() -> showDialogMessage("Enter your phone number")
            mBind.etLoginPhoneOtp.text.toString().trim().isEmpty() -> showDialogMessage("Enter OTP")
            else -> {
                val bodyLogin = LoginRegisterReq(
                    phone = mBind.etLoginPhoneNumber.text.toString().trim(),
                    phoneCode = "+86",
                    code = mBind.etLoginPhoneOtp.text.toString().trim(),
                )
                val strData = Gson().toJson(bodyLogin)
                val paramsBody =
                    AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                mViewModel.loginCallBack(paramsBody)
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.loginResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                val gson = Gson()
                val loginData: LoginResponse = gson.fromJson(mResponse, LoginResponse::class.java)
                if (loginData.status == 0) {
                    /** 保存用户信息 */
                    CacheUtil.setUser(loginData)
                    if (loginData.user != null) {
                        KvUtils.encode(Constant.TOKEN, loginData.user!!.token)
                        KvUtils.encode(Constant.USER_PHONE, loginData.user!!.phone)
                    }
                    startActivity<MainActivity>()
                    finishActivity(LoginActivity::class.java)
                } else {
                    val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                    RxToast.showToast(msg)
                }
            }
        }
        mViewModel.otpResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                val status = JSONObject(mResponse).getInt(Constant.STATUS)
                if (status == 0) {
                    RxToast.showToast("Successfully sent")
                } else {
                    val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                    RxToast.showToast(msg)
                }
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

