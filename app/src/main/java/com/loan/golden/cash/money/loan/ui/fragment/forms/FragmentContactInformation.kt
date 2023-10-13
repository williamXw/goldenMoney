package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.fastjson.JSONObject
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
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.data.param.ContactParam
import com.loan.golden.cash.money.loan.databinding.FragmentContactInformationBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.ContactAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.SinglePicker
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.showLoadingExt
import me.hgj.mvvmhelper.ext.vertical
import me.hgj.mvvmhelper.util.decoration.DividerOrientation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:45
 * @Describe    : ContactInformation
 */
class FragmentContactInformation : BaseFragment<BasicFormsViewModel, FragmentContactInformationBinding>(), OnOptionPickedListener {

    private var selectType = 0
    private var mFormId = ""
    private var contactJSonStr = ""
    private lateinit var mPicker: SinglePicker
    private val mAdapter: ContactAdapter by lazy { ContactAdapter(arrayListOf()) }
    private var mList: ArrayList<Int> = arrayListOf()

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Contact information") { nav().navigateUp() }
        mFormId = arguments?.getString("formId").toString()

        /** 获取指定表单 */
        val body = CharlottetownParam(CharlottetownParam.Model(formId = mFormId))
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        showLoadingExt("loading....")
        mViewModel.lottetownCallBack(paramsBody)

        mBind.swipeRecyclerView.run {
            vertical()
            divider {
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapter
        }
        mAdapter.addChildClickViewIds(R.id.llContactInfoRelationship)
        mAdapter.setOnItemChildClickListener { _, _, position ->
            selectType = position
            selectedPicker()
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvContactInfoSubmit) {
            when (it) {
                mBind.tvContactInfoSubmit -> {
                    submitContactInfo()
                }
            }
        }
    }

    private fun submitContactInfo() {
        val contactList: ArrayList<ContactParam.ModelBean.SubmitDataBean.UserEmergsBean> = arrayListOf()
        mAdapter.data.forEachIndexed { index, _ ->
            val etName = mAdapter.getViewByPosition(index, R.id.etContactInfoName) as AppCompatEditText
            val etPhone = mAdapter.getViewByPosition(index, R.id.etContactInfoPhone) as AppCompatEditText
            val tvRelationshipId = mAdapter.getViewByPosition(index, R.id.tvContactInfoRelationshipId) as AppCompatTextView
            contactList.add(
                ContactParam.ModelBean.SubmitDataBean.UserEmergsBean
                    (
                    name = etName.text.toString().trim(),
                    phone = etPhone.text.toString().trim(),
                    relation = tvRelationshipId.text.toString().trim()
                )
            )
        }
        val body = ContactParam(
            ContactParam.ModelBean(
                formId = mFormId,
                submitData = ContactParam.ModelBean.SubmitDataBean(
                    userEmergs = contactList
                )
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.lustrationLustreCallBack(paramsBody)
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
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 获取指定表单 */
        mViewModel.lottetownResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    var count = 0
                    if (it?.model != null && it.model!!.forms.isNotEmpty() && it.model!!.forms[0].content.isNotEmpty()) {
                        it.model?.forms?.get(0)?.content?.forEachIndexed { _, contentBean ->
                            if (contentBean.name == "Contact Person") {
                                contactJSonStr = JSONObject.toJSONString(contentBean.props!!.relationList)
                                count = contentBean.props!!.fieldConf!!.count
                            }
                        }
                    }
                    for (index in 0 until count) {
                        mList.add(index)
                    }
                    mAdapter.setList(mList)
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
            }else{
                nav().navigateAction(R.id.action_to_fragment_product_list)
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
                    nav().navigateAction(R.id.action_to_fragment_bank_info, Bundle().apply {
                        putString("formId", mFormId)
                    })
                }

                "live" -> {//活体检测
                    nav().navigateAction(R.id.action_to_fragment_live_detection)
                }

                "ALIVE_H5" -> {//活体检测H5

                }
            }
        }
    }

    /** 获取一个未完成的表单 */
    private fun getIncompleteForm() {
        val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
        val strData = Gson().toJson(aeSirParam)
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        context?.let { it1 -> mViewModel.aesculinAesirCallBack(paramsBody, it1) }
    }

    private fun selectedPicker() {
        if (contactJSonStr.isEmpty()) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> SinglePicker(it1, contactJSonStr) }!!
        mPicker.setOnOptionPickedListener(this)
        mPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
            mPicker.titleView.text = mPicker.wheelView.formatItem(position)
        }
        mPicker.show()
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        val tvContactInfoRelationship = mAdapter.getViewByPosition(selectType,R.id.tvContactInfoRelationship) as AppCompatTextView
        tvContactInfoRelationship.text = mPicker.wheelView.formatItem(position)
        val tvContactInfoRelationshipId = mAdapter.getViewByPosition(selectType,R.id.tvContactInfoRelationshipId) as AppCompatTextView
        tvContactInfoRelationshipId.text = (item as MaritalEntity).id
    }
}