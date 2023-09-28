package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentPersonalInfoBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheel.SexPicker
import com.loan.golden.cash.money.wheel.contract.OnOptionPickedListener

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 15:49
 * @Describe    : PersonalInfo
 */
class FragmentPersonalInfo : BaseFragment<BasicFormsViewModel, FragmentPersonalInfoBinding>(), OnOptionPickedListener {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Personal information") { nav().navigateUp() }
    }


    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llPersonalInfoGender) {
            when (it) {
                mBind.llPersonalInfoGender -> {
                    val picker = SexPicker(activity)
                    picker.setBodyWidth(140)
                    picker.setIncludeSecrecy(false)
                    picker.setDefaultValue("女")
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

    }
}