package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/25 11:00
 * @Describe    :
 */
@Parcelize
data class DiamantiferousResponse(

    val status: Int = 0,
    val time: String = "",
    val maxAge: Int = 0,
    val message: String = "",
    val model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        var idCard: String = "",
        var realName: String = "",
        var birthDay: Long = 0,
        var taxRegNumber: String = "",//pan卡号
        var pinCode: String = ""//pan卡号

    ) : Parcelable

}
