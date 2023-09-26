package com.loan.golden.cash.money.loan.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import me.hgj.mvvmhelper.util.LogUtils

/**
 * @Author      : hxw
 * @Date        : 2023/9/26 10:08
 * @Describe    :
 */
class DeviceUpLoadService : Service() {

    private val myBinder = mBinder()

    class mBinder : Binder() {
        fun a() {
            LogUtils.debugInfo("-------------service")
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.debugInfo("-----------onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.debugInfo("-----------onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.debugInfo("-----------onDestroy")
    }
}