package com.loan.golden.cash.money.loan.app.api

import com.google.gson.Gson
import com.loan.golden.cash.money.loan.app.App
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.CacheUtil
import com.loan.golden.cash.money.loan.app.util.DeviceUtil
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.HeaderParam
import com.loan.golden.cash.money.loan.data.response.LoginResponse
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
        val aDid = App.aDid
        val headerBody = HeaderParam(
            GAID = aDid,
            VERSION = App.versionName,
            RV = "1.0.0",
            AID = DeviceUtil.getAndroidId(appContext)
        )
        val header = Gson().toJson(headerBody)
        val builder = chain.request().newBuilder()
        if (CacheUtil.isLogin()) {
            val user = CacheUtil.getUser() as LoginResponse
            user.user?.let { builder.addHeader("Auth", it.token).build() }
        }
        builder.addHeader(
            "Token",
            Constant.appId + AESTool.encrypt1(header, Constant.AES_KEY).toString()
        ).build()
        LogUtils.debugInfo("---------->>>token:" + AESTool.encrypt1(header, Constant.AES_KEY))
        return chain.proceed(builder.build())
    }
}