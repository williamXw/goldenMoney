package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.param.ChainBridgeParam
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response

/**
 * 作者　: hxw
 * 时间　: 2023/9/3
 * 描述　:
 */
class LoginViewModel : BaseViewModel() {

    var loginResult = MutableLiveData<Response>()

    /**验证码登录*/
    fun loginCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                loginResult.value = UserRepository.login(body).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.LOGIN
        }
    }

    /**发送验证码（OTP）*/
    var otpResult = MutableLiveData<Response>()
    fun chainBridgeCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                otpResult.value = UserRepository.chainBridge(body).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.CHAINBRIDGE
        }
    }
}