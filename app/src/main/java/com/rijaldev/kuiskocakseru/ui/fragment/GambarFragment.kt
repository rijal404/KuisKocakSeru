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
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentGambarBinding
import com.rijaldev.kuiskocakseru.ui.OnBackPressed
import com.rijaldev.kuiskocakseru.ui.model.*

class GambarFragment : Fragment(), View.OnClickListener, OnBackPressed {

    private lateinit var binding: FragmentGambarBinding
    private lateinit var listGambarSoal: MutableList<Pertanyaan>
    private var nomorPertanyaan: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mpPass: MediaPlayer
    private lateinit var mFail: MediaPlayer
    private var displayType: Int = 0
    private var mInterstitialAd : InterstitialAd? = null
    private lateinit var adRequest: AdRequest
    private lateinit var sharedPref: SharedPreferences
    private val MY_PREF  = "UserSP"
    private var keyHasil = ""
    private var typeData = 3
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGambarBinding.inflate(inflater, container, false)
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        displayType = requireArguments().getInt("type")
        mpPass = MediaPlayer.create(context, R.raw.pass)
        mFail = MediaPlayer.create(context, R.raw.error)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        val inflaterAnim = TransitionInflater.from(requireContext())
        enterTransition = inflaterAnim.inflateTransition(R.transition.fade)
        setUpView()
        setAds()
        binding.btnFirstImg.setOnClickListener(this)
        binding.btnSecondImg.setOnClickListener(this)
        binding.btnThirdImg.setOnClickListener(this)
        binding.btnFourthImg.setOnClickListener(this)
    }

    private fun setUpView() {

        when(displayType) {
            3 -> {
                getPertanyaan(DataIQ.listData)
                binding.imageGm.visibility = View.VISIBLE
                typeData = 3
                keyHasil = "keyIQ"
            }
            4 -> {
                getPertanyaan(DataMath.listData)
                binding.tvPertanyaanGm.visibility = View.VISIBLE
                typeData = 4
                keyHasil = "keyMath"
            }
            5 -> {
                getPertanyaan(DataFlags.listData)
                binding.imageGm.visibility = View.VISIBLE
                typeData = 5
                keyHasil = "keyFlag"
            }
        }
    }

    private fun getPertanyaan(collection: Collection<Pertanyaan>) {
        listGambarSoal = arrayListOf()
        listGambarSoal.addAll(collection)
        setPertanyaan()
        nomorPertanyaan = 0
    }

    private fun setPertanyaan() {
        binding.imageGm.setImageResource(listGambarSoal[0].gambar!!)
        binding.tvPertanyaanGm.text = listGambarSoal[0].pertanyaan
        binding.btnFirstImg.text = listGambarSoal[0].pilihanA
        binding.btnSecondImg.text = listGambarSoal[0].pilihanB
        binding.btnThirdImg.text = listGambarSoal[0].pilihanC
        binding.btnFourthImg.text = listGambarSoal[0].pilihanD

        setupProgressBar()
    }

    private fun setupProgressBar() {
        val progressBar = binding.proBar
        var i = 0
        progressBar.progress = i
        countDownTimer = object : CountDownTimer(10000, 1000) {
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
            R.id.btnFirstImg -> selectedPilihan = 1
            R.id.btnSecondImg -> selectedPilihan = 2
            R.id.btnThirdImg -> selectedPilihan = 3
            R.id.btnFourthImg -> selectedPilihan = 4
        }
        countDownTimer.cancel()
        if (v != null) {
            checkJawaban(selectedPilihan!!, v)
        }
    }

    private fun checkJawaban(selectedPilihan: Int, view: View) {
        if (selectedPilihan == listGambarSoal[nomorPertanyaan].jawaban) {
            mpPass.start()
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
            score += 10
        } else {
            mFail.start()
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#fe6a6a"))
            when(listGambarSoal[nomorPertanyaan].jawaban) {
                1 -> binding.btnFirstImg.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                2 -> binding.btnSecondImg.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                3 -> binding.btnThirdImg.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
                4 -> binding.btnFourthImg.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#05c23d"))
            }
            Handler(Looper.getMainLooper()).postDelayed({setFragment()},300L)
        }
        Handler(Looper.getMainLooper()).postDelayed({gantiPertanyaan()},300L)
    }

    private fun gantiPertanyaan() {
        if (nomorPertanyaan < listGambarSoal.size - 1) {

            nomorPertanyaan++

            playAnimation(binding.tvPertanyaanGm,0, 0)
            playAnimation(binding.btnFirstImg,0, 1)
            playAnimation(binding.btnSecondImg,0, 2)
            playAnimation(binding.btnThirdImg,0, 3)
            playAnimation(binding.btnFourthImg,0, 4)
            playAnimation(binding.imageGm, 0, 5)

            setupProgressBar()
        }else {
            setFragment()
        }
    }

    private fun setFragment() {
        val fr = HasilFragment()
        val bundle = Bundle().apply {
            putInt("keyType", displayType)
        }
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
                            0 -> binding.tvPertanyaanGm.text = listGambarSoal[nomorPertanyaan].pertanyaan
                            1 -> binding.btnFirstImg.text = listGambarSoal[nomorPertanyaan].pilihanA
                            2 -> binding.btnSecondImg.text = listGambarSoal[nomorPertanyaan].pilihanB
                            3 -> binding.btnThirdImg.text = listGambarSoal[nomorPertanyaan].pilihanC
                            4 -> binding.btnFourthImg.text = listGambarSoal[nomorPertanyaan].pilihanD
                            5 -> binding.imageGm.setImageResource(listGambarSoal[nomorPertanyaan].gambar!!)
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