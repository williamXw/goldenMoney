package com.loan.golden.cash.money.loan.data.repository

import com.loan.golden.cash.money.loan.app.api.NetUrl
import com.loan.golden.cash.money.loan.data.response.LoginRegisterReq
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

    /**登录*/
    fun login(body: LoginRegisterReq): Await<Response> {
        return RxHttp.postBody(NetUrl.LOGIN).setBody(body)
            .toOkResponse()
    }

}

