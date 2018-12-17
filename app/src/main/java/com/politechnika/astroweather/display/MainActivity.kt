package com.politechnika.astroweather.display

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openInfo(view: View) {
        startActivity(Intent(this@MainActivity, InfoActivity::class.java))
    }

    fun openSettings(view: View) {
        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
    }
}
