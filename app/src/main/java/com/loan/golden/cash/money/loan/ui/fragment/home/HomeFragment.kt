package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentHomeBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.HomeViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 13:59
 * @Describe    : 首页
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvHomeStartLoan) {
            when (it) {
                mBind.tvHomeStartLoan -> {
                    nav().navigateAction(R.id.action_to_fragment_repayment_mode)
                }
            }
        }
    }
}