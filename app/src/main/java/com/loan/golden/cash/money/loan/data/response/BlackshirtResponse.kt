package com.loan.golden.cash.money.loan.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/11 11:45
 * @Describe    :
 */
@Parcelize
data class BlackshirtResponse(

    var status: Int = 0,
    var time: Long = 0,
    var maxAge: Int = 0,
    var message: String = "",
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
        class SortBean() : Parcelable

        @Parcelize
        class ContentBean(

            var id: String = "",
            var countryId: String = "",
            var userId: String = "",
            var loanAppId: String = "",
            var productId: String = "",
            var channelId: String = "",
            var termUnit: String = "",
            var currencyType: String = "",
            var mobile: String = "",
            var idCard: String = "",
            var deviceInfoId: String = "",
            var status: String = "",
            var ip: String = "",
            var statusName: String = "",
            var statusColor: String = "",
            var note: String = "",
            var statusNote: String = "",
            var created: Long = 0,
            var modified: Long = 0,
            var change: Boolean = false,
            var attributed: Boolean = false,
            var fakeFinished: Boolean = false,
            var noDelayRepay: Boolean = false,
            var urlRepay: Boolean = false,
            var delay: Boolean = false,
            var loan: Boolean = false,
            var firstLoan: Boolean = false,
            var finance: FinanceBean? = null,
            var term: Int = 0,
            var statusSort: Int = 0,
            var amount: Double = 0.0,
            var reloanInfo: ReloanInfoBean? = null,
            var reloanTypes: ArrayList<String> = arrayListOf(),
            var flowInfo: ArrayList<FlowInfoBean> = arrayListOf(),
            var promoteStrategyIds: ArrayList<PromoteStrategyIdsBean> = arrayListOf(),
            var userPhases: ArrayList<String> = arrayListOf(),
            var product: ProductBean? = null,
            var ossFiles: OssFilesBean? = null,
            var ossRecordCounts: OssRecordCountsBean? = null,
            var statusTimeMap: StatusTimeMapBean? = null,
            var orderTagIds: ArrayList<String> = arrayListOf(),

            ) : Parcelable {

            @Parcelize
            class StatusTimeMapBean() : Parcelable

            @Parcelize
            class OssRecordCountsBean() : Parcelable

            @Parcelize
            class OssFilesBean() : Parcelable

            @Parcelize
            class ProductBean(

                var id: String = "",
                var countryId: String = "",
                var name: String = "",
                var termUnit: String = "",
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
                var pushGroupIds: ArrayList<String> = arrayListOf(),
                var dayLimit: Double = 0.0,
                var dayCount: Double = 0.0,
                var firstLoanDayLimit: Double = 0.0,
                var firstLoanDayCount: Double = 0.0,
                var platformFirstLoanDayLimit: Double = 0.0,
                var platformFirstLoanDayCount: Double = 0.0,
                var amount: Double = 0.0,
                var serviceRate: Double = 0.0,
                var serviceAmount: Double = 0.0,
                var dayRate: Double = 0.0,
                var overdueRate: Double = 0.0,
                var rateAmount: Double = 0.0,
                var contractAmount: Double = 0.0,
                var term: Int = 0,
                var feeDescList: ArrayList<String> = arrayListOf(),

                ) : Parcelable

            @Parcelize
            class PromoteStrategyIdsBean() : Parcelable

            @Parcelize
            class FlowInfoBean() : Parcelable

            @Parcelize
            class ReloanInfoBean() : Parcelable

            @Parcelize
            class FinanceBean(

                var thirdCost: Double = 0.0,
                var messageCost: Double = 0.0,
                var payCost: Double = 0.0,
                var loanCost: Double = 0.0,
                var loanRepay: Double = 0.0,

                ) : Parcelable
        }
    }
}
