package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpLoadDeviceInfoResponse(

    val extra: String = "",
    val maxAge: Int = 0,
    val message: String = "",
    val list: ArrayList<String> = arrayListOf(),
    val status: Int = 0,
    val time: String = ""

) : Parcelable