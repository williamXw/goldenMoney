package com.loan.golden.cash.money.loan.ui.fragment.mine

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.alibaba.fastjson.JSONObject
import com.google.gson.Gson
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.loan.golden.cash.money.loan.app.base.BaseFragment
import com.loan.golden.cash.money.loan.app.ext.initBack
import com.loan.golden.cash.money.loan.app.permissions.PermissionInterceptor
import com.loan.golden.cash.money.loan.app.util.AESTool
import com.loan.golden.cash.money.loan.app.util.GlideEngine
import com.loan.golden.cash.money.loan.app.util.ImgUtils
import com.loan.golden.cash.money.loan.app.util.PictureUtil
import com.loan.golden.cash.money.loan.app.util.RxToast
import com.loan.golden.cash.money.loan.app.util.nav
import com.loan.golden.cash.money.loan.app.util.setOnclickNoRepeat
import com.loan.golden.cash.money.loan.app.util.startActivity
import com.loan.golden.cash.money.loan.data.commom.Constant
import com.loan.golden.cash.money.loan.data.param.RaddlemanParam
import com.loan.golden.cash.money.loan.data.param.UnrighteousnessParam
import com.loan.golden.cash.money.loan.data.response.ImageModel
import com.loan.golden.cash.money.loan.databinding.FragmentAskQuestionBinding
import com.loan.golden.cash.money.loan.ui.activity.LoginActivity
import com.loan.golden.cash.money.loan.ui.fragment.forms.ORCInspectionFragment
import com.loan.golden.cash.money.loan.ui.viewmodel.MineViewModel
import com.loan.golden.cash.money.wheelpicker.contract.OnOptionPickedListener
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity
import com.loan.golden.cash.money.wheelpicker.widget.QuestionTypePicker
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.PictureSelectorStyle
import me.hgj.mvvmhelper.ext.showLoadingExt
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.ref.WeakReference

/**
 * @Author      : hxw
 * @Date        : 2023/10/12 15:45
 * @Describe    : ask question
 */
class FragmentAskQuestion : BaseFragment<MineViewModel, FragmentAskQuestionBinding>(), OnOptionPickedListener {

    private var typeId: String = ""
    private var mapImageId: MutableMap<Int, Int> = mutableMapOf()
    private var mImages: ArrayList<String> = arrayListOf()
    private var mId = ""
    private var mJsonStr = ""
    private lateinit var mPicker: QuestionTypePicker
    private val mHandler = MyHandler(WeakReference(this))
    private var mRealPath: String = ""

    private class MyHandler(val wrActivity: WeakReference<FragmentAskQuestion>) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            wrActivity.get()?.run {
                when (msg.what) {
                    ORCInspectionFragment.WHAT -> {
                        mViewModel.streamStreambedCallBack(msg.obj as File)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.customToolbar.initBack("Ask questions") { nav().navigateUp() }
        mId = arguments?.getString("id").toString()
        upLoadImageAsk()
    }

    override fun onBindViewClick() {
        super.onBindViewClick()
        setOnclickNoRepeat(mBind.tvAskQuestionType, mBind.tvQuestionSubmit) {
            when (it) {
                mBind.tvAskQuestionType -> {
                    getUnrighteousness()
                }

                mBind.tvQuestionSubmit -> {
                    questionSubmit()
                }
            }
        }
    }

    private fun questionSubmit() {
        val content = mBind.etAskQuestionContent.text.toString().trim()
        if (content.isEmpty()) {
            RxToast.showToast("Please enter your questions and suggestions")
            return
        }
        val body = RaddlemanParam(
            RaddlemanParam.ModelBean(
                typeId = typeId,
                content = content,
                images = mImages,
                thirdOrderId = mId
            )
        )
        val gsonData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(gsonData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.raddledRaddlemanCallBack(paramsBody)
    }

    private fun getUnrighteousness() {
        showLoadingExt("loading.....")
        val body = UnrighteousnessParam(id = mId)
        val strData = Gson().toJson(body)
        val paramsBody = AESTool.encrypt1(strData, Constant.AES_KEY).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        mViewModel.unrighteousnessCallBack(paramsBody)
    }

    override fun onRequestSuccess() {
        super.onRequestSuccess()
        /** 提交反馈 */
        mViewModel.raddleedResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    RxToast.showToast("Submitted successfully")
                    nav().navigateUp()
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }

        }
        /** 获取反馈类型列表 */
        mViewModel.unrighteousnessResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    if (it.list.isNotEmpty()) {
                        mJsonStr = JSONObject.toJSONString(it.list)
                        selectedPicker(mJsonStr)
                    } else {
                        RxToast.showToast("Data acquisition exception")
                    }
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
        /** 上传文件2 (串行 请求 写法) */
        mViewModel.imageResult.observe(viewLifecycleOwner) {
            when (it.status) {
                1012 -> {
                    startActivity<LoginActivity>()
                }

                0 -> {
                    if (it.model != null) {
                        addNewData()
                    }
                }

                else -> {
                    RxToast.showToast(it.message)
                }
            }
        }
    }

