package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/20 9:14
 * @Describe    :
 */
@Parcelize
data class AppInfoParam(

    var model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var deviceApps: ArrayList<DeviceAppsBean> = arrayListOf()

    ) : Parcelable {

        @Parcelize
        class DeviceAppsBean(

            var appName: String = "",
            var packageName: String = "",
            var version: String = "",
            var versionCode: String = "",
            var appType: String = "",
            var flags: String = "",
            var installTime: String = "",
            var updateTime: String = "",

            ) : Parcelable

    }

}
