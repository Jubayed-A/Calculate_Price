package com.example.calculateprice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class CalculateAdapter(private var items: MutableList<CalculateList>) :
    RecyclerView.Adapter<CalculateAdapter.ItemViewHolder>() {

    init {
        this.items = ArrayList<CalculateList>() // Example initialization (empty list)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val kgText: TextView = itemView.findViewById(R.id.kgText)
        val edKg: TextInputEditText = itemView.findViewById(R.id.edKg)
        val gramText: TextView = itemView.findViewById(R.id.gramText)
        val edGram: TextInputEditText = itemView.findViewById(R.id.edGram)
        val edPrice: TextInputEditText = itemView.findViewById(R.id.edPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_calculate, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = "Item ${position + 1}"
        holder.kgText.text = "KG"
        holder.gramText.text = "Gram"

        holder.edKg.hint = ""
        holder.edGram.hint = ""
        holder.edPrice.hint = ""

        // Set listeners to update KG and Gram values when text changes
        holder.edKg.addTextChangedListener {
            val kg = it.toString().toDoubleOrNull() ?: 0.0
            item.kg = kg.toInt()
        }

        holder.edGram.addTextChangedListener {
            val gram = it.toString().toDoubleOrNull() ?: 0.0
            item.gram = gram.toInt()
        }

        // Set a text change listener for edPrice to capture the price per kg
        holder.edPrice.addTextChangedListener { priceText ->
            val price = priceText.toString().toDoubleOrNull() ?: 0.0
            item.pricePerKg = price
        }

    }

    override fun getItemCount(): Int = items.size

    fun addItem(newItem: CalculateList) {
        items.add(items.size, newItem)
        notifyItemInserted(items.size - 1)
    }

    fun removeLastItem() {
        if (items.isNotEmpty()) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size)
        }
    }

    fun getPricePerKg(): List<Double> {
        val pricePerKgList = mutableListOf<Double>()
        for (item in items) {
            pricePerKgList.add(item.pricePerKg)
        }
        return pricePerKgList
    }

    fun getItems(): List<CalculateList> { // Public getter function
        return items.toList() // Return an immutable copy
    }

}