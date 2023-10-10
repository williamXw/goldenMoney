package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.graphics.BitmapFactory
import android.os.Bundle
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.GlideEngine
import com.loan.golden.cash.money.loan.app.util.ImageLoaderManager
import com.loan.golden.cash.money.loan.app.util.MeSandboxFileEngine
import com.loan.golden.cash.money.loan.app.util.PictureUtil
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.databinding.FragmentLiveDetectionBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureSelectorStyle


/**
 *  author : huxiaowei
 *  date : 2023/10/9 22:39
 *  description : 活体检测
 */
class FragmentLiveDetection : BaseFragment<BasicFormsViewModel, FragmentLiveDetectionBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.rlHeadPic) {
            when (it) {
                mBind.rlHeadPic -> {
                    uploadFacialPhotos()
                }
            }
        }
    }

    private fun uploadFacialPhotos() {
        val selectionModel: PictureSelectionModel = PictureSelector.create(context)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(PictureSelectorStyle())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setCropEngine(PictureUtil.getCropFileEngine())
            .setCompressEngine(PictureUtil.getCompressFileEngine())
            .setSandboxFileEngine(MeSandboxFileEngine())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setCameraInterceptListener(PictureUtil.getCustomCameraEvent())
            .setPermissionDescriptionListener(PictureUtil.getPermissionDescriptionListener())
            .setPreviewInterceptListener(PictureUtil.getPreviewInterceptListener())
            .setPermissionDeniedListener(PictureUtil.getPermissionDeniedListener())
            .setQueryFilterListener { false }
            .isLoopAutoVideoPlay(false)
            .isPageSyncAlbumCount(true)
            .setLanguage(LanguageConfig.SYSTEM_LANGUAGE)
            .setQuerySortOrder("")
            .isDisplayCamera(true)
            .isOpenClickSound(true)
            .isPreviewImage(true)
            .isMaxSelectEnabledMask(true)
            .isDirectReturnSingle(true)
        selectionModel.forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: ArrayList<LocalMedia>?) {
                val mRealPath = result!![0].realPath
                val bitmap = BitmapFactory.decodeFile(mRealPath)
                ImageLoaderManager.loadCircleImage(context, bitmap, mBind.ivLiveHead)
            }

            override fun onCancel() {}
        })
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
    }
}