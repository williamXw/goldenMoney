package com.loan.golden.cash.money.loan.ui.fragment.repayment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.databinding.FragmentRepaymentPageBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.RepaymentViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 10:27
 * @Describe    : 还款页面
 */
class FragmentRepaymentPage : BaseFragment<RepaymentViewModel, FragmentRepaymentPageBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val url = arguments?.getString("url")
        initWebView(url)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String?) {
        url?.let { mBind.webview.loadUrl(it) }
        val webSettings = mBind.webview.settings
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
        mBind.webview.webChromeClient = webChromeClient
    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return super.onJsAlert(view, url, message, result)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            mBind.progressBar.visibility = View.VISIBLE
            mBind.progressBar.progress = newProgress
            if (newProgress == 100) {
                mBind.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBind.webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mBind.webview.clearHistory()
        mBind.webview.destroy()
    }
}