package com.loan.golden.cash.money.loan.app.api

import com.loan.golden.cash.money.loan.app.App
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.AesUtils
import com.loan.golden.cash.money.loan.app.util.DeviceUtil
import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.data.commom.Constant
import me.hgj.mvvmhelper.base.appContext
import me.hgj.mvvmhelper.net.interception.logging.util.LogUtils
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
        val header =
            "({GAID:" + aDid + "," + "VERSION:" + App.versionName + "," + "RV:1.0.0" + "," + "AID:" + DeviceUtil.getAndroidId(appContext) + "})"

        val builder = chain.request().newBuilder()
        if (KvUtils.decodeBoolean(Constant.IS_INIT_LOGIN)) {
            builder.addHeader("Auth", token).build()
        }
        builder.addHeader(
            "Token",
            Constant.appId + AESTool.encrypt(header, Constant.AES_KEY).toString()
        ).build()
        LogUtils.debugInfo("---------->>>token:" + AESTool.encrypt1(header, Constant.AES_KEY))
        return chain.proceed(builder.build())
    }
}