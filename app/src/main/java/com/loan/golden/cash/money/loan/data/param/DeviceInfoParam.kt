package com.loan.golden.cash.money.loan.data.param

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author      : hxw
 * @Date        : 2023/10/20 13:15
 * @Describe    :
 */
@Parcelize
class DeviceInfoParam(

    val model: ModelBean? = null

) : Parcelable {

    @Parcelize
    class ModelBean(

        var generalData: GeneralDataBean? = null,
        var hardware: HardwareBean? = null,
        var publicIp: PublicIpBean? = null,
        var simCard: SimCardBean? = null,
        var otherData: OtherDataBean? = null,
        var location: LocationBean? = null,
        var storage: StorageBean? = null,
        var devFile: DevFileBean? = null,
        var batteryStatus: BatteryStatusBean? = null,
        var currWifi: CurrWifiBean? = null,
        var configWifi: ArrayList<ConfigWifiBean>? = arrayListOf(),

        ) : Parcelable {

        @Parcelize
        class ConfigWifiBean(

            var name: String = "",//路由名称
            var bssid: String = "",
            var ssid: String = "",
            var mac: String = "",
            var ip: String = "",

            ) : Parcelable

        @Parcelize
        class CurrWifiBean(

            var name: String = "",//路由名称
            var bssid: String = "",
            var ssid: String = "",
            var mac: String = "",
            var ip: String = "",

            ) : Parcelable

        @Parcelize
        class BatteryStatusBean(

            var batteryPct: Double = 0.0,//当前电量 百分比
            var isUsbCharge: Boolean = false,//是否USB连接
            var isAcCharge: Boolean = false,//是否连接充电器
            var isCharging: Boolean = false,//是否充电

        ) : Parcelable

        @Parcelize
        class DevFileBean(

            var audioExternal: Int = 0,//音频外部文件个数
            var audioInternal: Int = 0,//音频内部文件个数
            var downloadFiles: Int = 0,//下载的文件个数
            var imagesExternal: Int = 0,//图片外部文件个数
            var imagesInternal: Int = 0,//图片内部文件个数
            var videoExternal: Int = 0,//视频外部文件个数
            var videoInternal: Int = 0,//视频内部文件个数

        ) : Parcelable

        @Parcelize
        class StorageBean(

            var ramTotalSize: String = "",//运行内存总大小
            var ramUsableSize: String = "",//运行内存可用大小
            var memoryCardSize: String = "",//内存卡大小
            var memoryCardSizeUse: String = "",//已用内存卡大小
            var mainStorage: String = "",//储存区域路径
            var externalStorage: String = "",//外部存储器路径
            var internalStorageUsable: String = "",//手机内部总存储空间可以大小
            var internalStorageTotal: String = "",//手机内部总存储空间总大小

        ) : Parcelable

        @Parcelize
        class LocationBean(

            var country: String = "",
            var province: String = "",
            var city: String = "",
            var largeDistrict: String = "",
            var smallDistrict: String = "",
            var address: String = "",
            var gps: GpsBean? = null,

            ) : Parcelable {

            @Parcelize
            class GpsBean(

                var latitude: Double = 0.0,
                var longitude: Double = 0.0,

                ) : Parcelable

        }

        @Parcelize
        class OtherDataBean(

            var hasRoot: Boolean = false,//是否root
            var lastBootTime: Long = 0,//手机运行时间
            var keyboard: String = "",//获取当前设备所关联的键盘的类型
            var simulator: Boolean = false,//是否是模拟器
            var adbEnabled: Boolean = false,//是否启用调试模式
            var dbm: String = "",//手机信号强弱
            var imageNum: Int = 0,//手机相片数量
            var screenWidth: Int = 0,
            var screenHeight: Int = 0,
            var screenDensity: Float = 0.0F,
            var screenDensityDpi: Int = 0,
            var cpuNumber: Int = 0,
            var appFreeMemory: Double = 0.0,//app可释放内存 单位M
            var appAvailableMemory: Double = 0.0,//app当前可用内存 单位M
            var appMaxMemory: Double = 0.0,//app最大占用内存 单位M
            var maxBattery: Int = 0,//最大电量
            var levelBattery: Int = 0,//剩余电量
            var totalBootTimeWake: Long = 0,//开机总时长 非休眠时间 单位微秒
            var totalBootTime: Long = 0,//开机总时长 单位微秒

        ) : Parcelable


        @Parcelize
        class SimCardBean(

            var countryIso: String = "",
            var serialNumber: String = "",
            var simCardReady: String = "",
            var operator: String = "",
            var operatorName: String = "",
            var mobileDataEnabled: Boolean = false,
            var mobileData: String = "",
            var dataNetworkType: String = "",

            ) : Parcelable

        @Parcelize
        class PublicIpBean(

            var internetIp: String = "",
            var intranetIp: String = "",
            var internetIp2: String = "",

            ) : Parcelable

        @Parcelize
        class HardwareBean(

            var deviceName: String = "",
            var brand: String = "",
            var product: String = "",
            var model: String = "",
            var release: String = "",
            var cpuType: String = "",
            var sdkVersion: String = "",
            var serialNumber: String = "",
            var physicalSize: String = "",
            var manufacturer: String = "",
            var display: String = "",
            var fingerprint: String = "",
            var abis: ArrayList<String> = arrayListOf(),
            var board: String = "",
            var buildId: String = "",
            var host: String = "",
            var type: String = "",
            var buildUser: String = "",
            var cpuAbi: String = "",
            var cpuAbi2: String = "",
            var bootloader: String = "",
            var hardware: String = "",
            var baseOS: String = "",
            var radioVersion: String = "",
            var sdCardPath: String = "",
            var internalTotalSize: Long = 0,
            var internalAvailableSize: Long = 0,
            var externalTotalSize: Long = 0,
            var externalAvailableSize: Long = 0,
            var sdCardInfo: MutableMap<String, Long> = mutableMapOf(),

            ) : Parcelable

        @Parcelize
        class GeneralDataBean(

            var andId: String = "",//安卓id
            var phoneType: String = "",//网络通讯标准 (CDMA,GSM,SIP,None)
            var mnc: String = "",//移动信号的网络码
            var gaid: String = "",//google广告ID
            var dns: String = "",
            var language: String = "",//当前系统语言
            var mcc: String = "",//得到移动信号的国家码
            var localeIso3Language: String = "",//国家移动设备识别码
            var localeDisplayLanguage: String = "",//本地国家代码
            var imei: String = "",//入网标识
            var phoneNumber: String = "",//获取设备手机号
            var networkOperator: String = "",//网络运营者
            var networkType: String = "",//当前网络类型 (NETWORK_WIFI,NETWORK_4G,NETWORK_3G,NETWORK_2G,NETWORK_UNKNOWN)
            var timeZoneId: String = "",//主机所处时区的对象
            var localeIso3Country: String = "",//国家语言环境

        ) : Parcelable

    }

}