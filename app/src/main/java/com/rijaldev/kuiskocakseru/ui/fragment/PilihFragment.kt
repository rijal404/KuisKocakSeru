package com.rijaldev.kuiskocakseru.ui.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.rijaldev.kuiskocakseru.R
import com.rijaldev.kuiskocakseru.databinding.FragmentPilihBinding
import com.rijaldev.kuiskocakseru.ui.OnBackPressed

class PilihFragment : Fragment() {

    private lateinit var binding: FragmentPilihBinding
    private lateinit var mp: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPilihBinding.inflate(inflater, container, false)
        mp = MediaPlayer.create(context, R.raw.pop)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setBackgroundDrawableResource(R.drawable.background)
        val inflaterAnim = TransitionInflater.from(requireContext())
        enterTransition = inflaterAnim.inflateTransition(R.transition.slide)
        buttonListeners()
    }

    private fun buttonListeners() {
        binding.btnKocak.setOnClickListener {
            mp.start()
            setIntent(1, GameFragment())
        }
        binding.btnUmum.setOnClickListener {
            mp.start()
            setIntent(2, GameFragment())
        }
        binding.btnIq.setOnClickListener {
            mp.start()
            setIntent(3, GambarFragment())
        }
        binding.btnMath.setOnClickListener {
            mp.start()
            setIntent(4, GambarFragment())
        }
        binding.btnFlag.setOnClickListener {
            mp.start()
            setIntent(5, GambarFragment())
        }
        binding.btnTech.setOnClickListener {
            mp.start()
            setIntent(6, GameFragment())
        }
    }

    private fun setIntent(type: Int, fragment: Fragment) {

        val bundle = Bundle().apply { putInt("type", type) }
        fragment.arguments = bundle

        val trans = activity?.supportFragmentManager?.beginTransaction()
        trans?.replace(R.id.main_frame, fragment)
        trans?.addToBackStack("pilih")
        trans?.commit()
    }
}