package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 9:58
 * @Describe    :
 */
@Parcelize
data class ChippieResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var orderId: String = "",
        var repayAmount: Double = 0.0,
        var repayCode: String = "",
        var repayCodeType: Int = 0,
        var repayNote: String = "",
        var currencyType: String = ""

    ) : Parcelable

}
