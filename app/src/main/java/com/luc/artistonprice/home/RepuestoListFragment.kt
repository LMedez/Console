package com.luc.artistonprice.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.luc.artistonprice.base.BaseFragment
import com.luc.artistonprice.databinding.FragmentRepuestoListBinding
import com.luc.artistonprice.home.adapter.RepuestoListAdapter
import com.luc.common.model.Caldera
import com.luc.presentation.viewmodel.DomainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val ARG_OBJECT = "arg"

class ProductListFragment :
    BaseFragment<FragmentRepuestoListBinding>(FragmentRepuestoListBinding::inflate) {

    private val domainViewModel: DomainViewModel by sharedViewModel()

    private val repuestoListAdapter: RepuestoListAdapter by lazy { RepuestoListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var caldera: Caldera? = null

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            caldera = (getParcelable(ARG_OBJECT) as? Caldera)
            binding.recyclerView.adapter = repuestoListAdapter
        }

        domainViewModel.repuestoList(caldera?.id ?: return).observe(viewLifecycleOwner) {
            repuestoListAdapter.repuestoList = it
        }

        repuestoListAdapter.setOnCheckBoxClick { repuesto, checked ->
            if (checked) domainViewModel.addRepuesto(repuesto)
            else domainViewModel.removeRepuesto(repuesto)
        }
    }

}