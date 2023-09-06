package com.loan.golden.cash.money.loan.app.ext

import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.widget.CustomToolBar


/**
 * 作者　: hxw
 * 时间　: 2021/6/9
 * 描述　:
 */

/**
 * 初始化有返回键的toolbar
 */
fun CustomToolBar.initBack(
    titleStr: String = "标题",
    backImg: Int = R.mipmap.icon_gray_back,
    onBack: (toolbar: CustomToolBar) -> Unit
): CustomToolBar {
    this.setCenterTitle(titleStr)
    this.getBaseToolBar().setNavigationIcon(backImg)
    this.getBaseToolBar().setNavigationOnClickListener { onBack.invoke(this) }
    return this
}