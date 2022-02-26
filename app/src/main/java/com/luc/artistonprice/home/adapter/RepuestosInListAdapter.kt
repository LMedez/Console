package com.luc.artistonprice.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luc.artistonprice.R
import com.luc.artistonprice.databinding.ProductItemBinding
import com.luc.artistonprice.utils.capitalizeFirstChar
import com.luc.common.model.Repuesto

class RepuestosInListAdapter :
    ListAdapter<Repuesto, RepuestosInListAdapter.ViewHolder>(CalderaDiffCallback) {


    private var onAddClick: ((Repuesto) -> Unit)? = null

    fun setOnAddClick(listener: (Repuesto) -> Unit) {
        onAddClick = listener
    }

    var repuestoList: List<Repuesto> = listOf()
        set(value) {
            submitList(value)
            notifyDataSetChanged()
        }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ProductItemBinding.bind(view)

        fun bind(repuesto: Repuesto) = with(binding) {
            addButton.setOnClickListener {
                onAddClick?.let { click ->
                    click(repuesto)
                }
            }
            val count = if (repuesto.count != 1) "(${repuesto.count})" else ""
            calderaName.text = repuesto.calderaName
            productName.text =
                "${repuesto.descripcion.capitalizeFirstChar()} $count"
            servicePriceAndUsd.text = "USD$${repuesto._precioService} x ${repuesto.dolarValue}"
            publicoAndUsd.text =
                if (repuesto.settings?.applyGain != false) "USD$${repuesto._precioPublico} x ${repuesto.dolarValue} + %${repuesto.gainValue}"
                else "USD$${repuesto._precioPublico} x ${repuesto.dolarValue}"
            servicePriceArs.text = "ARS$${repuesto.precioServiceInARS}"
            publicoPriceArs.text = "ARS$${repuesto.precioPublicoInARS}"
            serviceDescripcion.text =
                if (repuesto.settings?.applyIva != false) "Service + IVA" else "Service"
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