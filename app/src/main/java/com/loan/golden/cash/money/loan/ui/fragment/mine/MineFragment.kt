package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.CacheUtil
import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.response.LoginResponse
import com.loan.golden.cash.money.loan.databinding.FragmentMineBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.finishAllActivity

/**
 * @Author      : hxw
 * @Date        : 2023/9/2 14:01
 * @Describe    : 我的
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(
            mBind.ivMineHead,
            mBind.llMineMyOrder,
            mBind.llMinePrivacyPolicy,
            mBind.llMineTermsCondition,
            mBind.llMineLoanProduct,
            mBind.llMineServiceEmail,
            mBind.llMineSuccess,
            mBind.llMineOverdue,
            mBind.llMineFinish,
            mBind.tvMineLogout,
        ) {
            when (it) {
                mBind.tvMineLogout -> {
                    KvUtils.encode(Constant.TOKEN, "")
                    CacheUtil.setIsLogin(false)
                    finishAllActivity()
                }

                mBind.ivMineHead -> {
                    nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                }

                mBind.llMineMyOrder -> {
                    nav().navigateAction(R.id.action_to_fragment_product_loan, Bundle().apply {
                        putInt("orderIndex", 0)
                    })
                }

                mBind.llMineTermsCondition -> {
                    nav().navigateAction(R.id.action_login_fragment_privacy_policy, Bundle().apply {
                        putString("url", "https://app.goldenmoney.shop/html/agreement/xeptze.html")
                    })
                }

                mBind.llMinePrivacyPolicy -> {
                    nav().navigateAction(R.id.action_login_fragment_privacy_policy, Bundle().apply {
                        putString("url", "https://app.goldenmoney.shop/html/agreement/ultfii.html")
                    })
                }

                mBind.llMineLoanProduct -> {
                    nav().navigateAction(R.id.action_to_fragment_product_list)
                }

                mBind.llMineServiceEmail -> {
                    nav().navigateAction(R.id.action_to_fragment_service_email)
                }

                mBind.llMineSuccess -> {
                    nav().navigateAction(R.id.action_to_fragment_product_loan, Bundle().apply {
                        putInt("orderIndex", 1)
                    })
                }

                mBind.llMineOverdue -> {
                    nav().navigateAction(R.id.action_to_fragment_product_loan, Bundle().apply {
                        putInt("orderIndex", 2)
                    })
                }

                mBind.llMineFinish -> {
                    nav().navigateAction(R.id.action_to_fragment_product_loan, Bundle().apply {
                        putInt("orderIndex", 3)
                    })
                }
            }
        }
    }
}