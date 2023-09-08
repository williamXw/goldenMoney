package com.loan.golden.cash.money.loan.ui.fragment.ocr

import android.graphics.BitmapFactory
import android.os.Bundle
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
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
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

/**
 * @Author      : hxw
 * @Date        : 2023/9/8 11:09
 * @Describe    : OCRInspection
 */
class ORCInspectionFragment : BaseFragment<ORCViewModel, FragmentOrcInspectionBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("ORC Inspection") {
            nav().navigateUp()
        }
        context?.let { ContextCompat.getColor(it, R.color.colorWhite) }?.let { mBind.customToolbar.setCenterTitleColor(it) }

        context?.let { ContextCompat.getColor(it, R.color.color_FFF733) }?.let {
            RxTextTool.getBuilder("*Please Upload A Clear Photo Of Your ID Card HereWhich Willlncrease The Success Rate of")
                .append(" Your Loan Application From 20% To30%").setTextColor(it)
                .into(mBind.tvORCContent)
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llFront) {
            when (it) {
                mBind.llFront -> {
                    requestPermission()
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
                val bitmapCompress = ImgUtils.compressByQuality(bitmap, 500)
                val fileCompress = ImgUtils.saveBitmapFile(bitmapCompress)
                mBind.ivORCAadhaarFront.setImageBitmap(bitmap)
                //请求接口
            }

            override fun onCancel() {}
        })
    }
}