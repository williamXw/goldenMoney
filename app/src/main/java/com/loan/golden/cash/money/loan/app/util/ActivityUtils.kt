package com.loan.golden.cash.money.loan.app.util

import android.app.Activity
import com.blankj.utilcode.util.LogUtils
import java.util.Collections
import java.util.LinkedList

/**
 * @Author : hxw
 * @Date : 2023/9/15 10:32
 * @Describe :
 */
object ActivityUtils {

    private const val TAG = "ActivityUtils"
    private val mActivitys = Collections.synchronizedList(LinkedList<Activity>())

    /* activity列表 */
    fun getmActivitys(): List<Activity>? {
        return mActivitys
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    fun pushActivity(activity: Activity) {
        mActivitys.add(activity)
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    fun popActivity(activity: Activity) {
        mActivitys.remove(activity)
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (mActivitys == null || mActivitys.isEmpty()) {
            null
        } else mActivitys[mActivitys.size - 1]
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    fun finishCurrentActivity() {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return
        }
        val activity = mActivitys[mActivitys.size - 1]
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (mActivitys == null || mActivitys.isEmpty()) {
            return
        }
        if (activity != null) {
            mActivitys.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return
        }
        for (activity in mActivitys) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    fun findActivity(cls: Class<*>): Activity? {
        var targetActivity: Activity? = null
        if (mActivitys != null) {
            for (activity in mActivitys) {
                if (activity.javaClass == cls) {
                    targetActivity = activity
                    break
                }
            }
        }
        return targetActivity
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    val topActivity: Activity?
        get() {
            var mBaseActivity: Activity? = null
            synchronized(mActivitys) {
                val size = mActivitys.size - 1
                if (size < 0) {
                    return null
                }
                mBaseActivity = mActivitys[size]
            }
            return mBaseActivity
        }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    val topActivityName: String?
        get() {
            var mBaseActivity: Activity? = null
            synchronized(mActivitys) {
                val size = mActivitys.size - 1
                if (size < 0) {
                    return null
                }
                mBaseActivity = mActivitys[size]
            }
            return mBaseActivity!!.javaClass.name
        }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        if (mActivitys == null) {
            return
        }
        for (activity in mActivitys) {
            activity.finish()
        }
        mActivitys.clear()
    }

    /**
     * 退出应用程序
     */
    fun appExit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            LogUtils.i(TAG, "appExit: " + e.message)
        }
    }
}