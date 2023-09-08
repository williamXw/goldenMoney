package com.loan.golden.cash.money.loan.app.listener

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.util.ImageLoaderUtils
import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.StyleUtils
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine
import java.io.File

/**
 * @Author      : hxw
 * @Date        : 2023/3/27 15:20
 * @Describe    :
 */
class ImageFileCropEngine : CropFileEngine {

    private val aspect_ratio_x = 1
    private var aspect_ratio_y = 1

    override fun onStartCrop(fragment: Fragment?, srcUri: Uri?, destinationUri: Uri?, dataSource: ArrayList<String>?, requestCode: Int) {
        val options: UCrop.Options = fragment?.let { buildOptions(it.requireContext()) }!!
        val uCrop: UCrop = destinationUri?.let { srcUri?.let { it1 -> UCrop.of(it1, it, dataSource) } }!!
        uCrop.withOptions(options)
        uCrop.setImageEngine(object : UCropImageEngine {
            override fun loadImage(context: Context?, url: String?, imageView: ImageView?) {
                if (!ImageLoaderUtils.assertValidRequest(context)) {
                    return
                }
                Glide.with(context!!).load(url).override(180, 180).into(imageView!!)
            }

            override fun loadImage(context: Context?, url: Uri?, maxWidth: Int, maxHeight: Int, call: UCropImageEngine.OnCallbackListener<Bitmap>?) {
                Glide.with(context!!).asBitmap().load(url).override(maxWidth, maxHeight).into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        call?.onCall(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        call?.onCall(null)
                    }

                })
            }
        })
        uCrop.start(fragment.requireActivity(), fragment, requestCode)
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private fun buildOptions(context: Context): UCrop.Options {
        val options = UCrop.Options()
        options.setHideBottomControls(false)//是否显示裁剪菜单栏
        options.setFreeStyleCropEnabled(false)//裁剪框or图片拖动
        options.setShowCropFrame(true)//是否显示裁剪边框
        options.setShowCropGrid(true)//是否显示裁剪框网格
        options.setCircleDimmedLayer(false)//圆形头像裁剪模式
        options.withAspectRatio(aspect_ratio_x.toFloat(), aspect_ratio_y.toFloat())
        getSandboxPath(context)?.let { options.setCropOutputPathDir(it) }//
        options.isCropDragSmoothToCenter(false)//
        options.setSkipCropMimeType(null)//
        options.isForbidCropGifWebp(false)//禁止裁剪gif
        options.isForbidSkipMultipleCrop(true)
        options.setMaxScaleMultiplier(100f)//
        if (PictureSelectorStyle() != null && PictureSelectorStyle().selectMainStyle.statusBarColor != 0) {
            val mainStyle: SelectMainStyle = PictureSelectorStyle().selectMainStyle
            val isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack
            val statusBarColor = mainStyle.statusBarColor
            options.isDarkStatusBarBlack(isDarkStatusBarBlack)
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor)
                options.setToolbarColor(statusBarColor)
            } else {
                options.setStatusBarColor(ContextCompat.getColor(context, R.color.color_393A3E))
                options.setToolbarColor(ContextCompat.getColor(context, R.color.color_393A3E))
            }
            val titleBarStyle: TitleBarStyle = PictureSelectorStyle().titleBarStyle
            if (StyleUtils.checkStyleValidity(titleBarStyle.titleTextColor)) {
                options.setToolbarWidgetColor(titleBarStyle.titleTextColor)
            } else {
                options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.colorWhite))
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(context, R.color.color_393A3E))
            options.setToolbarColor(ContextCompat.getColor(context, R.color.color_393A3E))
            options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.colorWhite))
        }
        return options
    }


    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private fun getSandboxPath(context: Context): String? {
        val externalFilesDir: File? = context.getExternalFilesDir("")
        val customFile = File(externalFilesDir!!.absolutePath, "pigeon")
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }
}