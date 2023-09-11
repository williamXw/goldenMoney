package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentGoldenMoneyBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.GoldenMoneyViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 17:10
 * @Describe    : 还款页
 */
class GoldenMoneyFragment : BaseFragment<GoldenMoneyViewModel, FragmentGoldenMoneyBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Golden Money") { nav().navigateUp() }
    }
}