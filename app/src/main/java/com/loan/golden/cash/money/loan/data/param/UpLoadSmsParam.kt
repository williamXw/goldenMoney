package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *  author : huxiaowei
 *  date : 2023/10/22 16:02
 *  description :
 */
@Parcelize
class UpLoadSmsParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        val list: ArrayList<ListBean> = arrayListOf()

    ) : Parcelable {

        @Parcelize
        class ListBean(

            var msgNo: Long = 0,//短消息序号
            var threadNo: Long = 0,//对话的序号
            var address: String = "",//发件人地址
            var personName: String = "",//发件人
            var protocol: Int = 0,//协议 0 SMS_RPOTO, 1 MMS_PROTO
            var read: Int = 0,//是否阅读 0未读， 1已读
            var type: Int = 0,//类型 1是接收到的，2是已发出
            var status: Int = 0, //状态 -1接收，0 complete, 64 pending, 128 failed
            var body: String = "",//短消息内容
            var serviceCenter: String = "",//短信服务中心号码编号
            var date: Long = 0,//日期

        ) : Parcelable

    }

}