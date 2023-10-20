package com.loan.golden.cash.money.loan.app.util;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;
import static android.telephony.TelephonyManager.*;

import com.blankj.utilcode.util.DeviceUtils;

import me.hgj.mvvmhelper.util.LogUtils;


public class DriverInfoUtil {
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

//    private static DeviceUtils.U getHardware(Activity context) {
//        DeviceUtils.U u = DeviceUtils.U.init();
//        u.add("deviceName", DriverInfoUtil.getDriverDevice()) //__xor__
//                // SMT_RANDOM_SORT_BEGIN
//                .add("brand", DriverInfoUtil.getBrand()) //__xor__
//                .add("product", DriverInfoUtil.getDriverProduct()) //__xor__
//                .add("model", DriverInfoUtil.getModel()) //__xor__
//                .add("release", DriverInfoUtil.getDriverOsVersion()) //__xor__
//                .add("cpuType", DriverInfoUtil.getCpuName()) //__xor__
//                .add("sdkVersion", DriverInfoUtil.getDriverSDKVersion()) //__xor__
//                .add("serialNumber", DriverInfoUtil.getSerialNumber()) //__xor__
//                // SMT_RANDOM_SORT_END
//                .add("physicalSize", DriverInfoUtil.getPhysicalSize(context)); //__xor__
//        try {
//            u// SMT_RANDOM_SORT_BEGIN
//                    .add("manufacturer", Build.MANUFACTURER) //__xor__
//                    .add("display", Build.DISPLAY) //__xor__
//                    .add("fingerprint", Build.FINGERPRINT) //__xor__
//                    .add("abis", getAbis(context)) //__xor__
//                    .add("board", Build.BOARD) //__xor__
//                    .add("buildId", Build.ID) //__xor__
//                    .add("host", Build.HOST) //__xor__
//                    .add("type", Build.TYPE) //__xor__
//                    .add("buildUser", Build.USER) //__xor__
//                    .add("cpuAbi", Build.CPU_ABI) //__xor__
//                    .add("cpuAbi2", Build.CPU_ABI2) //__xor__
//                    .add("bootloader", Build.BOOTLOADER) //__xor__
//                    .add("hardware", Build.HARDWARE) //__xor__
//
//                    .add("baseOS", Build.VERSION.BASE_OS)
//            // SMT_RANDOM_SORT_END
//            ; //__xor__
//        } catch (Exception e) {
//        }
//        u.add("radioVersion", DriverInfoUtil.getRadioVersion());  //__xor__
//        try {
//            u.add("sdCardPath", Environment.getExternalStorageDirectory().toString()); //__xor__
//        } catch (Exception e) {
//        }
//        // SMT_RANDOM_SORT_BEGIN
//        u
//                .add("internalTotalSize", DriverInfoUtil.getTotalInternalMemorySize()) //__xor__
//                .add("internalAvailableSize", DriverInfoUtil.getAvailableInternalMemorySize()) //__xor__
//                .add("externalTotalSize", DriverInfoUtil.getTotalExternalMemorySize()) //__xor__
//                .add("externalAvailableSize", DriverInfoUtil.getAvailableExternalMemorySize()) //__xor__
//                .add("sdCardInfo", DriverInfoUtil.getSDInfo())
//        // SMT_RANDOM_SORT_END
//        ; //__xor__
//
//        return u;
//    }

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

    private static List<String> getAbis(Activity context) {
        List<String> ret = new ArrayList<>();
        if (Build.SUPPORTED_ABIS != null) {
            for (String s : Build.SUPPORTED_ABIS) {
                ret.add(s);
            }
        }
        return ret;
    }

//    private static DeviceUtils.U getPublicIp(Activity context) {
//        DeviceUtils.U u = DeviceUtils.U.init();
//        try {
//            // 内网地址
//            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface ni : nilist) {
//                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
//                for (InetAddress address : ialist) {
//                    if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
//                        u.add("intranetIp", isNullText(address.getHostAddress())); //__xor__
//                        break;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//        }
//        return u;
//    }

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

    @SuppressLint("MissingPermission")
//    private static DeviceUtils.U getCurrentWifi(Activity context) {
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

