package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 13:24
 * @Describe    :
 */
@Parcelize
data class ClavicytheriumParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var orderId: String = ""

    ) : Parcelable

}
