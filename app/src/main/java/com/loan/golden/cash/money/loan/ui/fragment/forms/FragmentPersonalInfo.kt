package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.google.android.material.bottomsheet.BottomSheetDialog
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
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.data.param.FigeaterParam
import com.loan.golden.cash.money.loan.data.param.PersonalInfoParam
import com.loan.golden.cash.money.loan.databinding.FragmentPersonalInfoBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.FigeaterAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.SinglePicker
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.getColorExt
import me.hgj.mvvmhelper.ext.showLoadingExt
import me.hgj.mvvmhelper.ext.vertical
import me.hgj.mvvmhelper.util.decoration.DividerOrientation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 15:49
 * @Describe    : PersonalInfo
 */
class FragmentPersonalInfo : BaseFragment<BasicFormsViewModel, FragmentPersonalInfoBinding>(), OnOptionPickedListener {

    private var selectType = -1
    private var maritalJSonStr: String = ""
    private var genderJSonStr: String = ""
    private var childrenJSonStr: String = ""
    private var residenceJSonStr: String = ""
    private var educationJSonStr: String = ""
    private lateinit var mPicker: SinglePicker
    private var mGenderIndex = -1
    private var mMaritalIndex = -1
    private var mChildIndex = -1
    private var mResidenceIndex = -1
    private var mEducationIndex = -1

    private lateinit var dialogBottomAddress: BottomSheetDialog

