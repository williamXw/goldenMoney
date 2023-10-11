package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.BlackshirtResponse
import com.loan.golden.cash.money.loan.databinding.ItemBlackshirtListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/11 11:38
 * @Describe    :
 */
class BlackshirtAdapter(data: ArrayList<BlackshirtResponse.PageBean.ContentBean>) :
    BaseQuickAdapter<BlackshirtResponse.PageBean.ContentBean, DataBindBaseViewHolder>(R.layout.item_blackshirt_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: BlackshirtResponse.PageBean.ContentBean) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemBlackshirtListBinding
        binding.data = item.product
        binding.executePendingBindings()//防止列表抖动
    }
}