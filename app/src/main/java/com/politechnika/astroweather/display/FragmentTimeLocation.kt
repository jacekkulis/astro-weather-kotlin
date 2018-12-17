package com.politechnika.astroweather.display

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class FragmentTimeLocation : Fragment() {
    private var infoActivity: InfoActivity? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        infoActivity = this.activity as InfoActivity

        inflater.inflate(R.layout.time_location_fragment, container, false)
        updateTimeField(view!!)

        val location = AstroCalculator.Location(infoActivity!!.latitude, infoActivity!!.longitude)

        var latitude = location.latitude
        var longitude = location.longitude
        val latitudeText: String
        val longitudeText: String

        if (latitude < 0) {
            latitude -= 2 * latitude
            latitudeText = java.lang.Double.toString(latitude) + "S"
        } else if (latitude > 0) {
            latitudeText = java.lang.Double.toString(latitude) + "N"
        } else {
            latitudeText = java.lang.Double.toString(latitude)
        }
        if (longitude < 0) {
            longitude -= 2 * longitude
            longitudeText = java.lang.Double.toString(longitude) + "W"
        } else if (latitude > 0) {
            longitudeText = java.lang.Double.toString(longitude) + "E"
        } else {
            longitudeText = java.lang.Double.toString(longitude)
        }

        (view!!.findViewById<View>(R.id.time_TV_localization_value) as TextView).text = "Szer: $latitudeText, dł: $longitudeText"

        val t = object : Thread() {

            override fun run() {
                try {
                    while (!isInterrupted) {
                        Thread.sleep(1000)
                        if (isAdded) {
                            activity.runOnUiThread { updateTimeField(view!!) }
                        }
                    }
                } catch (e: InterruptedException) {

                }

            }
        }

        t.start()

        return view
    }

    private fun updateTimeField(view: View) {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+2:00")
        val timeZone = TimeZone.getDefault()
        val offset = 2

        Log.d("TimeZoneOffset", SimpleDateFormat("hh-mm-ss").format(Calendar.getInstance().time))

        val astroDateTime = AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), offset, timeZone.useDaylightTime())

        val time = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
        (view.findViewById<View>(R.id.time_TV_time_value) as TextView).text = time
    }

    private fun formatToTwoDigits(number: Int): String {
        return String.format("%1$02d", number)
    }

}
