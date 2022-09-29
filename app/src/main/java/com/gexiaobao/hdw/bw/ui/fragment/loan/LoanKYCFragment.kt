package com.gexiaobao.hdw.bw.ui.fragment.loan

import android.annotation.SuppressLint
import android.os.Bundle
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.databinding.FragmentKycLoanBinding
import com.gexiaobao.hdw.bw.ui.viewmodel.LoanFragmentViewModel

/**
 *  author : huxiaowei
 *  date : 2022/9/29 20:58
 *  description : 认证之后的首页
 */
class LoanKYCFragment : BaseFragment<LoanFragmentViewModel, FragmentKycLoanBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewmodel = mViewModel
    }

}