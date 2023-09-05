package com.loan.golden.cash.money.loan.app.ext

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 作者　: hxw
 * 时间　: 2022/5/23
 * 描述　: 全局发送消息 通过LiveData实现
 */
object LiveDataEvent {

    //示例：登录成功发送通知
    val loginEvent = UnPeekLiveData<Boolean>()

}