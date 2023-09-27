package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
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
import com.loan.golden.cash.money.loan.data.param.FigeaterParam
import com.loan.golden.cash.money.loan.databinding.FragmentBasicInfoBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.FigeaterAdapter
import com.loan.golden.cash.money.loan.ui.adapter.OccupationAdapter
import com.loan.golden.cash.money.loan.ui.adapter.OccupationAdapter2
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.getColorExt
import me.hgj.mvvmhelper.ext.vertical
import me.hgj.mvvmhelper.util.decoration.DividerOrientation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 *  author : huxiaowei
 *  date : 2023/9/25 20:56Ø
 *  description : Work information
 */
class FragmentBasicInfo : BaseFragment<BasicFormsViewModel, FragmentBasicInfoBinding>() {

    private var parentId = ""
    private var province = ""
    private var city = ""
    private var area = ""
    private var town = ""
    private var mFormId = ""

    private lateinit var dialogBottom: BottomSheetDialog
    private lateinit var dialogFigeaterBottom: BottomSheetDialog

    /** 选择工作类型 */
    private val mAdapter: OccupationAdapter by lazy { OccupationAdapter(arrayListOf()) }
    private val mAdapter2: OccupationAdapter2 by lazy { OccupationAdapter2(arrayListOf()) }

    /** 选择省市区 */
    private val mAdapterFigeater: FigeaterAdapter by lazy { FigeaterAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Work information") {
            nav().navigateUp()
        }
        mFormId = arguments?.getString("mFormId").toString()
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(
            mBind.llWorkInfoOccupation,
            mBind.tvBasicInfoSubmit,
            mBind.llWorkInfoCompanyAddress
        ) {
            when (it) {
                mBind.tvBasicInfoSubmit -> {
                    submitFormsData()
                    nav().navigateAction(R.id.action_to_fragment_personal_information)
                }

                mBind.llWorkInfoOccupation -> {
                    mViewModel.kaliCallBack()
                }

                mBind.llWorkInfoCompanyAddress -> {
                    parentId = ""
                    getCompanyAddress()
                }
            }
        }
    }

    private fun submitFormsData() {

    }

    private fun getCompanyAddress() {
        val body = FigeaterParam(parentId)
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY)
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.getFigeaterCallBack(paramsBody)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.figeaterResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    showFigeaterDialog()
                    mAdapterFigeater.setList(it.model)
                    mAdapterFigeater.notifyDataSetChanged()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 获取工作岗位信息 */
        mViewModel.kaliResponseResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    showOccupationDialog(1)
                    mAdapter.setList(it.model)
                    mAdapter.notifyDataSetChanged()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun showFigeaterDialog() {
        dialogFigeaterBottom = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }!!
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.bottom_figeater_view, null)
        val swipeRecyclerView = dialogView.findViewById<SwipeRecyclerView>(R.id.swipeRecyclerView)
        swipeRecyclerView.run {
            vertical()
            divider {
                //分割线颜色
                setColor(getColorExt(R.color.colorBgGray_EBEBEB))
                //分割线高度
                setDivider(1)
                //是否首尾都有分割线
                includeVisible = false
                //分割线方向
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapterFigeater
        }
        mAdapterFigeater.setOnItemClickListener { _, _, position ->
            val mType = mAdapterFigeater.data[position].type
            when (mType) {
                "PROVINCES" -> {
                    province = mAdapterFigeater.data[position].name
                }

                "REGENCIES" -> {
                    city = mAdapterFigeater.data[position].name
                }

                "DISTRICTS" -> {
                    area = mAdapterFigeater.data[position].name
                }

                "VILLAGES" -> {
                    town = mAdapterFigeater.data[position].name
                }
            }
            if (mAdapterFigeater.data[position].haveChild) {
                dialogFigeaterBottom.dismiss()
                parentId = if (mType == "PROVINCES") {
                    mAdapterFigeater.data[position].id
                } else {
                    mAdapterFigeater.data[position].parentId
                }
                getCompanyAddress()
            } else {
                dialogFigeaterBottom.dismiss()
                mBind.tvWorkInformationCompanyAddress.text = "$province $city $area $town"
            }
        }
        dialogFigeaterBottom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogFigeaterBottom.setContentView(dialogView)
        dialogFigeaterBottom.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showOccupationDialog(it: Int) {
        dialogBottom = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }!!
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.bottom_occupation, null)
        val swipeRecyclerView = dialogView.findViewById<SwipeRecyclerView>(R.id.swipeRecyclerView)
        swipeRecyclerView.run {
            vertical()
            divider {
                //分割线颜色
                setColor(getColorExt(R.color.colorBgGray_EBEBEB))
                //分割线高度
                setDivider(1)
                //是否首尾都有分割线
                includeVisible = false
                //分割线方向
                orientation = DividerOrientation.VERTICAL
            }
            //设置适配器
            when (it) {
                1 -> {
                    adapter = mAdapter
                }

                2 -> {
                    adapter = mAdapter2
                }
            }
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            dialogBottom.dismiss()
            showOccupationDialog(2)
            mAdapter2.setList(mAdapter.data[position].children)
            mAdapter2.notifyDataSetChanged()
        }
        mAdapter2.setOnItemClickListener { _, _, position ->
            mBind.tvWorkInformationOccupation.text = mAdapter2.data[position].name
            dialogBottom.dismiss()
        }
        dialogBottom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBottom.setContentView(dialogView)
        dialogBottom.show()
    }

}