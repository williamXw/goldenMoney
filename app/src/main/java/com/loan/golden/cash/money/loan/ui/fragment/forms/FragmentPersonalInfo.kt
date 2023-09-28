package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.data.param.FigeaterParam
import com.loan.golden.cash.money.loan.databinding.FragmentPersonalInfoBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheel.SexPicker
import com.loan.golden.cash.money.wheel.contract.OnOptionPickedListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 15:49
 * @Describe    : PersonalInfo
 */
class FragmentPersonalInfo : BaseFragment<BasicFormsViewModel, FragmentPersonalInfoBinding>(),
    OnOptionPickedListener {

    private lateinit var picker: SexPicker
    private var mGender = "女"

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Personal information") { nav().navigateUp() }
        val mFormId = arguments?.getString("formId").toString()
        /** 获取指定表单 */
        val body = CharlottetownParam(
            CharlottetownParam.Model(
                formId = mFormId,
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.lottetownCallBack(paramsBody)
    }


    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llPersonalInfoGender) {
            when (it) {
                mBind.llPersonalInfoGender -> {
                    picker = SexPicker(activity)
                    picker.setBodyWidth(140)
                    picker.setIncludeSecrecy(false)
                    picker.setDefaultValue(mGender)
                    picker.setOnOptionPickedListener(this)
                    picker.wheelLayout.setOnOptionSelectedListener { position, item ->
                        picker.titleView.text = picker.wheelView.formatItem(position)
                    }
                    picker.show()
                }
            }
        }
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        mGender = picker.wheelView.formatItem(position)
        mBind.tvPersonalInfoPhone.text = mGender
    }
}