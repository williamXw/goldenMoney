package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.databinding.FragmentPersonalInfoBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheel.SexPicker
import com.loan.golden.cash.money.wheel.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheel.widget.SinglePicker
import me.hgj.mvvmhelper.ext.showLoadingExt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 15:49
 * @Describe    : PersonalInfo
 */
class FragmentPersonalInfo : BaseFragment<BasicFormsViewModel, FragmentPersonalInfoBinding>(),
    OnOptionPickedListener {

    private var maritalJSonStr: String = ""
    private lateinit var picker: SexPicker
    private lateinit var sinPicker: SinglePicker
    private var mGender = "女"
    private var mMarital = "Single"
    private var selectType = 0

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
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading....")
        mViewModel.lottetownCallBack(paramsBody)
    }


    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llPersonalInfoGender, mBind.llPersonalInfoMaritalStatus) {
            when (it) {
                mBind.llPersonalInfoGender -> {//性别
                    selectType = 1
                    picker = SexPicker(activity)
                    picker.setIncludeSecrecy(false)
                    picker.setDefaultValue(mGender)
                    picker.setOnOptionPickedListener(this)
                    picker.wheelLayout.setOnOptionSelectedListener { position, item ->
                        picker.titleView.text = picker.wheelView.formatItem(position)
                    }
                    picker.show()
                }

                mBind.llPersonalInfoMaritalStatus -> {//婚姻状况
                    if (maritalJSonStr.isEmpty()) {
                        RxToast.showToast("data exception")
                        return@setOnclickNoRepeat
                    }
                    selectType = 2
                    sinPicker = activity?.let { it1 -> SinglePicker(it1, maritalJSonStr) }!!
                    sinPicker.setDefaultValue("")
                    sinPicker.setOnOptionPickedListener(this)
                    sinPicker.show()
                }
            }
        }
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        when (selectType) {
            1 -> {
                mGender = picker.wheelView.formatItem(position)
                mBind.tvPersonalInfoPhone.text = mGender
            }

            2 -> {
                mMarital = sinPicker.wheelView.formatItem(position)
                mBind.tvPersonalInfoMaritalStatus.text = mMarital
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.lottetownResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    it.model?.forms?.get(0)?.content?.forEachIndexed { index, contentBean ->
                        if (contentBean.name == "Marital Status") {
                            maritalJSonStr = JSONObject.toJSONString(contentBean.options)
                        }
                    }
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }
}