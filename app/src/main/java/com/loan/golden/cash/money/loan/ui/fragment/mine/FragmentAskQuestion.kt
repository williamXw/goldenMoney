package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentAskQuestionBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/10/12 15:45
 * @Describe    : ask question
 */
class FragmentAskQuestion : BaseFragment<MineViewModel, FragmentAskQuestionBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Ask questions") { nav().navigateUp() }
    }
}