package com.gexiaobao.hdw.bw.ui.fragment.repayment

import android.os.Bundle
import com.gexiaobao.hdw.bw.R
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.app.util.nav
import com.gexiaobao.hdw.bw.app.util.navigateAction
import com.gexiaobao.hdw.bw.app.util.setOnclickNoRepeat
import com.gexiaobao.hdw.bw.databinding.FragmentRepaymentBinding
import com.gexiaobao.hdw.bw.ui.viewmodel.RepaymentFragmentViewModel

/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe : 比赛
 */
class RePaymentFragment : BaseFragment<RepaymentFragmentViewModel, FragmentRepaymentBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        setOnclickNoRepeat(mBind.btnContinue) {
            when (it) {
                mBind.btnContinue -> {
                    nav().navigateAction(R.id.action_loan_to_indentification)
                }
            }
        }
        super.onBindViewClick()
    }
}