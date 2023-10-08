package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentBankInfoBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/10/8 13:27
 * @Describe    : 银行卡信息
 */
class FragmentBankInfo : BaseFragment<BasicFormsViewModel, FragmentBankInfoBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Bank") { nav().navigateUp() }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvBankSubmit) {
            when (it) {
                mBind.tvBankSubmit -> {
                    submitBankInfo()
                }
            }
        }
    }

    private fun submitBankInfo() {

    }
}