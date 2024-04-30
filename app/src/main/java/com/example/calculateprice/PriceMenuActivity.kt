package com.example.calculateprice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PriceMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_menu)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Retrieve all item data from the intent (using Double for weight)
        val allItemNames = intent.getStringArrayExtra("allItemNames") ?: emptyArray()
        val allItemKg = intent.getIntArrayExtra("allItemKg") ?: IntArray(0)
        val allItemGram = intent.getIntArrayExtra("allItemGram") ?: IntArray(0)
        val allItemTotalPrice = intent.getDoubleArrayExtra("allItemTotalPrice") ?: DoubleArray(0)
        val totalPrice = intent.getDoubleExtra("totalPrice", 0.0)
        val allItemPricePerKg = intent.getDoubleArrayExtra("itemPricePerKg") ?: DoubleArray(0)

        // Add data rows for each item
        for (i in allItemNames.indices) {
            val itemName = allItemNames[i]
            val totalKg = allItemKg[i] + (allItemGram[i] / 1000.0) // Combine kg and gram
            val pricePerKg =
                allItemPricePerKg[i]// Assuming constant price per Kg (modify if needed)
            val itemTotalPrice = allItemTotalPrice[i]

            val tableRow = LayoutInflater.from(this).inflate(R.layout.table_row, null) as TableRow
            tableRow.findViewById<TextView>(R.id.nameTextView).text = itemName
            tableRow.findViewById<TextView>(R.id.totalKgTextView).text =
                String.format("%.2f", totalKg) // Format with 2 decimal places
            tableRow.findViewById<TextView>(R.id.kgPriceTextView).text =
                String.format("৳ %.2f", pricePerKg)
            tableRow.findViewById<TextView>(R.id.totalPriceTextView).text =
                String.format("৳ %.2f", itemTotalPrice)
            tableLayout.addView(tableRow)
        }

        val tableRowFinal =
            LayoutInflater.from(this).inflate(R.layout.table_row_final, null) as TableRow

        tableRowFinal.findViewById<TextView>(R.id.totalPriceTextView).text =
            String.format("৳ %.2f", totalPrice)
        tableLayout.addView(tableRowFinal)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(intent)
        }

    }
}

