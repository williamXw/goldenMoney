package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 9:39
 * @Describe    : 获取指定表单
 */
@Parcelize
data class CharlottetownParam(

    var model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        var formId: String = "",
        var nodeType: String = "NODE1"

    ) : Parcelable
}
