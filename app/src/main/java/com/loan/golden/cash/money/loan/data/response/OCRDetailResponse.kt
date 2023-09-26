package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/26 13:44
 * @Describe    :
 */
@Parcelize
class OCRDetailResponse(

    val idCard: String = "",
    val realName: String = "",
    val taxRegNumber: String = "",
    val birthDay: String = "",
    val pinCode: String = "",
    val idCardImageFront: String = "",
    val idCardImageBack: String = "",
    val idCardImagePan: String = "",

    ) : Parcelable