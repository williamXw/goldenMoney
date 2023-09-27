package com.loan.golden.cash.money.loan.ui.fragment.ocr

import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentBasicInfoBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel

/**
 *  author : huxiaowei
 *  date : 2023/9/25 20:56Ø
 *  description : Work information
 */
class FragmentBasicInfo : BaseFragment<ORCViewModel, FragmentBasicInfoBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Work information") {
            nav().navigateUp()
        }
    }
}