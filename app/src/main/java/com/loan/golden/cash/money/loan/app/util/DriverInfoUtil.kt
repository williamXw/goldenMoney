package com.loan.golden.cash.money.loan.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Geocoder
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.PermissionUtils
import com.loan.golden.cash.money.loan.app.App
import com.loan.golden.cash.money.loan.data.param.DeviceInfoParam
import com.loan.golden.cash.money.loan.data.param.DeviceInfoParam.ModelBean.GeneralDataBean
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer
import java.net.NetworkInterface
import java.text.DecimalFormat
import java.util.Collections
import java.util.Locale
import java.util.TimeZone


object DriverInfoUtil {
    fun getOtherData(context: Activity): DeviceInfoParam.ModelBean.OtherDataBean {
        val sysPhotos = DeviceUtil.getSystemPhotoList(context)
        val isRoot = DeviceUtil.isRooted()
        val isEmulator = DeviceUtil.isEmulator()

        var body = DeviceInfoParam.ModelBean.OtherDataBean()
        try {
            var dbm = DeviceUtil.getCellInfo(context)["dbm"]
            if (dbm != null) {
                dbm = isNullText(dbm.toString())
            }
            val displayMetrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display?.getMetrics(displayMetrics)
            }
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            body = DeviceInfoParam.ModelBean.OtherDataBean(
                hasRoot = isRoot,
                lastBootTime = DeviceUtil.getLastBootTime(context),
                keyboard = DeviceUtil.getKeyboard(context).toString(),
                simulator = isEmulator,
                adbEnabled = DeviceUtil.isDevMode(context),
                dbm = dbm.toString(),
                imageNum = sysPhotos.size,
                screenWidth = width,
                screenHeight = height,
                screenDensity = displayMetrics.density,
                screenDensityDpi = displayMetrics.densityDpi,
                cpuNumber = DeviceUtil.getCpuNumCores(),
                appFreeMemory = DeviceUtil.getMemory(context)[2].toDouble(),
                appAvailableMemory = DeviceUtil.getMemory(context)[1].toDouble(),
                appMaxMemory = DeviceUtil.getMemory(context)[0].toDouble(),
                maxBattery = DeviceUtil.getBattery(context)[0],
                levelBattery = DeviceUtil.getBattery(context)[1],
                totalBootTimeWake = DeviceUtil.getOsTime(context)!![1],
                totalBootTime = DeviceUtil.getOsTime(context)!![0]
            )
        } catch (_: Exception) {
        }
        return body
    }

    fun getStorage(context: Activity): DeviceInfoParam.ModelBean.StorageBean {
        var body = DeviceInfoParam.ModelBean.StorageBean()
        try {
            body = DeviceInfoParam.ModelBean.StorageBean(
                ramTotalSize = isNullText(DeviceUtil.getRamTotalSize(context)),
                ramUsableSize = isNullText(DeviceUtil.getRamAvailSize(context)),
                memoryCardSize = isNullText(DeviceUtil.getSDInfo()?.get("totalSize").toString()),
                memoryCardSizeUse = isNullText(DeviceUtil.getSDInfo()?.get("useSize").toString()),
                mainStorage = isNullText(DeviceUtil.getRootDirectory()),
                externalStorage = isNullText(DeviceUtil.getExternalStorageDirectory()),
                internalStorageUsable = isNullText(
                    DeviceUtil.getAvailableInternalMemorySize().toString()
                ),
                internalStorageTotal = isNullText(
                    DeviceUtil.getTotalInternalMemorySize().toString()
                )
            )
        } catch (_: Exception) {
        }
        return body
    }

    fun getSimCard(context: Activity): DeviceInfoParam.ModelBean.SimCardBean {
        var body = DeviceInfoParam.ModelBean.SimCardBean()
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return body
                }
                mobileDataEnabled = tm.isDataEnabled
            }
            body = DeviceInfoParam.ModelBean.SimCardBean(
                countryIso = DeviceUtil.getSimCountryIso(context).toString(),
                serialNumber = DeviceUtil.getSerialNumber(),
                simCardReady = DeviceUtil.isSimCardReady(context).toString(),
                operator = tm.simOperator,
                operatorName = tm.simOperatorName,
                mobileDataEnabled = mobileDataEnabled,
                mobileData = DeviceUtil.isMobileData(context).toString(),
                dataNetworkType = DeviceUtil.getDataNetworkType(context).toString()
            )
        } catch (e: Exception) {
            return body
        }
        return body
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getHardware(context: Activity): DeviceInfoParam.ModelBean.HardwareBean {
        var body = DeviceInfoParam.ModelBean.HardwareBean()
        try {
            body = DeviceInfoParam.ModelBean.HardwareBean(
                deviceName = DeviceUtil.getDriverDevice(),
                brand = getBrand(),
                product = DeviceUtil.getDriverProduct(),
                model = getModel(),
                release = DeviceUtil.getDriverOsVersion(),
                cpuType = DeviceUtil.getCpuName(),
                sdkVersion = DeviceUtil.getDriverSDKVersion(),
                serialNumber = DeviceUtil.getSerialNumber(),
                physicalSize = getPhysicalSize(context).toString(),
                manufacturer = Build.MANUFACTURER,
                display = Build.DISPLAY,
                fingerprint = Build.FINGERPRINT,
                abis = getAbis(context),
                board = Build.BOARD,
                buildId = Build.ID,
                host = Build.HOST,
                type = Build.TYPE,
                buildUser = Build.USER,
                cpuAbi = Build.CPU_ABI,
                cpuAbi2 = Build.CPU_ABI2,
                bootloader = Build.BOOTLOADER,
                hardware = Build.HARDWARE,
                baseOS = Build.VERSION.BASE_OS,
                radioVersion = DeviceUtil.getRadioVersion(),
                sdCardPath = Environment.getExternalStorageDirectory().toString(),
                internalTotalSize = DeviceUtil.getTotalInternalMemorySize(),
                internalAvailableSize = DeviceUtil.getAvailableInternalMemorySize(),
                externalTotalSize = DeviceUtil.getTotalExternalMemorySize(),
                externalAvailableSize = DeviceUtil.getAvailableExternalMemorySize(),
                sdCardInfo = DeviceUtil.getSDInfo() as MutableMap<String, Long>
            )
        } catch (_: Exception) {
        }
        return body
    }

    fun getDivFile(context: Activity): DeviceInfoParam.ModelBean.DevFileBean {
        var body = DeviceInfoParam.ModelBean.DevFileBean()
        try {
            val audioInternalDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            val audioInternalFileCount =
                if (audioInternalDir.listFiles() == null) 0 else audioInternalDir.listFiles().size

            val audioExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            val audioExternalFileCount =
                if (audioExternalDir!!.listFiles() == null) 0 else audioExternalDir.listFiles()?.size

            // 获取图片内部存储文件个数
            val imagesInternalDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            val imagesInternalFileCount =
                if (imagesInternalDir.listFiles() == null) 0 else imagesInternalDir.listFiles()?.size

            // 获取图片外部存储文件个数
            val imagesExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imagesExternalFileCount =
                if (imagesExternalDir!!.listFiles() == null) 0 else imagesExternalDir.listFiles()?.size

            // 获取视频内部存储文件个数
            val videoInternalDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
            val videoInternalFileCount =
                if (videoInternalDir.listFiles() == null) 0 else videoInternalDir.listFiles()?.size

            // 获取视频外部存储文件个数
            val videoExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
            val videoExternalFileCount =
                if (videoExternalDir!!.listFiles() == null) 0 else videoExternalDir.listFiles()?.size
            // 获取下载文件个数
            val downloadDirPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
            val downloadDir = File(downloadDirPath)
            val downloadFiles = downloadDir.listFiles()
            val downloadFileCount = downloadFiles?.size ?: 0

            body = audioExternalFileCount?.let {
                imagesExternalFileCount?.let { it1 ->
                    imagesInternalFileCount?.let { it2 ->
                        videoExternalFileCount?.let { it3 ->
                            videoInternalFileCount?.let { it4 ->
                                DeviceInfoParam.ModelBean.DevFileBean(
                                    audioExternal = it,
                                    audioInternal = audioInternalFileCount,
                                    downloadFiles = downloadFileCount,
                                    imagesExternal = it1,
                                    imagesInternal = it2,
                                    videoExternal = it3,
                                    videoInternal = it4,
                                )
                            }
                        }
                    }
                }
            }!!
        } catch (e: Exception) {

        }
        return body
    }

    fun getAbis(context: Activity?): ArrayList<String> {
        val ret: ArrayList<String> = ArrayList()
        if (Build.SUPPORTED_ABIS != null) {
            for (s in Build.SUPPORTED_ABIS) {
                ret.add(s)
            }
        }
        return ret
    }

    fun getPublicIp(): DeviceInfoParam.ModelBean.PublicIpBean {
        var body = DeviceInfoParam.ModelBean.PublicIpBean()
        try {
            // 内网地址
            val niList = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (ni in niList) {
                val iaList = Collections.list(ni.inetAddresses)
                for (address in iaList) {
                    if (!address.isLoopbackAddress && !address.isLinkLocalAddress) {
                        body = address.hostAddress?.let {
                            DeviceInfoParam.ModelBean.PublicIpBean(
                                intranetIp = it
                            )
                        }!!
                        break
                    }
                }
            }
        } catch (_: Exception) {
        }
        return body
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Activity): DeviceInfoParam.ModelBean.LocationBean {
        var body = DeviceInfoParam.ModelBean.LocationBean()
        var latitude = -0.0
        var longitude = -0.0
        try {
            if (PermissionUtils.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val providers = locationManager.getProviders(true)
                if (providers.contains(LocationManager.GPS_PROVIDER)) {
                    val criteria = Criteria()
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    criteria.isAltitudeRequired = false
                    criteria.isBearingRequired = false
                    criteria.isCostAllowed = true
                    criteria.powerRequirement = Criteria.POWER_LOW
                    val provider = locationManager.getBestProvider(criteria, true)
                    var location = provider?.let { locationManager.getLastKnownLocation(it) }

                    if (null != location) {
                        latitude = location.latitude
                        longitude = location.longitude
                    } else {
                        location =
                            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (null != location) {
                            latitude = location.latitude
                            longitude = location.longitude
                        }
                    }
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val df = DecimalFormat()
                    df.maximumFractionDigits = 3
                    val lat = df.format(latitude).toDouble()
                    val lon = df.format(longitude).toDouble()
                    val addresses = geocoder.getFromLocation(lat, lon, 1)
                    if (addresses!!.size > 0) {
                        val address = addresses[0]
                        country = address.countryName
                        province = address.adminArea
                        city = address.subAdminArea
                        bigDirect = address.locality
                        smallDirect = address.thoroughfare
                        detailed = address.getAddressLine(0)
                    }

                } else {
                    val criteria = Criteria()
                    criteria.isCostAllowed = false
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    criteria.isAltitudeRequired = false
                    criteria.isBearingRequired = false
                    criteria.isCostAllowed = false
                    criteria.powerRequirement = Criteria.POWER_LOW
                    val providerName = locationManager.getBestProvider(criteria, true)
                    if (providerName != null) {
                        val location = locationManager.getLastKnownLocation(providerName)
                        if (location != null) {
                            try {
                                latitude = location.latitude
                                longitude = location.longitude
                                val geocoder = Geocoder(context, Locale.getDefault())
                                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                                if (addresses!!.size > 0) {
                                    val address = addresses[0]
                                    country = address.countryName
                                    province = address.adminArea
                                    city = address.subAdminArea
                                    bigDirect = address.locality
                                    smallDirect = address.thoroughfare
                                    detailed = address.getAddressLine(0)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        return body
                    }
                }
            }
        } catch (_: Exception) {
        }
        body = DeviceInfoParam.ModelBean.LocationBean(
            country = country,
            province = province,
            city = city,
            largeDistrict = isNullText(bigDirect),
            smallDistrict = isNullText(smallDirect),
            address = isNullText(detailed),
            gps = DeviceInfoParam.ModelBean.LocationBean.GpsBean(
                latitude = latitude,
                longitude = longitude
            )
        )
        return body
    }

    fun getCurrentWifi(context: Activity): DeviceInfoParam.ModelBean.CurrWifiBean {
        var body = DeviceInfoParam.ModelBean.CurrWifiBean()
        try {
            @SuppressLint("WifiManagerLeak") val wifiManager =
                context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            @SuppressLint("MissingPermission") val wifiInfo = wifiManager.connectionInfo
            if (null != wifiInfo) {
                body = DeviceInfoParam.ModelBean.CurrWifiBean(
                    name = wifiInfo.ssid,
                    bssid = wifiInfo.bssid,
                    ssid = wifiInfo.ssid,
                    mac = wifiInfo.macAddress,
                    ip = DeviceUtil.intToIpAddress(wifiInfo.ipAddress),
                )
            }
        } catch (e: Exception) {
        }
        return body
    }

    fun getConfigWifi(context: Activity): ArrayList<DeviceInfoParam.ModelBean.ConfigWifiBean> {
        var mList: ArrayList<DeviceInfoParam.ModelBean.ConfigWifiBean> = arrayListOf()
        try {
            @SuppressLint("WifiManagerLeak") val wifiManager =
                context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            @SuppressLint("MissingPermission") val scanResults = wifiManager.scanResults
            if (scanResults != null) {
                for (index in 0 until scanResults.size) {
                    val scanResult = scanResults[index]
                    mList.add(
                        DeviceInfoParam.ModelBean.ConfigWifiBean(
                            name = isNullText(scanResult.SSID),
                            bssid = isNullText(scanResult.BSSID),
                            ssid = isNullText(scanResult.SSID),
                            mac = isNullText(scanResult.BSSID),
                        )
                    )
                }
            }
        } catch (_: Exception) {
        }
        return mList
    }

    fun getBatteryStatus(context: Activity): DeviceInfoParam.ModelBean.BatteryStatusBean {
        var body = DeviceInfoParam.ModelBean.BatteryStatusBean()
        try {
            val batteryInfoIntent =
                context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val level = batteryInfoIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)//电量（0-100）
            val scale = batteryInfoIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val plugged = batteryInfoIntent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val batteryPct = level?.div(scale?.toFloat()!!)?.toDouble()
            var isUsbCharge = false
            var isCharging = false
            var isAcCharge = false
            when (plugged) {
                BatteryManager.BATTERY_PLUGGED_AC -> {
                    isAcCharge = true
                    isCharging = true
                }

                BatteryManager.BATTERY_PLUGGED_USB -> {
                    isUsbCharge = true
                    isCharging = true
                }
            }
            batteryPct?.let {
                body = DeviceInfoParam.ModelBean.BatteryStatusBean(
                    batteryPct = it,
                    isUsbCharge = isUsbCharge,
                    isAcCharge = isAcCharge,
                    isCharging = isCharging,
                )
            }!!
        } catch (e: Exception) {
        }
        return body
    }

    fun getGeneralData(context: Activity): GeneralDataBean {
        return GeneralDataBean(
            andId = getAndroidID(context).toString(),
            phoneType = getPhoneType(context).toString(),
            mnc = getMNC(context).toString(),
            gaid = App.aDid,
            dns = DeviceUtil.getLocalDNS().toString(),
            language = getOsLanguage(context).toString(),
            mcc = getMCC(context).toString(),
            localeIso3Language = getISO3Language(context).toString(),
            localeDisplayLanguage = getLocaleDisplayLanguage().toString(),
            imei = getDriverIMIE(context),
            phoneNumber = getPhone(context).toString(),
            networkOperator = getNetworkOperator(context).toString(),
            networkType = getNetworkType(context),
            timeZoneId = getTimeZoneId().toString(),
            localeIso3Country = getISO3Country(context).toString()
        )
    }

    fun isNullText(text: String?): String {
        if (null == text) {
            return ""
        }
        return if (TextUtils.isEmpty(text)) {
            ""
        } else text
    }

    private var detailed = ""
    private var smallDirect = ""
    private var bigDirect = ""
    private var city = ""
    private var province = ""
    private var country = ""
    private var mobileDataEnabled: Boolean = false
    val cpuName: String
        get() {
            try {
                val fr = FileReader("/proc/cpuinfo") //__xor__
                val br = BufferedReader(fr)
                val text = br.readLine()
                val array = text.split(":\\s+".toRegex(), limit = 2).toTypedArray()
                for (i in array.indices) {
                }
                return array[1]
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }


    fun getPhysicalSize(context: Context): String? {
        return try {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val defaultDisplay = windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            defaultDisplay.getMetrics(displayMetrics)
            java.lang.Double.toString(
                Math.sqrt(
                    Math.pow(
                        (displayMetrics.heightPixels.toFloat() / displayMetrics.ydpi).toDouble(),
                        2.0
                    ) + Math.pow(
                        (displayMetrics.widthPixels.toFloat() / displayMetrics.xdpi).toDouble(),
                        2.0
                    )
                )
            )
        } catch (e: Exception) {
            null
        }
    }

    fun getOsLanguage(context: Context): String? {
        return try {
            val locale = context.resources.configuration.locale
            locale.language
        } catch (e: Exception) {
            null
        }
    }

    fun getISO3Language(context: Context): String? {
        return try {
            val locale = context.resources.configuration.locale
            locale.isO3Language
        } catch (e: Exception) {
            null
        }
    }

    fun getISO3Country(context: Context): String? {
        return try {
            val locale = context.resources.configuration.locale
            locale.isO3Country
        } catch (e: Exception) {
            null
        }
    }

    fun getTimeZoneId(): String? {
        return try {
            TimeZone.getDefault().id
        } catch (e: Exception) {
            null
        }
    }

    fun getLocaleDisplayLanguage(): String? {
        return try {
            Locale.getDefault().displayLanguage
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    fun getDriverIMIE(context: Context): String {
        return try {
            val flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
            if (!flag) {
                getTelephonyManager(context)!!.deviceId
            } else ""
        } catch (e: Exception) {
            ""
        }
    }

    private var telephonyManager: TelephonyManager? = null
    private fun getTelephonyManager(context: Context): TelephonyManager? {
        if (telephonyManager == null) {
            telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager
    }

    fun getNetworkOperator(context: Context): String? {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.networkOperator
        } catch (e: Exception) {
            null
        }
    }

    fun getNetworkType(context: Context): String {
        try {
            val teleMan = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val networkType = teleMan.networkType
                when (networkType) {
                    TelephonyManager.NETWORK_TYPE_1xRTT -> return "1xRTT" //__xor__
                    TelephonyManager.NETWORK_TYPE_CDMA -> return "CDMA" //__xor__
                    TelephonyManager.NETWORK_TYPE_EDGE -> return "EDGE" //__xor__
                    TelephonyManager.NETWORK_TYPE_EVDO_B -> return "EVDO rev. B" //__xor__
                    TelephonyManager.NETWORK_TYPE_GPRS -> return "GPRS" //__xor__
                    TelephonyManager.NETWORK_TYPE_HSDPA -> return "HSDPA" //__xor__
                    TelephonyManager.NETWORK_TYPE_HSPA -> return "HSPA" //__xor__
                    TelephonyManager.NETWORK_TYPE_HSPAP -> return "HSPA+" //__xor__
                    TelephonyManager.NETWORK_TYPE_EHRPD -> return "eHRPD" //__xor__
                    TelephonyManager.NETWORK_TYPE_HSUPA -> return "HSUPA" //__xor__
                    TelephonyManager.NETWORK_TYPE_IDEN -> return "iDen" //__xor__
                    TelephonyManager.NETWORK_TYPE_LTE -> return "LTE" //__xor__
                    TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "EVDO rev. 0" //__xor__
                    TelephonyManager.NETWORK_TYPE_EVDO_A -> return "EVDO rev. A" //__xor__
                    TelephonyManager.NETWORK_TYPE_UMTS -> return "UMTS" //__xor__
                    TelephonyManager.NETWORK_TYPE_UNKNOWN -> return "Unknown" //__xor__
                }
            }
        } catch (e: Exception) {
        }
        return "Unknown" //__xor__
    }

    fun getBrand(): String {
        try {
            return Build.BRAND
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getModel(): String {
        try {
            return Build.MODEL
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getMNC(context: Context): String? {
        return try {
            val cfg = context.resources.configuration
            cfg.mnc.toString() + ""
        } catch (e: Exception) {
            null
        }
    }

    private const val marshmallowMacAddress = "02:00:00:00:00:00"
    private const val fileAddressMac = "/sys/class/net/wlan0/address" //__xor__

    private fun getAdressMacByInterface(): String? {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (nif.name.equals("wlan0", ignoreCase = true)) {
                    val macBytes = nif.hardwareAddress ?: return ""
                    val res1 = StringBuilder()
                    for (b in macBytes) {
                        res1.append(String.format("%02X:", b))
                    }
                    if (res1.length > 0) {
                        res1.deleteCharAt(res1.length - 1)
                    }
                    return res1.toString()
                }
            }
        } catch (e: Exception) {
        }
        return null
    }

    @Throws(Exception::class)
    private fun getAddressMacByFile(wifiMan: WifiManager): String {
        val ret: String
        val wifiState = wifiMan.wifiState
        wifiMan.isWifiEnabled = true
        val fl = File(fileAddressMac)
        val fin = FileInputStream(fl)
        ret = crunchifyGetStringFromStream(fin)
        fin.close()
        val enabled = WifiManager.WIFI_STATE_ENABLED == wifiState
        wifiMan.isWifiEnabled = enabled
        return ret
    }

    @Throws(IOException::class)
    private fun crunchifyGetStringFromStream(crunchifyStream: InputStream?): String {
        return if (crunchifyStream != null) {
            val crunchifyWriter: Writer = StringWriter()
            val crunchifyBuffer = CharArray(2048)
            try {
                val crunchifyReader: Reader =
                    BufferedReader(InputStreamReader(crunchifyStream, "UTF-8"))
                var counter: Int
                while (crunchifyReader.read(crunchifyBuffer).also { counter = it } != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter)
                }
            } finally {
                crunchifyStream.close()
            }
            crunchifyWriter.toString()
        } else {
            "No Contents" //__xor__
        }
    }

    private fun getPhone(context: Context): String? {
        var tel1: String? = ""
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            @SuppressLint("MissingPermission") val tel = tm.line1Number //获取本机号码
            tel1 = tel
        } catch (e: Exception) {
        }
        if (TextUtils.isEmpty(tel1)) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    var subscriptionManager: SubscriptionManager? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        subscriptionManager = SubscriptionManager.from(context)
                        val subscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
                        if (subscriptionInfoList != null && subscriptionInfoList.size > 0) {
                            for (info in subscriptionInfoList) {
                                if (!TextUtils.isEmpty(info.number)) {
                                    tel1 = info.number
                                    break
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
        if (TextUtils.isEmpty(tel1)) {
            try {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val telephonyClass = Class.forName(telephonyManager.javaClass.name)
                val getITelephonyMethod = telephonyClass.getDeclaredMethod("getITelephony")
                getITelephonyMethod.isAccessible = true
                val iTelephony = getITelephonyMethod.invoke(telephonyManager)
                val iTelephonyClass = Class.forName(iTelephony.javaClass.name)
                val getSubscriberIdMethod = iTelephonyClass.getDeclaredMethod("getSubscriberId")
                getSubscriberIdMethod.isAccessible = true
                val subscriberId = getSubscriberIdMethod.invoke(iTelephony) as String
                if (subscriberId != null && subscriberId.length > 3) {
                    tel1 = subscriberId.substring(3)
                }
            } catch (e: Exception) {
            }
        }
        if (TextUtils.isEmpty(tel1)) {
            try {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val operatorNumeric = telephonyManager.simOperator
                if (operatorNumeric != null && operatorNumeric.length > 0) { // 判断是否为中国电信或中国卫通运营商
                    val c = Class.forName("android.os.SystemProperties")
                    val get = c.getMethod("get", String::class.java)
                    val phoneNumber = get.invoke(c, "ro.cdma.icc_line1") as String
                    if (phoneNumber != null && phoneNumber.length > 0) {
                        tel1 = phoneNumber
                    }
                }
            } catch (e: Exception) {
            }
        }
        return tel1
    }

    private fun getAndroidID(context: Context): String? {
        return try {
            //LogUtil.e("androidID: " + androidID);
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            null
        }
    }

    fun getMCC(context: Context): String? {
        return try {
            val cfg = context.resources.configuration
            cfg.mcc.toString() + ""
        } catch (e: Exception) {
            null
        }
    }

    fun getPhoneType(context: Context): String? {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var phoneTypeStr = ""
            val phoneType = tm.phoneType
            when (phoneType) {
                TelephonyManager.PHONE_TYPE_CDMA -> phoneTypeStr = "CDMA" //__xor__
                TelephonyManager.PHONE_TYPE_GSM -> phoneTypeStr = "GSM" //__xor__
                TelephonyManager.PHONE_TYPE_SIP -> phoneTypeStr = "SIP" //__xor__
                TelephonyManager.PHONE_TYPE_NONE -> phoneTypeStr = "None" //__xor__
            }
            phoneTypeStr
        } catch (e: Exception) {
            null
        }
    }

    fun checkSelfPermission(context: Context?, permission: String?): Boolean {
        return ActivityCompat.checkSelfPermission(
            context!!,
            permission!!
        ) != PackageManager.PERMISSION_GRANTED
    }
}