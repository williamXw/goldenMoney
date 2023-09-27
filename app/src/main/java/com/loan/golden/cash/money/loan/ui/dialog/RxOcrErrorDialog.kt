package com.loan.golden.cash.money.loan.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.loan.golden.cash.money.loan.R

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 11:39
 * @Describe    :
 */
class RxOcrErrorDialog(context: Context?) : RxDialog(context!!) {


    init {
        initView()
    }

    @SuppressLint("MissingInflatedId")
    private fun initView() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_ocr_error, null)
        val tvContinue = dialogView.findViewById<AppCompatTextView>(R.id.tvOcrErrorDialogContinue)
        tvContinue.setOnClickListener {
            dismiss()
        }
        setContentView(dialogView)
    }
}