package com.rijaldev.kuiskocakseru.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private lateinit var etNama: EditText
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mp: MediaPlayer
    private val MY_PREF  = "UserSP"
    private val keyNama = "keyNama"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)
        etNama = binding.etNama
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        mp = MediaPlayer.create(context,R.raw.pop)
        val ads = activity?.findViewById<FrameLayout>(R.id.ad_container)
        ads?.visibility = View.GONE
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        viewListeners()
    }

    private fun viewListeners() {
        binding.btnInput.setOnClickListener {
            mp.start()
            if (TextUtils.isEmpty(etNama.text.trim().toString())) {
                binding.inputLayoutNama.error = "Masukkan nama dulu ya :)"
            } else {
                etNama.onEditorAction(EditorInfo.IME_ACTION_DONE)
                binding.inputLayoutNama.error = null

                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putString(keyNama, etNama.text.toString())
                editor.apply()

                Handler(Looper.getMainLooper()).postDelayed({ setFragment(HomeFragment()) }, 1000L)
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        val trans = activity?.supportFragmentManager?.beginTransaction()
        trans?.replace(R.id.main_frame, fragment)
        trans?.commit()
    }
}