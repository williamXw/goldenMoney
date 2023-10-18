package com.loan.golden.cash.money.loan.ui.fragment.repayment

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.OrogenyParam
import com.loan.golden.cash.money.loan.databinding.FragmentRepaymentModeBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 13:57
 * @Describe    : RepaymentMode
 */
class RepaymentModeFragment : BaseFragment<RepaymentViewModel, FragmentRepaymentModeBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Repayment Mode") { nav().navigateUp() }
        val id = arguments?.getString("id").toString()
        refreshData(id)
    }

    private fun refreshData(id: String) {
        val body = OrogenyParam(
            OrogenyParam.ModelBean(
                orderId = id
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.orogenicsOrogenyCallBack(paramsBody)
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.orogenyResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {

                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }
}