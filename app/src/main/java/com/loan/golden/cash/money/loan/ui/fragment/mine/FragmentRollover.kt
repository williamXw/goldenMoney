package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.OrogenyParam
import com.loan.golden.cash.money.loan.databinding.FragmentRolloverBinding
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
}