package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentGoldenMoneyBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.GoldenMoneyViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 17:10
 * @Describe    : 还款页
 */
class GoldenMoneyFragment : BaseFragment<GoldenMoneyViewModel, FragmentGoldenMoneyBinding>() {

    private var mId: String = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Golden Money") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.ivCustomerService, mBind.tvGoldenMoneyRollover, mBind.tvGoldenMoneyRepayment) {
            when (it) {
                mBind.ivCustomerService -> {
                    nav().navigateAction(R.id.action_to_fragment_ask_question_list, Bundle().apply {
                        putString("id", mId)
                    })
                }

                mBind.tvGoldenMoneyRollover -> {
                    nav().navigateAction(R.id.action_to_fragment_rollover, Bundle().apply {
                        putString("id", mId)
                    })
                }

                mBind.tvGoldenMoneyRepayment -> {
                    nav().navigateAction(R.id.action_to_fragment_repayment_mode, Bundle().apply {
                        putString("id", mId)
                    })
                }

            }
        }
    }
}