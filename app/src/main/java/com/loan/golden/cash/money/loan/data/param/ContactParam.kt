package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/8 11:23
 * @Describe    :
 */
@Parcelize
data class ContactParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var formId: String = "",
        var submitData: SubmitDataBean? = null

    ) : Parcelable {

        @Parcelize
        class SubmitDataBean(

            var userEmergs: ArrayList<UserEmergsBean> = arrayListOf()

        ) : Parcelable {

            @Parcelize
            class UserEmergsBean(

                var name: String = "",
                var phone: String = "",
                var relation: String = "",

                ) : Parcelable

        }
    }

}
