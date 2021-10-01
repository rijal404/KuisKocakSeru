package com.rijaldev.kuiskocakseru.ui.model

import com.rijaldev.kuiskocakseru.R

object DataFlags {
    private val pertanyaan = intArrayOf(R.drawable.flag1,
        R.drawable.flag2,
        R.drawable.flag3,
        R.drawable.flag4,
        R.drawable.flag5,
        R.drawable.flag6,
        R.drawable.flag7,
        R.drawable.flag8,
        R.drawable.flag9,
        R.drawable.flag10,
    )

    private val pilihanA = arrayOf("Yaman",
        "Spanyol",
        "Brazil",
        "Amerika",
        "Thailand",
        "India",
        "Indonesia",
        "Turki",
        "Korea Utara",
        "Qatar"
    )

    private val pilihanB = arrayOf("Mesir",
        "Portugal",
        "Afrika",
        "Turki",
        "Ekuador",
        "Guatemala",
        "Australia",
        "Pakistan",
        "Korea Selatan",
        "Bahrain"
    )

    private val pilihanC = arrayOf("Arab Saudi",
        "England",
        "Bahrain",
        "Monako",
        "Filipina",
        "Srilanka",
        "Malaysia",
        "Iran",
        "Jepang",
        "Palestina"
    )

    private val pilihanD = arrayOf("Uni Emirat Arab",
        "Argentina",
        "Bangladesh",
        "Denmark",
        "Vietnam",
        "Hungaria",
        "Thailand",
        "Irak",
        "Kanada",
        "Yaman"
    )

    private val jawaban = intArrayOf(3,
        4,
        1,
        4,
        3,
        2,
        1,
        4,
        2,
        3
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