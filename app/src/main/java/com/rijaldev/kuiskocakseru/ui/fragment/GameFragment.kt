package com.rijaldev.kuiskocakseru.ui.fragment

import android.animation.Animator
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.FragmentManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentGameBinding
import com.rijaldev.kuiskocakseru.ui.OnBackPressed
import com.rijaldev.kuiskocakseru.ui.model.DataKocak
import com.rijaldev.kuiskocakseru.ui.model.DataTech
import com.rijaldev.kuiskocakseru.ui.model.DataUmum
import com.rijaldev.kuiskocakseru.ui.model.Pertanyaan

class GameFragment : Fragment(), View.OnClickListener, OnBackPressed {

    private lateinit var binding: FragmentGameBinding
    private lateinit var listPertanyaan: MutableList<Pertanyaan>
    private var nomorPertanyaan: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mpPass: MediaPlayer
    private lateinit var mpFail: MediaPlayer
    private var displayType: Int = 0
    private var mInterstitialAd : InterstitialAd? = null
    private lateinit var adRequest: AdRequest
    private lateinit var sharedPref: SharedPreferences
    private val MY_PREF  = "UserSP"
    private var keyHasil = "keyHasil"
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        displayType = requireArguments().getInt("type")
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        mpPass = MediaPlayer.create(context, R.raw.pass)
        mpFail = MediaPlayer.create(context, R.raw.error)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        val inflaterAnim = TransitionInflater.from(requireContext())
        enterTransition = inflaterAnim.inflateTransition(R.transition.fade)
        setUpView()
        setAds()
        binding.btnFirst.setOnClickListener(this)
        binding.btnSecond.setOnClickListener(this)
        binding.btnThird.setOnClickListener(this)
        binding.btnFourth.setOnClickListener(this)
    }

    private fun setUpView() {

        when(displayType) {
            1 -> {
                getPertanyaan(DataKocak.listData)
                keyHasil = "keyKocak"

            }
            2 -> {
                getPertanyaan(DataUmum.listData)
                keyHasil = "keyUmum"
            }
            6 -> {
                getPertanyaan(DataTech.listData)
                keyHasil = "keyTech"
            }
        }
    }

    private fun getPertanyaan(collection: Collection<Pertanyaan>) {
        listPertanyaan = arrayListOf()
        listPertanyaan.addAll(collection)
        setPertanyaan()
        nomorPertanyaan = 0
    }

    private fun setPertanyaan() {
        binding.tvPertanyaan.text = listPertanyaan[0].pertanyaan
        binding.btnFirst.text = listPertanyaan[0].pilihanA
        binding.btnSecond.text = listPertanyaan[0].pilihanB
        binding.btnThird.text = listPertanyaan[0].pilihanC
        binding.btnFourth.text = listPertanyaan[0].pilihanD

        setupProgressBar()
    }

    private fun setupProgressBar() {
        val progressBar = binding.progressBar
        var i = 0

        progressBar.progress = i
        countDownTimer = object :CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                i++
                progressBar.progress = i * 100 / (10000/1000)
            }
            override fun onFinish() {
                i++
                setFragment()
            }
        }
        countDownTimer.start()
    }

    override fun onClick(v: View?) {

        var selectedPilihan: Int? = null
        when(v?.id) {
            R.id.btnFirst -> selectedPilihan = 1
            R.id.btnSecond -> selectedPilihan = 2
            R.id.btnThird -> selectedPilihan = 3
            R.id.btnFourth -> selectedPilihan = 4
        }
        countDownTimer.cancel()
        if (v != null) {
            checkJawaban(selectedPilihan!!, v)
        }
    }

    private fun checkJawaban(selectedPilihan: Int, view: View) {
        if (selectedPilihan == listPertanyaan[nomorPertanyaan].jawaban) {
            mpPass.start()
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
            score += 10
        } else {
            mpFail.start()
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fe6a6a"))
            when(listPertanyaan[nomorPertanyaan].jawaban) {
                1 -> binding.btnFirst.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                2 -> binding.btnSecond.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                3 -> binding.btnThird.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                4 -> binding.btnFourth.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
            }
            Handler(Looper.getMainLooper()).postDelayed({setFragment()},300L)
        }
        Handler(Looper.getMainLooper()).postDelayed({gantiPertanyaan()},300L)
    }

    private fun gantiPertanyaan() {
        if (nomorPertanyaan < listPertanyaan.size - 1) {

            nomorPertanyaan++

            playAnimation(binding.tvPertanyaan,0, 0)
            playAnimation(binding.btnFirst,0, 1)
            playAnimation(binding.btnSecond,0, 2)
            playAnimation(binding.btnThird,0, 3)
            playAnimation(binding.btnFourth,0, 4)

            setupProgressBar()
        }else {
            setFragment()
        }
    }

    private fun setFragment() {
        val fr = HasilFragment()

        val bundle = Bundle().apply { putInt("keyType", displayType)}
        fr.arguments = bundle

        val manager = activity?.supportFragmentManager
        manager?.popBackStack("pilih", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(keyHasil, score)
        editor.apply()

        val trans = manager?.beginTransaction()
        trans?.replace(R.id.main_frame, fr)
        trans?.addToBackStack("game")
        trans?.commit()
    }

    private fun playAnimation(view: View, value: Int, viewNum: Int) {
        view.animate().alpha(value.toFloat()).scaleX(value.toFloat()).scaleY(value.toFloat()).setDuration(500).setStartDelay(100)
            .setInterpolator(DecelerateInterpolator()).setListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(p0: Animator?) {}

                override fun onAnimationEnd(p0: Animator?) {
                    if (value == 0) {
                        when(viewNum) {
                            0 -> binding.tvPertanyaan.text = listPertanyaan[nomorPertanyaan].pertanyaan
                            1 -> binding.btnFirst.text = listPertanyaan[nomorPertanyaan].pilihanA
                            2 -> binding.btnSecond.text = listPertanyaan[nomorPertanyaan].pilihanB
                            3 -> binding.btnThird.text = listPertanyaan[nomorPertanyaan].pilihanC
                            4 -> binding.btnFourth.text = listPertanyaan[nomorPertanyaan].pilihanD
                        }

                        view.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

                        playAnimation(view, 1, viewNum)
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {}

                override fun onAnimationRepeat(p0: Animator?) {}
            })
    }

    private fun setAds() {
        adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),"#ISI ID IKLAN ANDA", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
            }
        }
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