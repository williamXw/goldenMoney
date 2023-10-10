package com.loan.golden.cash.money.loan.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.data.response.LiveResponse
import com.loan.golden.cash.money.loan.data.response.NapperResponse
import com.loan.golden.cash.money.loan.data.response.TrigonResponse
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequest
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.json.JSONObject

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 14:01
 * @Describe    :
 */
class MineViewModel : BaseViewModel() {

    /** 产品信息列表  */
    var napperResult = MutableLiveData<NapperResponse>()
    fun napperCallBack(body: RequestBody, mContext: Context): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val form = UserRepository.napper(body).await()
                val dataBody = form.body!!.string()
                if (form.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse =
                            SettingUtil.removeQuotes(AESTool.decrypt(dataBody, Constant.AES_KEY))
                        val gson = Gson()
                        val mData: NapperResponse =
                            gson.fromJson(mResponse, NapperResponse::class.java)
                        when (mData.status) {
                            1012 -> {
                                mContext.startActivity<LoginActivity>()
                            }

                            0 -> {
                                napperResult.value = mData
                            }

                            else -> {
                                val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                                RxToast.showToast(msg)
                            }
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_CUSTOM
            loadingMessage = "loading....."
            requestCode = NetUrl.NAPPER
        }
    }


    /** 产品手续费试算 */
    var trigonResult = MutableLiveData<TrigonResponse>()
    fun loanApplicationCallBack(body: RequestBody) {
        rxHttpRequest {
            onRequest = {
                val response = UserRepository.trigon(body).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                        val gson = Gson()
                        val mData: TrigonResponse? = gson.fromJson(mResponse, TrigonResponse::class.java)
                        trigonResult.value = mData!!
                    }
                }
                loadingType = LoadingType.LOADING_DIALOG
                loadingMessage = "loading....."
                requestCode = NetUrl.APOLOGIA_APOLOGISE
            }
        }
    }
}