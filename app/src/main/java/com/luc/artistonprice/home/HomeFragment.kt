package com.luc.artistonprice.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.luc.artistonprice.R
import com.luc.artistonprice.base.BaseFragment
import com.luc.artistonprice.databinding.FragmentHomeBinding
import com.luc.artistonprice.home.adapter.ViewPagerAdapter
import com.luc.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.calderaList.observe(viewLifecycleOwner) {
            viewPagerAdapter = ViewPagerAdapter(requireActivity(), it)
            binding.viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = it[position].caldera
            }.attach()
        }
    }
}