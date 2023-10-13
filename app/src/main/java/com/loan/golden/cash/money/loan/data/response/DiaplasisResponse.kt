package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 14:43
 * @Describe    :
 */
@Parcelize
data class DiaplasisResponse(

    var status: Int = -1,
    var time: Long = 0,
    var maxAge: Long = -1,
    var message: String = "",
    var page: PageBean? = null

) : Parcelable {

    @Parcelize
    class PageBean(

        var content: ArrayList<ContentBean> = arrayListOf(),
        var pageable: String = "",
        var first: Boolean = false,
        var numberOfElements: Int = 0,
        var totalElements: Int = 0,
        var totalPages: Int = 0,
        var hasNext: Boolean = false,
        var hasPrevious: Boolean = false,
        var last: Boolean = false,
        var number: Int = 0,
        var size: Int = 0,
        var sort: SortBean? = null,
        var empty: Boolean = false

    ) : Parcelable {

        @Parcelize
        class SortBean(

            var sorted: Boolean = false,
            var unsorted: Boolean = false,
            var empty: Boolean = false,

            ) : Parcelable

        @Parcelize
        class ContentBean(

            var id: String = "",
            var created: Long = 0,
            var modified: Long = 0,
            var typeId: String = "",
            var content: String = "",
            var images: ArrayList<String> = arrayListOf(),
            var thirdOrderId: String = "",
            var reply: Boolean = false,
            var replyTime: Long = 0,
            var replyContent: String = ""

        ) : Parcelable

    }

}
