package com.luc.console.home

import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.util.forEach
import com.luc.console.base.BaseFragment
import com.luc.console.databinding.FragmentRepuestoListBinding
import com.luc.console.home.adapter.RepuestoListAdapter
import com.luc.console.utils.SparseBooleanArrayParcelable
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
        binding.recyclerView.itemAnimator = null
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

        domainViewModel.selectedRepuestoList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                repuestoListAdapter.itemStateArray.forEach { key, _ ->
                    repuestoListAdapter.notifyItemChanged(key)
                }
                repuestoListAdapter.itemStateArray.clear()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sparseBooleanArray: SparseBooleanArray? =
            savedInstanceState?.getBundle(CHECKBOX_BUNDLE)?.getParcelable(
                CHECKBOX_LIST
            ) as? SparseBooleanArray

        sparseBooleanArray?.let {
            repuestoListAdapter.itemStateArray = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(
            CHECKBOX_BUNDLE,
            bundleOf(CHECKBOX_LIST to SparseBooleanArrayParcelable(repuestoListAdapter.itemStateArray))
        )
    }
}

private const val CHECKBOX_LIST = "checBoxList"
private const val CHECKBOX_BUNDLE = "checBoxBundle"