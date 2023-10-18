package com.loan.golden.cash.money.loan.ui.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.app.util.ImageLoaderManager
import com.loan.golden.cash.money.loan.databinding.ItemQuestionImageBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/18 13:46
 * @Describe    :
 */
class QuestionImageAdapter(data: ArrayList<String>) : BaseQuickAdapter<String, DataBindBaseViewHolder>(R.layout.item_question_image, data) {
    override fun convert(holder: DataBindBaseViewHolder, item: String) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemQuestionImageBinding
//        binding.data = item
        binding.executePendingBindings()//防止列表抖动

        val imageView = holder.getView<AppCompatImageView>(R.id.ivQuestionImage)
        ImageLoaderManager.loadRoundImage(context, item, imageView, 5)
    }
}