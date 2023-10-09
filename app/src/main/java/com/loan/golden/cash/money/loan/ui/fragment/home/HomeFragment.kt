package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.databinding.FragmentHomeBinding
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
                        AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    context?.let { it1 -> mViewModel.aesculinAesirCallBack(paramsBody, it1) }
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 获取一个未完成的表单 */
        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            var formType = ""
            if (!it.model!!.forms.isNullOrEmpty() || it.model!!.forms.size != 0) {
                it.model!!.forms.forEachIndexed { _, _ ->
                    formType = it.model!!.forms[0].columnField
                    mFormId = it.model!!.forms[0].formId
                }
            }
            when (formType) {
                "ocr" -> {//证件识别
//                        nav().navigateAction(R.id.action_to_fragment_repayment_mode)
                    nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                }

                "formPerson" -> {
                    nav().navigateAction(R.id.action_to_fragment_personal_information, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formWork" -> {//基础信息
                    nav().navigateAction(R.id.action_to_fragment_basic_info, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formEmergency" -> {
                    nav().navigateAction(R.id.action_to_fragment_contact_information, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formBank" -> {

                }

                "live" -> {//活体检测

                }

                "ALIVE_H5" -> {//活体检测H5

                }
            }
        }
    }
}