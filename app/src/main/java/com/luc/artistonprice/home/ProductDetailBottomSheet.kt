package com.luc.artistonprice.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.luc.artistonprice.databinding.FragmentProductDetailBottomSheetBinding
import com.luc.artistonprice.home.adapter.RepuestosInListAdapter
import com.luc.artistonprice.utils.lerp
import com.luc.common.NetworkStatus
import com.luc.common.model.Repuesto
import com.luc.common.model.Settings
import com.luc.presentation.viewmodel.DomainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ProductDetailBottomSheet : Fragment() {

    private val domainViewModel: DomainViewModel by sharedViewModel()
    private val repuestosInListAdapter: RepuestosInListAdapter by lazy { RepuestosInListAdapter() }

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentProductDetailBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentProductDetailBottomSheetBinding.inflate(inflater, container, false).apply {
                behavior = BottomSheetBehavior.from(sheetContainer)
                behavior.isHideable = true
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
                            binding.arrowUp.rotation = lerp(0f, 180f, 0.2f, 0.8f, slideOffset)
                            recyclerView.alpha = lerp(0f, 1f, 0.2f, 0.8f, slideOffset)
                        }
                    })
                }
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
                arrowUp.setOnClickListener {
                    behavior.state = if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                        BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        domainViewModel.selectedRepuestoList.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = repuestosInListAdapter
            repuestosInListAdapter.repuestoList = it
            if (it.isEmpty())
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
            else {
                setUpView(it)
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        domainViewModel.settings.observe(viewLifecycleOwner) {
            setDescriptionText(it)
        }

        binding.sendEmail.setOnClickListener {
            composeEmail(
                arrayOf("luciano.sisemas.12@gmail.com"),
                "Email from Ariston"
            )
        }
    }

    fun setDescriptionText(settings: Settings) {
        binding.serviceDescripcion.text = if (settings.applyIva) "Service + IVA" else "Service"
        binding.publicoDescripcion.text =
            if (settings.applyGain) "Publico + IVA + %${settings.gainValue}" else "Publico + IVA"
    }

    fun setUpView(repuesto: List<Repuesto>) {
        binding.totalPriceService.text = "ARS$${repuesto.sumOf { it.precioServiceInARS }}"
        binding.totalPricePublico.text = "ARS$${repuesto.sumOf { it.precioPublicoInARS }}"
        binding.description.text = repuesto.last().descripcion
    }

    fun composeEmail(addresses: Array<String>, subject: String) {

        domainViewModel.sendEmail.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Success -> Log.d("tests", it.data)
                is NetworkStatus.Error -> Log.d("tests", it.message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}