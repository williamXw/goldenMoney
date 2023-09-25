package com.loan.golden.cash.money.loan.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 11:10
 * @Describe    :
 */
class ORCViewModel : BaseViewModel() {

    /** 上传文件 */
    var ocrUpLoadResult = MutableLiveData<Response>()
    fun streamStreambedCallBack(imageFile: File): MutableLiveData<Response>? {
        val body: RequestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull()) //表单类型
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)//表单类型
        builder.addFormDataPart("file", imageFile.name, body) //添加图片数据，body创建的请求体
        val requestBody = builder.build()
        return rxHttpRequestCallBack {
            onRequest = {
                ocrUpLoadResult.value = UserRepository.streamStreambed(requestBody).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.STREAM_STREAMBED
        }
    }

    /** 证件识别 */
    var diamantiferousResult = MutableLiveData<Response>()
    fun diamantiferousCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                diamantiferousResult.value = UserRepository.diamantiferous(body).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.DIAMANTIFEROUS
        }
    }

    /** 提交用户信息 */
    var carpologyResult = MutableLiveData<Response>()
    fun carpologyCallBack(body: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                carpologyResult.value = UserRepository.carpologistCarpology(body).await()
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.CARPOLOGIST_CARPOLOGY
        }
    }

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