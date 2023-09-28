package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentContactInformationBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:45
 * @Describe    : ContactInformation
 */
class FragmentContactInformation : BaseFragment<BasicFormsViewModel, FragmentContactInformationBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Contact information") { nav().navigateUp() }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvContactInfoSubmit) {
            when (it) {
                mBind.tvContactInfoSubmit -> {

                }
            }
        }
    }
}