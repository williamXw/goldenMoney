package me.hgj.mvvmhelper.core.livedata

import androidx.lifecycle.MutableLiveData


/**
 * 作者　: hxw
 * 时间　: 2023/9/3
 * 描述　:自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 */
class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}