package com.loan.golden.cash.money.loan.data.response

/**
 * created by : huxiaowei
 * @date : 20220919
 * Describe :
 */
data class LoginRegisterReq(
    val name: String,
    val password: String,
    val deviceId: String,
    val role: Int = 0
)
