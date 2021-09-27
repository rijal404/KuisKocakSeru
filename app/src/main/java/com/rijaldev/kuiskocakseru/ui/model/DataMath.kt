package com.rijaldev.kuiskocakseru.ui.model

import com.rijaldev.kuiskocakseru.R

object DataMath {
    private val pertanyaan = arrayOf("3 + 4 = ...",
        "20 - 3 + 2 = ...",
        "7 - 5 = ...",
        "5 + 2 x 3 = ...",
        "-3 + 5 = ...",
        "6 : -2 = ...",
        "3 + ... = 8",
        "... x 7 = 28",
        "-24 - 5 = ...",
        "5 x 6 : 3 = ..."
    )

    private val pilihanA = arrayOf("4",
        "16",
        "1",
        "11",
        "9",
        "3",
        "5",
        "2",
        "28",
        "11"
    )

    private val pilihanB = arrayOf("9",
        "19",
        "12",
        "10",
        "2",
        "-2",
        "10",
        "6",
        "-29",
        "12"
    )

    private val pilihanC = arrayOf("7",
        "21",
        "0",
        "9",
        "7",
        "2",
        "8",
        "4",
        "-28",
        "9"
    )

    private val pilihanD = arrayOf("21",
        "17",
        "2",
        "8",
        "11",
        "-3",
        "4",
        "5",
        "-19",
        "10"
    )

    private val jawaban = intArrayOf(3,
        2,
        4,
        1,
        2,
        4,
        1,
        3,
        2,
        4
    )

    val listData: ArrayList<Pertanyaan>
        get() {
            val list = arrayListOf<Pertanyaan>()
            for (position in pertanyaan.indices) {
                val quest = Pertanyaan()
                quest.pertanyaan = pertanyaan[position]
                quest.pilihanA= pilihanA[position]
                quest.pilihanB = pilihanB[position]
                quest.pilihanC = pilihanC[position]
                quest.pilihanD = pilihanD[position]
                quest.jawaban = jawaban[position]
                list.add(quest)
            }
            return list
        }
}