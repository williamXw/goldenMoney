package com.loan.golden.cash.money.loan.app.ext

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.loan.golden.cash.money.loan.R
import com.loan.golden.cash.money.loan.app.util.SettingUtil
import com.loan.golden.cash.money.loan.app.util.toHtml
import com.loan.golden.cash.money.loan.ui.view.DefineLoadMoreView
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import me.hgj.mvvmhelper.base.appContext

/**
 * 作者　: hxw
 * 时间　: 2020/2/20
 * 描述　:项目中自定义类的拓展函数
 */

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.icon_back_black,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    setBackgroundColor(resources.getColor(R.color.colorPrimary))
    title = titleStr.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

//fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
//    //是否可滑动
//    this.isUserInputEnabled = false
//    this.offscreenPageLimit = 5
//    //设置适配器
//    adapter = object : FragmentStateAdapter(fragment) {
//        override fun createFragment(position: Int): Fragment {
//            when (position) {
//                0 -> {
//                    return LoanFragment()
//                }
//                1 -> {
//                    return RePaymentFragment()
//                }
//                2 -> {
//                    return AccountFragment()
//                }
//                else -> {
//                    return LoanFragment()
//                }
//            }
//        }
//
//        override fun getItemCount() = 3
//    }
//    return this
//}


fun BottomNavigationViewEx.init(navigationItemSelectedAction: (Int) -> Unit): BottomNavigationViewEx {
    enableAnimation(true)
    enableShiftingMode(false)
    enableItemShiftingMode(true)
    itemIconTintList = SettingUtil.getColorStateList(SettingUtil.getColor(appContext))
    itemTextColor = SettingUtil.getColorStateList(appContext)
    setTextSize(12F)
    setOnNavigationItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}

/**
 * 拦截BottomNavigation长按事件 防止长按时出现Toast ---- 追求完美的大屌群友提的bug
 * @receiver BottomNavigationViewEx
 * @param ids IntArray
 */
fun BottomNavigationViewEx.interceptLongClick(vararg ids: Int) {
    val bottomNavigationMenuView: ViewGroup = (this.getChildAt(0) as ViewGroup)
    for (index in ids.indices) {
        bottomNavigationMenuView.getChildAt(index).findViewById<View>(ids[index]).setOnLongClickListener {
            true
        }
    }
}

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        setColorSchemeColors(SettingUtil.getColor(appContext))
    }
}

fun SwipeRecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

fun SwipeRecyclerView.initFooter(loadmoreListener: SwipeRecyclerView.LoadMoreListener): DefineLoadMoreView {
    val footerView = DefineLoadMoreView(appContext)
    //给尾部设置颜色
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(appContext))
    //设置尾部点击回调
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadmoreListener.onLoadMore()
    })
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadmoreListener)
    }
    return footerView
}