    /** 选择省市区 */
    private val mAdapter: FigeaterAdapter by lazy { FigeaterAdapter(arrayListOf()) }
    private var parentId = ""
    private var province = ""
    private var mProvinceId = ""
    private var mCityId = ""
    private var city = ""
    private var area = ""
    private var town = ""
    private var mFormId = ""
    private var mEducation = ""//教育
    private var mSex = ""//性别
    private var mMarital = ""//婚姻状态
    private var mChildrenCount = ""//
    private var mResidence = ""//住所

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Personal information") { nav().navigateUp() }
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
        setOnclickNoRepeat(
            mBind.llPersonalInfoEducation,
            mBind.llPersonalInfoGender,
            mBind.llPersonalInfoMaritalStatus,
            mBind.llPersonalInfoChildrenCount,
            mBind.llPersonalInfoResidence,
            mBind.llPersonalInfoAddress,
            mBind.tvPersonalInfoSubmit
        ) {
            when (it) {
                mBind.llPersonalInfoEducation -> {//教育
                    selectType = 0
                    selectedPicker(selectType, educationJSonStr)
                }

                mBind.llPersonalInfoGender -> {//性别
                    selectType = 1
                    selectedPicker(selectType, genderJSonStr)
                }

                mBind.llPersonalInfoMaritalStatus -> {//婚姻状况
                    selectType = 2
                    selectedPicker(selectType, maritalJSonStr)
                }

                mBind.llPersonalInfoChildrenCount -> {//几个孩子
                    selectType = 3
                    selectedPicker(selectType, childrenJSonStr)
                }

                mBind.llPersonalInfoResidence -> {//住所
                    selectType = 4
                    selectedPicker(selectType, residenceJSonStr)
                }

                mBind.llPersonalInfoAddress -> {//地址
                    parentId = ""
                    getCompanyAddress()
                }

                mBind.tvPersonalInfoSubmit -> {
                    submitPersonalInfo()
                }
            }
        }
    }

    private fun submitPersonalInfo() {
        when {
            mBind.tvPersonalInfoEducation.text.isEmpty() -> RxToast.showToast("Please select your education")

            else -> {
                val body = PersonalInfoParam(
                    PersonalInfoParam.ModelBean(
                        formId = mFormId,
                        submitData = PersonalInfoParam.ModelBean.SubmitDataBean(
                            education = mEducation,
                            sex = mSex,
                            marital = mMarital,
                            childrenCount = mChildrenCount,
                            postalCode = mBind.etPersonalInfoCode.text.toString().trim(),
                            residence = mResidence,
                            address = PersonalInfoParam.ModelBean.SubmitDataBean.AddressBean(
                                bigAddress = PersonalInfoParam.ModelBean.SubmitDataBean.AddressBean.BigAddressBean(
                                    province = mProvinceId,
                                    city = mCityId
                                ),
                                detailAddress = mBind.etPersonalAddressDetails.text.toString().trim()
                            ),
                            email = mBind.etPersonalInfoEmail.text.toString().trim()
                        )
                    )
                )

                val gsonData = Gson().toJson(body)
                val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                mViewModel.lustrationLustreCallBack(paramsBody)
            }
        }
    }

    private fun getCompanyAddress() {
        val body = FigeaterParam(parentId)
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.getFigeaterCallBack(paramsBody)
    }

    @SuppressLint("SetTextI18n")
    private fun showAddressDialog() {
        dialogBottomAddress = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }!!
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.bottom_figeater_view, null)
        val swipeRecyclerView = dialogView.findViewById<SwipeRecyclerView>(R.id.swipeRecyclerView)
        swipeRecyclerView.run {
            vertical()
            divider {
                //分割线颜色
                setColor(getColorExt(R.color.colorBgGray_EBEBEB))
                //分割线高度
                setDivider(1)
                //是否首尾都有分割线
                includeVisible = false
                //分割线方向
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapter
            mAdapter.setOnItemClickListener { _, _, position ->
                val mType = mAdapter.data[position].type
                when (mType) {
                    "PROVINCES" -> {
                        mProvinceId = mAdapter.data[position].id
                        province = mAdapter.data[position].name
                    }

                    "REGENCIES" -> {
                        mCityId = mAdapter.data[position].id
                        city = mAdapter.data[position].name
                    }

                    "DISTRICTS" -> {
                        area = mAdapter.data[position].name
                    }

                    "VILLAGES" -> {
                        town = mAdapter.data[position].name
                    }
                }
                if (mAdapter.data[position].haveChild) {
                    dialogBottomAddress.dismiss()
                    parentId = if (mType == "PROVINCES") {
                        mAdapter.data[position].id
                    } else {
                        mAdapter.data[position].parentId
                    }
                    getCompanyAddress()
                } else {
                    dialogBottomAddress.dismiss()
                    mBind.tvPersonalInfoAddress.text = "$province $city $area $town"
                }
            }
            dialogBottomAddress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogBottomAddress.setContentView(dialogView)
            dialogBottomAddress.show()
        }
    }

    private fun selectedPicker(selectType: Int, jsonStr: String) {
        if (jsonStr.isEmpty()) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> SinglePicker(it1, jsonStr) }!!
        when (selectType) {
            0 -> {
                mPicker.setDefaultPosition(mEducationIndex)
            }

            1 -> {
                mPicker.setDefaultPosition(mGenderIndex)
            }

            2 -> {
                mPicker.setDefaultPosition(mMaritalIndex)
            }

            3 -> {
                mPicker.setDefaultPosition(mChildIndex)
            }

            4 -> {
                mPicker.setDefaultPosition(mResidenceIndex)
            }
        }

        mPicker.setOnOptionPickedListener(this)
        mPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
            mPicker.titleView.text = mPicker.wheelView.formatItem(position)
        }
        mPicker.show()
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        when (selectType) {
            0 -> {
                mEducation = (item as MaritalEntity).id
                mEducationIndex = position
                mBind.tvPersonalInfoEducation.text = mPicker.wheelView.formatItem(position)
            }

            1 -> {
                mSex = (item as MaritalEntity).id
                mGenderIndex = position
                mBind.tvPersonalInfoPhone.text = mPicker.wheelView.formatItem(position)
            }

            2 -> {
                mMarital = (item as MaritalEntity).id
                mMaritalIndex = position
                mBind.tvPersonalInfoMaritalStatus.text = mPicker.wheelView.formatItem(position)
            }

            3 -> {
                mChildrenCount = (item as MaritalEntity).id
                mChildIndex = position
                mBind.tvPersonalInfoChildrenCount.text = mPicker.wheelView.formatItem(position)
            }

            4 -> {
                mResidence = (item as MaritalEntity).id
                mResidenceIndex = position
                mBind.tvPersonalInfoResidence.text = mPicker.wheelView.formatItem(position)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
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
        /** 获取地址信息 */
        mViewModel.figeaterResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    showAddressDialog()
                    mAdapter.setList(it.model)
                    mAdapter.notifyDataSetChanged()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 获取指定表单 */
        mViewModel.lottetownResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    if (it?.model != null && it.model!!.forms.isNotEmpty() && it.model!!.forms[0].content.isNotEmpty()) {
                        it.model?.forms?.get(0)?.content?.forEachIndexed { _, contentBean ->
                            if (contentBean.name == "Education") {
                                educationJSonStr = JSONObject.toJSONString(contentBean.options)
                            }
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

        /** 获取一个未完成的表单 */
        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            var formType = ""
            if (it.model!!.forms.isNotEmpty() || it.model!!.forms.size != 0) {
                it.model!!.forms.forEachIndexed { _, _ ->
                    formType = it.model!!.forms[0].columnField
                    mFormId = it.model!!.forms[0].formId
                }
            } else {
                nav().navigateAction(R.id.action_to_fragment_product_list)
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
                    nav().navigateAction(R.id.action_to_fragment_live_detection)
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
}