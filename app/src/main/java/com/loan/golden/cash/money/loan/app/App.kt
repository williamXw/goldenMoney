package com.loan.golden.cash.money.loan.app

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.Project
import com.loan.golden.cash.money.loan.app.util.ActivityUtils
import com.loan.golden.cash.money.loan.app.util.AdvertisingIdClient
import com.loan.golden.cash.money.loan.app.util.DeviceUtil
import me.hgj.mvvmhelper.base.MvvmHelper
import me.hgj.mvvmhelper.ext.currentProcessName
import java.util.concurrent.Executors


/**
 * 作者　: hxw
 * 时间　: 2021/6/9
 * 描述　:
 */
class App : Application() {

    companion object {
        lateinit var instance: App
        var aDid = ""
        var versionName = ""
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MvvmHelper.init(this, true)
        val processName = currentProcessName
        if (currentProcessName == packageName) {
            // 主进程初始化
            onMainProcessInit()
        } else {
            // 其他进程初始化
            processName?.let { onOtherProcessInit(it) }
        }
        initADid()
        versionName = DeviceUtil.getVerName()
        registerActivityListener()
    }

    private fun registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    ActivityUtils.pushActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {}

                override fun onActivityResumed(activity: Activity) {}

                override fun onActivityPaused(activity: Activity) {}

                override fun onActivityStopped(activity: Activity) {}

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

                override fun onActivityDestroyed(activity: Activity) {
                    val mActivitys = ActivityUtils.getmActivitys()
                    if (mActivitys.isNullOrEmpty()) {
                        return
                    }
                    if (mActivitys.contains(activity)) {
                        ActivityUtils.popActivity(activity)
                    }
                }
            })
        }
    }

    private fun initADid() {
        Executors.newSingleThreadExecutor().execute {
            try {
                aDid = AdvertisingIdClient.getGoogleAdId(applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * @description  代码的初始化请不要放在onCreate直接操作，按照下面新建异步方法
     */
    private fun onMainProcessInit() {
        AnchorsManager.getInstance()
            .debuggable(true)
            //设置锚点
            .addAnchor(
                InitNetWork.TASK_ID, InitUtils.TASK_ID, InitComm.TASK_ID
            ).start(
                Project.Builder("app", AppTaskFactory())
                    .add(InitNetWork.TASK_ID)
                    .add(InitComm.TASK_ID)
                    .add(InitUtils.TASK_ID)
                    .build()
            )
    }

    /**
     * 其他进程初始化，[processName] 进程名
     */
    private fun onOtherProcessInit(processName: String) {}

}