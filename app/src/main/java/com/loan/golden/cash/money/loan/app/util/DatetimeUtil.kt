package com.loan.golden.cash.money.loan.app.util

import android.annotation.SuppressLint
import android.text.format.DateFormat
import me.hgj.mvvmhelper.util.LogUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 作者　: hegaojian
 * 时间　: 2020/3/10
 * 描述　: 时间工具类
 */

object DatetimeUtil {

    val DATE_PATTERN = "yyyy-MM-dd"
    val DATE_PATTERN_HOUR_MINUTE = "HH:mm"
    var DATE_PATTERN_SS = "yyyy-MM-dd HH:mm:ss"
    var DATE_PATTERN_MM = "yyyy-MM-dd HH:mm"

    /**
     * 获取现在时刻
     */
    val now: Date
        get() = Date(Date().time)

    /**
     * 获取现在时刻
     */
    val nows: Date
        get() = formatDate(DATE_PATTERN, now)

    private fun getTimeLong(): Long {
        return System.currentTimeMillis()
    }


    /**
     * 获取当前时间（年月日时分秒）
     */
    fun getTimeString(): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(Date(getTimeLong()))
    }

    fun getCurrentTimeStamp(timeSec: Int): String {
        var minute = 0
        val days = if (timeSec < 24 * 60) {
            0
        } else {
            timeSec / (60 * 24)
        }
        minute = timeSec % (60 * 24)

        val hours = if (minute < 60) {
            0
        } else {
            minute / 60
        }
        minute %= 60
        val requiredFormat = if (days == 0) {
            hours.toString() + "小时" + minute + "分钟"
        } else if (hours == 0) {
            minute.toString() + "分钟"
        } else {
            days.toString() + "天" + hours + "小时" + minute + "分钟"
        }
        return requiredFormat
    }

    /**
     * Date to Strin
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date?, formatStyle: String): String {
        return if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            sdf.format(date)
        } else {
            ""
        }
    }

    /**
     * Date to Strin
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Long, formatStyle: String): String {
        val sdf = SimpleDateFormat(formatStyle)
        return sdf.format(Date(date))

    }

    fun formatDate(formatStyle: String, formatStr: String): Date {
        val format = SimpleDateFormat(formatStyle, Locale.CHINA)
        return try {
            val date = Date()
            date.time = format.parse(formatStr).time
            date
        } catch (e: Exception) {
            println(e.message)
            nows
        }
    }

    /**
     * Date to Date
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(formatStyle: String, date: Date?): Date {
        if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            val formatDate = sdf.format(date)
            try {
                return sdf.parse(formatDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                return Date()
            }

        } else {
            return Date()
        }
    }

    /**
     * 将时间戳转换为时间
     */
    fun stampToDate(s: String): Date {
        val lt = s.toLong()
        return Date(lt)
    }

    /**
     * 将时间字符串转Date类型
     */
    fun getCustomTime(dateStr: String): Date {
        return formatDate(DATE_PATTERN_SS, dateStr)
    }

    /**
     * @param time
     * @return yy-MM-dd HH:mm:ss格式时间
     */
    fun conversionTime(time: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return DateFormat.format(format, time).toString()
    }


    @SuppressLint("SimpleDateFormat")
    fun stringToDate(time: String): String {
        var format1: SimpleDateFormat? = null
        var date: Date? = null
        if (time.isNotEmpty()) {
            val format = SimpleDateFormat(DATE_PATTERN_SS)
            date = format.parse(time)
            format1 = SimpleDateFormat(DATE_PATTERN_HOUR_MINUTE)
        }
        return format1!!.format(date)
    }

    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getOldDate(distanceDay: Int): String? {
        val dft = SimpleDateFormat(DATE_PATTERN_SS)
        val beginDate = Date()
        val date = Calendar.getInstance()
        date.time = beginDate
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay)
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        LogUtils.debugInfo("前7天==" + endDate?.let { dft.format(it) })
        return endDate?.let { dft.format(it) }
    }

    /**
     * 获取前n分钟日期、后n分钟日期
     *
     * @param distanceDay 前几分钟 如获取前30分钟期则传-30即可；如果后30分钟则传30
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getOldAndNewTime(date: Date, distanceHour: Int): String? {
        val dft = SimpleDateFormat(DATE_PATTERN_SS)
        val beginDate = date
        val date = Calendar.getInstance()
        date.time = beginDate
        date.set(Calendar.MINUTE, date.get(Calendar.MINUTE) + distanceHour)
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return endDate?.let { dft.format(it) }
    }

    fun timeConverter(timeStr: String): String {
        val format = SimpleDateFormat(DATE_PATTERN)
        try {
            val date = format.parse(timeStr)
            return date.time.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}
