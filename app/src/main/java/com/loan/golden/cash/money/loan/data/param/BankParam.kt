package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/8 13:37
 * @Describe    :
 */
@Parcelize
data class BankParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var formId: String = "",
        var submitData: SubmitDataBean? = null

    ) : Parcelable {

        @Parcelize
        class SubmitDataBean(

            var userBank: UserBankBean? = null

        ) : Parcelable {

            @Parcelize
            class UserBankBean(

                var bankCode: String = "",
                var bankCard: String = "",
                var ifscCode: String = "",

                ) : Parcelable

        }
    }

}
