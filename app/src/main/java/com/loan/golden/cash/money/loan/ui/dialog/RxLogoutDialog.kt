package com.loan.golden.cash.money.loan.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.loan.golden.cash.money.loan.R

/**
 *  author : huxiaowei
 *  date : 2023/9/18 19:35
 *  description :
 */
class RxLogoutDialog : RxDialog {

    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvCancel: AppCompatTextView
    private lateinit var tvSure: AppCompatTextView

    constructor(context: Context?) : super(context!!) {
        initView()
    }

    private fun initView() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null)
        tvTitle = dialogView.findViewById(R.id.tv_dialog_title)
        tvCancel = dialogView.findViewById(R.id.tv_dialog_cancel)
        tvSure = dialogView.findViewById(R.id.tv_dialog_sure)

        tvCancel.setOnClickListener {
            dismiss()
        }

        setContentView(dialogView)
    }

    fun setTitle(title: String) {
        if (title.isNotEmpty()) {
            tvTitle.text = title
        }
    }

    fun setCancelText(cancelStr: String) {
        if (cancelStr.isNotEmpty()) {
            tvCancel.text = cancelStr
        }
    }

    fun setSureText(sureStr: String) {
        if (sureStr.isNotEmpty()) {
            tvSure.text = sureStr
        }
    }

    fun setSureListener(listener: View.OnClickListener?) {
        tvSure.setOnClickListener(listener)
    }

    fun setCancelListener(listener: View.OnClickListener?) {
        tvCancel.setOnClickListener(listener)
    }

}