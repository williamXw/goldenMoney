package com.loan.golden.cash.money.loan.app.listener

import android.Manifest
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.luck.picture.lib.dialog.RemindDialog
import com.luck.picture.lib.interfaces.OnCallbackListener
import com.luck.picture.lib.interfaces.OnPermissionDeniedListener
import com.luck.picture.lib.permissions.PermissionConfig
import com.luck.picture.lib.permissions.PermissionUtil

/**
 * @Author      : hxw
 * @Date        : 2023/7/26 16:11
 * @Describe    :
 */
class MeOnPermissionDeniedListener: OnPermissionDeniedListener {

    override fun onDenied(
        fragment: Fragment, permissionArray: Array<String>,
        requestCode: Int, call: OnCallbackListener<Boolean>
    ) {
        var tips = ""
        tips = if (TextUtils.equals(permissionArray[0], PermissionConfig.CAMERA[0])) {
            "缺少相机权限\n可能会导致不能使用摄像头功能"
        } else if (TextUtils.equals(permissionArray[0], Manifest.permission.RECORD_AUDIO)) {
            "缺少录音权限\n访问您设备上的音频、媒体内容和文件"
        } else {
            "缺少存储权限\n访问您设备上的照片、媒体内容和文件"
        }
        val dialog = RemindDialog.buildDialog(fragment.context, tips)
        dialog.setButtonText("去设置")
        dialog.setButtonTextColor(-0x828201)
        dialog.setContentTextColor(-0xcccccd)
        dialog.setOnDialogClickListener {
            PermissionUtil.goIntentSetting(fragment, requestCode)
            dialog.dismiss()
        }
        dialog.show()
    }
}