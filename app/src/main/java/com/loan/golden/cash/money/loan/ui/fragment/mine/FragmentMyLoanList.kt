package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.init
import com.loan.golden.cash.money.loan.app.ext.initFooter
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.databinding.FragmentMyLoanListBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.BlackshirtAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.dismissLoadingExt
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.showLoadingExt
import me.hgj.mvvmhelper.ext.vertical
import me.hgj.mvvmhelper.util.decoration.DividerOrientation
import me.hgj.mvvmhelper.util.decoration.SpaceItemDecoration

/**
 * @Author      : hxw
 * @Date        : 2023/9/6 17:14
 * @Describe    :
 */
class FragmentMyLoanList : BaseFragment<MineViewModel, FragmentMyLoanListBinding>() {

    private var mStatus = ""
    private val mAdapter: BlackshirtAdapter by lazy { BlackshirtAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mStatus = arguments?.getString("type").toString()
        refreshData(true)
        initAdapter()
    }

    private fun initAdapter() {
        mBind.includedList.swipeRecyclerView.run {
            vertical()
            divider {
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapter
        }
        mBind.includedList.swipeRecyclerView.init(LinearLayoutManager(context), mAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, 0))
            it.initFooter {
                //触发加载更多时请求数据
                refreshData(false)
            }
        }
        mBind.includedList.swipeRefreshLayout.init {
            refreshData(true)
        }
        mAdapter.addChildClickViewIds(R.id.tvItemNextBtn)
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.tvItemNextBtn -> {
                    if (mAdapter.data[position].status == "LOAN_SUCCESS" || mAdapter.data[position].status == "DUNNING") {//放款成功和逾期
                        nav().navigateAction(R.id.action_to_fragment_golden_money, Bundle().apply {
                            putString("id", mAdapter.data[position].id)
                        })
                    }
                }
            }
        }
    }

    private fun refreshData(isRefresh: Boolean) {
        showLoadingExt("loading.....")
        mViewModel.blackshirtCallBack(isRefresh, mStatus)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.blackshirtResult.observe(viewLifecycleOwner) {
            dismissLoadingExt()
            when (it.status) {
                0 -> {
                    if (it.page == null || it.page?.content?.isEmpty() == true) {
                        mBind.llEmptyData.isVisible = true
                        mBind.llList.isVisible = false
                    }
                    if (mBind.includedList.swipeRefreshLayout.isRefreshing) {
                        mBind.includedList.swipeRefreshLayout.isRefreshing = false
                    }
                    mAdapter.setList(it.page?.content)
                    mAdapter.notifyDataSetChanged()

                }

                1012 -> {
                    startActivity<LoginActivity>()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }

    companion object {
        fun newInstance(i: Int, type: String): FragmentMyLoanList {
            val fragment = FragmentMyLoanList()
            val bundle = Bundle()
            bundle.putInt("index", i)
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }
}