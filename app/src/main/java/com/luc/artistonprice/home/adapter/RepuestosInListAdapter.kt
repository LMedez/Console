package com.luc.artistonprice.home.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.luc.artistonprice.R
import com.luc.artistonprice.databinding.ProductItemBinding
import com.luc.artistonprice.databinding.RepuestoItemBinding
import com.luc.artistonprice.utils.capitalizeFirstChar
import com.luc.common.model.Repuesto
import com.luc.presentation.viewmodel.RepuestoConverted

class RepuestosInListAdapter :
    ListAdapter<Repuesto, RepuestosInListAdapter.ViewHolder>(CalderaDiffCallback) {


    private var onCheckBoxClick: ((Repuesto, Boolean) -> Unit)? = null

    fun setOnCheckBoxClick(listener: (Repuesto, Boolean) -> Unit) {
        onCheckBoxClick = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ProductItemBinding.bind(view)

        fun bind(repuesto: Repuesto) = with(binding) {
            calderaName.text = repuesto.calderaName
            productName.text = repuesto.descripcion.capitalizeFirstChar()
            servicePriceAndUsd.text = "USD$${repuesto.precioService} x ${repuesto.dolarValue}"
            publicoAndUsd.text = "USD$${repuesto.precioPublico} x ${repuesto.dolarValue} + %${repuesto.gainValue}"
            servicePriceArs.text = "ARS$${repuesto.precioServiceInARS}"
            publicoPriceArs.text = "ARS$${repuesto.precioPublicoInARS}"
        }
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repuesto = currentList[position]
        holder.bind(repuesto)
    }

    object CalderaDiffCallback : DiffUtil.ItemCallback<Repuesto>() {
        override fun areItemsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem == newItem
        }
    }
}