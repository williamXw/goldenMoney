package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/18 14:28
 * @Describe    :
 */
@Parcelize
class RaddlemanResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var id: String = "",
        var created: Long = 0,
        var modified: Long = 0,
        var typeId: String = "",
        var content: String = "",
        var images: ArrayList<String> = arrayListOf(),
        var thirdOrderId: String = "",
        var reply: Boolean = false

    ) : Parcelable

}