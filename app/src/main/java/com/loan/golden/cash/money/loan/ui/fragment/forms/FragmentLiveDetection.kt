package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.GlideEngine
import com.loan.golden.cash.money.loan.app.util.ImageLoaderManager
import com.loan.golden.cash.money.loan.app.util.ImgUtils
import com.loan.golden.cash.money.loan.app.util.MeSandboxFileEngine
import com.loan.golden.cash.money.loan.app.util.PictureUtil
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.LiveParam
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


/**
 *  author : huxiaowei
 *  date : 2023/10/9 22:39
 *  description : 活体检测
 */
class FragmentLiveDetection : BaseFragment<BasicFormsViewModel, FragmentLiveDetectionBinding>() {

    private var bitmap: Bitmap? = null

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.rlHeadPic, mBind.tvLiveSubmit) {
            when (it) {
                mBind.rlHeadPic -> {
                    uploadFacialPhotos()
                }

                mBind.tvLiveSubmit -> {
                    if (null == bitmap) {
                        RxToast.showToast("Please upload a facial photo first")
                        return@setOnclickNoRepeat
                    }
                    val resultBase64 = ImgUtils.byte2Base64(ImgUtils.bitmap2Byte(bitmap))
                    val body = LiveParam(
                        model = LiveParam.ModelBean(
                            faceInfo = resultBase64,
                            livenessType = "ACC_H5"
                        )
                    )
                    val gsonData = Gson().toJson(body)
                    val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                    context?.let { it1 -> mViewModel.ghettoizeCallBack(paramsBody, it1) }
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
                bitmap = BitmapFactory.decodeFile(mRealPath)
                ImageLoaderManager.loadCircleImage(context, bitmap, mBind.ivLiveHead)
            }

            override fun onCancel() {}
        })
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /**  */
        mViewModel.ghettoizeResult.observe(viewLifecycleOwner) {
            nav().navigateAction(R.id.action_to_fragment_product_list)
        }
    }
}