    @android.annotation.TargetApi(17)
    public static boolean isDevMode(Activity context) {
        try {
            if (Integer.valueOf(Build.VERSION.SDK) == 16) {
                return Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
            } else if (Integer.valueOf(Build.VERSION.SDK) >= 17) {
                return Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
            } else return false;
        } catch (Exception e) {
            return false;
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

//    private static DeviceUtils.U getGeneralData(Activity context) {
//        DeviceUtils.U u = DeviceUtils.U.init();
//        u// SMT_RANDOM_SORT_BEGIN
//                .add("andId", DriverInfoUtil.getAndroidID(context)) //__xor__
//                .add("phoneNumber", DriverInfoUtil.getPhone(context)) //__xor__
//                .add("phoneType", DriverInfoUtil.getPhoneType(context)) //__xor__
//                .add("mnc", DriverInfoUtil.getMNC(context)) //__xor__
//                .add("mcc", DriverInfoUtil.getMCC(context)) //__xor__
//                .add("dns", DriverInfoUtil.getLocalDNS()) //__xor__
//                .add("language", DriverInfoUtil.getOsLanguage(context)) //__xor__
//                .add("gaid", GoogleUtils.getGaid(context)) //__xor__
//                .add("imei", DriverInfoUtil.getDriverIMIE(context)) //__xor__
//                .add("networkOperator", DriverInfoUtil.getNetworkOperator(context)) //__xor__
//                .add("networkType", DriverInfoUtil.getNetworkType(context)) //__xor__
//                .add("networkOperatorName", DriverInfoUtil.getNetworkOperatorName(context)) //__xor__
//                .add("timeZoneId", DriverInfoUtil.getTimeZoneId()) //__xor__
//                .add("localeIso3Language", DriverInfoUtil.getISO3Language(context)) //__xor__
//                .add("localeDisplayLanguage", DriverInfoUtil.getLocaleDisplayLanguage()) //__xor__
//                .add("localeIso3Country", DriverInfoUtil.getISO3Country(context)) //__xor__
//                .add("imsi", DriverInfoUtil.getImsi(context))
//        // SMT_RANDOM_SORT_BEGIN
//        ; //__xor__
//        return u;
//    }


    private static String intToIpAddress(long ipInt) {
        StringBuffer sb = new StringBuffer();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    private static List removeDuplicate(List list) {
        List listTemp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (!listTemp.contains(list.get(i))) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

    public static String isNullText(String text) {
        if (null == text) {
            return "";
        }
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        return text;
    }

    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo"); //__xor__
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getCpuNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            File dir = new File("/sys/devices/system/cpu/"); //__xor__
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties"); //__xor__
            Method get = c.getMethod("get", String.class); //__xor__
            serial = (String) get.invoke(c, "ro.serialno"); //__xor__
            if (serial.isEmpty()) {
                Method get2 = c.getMethod("get", String.class, String.class);
                serial = (String) get2.invoke(c, "ril.serialnumber", "unknown"); //__xor__
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }

    @SuppressLint("MissingPermission")
    public static String getMacAddress(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            mac = wifi.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    public static String getDriverBrand() {
        try {
            String brand = Build.BRAND;
            return brand;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverModel() {
        try {
            String model = Build.MODEL;
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverOsVersion() {
        try {
            String device = Build.VERSION.RELEASE;
            return device;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverSDKVersion() {
        try {

            String version_sdk = Build.VERSION.SDK;
            return version_sdk;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverProduct() {
        try {
            String product = Build.PRODUCT;
            return product;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverBoard() {
        try {
            String board = Build.BOARD;
            return board;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverSerial() {
        try {
            String serial = Build.SERIAL;
            return serial;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverDisplay() {
        try {
            String display = Build.DISPLAY;
            return display;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverID() {
        try {
            String display = Build.ID;
            return display;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverBootloader() {
        try {
            String display = Build.BOOTLOADER;
            return display;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverFingerprint() {
        try {
            String fingerprint = Build.FINGERPRINT;
            return fingerprint;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverHost() {
        try {
            String host = Build.HOST;
            return host;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverHardWare() {
        try {
            String hardware = Build.HARDWARE;
            return hardware;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverDevice() {
        try {
            String device = Build.DEVICE;
            return device;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverUser() {
        try {
            String user = Build.USER;
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRadioVersion() {
        try {
            String radioVersion = Build.getRadioVersion();
            return radioVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverTags() {
        try {
            String tags = Build.TAGS;
            return tags;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverTime() {
        try {
            long time = Build.TIME;
            return time + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDriverType() {
        try {
            String type = Build.TYPE;
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getAvailableExternalMemorySize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getTotalExternalMemorySize() {
        try {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getAvailableInternalMemorySize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getTotalInternalMemorySize() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } catch (Exception e) {
            return 0;
        }
    }

    private static final String MEM_INFO_PATH = "/proc/meminfo"; //__xor__
    public static final String MEMTOTAL = "MemTotal"; //__xor__
    public static final String MEMFREE = "MemFree"; //__xor__

    public static String getRamTotalSize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem + "";

    }

    public static float[] getMemory(Context context) {
        try {
            float appMaxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
            float appAvailableMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
            float appFreeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
            float[] memoryList = {appMaxMemory, appAvailableMemory, appFreeMemory};
            return memoryList;
        } catch (Exception e) {
            float[] memoryList = {0, 0, 0};
            return memoryList;
        }
    }

    public static long[] getOsTime(Context context) {
        try {
            long[] times = {SystemClock.elapsedRealtime(), SystemClock.uptimeMillis()};
            return times;
        } catch (Exception e) {
            long[] times = {0, 0};
            return times;
        }
    }

    public static int[] getBattery(Context context) {
        try {
            Intent batteryInfoIntent = context.getApplicationContext().registerReceiver(null,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            int level = batteryInfoIntent.getIntExtra("level", 0); //__xor__
            int max = batteryInfoIntent.getIntExtra("scale", 100); //__xor__
            int[] batterys = {max, level};
            return batterys;
        } catch (Exception e) {
            int[] batterys = {0, 0};
            return batterys;
        }
    }


    public static String getRamAvailSize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem + "";
    }

    public static String getRootDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();

    }

    public static String getExternalStorageDirectory() {
        return System.getenv("SECONDARY_STORAGE"); //__xor__
    }


    public static String getPhysicalSize(Context context) {
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            return Double.toString(Math.sqrt(Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d) + Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d)));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLocalDNS() {
        try {
            Process cmdProcess = null;
            BufferedReader reader = null;
            String dnsIP = "";
            try {
                cmdProcess = Runtime.getRuntime().exec("getprop net.dns1"); //__xor__
                reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
                dnsIP = reader.readLine();
                return dnsIP;
            } catch (IOException e) {
                return null;
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                }
                cmdProcess.destroy();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isEmulator() {
        try {
            return Build.FINGERPRINT.startsWith("generic") //__xor__
                    || Build.FINGERPRINT.toLowerCase().contains("vbox") //__xor__
                    || Build.FINGERPRINT.toLowerCase().contains("test-keys") //__xor__
//                || Build.FINGERPRINT.startsWith("unknown") // 魅族MX4: unknown
                    || Build.MODEL.contains("google_sdk") //__xor__
                    || Build.MODEL.contains("sdk") //__xor__
                    || Build.MODEL.contains("Emulator") //__xor__
                    //|| Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion") //__xor__
                    || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) //__xor__
                    || "google_sdk".equals(Build.PRODUCT); //__xor__
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isRooted() {

        try {
            // get from build info
            String buildTags = Build.TAGS;
            if (buildTags != null && buildTags.contains("test-keys")) { //__xor__
                return true;
            }

            String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", //__xor__
                    "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", //__xor__
                    "/system/sd/xbin/su", //__xor__
                    "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"}; //__xor__
            for (String path : paths) {
                if (new File(path).exists()) return true;
            }

            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"}); //__xor__
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if (in.readLine() != null) return true;
                return false;
            } catch (Throwable t) {
                return false;
            } finally {
                if (process != null) process.destroy();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String getOsLanguage(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            return language;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getISO3Language(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getISO3Language();
            return language;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getISO3Country(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().locale;
            String country = locale.getISO3Country();
            return country;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTimeZoneId() {
        try {
            String timeZoneId = TimeZone.getDefault().getID();
            return timeZoneId;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLocaleDisplayLanguage() {
        try {
            String displayLanguage = Locale.getDefault().getDisplayLanguage();
            return displayLanguage;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    public static String getImsi(Context context) {
        try {
            if (!checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                return tm.getSubscriberId();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Map getCellInfo(Context context) {
        Map jsonObject = new HashMap();
        if (!checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            int dbm = -1;
            int cid = -1;
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            List<CellInfo> cellInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                cellInfoList = tm.getAllCellInfo();
                if (null != cellInfoList) {
                    for (CellInfo cellInfo : cellInfoList) {
                        if (cellInfo instanceof CellInfoGsm) {
                            CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthGsm.getDbm();
                            cid = ((CellInfoGsm) cellInfo).getCellIdentity().getCid();
                        } else if (cellInfo instanceof CellInfoCdma) {
                            CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthCdma.getDbm();
                            cid = ((CellInfoCdma) cellInfo).getCellIdentity().getBasestationId();
                        } else if (cellInfo instanceof CellInfoWcdma) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                                dbm = cellSignalStrengthWcdma.getDbm();
                                cid = ((CellInfoWcdma) cellInfo).getCellIdentity().getCid();
                            }
                        } else if (cellInfo instanceof CellInfoLte) {
                            CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte) cellInfo).getCellSignalStrength();
                            dbm = cellSignalStrengthLte.getDbm();
                            cid = ((CellInfoLte) cellInfo).getCellIdentity().getCi();
                        }
                    }
                }
            }
            jsonObject.put("dbm", dbm); //__xor__
            jsonObject.put("cid", cid); //__xor__
        }
        return jsonObject;
    }

    public static long getLastBootTime(Context context) {
        try {
            long lastBootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            return lastBootTime;
        } catch (Exception e) {
            return 0;
        }
    }

    @SuppressLint("MissingPermission")
    public static String getDriverUUID(Context context) {
        boolean flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (!flag) {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            return deviceUuid.toString(); // uniqueId

        }
        return "";
    }

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.warnInfo("Exception", String.valueOf(e));
        }
        return versionName;
    }

    @SuppressLint("MissingPermission")
    public static String getDriverIMIE(Context context) {
        try {
            boolean flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
            if (!flag) {
                return getTelephonyManager(context).getDeviceId();
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }


    private static TelephonyManager telephonyManager;


    private static TelephonyManager getTelephonyManager(Context context) {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        }
        return telephonyManager;
    }

    public static Map getSDInfo() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Map jsonObject = new HashMap();
                File path = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(path.getPath());
                // SMT_RANDOM_SORT_BEGIN
                long blockSize = sf.getBlockSize();
                long totalBlock = sf.getBlockCount();
                long availableBlock = sf.getAvailableBlocks();
                long totalLong = totalBlock * blockSize;
                long freeLong = availableBlock * blockSize;
                long useLong = totalLong - freeLong;
                // SMT_RANDOM_SORT_END
                // SMT_RANDOM_SORT_BEGIN
                jsonObject.put("totalSize", totalLong); //__xor__
                jsonObject.put("freeSize", freeLong); //__xor__
                jsonObject.put("useSize", useLong); //__xor__
                // SMT_RANDOM_SORT_END
                return jsonObject;
            } else if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
                return null;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> getSystemPhotoList(Context context) {
        List<String> result = new ArrayList<String>();
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
        return result;
    }

    public static Boolean isSimCardReady(Context context) {
        try {
            TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int simState = telMgr.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    // do something
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    // do something
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    return true;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    // do something
                    break;
            }
            return false;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    public static String getDataNetworkType(Context context) {
        String ret = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                switch (telephonyManager.getDataNetworkType()) {
                    // SMT_RANDOM_SORT_BEGIN
                    case NETWORK_TYPE_EDGE:
                    case NETWORK_TYPE_GPRS:
                    case NETWORK_TYPE_CDMA:
                    case NETWORK_TYPE_IDEN:
                    case NETWORK_TYPE_1xRTT:
                        // SMT_RANDOM_SORT_END
                        ret = "2G";
                        break;
                    // SMT_RANDOM_SORT_BEGIN
                    case NETWORK_TYPE_UMTS:
                    case NETWORK_TYPE_HSDPA:
                    case NETWORK_TYPE_HSPA:
                    case NETWORK_TYPE_HSPAP:
                    case NETWORK_TYPE_EVDO_0:
                    case NETWORK_TYPE_EVDO_A:
                    case NETWORK_TYPE_EVDO_B:
                        // SMT_RANDOM_SORT_END
                        ret = "3G";
                        break;
                    case NETWORK_TYPE_LTE:
                        ret = "4G";
                        break;
                    case NETWORK_TYPE_NR:
                        ret = "5G";
                        break;
                    default:
                        ret = "Unknown";
                }
            }
        } catch (Exception e) {
        }
        return ret;
    }

    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getNetworkOperatorName();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getSimCountryIso(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimCountryIso();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isMobileData(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }

    public static String getNetworkOperator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getNetworkOperator();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNetworkType(Context context) {
        try {
            TelephonyManager teleMan = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                int networkType = teleMan.getNetworkType();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return "1xRTT";  //__xor__
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return "CDMA"; //__xor__
                    case NETWORK_TYPE_EDGE:
                        return "EDGE"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        return "EVDO rev. B"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return "GPRS"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return "HSDPA"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return "HSPA"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "HSPA+"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        return "eHRPD"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return "HSUPA"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return "iDen"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "LTE"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return "EVDO rev. 0"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return "EVDO rev. A"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return "UMTS"; //__xor__
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        return "Unknown"; //__xor__
                }
            }
        } catch (Exception e) {
        }

        return "Unknown"; //__xor__
    }

    @SuppressLint("MissingPermission")
    public static String getSimSerialNumber(Context context) {
        boolean flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (!flag) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimSerialNumber();
        }
        return "";
    }

    public static String getBrand() {
        try {
            String brand = Build.BRAND;
            return brand;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getModel() {
        try {
            String model = Build.MODEL;
            return model;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getLine1Number(Context context) {
        boolean flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (!flag) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        }
        return "";
    }

    public static String getMNC(Context context) {
        try {
            Configuration cfg = context.getResources().getConfiguration();
            return cfg.mnc + "";
        } catch (Exception e) {
            return null;
        }
    }

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address"; //__xor__

    @SuppressLint("MissingPermission")
    public static String getAdresseMAC(Context context) {
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        if (wifiInf != null && marshmallowMacAddress.equals(wifiInf.getMacAddress())) {
            String result = null;
            try {
                result = getAdressMacByInterface();
                if (result != null) {
                    return result;
                } else {
                    result = getAddressMacByFile(wifiMan);
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return wifiInf.getMacAddress();
            } else {
                return "";
            }
        }
        return marshmallowMacAddress;
    }

    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents"; //__xor__
        }
    }

    public static String getPhone(Context context) {
        String tel1 = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            String tel = tm.getLine1Number(); //获取本机号码
            tel1 = tel;
        } catch (Exception e) {

        }
        if (TextUtils.isEmpty(tel1)) {
            try {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    SubscriptionManager subscriptionManager = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        subscriptionManager = SubscriptionManager.from(context);
                        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                        if (subscriptionInfoList != null && subscriptionInfoList.size() > 0) {
                            for (SubscriptionInfo info : subscriptionInfoList) {
                                if (!TextUtils.isEmpty(info.getNumber())) {
                                    tel1 = info.getNumber();
                                    break;
                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {

            }
        }

        if (TextUtils.isEmpty(tel1)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                Class telephonyClass = Class.forName(telephonyManager.getClass().getName());
                Method getITelephonyMethod = telephonyClass.getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                Object iTelephony = getITelephonyMethod.invoke(telephonyManager);
                Class<?> iTelephonyClass = Class.forName(iTelephony.getClass().getName());
                Method getSubscriberIdMethod = iTelephonyClass.getDeclaredMethod("getSubscriberId");
                getSubscriberIdMethod.setAccessible(true);
                String subscriberId = (String) getSubscriberIdMethod.invoke(iTelephony);
                if (subscriberId != null && subscriberId.length() > 3) {
                    tel1 = subscriberId.substring(3);
                }
            } catch (Exception e) {

            }
        }

        if (TextUtils.isEmpty(tel1)) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String operatorNumeric = telephonyManager.getSimOperator();
                if (operatorNumeric != null && operatorNumeric.length() > 0) { // 判断是否为中国电信或中国卫通运营商
                    Class<?> c = Class.forName("android.os.SystemProperties");
                    Method get = c.getMethod("get", String.class);
                    String phoneNumber = (String) get.invoke(c, "ro.cdma.icc_line1");
                    if (phoneNumber != null && phoneNumber.length() > 0) {
                        tel1 = phoneNumber;
                    }
                }
            } catch (Exception e) {

            }
        }

        return tel1;
    }


    public static String getAndroidID(Context context) {
        try {
            String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            //LogUtil.e("androidID: " + androidID);
            return androidID;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDeviceId(Context context) {
        boolean flag = checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        if (!flag) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
            return deviceId;
        }
        return "";
    }

    public static String getMCC(Context context) {
        try {
            Configuration cfg = context.getResources().getConfiguration();
            return cfg.mcc + "";
        } catch (Exception e) {
            return null;
        }
    }

    public static String getKeyboard(Context context) {
        try {
            Configuration cfg = context.getResources().getConfiguration();
            return cfg.keyboard + "";
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPhoneType(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String phoneTypeStr = "";
            int phoneType = tm.getPhoneType();
            switch (phoneType) {
                case TelephonyManager.PHONE_TYPE_CDMA:
                    phoneTypeStr = "CDMA"; //__xor__
                    break;
                case TelephonyManager.PHONE_TYPE_GSM:
                    phoneTypeStr = "GSM"; //__xor__
                    break;
                case TelephonyManager.PHONE_TYPE_SIP:
                    phoneTypeStr = "SIP"; //__xor__
                    break;
                case TelephonyManager.PHONE_TYPE_NONE:
                    phoneTypeStr = "None"; //__xor__
                    break;
            }
            return phoneTypeStr;
        } catch (Exception e) {
            return null;
        }
    }


    public static boolean checkSelfPermission(Context context, String permission) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            return false;
        }
    }
}
