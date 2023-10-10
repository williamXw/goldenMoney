package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.NapperParam
import com.loan.golden.cash.money.loan.databinding.FragmentProductListBinding
import com.loan.golden.cash.money.loan.ui.adapter.NapperAdapter
import com.loan.golden.cash.money.loan.ui.dialog.RxDialogProductList
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import me.hgj.mvvmhelper.ext.divider
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

    private val mAdapter: NapperAdapter by lazy { NapperAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Product List") { nav().navigateUp() }

        getNapperList()
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
    }

    private fun getNapperList() {
        val body = NapperParam(
            query = NapperParam.QueryBean()
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        context?.let { mViewModel.napperCallBack(paramsBody, it) }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvProductListSubmit) {
            when (it) {
                mBind.tvProductListSubmit -> {
                    val dialog = RxDialogProductList(context)

                    dialog.setFullScreenWidth()
                    dialog.show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.napperResult.observe(viewLifecycleOwner) {
            mAdapter.setList(it.page?.content)
            mAdapter.notifyDataSetChanged()
        }
    }
}