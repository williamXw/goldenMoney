package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/10 14:59
 * @Describe    :
 */
@Parcelize
data class LiveParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var faceInfo: String = "",
        var livenessType: String = ""

    ) : Parcelable
}
