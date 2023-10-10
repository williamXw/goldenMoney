package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

/**
 * @Author      : hxw
 * @Date        : 2023/10/10 15:45
 * @Describe    :
 */
@Parcelize
data class NapperResponse(

    var status: Int = 0,
    var message: String = "",
    var time: Long = 0,
    var maxAge: Int = 0,
    var page: PageBean? = null

) : Parcelable {

    @Parcelize
    class PageBean(

        var content: ArrayList<ContentBean> = arrayListOf(),
        var pageable: String = "",
        var first: Boolean = false,
        var last: Boolean = false,
        var hasNext: Boolean = false,
        var hasPrevious: Boolean = false,
        var empty: Boolean = false,
        var numberOfElements: Int = 0,
        var totalElements: Int = 0,
        var totalPages: Int = 0,
        var number: Int = 0,
        var size: Int = 0,
        var sort: SortBean? = null

    ) : Parcelable {

        @Parcelize
        class SortBean(

            var sorted: Boolean = false,
            var unsorted: Boolean = false,
            var empty: Boolean = false,

            ) : Parcelable

        @Parcelize
        class ContentBean(

            var isSelected:Boolean,
            var id: String = "",
            var countryId: String = "",
            var name: String = "",
            var termUnit: String = "",
            var currencyType: String = "",
            var showStrategyId: String = "",
            var loanAppId: String = "",
            var created: Long = 0,
            var modified: Long = 0,
            var lastSyncTime: Long = 0,
            var enabled: Boolean = false,
            var connected: Boolean = false,
            var enableMultiPush: Boolean = false,
            var excludeSystemReLoan: Boolean = false,
            var pause: Boolean = false,
            var delayRepay: Boolean = false,
            var afterPaid: Boolean = false,
            var enableFeeDesc: Boolean = false,
            var customApi: Boolean = false,
            var pushGroupIds: ArrayList<PushGroupIdsBean> = arrayListOf(),
            var dayLimit: Int = 0,
            var dayCount: Int = 0,
            var firstLoanDayLimit: Int = 0,
            var firstLoanDayCount: Int = 0,
            var platformFirstLoanDayLimit: Int = 0,
            var platformFirstLoanDayCount: Int = 0,
            var term: Int = 0,
            var contractAmount: Double = 0.0,
            var amount: Double = 0.0,
            var serviceRate: Double = 0.0,
            var serviceAmount: Double = 0.0,
            var dayRate: Double = 0.0,
            var overdueRate: Double = 0.0,
            var rateAmount: Double = 0.0,
            var feeDescList: ArrayList<FeeDescListBean> = arrayListOf(),

            ) : Parcelable {

            @Parcelize
            class FeeDescListBean() : Parcelable

            @Parcelize
            class PushGroupIdsBean(

            ) : Parcelable

        }
    }
}
