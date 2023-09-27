package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.KaliResponse
import com.loan.golden.cash.money.loan.databinding.ItemOccupationListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 15:36
 * @Describe    :
 */
class OccupationAdapter(data: ArrayList<KaliResponse.ModelInfo>) :
    BaseQuickAdapter<KaliResponse.ModelInfo, DataBindBaseViewHolder>(R.layout.item_occupation_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: KaliResponse.ModelInfo) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemOccupationListBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动
    }
}