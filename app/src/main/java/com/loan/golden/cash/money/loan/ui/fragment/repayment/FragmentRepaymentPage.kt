package com.loan.golden.cash.money.loan.ui.fragment.repayment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.FragmentRepaymentPageBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel
import me.hgj.mvvmhelper.util.LogUtils

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 10:27
 * @Describe    : 还款页面
 */
class FragmentRepaymentPage : BaseFragment<RepaymentViewModel, FragmentRepaymentPageBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        url = arguments?.getString("url")
        initWebView(url)

        /** 监听系统返回键 */
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                nav().navigateUp()
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String?) {
        url?.let { mBind.webViewRepayment.loadUrl(it) }
        val webSettings = mBind.webViewRepayment.settings
        webSettings.userAgentString = webSettings.userAgentString + "Android-Pigeon"
        webSettings.javaScriptEnabled = true//允许使用js
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE//不使用缓存，只从网络获取数据.
        // 自适应屏幕
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.loadWithOverviewMode = false//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.domStorageEnabled = true//DOM Storage
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW//加载https和http混合模式
        mBind.webViewRepayment.webChromeClient = webChromeClient
        mBind.webViewRepayment.webViewClient = webViewClient
    }

    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {//页面加载完成
            LogUtils.debugInfo("页面加载完成")
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return super.shouldOverrideUrlLoading(view, url)
        }
    }

    private var url: String? = ""
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return super.onJsAlert(view, url, message, result)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (mBind.progressBar != null) {
                mBind.progressBar.visibility = View.VISIBLE
            }
            mBind.progressBar.progress = newProgress
            if (newProgress == 100) {
                mBind.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        mBind.webViewRepayment.reload()
        super.onStart()
    }

    override fun onDestroyView()  {
        mBind.webViewRepayment.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mBind.webViewRepayment.clearHistory()
        mBind.webViewRepayment.destroy()
        super.onDestroyView()
    }
}