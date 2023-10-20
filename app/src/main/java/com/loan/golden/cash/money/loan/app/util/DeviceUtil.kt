package com.loan.golden.cash.money.loan.app.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.text.TextUtils
import me.hgj.mvvmhelper.base.appContext
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method
import java.util.*

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
}