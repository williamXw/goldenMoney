package com.loan.golden.cash.money.loan.app.listener

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.interfaces.OnCameraInterceptListener

/**
 * @Author      : hxw
 * @Date        : 2023/3/27 11:52
 * @Describe    :
 */
class MeOnCameraInterceptListener : OnCameraInterceptListener {

    override fun openCamera(fragment: Fragment, cameraMode: Int, requestCode: Int) {
        val camera: SimpleCameraX = SimpleCameraX.of()
        camera.isAutoRotation(true)
        camera.setCameraMode(cameraMode)
        camera.setVideoFrameRate(25)
        camera.setVideoBitRate(3 * 1024 * 1024)
        camera.isDisplayRecordChangeTime(true)
        camera.isManualFocusCameraPreview(true)
        camera.isZoomCameraPreview(true)
        camera.setOutputPathDir("")
        camera.setPermissionDeniedListener(null)
        camera.setPermissionDescriptionListener(null)
        camera.setImageEngine { context, url, imageView -> Glide.with(context!!).load(url).into(imageView!!) }
        camera.start(fragment.requireActivity(), fragment, requestCode)
    }
}