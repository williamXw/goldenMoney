package com.loan.golden.cash.money.loan.app.api

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * 作者　: hxw
 * 时间　: 2021/6/9
 * 描述　:
 */
object NetUrl {

    // 服务器请求成功的 Code值
    const val SUCCESS_CODE = 100001

    @DefaultDomain //设置为默认域名
    const val DEV_URL = "http://10.10.12.89:10081"

    /**登录*/
    const val LOGIN = "/u/v1/user/login"

}