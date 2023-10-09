package com.loan.golden.cash.money.loan.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.databinding.ItemContactListBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/9 16:07
 * @Describe    :
 */
class ContactAdapter(data: ArrayList<Int>) : BaseQuickAdapter<Int, DataBindBaseViewHolder>(R.layout.item_contact_list, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: Int) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemContactListBinding
//        binding.data = item
        binding.executePendingBindings()//防止列表抖动
        holder.setText(R.id.tvContactNum, "Contact Person " + SettingUtil.add(holder.layoutPosition.toString(), "1"))
    }
}