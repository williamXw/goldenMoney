package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/15 14:06
 * @Describe    :
 */
@Parcelize
data class AesirParam(

    val model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        val nodeType: String = "NODE1",


        ) : Parcelable
}

