package com.loan.golden.cash.money.loan.ui.adapter

import android.annotation.SuppressLint
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

        if (item.isSelected) {
            holder.setBackgroundResource(R.id.llBottom, R.drawable.round_blue_solid_bg10dp)
            holder.setBackgroundResource(R.id.ivNapperItemSelected, R.mipmap.icon_selected_napper)
        } else {
            holder.setBackgroundResource(R.id.llBottom, R.drawable.round_white_bg_10dp)
            holder.setBackgroundResource(R.id.ivNapperItemSelected, R.mipmap.icon_unselected_napper)
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