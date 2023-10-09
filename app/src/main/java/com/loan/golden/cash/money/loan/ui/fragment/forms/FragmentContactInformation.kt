package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.fastjson.JSONObject
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
import com.loan.golden.cash.money.loan.data.param.CharlottetownParam
import com.loan.golden.cash.money.loan.data.param.ContactParam
import com.loan.golden.cash.money.loan.databinding.FragmentContactInformationBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.adapter.ContactAdapter
import com.loan.golden.cash.money.loan.ui.adapter.FigeaterAdapter
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.SinglePicker
import me.hgj.mvvmhelper.ext.divider
import me.hgj.mvvmhelper.ext.getColorExt
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

    private var selectType = -1
    private var mFormId = ""
    private var contactJSonStr = ""
    private lateinit var mPicker: SinglePicker
    private var mContactIndex1 = -1
    private var mContactIndex2 = -1
    private var mContactId1 = ""
    private var mContactId2 = ""
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
                //分割线颜色
//                setColor(getColorExt(R.color.colorTran))
                //分割线高度
//                setDivider(1)
                //是否首尾都有分割线
//                includeVisible = false
                //分割线方向
                orientation = DividerOrientation.VERTICAL
            }
            adapter = mAdapter
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(
            mBind.tvContactInfoSubmit,
            mBind.llContactInfoRelationship,
            mBind.llContactInfoRelationship2
        ) {
            when (it) {
                mBind.tvContactInfoSubmit -> {
                    submitContactInfo()
                }

                mBind.llContactInfoRelationship -> {
                    selectType = 1
                    selectedPicker(selectType, contactJSonStr)
                }

                mBind.llContactInfoRelationship2 -> {
                    selectType = 2
                    selectedPicker(selectType, contactJSonStr)
                }
            }
        }
    }

    private fun submitContactInfo() {
        val contactList: ArrayList<ContactParam.ModelBean.SubmitDataBean.UserEmergsBean> = arrayListOf()
        contactList.add(ContactParam.ModelBean.SubmitDataBean.UserEmergsBean(name = ""))
        val contact = ContactParam(
            ContactParam.ModelBean(
                formId = mFormId,
                submitData = ContactParam.ModelBean.SubmitDataBean(
                    userEmergs = contactList
                )
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRequestSuccess() {
        super.onRequestSuccess()
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
    }

    private fun selectedPicker(type: Int, jsonStr: String) {
        if (jsonStr.isEmpty()) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> SinglePicker(it1, jsonStr) }!!
        when (type) {
            1 -> {
                mPicker.setDefaultPosition(mContactIndex1)
            }

            2 -> {
                mPicker.setDefaultPosition(mContactIndex2)
            }
        }
        mPicker.setOnOptionPickedListener(this)
        mPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
            mPicker.titleView.text = mPicker.wheelView.formatItem(position)
        }
        mPicker.show()
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        when (selectType) {
            1 -> {
                mContactId1 = (item as MaritalEntity).id
                mContactIndex1 = position
                mBind.tvContactInfoRelationship.text = mPicker.wheelView.formatItem(position)
            }

            2 -> {
                mContactId2 = (item as MaritalEntity).id
                mContactIndex2 = position
                mBind.tvContactInfoRelationship2.text = mPicker.wheelView.formatItem(position)
            }
        }
    }
}