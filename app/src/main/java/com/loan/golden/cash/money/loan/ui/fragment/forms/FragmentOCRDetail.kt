package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.CarPologyParam
import com.loan.golden.cash.money.loan.data.response.OCRDetailResponse
import com.loan.golden.cash.money.loan.databinding.FragmentOcrDetailBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author      : hxw
 * @Date        : 2023/9/26 13:28
 * @Describe    : OCRDetail
 */
class FragmentOCRDetail : BaseFragment<ORCViewModel, FragmentOcrDetailBinding>() {

    private lateinit var mData: OCRDetailResponse

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Ocr Detail") {
            nav().navigateUp()
        }
        mData = arguments?.getParcelable(Constant.DATA)!!
        mBind.data = mData
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvOCRDetailSubmit, mBind.llDataOfBirth) {
            when (it) {
                mBind.tvOCRDetailSubmit -> {
                    val carParam = CarPologyParam(
                        CarPologyParam.Model(
                            idCard = mData.idCard,
                            realName = mData.realName,
                            taxRegNumber = mData.taxRegNumber,
                            birthDay = mData.birthDay,
                            idCardImageFront = mData.idCardImageFront,
                            idCardImageBack = mData.idCardImageBack,
                            idCardImagePan = mData.idCardImagePan
                        )
                    )
                    val strData = Gson().toJson(carParam)
                    val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    context?.let { it1 -> mViewModel.carpologyCallBack(paramsBody, it1) }
                }

                mBind.llDataOfBirth -> {
//                    val picker = BirthdayPicker(this)
//                    picker.setDefaultValue(1991, 11, 11)
//                    picker.setOnDatePickedListener(this)
//                    picker.getWheelLayout().setResetWhenLinkage(false)
//                    picker.show()
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 获取一个未完成的表单 */
        mViewModel.aesirResult.observe(viewLifecycleOwner) {
            var formType = ""
            if (it.model != null) {
                if (it.model!!.forms.isNotEmpty() || it.model!!.forms.size != 0) {
                    it.model!!.forms.forEachIndexed { _, _ ->
                        formType = it.model!!.forms[0].formType
                    }
                }
            }
            when (formType) {
                "OCR" -> {//证件识别
                    nav().navigateAction(R.id.action_to_fragment_orc_inspection)
                }

                "BASIC" -> {//基础信息
                    nav().navigateAction(R.id.action_to_fragment_basic_info)
                }

                "ALIVE" -> {//活体检测

                }

                "ALIVE_H5" -> {//活体检测H5

                }
            }
        }
    }
}