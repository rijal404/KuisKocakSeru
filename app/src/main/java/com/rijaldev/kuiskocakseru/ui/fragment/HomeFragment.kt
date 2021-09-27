package com.rijaldev.kuiskocakseru.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mp: MediaPlayer
    private val MY_PREF  = "UserSP"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mp = MediaPlayer.create(context, R.raw.pop)
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflaterAnim = TransitionInflater.from(requireContext())
        enterTransition = inflaterAnim.inflateTransition(R.transition.slide)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        val ads = activity?.findViewById<FrameLayout>(R.id.ad_container)
        ads?.visibility = View.VISIBLE
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnPlay.setOnClickListener {
            mp.start()
            showFragment(PilihFragment())
        }

        binding.btnHistory.setOnClickListener {
            mp.start()
            showFragment(HistoryFragment())
        }

        binding.btnBantuan.setOnClickListener {
            mp.start()
            showAlert(R.drawable.help, R.string.bantuan, R.layout.help_message)
        }

        binding.btnShare.setOnClickListener {
            mp.start()
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Yuk buruan download Game ini, kira-kira kamu bisa sampai skor berapa nih? Langsung saja cuss... dijamin seru! \nhttps://play.google.com/store/apps/details?id=com.rijaldev.kuiskocakseru")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, "Share ke:"))
        }

        binding.btnAbout.setOnClickListener {
            mp.start()
            showAlert(R.drawable.about, R.string.tentang, R.layout.about_message)
        }

        binding.btnLogout.setOnClickListener {
            mp.start()
            val alert = AlertDialog.Builder(requireContext()).apply {
                setIcon(R.drawable.logout)
                setTitle(R.string.keluar)
                setMessage("\nSemua data akan di hapus, lanjutkan?\n")
                setNeutralButton("Ya") { _, _ ->
                    mp.start()
                    Handler(Looper.getMainLooper()).postDelayed({ logOut() }, 1000L)
                }
                setPositiveButton("Tidak", null)
            }
            alert.create()
            alert.show()
        }
    }

    private fun logOut() {
        val editor: SharedPreferences.Editor = sharedPref.edit().clear()
        editor.apply()
        val tran = activity?.supportFragmentManager?.beginTransaction()
        tran?.replace(R.id.main_frame, InputFragment())
        tran?.commit()
    }

    private fun showFragment(fragment: Fragment) {
        val tran = activity?.supportFragmentManager?.beginTransaction()
        tran?.replace(R.id.main_frame, fragment)
        tran?.addToBackStack(null)
        tran?.commit()
    }

    private fun showAlert(icon: Int, title: Int, view: Int) {
        val alert = AlertDialog.Builder(requireContext()).apply {
            setIcon(icon)
            setTitle(title)
            setView(view)
            setPositiveButton("Oke", null)
        }
        alert.create()
        alert.show()
    }
}