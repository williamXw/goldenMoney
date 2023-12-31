package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.core.content.ContextCompat
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.permissions.PermissionInterceptor
import com.loan.golden.cash.money.loan.app.util.GlideEngine
import com.loan.golden.cash.money.loan.app.util.ImgUtils
import com.loan.golden.cash.money.loan.app.util.PictureUtil
import com.loan.golden.cash.money.loan.app.util.RxTextTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.response.OCRDetailResponse
import com.loan.golden.cash.money.loan.databinding.FragmentOrcInspectionBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.dialog.RxOcrErrorDialog
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureSelectorStyle
import java.io.File
import java.lang.ref.WeakReference

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
    private var mBirthDay: String = ""
    private var mPinCode: String = ""

    private val mHandler = MyHandler(WeakReference(this))

    private class MyHandler(val wrActivity: WeakReference<ORCInspectionFragment>) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            wrActivity.get()?.run {
                when (msg.what) {
                    WHAT -> {
                        mViewModel.streamStreambedCallBack(
                            msg.obj as File,
                            upLoadType,
                            context!!,
                            mBind.ivORCAadhaarFront,
                            mBind.ivORCAadhaarBack,
                            mBind.ivORCAadhaarPanFront
                        )
                    }

                    else -> {

                    }
                }
            }
        }
    }

    companion object {
        const val WHAT = 100
    }

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
                    val ocrData = OCRDetailResponse(
                        idCard = mIdCard,
                        realName = mRealName,
                        taxRegNumber = mTaxRegNumber,
                        birthDay = mBirthDay,
                        pinCode = mPinCode,
                        idCardImageFront = mIdCardImageFront,
                        idCardImageBack = mIdCardImageBack,
                        idCardImagePan = mIdCardImagePan
                    )
                    nav().navigateAction(R.id.action_to_fragment_ocr_detail, Bundle().apply {
                        putParcelable(Constant.DATA, ocrData)
                    })
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
                    RxToast.showToast("Insufficient permissions. Please manually enable permissions and try again")
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
                    mHandler.sendMessageDelayed(Message.obtain().apply {
                        what = WHAT
                        obj = fileCompress
                    }, 300)
                }.start()
            }

            override fun onCancel() {}
        })
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 上传文件 */
        mViewModel.ocrImageResult.observe(viewLifecycleOwner) {
            when (upLoadType) {
                1 -> {
                    mIdCardImageFront = it.model!!.ossUrl
                }

                2 -> {
                    mIdCardImageBack = it.model!!.ossUrl
                }

                3 -> {
                    mIdCardImagePan = it.model!!.ossUrl
                }
            }
        }
        /** 证件识别 */
        mViewModel.diamanResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    when (upLoadType) {
                        1 -> {
                            mIdCard = it.model!!.idCard
                            mRealName = it.model.realName
                            val result = it.model.birthDay.toString()
                            if (result.isNotEmpty() && result.startsWith("-")) {
                                mBirthDay = result.substring(1, result.length)
                            }
                            isUpLoadSuccess1 = true
                        }

                        2 -> {
                            mPinCode = it.model!!.pinCode
                            isUpLoadSuccess2 = true
                        }

                        3 -> {
                            mTaxRegNumber = it.model!!.taxRegNumber
                            isUpLoadSuccess3 = true
                        }
                    }
                    RxToast.showToast("Upload successful")
                }

                else -> {
                    RxToast.showToast(it.message)
                    showOCRErrorDialog()
                }
            }
        }
    }

    private fun showOCRErrorDialog() {
        val dialog = RxOcrErrorDialog(context)
        dialog.setFullScreenWidth()
        dialog.show()
    }
}