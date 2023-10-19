package com.loan.golden.cash.money.loan.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.data.response.UpLoadDeviceInfoResponse
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.json.JSONObject

/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe :
 */
class MainViewModel : BaseViewModel() {

    /** 检测设备信息上报情况 */
    var nappyResult = MutableLiveData<UpLoadDeviceInfoResponse>()
    fun nappyNapraPath(mContext: Context): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.nappyNaprapath().await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(SettingUtil.removeQuotes(dataBody), Constant.AES_KEY)
                        val gson = Gson()
                        val mData: UpLoadDeviceInfoResponse = gson.fromJson(mResponse, UpLoadDeviceInfoResponse::class.java)
                        when (mData.status) {
                            1012 -> {
                                mContext.startActivity<LoginActivity>()
                            }

                            0 -> {
                                nappyResult.value = mData
                            }

                            else -> {
                                val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                                RxToast.showToast(msg)
                            }
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_NULL
            loadingMessage = "loading....."
            requestCode = NetUrl.NAPPER_NAPPY_NAPRAPATH
        }
    }
}