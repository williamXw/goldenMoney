package com.loan.golden.cash.money.loan.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import com.loan.golden.cash.money.loan.R

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 11:39
 * @Describe    :
 */
class RxDialogProductList(context: Context?) : RxDialog(context!!) {


    init {
        initView()
    }

    private fun initView() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_product_list, null)

        setContentView(dialogView)
    }
}