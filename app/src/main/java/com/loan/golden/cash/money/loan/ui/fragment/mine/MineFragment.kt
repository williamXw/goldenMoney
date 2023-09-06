package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentMineBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 14:01
 * @Describe    : 我的
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(
            mBind.llMineMyOrder,
            mBind.llMinePrivacyPolicy,
            mBind.llMineTermsCondition,
            mBind.llMineLoanProduct,
            mBind.llMineServiceEmail
        ) {
            when (it) {
                mBind.llMinePrivacyPolicy -> {
                    nav().navigateAction(R.id.action_login_fragment_privacy_policy)
                }
            }
        }
    }
}