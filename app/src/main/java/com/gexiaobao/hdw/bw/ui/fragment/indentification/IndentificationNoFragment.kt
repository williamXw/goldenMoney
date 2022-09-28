package com.gexiaobao.hdw.bw.ui.fragment.indentification

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import com.gexiaobao.hdw.bw.R
import com.gexiaobao.hdw.bw.app.base.BaseFragment
import com.gexiaobao.hdw.bw.app.util.GlideEngine
import com.gexiaobao.hdw.bw.app.util.nav
import com.gexiaobao.hdw.bw.app.util.navigateAction
import com.gexiaobao.hdw.bw.app.util.setOnclickNoRepeat
import com.gexiaobao.hdw.bw.databinding.FragmentIndentificationCardBinding
import com.gexiaobao.hdw.bw.databinding.FragmentIndentificationNoBinding
import com.gexiaobao.hdw.bw.ui.viewmodel.IndentificationViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener


/**
 *  author : huxiaowei
 *  date : 2022/9/25 13:39
 *  description :
 */
class IndentificationNoFragment :
    BaseFragment<IndentificationViewModel, FragmentIndentificationNoBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewmodel = mViewModel
    }

    override fun initData() {
        super.initData()
        mViewModel.title.set("Identification")
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.ivBack, mBind.btnContinue) {
            when (it) {
                mBind.ivBack -> {
                    nav().navigateUp()
                }
                mBind.btnContinue -> {
//                    nav().navigateAction(R.id.action_inden_no_to_inden_face)
                    nav().navigateAction(R.id.action_inden_no_to_inden_contacts)
                }
            }
        }
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
    }
}