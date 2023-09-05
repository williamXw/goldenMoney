package com.loan.golden.cash.money.loan.app.api

import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.data.commom.Constant
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * 作者　: hxw
 * 时间　: 2021/6/9
 * 描述　:
 */
class HeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = KvUtils.decodeString(Constant.TOKEN)
        val builder = chain.request().newBuilder()
        builder.addHeader("x-token", token).build()
        return chain.proceed(builder.build())
    }

}