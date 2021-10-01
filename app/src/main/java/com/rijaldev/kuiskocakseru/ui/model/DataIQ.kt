package com.rijaldev.kuiskocakseru.ui.model

import com.rijaldev.kuiskocakseru.R

object DataIQ {
    private val pertanyaan = intArrayOf(R.drawable.iq1,
        R.drawable.iq2,
        R.drawable.iq3,
        R.drawable.iq4,
        R.drawable.iq5,
        R.drawable.iq6,
        R.drawable.iq7,
        R.drawable.iq8,
        R.drawable.iq9,
        R.drawable.iq10

    )

    private val pilihanA = arrayOf("3",
        "1",
        "96",
        "Sore",
        "4",
        "58/18",
        "Kucing",
        "4",
        "9",
        "7"
    )

    private val pilihanB = arrayOf("6",
        "7",
        "32",
        "Malam",
        "6",
        "57/18",
        "Ayam",
        "5",
        "8",
        "5"
    )

    private val pilihanC = arrayOf("4",
        "6",
        "92",
        "Siang",
        "10",
        "58/19",
        "Bebek",
        "6",
        "1",
        "9"
    )

    private val pilihanD = arrayOf("5",
        "5",
        "73",
        "Subuh",
        "12",
        "57/19",
        "Angsa",
        "9",
        "12",
        "4"
    )

    private val jawaban = intArrayOf(2,
        4,
        1,
        3,
        1,
        4,
        1,
        3,
        1,
        2
    )

    val listData: ArrayList<Pertanyaan>
        get() {
            val list = arrayListOf<Pertanyaan>()
            for (position in pertanyaan.indices) {
                val quest = Pertanyaan()
                quest.gambar = pertanyaan[position]
                quest.pilihanA= pilihanA[position]
                quest.pilihanB = pilihanB[position]
                quest.pilihanC = pilihanC[position]
                quest.pilihanD = pilihanD[position]
                quest.jawaban = jawaban[position]
                list.add(quest)
            }
            list.shuffle()
            return list
        }
}