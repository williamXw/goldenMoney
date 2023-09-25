package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date

/**
 * @Author      : hxw
 * @Date        : 2023/9/15 14:06
 * @Describe    :
 */
@Parcelize
data class CarPologyParam(

    val model: Model? = null,

    ) : Parcelable {

    @Parcelize
    class Model(

        val idCard: String = "",//身份证Id
        val realName: String = "",////姓名，根据表单配置是完整名称还是分开提交
        val taxRegNumber: String = "",//印度Pan卡号
        val birthDay: String = "",////出生日期，如：936115200000
        val idCardImageFront: String = "",//身份证正面照Url
        val idCardImageBack: String = "",//身份证反面照Url
        val idCardImagePan: String = "",//印度Pan卡正面照Url


    ) : Parcelable
}

