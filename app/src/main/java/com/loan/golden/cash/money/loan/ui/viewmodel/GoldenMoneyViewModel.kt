package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.ChippieResponse
import com.loan.golden.cash.money.loan.data.response.ClavicytheriumResponse
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 17:10
 * @Describe    :
 */
class GoldenMoneyViewModel : BaseViewModel() {

    /** 获取订单还款计划 */
    var clavicytheriumResult = MutableLiveData<ClavicytheriumResponse>()
    fun clavicytheriumCallBack(paramsBody: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.clavicytherium(paramsBody).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(SettingUtil.removeQuotes(dataBody), Constant.AES_KEY)
                        val gson = Gson()
                        val mData: ClavicytheriumResponse = gson.fromJson(mResponse, ClavicytheriumResponse::class.java)
                        clavicytheriumResult.value = mData
                    }
                }
            }
            loadingType = LoadingType.LOADING_CUSTOM
            loadingMessage = "loading....."
            requestCode = NetUrl.CLAVICYTHERIUM
        }
    }
}