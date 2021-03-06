package com.luc.console.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.postDelayed
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.luc.console.databinding.FragmentProductDetailBottomSheetBinding
import com.luc.console.home.adapter.RepuestosInListAdapter
import com.luc.console.utils.lerp
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
                behavior.isGestureInsetBottomIgnored = true
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
                if (behavior.state == BottomSheetBehavior.STATE_HIDDEN)
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        domainViewModel.settings.observe(viewLifecycleOwner) {
            setDescriptionText(it)
        }

        binding.sendEmail.setOnClickListener {
            domainViewModel.sendEmail()
        }

        binding.clearList.setOnClickListener {
            domainViewModel.clearSelectedRepuestoList()
        }

        repuestosInListAdapter.setOnAddClick { repuesto ->
            domainViewModel.addRepuesto(repuesto)
        }

        composeEmail()

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

    fun composeEmail() {
        binding.progressBar.alpha = 0f
        domainViewModel.email.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Loading -> {
                    with(binding) {
                        progressBar.visibility = View.VISIBLE
                        progressBar.animate().alpha(1f)
                        sendEmail.alpha = 0.3f
                        sendEmail.isEnabled = false
                        clearList.isEnabled = false

                    }
                }
                is NetworkStatus.Success -> {
                    with(binding) {
                        progressBar.visibility = View.INVISIBLE
                        sendEmail.isEnabled = true
                        sendEmail.alpha = 1f
                        clearList.isEnabled = true
                    }
                    Toast.makeText(
                        requireContext(),
                        "Email enviado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    view?.postDelayed(1000) {
                        behavior.state = BottomSheetBehavior.STATE_HIDDEN
                        domainViewModel.clearSelectedRepuestoList()
                    }
                }
                is NetworkStatus.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}