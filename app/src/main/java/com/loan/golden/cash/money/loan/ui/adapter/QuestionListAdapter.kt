package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.DiaplasisResponse
import com.loan.golden.cash.money.loan.databinding.ItemQuestionListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/13 15:02
 * @Describe    :
 */
class QuestionListAdapter(data: ArrayList<DiaplasisResponse.PageBean.ContentBean>) :
    BaseQuickAdapter<DiaplasisResponse.PageBean.ContentBean, DataBindBaseViewHolder>(
        R.layout.item_question_list, data
    ) {

    override fun convert(holder: DataBindBaseViewHolder, item: DiaplasisResponse.PageBean.ContentBean) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemQuestionListBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动
    }
}