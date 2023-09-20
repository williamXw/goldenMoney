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
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.response.OCRResponse
import com.loan.golden.cash.money.loan.databinding.FragmentOrcInspectionBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.ORCViewModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureSelectorStyle
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
                val dataBody = it.body!!.string()
                val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                val gson = Gson()
                val ocrData: OCRResponse = gson.fromJson(mResponse, OCRResponse::class.java)
                if (ocrData.status == 0) {
                    if (ocrData.model != null) {
                        when (upLoadType) {
                            1 -> {
                                isUpLoadSuccess1 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    ocrData.model.ossUrl,
                                    mBind.ivORCAadhaarFront,
                                    12
                                )
                            }

                            2 -> {
                                isUpLoadSuccess2 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    ocrData.model.ossUrl,
                                    mBind.ivORCAadhaarBack,
                                    12
                                )
                            }

                            3 -> {
                                isUpLoadSuccess3 = true
                                ImageLoaderManager.loadRoundImage(
                                    context,
                                    ocrData.model.ossUrl,
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
    }
}