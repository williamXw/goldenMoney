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
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.param.FigeaterParam
import com.loan.golden.cash.money.loan.data.param.WorkParam
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
class FragmentWorkInfo : BaseFragment<BasicFormsViewModel, FragmentBasicInfoBinding>() {

    private var parentId = ""
    private var province = ""
    private var city = ""
    private var area = ""
    private var town = ""
    private var mFormId = ""
    private var mProfession = ""//行业
    private var mWorkType = ""

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
        mFormId = arguments?.getString("formId").toString()
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
        when {
            mBind.tvWorkInformationOccupation.text.toString().trim().isEmpty() -> RxToast.showToast(
                "Please select your industry"
            )

            mBind.etWorkInfoMonthIncome.text.toString().trim()
                .isEmpty() -> RxToast.showToast("Please fill in your income")

            mBind.etWorkInfoCompanyName.text.toString().trim()
                .isEmpty() -> RxToast.showToast("Please fill in your company name")

            mBind.tvWorkInformationCompanyAddress.text.toString().trim()
                .isEmpty() -> RxToast.showToast("Please select a company address")

            mBind.etWorkInfoDetailAddress.text.toString().trim()
                .isEmpty() -> RxToast.showToast("Please enter the company's detailed address")

            mBind.etWorkInfoCompanyPinCode.text.toString().trim()
                .isEmpty() -> RxToast.showToast("please enter Company PinCode")

            else -> {
                val body = WorkParam(
                    WorkParam.Model(
                        formId = mFormId,
                        submitData = WorkParam.Model.SubmitData(
                            professionInfo = WorkParam.Model.SubmitData.ProfessionInfo(
                                workType = mWorkType,
                                profession = mProfession
                            ),
                            monthIncome = mBind.etWorkInfoMonthIncome.text.toString().toInt(),
                            company = mBind.etWorkInfoCompanyName.text.toString().trim(),
                            companyAddress = WorkParam.Model.SubmitData.CompanyAddress(
                                bigAddress = WorkParam.Model.SubmitData.CompanyAddress.BigAddress(
                                    province = province,
                                    city = city
                                ),
                                detailAddress = mBind.etWorkInfoDetailAddress.text.toString().trim()
                            ),
                            companyPinCode = mBind.etWorkInfoCompanyPinCode.text.toString().trim()
                        )
                    )
                )
                val gsonData = Gson().toJson(body)
                val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                mViewModel.lustrationLustreCallBack(paramsBody)
            }
        }
    }

    private fun getCompanyAddress() {
        val body = FigeaterParam(parentId)
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.getFigeaterCallBack(paramsBody)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 提交表单 */
        mViewModel.lustreResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    getIncompleteForm()
//                    nav().navigateAction(R.id.action_to_fragment_personal_information, Bundle().apply {
//                        putString("formId", mFormId)
//                    })
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 获取地址信息 */
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
        /** 获取一个未完成的表单 */
        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            var formType = ""
            if (it.model!!.forms.isNotEmpty() || it.model!!.forms.size != 0) {
                it.model!!.forms.forEachIndexed { _, _ ->
                    formType = it.model!!.forms[0].columnField
                    mFormId = it.model!!.forms[0].formId
                }
            }
            when (formType) {
                "ocr" -> {//证件识别
//                        nav().navigateAction(R.id.action_to_fragment_repayment_mode)
                    nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                }

                "formPerson" -> {
                    nav().navigateAction(R.id.action_to_fragment_personal_information, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formWork" -> {//基础信息
                    nav().navigateAction(R.id.action_to_fragment_basic_info, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formEmergency" -> {
                    nav().navigateAction(R.id.action_to_fragment_contact_information, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "formBank" -> {

                }

                "live" -> {//活体检测

                }

                "ALIVE_H5" -> {//活体检测H5

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
            mWorkType = mAdapter.data[position].id
            showOccupationDialog(2)
            mAdapter2.setList(mAdapter.data[position].children)
            mAdapter2.notifyDataSetChanged()
        }
        mAdapter2.setOnItemClickListener { _, _, position ->
            mProfession = mAdapter2.data[position].id
            mBind.tvWorkInformationOccupation.text = mAdapter2.data[position].name
            dialogBottom.dismiss()
        }
        dialogBottom.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBottom.setContentView(dialogView)
        dialogBottom.show()
    }

    /** 获取一个未完成的表单 */
    private fun getIncompleteForm() {
        val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
        val strData = Gson().toJson(aeSirParam)
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        context?.let { it1 -> mViewModel.aesculinAesirCallBack(paramsBody, it1) }
    }
}