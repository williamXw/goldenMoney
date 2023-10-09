package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.param.BankParam
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.databinding.FragmentBankInfoBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.SinglePicker
import me.hgj.mvvmhelper.ext.showLoadingExt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/10/8 13:27
 * @Describe    : 银行卡信息
 */
class FragmentBankInfo : BaseFragment<BasicFormsViewModel, FragmentBankInfoBinding>(), OnOptionPickedListener {

    private var mFormId = ""
    private var bankJSonStr = ""
    private var bankId = ""
    private lateinit var mPicker: SinglePicker

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Bank") { nav().navigateUp() }
        mFormId = arguments?.getString("formId").toString()

        /** 获取指定表单 */
        val body = CharlottetownParam(CharlottetownParam.Model(formId = mFormId))
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading....")
        mViewModel.lottetownCallBack(paramsBody)
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvBankSubmit,mBind.llBankName) {
            when (it) {
                mBind.tvBankSubmit -> {
                    submitBankInfo()
                }
                mBind.llBankName->{
                    selectedPicker()
                }
            }
        }
    }

    private fun selectedPicker() {
        if (bankJSonStr.isEmpty()) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> SinglePicker(it1, bankJSonStr) }!!
        mPicker.setOnOptionPickedListener(this)
        mPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
            mPicker.titleView.text = mPicker.wheelView.formatItem(position)
        }
        mPicker.show()
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 获取指定表单 */
        mViewModel.lottetownResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    if (it?.model != null && it.model!!.forms.isNotEmpty() && it.model!!.forms[0].content.isNotEmpty()) {
                        it.model?.forms?.get(0)?.content?.forEachIndexed { _, contentBean ->
                            if (contentBean.name == "Bank Name") {
                                bankJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
                        }
                    }
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 提交表单 */
        mViewModel.lustreResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    getIncompleteForm()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 获取一个未完成的表单 */
        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            var formType = ""
            if (it.model!!.forms.isNotEmpty() || it.model!!.forms.size != 0) {
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
                    nav().navigateAction(R.id.action_to_fragment_bank_info, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "live" -> {//活体检测
                    nav().navigateAction(R.id.action_to_fragment_live_detection, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "ALIVE_H5" -> {//活体检测H5

                }
            }
        }
    }

    /** 获取一个未完成的表单 */
    private fun getIncompleteForm() {
        val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
        val strData = Gson().toJson(aeSirParam)
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        context?.let { it1 -> mViewModel.aesculinAesirCallBack(paramsBody, it1) }
    }

    private fun submitBankInfo() {
        val ifscCode = mBind.etIFSCCode.text.toString().trim()
        val bankCard = mBind.etBankNumber.text.toString().trim()
        if (bankCard.length != 10) {
            RxToast.showToast("Incorrect bankNumber format")
            return
        }
        if (ifscCode.indexOf("0", 0, false) != 4 || ifscCode.length != 11) {
            RxToast.showToast("Incorrect IFSC format")
            return
        }
        val body = BankParam(BankParam.ModelBean(
            formId = mFormId,
            submitData = BankParam.ModelBean.SubmitDataBean(
                userBank = BankParam.ModelBean.SubmitDataBean.UserBankBean(
                    bankCode = bankId,
                    bankCard = mBind.etBankNumber.text.toString().trim(),
                    ifscCode = mBind.etIFSCCode.text.toString().trim()
                )
            )
        ))
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.lustrationLustreCallBack(paramsBody)
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        mBind.tvBankName.text = mPicker.wheelView.formatItem(position)
        bankId = (item as MaritalEntity).id
    }
}