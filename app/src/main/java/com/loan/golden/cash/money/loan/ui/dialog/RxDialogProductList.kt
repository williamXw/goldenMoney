package com.loan.golden.cash.money.loan.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.data.response.NapperResponse

/**
 * @Author      : hxw
 * @Date        : 2023/9/11 11:39
 * @Describe    :
 */
class RxDialogProductList(context: Context?) : RxDialog(context!!) {

    private lateinit var tvDialogLoanProduct: AppCompatTextView
    private lateinit var tvDialogTerm: AppCompatTextView
    private lateinit var tvDialogLoanAmount: AppCompatTextView
    private lateinit var tvDialogLoanDailyInterestRate: AppCompatTextView
    private lateinit var tvDialogGST: AppCompatTextView
    private lateinit var tvDialogTotal: AppCompatTextView
    private lateinit var tvDialogAmountReceived: AppCompatTextView

    init {
        initView()
    }

    @SuppressLint("MissingInflatedId")
    private fun initView() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_product_list, null)
        tvDialogLoanProduct = dialogView.findViewById(R.id.tvDialogLoanProducts)
        tvDialogTerm = dialogView.findViewById(R.id.tvDialogTerm)
        tvDialogLoanAmount = dialogView.findViewById(R.id.tvDialogLoanAmount)
        tvDialogLoanDailyInterestRate = dialogView.findViewById(R.id.tvDialogLoanDailyInterestRate)
        tvDialogGST = dialogView.findViewById(R.id.tvDialogGST)
        tvDialogTotal = dialogView.findViewById(R.id.tvDialogTotal)
        tvDialogAmountReceived = dialogView.findViewById(R.id.tvDialogAmountReceived)
        val tvDialogCancel = dialogView.findViewById<AppCompatTextView>(R.id.tvDialogCancel)
        tvDialogCancel.setOnClickListener {
            dismiss()
        }
        setContentView(dialogView)
    }

    @SuppressLint("SetTextI18n")
    fun setLoanData(contentBean: NapperResponse.PageBean.ContentBean) {
        tvDialogLoanProduct.text = contentBean.name
        tvDialogTerm.text = contentBean.term.toString() + " " + contentBean.termUnit
        tvDialogLoanAmount.text = "₹ " + contentBean.amount.toString()
        tvDialogLoanDailyInterestRate.text = contentBean.dayRate.toString() + "%"
        tvDialogGST.text = "₹ " + contentBean.serviceRate.toString()
        tvDialogTotal.text = "₹ " + contentBean.rateAmount.toString()//利息加服务费
        tvDialogAmountReceived.text = "₹ " + contentBean.contractAmount.toString()
    }
}