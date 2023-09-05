package com.loan.golden.cash.money.loan.app.api

import android.app.Application
import com.loan.golden.cash.money.loan.app.App
import com.loan.golden.cash.money.loan.app.util.DeviceUtil
import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.data.commom.Constant
import me.hgj.mvvmhelper.base.appContext
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
        val aDid = App.aDid
        val builder = chain.request().newBuilder()
        builder.addHeader(
            "Token",
            Constant.appId + "+Aes({GAID:" + aDid + "," + "VERSION:"
                    + App.versionName + ","
                    + "RV:1.0.0" + "," + "AID:" + DeviceUtil.getAndroidId(appContext) + "})"
        ).build()
        return chain.proceed(builder.build())
    }
}