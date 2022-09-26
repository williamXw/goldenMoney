package com.gexiaobao.hdw.bw.ui.fragment.login

import android.os.Bundle
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.gexiaobao.hdw.bw.R
import com.gexiaobao.hdw.bw.app.api.NetUrl
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.app.dialog.RxDialogSure
import com.gexiaobao.hdw.bw.app.ext.LiveDataEvent
import com.gexiaobao.hdw.bw.app.ext.hideSoftKeyboard
import com.gexiaobao.hdw.bw.app.util.*
import com.gexiaobao.hdw.bw.app.util.RxTextTool.getBuilder
import com.gexiaobao.hdw.bw.data.commom.Constant
import com.gexiaobao.hdw.bw.data.response.LoginRegisterReq
import com.gexiaobao.hdw.bw.databinding.FragmentVerCodeBinding
import com.gexiaobao.hdw.bw.ui.activity.MainActivity
import com.gexiaobao.hdw.bw.ui.viewmodel.LoginViewModel
import com.gyf.immersionbar.ImmersionBar
import me.hgj.mvvmhelper.ext.showDialogMessage
import me.hgj.mvvmhelper.net.LoadStatusEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * created by : huxiaowei
 * @date :20220913
 * Describe : 填写验证码
 */
class VeriCodeFragment : BaseFragment<LoginViewModel, FragmentVerCodeBinding>() {

    private var mobileNum = ""
    private var noteToken = ""
    private var mAndroidID = ""
    private var mAppVersion = ""

    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this)
            .keyboardEnable(true).init()
        mBind.viewmodel = mViewModel
        mViewModel.isChecked.set(true)

        arguments?.let {
            mobileNum = it.getString(Constant.MOBILE_NUMBER)!!
            noteToken = it.getString(Constant.NOTETOKEN)!!
        }
        mAndroidID = context?.let { DeviceUtil.getAndroidId(it) }.toString()
        mAppVersion = activity?.let { it1 -> DeviceUtil.getVersionCode(it1) }.toString()
    }

    override fun initData() {
        super.initData()
        initSpannableText()
        mBind.etVerCode.addTextChangedListener {
            if (it != null) {
                mViewModel.deal.set(it.toString())
                changeBtnBg(mBind.etVerCode)
            }
        }
        mBind.checkboxDeal.setOnCheckedChangeListener { _, isChecked ->
            mViewModel.isChecked.set(isChecked)
            changeBtnBg(mBind.etVerCode)
        }
    }

    private fun changeBtnBg(etVerCode: AppCompatEditText) {
        if (mViewModel.isChecked.get()) {
            if (etVerCode.length() == 4) {
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12_click)
            } else {
                mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12)
            }
        } else {
            mBind.btnContinueLogin.setBackgroundResource(R.drawable.round_btn_12)
        }
    }

    private fun initSpannableText() {
        /**语音验证码点击事件*/
        val clickSpanOTP: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val rxDialogSure = RxDialogSure(context)
                rxDialogSure.setLogo(R.mipmap.icon_dialog_logo)
                rxDialogSure.setContent(
                    "Your number is being dialed, \n" +
                            "please pay attention to answer the call, and input the 4-digital OTP which you hear."
                )
                rxDialogSure.btnSure.setOnClickListener {
                    RxToast.showToast("消失了")
                    rxDialogSure.cancel()
                }
                rxDialogSure.setFullScreenWidth()
                rxDialogSure.show()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }
        mBind.tvVoice.movementMethod = LinkMovementMethod.getInstance()
        getBuilder("").append("Can't receive OTP ?")
            .append("Try Voice OTP")
            .setClickSpan(clickSpanOTP).into(mBind.tvVoice)


        /**服务协议点击事件*/
        val clickSpanUser: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                nav().navigateAction(R.id.action_code_to_web)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
            }
        }

        /**隐私政策点击事件*/
        val clickSpanPrivacy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                nav().navigateAction(R.id.action_code_to_web)
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
        setOnclickNoRepeat(mBind.btnResend, mBind.btnContinueLogin) {
            when (it) {
                mBind.btnResend -> {
                    reSendVerCode()
                }
                mBind.btnContinueLogin -> {
                    loginToMain()
                }
            }
        }
    }

    private fun loginToMain() {
        startActivity<MainActivity>()
        activity?.finish()
        val deviceType = DeviceUtil.model
        var loginParams = LoginRegisterReq(
            mAndroidID,
            "",
            mAppVersion,
            0,
            mobileNum,
            "",
            deviceType,
            "",
            "",
            "",
            "",
            10033,
            noteToken,
            mViewModel.deal.get(),
            ""
        )

        val body =
            EncryptUtil.toRequestBody(
                "androidid" to mAndroidID,
                "appInstanceId" to "",
                "appVersion" to mAppVersion,
                "customerId" to 0,
                "customerMobile" to mobileNum,
                "deviceId" to "",
                "deviceType" to deviceType,
                "fcmToken" to "",
                "ip" to "",
                "latitude" to "",
                "longitude" to "",
                "marketId" to 10033,
                "noteToken" to noteToken,
                "otpCode" to mViewModel.deal.get(),
                "regGaid" to "",
            )
        when {
            mViewModel.deal.get().length != 4 -> showDialogMessage("Enter your phone OTP")
            !mViewModel.isChecked.get() -> showDialogMessage("Please selected...")
            else -> {
                mViewModel.loginCallBack(body)?.observe(this) {
                    startActivity<MainActivity>()
                    activity?.finish()
                }
            }
        }
    }

    private fun reSendVerCode() {
        val body =
            EncryptUtil.toRequestBody(
                "androidId" to mAndroidID,
                "appVersion" to mAppVersion,
                "customerAge" to "",
                "customerId" to 10033,
                "customerMobile" to mViewModel.mobileNum,
                "customerName" to "",
                "customerVa1" to "",
                "gaid" to "",
                "isVoice" to true,
                "marketId" to 0
            )
        var parmas = EncryptUtil.encode(body.toString())

        /**进行四级嵌套*/
        val paramsBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            EncryptUtil.encryptBody(parmas)
        )
        when {
            mViewModel.deal.get().length != 4 -> showDialogMessage("Enter your phone OTP")
            !mViewModel.isChecked.get() -> showDialogMessage("Please selected...")
            else -> {
                mViewModel.customerOtpCallBack(paramsBody)?.observe(this) {
                    hideSoftKeyboard(activity)
                    nav().navigateAction(R.id.action_mobile_to_code)
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.loginResult.observe(this, Observer {
            //请求成功  可以做保存信息等操作 ....
            LiveDataEvent.loginEvent.value = true //通知登录成功
        })
    }

    override fun onRequestError(loadStatus: LoadStatusEntity) {
        super.onRequestError(loadStatus)
        when (loadStatus.requestCode) {
            NetUrl.CUSTOMER_OTP, NetUrl.LOGIN -> {
                showDialogMessage(loadStatus.errorMessage)
            }
        }
    }

}