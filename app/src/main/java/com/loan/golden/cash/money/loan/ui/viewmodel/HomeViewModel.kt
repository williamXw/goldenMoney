package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 13:59
 * @Describe    :
 */
class HomeViewModel : BaseViewModel() {

    /** 获取一个未完成的表单 */
    var aesculinAesirResult = MutableLiveData<Response>()
    fun aesculinAesirCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                aesculinAesirResult.value = UserRepository.aesculinAesir(body).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.AESCULAPIUS_AESCULIN_AESIR
        }
    }
}