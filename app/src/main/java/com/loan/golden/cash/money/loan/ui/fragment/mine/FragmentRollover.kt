package com.loan.golden.cash.money.loan.ui.fragment.mine

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
import com.loan.golden.cash.money.loan.data.param.OrogenyParam
import com.loan.golden.cash.money.loan.databinding.FragmentRolloverBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel
import me.hgj.mvvmhelper.ext.showLoadingExt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 15:10
 * @Describe    : 延期还款
 */
class FragmentRollover : BaseFragment<RepaymentViewModel, FragmentRolloverBinding>() {

    private var mId = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolBar.initBack("Rollover") { nav().navigateUp() }
    }

    override fun initData() {
        super.initData()
        val mId = arguments?.getString("id").toString()
        val body = OrogenyParam(
            OrogenyParam.ModelBean(
                orderId = mId
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading.....")
        mViewModel.breechlessCallBack(paramsBody)
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.breechlessResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    mId = it.model?.orderId.toString()
                    mBind.data = it.model
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvRolloverRepay) {
            when (it) {
                mBind.tvRolloverRepay -> {
                    nav().navigateAction(R.id.action_to_fragment_repayment_mode, Bundle().apply {
                        putString("id", mId)
                        putString("repayType", "DELAY")//展期还款
                    })
                }
            }
        }
    }
}