    private fun selectedPicker(mJsonStr: String?) {
        if (mJsonStr?.isEmpty() == true) {
            RxToast.showToast("data exception")
            return
        }
        mPicker = activity?.let { it1 -> QuestionTypePicker(it1, mJsonStr) }!!
        mPicker.setOnOptionPickedListener(this)
        mPicker.wheelLayout.setOnOptionSelectedListener { position, _ ->
            mPicker.titleView.text = mPicker.wheelView.formatItem(position)
        }
        mPicker.show()
    }

    private fun addNewData() {
        val tempList: MutableList<ImageModel> = ArrayList()
        val model = ImageModel()
        model.path = mRealPath
        tempList.add(model)
        /** 调用新增数据 */
        mBind.uploadMultiImageView.addNewData(tempList.toList())
    }

    private fun upLoadImageAsk() {
        mBind.uploadMultiImageView
            .setImageInfoList(null)
            // 不开启拖拽排序
            .setDrag(false)
            // 设置每行3列
            .setColumns(3)
            // 显示新增按钮
            .setShowAdd(true)
            // 设置图片缩放类型 (默认 CENTER_CROP)
            .setScaleType(ImageView.ScaleType.CENTER_CROP)
            // item点击回调
            .setImageItemClickListener { _ -> }
            // 设置删除点击监听（如果不设置，测试默认移除数据），自己处理数据删除过程
            .setDeleteClickListener { multiImageView, position ->
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("delete")
                        .setMessage("Are you sure you want to delete it?")
                        .setNegativeButton("sure") { dialog, _ ->
                            dialog.dismiss()
                            multiImageView.deleteItem(position)
                            mapImageId.remove(position)
                        }
                        .show()
                }
            }
            // 图片加载
            .setImageViewLoader { _, path, imageView ->
                // （这里自己选择图片加载框架，不做限制）
                imageView.setImageURI(Uri.parse("file://$path"))
            }
            // 新增按钮点击回调
            .setAddClickListener {
                requestPermission()
            }
            .show()
    }

    private fun requestPermission() {
        XXPermissions.with(this)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .permission(Permission.READ_MEDIA_AUDIO)
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .interceptor(PermissionInterceptor())
            .request { _, allGranted ->
                if (allGranted) {
                    selectImage()
                } else {
                    RxToast.showToast("Insufficient permissions. Please manually enable permissions and try again")
                }
            }
    }

    private fun selectImage() {
        val selectionModel: PictureSelectionModel = PictureSelector.create(context)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(PictureSelectorStyle())
            .setImageEngine(GlideEngine.createGlideEngine())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setCameraInterceptListener(PictureUtil.getCustomCameraEvent())
            .setCompressEngine(PictureUtil.getCompressFileEngine())
            .isLoopAutoVideoPlay(false)
            .isPageSyncAlbumCount(true)
            .setLanguage(LanguageConfig.SYSTEM_LANGUAGE)
            .setQuerySortOrder("")
            .isDisplayCamera(true)
            .isOpenClickSound(true)
            .isPreviewImage(false)
            .isMaxSelectEnabledMask(false)
            .isDirectReturnSingle(false)
            .setMaxSelectNum(1)
            .isDirectReturnSingle(true)
        selectionModel.forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: java.util.ArrayList<LocalMedia>?) {
                mRealPath = result!![0].realPath
                /** 方案2：压缩图片后上传 */
                val bitmap = BitmapFactory.decodeFile(mRealPath)
                /** 循环压缩图片 耗时任务  在子线程中运行 */
                Thread {
                    val bitmapCompress = ImgUtils.compressByQuality(bitmap, 100)
                    val fileCompress = ImgUtils.saveBitmapFile(bitmapCompress)
                    mHandler.sendMessageDelayed(Message.obtain().apply {
                        what = ORCInspectionFragment.WHAT
                        obj = fileCompress
                    }, 300)
                }.start()
            }

            override fun onCancel() {}
        })
    }

    override fun onOptionPicked(position: Int, item: Any?) {
        typeId = (item as MaritalEntity).id
        mBind.tvAskQuestionType.text = mPicker.wheelView.formatItem(position)
    }
}