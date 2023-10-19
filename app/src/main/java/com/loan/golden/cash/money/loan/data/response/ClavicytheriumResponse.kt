package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 13:28
 * @Describe    :
 */
@Parcelize
data class ClavicytheriumResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var orderId: String = "",
        var overdueDays: Int = 0,
        var urlRepay: Boolean = false,
        var termUnit: String = "",
        var loanAmount: Double = 0.0,
        var overdue: Boolean = false,
        var interestAmount: Double = 0.0,
        var adminAmount: Double = 0.0,
        var orderStatus: String = "",
        var expiryAmount: Double = 0.0,
        var loanTerm: Int = 0,
        var interestRate: Double = 0.0,
        var urlInfo: String = "",
        var repayAmount: Double = 0.0,
        var delayRepay: Boolean = false,
        var actualAmount: Double = 0.0,
        var currencyType: String = "",
        var bankCard: String = "",
        var phone: String = "",

        ) : Parcelable

}
