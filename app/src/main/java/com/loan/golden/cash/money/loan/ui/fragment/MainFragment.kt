package com.loan.golden.cash.money.loan.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.databinding.FragmentMainBinding
import com.loan.golden.cash.money.loan.ui.fragment.home.HomeFragment
import com.loan.golden.cash.money.loan.ui.fragment.mine.MineFragment
import com.loan.golden.cash.money.loan.ui.viewmodel.MainViewModel

/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe :
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {

    private val homeFragmentPage = HomeFragment()
    private val mineFragmentPage = MineFragment()

    override fun initView(savedInstanceState: Bundle?) {
        mBind.mainViewpager.addOnPageChangeListener(viewPagerListener)
        val fragmentManager = activity?.supportFragmentManager
        val fragmentPagerAdapter = object : FragmentPagerAdapter(fragmentManager!!) {
            override fun getCount(): Int {
                return 2
            }

            override fun getItem(position: Int): Fragment {
                when (position) {
                    0 -> {
                        return homeFragmentPage
                    }

                    1 -> {
                        return mineFragmentPage
                    }
                }
                return homeFragmentPage
            }

        }
        mBind.mainViewpager.adapter = fragmentPagerAdapter
        mBind.mainBottom.setOnNavigationItemSelectedListener(navigationViewListener)
    }

    private val viewPagerListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            mBind.mainBottom.menu.getItem(position).isChecked = true
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    private val navigationViewListener = BottomNavigationView.OnNavigationItemSelectedListener {
        mBind.mainViewpager.currentItem = it.order
        return@OnNavigationItemSelectedListener true
    }
}