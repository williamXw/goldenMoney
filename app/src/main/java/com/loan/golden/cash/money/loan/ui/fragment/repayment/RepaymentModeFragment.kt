package com.loan.golden.cash.money.loan.ui.fragment.repayment

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentRepaymentModeBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 13:57
 * @Describe    : RepaymentMode
 */
class RepaymentModeFragment : BaseFragment<RepaymentViewModel, FragmentRepaymentModeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Repayment Mode") { nav().navigateUp() }
    }
}