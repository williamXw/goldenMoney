package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.OrogenyResponse
import com.loan.golden.cash.money.loan.data.response.UnrighteousnessResponse
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 13:58
 * @Describe    :
 */
class RepaymentViewModel : BaseViewModel() {

    /** 获取订单还款方式列表 */
    var orogenyResult = MutableLiveData<OrogenyResponse>()
    fun orogenicsOrogenyCallBack(paramsBody: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.orogenicsOrogeny(paramsBody).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                        val gson = Gson()
                        val mData: OrogenyResponse = gson.fromJson(mResponse, OrogenyResponse::class.java)
                        orogenyResult.value = mData
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.OROGENICS_OROGENY
        }
    }
}