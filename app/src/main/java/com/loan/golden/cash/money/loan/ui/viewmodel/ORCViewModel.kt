package com.loan.golden.cash.money.loan.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.ImageLoaderManager
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.DiamantiferousParam
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.DiamantiferousResponse
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.makeramen.roundedimageview.RoundedImageView
import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.ext.logI
import me.hgj.mvvmhelper.ext.rxHttpRequest
import me.hgj.mvvmhelper.ext.rxHttpRequestCallBack
import me.hgj.mvvmhelper.net.LoadingType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.jetbrains.anko.startActivity
import org.json.JSONObject
import java.io.File

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 11:10
 * @Describe    :
 */
class ORCViewModel : BaseViewModel() {

    private var imageUrl: String = ""

    /** 上传文件2 (串行 请求 写法) */
    var ocrImageResult = MutableLiveData<OCRResponse>()

    /** 证件识别 */
    var diamanResult = MutableLiveData<DiamantiferousResponse>()
    fun streamStreambedCallBack(
        imageFile: File,
        type: Int,
        mContext: Context,
        ivFront: RoundedImageView,
        ivBack: RoundedImageView,
        ivPan: RoundedImageView
    ) {
        val cardType = when (type) {
            1 -> {
                "FRONT"
            }

            2 -> {
                "BACK"
            }

            3 -> {
                "PAN"
            }

            else -> {
                ""
            }
        }

        val body: RequestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull()) //表单类型
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)//表单类型
        builder.addFormDataPart("file", imageFile.name, body) //添加图片数据，body创建的请求体
        val requestBody = builder.build()
        rxHttpRequest {
            onRequest = {
                val upLoadPic = UserRepository.streamStreambed(requestBody).await()
                val dataBody = upLoadPic.body?.string()
                "打印一下upLoadPic的数据${dataBody}".logI()
                if (upLoadPic.code == 200) {
                    if (dataBody != null) {
                        if (dataBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                            val gson = Gson()
                            val ocrData: OCRResponse = gson.fromJson(mResponse, OCRResponse::class.java)
                            when (ocrData.status) {
                                1012 -> {
                                    mContext.startActivity<LoginActivity>()
                                }

                                0 -> {
                                    if (ocrData.model != null) {
                                        imageUrl = ocrData.model.ossUrl
                                    }
                                    ocrImageResult.value = ocrData
                                }

                                else -> {
                                    val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                                    RxToast.showToast(msg)
                                }
                            }
                        }
                    }
                }
                val bodyD = DiamantiferousParam(DiamantiferousParam.Model(url = imageUrl, cardType = cardType))
                val strData = Gson().toJson(bodyD)
                val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val diaman = UserRepository.diamantiferous(paramsBody).await()
                val mDiamanBody = diaman.body?.string()
                if (diaman.code == 200) {
                    if (mDiamanBody != null) {
                        if (mDiamanBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(mDiamanBody, Constant.AES_KEY)
                            val gson = Gson()
                            val dData: DiamantiferousResponse = gson.fromJson(mResponse, DiamantiferousResponse::class.java)
                            diamanResult.value = dData
                            if (dData.status == 0) {
                                when (type) {
                                    1 -> {
                                        ImageLoaderManager.loadRoundImage(mContext, imageUrl, ivFront, 12)
                                    }

                                    2 -> {
                                        ImageLoaderManager.loadRoundImage(mContext, imageUrl, ivBack, 12)
                                    }

                                    3 -> {
                                        ImageLoaderManager.loadRoundImage(mContext, imageUrl, ivPan, 12)
                                    }
                                }
                            }
                        }
                    }
                }
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