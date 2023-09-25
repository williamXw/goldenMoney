package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiamantiferousResponse(

    val extra: String = "",
    val maxAge: Int = 0,
    val message: String = "",
    val model: Model? = null,
    val status: Int = 0,
    val time: String = ""

) : Parcelable {

    @Parcelize
    class Model(

        var idCard: String = "",
        var realName: String = "",
        var birthDay: Long = 0,
        var taxRegNumber:String = "",


    ) : Parcelable
}