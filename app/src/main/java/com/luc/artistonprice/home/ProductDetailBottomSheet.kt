package com.luc.artistonprice.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.doOnLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.luc.artistonprice.R
import com.luc.artistonprice.databinding.FragmentProductDetailBottomSheetBinding
import com.luc.artistonprice.utils.lerp


class ProductDetailBottomSheet : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =
            FragmentProductDetailBottomSheetBinding.inflate(inflater, container, false).apply {
                val behavior = BottomSheetBehavior.from(sheetContainer)
                val backCallback =
                    requireActivity().onBackPressedDispatcher.addCallback(
                        viewLifecycleOwner,
                        false
                    ) {
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                sheetContainer.doOnLayout {
                    behavior.addBottomSheetCallback(object :
                        BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            backCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            recyclerView.alpha = lerp(1f, 0f, 0f, 0.15f, slideOffset)
                        }
                    })
                }

            }

        return binding.root
    }


}