package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.NapperParam
import com.loan.golden.cash.money.loan.data.param.TrigonParam
import com.loan.golden.cash.money.loan.databinding.FragmentProductListBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.NapperAdapter
import com.loan.golden.cash.money.loan.ui.dialog.RxDialogProductList
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.dismissLoadingExt
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.showLoadingExt
import me.hgj.mvvmhelper.ext.vertical
import me.hgj.mvvmhelper.util.decoration.DividerOrientation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/7 13:38
 * @Describe    : ProductList
 */
class ProductListFragment : BaseFragment<MineViewModel, FragmentProductListBinding>() {

    private lateinit var dialog: RxDialogProductList
    private val mAdapter: NapperAdapter by lazy { NapperAdapter(arrayListOf()) }
    private var mPosition = -1

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Product List") { nav().navigateUp() }

        getNapperList()
        initAdapter()
        mBind.includedList.swipeRefreshLayout.setOnRefreshListener {
            getNapperList()
        }
    }

    private fun initAdapter() {
        mBind.includedList.swipeRecyclerView.run {
            vertical()
            divider {
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            mPosition = position
            mAdapter.setItemSelected(position)
        }
    }

    private fun getNapperList() {
        showLoadingExt("loading.....")
        val body = NapperParam(
            query = NapperParam.QueryBean()
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        context?.let { mViewModel.napperCallBack(paramsBody, it) }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvProductListSubmit) {
            when (it) {
                mBind.tvProductListSubmit -> {
                    showProductDialog()
                }
            }
        }
    }

    private fun showProductDialog() {
        if (mPosition == -1) {
            RxToast.showToast("Please select a product")
            return
        }
        dialog = RxDialogProductList(context)
        dialog.setLoanData(mAdapter.data[mPosition])
        val tvDialogSubmit = dialog.findViewById<AppCompatTextView>(R.id.tvDialogSubmit)
        tvDialogSubmit.setOnClickListener {
            loanApplication()
        }
        dialog.setFullScreenWidth()
        dialog.show()
    }

    private fun loanApplication() {
        val idList: ArrayList<String> = arrayListOf()
        idList.add(mAdapter.data[mPosition].id)
        val body = TrigonParam(
            model = TrigonParam.ModelBean(
                productIds = idList
            )
        )
        val strData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.loanApplicationCallBack(paramsBody)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.apologiaResult.observe(viewLifecycleOwner) {
            when (it.status) {
                0 -> {
                    if (::dialog.isInitialized) {
                        dialog.dismiss()
                    }
                }

                1012 -> {
                    startActivity<LoginActivity>()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 产品手续费试算 */
        mViewModel.trigonResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 产品信息列表  */
        mViewModel.napperResult.observe(viewLifecycleOwner) {
            mPosition = -1
            dismissLoadingExt()
            if (mBind.includedList.swipeRefreshLayout.isRefreshing) {
                mBind.includedList.swipeRefreshLayout.isRefreshing = false
            }
            mAdapter.setList(it.page?.content)
            mAdapter.notifyDataSetChanged()
        }
    }
}