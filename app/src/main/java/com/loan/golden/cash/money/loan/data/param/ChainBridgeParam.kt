package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/15 14:06
 * @Describe    :
 */
@Parcelize
data class ChainBridgeParam(

    val model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        val phone: String,
        val phoneCode: String

    ) : Parcelable {
        override fun toString(): String {
            val sb = StringBuilder("ChainBridgeParam{")
            sb.append("model{")
            sb.append("'phone':").append(phone)
            sb.append(", 'phoneCode':").append(phoneCode)
            sb.append("}")
            sb.append("}")
            return sb.toString()
        }
    }
}

