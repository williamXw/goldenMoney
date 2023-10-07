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

    private var selectType = 0
    private var maritalJSonStr: String = ""
    private var genderJSonStr: String = ""
    private var childrenJSonStr: String = ""
    private var residenceJSonStr: String = ""
    private lateinit var genderPicker: SinglePicker
    private lateinit var sinPicker: SinglePicker
    private lateinit var childPicker: SinglePicker
    private lateinit var residencePicker: SinglePicker
    private var mGenderIndex = -1
    private var mMaritalIndex = -1
    private var mChildIndex = -1
    private var mResidenceIndex = -1

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
        setOnclickNoRepeat(
            mBind.llPersonalInfoGender,
            mBind.llPersonalInfoMaritalStatus,
            mBind.llPersonalInfoChildrenCount,
            mBind.llPersonalInfoResidence
        ) {
            when (it) {
                mBind.llPersonalInfoGender -> {//性别
                    if (genderJSonStr.isEmpty()) {
                        RxToast.showToast("data exception")
                        return@setOnclickNoRepeat
                    }
                    selectType = 1
                    genderPicker = activity?.let { it1 -> SinglePicker(it1, genderJSonStr) }!!
                    genderPicker.setDefaultPosition(mGenderIndex)
                    genderPicker.setOnOptionPickedListener(this)
                    genderPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
                        genderPicker.titleView.text = genderPicker.wheelView.formatItem(position)
                    }
                    genderPicker.show()
                }

                mBind.llPersonalInfoMaritalStatus -> {//婚姻状况
                    if (maritalJSonStr.isEmpty()) {
                        RxToast.showToast("data exception")
                        return@setOnclickNoRepeat
                    }
                    selectType = 2
                    sinPicker = activity?.let { it1 -> SinglePicker(it1, maritalJSonStr) }!!
                    sinPicker.setDefaultPosition(mMaritalIndex)
                    sinPicker.setOnOptionPickedListener(this)
                    sinPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
                        sinPicker.titleView.text = sinPicker.wheelView.formatItem(position)
                    }
                    sinPicker.show()
                }

                mBind.llPersonalInfoChildrenCount -> {//几个孩子
                    if (childrenJSonStr.isEmpty()) {
                        RxToast.showToast("data exception")
                        return@setOnclickNoRepeat
                    }
                    selectType = 3
                    childPicker = activity?.let { it1 -> SinglePicker(it1, childrenJSonStr) }!!
                    childPicker.setDefaultPosition(mChildIndex)
                    childPicker.setOnOptionPickedListener(this)
                    childPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
                        childPicker.titleView.text = childPicker.wheelView.formatItem(position)
                    }
                    childPicker.show()
                }

                mBind.llPersonalInfoResidence -> {//住所
                    if (residenceJSonStr.isEmpty()) {
                        RxToast.showToast("data exception")
                        return@setOnclickNoRepeat
                    }
                    selectType = 4
                    residencePicker = activity?.let { it1 -> SinglePicker(it1, residenceJSonStr) }!!
                    residencePicker.setDefaultPosition(mResidenceIndex)
                    residencePicker.setOnOptionPickedListener(this)
                    residencePicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
                        residencePicker.titleView.text = residencePicker.wheelView.formatItem(position)
                    }
                    residencePicker.show()
                }
            }
        }
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        when (selectType) {
            1 -> {
                mGenderIndex = position
                mBind.tvPersonalInfoPhone.text = genderPicker.wheelView.formatItem(position)
            }

            2 -> {
                mMaritalIndex = position
                mBind.tvPersonalInfoMaritalStatus.text = sinPicker.wheelView.formatItem(position)
            }

            3 -> {
                mChildIndex = position
                mBind.tvPersonalInfoChildrenCount.text = childPicker.wheelView.formatItem(position)
            }

            4 -> {
                mResidenceIndex = position
                mBind.tvPersonalInfoResidence.text = residencePicker.wheelView.formatItem(position)
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
                    if (it?.model != null && it.model!!.forms.isNotEmpty() && it.model!!.forms[0].content.isNotEmpty()) {
                        it.model?.forms?.get(0)?.content?.forEachIndexed { _, contentBean ->
                            if (contentBean.name == "Marital Status") {
                                maritalJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
                            if (contentBean.name == "Gender") {
                                genderJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
                            if (contentBean.name == "Children count") {
                                childrenJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
                            if (contentBean.name == "Residence") {
                                residenceJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
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