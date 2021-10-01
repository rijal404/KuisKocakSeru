package com.rijaldev.kuiskocakseru.ui

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.ActivityMainBinding
import com.rijaldev.kuiskocakseru.ui.fragment.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adView: AdView
    private var initialLayoutComplete = false
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mp: MediaPlayer
    private val MY_PREF  = "UserSP"
    private val keyNama = "keyNama"
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density
            var adWidthPixels = binding.adContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mp = MediaPlayer.create(this, R.raw.pop)
        setView()
        setAds()
    }

    override fun onPause() {
        adView.pause()
        mp.release()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun setView() {
        sharedPref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)

        when(sharedPref.getString(keyNama, null)) {
            null -> showFragment(InputFragment())
            else -> showFragment(HomeFragment())
        }
    }

    private fun setAds() {
        MobileAds.initialize(this) {}

        adView = AdView(this)
        binding.adContainer.addView(adView)
        binding.adContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }

    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID
        adView.adSize = adSize
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun showFragment(fragment: Fragment) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.main_frame, fragment)
        trans.commit()
    }

    override fun onBackPressed() {
        tellFragments()
        super.onBackPressed()
    }

    private fun tellFragments() {
        val fragment: List<Fragment> = supportFragmentManager.fragments
        for (i in fragment) {
            when(i) {
                is GameFragment -> i.onBackPressed()
                is HasilFragment -> i.onBackPressed()
                is GambarFragment -> i.onBackPressed()
            }
        }
    }

    companion object {
        private const val AD_UNIT_ID = "ca-app-pub-3895931842313770/1178207613"
    }
}