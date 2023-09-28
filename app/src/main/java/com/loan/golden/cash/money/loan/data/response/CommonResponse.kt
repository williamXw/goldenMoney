package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 9:53
 * @Describe    :
 */
@Parcelize
data class CommonResponse(

    var status: Int = -1,
    var time: Long = 0,
    var maxAge: Long = -1,
    var message: String = ""

) : Parcelable