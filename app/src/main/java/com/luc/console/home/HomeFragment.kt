package com.luc.console.home

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.luc.console.base.BaseFragment
import com.luc.console.databinding.FragmentHomeBinding
import com.luc.console.home.adapter.ViewPagerAdapter
import com.luc.presentation.viewmodel.DomainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val domainViewModel: DomainViewModel by sharedViewModel()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        domainViewModel.getCalderas.observe(viewLifecycleOwner) {
            viewPagerAdapter = ViewPagerAdapter(this, it)
            binding.viewPager.offscreenPageLimit = 3
            binding.viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = it[position].caldera
            }.attach()
        }
    }
}