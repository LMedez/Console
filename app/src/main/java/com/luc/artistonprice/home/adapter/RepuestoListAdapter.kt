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
import com.luc.artistonprice.databinding.RepuestoItemBinding
import com.luc.common.model.Repuesto

class RepuestoListAdapter :
    ListAdapter<Repuesto, RepuestoListAdapter.ViewHolder>(CalderaDiffCallback) {

    val itemStateArray = SparseBooleanArray()

    private var onCheckBoxClick: ((Repuesto) -> Unit)? = null

    fun setOnCheckBoxClick(listener: (Repuesto) -> Unit) {
        onCheckBoxClick = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepuestoItemBinding.bind(view)
        private val checkBox = binding.checkBox

        init {
            checkBox.setOnClickListener {
                if (!itemStateArray.get(adapterPosition, false)) {//checkbox checked
                    checkBox.isChecked = true
                    //stores checkbox states and position
                    itemStateArray.put(adapterPosition, true)
                } else {//checkbox unchecked
                    checkBox.isChecked = false
                    //stores checkbox states and position.
                    itemStateArray.put(adapterPosition, false)
                }
            }
        }

        fun bind(repuesto: Repuesto) = with(binding) {
            descripcion.text = repuesto.descripcion
            codigo.text = repuesto.codigo
            precioService.text = "$${repuesto.precioService}"
            precioPublico.text = "$${repuesto.precioPublico}"
        }
    }

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repuesto_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repuesto = currentList[position]
        holder.bind(repuesto)
        holder.itemView.findViewById<MaterialCheckBox>(R.id.checkBox).isChecked =
            itemStateArray.get(position, false)
    }

    object CalderaDiffCallback : DiffUtil.ItemCallback<Repuesto>() {
        override fun areItemsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem == newItem
        }
    }
}