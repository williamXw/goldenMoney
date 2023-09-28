package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 9:39
 * @Describe    :
 */
@Parcelize
data class LustreParam(

    var model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        var formId: String = "",
        var submitData: WorkParam? = null

    ) : Parcelable
}
