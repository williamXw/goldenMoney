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
        var phone: String = ""

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

                    var relationList: ArrayList<RelationListBean> = arrayListOf(),
                    var fieldConf: FieldConfBean? = null

                ) : Parcelable {

                    @Parcelize
                    class FieldConfBean(

                        var nameLabel: String = "",
                        var phoneLabel: String = "",
                        var phonePlaceholder: String = "",
                        var relationLabel: String = "",
                        var count: Int = 0,

                        ) : Parcelable

                    @Parcelize
                    class RelationListBean(

                        var id: String = "",
                        var created: Long = 0,
                        var modified: Long = 0,
                        var countryIds: ArrayList<String> = arrayListOf(),
                        var categoryId: String = "",
                        var name: String = "",
                        var value: String = "",
                        var hasChildren: Boolean = false,
                        var color: String = "",
                        var values: ValuesBean? = null,
                        var sort: Int = 0

                    ) : Parcelable {

                        @Parcelize
                        class ValuesBean(

                            var values: ValuesBean2? = null

                        ) : Parcelable {

                            @Parcelize
                            class ValuesBean2(

                                var id: String = "",
                                var es_NI: String = "",
                                var en_us: String = "",
                                var hi: String = "",
                                var ur: String = "",

                                ) : Parcelable

                        }
                    }
                }

                @Parcelize
                class OptionsBeean(

                    var id: String = "",
                    var created: Long = 0,
                    var modified: Long = 0,
                    var countryIds: ArrayList<String> = arrayListOf(),
                    var categoryId: String = "",
                    var name: String = "",
                    var value: String = "",
                    var hasChildren: Boolean = false,
                    var color: String = "",
                    var values: ValuesBean? = null,
                    var sort: Int = 0

                ) : Parcelable {

                    @Parcelize
                    class ValuesBean(

                        var values: ValuesBeanChild? = null,
                        var _t: String = ""

                    ) : Parcelable {

                        @Parcelize
                        class ValuesBeanChild(

                            var id: String = "",
                            var es_NI: String = "",
                            var en_us: String = "",
                            var hi: String = "",
                            var vi: String = "",
                            var zh_TW: String = "",
                            var ur: String = "",

                            ) : Parcelable
                    }
                }
            }
        }
    }
}