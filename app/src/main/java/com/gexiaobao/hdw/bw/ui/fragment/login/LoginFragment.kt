package com.gexiaobao.hdw.bw.ui.fragment.login

import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.gexiaobao.hdw.bw.R
import com.gexiaobao.hdw.bw.app.api.NetUrl
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.app.ext.hideSoftKeyboard
import com.gexiaobao.hdw.bw.app.util.*
import com.gexiaobao.hdw.bw.app.util.RxTextTool.getBuilder
import com.gexiaobao.hdw.bw.data.commom.Constant
import com.gexiaobao.hdw.bw.databinding.FragmentLoginBinding
import com.gexiaobao.hdw.bw.ui.activity.MainActivity
import com.gexiaobao.hdw.bw.ui.viewmodel.LoginViewModel
import com.gyf.immersionbar.ImmersionBar
import me.hgj.mvvmhelper.ext.showDialogMessage
import me.hgj.mvvmhelper.net.LoadStatusEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.*

/**
 * created by : huxiaowei
 * @date : 20220913
 * Describe :
 */
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).keyboardEnable(true).init()
        mBind.viewmodel = mViewModel
        mViewModel.isChecked.set(true)
        initSpannableText()
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun initData() {
        super.initData()

        mBind.etLoginMobile.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                mViewModel.mobileNum.set(it.toString())
                changeBtnBg(mBind.etLoginMobile)
            }
        }

        mBind.checkboxDeal.setOnCheckedChangeListener { _, isChecked ->
            mViewModel.isChecked.set(isChecked)
            changeBtnBg(mBind.etLoginMobile)
        }
    }

    private fun changeBtnBg(it: AppCompatEditText) {
        if (it.text?.startsWith("0") == true) {
            if (it.length() == 11 && mViewModel.isChecked.get())
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12_click)
            else
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12)
        } else {
            if (it.length() == 10 && mViewModel.isChecked.get())
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12_click)
            else
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12)
        }
    }

    private fun initSpannableText() {
        /**用户协议点击事件*/
        val clickSpanUser: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                nav().navigateAction(R.id.action_mobile_to_web)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }

        /**隐私政策点击事件*/
        val clickSpanPrivacy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                nav().navigateAction(R.id.action_mobile_to_web)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        mBind.tvDeal.movementMethod = LinkMovementMethod.getInstance()
        getBuilder("").append("Agree")
            .append("《User Service》").setClickSpan(clickSpanUser)
            .append("&")
            .append("《Privacy Policy》").setClickSpan(clickSpanPrivacy)
            .into(mBind.tvDeal)
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.btnContinueLogin) {
            when (it.id) {
                R.id.btn_continue_login -> {
                    val body =
                        EncryptUtil.toRequestBody(
                            "androidId" to context?.let { it1 -> DeviceUtil.getAndroidId(it1) },
                            "appVersion" to activity?.let { it1 -> DeviceUtil.getVersionCode(it1) },
                            "customerAge" to "",
                            "customerId" to 0,
                            "customerMobile" to mViewModel.mobileNum,
                            "customerName" to "",
                            "customerVa1" to "",
                            "gaid" to "",
                            "isVoice" to true,
                            "marketId" to 10033
                        )
                    var parmas = EncryptUtil.encode(body.toString())
                    val paramsBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        EncryptUtil.encryptBody(parmas)
                    )
                    when {
                        mViewModel.mobileNum.get()
                            .startsWith("0") && mViewModel.mobileNum.get().length != 11 -> showDialogMessage(
                            "Enter your phone number"
                        )
                        !mViewModel.mobileNum.get()
                            .startsWith("0") && mViewModel.mobileNum.get().length != 10 -> showDialogMessage(
                            "Enter your phone number"
                        )
                        !mViewModel.isChecked.get() -> showDialogMessage("Please selected...")
                        else -> {
                            mViewModel.customerOtpCallBack(body)?.observe(this) {
                                hideSoftKeyboard(activity)
                                nav().navigateAction(R.id.action_mobile_to_code, Bundle().apply {
                                    putString(Constant.MOBILE_NUMBER, mViewModel.mobileNum.get())
                                    putString(Constant.NOTETOKEN, it.noteToken)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRequestError(loadStatus: LoadStatusEntity) {
        when (loadStatus.requestCode) {
            NetUrl.CUSTOMER_OTP -> {
                showDialogMessage(loadStatus.errorMessage)
            }
        }
    }

}