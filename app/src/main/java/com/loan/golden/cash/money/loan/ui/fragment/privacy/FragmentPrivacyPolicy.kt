package com.loan.golden.cash.money.loan.ui.fragment.privacy

import android.os.Bundle
import android.view.View
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.core.view.isVisible
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.databinding.ActivityPrivacyPolicyBinding
import me.hgj.mvvmhelper.base.BaseViewModel

/**
 * @Author      : hxw
 * @Date        : 2023/9/6 16:42
 * @Describe    : 隐私政策fragment
 */
class FragmentPrivacyPolicy : BaseFragment<BaseViewModel, ActivityPrivacyPolicyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Privacy Policy") { nav().navigateUp() }
        initWebView()
        mBind.llBtnBottom.isVisible = false
    }

    private fun initWebView() {
        val url = arguments?.getString("url")
        if (url != null) {
            mBind.webView.loadUrl(url)
        }
        val webSettings = mBind.webView.settings
        webSettings.userAgentString = webSettings.userAgentString + "Android-GoldenMoney"
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE//不使用缓存，只从网络获取数据.
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true//设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.javaScriptEnabled = true//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webSettings.loadWithOverviewMode = false//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.domStorageEnabled = true//DOM Storage
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW//加载https和http混合模式
        mBind.webView.webChromeClient = webChromeClient
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
        mBind.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mBind.webView.clearHistory()
        mBind.webView.destroy()
        super.onDestroyView()
    }
}