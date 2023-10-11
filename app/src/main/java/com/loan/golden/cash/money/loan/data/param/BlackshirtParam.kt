package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/11 10:30
 * @Describe    :
 */
@Parcelize
data class BlackshirtParam(

    var query: QueryBean? = null

) : Parcelable {

    @Parcelize
    class QueryBean(

        var status: String = "",
        var pageNo: Int = 0,
        var pageSize: Int = 0,

        ) : Parcelable
}