package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.databinding.FragmentQuestionListBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.QuestionListAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.showLoadingExt

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 13:28
 * @Describe    :
 */
class FragmentQuestionList : BaseFragment<MineViewModel, FragmentQuestionListBinding>() {

    private var mId: String = ""
    private val mAdapter: QuestionListAdapter by lazy { QuestionListAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolBar.initBack("Ask Question") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()

        refreshData(true)
    }

    private fun refreshData(isRefresh: Boolean) {
        showLoadingExt("loading.....")
        mViewModel.diaplasisCallBack(isRefresh, mId)

        initAdapter()
    }

    private fun initAdapter() {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.diaplasisResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    if (it.page == null || it.page!!.content.isEmpty()) {
                        mBind.llEmptyView.isVisible = true
                    } else {
                        mAdapter.setList(it.page!!.content)
                        mAdapter.notifyDataSetChanged()
                    }
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }
}