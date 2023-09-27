package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:27
 * @Describe    :
 */
@Parcelize
class FigeaterResponse(

    var status: Int = 0,
    var time: Long = 0,
    var message: String = "",
    var maxAge: Int = 0,
    var model: ArrayList<ModelInfo> = arrayListOf()

) : Parcelable {

    /**
     * 数据是否为空
     */
    fun isEmpty() = model == null || (model as List<*>).isEmpty()

    @Parcelize
    class ModelInfo(

        var id: String = "",
        var created: Long = 0,
        var modified: Long = 0,
        var countryId: String = "",
        var parentId: String = "",
        var type: String = "",//PROVINCES-省、REGENCIES-市、DISTRICTS-区、VILLAGES-村镇
        var name: String = "",
        var haveChild: Boolean = false,
        var root: Boolean = false,

        ) : Parcelable
}