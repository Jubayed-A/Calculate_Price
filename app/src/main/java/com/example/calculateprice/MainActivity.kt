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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        fabAddItem = findViewById(R.id.btnAdd)
        val btnCalculate = findViewById<Button>(R.id.btnCalculated)


        adapter = CalculateAdapter(mutableListOf())
        recyclerView.adapter = adapter

        val defaultItem = CalculateList("First Item",0.0,0.0, 0.0,0.0)
        adapter.addItem(defaultItem)

        fabAddItem.setOnClickListener {
            val newItem = CalculateList("New Item", 0.0, 0.0, 0.0, 0.0)
            adapter.addItem(newItem)
            // Scroll to the new item (optional)
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }


        btnCalculate.setOnClickListener {
            // Check if the adapter is set and not empty
            if (adapter.itemCount == 0) {
                Toast.makeText(this, "Add items to calculate", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Access the first item in the list
            val item = adapter.getItems().first()

            // Check if KG or gram has a value
            if (item.kg == 0.0 && item.gram == 0.0) {
                Toast.makeText(this, "Enter either KG or Gram value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if Price Per Kg has a value
            if (item.pricePerKg == 0.0) {
                Toast.makeText(this, "Enter Price Per Kg", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start PriceMenuActivity with item details
            val intent = Intent(this, PriceMenuActivity::class.java)
            intent.apply {
                putExtra("itemName", item.name)
                putExtra("itemKg", item.kg)
                putExtra("itemGram", item.gram)
                putExtra("itemPricePerKg", item.pricePerKg)
                putExtra("totalPrice", item.price)
            }
            startActivity(intent)
        }

    }

}
