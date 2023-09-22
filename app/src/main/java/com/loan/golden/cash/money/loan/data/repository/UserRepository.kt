package com.loan.golden.cash.money.loan.data.repository

import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import rxhttp.toOkResponse
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp

/**
 * 作者　: hxw
 * 时间　: 2020/11/2
 * 描述　: 数据仓库
 */
object UserRepository {

    /** 登录 */
    fun login(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.LOGIN).setBody(body)
            .toOkResponse()
    }

    /** 发送OTP */
    fun chainBridge(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.CHAINBRIDGE).setBody(body)
            .toOkResponse()
    }

    /** 上传文件 */
    fun streamStreambed(body: MultipartBody): Await<Response> {
        return RxHttp.postBody(NetUrl.STREAM_STREAMBED).setBody(body)
            .toOkResponse()
    }

    /** 证件识别 */
    fun diamantiferous(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.DIAMANTIFEROUS).setBody(body)
            .toOkResponse()
    }

    /** 获取一个未完成的表单 */
    fun aesculinAesir(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.AESCULAPIUS_AESCULIN_AESIR).setBody(body)
            .toOkResponse()
    }

}

