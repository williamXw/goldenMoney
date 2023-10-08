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
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.RequestBody
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.json.JSONObject

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 13:59
 * @Describe    :
 */
class HomeViewModel : BaseViewModel() {

    /** 获取一个未完成的表单 */
    var aesculinAesirResult = MutableLiveData<AesirResponse>()
    fun aesculinAesirCallBack(body: RequestBody, mContext: Context): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val form = UserRepository.aesculinAesir(body).await()
                val dataBody = form.body!!.string()
                if (form.code == 200) {
                    if (dataBody != null && dataBody.isNotEmpty()) {
                        val mResponse = SettingUtil.removeQuotes(AESTool.decrypt(dataBody, Constant.AES_KEY))
                        val gson = Gson()
                        val mData: AesirResponse = gson.fromJson(mResponse, AesirResponse::class.java)
                        when (mData.status) {
                            1012 -> {
                                mContext.startActivity<LoginActivity>()
                            }

                            0 -> {
                                aesculinAesirResult.value = mData
                            }

                            else -> {
                                val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                                RxToast.showToast(msg)
                            }
                        }
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.AESCULAPIUS_AESCULIN_AESIR
        }
    }
}