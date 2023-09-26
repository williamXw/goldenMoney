package com.loan.golden.cash.money.loan.ui.fragment.ocr

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.DatetimeUtil
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.param.CarPologyParam
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.data.response.OCRDetailResponse
import com.loan.golden.cash.money.loan.databinding.FragmentOcrDetailBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * @Author      : hxw
 * @Date        : 2023/9/26 13:28
 * @Describe    : OCRDetail
 */
class FragmentOCRDetail : BaseFragment<ORCViewModel, FragmentOcrDetailBinding>() {

    private lateinit var mData: OCRDetailResponse

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Ocr Detail") {
            nav().navigateUp()
        }
        mData = arguments?.getParcelable(Constant.DATA)!!
        mBind.data = mData
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvOCRDetailSubmit) {
            when (it) {
                mBind.tvOCRDetailSubmit -> {
                    val carParam = CarPologyParam(
                        CarPologyParam.Model(
                            idCard = mData.idCard,
                            realName = mData.realName,
                            taxRegNumber = mData.taxRegNumber,
                            birthDay = mData.birthDay,
                            idCardImageFront = mData.idCardImageFront,
                            idCardImageBack = mData.idCardImageBack,
                            idCardImagePan = mData.idCardImagePan
                        )
                    )
                    val strData = Gson().toJson(carParam)
                    val paramsBody =
                        AESTool.encrypt1(strData, Constant.AES_KEY)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    mViewModel.carpologyCallBack(paramsBody)
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 提交用户信息 */
        mViewModel.carpologyResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val mData: AesirResponse? = gson.fromJson(mResponse, AesirResponse::class.java)
                    if (mData != null) {
                        if (mData.status == 1012) {
                            startActivity<LoginActivity>()
                            return@observe
                        }
                        if (mData.status == 0) {
                            val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
                            val strData = Gson().toJson(aeSirParam)
                            val paramsBody =
                                AESTool.encrypt1(strData, Constant.AES_KEY)
                                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                            mViewModel.aesculinAesirCallBack(paramsBody)
                        } else {
                            val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                            RxToast.showToast(msg)
                        }
                    }
                }
            }
        }
        /** 获取一个未完成的表单 */
        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                var formType = ""
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val aesirResponse: AesirResponse? =
                        gson.fromJson(mResponse, AesirResponse::class.java)
                    if (aesirResponse != null) {
                        if (aesirResponse.status == 1012) {
                            startActivity<LoginActivity>()
                            return@observe
                        }
                        if (aesirResponse.status == 0 && aesirResponse.model != null) {
                            if (aesirResponse.model!!.forms.isNotEmpty() || aesirResponse.model!!.forms.size != 0) {
                                aesirResponse.model!!.forms.forEachIndexed { _, _ ->
                                    formType = aesirResponse.model!!.forms[0].formType
                                }
                            }
                        } else {
                            val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                            RxToast.showToast(msg)
                        }
                    }
                }
                when (formType) {
                    "OCR" -> {//证件识别
                        nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                    }

                    "BASIC" -> {//基础信息
                        nav().navigateAction(R.id.action_to_fragment_basic_info)
                    }

                    "ALIVE" -> {//活体检测

                    }

                    "ALIVE_H5" -> {//活体检测H5

                    }
                }
            }
        }
    }
}