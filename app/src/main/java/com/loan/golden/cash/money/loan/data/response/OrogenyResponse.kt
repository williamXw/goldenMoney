package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/18 16:38
 * @Describe    :
 */
@Parcelize
data class OrogenyResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var methods: ArrayList<MethodsBean> = arrayListOf()

    ) : Parcelable {

        @Parcelize
        class MethodsBean(

            var isSelected:Boolean,
            var methodName: String = "",
            var methodCode: String = "",
            var code: String = "",
            var repayMethod: String = "",

            ) : Parcelable

    }

}
