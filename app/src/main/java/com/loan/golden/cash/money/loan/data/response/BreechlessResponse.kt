package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/23 13:26
 * @Describe    :
 */
@Parcelize
data class BreechlessResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var orderId: String = "",
        var loanAppId: String = "",
        var loanAmount: Double = 0.0,
        var interestAmount: Double = 0.0,
        var expiryAmount: Double = 0.0,
        var adminAmount: Double = 0.0,
        var delayAmount: Double = 0.0,
        var repayAmount: Double = 0.0,
        var expiryTime: Long = 0,
        var startTime: Long = 0,
        var endTime: Long = 0,
        var delayTimes: Int = 0,
        var limitTimes: Int = 0,
        var termUnit: String = "",
        var delayTerm: Int = 0,
        var currencyType: String = ""

    ) : Parcelable

}
