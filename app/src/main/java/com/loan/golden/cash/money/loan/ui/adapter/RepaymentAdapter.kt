package com.loan.golden.cash.money.loan.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.base.DataBindBaseViewHolder
import com.loan.golden.cash.money.loan.data.response.OrogenyResponse
import com.loan.golden.cash.money.loan.databinding.ItemRepaymentMethodBinding

/**
 * @Author      : hxw
 * @Date        : 2023/10/19 8:45
 * @Describe    :
 */
class RepaymentAdapter(data: ArrayList<OrogenyResponse.ModelBean.MethodsBean>) :
    BaseQuickAdapter<OrogenyResponse.ModelBean.MethodsBean, DataBindBaseViewHolder>(R.layout.item_repayment_method, data) {

    override fun convert(holder: DataBindBaseViewHolder, item: OrogenyResponse.ModelBean.MethodsBean) {
        val binding = DataBindBaseViewHolder.getBinding(holder) as ItemRepaymentMethodBinding
        binding.data = item
        binding.executePendingBindings()//防止列表抖动

        if (item.isSelected) {
            holder.setBackgroundResource(R.id.ivRepaymentSelected, R.mipmap.icon_repayment_selected)
        } else {
            holder.setBackgroundResource(R.id.ivRepaymentSelected, R.mipmap.icon_repayment_unselected)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemSelected(position: Int) {
        for (index in 0 until data.size) {
            data[index].isSelected = (index == position)
        }
        notifyDataSetChanged()
    }
}