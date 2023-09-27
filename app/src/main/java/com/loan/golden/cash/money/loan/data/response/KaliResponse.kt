package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 14:27
 * @Describe    :
 */
@Parcelize
class KaliResponse(

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
        var name: String = "",
        var children: ArrayList<ChildrenBean> = arrayListOf()

    ) : Parcelable {

        /**
         * 数据是否为空
         */
        fun isEmpty() = children == null || (children as List<*>).isEmpty()

        @Parcelize
        class ChildrenBean(

            var id: String = "",
            var name: String = ""

        ) : Parcelable
    }
}