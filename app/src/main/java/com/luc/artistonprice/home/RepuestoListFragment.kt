package com.luc.artistonprice.home

import android.os.Bundle
import android.view.View
import com.luc.artistonprice.base.BaseFragment
import com.luc.artistonprice.databinding.FragmentRepuestoListBinding

private const val ARG_OBJECT = "arg"
class ProductListFragment : BaseFragment<FragmentRepuestoListBinding>(FragmentRepuestoListBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            //binding.text.text = (getParcelable(ARG_OBJECT) as? Caldera)?.caldera
        }
    }
}