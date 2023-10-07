package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.data.response.CommonResponse
import com.loan.golden.cash.money.loan.data.response.DiamantiferousResponse
import com.loan.golden.cash.money.loan.data.response.FigeaterResponse
import com.loan.golden.cash.money.loan.data.response.KaliResponse
import com.loan.golden.cash.money.loan.data.response.LottetownResponse
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequest
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:15
 * @Describe    :
 */
class BasicFormsViewModel : BaseViewModel() {

    /** 获取工作岗位信息 */
    val kaliResponseResult = MutableLiveData<KaliResponse>()
    fun kaliCallBack(): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val kaliResult = UserRepository.kaleyardKali().await()
                val mBody = kaliResult.body?.string()
                if (kaliResult.code == 200) {
                    if (mBody != null) {
                        if (mBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(mBody, Constant.AES_KEY)
                            val gson = Gson()
                            val mData: KaliResponse =
                                gson.fromJson(mResponse, KaliResponse::class.java)
                            kaliResponseResult.value = mData
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.KALEYARD_KALI
        }
    }

    /** 获取地址信息 */
    val figeaterResult = MutableLiveData<FigeaterResponse>()
    fun getFigeaterCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.getFigeater(body).await()
                val mBody = response.body?.string()
                if (response.code == 200) {
                    if (mBody != null) {
                        if (mBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(mBody, Constant.AES_KEY)
                            val gson = Gson()
                            val mData: FigeaterResponse =
                                gson.fromJson(mResponse, FigeaterResponse::class.java)
                            figeaterResult.value = mData
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.KALEYARD_KALI
        }
    }

    /** 提交表单 */
    val lustreResult = MutableLiveData<CommonResponse>()
    fun lustrationLustreCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.lustrationLustre(body).await()
                val mBody = response.body?.string()
                if (response.code == 200) {
                    if (mBody != null) {
                        if (mBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(mBody, Constant.AES_KEY)
                            val gson = Gson()
                            val mData: CommonResponse = gson.fromJson(mResponse, CommonResponse::class.java)
                            lustreResult.value = mData
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.LUSTRATION_LUSTRE
        }
    }

    /** 获取指定表单 */
    val lottetownResult = MutableLiveData<LottetownResponse>()
    fun lottetownCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.charlotteCharlottetown(body).await()
                val mBody = response.body?.string()
                if (response.code == 200) {
                    if (mBody != null) {
                        if (mBody.isNotEmpty()) {
                            val mResponse = SettingUtil.removeQuotes(AESTool.decrypt(mBody, Constant.AES_KEY))
                            val gson = Gson()
                            val mData: LottetownResponse = gson.fromJson(mResponse, LottetownResponse::class.java)
                            lottetownResult.value = mData
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_CUSTOM
            loadingMessage = "loading....."
            requestCode = NetUrl.CHARLOTTE_CHARLOTTETOWN
        }
    }

}