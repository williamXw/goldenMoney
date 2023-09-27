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

    /** 证件识别 */
    const val DIAMANTIFEROUS = "/conhydrine_conic/monolayer_monolingual_monolith/diamantiferous"

    /** 获取一个未完成的表单 */
    const val AESCULAPIUS_AESCULIN_AESIR = "/viewfinder_viewless_viewphone/outdone_outdoor/aesculapius_aesculin_aesir"

    /** 提交用户信息 */
    const val CARPOLOGIST_CARPOLOGY = "/hypermeter_hypermetric_hypermetrical/carpologist_carpology"

    /** 检测设备信息上报情况 */
    const val NAPPER_NAPPY_NAPRAPATH = "/preclinical/mennonite_menology/napper_nappy_naprapath"

    /** 获取工作岗位信息 */
    const val KALEYARD_KALI = "/garboard_garboil_garbologist/kaleyard_kali"

    /** 获取地址信息 */
    const val FIGEATER = "/grew_grewsome_grey/figeater"

}