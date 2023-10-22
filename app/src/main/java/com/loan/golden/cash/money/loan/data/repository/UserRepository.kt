package com.loan.golden.cash.money.loan.data.repository

import com.loan.golden.cash.money.loan.app.api.NetUrl
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

    /** 提交用户信息 */
    fun carpologistCarpology(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.CARPOLOGIST_CARPOLOGY).setBody(body)
            .toOkResponse()
    }

    /** 提检测设备信息上报情况 */
    fun nappyNaprapath(): Await<Response> {
        return RxHttp.get(NetUrl.NAPPER_NAPPY_NAPRAPATH).toOkResponse()
    }

    /** 获取工作岗位信息 */
    fun kaleyardKali(): Await<Response> {
        return RxHttp.postBody(NetUrl.KALEYARD_KALI).setBody("").toOkResponse()
    }

    /** 获取地址信息 */
    fun getFigeater(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.FIGEATER).setBody(body).toOkResponse()
    }

    /** 提交表单 */
    fun lustrationLustre(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.LUSTRATION_LUSTRE).setBody(body).toOkResponse()
    }

    /** 获取指定表单 */
    fun charlotteCharlottetown(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.CHARLOTTE_CHARLOTTETOWN).setBody(body).toOkResponse()
    }

    /** 活体检测+人脸对比 */
    fun ghettoize(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.GHETTOIZE).setBody(body).toOkResponse()
    }

    /** 产品信息接口 */
    fun napper(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.NAPPER).setBody(body).toOkResponse()
    }

    /** 产品手续费试算 */
    fun trigon(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.TRIGON).setBody(body).toOkResponse()
    }

    /** 贷款申请 */
    fun apologiaApologise(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.APOLOGIA_APOLOGISE).setBody(body).toOkResponse()
    }

    /** 订单列表 */
    fun blackshirt(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.BLACKSHIRT).setBody(body).toOkResponse()
    }

    /** 获取反馈类型列表 */
    fun unrighteousness(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.UNRIGHTEOUSNESS).setBody(body).toOkResponse()
    }

    /** 获取反馈列表 */
    fun diaplasis(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.DIAPLASIS).setBody(body).toOkResponse()
    }

    /** 获取反馈列表 */
    fun raddledRaddleman(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.RADDLE_RADDLED_RADDLEMAN).setBody(body).toOkResponse()
    }

    /** 获取订单还款方式列表 */
    fun orogenicsOrogeny(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.OROGENICS_OROGENY).setBody(body).toOkResponse()
    }

    /** 获取订单还款链接/还款码 */
    fun chippewaChippie(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.CHIPPER_CHIPPEWA_CHIPPIE).setBody(body).toOkResponse()
    }

    /** 获取订单还款计划 */
    fun clavicytherium(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.CLAVICYTHERIUM).setBody(body).toOkResponse()
    }

    /** 展期试算 */
    fun breechingBreechless(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.BREECHING_BREECHLESS).setBody(body).toOkResponse()
    }

    /** 上报APP信息 */
    fun mnemonMnemonic(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.MNEMON_MNEMONIC).setBody(body).toOkResponse()
    }

    /** 上报设备信息 */
    fun ganger(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.GANGER).setBody(body).toOkResponse()
    }

    /** 上报SMS信息 */
    fun sacristSacristan(body: RequestBody): Await<Response> {
        return RxHttp.postBody(NetUrl.SACRIST_SACRISTAN).setBody(body).toOkResponse()
    }

}

