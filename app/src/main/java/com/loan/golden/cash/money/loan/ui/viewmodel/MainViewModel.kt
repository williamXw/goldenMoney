package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.Response

/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe :
 */
class MainViewModel : BaseViewModel() {

    /** 提交用户信息 */
    var nappyResult = MutableLiveData<Response>()
    fun nappyNapraPath(): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                nappyResult.value = UserRepository.nappyNaprapath().await()
            }
            loadingType = LoadingType.LOADING_NULL
            loadingMessage = "loading....."
            requestCode = NetUrl.NAPPER_NAPPY_NAPRAPATH
        }
    }
}