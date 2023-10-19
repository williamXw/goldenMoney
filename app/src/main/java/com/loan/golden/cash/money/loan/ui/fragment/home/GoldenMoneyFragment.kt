package com.loan.golden.cash.money.loan.ui.fragment.home

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.ClavicytheriumParam
import com.loan.golden.cash.money.loan.databinding.FragmentGoldenMoneyBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.GoldenMoneyViewModel
import me.hgj.mvvmhelper.ext.showLoadingExt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 17:10
 * @Describe    : 还款页
 */
class GoldenMoneyFragment : BaseFragment<GoldenMoneyViewModel, FragmentGoldenMoneyBinding>() {

    private var mId: String = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Golden Money") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()
    }

    override fun initData() {
        super.initData()
        val body = ClavicytheriumParam(
            ClavicytheriumParam.ModelBean(
                orderId = mId
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading.....")
        mViewModel.clavicytheriumCallBack(paramsBody)
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.ivCustomerService, mBind.tvGoldenMoneyRollover, mBind.tvGoldenMoneyRepayment) {
            when (it) {
                mBind.ivCustomerService -> {
                    nav().navigateAction(R.id.action_to_fragment_ask_question_list, Bundle().apply {
                        putString("id", mId)
                    })
                }

                mBind.tvGoldenMoneyRollover -> {
                    nav().navigateAction(R.id.action_to_fragment_rollover, Bundle().apply {
                        putString("id", mId)
                        putString("repayType", "DELAY")//展期还款
                    })
                }

                mBind.tvGoldenMoneyRepayment -> {
                    nav().navigateAction(R.id.action_to_fragment_repayment_mode, Bundle().apply {
                        putString("id", mId)
                        putString("repayType", "IMMEDIATE")//立即还款
                    })
                }

            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 获取订单还款计划 */
        mViewModel.clavicytheriumResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    mBind.data = it.model
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }
}