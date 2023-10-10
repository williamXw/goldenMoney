package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  author : huxiaowei
 *  date : 2023/10/10 22:43
 *  description :
 */
@Parcelize
data class TrigonResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var model: ArrayList<ModelBean> = arrayListOf(),
    var message: String = ""

) : Parcelable {

    @Parcelize
    class ModelBean(

        var productId: String = "",
        var name: String = "",
        var amount: Double = 0.00,
        var term: Int = 0,
        var interestAmount: Double = 0.00,
        var adminAmount: Double = 0.0,
        var actualAmount: Double = 0.0,
        var repayAmount: Double = 0.0,
        var overdueRate: Double = 0.0,
        var enableFeeDesc: Boolean = false,
        var feeDescList: ArrayList<FeeDescListBean> = arrayListOf()

    ) : Parcelable {

        @Parcelize
        class FeeDescListBean(

            var title: String = "",
            var value: String = "",

            ) : Parcelable
    }
}
