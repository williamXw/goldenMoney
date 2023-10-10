package com.loan.golden.cash.money.loan.app.util

import android.content.Context
import com.luck.picture.lib.engine.UriToFileTransformEngine
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.utils.SandboxTransformUtils

/**
 * @Author      : hxw
 * @Date        : 2023/7/26 16:06
 * @Describe    : 自定义沙盒处理
 */
class MeSandboxFileEngine : UriToFileTransformEngine {
    override fun onUriToFileAsyncTransform(context: Context, srcPath: String, mineType: String, call: OnKeyValueResultCallbackListener) {
        if (call != null) {
            call.onCallback(srcPath, SandboxTransformUtils.copyPathToSandbox(context, srcPath, mineType))
        }
    }
}