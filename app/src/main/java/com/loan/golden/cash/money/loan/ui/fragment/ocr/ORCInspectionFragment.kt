package com.loan.golden.cash.money.loan.ui.fragment.ocr

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.permissions.PermissionInterceptor
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.GlideEngine
import com.loan.golden.cash.money.loan.app.util.ImageLoaderManager
import com.loan.golden.cash.money.loan.app.util.ImgUtils
import com.loan.golden.cash.money.loan.app.util.PictureUtil
import com.loan.golden.cash.money.loan.app.util.RxTextTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AesirParam
import com.loan.golden.cash.money.loan.data.param.CarPologyParam
import com.loan.golden.cash.money.loan.data.param.DiamantiferousParam
import com.loan.golden.cash.money.loan.data.response.AesirResponse
import com.loan.golden.cash.money.loan.data.response.DiamantiferousResponse
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import com.loan.golden.cash.money.loan.databinding.FragmentOrcInspectionBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureSelectorStyle
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 11:09
 * @Describe    : OCRInspection
 */
class ORCInspectionFragment : BaseFragment<ORCViewModel, FragmentOrcInspectionBinding>() {

    private var upLoadType = -1
    private var isUpLoadSuccess1 = false
    private var isUpLoadSuccess2 = false
    private var isUpLoadSuccess3 = false
    private var mIdCardImageFront: String = ""
    private var mIdCardImageBack: String = ""
    private var mIdCardImagePan: String = ""
    private var mIdCard: String = ""
    private var mRealName: String = ""
    private var mTaxRegNumber: String = ""
    private var mCardType: String = ""
    private var mBirthDay: String = ""

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("ORC Inspection") {
            nav().navigateUp()
        }
        context?.let { ContextCompat.getColor(it, R.color.colorWhite) }
            ?.let { mBind.customToolbar.setCenterTitleColor(it) }

