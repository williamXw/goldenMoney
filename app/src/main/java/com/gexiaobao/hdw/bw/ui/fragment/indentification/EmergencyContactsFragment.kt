package com.gexiaobao.hdw.bw.ui.fragment.indentification

import android.os.Bundle
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.app.util.nav
import com.gexiaobao.hdw.bw.app.util.setOnclickNoRepeat
import com.gexiaobao.hdw.bw.databinding.FragmentEmergencyContactsBinding
import com.gexiaobao.hdw.bw.ui.viewmodel.EmergencyContactsFragmentVM

/**
 *  author : huxiaowei
 *  date : 2022/9/27 22:55
 *  description :紧急联系人
 */
class EmergencyContactsFragment :
    BaseFragment<EmergencyContactsFragmentVM, FragmentEmergencyContactsBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewmodel = mViewModel
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.ivBack, mBind.btnNext) {
            when (it) {
                mBind.ivBack -> {
                    nav().navigateUp()
                }
                mBind.btnNext -> {

                }
            }
        }
    }

}