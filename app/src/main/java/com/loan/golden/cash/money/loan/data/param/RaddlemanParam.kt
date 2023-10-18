package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/18 14:08
 * @Describe    : 提交反馈
 */
@Parcelize
data class RaddlemanParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var typeId: String = "",
        var content: String = "",
        var images: ArrayList<String> = arrayListOf(),
        var thirdOrderId: String = ""//订单ID

    ) : Parcelable

}
