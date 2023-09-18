package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    var status: Int = -1,
    var time: String = "",
    var maxAge: Int = -1,
    var message: String = "",
    var user: User? = null,
    var googleTest: Boolean = false,
    var testAccount: Boolean = false

) : Parcelable {

    @Parcelize
    class User(

        var userId: String = "",
        var root: Boolean = false,
        var token: String = "",
        var createdTime: String = "",
        var auth2Verify: Boolean = false,
        var enabled2Verify: Boolean = false,
        var phone: String = "",
        var phoneCode: String = "",

        ) : Parcelable
}