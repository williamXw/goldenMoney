package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 11:33
 * @Describe    :
 */
@Parcelize
data class UnrighteousnessResponse(

    var status: Int = 0,
    var time: Long = 0,
    var message: String = "",
    var maxAge: Int = 0,
    var list: ArrayList<ListBean> = arrayListOf()

) : Parcelable {

    @Parcelize
    class ListBean(

        var id: String = "",
        var created: Long = 0,
        var modified: Long = 0,
        var name: NameBean? = null,
        var description: DescriptionBean? = null,
        var curName: String = "",
        var curDescription: String = "",
        var sort: Int = 0,
        var requireImg: Boolean = false

    ) : Parcelable {

        @Parcelize
        class DescriptionBean(

            var values: ValuesBean? = null,
            var _t: String = ""

        ) : Parcelable {

            @Parcelize
            class ValuesBean(var en_us: String = "") : Parcelable
        }

        @Parcelize
        class NameBean(

            var values: ValuesBean? = null,
            var _t: String = ""

        ) : Parcelable {

            @Parcelize
            class ValuesBean(

                var en_us: String = "",
                var hi: String = "",

                ) : Parcelable
        }
    }

}
