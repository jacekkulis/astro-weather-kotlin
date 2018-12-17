package com.politechnika.astroweather.display

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime

import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class FragmentSun : Fragment() {
    //private var view: View? = null
    private var astroCalculator: AstroCalculator? = null
    private var infoActivity: InfoActivity? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflater!!.inflate(R.layout.sun_fragment, container, false)
        infoActivity = activity as InfoActivity

        val location = AstroCalculator.Location(infoActivity!!.latitude, infoActivity!!.longitude)
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+2:00")
        val timeZone = TimeZone.getDefault()
        val offset = 2
        val astroDateTime = AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), offset, timeZone.useDaylightTime())
        astroCalculator = AstroCalculator(astroDateTime, location)

        setSunData()

        val t = object : Thread() {

            override fun run() {
                try {
                    while (!isInterrupted) {
                        Thread.sleep((60000 * infoActivity!!.refreshTime).toLong())
                        if (isAdded) {
                            activity.runOnUiThread { setSunData() }
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }
        t.start()

        return view
    }

    private fun setSunrise(view: View, astroCalculator: AstroCalculator) {
        val now = Date()
        val astroDateTime = astroCalculator.sunInfo.sunrise

        (view.findViewById<View>(R.id.sun_TV_sunrise_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second) + ", azymut: " + String.format("%.2f", astroCalculator.sunInfo.azimuthRise)
    }

    private fun setSunset(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.sunInfo.sunset
        (view.findViewById<View>(R.id.sun_TV_sunset_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second) + ", azymut: " + String.format("%.2f", astroCalculator.sunInfo.azimuthSet)
    }

    private fun setTwilight(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.sunInfo.twilightEvening
        (view.findViewById<View>(R.id.sun_TV_twilight_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setCivilTwilight(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.sunInfo.twilightMorning
        (view.findViewById<View>(R.id.sun_TV_civilTwilight_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setSunData() {
        setSunrise(view!!, astroCalculator!!)
        setSunset(view!!, astroCalculator!!)
        setTwilight(view!!, astroCalculator!!)
        setCivilTwilight(view!!, astroCalculator!!)
    }

    private fun formatToTwoDigits(number: Int): String {
        return String.format("%1$02d", number)
    }

    companion object {

        fun newInstance(): FragmentSun {
            return FragmentSun()
        }
    }

}

