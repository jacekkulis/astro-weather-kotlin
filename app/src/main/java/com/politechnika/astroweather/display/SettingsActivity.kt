package com.politechnika.astroweather.display

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {
    internal lateinit var refreshTimeValue: EditText
    var longitude: Double = 19.451377
    var latitude: Double = 51.7444712
    var refreshTime: Int = 15


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        refreshTimeValue = findViewById<View>(R.id.refreshTimeValue) as EditText

        (findViewById<View>(R.id.latitudeValue) as TextView).text = java.lang.Double.toString(latitude)
        (findViewById<View>(R.id.longitudeValue) as TextView).text = java.lang.Double.toString(longitude)
        (findViewById<View>(R.id.refreshTimeValue) as TextView).text = Integer.toString(refreshTime)
    }


    fun save(view: View) {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))

        try {
            if (TextUtils.isEmpty(refreshTimeValue.text.toString()) || refreshTimeValue.text.toString().isEmpty()) {
                refreshTime = 15
                (findViewById<View>(R.id.refreshTimeValue) as TextView).text = "15"
                Toast.makeText(this, "Nic nie wpisano. Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) == 0) {
                refreshTime = 15
                Toast.makeText(this, "Nie można wpisac 0. Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) < 0) {
                refreshTime = 15
                Toast.makeText(this, "Nie można wpisac wartosci ujemnej(min. 1). Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) > 60) {
                refreshTime = 15
                Toast.makeText(this, "Zbyt duza wartosc (max. 60). Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else {
                refreshTime = Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString())
            }

            latitude = java.lang.Double.parseDouble((findViewById<View>(R.id.latitudeValue) as TextView).text.toString())
            longitude = java.lang.Double.parseDouble((findViewById<View>(R.id.longitudeValue) as TextView).text.toString())
        } catch (e: Exception) {
            Toast.makeText(this, "Wrong input.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    fun cancel(view: View) {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
    }


}
