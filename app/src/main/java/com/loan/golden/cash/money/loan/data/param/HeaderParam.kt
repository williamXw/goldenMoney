package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/18 13:36
 * @Describe    :
 */
@Parcelize
class HeaderParam(

    val GAID: String = "",
    val VERSION: String = "",
    val RV: String = "",
    val AID: String = "",

    ) : Parcelable