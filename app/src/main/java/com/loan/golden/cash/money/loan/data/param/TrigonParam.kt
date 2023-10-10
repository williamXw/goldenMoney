package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  author : huxiaowei
 *  date : 2023/10/10 22:20
 *  description : 产品手续费试算
 */
@Parcelize
data class TrigonParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var productIds: ArrayList<String> = arrayListOf()//产品编号

    ) : Parcelable
}
