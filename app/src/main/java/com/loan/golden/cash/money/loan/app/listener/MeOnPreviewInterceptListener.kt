package com.loan.golden.cash.money.loan.app.listener

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.loan.golden.cash.money.loan.ui.fragment.CustomPreviewFragment
import com.luck.picture.lib.basic.FragmentInjectManager
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnPreviewInterceptListener

/**
 * @Author      : hxw
 * @Date        : 2023/7/26 16:10
 * @Describe    : 自定义预览
 */
class MeOnPreviewInterceptListener : OnPreviewInterceptListener {

    override fun onPreview(
        context: Context,
        position: Int,
        totalNum: Int,
        page: Int,
        currentBucketId: Long,
        currentAlbumName: String,
        isShowCamera: Boolean,
        data: java.util.ArrayList<LocalMedia>,
        isBottomPreview: Boolean
    ) {
        val previewFragment: CustomPreviewFragment = CustomPreviewFragment.newInstance()
        previewFragment.setInternalPreviewData(
            isBottomPreview, currentAlbumName, isShowCamera,
            position, totalNum, page, currentBucketId, data
        )
        FragmentInjectManager.injectFragment(context as FragmentActivity, CustomPreviewFragment.TAG, previewFragment)
    }
}