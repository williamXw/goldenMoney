package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  author : huxiaowei
 *  date : 2023/9/28 22:01
 *  description :
 */
@Parcelize
data class LottetownResponse(

    var status: Int = -1,
    var time: Long = 0,
    var maxAge: Long = 0,
    var message: String = "",
    var model: Model? = null

) : Parcelable {

    @Parcelize
    class Model(

        var forms: ArrayList<FormsBean> = arrayListOf(),
        var baseInfo: BaseInfoBean? = null,
        var phone: Long = 0

    ) : Parcelable {

        @Parcelize
        class BaseInfoBean(

            var birthDate: Long = 0, var workingMonth: Int = 0, var taxRegNumber: String = ""

        ) : Parcelable

        @Parcelize
        class FormsBean(

            var formId: String = "",
            var formType: String = "",
            var columnField: String = "",
            var formName: String = "",
            var formDesc: String = "",
            var content: ArrayList<ContentBean> = arrayListOf(),
            var sort: Int = 0

        ) : Parcelable {

            @Parcelize
            class ContentBean(

                var name: String = "",
                var options: ArrayList<OptionsBeean> = arrayListOf(),
                var id: String = "",
                var type: String = "",
                var required: Boolean = false,
                var secondConfirm: Boolean = false,
                var props: PropsBean? = null,


                ) : Parcelable {

                @Parcelize
                class PropsBean(

                ) : Parcelable

                @Parcelize
                class OptionsBeean(


                ) : Parcelable
            }
        }
    }
}