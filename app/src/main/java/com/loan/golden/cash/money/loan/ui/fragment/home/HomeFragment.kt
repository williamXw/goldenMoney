package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.databinding.FragmentHomeBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.HomeViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 13:59
 * @Describe    : 首页
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var mFormId = ""
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvHomeStartLoan) {
            when (it) {
                mBind.tvHomeStartLoan -> {//点击开始借款
                    val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
                    val strData = Gson().toJson(aeSirParam)
                    val paramsBody =
                        AESTool.encrypt1(strData, Constant.AES_KEY)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    mViewModel.aesculinAesirCallBack(paramsBody)
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
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
                            if (!aesirResponse.model!!.forms.isNullOrEmpty() || aesirResponse.model!!.forms.size != 0) {
                                aesirResponse.model!!.forms.forEachIndexed { index, formsData ->
                                    formType = aesirResponse.model!!.forms[0].formType
                                    mFormId = aesirResponse.model!!.forms[0].formId
                                }
                            }
                        }
                    }
                }
                when (formType) {
                    "OCR" -> {//证件识别
//                        nav().navigateAction(R.id.action_to_fragment_repayment_mode)
                        nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                    }

                    "BASIC" -> {//基础信息
                        nav().navigateAction(R.id.action_to_fragment_basic_info, Bundle().apply {
                            putString("mFormId", mFormId)
                        })
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