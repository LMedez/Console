package com.luc.artistonprice.home.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.luc.artistonprice.home.ProductListFragment
import com.luc.common.model.Caldera

private const val ARG_OBJECT = "arg"
class ViewPagerAdapter (fa: Fragment, private val calderaList: List<Caldera>):  FragmentStateAdapter(fa) {
    override fun getItemCount() = calderaList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = ProductListFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(ARG_OBJECT, calderaList[position])
        }
        return fragment
    }


}