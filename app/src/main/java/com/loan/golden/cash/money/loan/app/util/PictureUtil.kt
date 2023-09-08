package com.loan.golden.cash.money.loan.app.util

import com.loan.golden.cash.money.loan.app.listener.ImageFileCropEngine
import com.loan.golden.cash.money.loan.app.listener.MeOnCameraInterceptListener
import com.loan.golden.cash.money.loan.app.listener.MeOnPermissionDeniedListener
import com.loan.golden.cash.money.loan.app.listener.MeOnPermissionDescriptionListener
import com.loan.golden.cash.money.loan.app.listener.MeOnPreviewInterceptListener
import com.luck.picture.lib.interfaces.OnCameraInterceptListener
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener
import com.luck.picture.lib.interfaces.OnPermissionDescriptionListener
import com.luck.picture.lib.interfaces.OnPreviewInterceptListener

/**
 * @Author      : hxw
 * @Date        : 2023/7/26 16:02
 * @Describe    :
 */
object PictureUtil {

    /**
     * 裁剪引擎
     *
     * @return
     */
    fun getCropFileEngine(): ImageFileCropEngine {
        return ImageFileCropEngine()
    }

    /**
     * 压缩引擎
     *
     * @return
     */
    fun getCompressFileEngine(): ImageFileCompressEngine {
        return ImageFileCompressEngine()
    }

    /**
     * 自定义相机事件
     *
     * @return
     */
    fun getCustomCameraEvent(): OnCameraInterceptListener {
        return MeOnCameraInterceptListener()
    }

    /**
     * 权限说明
     *
     * @return
     */
    fun getPermissionDescriptionListener(): OnPermissionDescriptionListener {
        return MeOnPermissionDescriptionListener()
    }

    /**
     * 自定义预览
     *
     * @return
     */
    fun getPreviewInterceptListener(): OnPreviewInterceptListener {
        return MeOnPreviewInterceptListener()
    }

    /**
     * 权限拒绝后回调
     *
     * @return
     */
    fun getPermissionDeniedListener(): OnPermissionDeniedListener {
        return MeOnPermissionDeniedListener()
    }

}