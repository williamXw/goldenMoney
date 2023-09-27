package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.FigeaterResponse
import com.loan.golden.cash.money.loan.databinding.ItemFigeaterListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/9/27 15:36
 * @Describe    :
 */
class FigeaterAdapter(data: ArrayList<FigeaterResponse.ModelInfo>) :
    BaseQuickAdapter<FigeaterResponse.ModelInfo, DataBindBaseViewHolder>(R.layout.item_figeater_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: FigeaterResponse.ModelInfo) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemFigeaterListBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动
    }
}