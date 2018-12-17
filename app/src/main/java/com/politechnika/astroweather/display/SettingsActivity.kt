package com.politechnika.astroweather.display

import android.content.Intent
import android.os.Bundle
import android.support.v4.util.TimeUtils
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import org.w3c.dom.Text

class SettingsActivity : AppCompatActivity() {
    internal lateinit var refreshTimeValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        refreshTimeValue = findViewById<View>(R.id.refreshTimeValue) as EditText
        (findViewById<View>(R.id.latitudeValue) as TextView).text = java.lang.Double.toString(InfoActivity.latitude)
        (findViewById<View>(R.id.longitudeValue) as TextView).text = java.lang.Double.toString(InfoActivity.longitude)
        (findViewById<View>(R.id.refreshTimeValue) as TextView).text = Integer.toString(InfoActivity.refreshTime)
    }


    fun save(view: View) {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))

        try {
            if (TextUtils.isEmpty(refreshTimeValue.text.toString()) || refreshTimeValue.text.toString().trim { it <= ' ' } == null || refreshTimeValue.text.toString().length == 0) {
                InfoActivity.refreshTime = 15
                (findViewById<View>(R.id.refreshTimeValue) as TextView).text = "15"
                Toast.makeText(this, "Nic nie wpisano. Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) == 0) {
                InfoActivity.refreshTime = 15
                Toast.makeText(this, "Nie można wpisac 0. Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) < 0) {
                InfoActivity.refreshTime = 15
                Toast.makeText(this, "Nie można wpisac wartosci ujemnej(min. 1). Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString()) > 60) {
                InfoActivity.refreshTime = 15
                Toast.makeText(this, "Zbyt duza wartosc (max. 60). Ustawiono wartosc domyslna.", Toast.LENGTH_SHORT).show()
            } else {
                InfoActivity.refreshTime = Integer.parseInt((findViewById<View>(R.id.refreshTimeValue) as TextView).text.toString())
            }

            InfoActivity.latitude = java.lang.Double.parseDouble((findViewById<View>(R.id.latitudeValue) as TextView).text.toString())
            InfoActivity.longitude = java.lang.Double.parseDouble((findViewById<View>(R.id.longitudeValue) as TextView).text.toString())
        } catch (e: Exception) {
            Toast.makeText(this, "Wrong input.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }

    fun cancel(view: View) {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
    }


}
