package com.loan.golden.cash.money.loan.app.api

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * 作者　: hxw
 * 时间　: 2021/6/9
 * 描述　:
 */
object NetUrl {

    // 服务器请求成功的 Code值
    const val SUCCESS_CODE = 0

    @DefaultDomain //设置为默认域名
    const val DEV_URL = "https://app.goldenmoney.shop"

    /** 登录 */
    const val LOGIN = "/slic/pep_peperino/azaiea"
    /** 发送验证码 */
    const val CHAINBRIDGE = "/dystrophia/notchery_notchwing/chainbridge"
    /** 上传文件 */
    const val STREAM_STREAMBED = "/specifiable/stream_streambed"

}