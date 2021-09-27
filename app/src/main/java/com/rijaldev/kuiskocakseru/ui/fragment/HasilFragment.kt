package com.rijaldev.kuiskocakseru.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentHasilBinding
import com.rijaldev.kuiskocakseru.ui.OnBackPressed

class HasilFragment : Fragment(), OnBackPressed {

    private lateinit var binding: FragmentHasilBinding
    private var mInterstitialAd : InterstitialAd? = null
    private lateinit var adRequest: AdRequest
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mp: MediaPlayer
    private val MY_PREF  = "UserSP"
    private val keyNama = "keyNama"
    private var keyHasil = ""
    private var typeData : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHasilBinding.inflate(inflater, container, false)
        typeData = requireArguments().getInt("keyType", 1)
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        mp = MediaPlayer.create(context, R.raw.pop)
        setData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.color.white)
        setAds()
        setView()
    }

    private fun setData() {
        when(typeData) {
            1 -> keyHasil = "keyKocak"
            2 -> keyHasil = "keyUmum"
            3 -> keyHasil = "keyIQ"
            4 -> keyHasil = "keyMath"
            5 -> keyHasil = "keyFlag"
            6 -> keyHasil = "keyTech"
        }
    }

    private fun setView() {
        val nama = sharedPref.getString(keyNama, "")
        val hasil = sharedPref.getInt(keyHasil, 0)
        binding.score.text = hasil.toString()
        when(hasil) {
            in 0..50 -> setResult(R.drawable.fail, nama.toString(), "Tingkatkan skormu")
            else -> setResult(R.drawable.pass, nama.toString(), "Good job kawan!")
        }

        binding.btnBack.setTextColor(resources.getColor(R.color.white))
        binding.btnBack.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({onBackPressed()}, 1000L)
            Handler(Looper.getMainLooper()).postDelayed({fragment()},1000L)
        }
    }

    private fun setAds() {
        adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),"ca-app-pub-3940256099942544/8691691433", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {}

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {}

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
    }

    private fun setResult(resId: Int, name: String, message: String) {
        binding.avatar.setImageResource(resId)
        binding.message.text = "$name, $message"
    }

    private fun fragment() {
        val fm =  activity?.supportFragmentManager
        fm?.popBackStack("game", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val trans = fm?.beginTransaction()
        trans?.replace(R.id.main_frame, PilihFragment())
        trans?.commit()
    }

    override fun onBackPressed(): Boolean {
        return if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
            false
        } else {
            true
        }
    }
}