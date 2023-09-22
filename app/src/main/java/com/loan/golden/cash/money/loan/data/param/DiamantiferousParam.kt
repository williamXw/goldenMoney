package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/15 14:06
 * @Describe    : 证件识别
 */
@Parcelize
data class DiamantiferousParam(

    val model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        val url: String = "",//证件Url",
        val cardType: String = "",//FRONT-正面,BACK-反面,PAN-印度P卡正面,NIN-尼日利亚,BVN-尼日利亚"

    ) : Parcelable
}

