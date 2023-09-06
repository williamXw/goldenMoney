package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.databinding.FragmentMyLoanListBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/6 17:14
 * @Describe    :
 */
class FragmentMyLoanList : BaseFragment<MineViewModel, FragmentMyLoanListBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(i: Int): FragmentMyLoanList {
            val fragment = FragmentMyLoanList()
            val bundle = Bundle()
            bundle.putInt("index", i)
            fragment.arguments = bundle
            return fragment
        }
    }
}