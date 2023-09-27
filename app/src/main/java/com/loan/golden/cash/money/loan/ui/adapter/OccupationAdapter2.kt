package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.KaliResponse
import com.loan.golden.cash.money.loan.databinding.ItemOccupation2ListBinding
import com.loan.golden.cash.money.loan.databinding.ItemOccupationListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 15:36
 * @Describe    :
 */
class OccupationAdapter2(data: ArrayList<KaliResponse.ModelInfo.ChildrenBean>) :
    BaseQuickAdapter<KaliResponse.ModelInfo.ChildrenBean, DataBindBaseViewHolder>(R.layout.item_occupation2_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: KaliResponse.ModelInfo.ChildrenBean) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemOccupation2ListBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动
    }
}