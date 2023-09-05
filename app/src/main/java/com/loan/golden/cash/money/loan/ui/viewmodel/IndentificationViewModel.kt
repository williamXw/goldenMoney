package com.loan.golden.cash.money.loan.ui.viewmodel

import me.hgj.mvvmhelper.base.BaseViewModel
import me.hgj.mvvmhelper.core.databinding.StringObservableField

/**
 *  author : huxiaowei
 *  date : 2022/9/25 13:39
 *  description :
 */
class IndentificationViewModel : BaseViewModel() {

    var title = StringObservableField()
    var name = StringObservableField()
    var namePan = StringObservableField()
    var panNo = StringObservableField()
    var aadhaarNo = StringObservableField()
    var dateOfBirth = StringObservableField()
    var gender = StringObservableField()
    var pincode = StringObservableField()
    var email = StringObservableField()
    var address = StringObservableField()


}