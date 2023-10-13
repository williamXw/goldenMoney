package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentQuestionListBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.showLoadingExt

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 13:28
 * @Describe    :
 */
class FragmentQuestionList : BaseFragment<MineViewModel, FragmentQuestionListBinding>() {

    private var mId: String = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolBar.initBack("Ask Question") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()

        refreshData(true)
    }

    private fun refreshData(isRefresh: Boolean) {
        showLoadingExt("loading.....")
        mViewModel.diaplasisCallBack(isRefresh, mId)
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        setOnclickNoRepeat(mBind.tvQuestionListSubmit) {
            when (it) {
                mBind.tvQuestionListSubmit -> {
                    nav().navigateAction(R.id.action_to_fragment_ask_question, Bundle().apply {
                        putString("id", mId)
                    })
                }
            }
        }
    }
}