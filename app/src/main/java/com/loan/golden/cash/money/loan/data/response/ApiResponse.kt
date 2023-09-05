package com.loan.golden.cash.money.loan.data.response

/**
 * 时间　: 2020/11/18
 * 作者　: hxw
 * 描述　: 玩Android 服务器返回的数据基类
 */
data class ApiResponse<T>(
    var data: T,
    var code: Int = -1,
    var msg: String = "",
    var msgCn: String = ""
)