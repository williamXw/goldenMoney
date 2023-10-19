package com.loan.golden.cash.money.loan.ui.fragment.repayment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.init
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.ChippewaChippieParam
import com.loan.golden.cash.money.loan.data.param.OrogenyParam
import com.loan.golden.cash.money.loan.databinding.FragmentRepaymentModeBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.RepaymentAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel
import me.hgj.mvvmhelper.ext.dismissLoadingExt
import me.hgj.mvvmhelper.ext.showLoadingExt
import me.hgj.mvvmhelper.util.decoration.SpaceItemDecoration
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 13:57
 * @Describe    : RepaymentMode
 */
class RepaymentModeFragment : BaseFragment<RepaymentViewModel, FragmentRepaymentModeBinding>() {

    private val mAdapter: RepaymentAdapter by lazy { RepaymentAdapter(arrayListOf()) }
    private var mPosition = -1
    private var mId = ""
    private var mRepayType = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Repayment Mode") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()
        mRepayType = arguments?.getString("repayType").toString()
        refreshData(mId)
        initAdapter()
    }

    private fun initAdapter() {
        mBind.swipeRecyclerView.init(LinearLayoutManager(context), mAdapter).addItemDecoration(SpaceItemDecoration(0, 0))

        mAdapter.setOnItemClickListener { _, _, position ->
            mPosition = position
            mAdapter.setItemSelected(position)
            chippewaChippie(position)
        }
    }

    /** 获取订单还款链接/还款码 */
    private fun chippewaChippie(position: Int) {
        val body = ChippewaChippieParam(
            ChippewaChippieParam.ModelBean(
                orderId = mId,
                repayMethod = mAdapter.data[position].repayMethod,
                methodCode = mAdapter.data[position].methodCode,
                repayType = mRepayType
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.chippewaChippieCallBack(paramsBody)
    }

    private fun refreshData(id: String) {
        val body = OrogenyParam(
            OrogenyParam.ModelBean(
                orderId = id
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading.....")
        mViewModel.orogenicsOrogenyCallBack(paramsBody)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 获取订单还款链接/还款码 */
        mViewModel.chippieResult.observe(viewLifecycleOwner) {
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
        /** 获取订单还款方式列表 */
        mViewModel.orogenyResult.observe(viewLifecycleOwner) {
            dismissLoadingExt()
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    mAdapter.setList(it.model?.methods)
                    mAdapter.notifyDataSetChanged()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }
}