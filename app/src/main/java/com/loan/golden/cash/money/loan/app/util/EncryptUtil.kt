package com.loan.golden.cash.money.loan.app.util

import java.util.Locale
import kotlin.experimental.xor


/**
 * Created by Win on 2022/03/29
 */
object EncryptUtil {

    private const val key = ""

    private fun parseByte2HexStr(buf: ByteArray): String {
        val sb = StringBuffer()
        for (i in buf.indices) {
            var hex = Integer.toHexString((buf[i].toInt() and 0xFF))
            if (hex.length == 1) {
                hex = "0$hex"
            }
            sb.append(hex.uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }

//    private fun parseHexStr2Byte(hexStr: String): ByteArray? {
//        if (hexStr.isEmpty()) return null
//        val result = ByteArray(hexStr.length / 2)
//        for (i in 0 until hexStr.length / 2) {
//            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
//            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(16)
//            result[i] = (high * 16 + low).toByte()
//        }
//        return result
//    }


    fun encode(res: String?): String {
        if (res.isNullOrEmpty()) {
            return ""
        }
        try {
            val bs = res.toByteArray()
            for (i in bs.indices) {
                bs[i] = (bs[i] xor key.hashCode().toByte())
            }
            return parseByte2HexStr(bs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }


//    fun decode(res: String?): String {
//        if (res.isNullOrEmpty()) {
//            return ""
//        }
//        try {
//            val bs = parseHexStr2Byte(res)
//            for (i in bs!!.indices) {
//                bs[i] = (bs[i] xor key.hashCode().toByte())
//            }
//            return String(bs)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return ""
//    }

//    /**
//     * 将map数据转换为 普通的 json RequestBody
//     * @param map 以前的请求参数
//     * @return
//     */
//    fun convertMapToBody(map: Map<*, *>?): RequestBody{
//        return map?.let {
//            JSONObject(it).toString()
//                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//        }!!
//    }
//
//    /**
//     * 将map数据转换为图片，文件类型的  RequestBody
//     * @param map 以前的请求参数
//     * @return 待测试
//     */
//    fun convertMapToMediaBody(map: Map<*, *>?): RequestBody? {
//        return map?.let {
//            JSONObject(it).toString()
//                .toRequestBody("multipart/form-data; charset=utf-8".toMediaTypeOrNull())
//        }
//    }
//
//    fun toRequestBody(vararg params: Pair<String, Any?>): RequestBody {
//        return toJSONObject(*params).toString()
//            .toRequestBody("application/json".toMediaTypeOrNull())
//    }
//
//    private fun toJSONObject(vararg params: Pair<String, Any?>): JSONObject {
//        val param = JSONObject()
//        for (i in params) {
//            val value = if (i.second == null) "" else i.second
//            param.put(i.first, value)
//        }
//        return param
//    }
}