package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentServiceEmailBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/7 17:03
 * @Describe    : ServiceEmail
 */
class ServiceEmailFragment : BaseFragment<MineViewModel, FragmentServiceEmailBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Service Email") {
            nav().navigateUp()
        }
    }
}