package com.rijaldev.kuiskocakseru.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var sharedPref: SharedPreferences
    private val MY_PREF  = "UserSP"
    private val keyKocak = "keyKocak"
    private val keyUmum = "keyUmum"
    private val keyIQ = "keyIQ"
    private val keyMath = "keyMath"
    private val keyFlag = "keyFlag"
    private val keyTech = "keyTech"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        sharedPref = requireActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        setView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        val inflaterAnim = TransitionInflater.from(requireContext())
        enterTransition = inflaterAnim.inflateTransition(R.transition.fade)
    }

    private fun setView() {
        val kocak = sharedPref.getInt(keyKocak, 0)
        val umum = sharedPref.getInt(keyUmum, 0)
        val iq = sharedPref.getInt(keyIQ, 0)
        val math = sharedPref.getInt(keyMath, 0)
        val flag = sharedPref.getInt(keyFlag, 0)
        val tech = sharedPref.getInt(keyTech, 0)

        binding.tvKocak.text = setText(kocak)
        binding.tvUmum.text = setText(umum)
        binding.tvIQ.text = setText(iq)
        binding.tvMath.text = setText(math)
        binding.tvFlag.text = setText(flag)
        binding.tvTech.text = setText(tech)

    }

    private fun setText(data: Int): String {
        return "$data"
    }
}