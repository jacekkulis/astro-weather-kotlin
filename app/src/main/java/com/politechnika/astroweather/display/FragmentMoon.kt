package com.politechnika.astroweather.display

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.astrocalculator.AstroCalculator
import com.astrocalculator.AstroDateTime
import java.util.*

class FragmentMoon : Fragment() {
    private var astroCalculator: AstroCalculator? = null
    private var infoActivity: InfoActivity? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.moon_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        infoActivity = this.activity as InfoActivity

        val location = AstroCalculator.Location(infoActivity!!.latitude, infoActivity!!.longitude)
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT+2:00")

        val timeZone = TimeZone.getDefault()
        val offset = 2
        val astroDateTime = AstroDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), offset, timeZone.useDaylightTime())
        astroCalculator = AstroCalculator(astroDateTime, location)

        setMoonData()

        val t = object : Thread() {

            override fun run() {
                try {
                    while (!isInterrupted) {
                        Thread.sleep((60000 * infoActivity!!.refreshTime).toLong())
                        if (isAdded) {
                            activity.runOnUiThread { setMoonData() }
                        }
                    }
                } catch (e: InterruptedException) {
                }

            }
        }
        t.start()


    }


    private fun setMoonrise(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.moonInfo.moonrise
        (view.findViewById<View>(R.id.moon_TV_moonrise_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setMoonset(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.moonInfo.moonset
        (view.findViewById<View>(R.id.moon_TV_moonset_value) as TextView).text = formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setNewmoon(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.moonInfo.nextNewMoon
        (view.findViewById<View>(R.id.moon_TV_newmoon_value) as TextView).text = formatToTwoDigits(astroDateTime.day) + "." + formatToTwoDigits(astroDateTime.month) + "." + formatToTwoDigits(astroDateTime.year) + "r., " + formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setFullmoon(view: View, astroCalculator: AstroCalculator) {
        val astroDateTime = astroCalculator.moonInfo.nextFullMoon
        (view.findViewById<View>(R.id.moon_TV_full_value) as TextView).text = formatToTwoDigits(astroDateTime.day) + "." + formatToTwoDigits(astroDateTime.month) + "." + formatToTwoDigits(astroDateTime.year) + "r., " + formatToTwoDigits(astroDateTime.hour) + ":" + formatToTwoDigits(astroDateTime.minute) + ":" + formatToTwoDigits(astroDateTime.second)
    }

    private fun setPhase(view: View, astroCalculator: AstroCalculator) {
        var moonPhase = astroCalculator.moonInfo.illumination
        moonPhase *= 100.0
        (view.findViewById<View>(R.id.moon_TV_phase_value) as TextView).setText(String.format("%.2f", moonPhase) + "%")
    }

    private fun setSynodicDay(view: View, astroCalculator: AstroCalculator) {
        val moonAge = astroCalculator.moonInfo.age
        (view.findViewById<View>(R.id.moon_TV_synodic_value) as TextView).text = moonAge.toInt().toString() + " dzień"
    }

    private fun setMoonData() {
        setMoonrise(view!!, astroCalculator!!)
        setMoonset(view!!, astroCalculator!!)
        setNewmoon(view!!, astroCalculator!!)
        setFullmoon(view!!, astroCalculator!!)
        setPhase(view!!, astroCalculator!!)
        setSynodicDay(view!!, astroCalculator!!)
    }

    private fun formatToTwoDigits(number: Int): String {
        return String.format("%1$02d", number)
    }

    companion object {

        fun newInstance(): FragmentMoon {
            return FragmentMoon()
        }
    }
}
