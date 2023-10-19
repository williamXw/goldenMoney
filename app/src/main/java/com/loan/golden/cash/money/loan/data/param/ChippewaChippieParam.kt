package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 9:48
 * @Describe    :
 */
@Parcelize
data class ChippewaChippieParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var orderId: String = "",
        var repayMethod: String = "",
        var methodCode: String = "",
        var repayType: String = "",

        ) : Parcelable

}
