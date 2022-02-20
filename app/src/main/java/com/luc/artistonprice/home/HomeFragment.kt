package com.luc.artistonprice.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.luc.artistonprice.base.BaseFragment
import com.luc.artistonprice.databinding.FragmentHomeBinding
import com.luc.artistonprice.home.adapter.ViewPagerAdapter
import com.luc.presentation.viewmodel.DomainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val domainViewModel: DomainViewModel by sharedViewModel()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        domainViewModel.getCalderas.observe(viewLifecycleOwner) {
            viewPagerAdapter = ViewPagerAdapter(requireActivity(), it)
            binding.viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = it[position].caldera
            }.attach()
        }
    }
}