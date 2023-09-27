package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentMyLoanBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/6 16:53
 * @Describe    : MyLoan
 */
class MyLoanFragment : BaseFragment<MineViewModel, FragmentMyLoanBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("My Loan") { nav().navigateUp() }
        initViewPager()
    }

    private fun initViewPager() {
        val orderIndex = arguments?.getInt("orderIndex", 0)
        val tab1 = mBind.tabLayout.newTab().setText("All")
        mBind.tabLayout.addTab(tab1)
        val tab2 = mBind.tabLayout.newTab().setText("Success")
        mBind.tabLayout.addTab(tab2)
        val tab3 = mBind.tabLayout.newTab().setText("Overdue")
        mBind.tabLayout.addTab(tab3)
        val tab4 = mBind.tabLayout.newTab().setText("Finish")
        mBind.tabLayout.addTab(tab4)

        mBind.tabLayout.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab) {}
            override fun onTabUnselected(p0: TabLayout.Tab) {}

            override fun onTabSelected(p0: TabLayout.Tab) {
                replaceFragments(p0.position)
            }
        })
        if (orderIndex != null) {
            replaceFragments(orderIndex)
            mBind.tabLayout.getTabAt(orderIndex)!!.select()
        }
    }

    private fun replaceFragments(position: Int) {
        childFragmentManager.beginTransaction().apply {
            when (position) {
                0 -> replace(R.id.home_activity_frag_container, FragmentMyLoanList.newInstance(0))
                1 -> replace(R.id.home_activity_frag_container, FragmentMyLoanList.newInstance(1))
                2 -> replace(R.id.home_activity_frag_container, FragmentMyLoanList.newInstance(2))
                3 -> replace(R.id.home_activity_frag_container, FragmentMyLoanList.newInstance(3))

            }
        }.commitAllowingStateLoss()
    }
}