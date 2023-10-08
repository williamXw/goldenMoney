package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/8 9:10
 * @Describe    :
 */
@Parcelize
data class PersonalInfoParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var formId: String = "",
        var submitData: SubmitDataBean? = null

    ) : Parcelable {

        @Parcelize
        class SubmitDataBean(

            var education: String = "",
            var sex: String = "",
            var marital: String = "",
            var childrenCount: String = "",
            var postalCode: String = "",
            var residence: String = "",
            var address: AddressBean? = null,
            var email: String = ""

        ) : Parcelable {

            @Parcelize
            class AddressBean(

                var bigAddress: BigAddressBean? = null,
                var detailAddress: String = ""

            ) : Parcelable {

                @Parcelize
                class BigAddressBean(

                    var province: String = "",
                    var city: String = "",

                    ) : Parcelable
            }
        }
    }

}