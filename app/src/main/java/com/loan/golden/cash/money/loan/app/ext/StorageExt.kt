package com.loan.golden.cash.money.loan.app.ext

import com.loan.golden.cash.money.loan.data.annotation.ValueKey
import com.tencent.mmkv.MMKV

/**
 * 作者　: hxw
 * 时间　: 2021/7/8
 * 描述　:
 */

/**
 * 获取MMKV
 */
val mmkv: MMKV by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    MMKV.mmkvWithID(ValueKey.MMKV_APP_KEY)
}


