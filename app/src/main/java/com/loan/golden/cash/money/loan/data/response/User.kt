package com.loan.golden.cash.money.loan.data.response

data class User(
    val auth2Verify: Boolean,
    val createdTime: Long,
    val enabled2Verify: Boolean,
    val phone: String,
    val phoneCode: String,
    val root: Boolean,
    val token: String,
    val userId: String
)