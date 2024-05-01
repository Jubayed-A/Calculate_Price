package com.example.calculateprice.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculateprice.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spalsh)

        Handler(Looper.getMainLooper()).postDelayed({
            // Start your main activity here
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}