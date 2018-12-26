package com.politechnika.astroweather.display

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View

class InfoActivity : AppCompatActivity() {

    var longitude: Double = 19.451377
    var latitude: Double = 51.7444712
    var refreshTime: Int = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            setContentView(R.layout.activity_info_slider)
            val pager = findViewById<View>(R.id.viewPager) as ViewPager
            pager.adapter = MyPagerAdapter(supportFragmentManager)
        } else if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setContentView(R.layout.activity_info)
        }

    }


    fun openSettings(view: View) {
        startActivity(Intent(this@InfoActivity, SettingsActivity::class.java))
    }


    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(pos: Int): Fragment {
            when (pos) {
                0 -> return FragmentSun.newInstance()
                1 -> return FragmentMoon.newInstance()
                else -> return FragmentSun.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }


}
