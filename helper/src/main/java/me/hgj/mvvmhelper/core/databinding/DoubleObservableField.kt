package me.hgj.mvvmhelper.core.databinding

import androidx.databinding.ObservableField

/**
 * 作者　: hxw
 * 时间　: 2023/9/3
 * 描述　:自定义的Int类型 ObservableField  提供了默认值，避免取值的时候还要判空
 */
class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {

    override fun get(): Double {
        return super.get()!!
    }

}