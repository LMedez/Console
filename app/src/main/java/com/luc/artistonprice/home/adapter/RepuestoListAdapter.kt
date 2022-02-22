package com.luc.artistonprice.home.adapter

import android.graphics.drawable.Drawable
import android.util.Log
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
import com.luc.artistonprice.utils.getDrawableOrNull
import com.luc.artistonprice.utils.themeColor
import com.luc.common.model.Repuesto

class RepuestoListAdapter :
    ListAdapter<Repuesto, RepuestoListAdapter.ViewHolder>(CalderaDiffCallback) {

    val itemStateArray = SparseBooleanArray()

    private var onCheckBoxClick: ((Repuesto, Boolean) -> Unit)? = null

    fun setOnCheckBoxClick(listener: (Repuesto, Boolean) -> Unit) {
        onCheckBoxClick = listener
    }

    var repuestoList: List<Repuesto> = listOf()
        set(value) {
            submitList(value)
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RepuestoItemBinding.bind(view)

        fun bind(repuesto: Repuesto) = with(binding) {
            descripcion.text = repuesto.descripcion
            codigo.text = repuesto.codigo
            precioServiceDescription.text = if (repuesto.settings?.applyIva != false) "Service + IVA" else "Service"
            precioService.text = "$${repuesto._precioService}"
            precioPublico.text = "$${repuesto.precioPublico}"

            checkBox.setOnClickListener {
                onCheckBoxClick?.let { click ->
                    if (!itemStateArray.get(adapterPosition, false)) {//checkbox checked
                        checkIndicator.setBackgroundColor(checkBox.context.themeColor(R.attr.colorSecondary))
                        checkBox.isChecked = true
                        click(repuesto, true)
                        //stores checkbox states and position
                        itemStateArray.put(adapterPosition, true)
                    } else {//checkbox unchecked
                        checkIndicator.background =
                            checkBox.context.getDrawableOrNull(R.drawable.divider)
                        checkBox.isChecked = false
                        click(repuesto, false)
                        //stores checkbox states and position.
                        itemStateArray.put(adapterPosition, false)

                    }
                }
            }
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

        if (itemStateArray.get(position, false))
            holder.itemView.findViewById<View>(R.id.checkIndicator)
                .setBackgroundColor(holder.itemView.context.themeColor(R.attr.colorSecondary))
        else holder.itemView.findViewById<View>(R.id.checkIndicator)
            .background = holder.itemView.context.getDrawableOrNull(R.drawable.divider)
    }

    object CalderaDiffCallback : DiffUtil.ItemCallback<Repuesto>() {
        override fun areItemsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repuesto, newItem: Repuesto): Boolean {
            return oldItem.settings == newItem.settings
        }
    }
}