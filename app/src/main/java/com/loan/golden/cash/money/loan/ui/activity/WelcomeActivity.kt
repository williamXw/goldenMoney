package com.loan.golden.cash.money.loan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jaeger.library.StatusBarUtil
import com.loan.golden.cash.money.loan.app.ext.countDownCoroutines
import com.loan.golden.cash.money.loan.app.util.CacheUtil
import com.loan.golden.cash.money.loan.app.util.KvUtils
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.Job
import org.jetbrains.anko.startActivity

/**
 * created by : huxiaowei
 * @date : 2022/09/06
 * Describe : 欢迎页
 */
class WelcomeActivity : AppCompatActivity() {

    private val binding: ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //设置状态栏全透明
        StatusBarUtil.setTransparent(this)
        startCountDownCoroutines()
    }

    private fun startCountDownCoroutines() {
        val isInit = CacheUtil.isLogin()
        val isAgree = KvUtils.decodeBooleanTure(Constant.IS_AGREE_PRIVACY, false)
        //开启倒计时3s
        job = countDownCoroutines(1, {
        }, {
            /** 如果登陆过就直接跳转主页面 否则去登录 */
            if (isAgree) {
                if (isInit) {
                    startActivity<MainActivity>()
                } else {
                    startActivity<LoginActivity>()
                }
            } else {
                startActivity<PrivacyPolicyActivity>()
            }
            finish()
        }, lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (job != null) {
            job?.cancel()
            job = null
        }
    }
}

