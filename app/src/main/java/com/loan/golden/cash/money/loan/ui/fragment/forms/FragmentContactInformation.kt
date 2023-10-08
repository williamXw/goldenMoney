package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.param.ContactParam
import com.loan.golden.cash.money.loan.databinding.FragmentContactInformationBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.SinglePicker

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:45
 * @Describe    : ContactInformation
 */
class FragmentContactInformation : BaseFragment<BasicFormsViewModel, FragmentContactInformationBinding>(),
    OnOptionPickedListener {

    private var selectType = -1
    private var mFormId = ""
    private var genderJSonStr = ""
    private var maritalJSonStr: String = ""
    private lateinit var mPicker: SinglePicker
    private var mContactIndex1 = -1
    private var mContactIndex2 = -1
    private var mMaritalIndex1 = -1
    private var mMaritalIndex2 = -1
    private var mGender1 = ""
    private var mGender2 = ""
    private var mMarital1 = ""
    private var mMarital2 = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Contact information") { nav().navigateUp() }
        mFormId = arguments?.getString("formId").toString()
        genderJSonStr = arguments?.getString("genderJSonStr").toString()
        maritalJSonStr = arguments?.getString("maritalJSonStr").toString()
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(
            mBind.tvContactInfoSubmit,
            mBind.llContactInfoGender,
            mBind.llContactInfoGender2,
            mBind.llContactInfoMaritalStatus,
            mBind.llContactInfoMaritalStatus2
        ) {
            when (it) {
                mBind.tvContactInfoSubmit -> {
                    submitContactInfo()
                }

                mBind.llContactInfoGender -> {
                    selectType = 1
                    selectedPicker(selectType, genderJSonStr)
                }

                mBind.llContactInfoGender2 -> {
                    selectType = 2
                    selectedPicker(selectType, genderJSonStr)
                }

                mBind.llContactInfoMaritalStatus -> {
                    selectType = 3
                    selectedPicker(selectType, maritalJSonStr)
                }

                mBind.llContactInfoMaritalStatus2 -> {
                    selectType = 4
                    selectedPicker(selectType, maritalJSonStr)
                }
            }
        }
    }

    private fun submitContactInfo() {
        val contactList: ArrayList<ContactParam.ModelBean.SubmitDataBean.UserEmergsBean> = arrayListOf()
        contactList.add(ContactParam.ModelBean.SubmitDataBean.UserEmergsBean(name = ""))
        val contact = ContactParam(
            ContactParam.ModelBean(
                formId = mFormId,
                submitData = ContactParam.ModelBean.SubmitDataBean(
                    userEmergs = contactList
                )
            )
        )
    }

    private fun selectedPicker(type: Int, jsonStr: String) {
        if (jsonStr.isEmpty()) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> SinglePicker(it1, jsonStr) }!!
        when (type) {
            1 -> {
                mPicker.setDefaultPosition(mContactIndex1)
            }

            2 -> {
                mPicker.setDefaultPosition(mContactIndex2)
            }

            3 -> {
                mPicker.setDefaultPosition(mMaritalIndex1)
            }

            4 -> {
                mPicker.setDefaultPosition(mMaritalIndex2)
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
            1 -> {
                mGender1 = (item as MaritalEntity).id
                mContactIndex1 = position
                mBind.tvContactInfoGender.text = mPicker.wheelView.formatItem(position)
            }

            2 -> {
                mGender2 = (item as MaritalEntity).id
                mContactIndex2 = position
                mBind.tvContactInfoGender2.text = mPicker.wheelView.formatItem(position)
            }

            3 -> {
                mMarital1 = (item as MaritalEntity).id
                mMaritalIndex1 = position
                mBind.tvContactInfoMaritalStatus.text = mPicker.wheelView.formatItem(position)
            }

            4 -> {
                mMarital2 = (item as MaritalEntity).id
                mMaritalIndex2 = position
                mBind.tvContactInfoMaritalStatus2.text = mPicker.wheelView.formatItem(position)
            }
        }
    }
}