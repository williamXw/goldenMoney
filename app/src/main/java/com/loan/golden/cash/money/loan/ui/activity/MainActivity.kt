package com.loan.golden.cash.money.loan.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseActivity
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.DeviceUpLoadService
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.response.UpLoadDeviceInfoResponse
import com.loan.golden.cash.money.loan.databinding.ActivityMainBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MainViewModel
import org.json.JSONObject

/**
 * created by : huxiaowei
 * @date : 20220920
 * Describe :
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L
    lateinit var myBinder: DeviceUpLoadService.mBinder

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myBinder = p1 as DeviceUpLoadService.mBinder
            myBinder.a()
            mViewModel.nappyNapraPath()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    override fun initView(savedInstanceState: Bundle?) {

        val intent = Intent(this, DeviceUpLoadService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)//绑定Service

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.main_fragment) {
                    //如果当前界面不是主页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.show("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        finish()
                    }
                }
            }
        })
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.nappyResult.observe(this) {
            if (it.code == 200) {
                val dataBody = it.body!!.string()
                if (dataBody.isNotEmpty()) {
                    val mResponse = AESTool.decrypt(dataBody, Constant.AES_KEY)
                    val gson = Gson()
                    val mData: UpLoadDeviceInfoResponse = gson.fromJson(mResponse, UpLoadDeviceInfoResponse::class.java)
                    if (mData.status == 1012) {
                        startActivity<LoginActivity>()
                        return@observe
                    }
                    if (mData.status == 0) {
                        if (mData.list.isNotEmpty()) {
                            mData.list.forEachIndexed { _, s ->
                                when (s) {
                                    "DEVICE" -> {

                                    }
                                }
                            }
                        }
                    } else {
                        val msg = JSONObject(mResponse).getString(Constant.MESSAGE)
                        RxToast.showToast(msg)
                    }
                } else {
                    RxToast.showToast("dataBody is empty")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)//解绑Service
    }
}