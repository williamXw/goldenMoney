package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/10 15:40
 * @Describe    :
 */
@Parcelize
data class NapperParam(

    var query: QueryBean? = null

) : Parcelable {

    @Parcelize
    class QueryBean(

    ) : Parcelable
}
