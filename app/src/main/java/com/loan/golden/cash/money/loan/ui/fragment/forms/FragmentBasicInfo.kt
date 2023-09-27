package com.loan.golden.cash.money.loan.ui.fragment.forms

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.navigateAction
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.response.KaliResponse
import com.loan.golden.cash.money.loan.databinding.FragmentBasicInfoBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.viewmodel.BasicFormsViewModel

/**
 *  author : huxiaowei
 *  date : 2023/9/25 20:56Ø
 *  description : Work information
 */
class FragmentBasicInfo : BaseFragment<BasicFormsViewModel, FragmentBasicInfoBinding>() {

    private lateinit var dialogBottom1: BottomSheetDialog
    private lateinit var dialogBottom2: BottomSheetDialog

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Work information") {
            nav().navigateUp()
        }
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.llWorkInfoOccupation, mBind.tvBasicInfoSubmit) {
            when (it) {
                mBind.tvBasicInfoSubmit -> {
                    nav().navigateAction(R.id.action_to_fragment_personal_information)
                }

                mBind.llWorkInfoOccupation -> {
                    mViewModel.kaliCallBack()
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.kaliResponseResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    showOccupationDialog(it)
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }

    private fun showOccupationDialog(it: KaliResponse) {
        dialogBottom1 = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }!!
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.bottom_occupation, null)

        dialogBottom1.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBottom1.setContentView(dialogView)
        dialogBottom1.show()
    }
}