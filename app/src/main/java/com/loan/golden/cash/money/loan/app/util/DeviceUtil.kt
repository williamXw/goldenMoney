package com.loan.golden.cash.money.loan.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.CellInfo
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import com.loan.golden.cash.money.loan.app.util.DriverInfoUtil2.checkSelfPermission
import me.hgj.mvvmhelper.base.appContext
import java.io.BufferedReader
import java.io.File
import java.io.FileFilter
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Pattern


/**
 * created by :
 * @date : 20220915
 * Describe :
 */
object DeviceUtil {
    /**
     * 获取app的名称
     * @param context
     * @return
     */
    fun getAppName(context: Context): String {
        var appName = ""
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            appName = context.resources.getString(labelRes)
        } catch (e: Throwable) {
            e.printStackTrace();
        }
        return appName;
    }

    //获取软件版本号，对应AndroidManifest.xml下android:versionCode
    fun getVersionCode(activity: Activity): Int {
        var versionCodes = 0
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCodes =
                activity.packageManager.getPackageInfo(activity.packageName, 0).versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCodes
    }

    /**
     * 获取版本号名称
     *
     * @param
     * @return
     */
    fun getVerName(): String {
        var verName = ""
        try {
            verName =
                appContext.packageManager.getPackageInfo(appContext.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verName
    }

    /**
     * Return the version name of device's system.
     *
     * @return the version name of device's system
     */
    val sDKVersionName: String
        get() = Build.VERSION.RELEASE

    /**
     * Return version code of device's system.
     *
     * @return version code of device's system
     */
    val sDKVersionCode: Int
        get() = Build.VERSION.SDK_INT

    /**
     * Return the android id of device.
     *
     * @return the android id of device
     */
    fun getAndroidId(context: Context): String {
        val id = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        return if ("9774d56d682e549c" == id) "" else id ?: ""
    }

    /**
     * Return the manufacturer of the product/hardware.
     *
     * e.g. Xiaomi
     *
     * @return the manufacturer of the product/hardware
     */
    val manufacturer: String
        get() = Build.MANUFACTURER

    /**
     * Return the model of device.
     *
     * e.g. MI2SC
     *
     * @return the model of device
     */
    val model: String
        get() {
            var model = Build.MODEL
            model = model?.trim { it <= ' ' }?.replace("\\s*".toRegex(), "") ?: ""
            return model
        }

    /**
     * Return an ordered list of ABIs supported by this device. The most preferred ABI is the first
     * element in the list.
     *
     * @return an ordered list of ABIs supported by this device
     */
    val aBIs: Array<String>
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Build.SUPPORTED_ABIS
        } else {
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
            } else arrayOf(Build.CPU_ABI)
        }
    private val KEY_UDID = "KEY_UDID"

    private val udid: String? = null
    /**
     * Return the unique device id.
     * <pre>{1}{UUID(macAddress)}</pre>
     * <pre>{2}{UUID(androidId )}</pre>
     * <pre>{9}{UUID(random    )}</pre>
     *
     * @return the unique device id
     */
    //    public static String getUniqueDeviceId() {
    //        return getUniqueDeviceId("", true);
    //    }
    /**
     * Return the unique device id.
     * <pre>android 10 deprecated {prefix}{1}{UUID(macAddress)}</pre>
     * <pre>{prefix}{2}{UUID(androidId )}</pre>
     * <pre>{prefix}{9}{UUID(random    )}</pre>
     *
     * @param prefix The prefix of the unique device id.
     * @return the unique device id
     */
    //    public static String getUniqueDeviceId(String prefix) {
    //        return getUniqueDeviceId(prefix, true);
    //    }
    /**
     * Return the unique device id.
     * <pre>{1}{UUID(macAddress)}</pre>
     * <pre>{2}{UUID(androidId )}</pre>
     * <pre>{9}{UUID(random    )}</pre>
     *
     * @param useCache True to use cache, false otherwise.
     * @return the unique device id
     */
    //    public static String getUniqueDeviceId(boolean useCache) {
    //        return getUniqueDeviceId("", useCache);
    //    }
    /**
     * Return the unique device id.
     * <pre>android 10 deprecated {prefix}{1}{UUID(macAddress)}</pre>
     * <pre>{prefix}{2}{UUID(androidId )}</pre>
     * <pre>{prefix}{9}{UUID(random    )}</pre>
     *
     * @param prefix The prefix of the unique device id.
     * //     * @param useCache True to use cache, false otherwise.
     * @return the unique device id
     */
    //    public static String getUniqueDeviceId(String prefix, boolean useCache) {
    //        if (!useCache) {
    //            return getUniqueDeviceIdReal(prefix);
    //        }
    //        if (udid == null) {
    //            synchronized (DeviceUtil.class) {
    //                if (udid == null) {
    //                    UtilsBridge utilsBridge = new UtilsBridge(activity);
    //                    final String id = utilsBridge.getSpUtils4Utils().getString(KEY_UDID, null);
    //                    if (id != null) {
    //                        udid = id;
    //                        return udid;
    //                    }
    //                    return getUniqueDeviceIdReal(prefix);
    //                }
    //            }
    //        }
    //        return udid;
    //    }
    //    private static String getUniqueDeviceIdReal(String prefix) {
    //        try {
    //            final String androidId = getAndroidID();
    //            if (!TextUtils.isEmpty(androidId)) {
    //                return saveUdid(prefix + 2, androidId);
    //            }
    //
    //        } catch (Exception ignore) {/**/}
    //        return saveUdid(prefix + 9, "");
    //    }
    //    private static String saveUdid(String prefix, String id) {
    //        udid = getUdid(prefix, id);
    //        UtilsBridge utilsBridge = new UtilsBridge(activity);
    //        utilsBridge.getSpUtils4Utils().put(KEY_UDID, udid);
    //        return udid;
    //    }
    private fun getUdid(prefix: String, id: String): String {
        return if (id == "") {
            prefix + UUID.randomUUID().toString().replace("-", "")
        } else prefix + UUID.nameUUIDFromBytes(id.toByteArray()).toString()
            .replace("-", "")
    }

    fun getDriverDevice(): String {
        try {
            return Build.DEVICE
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDriverProduct(): String {
        try {
            return Build.PRODUCT
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCpuName(): String {
        try {
            val fr = FileReader("/proc/cpuinfo") //__xor__
            val br = BufferedReader(fr)
            val text = br.readLine()
            val array = text.split(":\\s+".toRegex(), limit = 2).toTypedArray()
            for (i in array.indices) {
            }
            return array[1]
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDriverOsVersion(): String {
        try {
            return Build.VERSION.RELEASE
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDriverSDKVersion(): String {
        try {
            return Build.VERSION.SDK
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @SuppressLint("PrivateApi")
    fun getSerialNumber(): String {
        var serial: String? = null
        try {
            val c = Class.forName("android.os.SystemProperties")
            val get: Method = c.getMethod("get", String::class.java)
            serial = get.invoke(c, "ro.serialno")?.toString()
            if (serial!!.isEmpty()) {
                val get2: Method = c.getMethod("get", String::class.java, String::class.java)
                serial = get2.invoke(c, "ril.serialnumber", "unknown")?.toString()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return serial.toString()
    }

    fun getRadioVersion(): String {
        try {
            return Build.getRadioVersion()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getTotalInternalMemorySize(): Long {
        return try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } catch (e: java.lang.Exception) {
            0
        }
    }

    fun getAvailableInternalMemorySize(): Long {
        return try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            availableBlocks * blockSize
        } catch (e: java.lang.Exception) {
            0
        }
    }

    fun getTotalExternalMemorySize(): Long {
        return try {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } catch (e: java.lang.Exception) {
            0
        }
    }

    fun getAvailableExternalMemorySize(): Long {
        return try {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            availableBlocks * blockSize
        } catch (e: java.lang.Exception) {
            0
        }
    }

    fun getSDInfo(): Map<*, *>? {
        return try {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//                val jsonObject: MutableMap<Any, Any> = mapOf<Any,Any>()
                val path = Environment.getExternalStorageDirectory()
                val sf = StatFs(path.path)
                // SMT_RANDOM_SORT_BEGIN
                val blockSize = sf.blockSize.toLong()
                val totalBlock = sf.blockCount.toLong()
                val availableBlock = sf.availableBlocks.toLong()
                val totalLong = totalBlock * blockSize
                val freeLong = availableBlock * blockSize
                val useLong = totalLong - freeLong
                // SMT_RANDOM_SORT_END
                // SMT_RANDOM_SORT_BEGIN
                // SMT_RANDOM_SORT_END
                return mutableMapOf(
                    "totalSize" to totalLong,
                    "freeSize" to freeLong,
                    "useSize" to useLong
                )
            } else if (Environment.getExternalStorageState() == Environment.MEDIA_REMOVED) {
                return null
            }
            null
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun getLocalDNS(): String? {
        return try {
            var cmdProcess: Process? = null
            var reader: BufferedReader? = null
            var dnsIP: String? = ""
            try {
                cmdProcess = Runtime.getRuntime().exec("getprop net.dns1") //__xor__
                reader = BufferedReader(InputStreamReader(cmdProcess.inputStream))
                dnsIP = reader.readLine()
                dnsIP
            } catch (e: IOException) {
                null
            } finally {
                try {
                    reader!!.close()
                } catch (e: IOException) {
                }
                cmdProcess!!.destroy()
            }
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun getSimCountryIso(context: Context): String? {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.simCountryIso
        } catch (e: Exception) {
            null
        }
    }

    fun isSimCardReady(context: Context): Boolean? {
        return try {
            val telMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simState = telMgr.simState
            when (simState) {
                TelephonyManager.SIM_STATE_ABSENT -> {}
                TelephonyManager.SIM_STATE_NETWORK_LOCKED -> {}
                TelephonyManager.SIM_STATE_PIN_REQUIRED -> {}
                TelephonyManager.SIM_STATE_PUK_REQUIRED -> {}
                TelephonyManager.SIM_STATE_READY -> return true
                TelephonyManager.SIM_STATE_UNKNOWN -> {}
            }
            false
        } catch (e: Exception) {
            null
        }
    }

    fun isMobileData(context: Context): Boolean {
        var mobileDataEnabled = false // Assume disabled
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val cmClass = Class.forName(cm.javaClass.name)
            val method = cmClass.getDeclaredMethod("getMobileDataEnabled")
            method.isAccessible = true // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = method.invoke(cm) as Boolean
        } catch (e: Exception) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled
    }

    fun getDataNetworkType(context: Context): String? {
        var ret: String? = null
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
                    return ret
                }
                ret = when (telephonyManager.dataNetworkType) {
                    TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_1xRTT ->                         // SMT_RANDOM_SORT_END
                        "2G"

                    TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_EVDO_B ->                         // SMT_RANDOM_SORT_END
                        "3G"

                    TelephonyManager.NETWORK_TYPE_LTE -> "4G"
                    TelephonyManager.NETWORK_TYPE_NR -> "5G"
                    else -> "Unknown"
                }
            }
        } catch (_: Exception) {
        }
        return ret
    }

    fun getSystemPhotoList(context: Context?): List<String> {
        val result: MutableList<String> = ArrayList()
        try {
            if (!checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val contentResolver = context?.contentResolver
                val cursor = contentResolver?.query(uri, null, null, null, null)
                if (cursor == null || cursor.count <= 0) {
                    return emptyList()
                }
                while (cursor.moveToNext()) {
                    val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    val path = cursor.getString(index)
                    val file = File(path)
                    if (file.exists()) {
                        result.add(path)
                    }
                }
            }
        } catch (_: Exception) {
        }
        return ArrayList()
    }

    fun isEmulator(): Boolean {
        return try {
            (Build.FINGERPRINT.startsWith("generic") //__xor__
                    || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox") //__xor__
                    || Build.FINGERPRINT.lowercase(Locale.getDefault())
                .contains("test-keys") //__xor__
                    //                || Build.FINGERPRINT.startsWith("unknown") // 魅族MX4: unknown
                    || Build.MODEL.contains("google_sdk") //__xor__
                    || Build.MODEL.contains("sdk") //__xor__
                    || Build.MODEL.contains("Emulator") //__xor__
                    //|| Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")) || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
                "generic"
            ) || "google_sdk" == Build.PRODUCT //__xor__
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun isRooted(): Boolean {
        return try {
            // get from build info
            val buildTags = Build.TAGS
            if (buildTags != null && buildTags.contains("test-keys")) { //__xor__
                return true
            }
            val paths = arrayOf(
                "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su",  //__xor__
                "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su",  //__xor__
                "/system/sd/xbin/su",  //__xor__
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"
            ) //__xor__
            for (path in paths) {
                if (File(path).exists()) return true
            }
            var process: Process? = null
            try {
                process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su")) //__xor__
                val `in` = BufferedReader(InputStreamReader(process.inputStream))
                if (`in`.readLine() != null) true else false
            } catch (t: Throwable) {
                false
            } finally {
                process?.destroy()
            }
        } catch (e: java.lang.Exception) {
            false
        }
    }

    fun getLastBootTime(context: Context?): Long {
        return try {
            System.currentTimeMillis() - SystemClock.elapsedRealtime()
        } catch (e: Exception) {
            0
        }
    }

    fun getKeyboard(context: Context): String? {
        return try {
            val cfg = context.resources.configuration
            cfg.keyboard.toString() + ""
        } catch (e: Exception) {
            null
        }
    }

    fun isDevMode(context: Activity): Boolean {
        return try {
            if (Integer.valueOf(Build.VERSION.SDK) == 16) {
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0
                ) != 0
            } else if (Integer.valueOf(Build.VERSION.SDK) >= 17) {
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
                ) != 0
            } else false
        } catch (e: Exception) {
            false
        }
    }

    fun getCellInfo(context: Context): MutableMap<String, Any> {
        val jsonObject: MutableMap<String, Any> = mutableMapOf()
        if (!checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            var dbm = -1
            var cid = -1
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val cellInfoList: List<CellInfo>?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cellInfoList = tm.allCellInfo
                if (null != cellInfoList) {
                    for (cellInfo in cellInfoList) {
                        if (cellInfo is CellInfoGsm) {
                            val cellSignalStrengthGsm = cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthGsm.dbm
                            cid = cellInfo.cellIdentity.cid
                        } else if (cellInfo is CellInfoCdma) {
                            val cellSignalStrengthCdma = cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthCdma.dbm
                            cid = cellInfo.cellIdentity.basestationId
                        } else if (cellInfo is CellInfoWcdma) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                val cellSignalStrengthWcdma = cellInfo.cellSignalStrength
                                dbm = cellSignalStrengthWcdma.dbm
                                cid = cellInfo.cellIdentity.cid
                            }
                        } else if (cellInfo is CellInfoLte) {
                            val cellSignalStrengthLte = cellInfo.cellSignalStrength
                            dbm = cellSignalStrengthLte.dbm
                            cid = cellInfo.cellIdentity.ci
                        }
                    }
                }
            }
            jsonObject["dbm"] = dbm
            jsonObject["cid"] = cid
        }
        return jsonObject
    }

    fun getCpuNumCores(): Int {
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
                return Pattern.matches("cpu[0-9]", pathname.name)
            }
        }
        return try {
            val dir = File("/sys/devices/system/cpu/") //__xor__
            val files = dir.listFiles(CpuFilter())
            files.size
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            1
        }
    }

    fun getMemory(context: Context?): FloatArray {
        return try {
            val appMaxMemory = (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024)).toFloat()
            val appAvailableMemory =
                (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024)).toFloat()
            val appFreeMemory = (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024)).toFloat()
            floatArrayOf(appMaxMemory, appAvailableMemory, appFreeMemory)
        } catch (e: Exception) {
            floatArrayOf(0f, 0f, 0f)
        }
    }

    fun getBattery(context: Context): IntArray {
        return try {
            val batteryInfoIntent = context.applicationContext.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val level = batteryInfoIntent!!.getIntExtra("level", 0)
            val max = batteryInfoIntent.getIntExtra("scale", 100)
            intArrayOf(max, level)
        } catch (e: Exception) {
            intArrayOf(0, 0)
        }
    }

    fun getOsTime(context: Context?): LongArray? {
        return try {
            longArrayOf(SystemClock.elapsedRealtime(), SystemClock.uptimeMillis())
        } catch (e: java.lang.Exception) {
            longArrayOf(0, 0)
        }
    }

    fun getRamTotalSize(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem.toString() + ""
    }

    fun getRamAvailSize(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem.toString() + ""
    }

    fun getRootDirectory(): String? {
        return Environment.getExternalStorageDirectory().absolutePath
    }

    fun getExternalStorageDirectory(): String? {
        return System.getenv("SECONDARY_STORAGE") //__xor__
    }

    fun intToIpAddress(ipInt: Int): String {
        val sb = StringBuffer()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }
}