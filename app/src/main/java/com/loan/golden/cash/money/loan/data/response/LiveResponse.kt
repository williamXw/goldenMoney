package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

/**
 * @Author      : hxw
 * @Date        : 2023/10/10 15:20
 * @Describe    :
 */
@Parcelize
data class LiveResponse(

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
        var countryId: String = "",
        var userId: String = "",
        var passFacePass: Boolean = false,
        var livenessScore: Double = 0.0,
        var facePhotoImages: ArrayList<String> = arrayListOf(),
        var samePerson: Boolean = false,
        var confidenceScore: Double = 0.0,
        var status: String = "",
        var thirdId: String = "",
        var appId: String = "",
        var deviceInfoId: String = "",
        var rawBody: String = "",
        var multiplePass: Boolean = false,
        var cost: CostBean? = null

    ) : Parcelable {

        @Parcelize
        class CostBean(

            var currencyCode: String = "",
            var amount: Double = 0.0,
            var valid: Boolean = false,
            var noZero: Boolean = false,

            ) : Parcelable

    }
}