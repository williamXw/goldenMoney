package com.loan.golden.cash.money.loan.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemClock
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.loan.golden.cash.money.loan.app.App
import com.loan.golden.cash.money.loan.data.param.DeviceInfoParam
import com.loan.golden.cash.money.loan.data.param.DeviceInfoParam.ModelBean.GeneralDataBean
import com.tencent.bugly.proguard.u
import me.hgj.mvvmhelper.util.LogUtils.warnInfo
import java.io.BufferedReader
import java.io.File
import java.io.FileFilter
import java.io.FileInputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.io.StringWriter
import java.io.Writer
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import java.util.regex.Pattern


object DriverInfoUtil {
    //    public static Map<String, Object> getDeviceInfo(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        try {
    //            u.add("generalData", getGeneralData(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("batteryStatus", getBatteryStatus(context)); //__xor__
    //            // SMT_RANDOM_SORT_END
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("currWifi", getCurrentWifi(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("configWifi", getConfigWifi(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("hardware", getHardware(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("location", getLocation(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("publicIp", getPublicIp(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("simCard", getSimCard(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("storage", getStorage(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("otherData", getOtherData(context)); //__xor__
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        try {
    //            u.add("divFile", getDivFile(context));
    //        } catch (Exception e) {
    //
    //        }
    //        return u.getValue();
    //    }
    //    private static DeviceUtils.U getOtherData(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //
    //        List<String> sysPhotos = DriverInfoUtil.getSystemPhotoList(context);
    //        boolean isRoot = DriverInfoUtil.isRooted();
    //        boolean isEmulator = DriverInfoUtil.isEmulator();
    //
    //        // SMT_RANDOM_SORT_BEGIN
    //        u.add("imageNum", null == sysPhotos ? 0 : sysPhotos.size()); //__xor__
    //        u.add("hasRoot", isRoot); //__xor__
    //        u.add("simulator", isEmulator); //__xor__
    //        u.add("adbEnabled", isDevMode(context)); //__xor__
    //        u.add("keyboard", isNullText(DriverInfoUtil.getKeyboard(context))); //__xor__
    //        // SMT_RANDOM_SORT_END
    //        try {
    //            // SMT_RANDOM_SORT_BEGIN
    //            u.add("cpuNumber", DriverInfoUtil.getCpuNumCores()); //__xor__
    //            u.add("appMaxMemory", DriverInfoUtil.getMemory(context)[0]); //__xor__
    //            u.add("appAvailableMemory", DriverInfoUtil.getMemory(context)[1]); //__xor__
    //            u.add("appFreeMemory", DriverInfoUtil.getMemory(context)[2]); //__xor__
    //            u.add("totalBootTime", DriverInfoUtil.getOsTime(context)[0]); //__xor__
    //            u.add("totalBootTimeWake", DriverInfoUtil.getOsTime(context)[1]); //__xor__
    //            u.add("maxBattery", DriverInfoUtil.getBattery(context)[0]); //__xor__
    //            u.add("levelBattery", DriverInfoUtil.getBattery(context)[1]); //__xor__
    //            // SMT_RANDOM_SORT_END
    //        } catch (Exception e) {
    //
    //        }
    //        try {
    //            Object dbm = DriverInfoUtil.getCellInfo(context).get("dbm"); //__xor__
    //            if (dbm != null) {
    //                u.add("dbm", isNullText(dbm.toString())); //__xor__
    //            }
    //        } catch (Exception e) {
    //        }
    //        u.add("lastBootTime", DriverInfoUtil.getLastBootTime(context)); //__xor__
    //        try {
    //            DisplayMetrics displayMetrics = new DisplayMetrics();
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    //                context.getDisplay().getMetrics(displayMetrics);
    //            }
    ////            FlutterStartActivity.Companion.getInstance().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //            int height = displayMetrics.heightPixels;
    //            int width = displayMetrics.widthPixels;
    //            // SMT_RANDOM_SORT_BEGIN
    //            u.add("screenWidth", width); //__xor__
    //            u.add("screenHeight", height); //__xor__
    //            u.add("screenDensity", displayMetrics.density); //__xor__
    //            u.add("screenDensityDpi", displayMetrics.densityDpi); //__xor__
    //            // SMT_RANDOM_SORT_END
    //        } catch (Exception e) {
    //        }
    //        return u;
    //    }
    //    private static DeviceUtils.U getStorage(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        try {
    //            // SMT_RANDOM_SORT_BEGIN
    //            u.add("ramTotalSize", isNullText(DriverInfoUtil.getRamTotalSize(context))); //__xor__
    //            u.add("ramUsableSize", isNullText(DriverInfoUtil.getRamAvailSize(context))); //__xor__
    //            u.add("mainStorage", isNullText(DriverInfoUtil.getRootDirectory())); //__xor__
    //            u.add("externalStorage", isNullText(DriverInfoUtil.getExternalStorageDirectory())); //__xor__
    //            u.add("memoryCardSize", isNullText(DriverInfoUtil.getSDInfo().get("totalSize").toString())); //__xor__
    //            u.add("memoryCardSizeUse", isNullText(DriverInfoUtil.getSDInfo().get("useSize").toString())); //__xor__
    //            u.add("internalStorageTotal", isNullText(DriverInfoUtil.getTotalInternalMemorySize() + ""));
    //            u.add("internalStorageUsable", isNullText(DriverInfoUtil.getAvailableInternalMemorySize() + ""));
    //            // SMT_RANDOM_SORT_END
    //        } catch (Exception e) {
    //        }
    //        return u;
    //    }
    //    private static DeviceUtils.U getSimCard(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        // SMT_RANDOM_SORT_BEGIN
    //        u.add("countryIso", DriverInfoUtil.getSimCountryIso(context)); //__xor__
    //        u.add("serialNumber", isNullText(DriverInfoUtil.getSerialNumber())); //__xor__
    //        u.add("simCardReady", DriverInfoUtil.isSimCardReady(context)); //__xor__
    //        // SMT_RANDOM_SORT_END
    //
    //        // SMT_RANDOM_SORT_BEGIN
    //        u.add("mobileData", DriverInfoUtil.isMobileData(context)); //__xor__
    //        u.add("dataNetworkType", DriverInfoUtil.getDataNetworkType(context)); //__xor__
    //        // SMT_RANDOM_SORT_END
    //        try {
    //            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    //            u.add("operator", tm.getSimOperator()); //__xor__
    //            u.add("operatorName", tm.getSimOperatorName()); //__xor__
    //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    //                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
    //                    // TODO: Consider calling
    //                    //    ActivityCompat#requestPermissions
    //                    // here to request the missing permissions, and then overriding
    //                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                    //                                          int[] grantResults)
    //                    // to handle the case where the user grants the permission. See the documentation
    //                    // for ActivityCompat#requestPermissions for more details.
    //                    return u;
    //                }
    //                u.add("mobileDataEnabled", tm.isDataEnabled()); //__xor__
    //            }
    //        } catch (Exception e) {
    //            return u;
    //        }
    //        return u;
    //    }

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

    //    private static DeviceUtils.U getDivFile(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        try {
    //            File audioInternalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    //            int audioInternalFileCount = (audioInternalDir.listFiles() == null) ? 0 : audioInternalDir.listFiles().length;
    //            u.add("audioExternal", audioInternalFileCount);
    //
    //            File audioExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
    //            int audioExternalFileCount = (audioExternalDir.listFiles() == null) ? 0 : audioExternalDir.listFiles().length;
    //            u.add("audioExternal", audioExternalFileCount);
    //            // 获取图片内部存储文件个数
    //            File imagesInternalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    //            int imagesInternalFileCount = (imagesInternalDir.listFiles() == null) ? 0 : imagesInternalDir.listFiles().length;
    //            u.add("imagesInternal", imagesInternalFileCount);
    //            // 获取图片外部存储文件个数
    //            File imagesExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    //            int imagesExternalFileCount = (imagesExternalDir.listFiles() == null) ? 0 : imagesExternalDir.listFiles().length;
    //            u.add("imagesExternal", imagesExternalFileCount);
    //            // 获取视频内部存储文件个数
    //            File videoInternalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    //            int videoInternalFileCount = (videoInternalDir.listFiles() == null) ? 0 : videoInternalDir.listFiles().length;
    //            u.add("videoInternal", videoInternalFileCount);
    //            // 获取视频外部存储文件个数
    //            File videoExternalDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    //            int videoExternalFileCount = (videoExternalDir.listFiles() == null) ? 0 : videoExternalDir.listFiles().length;
    //            u.add("videoExternal", videoExternalFileCount);
    //            // 获取下载文件个数
    //            String downloadDirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    //            File downloadDir = new File(downloadDirPath);
    //            File[] downloadFiles = downloadDir.listFiles();
    //            int downloadFileCount = (downloadFiles == null) ? 0 : downloadFiles.length;
    //            u.add("downloadFiles", downloadFileCount);
    //        } catch (Exception e) {
    //
    //        }
    //        return u;
    //    }

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

    //    @SuppressLint("MissingPermission")
    //    public static DeviceUtils.U getLocation(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        DeviceUtils.U gps = DeviceUtils.U.init();
    //        try {
    //            if (PermissionUtils.isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
    //                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    //                List<String> providers = locationManager.getProviders(true);
    //                if (providers.contains(LocationManager.GPS_PROVIDER)) {
    //                    Criteria criteria = new Criteria();
    //                    // SMT_RANDOM_SORT_BEGIN
    //                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    //                    criteria.setAltitudeRequired(false);
    //                    criteria.setBearingRequired(false);
    //                    criteria.setCostAllowed(true);
    //                    criteria.setPowerRequirement(Criteria.POWER_LOW);
    //                    // SMT_RANDOM_SORT_END
    //
    //                    String provider = locationManager.getBestProvider(criteria, true);
    //                    @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
    //                    double latitude = -1;
    //                    double longitude = -1;
    //                    if (null != location) {
    //                        latitude = location.getLatitude();
    //                        longitude = location.getLongitude();
    //                        gps.add("latitude", latitude); //__xor__
    //                        gps.add("longitude", longitude); //__xor__
    //                    } else {
    //                        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
    //                        if (null != location) {
    //                            latitude = location.getLatitude();
    //                            longitude = location.getLongitude();
    //                            gps.add("latitude", latitude); //__xor__
    //                            gps.add("longitude", longitude); //__xor__
    //                        }
    //                    }
    //                    try {
    //                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    //
    //
    //                        DecimalFormat df = new DecimalFormat();
    //
    //                        df.setMaximumFractionDigits(3);
    //                        double lat = Double.parseDouble(df.format(latitude));
    //                        double lon = Double.parseDouble(df.format(longitude));
    //
    //                        List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
    //                        fillAddress(u, addresses);
    //                    } catch (Exception e) {
    //                        e.printStackTrace();
    //                    }
    //                } else {
    //                    Criteria criteria = new Criteria();
    //                    // SMT_RANDOM_SORT_BEGIN
    //                    criteria.setCostAllowed(false);
    //                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    //                    criteria.setAltitudeRequired(false);
    //                    criteria.setBearingRequired(false);
    //                    criteria.setCostAllowed(false);
    //                    criteria.setPowerRequirement(Criteria.POWER_LOW);
    //                    // SMT_RANDOM_SORT_END
    //                    String providerName = locationManager.getBestProvider(criteria, true);
    //                    if (providerName != null) {
    //                        Location location = locationManager.getLastKnownLocation(providerName);
    //                        if (location != null) {
    //                            try {
    //                                double latitude = location.getLatitude();
    //                                double longitude = location.getLongitude();
    //                                gps.add("latitude", latitude); //__xor__
    //                                gps.add("longitude", longitude); //__xor__
    //                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
    //                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
    //                                fillAddress(u, addresses);
    //                            } catch (Exception e) {
    //                                e.printStackTrace();
    //                            }
    //                        }
    //                    } else {
    //                        return null;
    //                    }
    //                }
    //            }
    //        } catch (Exception e) {
    //
    //        }
    //        u.add("gps", gps); //__xor__
    //        return u;
    //    }
    //    private static void fillAddress(DeviceUtils.U u, List<Address> addresses) {
    //        if (addresses.size() > 0) {
    //            Address address = addresses.get(0);
    //            // SMT_RANDOM_SORT_BEGIN
    //            String country = address.getCountryName();
    //            String province = address.getAdminArea();
    //            String city = address.getSubAdminArea();
    //            String bigDirect = address.getLocality();
    //            String smallDirect = address.getThoroughfare();
    //            String detailed = address.getAddressLine(0);
    //            // SMT_RANDOM_SORT_END
    //            // SMT_RANDOM_SORT_BEGIN
    //            u.add("country", isNullText(country)); //__xor__
    //            u.add("province", isNullText(province)); //__xor__
    //            u.add("city", isNullText(city)); //__xor__
    //            u.add("largeDistrict", isNullText(bigDirect)); //__xor__
    //            u.add("smallDistrict", isNullText(smallDirect)); //__xor__
    //            u.add("address", isNullText(detailed)); //__xor__
    //            // SMT_RANDOM_SORT_END
    //        }
    //    }
    @SuppressLint("MissingPermission") //    private static DeviceUtils.U getCurrentWifi(Activity context) {
    //        DeviceUtils.U u = DeviceUtils.U.init();
    //        try {
    //            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    //            @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    //            if (null != wifiInfo) {
    //                // SMT_RANDOM_SORT_BEGIN
    //                u.add("wifiConnected", true); //__xor__
    //                u.add("bssid", wifiInfo.getBSSID()); //__xor__
    //                u.add("ssid", wifiInfo.getSSID()); //__xor__
    //                u.add("mac", wifiInfo.getMacAddress()); //__xor__
    //                u.add("ip", intToIpAddress(wifiInfo.getIpAddress())); //__xor__
    //                // SMT_RANDOM_SORT_END
    //            }
    //        } catch (Exception e) {
    //        }
    //        return u;
    //    }
    //    private static List<Map> getConfigWifi(Activity context) {
    //        List<Map> ret = new ArrayList<>();
    //        try {
    //            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    //            @SuppressLint("MissingPermission") List<ScanResult> scanResults = wifiManager.getScanResults();
    //            if (scanResults != null) {
    //                for (int i = 0; i < scanResults.size(); i++) {
    //                    ScanResult scanResult = scanResults.get(i);
    //                    DeviceUtils.U u = DeviceUtils.U.init();
    //                    // SMT_RANDOM_SORT_BEGIN
    //                    u.add("ssid", isNullText(scanResult.SSID)); //__xor__
    //                    u.add("bssid", isNullText(scanResult.BSSID)); //__xor__
    //                    u.add("name", isNullText(scanResult.SSID)); //__xor__
    //                    u.add("mac", isNullText(scanResult.BSSID)); //__xor__
    //                    // SMT_RANDOM_SORT_END
    //                    ret.add(u.getValue());
    //                }
    //            }
    //        } catch (Exception e) {
    //        }
    //        return ret;
    //    }
    @TargetApi(17)
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

    //    private static DeviceUtils.U getBatteryStatus(Activity context) {
    //        try {
    //            Intent batteryInfoIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    //            // SMT_RANDOM_SORT_BEGIN
    //            int level = batteryInfoIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);//电量（0-100）
    //            int scale = batteryInfoIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    //            int plugged = batteryInfoIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);//
    //            // SMT_RANDOM_SORT_END
    //            float batteryPct = level / (float) scale;
    //            boolean isUsbCharge = false;
    //            boolean isCharging = false;
    //            boolean isAcCharge = false;
    //            switch (plugged) {
    //                case BatteryManager.BATTERY_PLUGGED_AC: {
    //                    isAcCharge = true;
    //                    isCharging = true;
    //                }
    //                break;
    //                case BatteryManager.BATTERY_PLUGGED_USB: {
    //                    isUsbCharge = true;
    //                    isCharging = true;
    //                }
    //                break;
    //            }
    //            DeviceUtils.U u = DeviceUtils.U.init();
    //            // SMT_RANDOM_SORT_BEGIN
    //            u.add("batteryPct", batteryPct); //__xor__
    //            u.add("isUsbCharge", isUsbCharge); //__xor__
    //            u.add("isAcCharge", isAcCharge); //__xor__
    //            u.add("isCharging", isCharging); //__xor__
    //            // SMT_RANDOM_SORT_END
    //            return u;
    //        } catch (Exception e) {
    //            return DeviceUtils.U.init();
    //        }
    //    }
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

    private fun intToIpAddress(ipInt: Long): String {
        val sb = StringBuffer()
        sb.append(ipInt and 0xFFL).append(".")
        sb.append(ipInt shr 8 and 0xFFL).append(".")
        sb.append(ipInt shr 16 and 0xFFL).append(".")
        sb.append(ipInt shr 24 and 0xFFL)
        return sb.toString()
    }

    private fun removeDuplicate(list: List<*>): List<*> {
        val listTemp: MutableList<*> = ArrayList<Any?>()
        for (i in list.indices) {
            if (!listTemp.contains(list[i])) {
                listTemp.add(list[i] as Nothing)
            }
        }
        return listTemp
    }

    fun isNullText(text: String?): String {
        if (null == text) {
            return ""
        }
        return if (TextUtils.isEmpty(text)) {
            ""
        } else text
    }

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

    //__xor__
    val cpuNumCores: Int
        get() {
            class CpuFilter : FileFilter {
                override fun accept(pathname: File): Boolean {
                    return Pattern.matches("cpu[0-9]", pathname.name)
                }
            }
            return try {
                val dir = File("/sys/devices/system/cpu/") //__xor__
                val files = dir.listFiles(CpuFilter())
                files.size
            } catch (e: Exception) {
                e.printStackTrace()
                1
            }
        }//__xor__//__xor__

    //__xor__
    //__xor__
    val serialNumber: String?
        get() {
            var serial: String? = null
            try {
                val c = Class.forName("android.os.SystemProperties") //__xor__
                val get = c.getMethod("get", String::class.java) //__xor__
                serial = get.invoke(c, "ro.serialno") as String //__xor__
                if (serial!!.isEmpty()) {
                    val get2 = c.getMethod("get", String::class.java, String::class.java)
                    serial = get2.invoke(c, "ril.serialnumber", "unknown") as String //__xor__
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return serial
        }

    @SuppressLint("MissingPermission")
    fun getMacAddress(context: Context): String {
        var mac = ""
        try {
            val wifi = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            mac = wifi.connectionInfo.macAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mac
    }

    val driverBrand: String
        get() {
            try {
                return Build.BRAND
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverModel: String
        get() {
            try {
                return Build.MODEL
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverOsVersion: String
        get() {
            try {
                return Build.VERSION.RELEASE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverSDKVersion: String
        get() {
            try {
                return Build.VERSION.SDK
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverProduct: String
        get() {
            try {
                return Build.PRODUCT
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverBoard: String
        get() {
            try {
                return Build.BOARD
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverSerial: String
        get() {
            try {
                return Build.SERIAL
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverDisplay: String
        get() {
            try {
                return Build.DISPLAY
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverID: String
        get() {
            try {
                return Build.ID
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverBootloader: String
        get() {
            try {
                return Build.BOOTLOADER
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverFingerprint: String
        get() {
            try {
                return Build.FINGERPRINT
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverHost: String
        get() {
            try {
                return Build.HOST
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverHardWare: String
        get() {
            try {
                return Build.HARDWARE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverDevice: String
        get() {
            try {
                return Build.DEVICE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverUser: String
        get() {
            try {
                return Build.USER
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val radioVersion: String
        get() {
            try {
                return Build.getRadioVersion()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverTags: String
        get() {
            try {
                return Build.TAGS
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverTime: String
        get() {
            try {
                val time = Build.TIME
                return time.toString() + ""
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val driverType: String
        get() {
            try {
                return Build.TYPE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    val availableExternalMemorySize: Long
        get() = try {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            availableBlocks * blockSize
        } catch (e: Exception) {
            0
        }
    val totalExternalMemorySize: Long
        get() = try {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } catch (e: Exception) {
            0
        }
    val availableInternalMemorySize: Long
        get() = try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            availableBlocks * blockSize
        } catch (e: Exception) {
            0
        }
    val totalInternalMemorySize: Long
        get() = try {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            totalBlocks * blockSize
        } catch (e: Exception) {
            0
        }
    private const val MEM_INFO_PATH = "/proc/meminfo" //__xor__
    const val MEMTOTAL = "MemTotal" //__xor__
    const val MEMFREE = "MemFree" //__xor__
    fun getRamTotalSize(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem.toString() + ""
    }

    fun getMemory(context: Context?): FloatArray {
        return try {
            val appMaxMemory = (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024)).toFloat()
            val appAvailableMemory = (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024)).toFloat()
            val appFreeMemory = (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024)).toFloat()
            floatArrayOf(appMaxMemory, appAvailableMemory, appFreeMemory)
        } catch (e: Exception) {
            floatArrayOf(0f, 0f, 0f)
        }
    }

    fun getOsTime(context: Context?): LongArray {
        return try {
            longArrayOf(SystemClock.elapsedRealtime(), SystemClock.uptimeMillis())
        } catch (e: Exception) {
            longArrayOf(0, 0)
        }
    }

    fun getBattery(context: Context): IntArray {
        return try {
            val batteryInfoIntent = context.applicationContext.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            val level = batteryInfoIntent!!.getIntExtra("level", 0) //__xor__
            val max = batteryInfoIntent.getIntExtra("scale", 100) //__xor__
            intArrayOf(max, level)
        } catch (e: Exception) {
            intArrayOf(0, 0)
        }
    }

    fun getRamAvailSize(context: Context): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem.toString() + ""
    }

    val rootDirectory: String
        get() = Environment.getExternalStorageDirectory().absolutePath

    //__xor__
    val externalStorageDirectory: String
        get() = System.getenv("SECONDARY_STORAGE") //__xor__

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
                    ) + Math.pow((displayMetrics.widthPixels.toFloat() / displayMetrics.xdpi).toDouble(), 2.0)
                )
            )
        } catch (e: Exception) {
            null
        }
    }

    //__xor__
    val localDNS: String?
        get() = try {
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
        } catch (e: Exception) {
            null
        }

    //__xor__
    //__xor__
    //__xor__
    //                || Build.FINGERPRINT.startsWith("unknown") // 魅族MX4: unknown
    //__xor__
    //__xor__
    //__xor__
    //|| Build.MODEL.contains("Android SDK built for x86")
    //__xor__
    //__xor__
    //__xor__
    val isEmulator: Boolean
        get() = try {
            (Build.FINGERPRINT.startsWith("generic") //__xor__
                    || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox") //__xor__
                    || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("test-keys") //__xor__
                    //                || Build.FINGERPRINT.startsWith("unknown") // 魅族MX4: unknown
                    || Build.MODEL.contains("google_sdk") //__xor__
                    || Build.MODEL.contains("sdk") //__xor__
                    || Build.MODEL.contains("Emulator") //__xor__
                    //|| Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")) || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") || "google_sdk" == Build.PRODUCT //__xor__
        } catch (e: Exception) {
            false
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
        } catch (e: Exception) {
            false
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
    fun getImsi(context: Context): String {
        return try {
            if (!checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                return tm.subscriberId
            }
            ""
        } catch (e: Exception) {
            ""
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
//    fun getCellInfo(context: Context): Map<*, *> {
//        val jsonObject: MutableMap<*, *> = HashMap<Any?, Any?>()
//        if (!checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//            var dbm = -1
//            var cid = -1
//            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            val cellInfoList: List<CellInfo>?
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                cellInfoList = tm.allCellInfo
//                if (null != cellInfoList) {
//                    for (cellInfo in cellInfoList) {
//                        if (cellInfo is CellInfoGsm) {
//                            val cellSignalStrengthGsm = cellInfo.cellSignalStrength
//                            dbm = cellSignalStrengthGsm.dbm
//                            cid = cellInfo.cellIdentity.cid
//                        } else if (cellInfo is CellInfoCdma) {
//                            val cellSignalStrengthCdma = cellInfo.cellSignalStrength
//                            dbm = cellSignalStrengthCdma.dbm
//                            cid = cellInfo.cellIdentity.basestationId
//                        } else if (cellInfo is CellInfoWcdma) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                                val cellSignalStrengthWcdma = cellInfo.cellSignalStrength
//                                dbm = cellSignalStrengthWcdma.dbm
//                                cid = cellInfo.cellIdentity.cid
//                            }
//                        } else if (cellInfo is CellInfoLte) {
//                            val cellSignalStrengthLte = cellInfo.cellSignalStrength
//                            dbm = cellSignalStrengthLte.dbm
//                            cid = cellInfo.cellIdentity.ci
//                        }
//                    }
//                }
//            }
//            jsonObject["dbm"] = dbm //__xor__
//            jsonObject["cid"] = cid //__xor__
//        }
//        return jsonObject
//    }

    fun getLastBootTime(context: Context?): Long {
        return try {
            System.currentTimeMillis() - SystemClock.elapsedRealtime()
        } catch (e: Exception) {
            0
        }
    }

    @SuppressLint("MissingPermission")
    fun getDriverUUID(context: Context): String {
        val flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        if (!flag) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val tmDevice: String
            val tmSerial: String
            val tmPhone: String
            val androidId: String
            tmDevice = "" + tm.deviceId
            tmSerial = "" + tm.simSerialNumber
            androidId = "" + Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())
            return deviceUuid.toString() // uniqueId
        }
        return ""
    }

    fun getAppVersionName(context: Context): String? {
        var versionName = ""
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            if (versionName == null || versionName.length <= 0) {
                return ""
            }
        } catch (e: Exception) {
            warnInfo("Exception", e.toString())
        }
        return versionName
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
            telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        }
        return telephonyManager
    }

//    fun getSDInfo(): Map<*, *>? {
//        return try {
//            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
//                val jsonObject: MutableMap<*, *> = HashMap<Any?, Any?>()
//                val path = Environment.getExternalStorageDirectory()
//                val sf = StatFs(path.path)
//                // SMT_RANDOM_SORT_BEGIN
//                val blockSize = sf.blockSize.toLong()
//                val totalBlock = sf.blockCount.toLong()
//                val availableBlock = sf.availableBlocks.toLong()
//                val totalLong = totalBlock * blockSize
//                val freeLong = availableBlock * blockSize
//                val useLong = totalLong - freeLong
//                // SMT_RANDOM_SORT_END
//                // SMT_RANDOM_SORT_BEGIN
//                jsonObject["totalSize"] = totalLong //__xor__
//                jsonObject["freeSize"] = freeLong //__xor__
//                jsonObject["useSize"] = useLong //__xor__
//                // SMT_RANDOM_SORT_END
//                return jsonObject
//            } else if (Environment.getExternalStorageState() == Environment.MEDIA_REMOVED) {
//                return null
//            }
//            null
//        } catch (e: Exception) {
//            null
//        }
//    }

    fun getSystemPhotoList(context: Context?): List<String> {
        //        try {
//            if (!checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//                ContentResolver contentResolver = context.getContentResolver();
//                Cursor cursor = contentResolver.query(uri, null, null, null, null);
//                if (cursor == null || cursor.getCount() <= 0) {
//                    return null;
//                }
//                while (cursor.moveToNext()) {
//                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    String path = cursor.getString(index);
//                    File file = new File(path);
//                    if (file.exists()) {
//                        result.add(path);
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
        return ArrayList()
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

    @SuppressLint("MissingPermission")
    fun getDataNetworkType(context: Context): String? {
        var ret: String? = null
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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
        } catch (e: Exception) {
        }
        return ret
    }

    fun getNetworkOperatorName(context: Context): String? {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.networkOperatorName
        } catch (e: Exception) {
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
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

    @SuppressLint("MissingPermission")
    fun getSimSerialNumber(context: Context): String {
        val flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        if (!flag) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.simSerialNumber
        }
        return ""
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

    fun getLine1Number(context: Context): String {
        val flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        if (!flag) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.line1Number
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

    @SuppressLint("MissingPermission")
    fun getAdresseMAC(context: Context): String? {
        val wifiMan = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo
        if (wifiInf != null && marshmallowMacAddress == wifiInf.macAddress) {
            var result: String? = null
            try {
                result = getAdressMacByInterface()
                return if (result != null) {
                    result
                } else {
                    result = getAddressMacByFile(wifiMan)
                    result
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            return if (wifiInf != null && wifiInf.macAddress != null) {
                wifiInf.macAddress
            } else {
                ""
            }
        }
        return marshmallowMacAddress
    }

    private fun getAdressMacByInterface(): String? {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
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
                val crunchifyReader: Reader = BufferedReader(InputStreamReader(crunchifyStream, "UTF-8"))
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
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
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
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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

//    fun getDeviceId(context: Context): String {
//        val flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
//        if (!flag) {
//            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            return tm.deviceId
//        }
//        return ""
//    }

    fun getMCC(context: Context): String? {
        return try {
            val cfg = context.resources.configuration
            cfg.mcc.toString() + ""
        } catch (e: Exception) {
            null
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
        return if (ActivityCompat.checkSelfPermission(context!!, permission!!) != PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            false
        }
    }
}