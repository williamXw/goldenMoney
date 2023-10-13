package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentRolloverBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 15:10
 * @Describe    : 延期还款
 */
class FragmentRollover : BaseFragment<MineViewModel, FragmentRolloverBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolBar.initBack("Rollover") { nav().navigateUp() }
    }
}