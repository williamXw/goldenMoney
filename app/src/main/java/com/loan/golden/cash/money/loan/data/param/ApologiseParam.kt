package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

/**
 *  author : huxiaowei
 *  date : 2023/10/10 23:09
 *  description :
 */
@Parcelize
data class ApologiseParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var productIds: ArrayList<String> = arrayListOf(),
        var code: String = ""

    ) : Parcelable

}