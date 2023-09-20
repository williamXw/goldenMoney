package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OCRResponse(

    val extra: String = "",
    val maxAge: Int = 0,
    val message: String = "",
    val model: Model? = null,
    val status: Int = 0,
    val time: String = ""

) : Parcelable {

    @Parcelize
    class Model(

        var id: String = "",
        var ossUrl: String = ""

    ) : Parcelable
}