package com.loan.golden.cash.money.loan.app.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author      : hxw
 * @Date        : 2023/4/23 13:58
 * @Describe    :
 */
class DataBindBaseViewHolder(view: View) : BaseViewHolder(view) {
    private var binding: ViewDataBinding? = null

    init {
        binding = DataBindingUtil.bind(itemView)
    }

    companion object {
        fun getBinding(dataBindBaseViewHolder: DataBindBaseViewHolder): ViewDataBinding? {
            return dataBindBaseViewHolder.binding
        }
    }
}