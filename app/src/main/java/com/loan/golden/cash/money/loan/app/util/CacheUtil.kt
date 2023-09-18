package com.loan.golden.cash.money.loan.app.util

import android.text.TextUtils
import com.google.gson.Gson
import com.loan.golden.cash.money.loan.data.response.LoginResponse
import com.tencent.mmkv.MMKV

object CacheUtil {

    /**
     * 获取保存的账户信息
     */
    fun getUser(): LoginResponse? {
        val kv = MMKV.mmkvWithID("app")
        val userStr = kv.decodeString("user")
        return if (TextUtils.isEmpty(userStr)) {
            null
        } else {
            Gson().fromJson(userStr, LoginResponse::class.java)
        }
    }

    /**
     * 设置账户信息
     */
    fun setUser(userResponse: LoginResponse?) {
        val kv = MMKV.mmkvWithID("app")
        if (userResponse == null) {
            kv.encode("user", "")
            setIsLogin(false)
        } else {
            kv.encode("user", Gson().toJson(userResponse))
            setIsLogin(true)
        }
    }

    /**
     * 是否已经登录
     */
    fun isLogin(): Boolean {
        val kv = MMKV.mmkvWithID("app")
        return kv.decodeBool("login", false)
    }

    /**
     * 设置是否已经登录
     */
    fun setIsLogin(isLogin: Boolean) {
        val kv = MMKV.mmkvWithID("app")
        kv.encode("login", isLogin)
    }
}