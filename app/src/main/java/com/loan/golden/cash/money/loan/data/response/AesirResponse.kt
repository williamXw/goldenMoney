package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AesirResponse(

    var status: Int = -1,
    var time: String = "",
    var maxAge: Int = -1,
    var message: String = "",
    var model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        var forms: ArrayList<FormsData> = ArrayList(),

        ) : Parcelable {

        @Parcelize
        class FormsData(

            var formId: String = "",//表单ID",
            var formType: String = "",//表单类型：BASIC-基础信息,OCR-证件识别,ALIVE-活体检测,ALIVE_H5-活体检测H5",
            var formName: String = "",//表单名称",
            var formDesc: String = "",//表单描述",
            var columnField: String = "",
            var sort: Int = 0,
//            var content: ArrayList<Any> = ArrayList(),//表单包含字段及字段的名称、类型、限制条件等"

        ) : Parcelable
    }
}