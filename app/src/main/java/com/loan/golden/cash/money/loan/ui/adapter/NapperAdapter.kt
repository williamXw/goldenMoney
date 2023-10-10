package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.NapperResponse
import com.loan.golden.cash.money.loan.databinding.ItemNapperListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/10 16:03
 * @Describe    :
 */
class NapperAdapter(data: ArrayList<NapperResponse.PageBean.ContentBean>) :
    BaseQuickAdapter<NapperResponse.PageBean.ContentBean, DataBindBaseViewHolder>(R.layout.item_napper_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: NapperResponse.PageBean.ContentBean) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemNapperListBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动


    }
}