        context?.let { ContextCompat.getColor(it, R.color.color_FFF733) }?.let {
            RxTextTool.getBuilder("*Please Upload A Clear Photo Of Your ID Card HereWhich Willlncrease The Success Rate of")
                .append(" Your Loan Application From 20% To30%").setTextColor(it)
                .into(mBind.tvORCContent)
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llFront, mBind.llBack, mBind.llPanCardFront, mBind.tvORCContinue) {
            when (it) {
                mBind.llFront -> {
                    upLoadType = 1
                    requestPermission()
                }

                mBind.llBack -> {
                    upLoadType = 2
                    requestPermission()
                }

                mBind.llPanCardFront -> {
                    upLoadType = 3
                    requestPermission()
                }

                mBind.tvORCContinue -> {
                    if (!isUpLoadSuccess1 || !isUpLoadSuccess2 || !isUpLoadSuccess3) {
                        RxToast.showToast("Please complete the certification")
                        return@setOnclickNoRepeat
                    }

                    val carParam = CarPologyParam(
                        CarPologyParam.Model(
                            idCard = mIdCard,
                            realName = mRealName,
                            taxRegNumber = mTaxRegNumber,
                            birthDay = mBirthDay,
                            idCardImageFront = mIdCardImageFront,
                            idCardImageBack = mIdCardImageBack,
                            idCardImagePan = mIdCardImagePan
                        )
                    )
                    val strData = Gson().toJson(carParam)
                    val paramsBody =
                        AESTool.encrypt1(strData, Constant.AES_KEY)
                            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    mViewModel.carpologyCallBack(paramsBody)
                }
            }
        }
    }

    private fun requestPermission() {
        XXPermissions.with(this)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .interceptor(PermissionInterceptor())
            .request { _, allGranted ->
                if (allGranted) {
                    selectImage()
                } else {
                    RxToast.showToast("权限不足，请手动开启权限后重试")
                }
            }
    }

    private fun selectImage() {
        val selectionModel: PictureSelectionModel = PictureSelector.create(context)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(PictureSelectorStyle())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setCameraInterceptListener(PictureUtil.getCustomCameraEvent())
            .setCompressEngine(PictureUtil.getCompressFileEngine())
            .isLoopAutoVideoPlay(false)
            .isPageSyncAlbumCount(true)
            .setLanguage(LanguageConfig.SYSTEM_LANGUAGE)
            .setQuerySortOrder("")
            .isDisplayCamera(true)
            .isOpenClickSound(true)
            .isPreviewImage(false)
            .isMaxSelectEnabledMask(false)
            .isDirectReturnSingle(false)
            .setMaxSelectNum(1)
            .isDirectReturnSingle(true)
        selectionModel.forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: java.util.ArrayList<LocalMedia>?) {
                val mRealPath = result!![0].realPath
                /** 方案1：不压缩图片，直接上传 */
//                val imageFile = File(mRealPath)
                /** 方案2：压缩图片后上传 */
                val bitmap = BitmapFactory.decodeFile(mRealPath)
                /** 循环压缩图片 耗时任务  在子线程中运行 */
                Thread {
                    val bitmapCompress = ImgUtils.compressByQuality(bitmap, 100)
                    val fileCompress = ImgUtils.saveBitmapFile(bitmapCompress)
                    mViewModel.streamStreambedCallBack(fileCompress)
                }.start()
            }

            override fun onCancel() {}
        })
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.ocrUpLoadResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                var dataBody = it.body!!.string()
                if (dataBody.startsWith('"')) {
                    dataBody = dataBody.substring(1, dataBody.length)
                }
                if (dataBody.endsWith('"')) {
                    dataBody = dataBody.substring(0, dataBody.length - 1)
                }
                if (dataBody.startsWith('"') && dataBody.endsWith('"')) {
                    dataBody = dataBody.substring(1, dataBody.length - 1)
                }
                if (dataBody.isEmpty()) {
                    RxToast.showToast("data is empty")
                    return@observe
                }
                val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                val gson = Gson()
                val ocrData: OCRResponse = gson.fromJson(mResponse, OCRResponse::class.java)
                if (ocrData.status == 0) {
                    if (ocrData.model != null) {
                        when (upLoadType) {
                            1 -> {
                                mCardType = "FRONT"
                                mIdCardImageFront = ocrData.model.ossUrl
                                diamantiferous(mIdCardImageFront, "FRONT")
                                isUpLoadSuccess1 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    mIdCardImageFront,
                                    mBind.ivORCAadhaarFront,
                                    12
                                )
                            }

                            2 -> {
                                mCardType = "BACK"
                                mIdCardImageBack = ocrData.model.ossUrl
                                diamantiferous(mIdCardImageBack, "BACK")
                                isUpLoadSuccess2 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    mIdCardImageBack,
                                    mBind.ivORCAadhaarBack,
                                    12
                                )
                            }

                            3 -> {
                                mCardType = "PAN"
                                mIdCardImagePan = ocrData.model.ossUrl
                                diamantiferous(mIdCardImagePan, "PAN")
                                isUpLoadSuccess3 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    mIdCardImagePan,
                                    mBind.ivORCAadhaarPanFront,
                                    12
                                )
                            }
                        }
                    }
                } else {
                    val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                    RxToast.showToast(msg)
                }
            }
        }
        mViewModel.diamantiferousResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val mData: DiamantiferousResponse =
                        gson.fromJson(mResponse, DiamantiferousResponse::class.java)
                    if (mData.status == 1012) {
                        startActivity<LoginActivity>()
                        return@observe
                    }
                    if (mData.status == 0) {
                        if (mCardType == "FRONT") {
                            mIdCard = mData.model!!.idCard
                            mRealName = mData.model.realName
                            val result = mData.model.birthDay.toString()
                            if (result.isNotEmpty() && result.startsWith("-")) {
                                mBirthDay = result.substring(1, result.length)
                            }
                        }
                        if (mCardType == "PAN") {
                            mTaxRegNumber = mData.model!!.taxRegNumber
                        }
                        RxToast.showToast("upLoad Success")
                    } else {
                        val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                        RxToast.showToast(msg)
                    }
                }
            }
        }

        mViewModel.carpologyResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val mData: AesirResponse? = gson.fromJson(mResponse, AesirResponse::class.java)
                    if (mData != null) {
                        if (mData.status == 1012) {
                            startActivity<LoginActivity>()
                            return@observe
                        }
                        if (mData.status == 0) {
                            val aeSirParam = AesirParam(AesirParam.Model("NODE1"))
                            val strData = Gson().toJson(aeSirParam)
                            val paramsBody =
                                AESTool.encrypt1(strData, Constant.AES_KEY)
                                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                            mViewModel.aesculinAesirCallBack(paramsBody)
                        } else {
                            val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                            RxToast.showToast(msg)
                        }
                    }
                }
            }
        }

        mViewModel.aesculinAesirResult.observe(viewLifecycleOwner) {
            if (it.code == 200) {
                var formType = ""
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val aesirResponse: AesirResponse? =
                        gson.fromJson(mResponse, AesirResponse::class.java)
                    if (aesirResponse != null) {
                        if (aesirResponse.status == 1012) {
                            startActivity<LoginActivity>()
                            return@observe
                        }
                        if (aesirResponse.status == 0 && aesirResponse.model != null) {
                            if (aesirResponse.model!!.forms.isNotEmpty() || aesirResponse.model!!.forms.size != 0) {
                                aesirResponse.model!!.forms.forEachIndexed { _, _ ->
                                    formType = aesirResponse.model!!.forms[0].formType
                                }
                            }
                        } else {
                            val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                            RxToast.showToast(msg)
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

    /** 证件识别 */
    private fun diamantiferous(url: String, cardType: String) {
        val body = DiamantiferousParam(
            DiamantiferousParam.Model(
                url = url,
                cardType = cardType
            )
        )
        val strData = Gson().toJson(body)
        val paramsBody =
            AESTool.encrypt1(strData, Constant.AES_KEY)
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.diamantiferousCallBack(paramsBody)
    }
}