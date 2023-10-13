package com.loan.golden.cash.money.loan.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.ApologiseParam
import com.loan.golden.cash.money.loan.data.param.BlackshirtAllParam
import com.loan.golden.cash.money.loan.data.param.BlackshirtParam
import com.loan.golden.cash.money.loan.data.repository.UserRepository
import com.loan.golden.cash.money.loan.data.response.BlackshirtResponse
import com.loan.golden.cash.money.loan.data.response.CommonResponse
import com.loan.golden.cash.money.loan.data.response.NapperResponse
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import com.loan.golden.cash.money.loan.data.response.TrigonResponse
import com.loan.golden.cash.money.loan.data.response.UnrighteousnessResponse
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import me.hgj.mvvmhelper.base.BaseViewModel
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
 * @Date        : 2023/9/2 14:01
 * @Describe    :
 */
class MineViewModel : BaseViewModel() {

    private var strData: String = ""
    private var productId: String = ""
    private var mPageNo = 0
    private var mPageSize = 10

    /** 产品列表 */
    var blackshirtResult = MutableLiveData<BlackshirtResponse>()
    fun blackshirtCallBack(isRefresh: Boolean, status: String): MutableLiveData<Response>? {
        if (isRefresh) {
            mPageNo = 1
        }
        if (status == "All") {
            val body = BlackshirtAllParam(
                query = BlackshirtAllParam.QueryBean(
                    pageNo = mPageNo,
                    pageSize = mPageSize
                )
            )
            strData = Gson().toJson(body)
        } else {
            val body = BlackshirtParam(
                query = BlackshirtParam.QueryBean(
                    status = status,
                    pageNo = mPageNo,
                    pageSize = mPageSize
                )
            )
            strData = Gson().toJson(body)
        }
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return rxHttpRequestCallBack {
            onRequest = {
                mPageNo++
                val response = UserRepository.blackshirt(paramsBody).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                        val gson = Gson()
                        val mData: BlackshirtResponse = gson.fromJson(mResponse, BlackshirtResponse::class.java)
                        blackshirtResult.value = mData
                    }
                }
            }
            loadingType = LoadingType.LOADING_CUSTOM
            loadingMessage = "loading....."
            requestCode = NetUrl.BLACKSHIRT
        }
    }

    /** 产品信息列表  */
    var napperResult = MutableLiveData<NapperResponse>()
    fun napperCallBack(body: RequestBody, mContext: Context): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val form = UserRepository.napper(body).await()
                val dataBody = form.body!!.string()
                if (form.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = SettingUtil.removeQuotes(AESTool.decrypt(dataBody, Constant.AES_KEY))
                        val gson = Gson()
                        val mData: NapperResponse = gson.fromJson(mResponse, NapperResponse::class.java)
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

    /** 贷款申请 */
    var apologiaResult = MutableLiveData<CommonResponse>()
    fun loanApplicationCallBack(body: RequestBody) {
        val mListIds: ArrayList<String> = arrayListOf()
        rxHttpRequest {
            onRequest = {
                val response = UserRepository.trigon(body).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                        val gson = Gson()
                        val mData: TrigonResponse = gson.fromJson(mResponse, TrigonResponse::class.java)
                        if (mData.model.isNotEmpty()) {
                            productId = mData.model[0].productId
                        }
                        trigonResult.value = mData
                    }
                }
                mListIds.add(productId)
                val body = ApologiseParam(
                    model = ApologiseParam.ModelBean(
                        productIds = mListIds
                    )
                )
                val strData = Gson().toJson(body)
                val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY)
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val response2 = UserRepository.apologiaApologise(paramsBody).await()
                val dataBody2 = response2.body!!.string()
                if (response2.code == 200) {
                    if (dataBody2.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody2, Constant.AES_KEY)
                        val gson = Gson()
                        val mData2: CommonResponse = gson.fromJson(mResponse, CommonResponse::class.java)
                        apologiaResult.value = mData2
                    }
                }
            }
            loadingType = LoadingType.LOADING_DIALOG
            loadingMessage = "loading....."
            requestCode = NetUrl.APOLOGIA_APOLOGISE
        }
    }

    /** 上传文件2 (串行 请求 写法) */
    var imageResult = MutableLiveData<OCRResponse>()
    fun streamStreambedCallBack(imageFile: File) {
        val body: RequestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull()) //表单类型
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)//表单类型
        builder.addFormDataPart("file", imageFile.name, body) //添加图片数据，body创建的请求体
        val requestBody = builder.build()
        rxHttpRequest {
            onRequest = {
                val upLoadPic = UserRepository.streamStreambed(requestBody).await()
                val dataBody = upLoadPic.body?.string()
                if (upLoadPic.code == 200) {
                    if (dataBody != null) {
                        if (dataBody.isNotEmpty()) {
                            val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                            val gson = Gson()
                            val mData: OCRResponse = gson.fromJson(mResponse, OCRResponse::class.java)
                            imageResult.value = mData
                        }
                    }
                }
            }
        }
    }

    /** 获取反馈类型列表 */
    var unrighteousnessResult = MutableLiveData<UnrighteousnessResponse>()
    fun unrighteousnessCallBack(paramsBody: RequestBody): MutableLiveData<Response>? {
        return rxHttpRequestCallBack {
            onRequest = {
                val response = UserRepository.unrighteousness(paramsBody).await()
                val dataBody = response.body!!.string()
                if (response.code == 200) {
                    if (dataBody.isNotEmpty()) {
                        val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                        val gson = Gson()
                        val mData: UnrighteousnessResponse = gson.fromJson(mResponse, UnrighteousnessResponse::class.java)
                        unrighteousnessResult.value = mData
                    }
                }
            }
            loadingType = LoadingType.LOADING_CUSTOM
            loadingMessage = "loading....."
            requestCode = NetUrl.UNRIGHTEOUSNESS
        }
    }

}