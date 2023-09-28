package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/28 8:20
 * @Describe    :
 */
@Parcelize
class WorkParam(

    var model: Model? = null

) : Parcelable {

    @Parcelize
    class Model(

        var formId: String = "",
        var submitData: SubmitData? = null

    ) : Parcelable {

        @Parcelize
        class SubmitData(

            var professionInfo: ProfessionInfo? = null,
            var monthIncome: Int = 0,
            var company: String = "",
            var companyAddress: CompanyAddress? = null,
            var companyPinCode: String = ""

        ) : Parcelable {

            @Parcelize
            class CompanyAddress(

                var bigAddress: BigAddress? = null,
                var detailAddress: String = ""

            ) : Parcelable {

                @Parcelize
                class BigAddress(

                    var province: String = "",
                    var city: String = "",

                    ) : Parcelable

            }

            @Parcelize
            class ProfessionInfo(

                var workType: String = "",
                var profession: String = "",

                ) : Parcelable
        }
    }
}