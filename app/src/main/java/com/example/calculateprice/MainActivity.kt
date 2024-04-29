package com.example.calculateprice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalculateAdapter
    private lateinit var fabAddItem: FloatingActionButton
    private lateinit var fabRemoveItem: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        fabAddItem = findViewById(R.id.btnAdd)
        fabRemoveItem = findViewById(R.id.btnRemove)
        val btnCalculate = findViewById<Button>(R.id.btnCalculated)


        adapter = CalculateAdapter(mutableListOf())
        recyclerView.adapter = adapter

        val defaultItem = CalculateList("Item 1", 0, 0, 0.0, 0.0)
        adapter.addItem(defaultItem)

        fabAddItem.setOnClickListener {
            val newItem = CalculateList("Item ${adapter.itemCount + 1}", 0, 0, 0.0, 0.0)
            adapter.addItem(newItem)
            // Scroll to the new item (optional)
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        fabRemoveItem.setOnClickListener {
            // Check if there are any items to remove
            if (adapter.itemCount > 0) {
                // Remove the last item from the list
                adapter.removeLastItem()
            } else {
                // If there are no items to remove, show a toast message
                Toast.makeText(this, "No items to remove", Toast.LENGTH_SHORT).show()
            }

        }

        btnCalculate.setOnClickListener {
            // Check if the adapter is set and not empty
            if (adapter.itemCount == 0) {
                Toast.makeText(this, "Add items to calculate", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var totalPrice = 0.0
            val allItemNames = mutableListOf<String>()
            val allItemKg = mutableListOf<Int>()
            val allItemGram = mutableListOf<Int>()
            val allItemTotalPrice = mutableListOf<Double>()

            // Loop through all items in the list
            for (item in adapter.getItems()) {
                // Check if KG or gram has a value
                if (item.kg == 0 && item.gram == 0) {
                    Toast.makeText(this, "Enter either KG or Gram value", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Check if Price Per Kg has a value
                if (item.pricePerKg == 0.0) {
                    Toast.makeText(this, "Enter Price Per Kg.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Calculate total kg for the item
                val totalKg = item.kg + (item.gram / 1000.0) // Convert gram to kg and add to kg

                // Calculate individual item price
                val itemPrice = totalKg * item.pricePerKg

                // Accumulate total price
                totalPrice += itemPrice

                // Store item details for passing to PriceMenuActivity
                allItemNames.add(item.name)
                allItemKg.add(item.kg)
                allItemGram.add(item.gram)
                allItemTotalPrice.add(itemPrice)
            }

            // Start PriceMenuActivity with total price and all item details
            val intent = Intent(this, PriceMenuActivity::class.java)
            intent.apply {
                putExtra("totalPrice", totalPrice)
                putExtra("itemPricePerKg", adapter.getPricePerKg().toDoubleArray())
                putExtra(
                    "allItemNames",
                    allItemNames.toTypedArray()
                ) // Convert list to string array
                putExtra("allItemKg", allItemKg.toIntArray()) // Convert list to double array
                putExtra("allItemGram", allItemGram.toIntArray()) // Convert list to double array
                putExtra(
                    "allItemTotalPrice",
                    allItemTotalPrice.toDoubleArray()
                ) // Convert list to double array
            }
            startActivity(intent)
        }


    }

}
